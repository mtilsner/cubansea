package eu.tilsner.cubansea.test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import eu.tilsner.cubansea.api.Configuration;
import eu.tilsner.cubansea.cluster.ClusteringAlgorithm;
import eu.tilsner.cubansea.cluster.simplefuzzykmeans.SimpleFuzzyKMeansClusteringAlgorithm;
import eu.tilsner.cubansea.distance.DistanceAlgorithm;
import eu.tilsner.cubansea.distance.euclid.EuclidDistanceAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationAlgorithm;
import eu.tilsner.cubansea.prepare.pottersummary.AbsolutePotterSummaryPreparationAlgorithm;
import eu.tilsner.cubansea.search.SearchEngine;
import eu.tilsner.cubansea.search.yahoo.YahooSearchEngine;
import eu.tilsner.cubansea.topic.TopicGeneratorAlgorithm;
import eu.tilsner.cubansea.topic.wordfrequency.WordFrequencyTopicGeneratorAlgorithm;

/**
 * A simple implementation of the configuration interface.
 * 
 * @author Matthias Tilsner
 */
public class TestConfiguration implements Configuration {

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getBlockSize()
	 */
	@Override
	public int getBlockSize() {
		return 25;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getClusterColors()
	 */
	@Override
	public List<Color> getClusterColors() {
		List<Color> _colors = new ArrayList<Color>();
		_colors.add(Color.RED);
		_colors.add(Color.BLUE);
		_colors.add(Color.GREEN);
		return _colors;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getClusteringAlgorithm()
	 */
	@Override
	public ClusteringAlgorithm getClusteringAlgorithm() {
		return new SimpleFuzzyKMeansClusteringAlgorithm();
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getClusteringBase()
	 */
	@Override
	public int getClusteringBase() {
		return 100;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getNumberOfClusters()
	 */
	@Override
	public int getNumberOfClusters() {
		return 4;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getPreparationAlgorithm()
	 */
	@Override
	public PreparationAlgorithm getPreparationAlgorithm() {
		return new AbsolutePotterSummaryPreparationAlgorithm();
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getSearchEngine()
	 */
	@Override
	public SearchEngine getSearchEngine() {
		return new YahooSearchEngine();
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getTopicGeneratorAlgorithm()
	 */
	@Override
	public TopicGeneratorAlgorithm getTopicGeneratorAlgorithm() {
		return new WordFrequencyTopicGeneratorAlgorithm(3);
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getDistanceAlgorithm()
	 */
	@Override
	public DistanceAlgorithm getDistanceAlgorithm() {
		return new EuclidDistanceAlgorithm();
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getSensitivity()
	 */
	@Override
	public double getSensitivity() {
		return 1.0;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getResultSummaryWeight()
	 */
	@Override
	public int getResultSummaryWeight() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getResultTitleWeight()
	 */
	@Override
	public int getResultTitleWeight() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.api.Configuration#getResultUrlWeight()
	 */
	@Override
	public int getResultUrlWeight() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see eu.tilsner.cubansea.cluster.ClusteringConfiguration#getMinimalClusterDistance()
	 */
	@Override
	public double getMinimalClusterDistance() {
		return 0.001;
	}
	
}
