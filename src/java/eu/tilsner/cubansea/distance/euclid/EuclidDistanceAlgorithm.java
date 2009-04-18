package eu.tilsner.cubansea.distance.euclid;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import eu.tilsner.cubansea.distance.DistanceAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationHelper;
import eu.tilsner.cubansea.prepare.PreparedResult;

/**
 * Simple implementation of the euclidean distance. For this, the sum is taken of
 * the square of the difference of all vector elements. The square root of this
 * sum is the euclidean distance
 * 
 * @author matthias
 *
 */
public class EuclidDistanceAlgorithm implements DistanceAlgorithm {

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.distance.DistanceAlgorithm#getDistance(eu.tilsner.cubansea.prepare.PreparedResult, eu.tilsner.cubansea.prepare.PreparedResult)
	 */
	@Override
	public double getDistance(PreparedResult item1, PreparedResult item2) {
		double distance = 0.0;
		Set<PreparedResult> items = new HashSet<PreparedResult>();
		items.add(item1);
		items.add(item2);
		Collection<String> attributes = PreparationHelper.getSharedWords(items);
		for(String _attribute: attributes) {
			distance += Math.pow(item1.getFrequency(_attribute) - item2.getFrequency(_attribute), 2);
		}
		return Math.sqrt(distance);
	}

}
