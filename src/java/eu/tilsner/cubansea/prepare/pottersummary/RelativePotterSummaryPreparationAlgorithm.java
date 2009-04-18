package eu.tilsner.cubansea.prepare.pottersummary;

import java.util.HashMap;
import java.util.Map;

import eu.tilsner.cubansea.search.SearchResult;

/**
 * Extension of the normal potter summary preparation algorithm. It normalizes the frequency
 * vectors to a maximum element size of "1".
 * 
 * @author matthias
 */
public class RelativePotterSummaryPreparationAlgorithm extends AbsolutePotterSummaryPreparationAlgorithm {

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.prepare.pottersummary.AbsolutePotterSummaryPreparationAlgorithm#processWordFrequencies(java.util.Map)
	 */
	@Override
	protected Map<SearchResult, Map<String,Double>> processWordFrequencies(Map<SearchResult, Map<String,Double>> wordFrequencies) {
		wordFrequencies = super.processWordFrequencies(wordFrequencies);
		Map<SearchResult, Map<String,Double>> _newWordFrequencies = new HashMap<SearchResult, Map<String,Double>>();
		for(Map.Entry<SearchResult, Map<String,Double>> _frequencies: wordFrequencies.entrySet()) {
			double _maxFrequency = 0.0;
			for(Double _frequency: _frequencies.getValue().values()) {
				_maxFrequency = Math.max(_maxFrequency, _frequency);
			}
			Map<String,Double> _newFrequencies = new HashMap<String,Double>();
			for(Map.Entry<String,Double> _entry: _frequencies.getValue().entrySet()) {
				_newFrequencies.put(_entry.getKey(), _entry.getValue()/_maxFrequency);
			}
			_newWordFrequencies.put(_frequencies.getKey(), _newFrequencies);
		}
		return _newWordFrequencies;
	}

}
