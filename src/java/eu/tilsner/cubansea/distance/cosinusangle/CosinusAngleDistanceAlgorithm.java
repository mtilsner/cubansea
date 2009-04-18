package eu.tilsner.cubansea.distance.cosinusangle;

import java.util.Collection;
import java.util.HashSet;

import eu.tilsner.cubansea.distance.DistanceAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationHelper;
import eu.tilsner.cubansea.prepare.PreparedResult;

/**
 * Distance Algorithm treating the two items as vectors and calculating the angle
 * between them. The vector length is being ignored.
 * 
 * @author matthias
 *
 */
public class CosinusAngleDistanceAlgorithm implements DistanceAlgorithm {

	public static final double MAXIMUM_ANGLE = Math.PI;
	
	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.distance.DistanceAlgorithm#getDistance(eu.tilsner.cubansea.prepare.PreparedResult, eu.tilsner.cubansea.prepare.PreparedResult)
	 */
	@Override
	public double getDistance(PreparedResult item1, PreparedResult item2) {
		Collection<PreparedResult> _items = new HashSet<PreparedResult>();
		_items.add(item1);
		_items.add(item2);
		Collection<String> _dimensions = PreparationHelper.getSharedWords(_items);
		double scalarProduct = 0.0;
		for(String _dimension: _dimensions) {
			scalarProduct += item1.getFrequency(_dimension) * item2.getFrequency(_dimension);
		}
		double lengthItem1 = getLengthOfVector(item1.getAllFrequencies().values());
		double lengthItem2 = getLengthOfVector(item2.getAllFrequencies().values());
		if(scalarProduct == 0.0 && lengthItem1 == lengthItem2)
			return 0.0;
		else if(scalarProduct == 0.0)
			return MAXIMUM_ANGLE;
		else
			return Math.acos(Math.min(1,scalarProduct / (lengthItem1 * lengthItem2)));
	}

	private double getLengthOfVector(Collection<Double> _elements) {
		double length = 0.0;
		for(double _element: _elements) {
			length += Math.pow(_element,2);
		}
		return Math.sqrt(length);
	}
	
}
