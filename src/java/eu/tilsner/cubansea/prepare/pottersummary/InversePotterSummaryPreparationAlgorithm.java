package eu.tilsner.cubansea.prepare.pottersummary;

import java.util.HashMap;
import java.util.Map;

import eu.tilsner.cubansea.search.SearchResult;

/**
 * Extended relative potter summary algorithm. It uses the popular
 * 		TF * IDF
 * formula, calculating the product of the relative word frequency
 * and the inverse document frequency.
 * 
 * @author matthias
 */
public class InversePotterSummaryPreparationAlgorithm extends RelativePotterSummaryPreparationAlgorithm {

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.prepare.pottersummary.AbsolutePotterSummaryPreparationAlgorithm#processWordFrequencies(java.util.Map)
	 */
	@Override
	protected Map<SearchResult, Map<String,Double>> processWordFrequencies(Map<SearchResult, Map<String,Double>> wordFrequencies) {
		/* calculate TF */
		wordFrequencies = super.processWordFrequencies(wordFrequencies);
		
		/* calculate IDF */
		Map<String, Double> _wordOccurences = new HashMap<String, Double>();
		for(Map<String,Double> _occurences: wordFrequencies.values()) {
			for(Map.Entry<String,Double> _occurence: _occurences.entrySet()) {
				if(!_wordOccurences.containsKey(_occurence.getKey())) 
					_wordOccurences.put(_occurence.getKey(), 1.0);
				else
					_wordOccurences.put(_occurence.getKey(), _wordOccurences.get(_occurence.getKey())+1);					
			}
		}
		Map<String, Double> _inverseFrequencies = new HashMap<String, Double>();
		for(Map.Entry<String, Double> _occurence: _wordOccurences.entrySet()) {
			_inverseFrequencies.put(_occurence.getKey(), Math.log(_wordOccurences.size() / _occurence.getValue()));
		}

		/* multiply TF and IDF */
		Map<SearchResult, Map<String,Double>> _newWordFrequencies = new HashMap<SearchResult, Map<String,Double>>();
		for(Map.Entry<SearchResult, Map<String,Double>> _frequencies: wordFrequencies.entrySet()) {
			Map<String,Double> _newFrequencies = new HashMap<String,Double>();
			for(Map.Entry<String,Double> _entry: _frequencies.getValue().entrySet()) {
				_newFrequencies.put(_entry.getKey(), _entry.getValue()*_inverseFrequencies.get(_entry.getKey()));
			}
			_newWordFrequencies.put(_frequencies.getKey(), _newFrequencies);
		}
		return _newWordFrequencies;
	}

}
