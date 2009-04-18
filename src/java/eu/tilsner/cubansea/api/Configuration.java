package eu.tilsner.cubansea.api;

import java.awt.Color;
import java.util.List;

import eu.tilsner.cubansea.cluster.ClusteringAlgorithm;
import eu.tilsner.cubansea.cluster.ClusteringConfiguration;
import eu.tilsner.cubansea.prepare.PreparationAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationConfiguration;
import eu.tilsner.cubansea.search.SearchEngine;
import eu.tilsner.cubansea.topic.TopicGeneratorAlgorithm;

public interface Configuration extends PreparationConfiguration, ClusteringConfiguration {
/* user interface specific business logic configuration */
	/**
	 * Determines what colors shall be used for coloring the
	 * different clusters and their results.
	 * 
	 * @return An ordered list of the colors to be used.
	 */
	public List<Color> getClusterColors();
	
	/**
	 * Determines how many results shall be requested in each
	 * request.
	 * 
	 * @return The number of new items requested.
	 */
	public int getBlockSize();

/* algorithm specific business logic configuration */
	/**
	 * Determines how many result items shall be used
	 * for constructing the clusters. 
	 * 
	 * @return The number of items used for the clustering.
	 */
	public int getClusteringBase();
	
	/**
	 * Creates the search engine that is supposed to be
	 * used.
	 * 
	 * @return An instance of a search engine.
	 */
	public SearchEngine getSearchEngine();
	
	/**
	 * Creates the preparaton algorithm that shall be used
	 * for preparing the search results for the clustering
	 * algorithm.
	 * 
	 * @return An instance of a preparation algorithm.
	 */
	public PreparationAlgorithm getPreparationAlgorithm();
	
	/**
	 * Creates the clustering algorithm that shall be used
	 * for creating the clusters.
	 * 
	 * @return An instance of a clustering algorithm.
	 */
	public ClusteringAlgorithm getClusteringAlgorithm();
	
	/**
	 * Creates the algorithm that shall be used for generating
	 * cluster topics.
	 * 
	 * @return An instance of the topic generator algorithm.
	 */
	public TopicGeneratorAlgorithm getTopicGeneratorAlgorithm();
	
}
