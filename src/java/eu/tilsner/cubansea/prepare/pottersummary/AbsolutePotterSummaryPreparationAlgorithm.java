package eu.tilsner.cubansea.prepare.pottersummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.tilsner.cubansea.prepare.PreparationAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationConfiguration;
import eu.tilsner.cubansea.prepare.PreparedResult;
import eu.tilsner.cubansea.search.SearchResult;
import eu.tilsner.cubansea.search.SearchResultException;
import eu.tilsner.cubansea.utilities.StemmerHelper;
import eu.tilsner.cubansea.utilities.StringHelper;

/**
 * Simple preparation algorithm. It simply splits the the summary of
 * search results into words. The words are then reduced to their word
 * stem using the PotterStemmer and the occurrences of word stems are
 * counted.
 * 
 * @author Matthias Tilsner
 */
public class AbsolutePotterSummaryPreparationAlgorithm implements PreparationAlgorithm {
	
	private PreparationConfiguration configuration;
	
	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.prepare.PreparationAlgorithm#prepareResults(java.util.List)
	 */
	@Override
	public List<PreparedResult> prepareResults(List<SearchResult> items) {
		Map<SearchResult, Map<String,Double>> _wordFrequencies = new HashMap<SearchResult, Map<String, Double>>();
		for(SearchResult _item: items) {
			_wordFrequencies.put(_item, calculateWordFrequencies(_item));
		}
		_wordFrequencies = processWordFrequencies(_wordFrequencies);
		List<PreparedResult> _results = new ArrayList<PreparedResult>();
		for(SearchResult _item: items) {
			_results.add(createPreparedResult(_item, _wordFrequencies.get(_item)));
		}
		return _results;
	}

	/**
	 * Processes the word frequency map. to enable refinement of this class by
	 * inheritance.
	 * 
	 * @param wordFrequencies The frequency map.
	 * @return The resulting (processed) frequency map.
	 */
	protected Map<SearchResult, Map<String,Double>> processWordFrequencies(Map<SearchResult, Map<String,Double>> wordFrequencies) {
		return wordFrequencies;
	}
	
	/**
	 * Wrapper method that creates a prepared result out of a search result.
	 * 
	 * @param result The search result.
	 * @return The prepared result wrapping the search result.
	 */
	private PreparedResult createPreparedResult(SearchResult result, Map<String,Double> wordFrequencies) {
		return new PotterSummaryPreparedResult(result, wordFrequencies);
	}
	
	/**
	 * Wrapper function for the calculation of word frequencies, based upon the result. The
	 * method creates a string that is used for generating the content.
	 * 
	 * @param result
	 * @return
	 */
	protected Map<String, Double> calculateWordFrequencies(SearchResult result) {
		String _content =   		StringHelper.join(StringHelper.multiply(result.getTitle(), configuration.getResultTitleWeight()), " ")
		  					+ " " + StringHelper.join(StringHelper.multiply(result.getSummary(), configuration.getResultSummaryWeight()), " ");
		try {
			_content += " " + StringHelper.join(StringHelper.multiply(result.getURL().toString(), configuration.getResultUrlWeight()), " ");
		} catch (SearchResultException e) { }
		return calculateWordFrequencies(_content);
	}
	
	/**
	 * Calculates the number of occurrences of the words of a given string. For this
	 * the string has to be split into words and these have to be reduced to their
	 * stems. 
	 * 
	 * @param content The string whose words are to be counted.
	 * @return A map containing all words used in the content and the number of their occurrences.
	 */
	protected Map<String, Double> calculateWordFrequencies(String content) {
		Map<String, Double> _results = new HashMap<String, Double>();
		for(String _word: getStems(getWords(content))) {
			if(_results.containsKey(_word))
				_results.put(_word, _results.get(_word)+1);
			else
				_results.put(_word, 1.0);
		}
		return _results;
	}

	/**
	 * Turns a all words in a list into their stems using the StemmerHelper wrapping the
	 * PotterStemmer algorithm. The stems are filtered in order to remove any trivial
	 * words not required to be counted. 
	 * 
	 * @param words The original list of words.
	 * @return The list with all words changed to their stem.
	 */
	private List<String> getStems(List<String> words) {
		List<String> _stems = new ArrayList<String>();
		for(String _word: words) {
			_stems.add(StemmerHelper.stem(_word));
		}
		return filterWords(_stems);
	}
	
	/**
	 * Returns all non-trivial words found inside a string.
	 * 
	 * @param content The string of the content with words separated by spaces.
	 * @return A filtered list of the words to be considered.
	 */
	private List<String> getWords(String content) {
		if(content == null) return new ArrayList<String>();
		content = content.replaceAll(PreparedResult.INVALID_CHARACTER_PATTERN, " ");
		content = content.replaceAll("\\s+", " ");
		List<String> _words = StringHelper.split(content, " ");
		return filterWords(_words);
	}
	
	/**
	 * Filters a list of words according to the filtering information provided
	 * in <i>PreparedResult</i>. Returns a new list without all words specified
	 * to be ignored.
	 * 
	 * @param words The list of words to be filtered.
	 * @return The filtered word list.
	 */
	private List<String> filterWords(List<String> words) {
		List<String> _filtered = new ArrayList<String>();
		for(String _word: words) {
			_word = _word.toLowerCase();
			if(_word.length() > PreparedResult.IGNORED_WORD_LENGTH && !PreparedResult.IGNORED_WORDS.contains(_word))
				_filtered.add(_word);
		}
		return _filtered;
	}
	
	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.prepare.PreparationAlgorithm#setConfiguration(eu.tilsner.cubansea.prepare.PreparationConfiguration)
	 */
	@Override
	public void setConfiguration(PreparationConfiguration configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Getter for the configuration used by this algorithm
	 * 
	 * @return The configuration used.
	 */
	protected PreparationConfiguration getConfiguration() {
		return configuration;
	}	
}
