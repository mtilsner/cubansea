package eu.tilsner.cubansea.distance;

import eu.tilsner.cubansea.prepare.PreparedResult;

/**
 * Algorithm to be used for calculating the distance between two prepared results.
 * 
 * @author matthias
 */
public interface DistanceAlgorithm {

	/**
	 * Primary method, performs the distance calculation. The interpretation of the
	 * distance has to be handled by the calling algorithm.
	 * 
	 * @param item1 The first result item.
	 * @param item2 The second result item.
	 * @return The distance between two result items.
	 */
	public double getDistance(PreparedResult item1, PreparedResult item2);
	
}
