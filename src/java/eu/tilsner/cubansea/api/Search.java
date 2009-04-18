package eu.tilsner.cubansea.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.Collections;
import eu.tilsner.cubansea.cluster.ClusteredResult;
import eu.tilsner.cubansea.cluster.ClusteringAlgorithm;
import eu.tilsner.cubansea.prepare.PreparationAlgorithm;
import eu.tilsner.cubansea.prepare.PreparedResult;
import eu.tilsner.cubansea.search.SearchEngineException;
import eu.tilsner.cubansea.search.SearchResult;
import eu.tilsner.cubansea.topic.TopicGeneratorAlgorithm;
import eu.tilsner.cubansea.utilities.TechnicalError;

/**
 * Primary facade object that can be used when dealing with the cubansea API.
 * It creates the different clusters and will be used by them to retrieve
 * additional results later on. It depends on a given configuration.
 * 
 * @author Matthias Tilsner
 */
public class Search {
	private	Configuration 						configuration;
	private	eu.tilsner.cubansea.search.Search	search;
	private	int									cacheStatus;
	private	PreparationAlgorithm				prepAlgorithm;
	private	ClusteringAlgorithm					clusterAlgorithm;
	private List<String>						terms;
	private	Map<eu.tilsner.cubansea.cluster.Cluster,Cluster> clusters;
	private boolean								done;
	
	/**
	 * Updates guesses the total number of result counts for each cluster.
	 */
	private void guessResultCounts() {
		for(Cluster _cluster: clusters.values()) {
			_cluster.setResultCountGuess((int) Math.round(_cluster.getCurrentResultCount()*1.0/cacheStatus*search.getResultCount()));
		}
	}
	
	/**
	 * Fetches the next chunk of results from the search engine and transforms the result
	 * into clustered results.
	 * 
	 * @return An ordered list of clustered results. Ordered in the same way as retrieved from
	 * 		   the search engine.
	 * @throws NoMoreResultsException The end of the result list has been reached. No additional
	 * 		   						  results can be fetched.
	 */
	private List<ClusteredResult> performFetch() throws NoMoreResultsException {
		if(cacheStatus >= search.getResultCount()) throw new NoMoreResultsException("all results fetched");
		List<SearchResult> _searchResults 			= search.getResults(cacheStatus, 
																		Math.min(configuration.getBlockSize(),search.getResultCount()));
		List<PreparedResult> _preparedResults		= prepAlgorithm.prepareResults(_searchResults);
		List<ClusteredResult> _clusteredResults 	= new ArrayList<ClusteredResult>();
		for(PreparedResult _result: _preparedResults) {
			_clusteredResults.add(clusterAlgorithm.createClusteredResult(clusters.keySet(), _result));
		}
		cacheStatus += _clusteredResults.size();
		return _clusteredResults;
	}
	
	/**
	 * All results are fetched. No more results can be expected.
	 * 
	 * @return
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * Wrapper for the performFetch method. It can be invoked by a cluster in order to request
	 * additional results.
	 * 
	 * @return A map of the new found results assigned to the clusters.
	 * @throws NoMoreResultsException The end of the result list has been reached.
	 */
	public Map<Cluster,List<Result>> fetchNextBlock() throws NoMoreResultsException {
		List<ClusteredResult> _results = performFetch();
		Map<Cluster,List<Result>> _assignments = new HashMap<Cluster,List<Result>>();
		for(ClusteredResult _result: _results) {
			for(eu.tilsner.cubansea.cluster.Cluster _cluster: clusterAlgorithm.determineRelevantClusters(clusters.keySet(), _result)) {
				if(_assignments.get(clusters.get(_cluster)) == null) 
					_assignments.put(clusters.get(_cluster), new ArrayList<Result>());
				_assignments.get(clusters.get(_cluster)).add(new Result(_result));
			}
		}
		for(Map.Entry<Cluster,List<Result>> _entry: _assignments.entrySet()) {
			_entry.getKey().addResults(_entry.getValue());
		}
		//guessResultCounts();
		return _assignments;
	}
	
	/**
	 * Returns the search terms that were used for generating
	 * this search result.
	 * 
	 * @return A list of the search terms.
	 */
	protected List<String> getTerms() {
		return terms;
	}
	
	/**
	 * Returns the clusters generated by this search.
	 * 
	 * @return The clusters.
	 */
	public Collection<Cluster> getClusters() {
		List<Cluster> _clusters = new ArrayList<Cluster>();
		_clusters.addAll(clusters.values());
		Collections.sort(_clusters,new Comparator<Cluster>(){
			@Override
			public int compare(Cluster o1, Cluster o2) {
				return o2.getResultCountGuess()-o1.getResultCountGuess();
			}
			
		});
		int _index = 0;			
		List<Color> _colors = configuration.getClusterColors();
		for(Cluster _cluster: _clusters) {
			_cluster.setBaseColor(_colors.get(_index++%_colors.size()));
		}
		return _clusters;
	}
	
	/**
	 * Generates a new search.
	 * 
	 * @param terms The search terms that shall be used when searching the web.
	 * @param config The configuration for this search.
	 */
	public Search(List<String> _terms, Configuration config) {
		try {
			clusters			= new HashMap<eu.tilsner.cubansea.cluster.Cluster,Cluster>();
			configuration		= config;
			terms				= _terms;
			prepAlgorithm		= config.getPreparationAlgorithm();
			clusterAlgorithm	= config.getClusteringAlgorithm();
			cacheStatus			= config.getClusteringBase();
			search				= config.getSearchEngine().createSearch(terms, cacheStatus);
			List<SearchResult> _sres	= search.getResults(0, cacheStatus);
			List<PreparedResult> _pres	= prepAlgorithm.prepareResults(_sres);
			Collection<eu.tilsner.cubansea.cluster.Cluster> _clusters = 
				clusterAlgorithm.createClusters(_pres, config.getNumberOfClusters());
			TopicGeneratorAlgorithm _topicAlgorithm = config.getTopicGeneratorAlgorithm();
			for(eu.tilsner.cubansea.cluster.Cluster _cluster: _clusters) {
				clusters.put(_cluster,new Cluster(_cluster, 
												  null, 
												  this, 
												  _topicAlgorithm.generateTopic(_cluster,terms)));
			}
			guessResultCounts();			
		} catch (SearchEngineException e) {
			throw new TechnicalError(e);
		}
	}
}
