package eu.tilsner.cubansea.prepare;

import java.util.Collection;
import java.util.HashSet;

public class PreparationHelper {
	/**
	 * Extracts the union of all words provided by the result item list.
	 * 
	 * @param items Items from which the words shall be taken.
	 * @return Union of all occurring word sets.
	 */
	public static Collection<String> getSharedWords(Collection<PreparedResult> items) {
		Collection<String> _words = new HashSet<String>();
		for(PreparedResult _item: items) {
			for(String _word: _item.getWords()) {
				if(!_words.contains(_word)) _words.add(_word);
			}
		}
		return _words;
	}

}
