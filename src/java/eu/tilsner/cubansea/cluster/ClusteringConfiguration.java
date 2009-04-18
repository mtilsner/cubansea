package eu.tilsner.cubansea.cluster;

import eu.tilsner.cubansea.distance.DistanceAlgorithm;

public interface ClusteringConfiguration {
	
	/**
	 * Determines how many clusters shall be created. 
	 * 
	 * @return The number of clusters.
	 */
	public int getNumberOfClusters();
	
	/**
	 * Determines how sensitive clusters are in getting results
	 * assigned. A number of 0 means that no results are being
	 * assigned naturally to a cluster: all results will simply
	 * be put the one cluster closest to them. A sensitivity of 1
	 * comes down to splitting the results equally among the
	 * clusters. A result can never be assigned to all clusters.
	 * Greater than one creates higher cluster overlapping 
	 *
	 * @return The sensitivity value.
	 */
	public double getSensitivity();
	
	/**
	 * Determines the minimal distance two cluster centroids may
	 * have. If they are closer, some algorithms may want to collapse
	 * them.
	 * 
	 * @return The minimal distance.
	 */
	public double getMinimalClusterDistance();

	
	/**
	 * Creates the algorithm that shall be used for calculating
	 * the distance between different results.
	 * 
	 * @return An instance of the distance algorithm.
	 */
	public DistanceAlgorithm getDistanceAlgorithm();

}
