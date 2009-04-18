import java.awt.Color
import java.util.Arrays;

import eu.tilsner.cubansea.api.Configuration as APIConfiguration
import eu.tilsner.cubansea.cluster.ClusteringAlgorithm
import eu.tilsner.cubansea.prepare.PreparationAlgorithm
import eu.tilsner.cubansea.topic.TopicGeneratorAlgorithm
import eu.tilsner.cubansea.search.SearchEngine
import eu.tilsner.cubansea.distance.DistanceAlgorithm

class Configuration implements APIConfiguration {
	def classLoader
    static transients = ['clusteringAlgorithm',
                         'preparationAlgorithm',
                         'topicGeneratorAlgorithm',
                         'distanceAlgorithm',
                         'searchEngine',
                         'clusterColors'] 
	
	/* unique identifier */
    String id
    
    /* ui configuration */
    boolean autoFetching
    int initiallyExpandedResults
	public List<Color> getClusterColors() {
		return topicColors.collect {topicColor -> Color.decode("0x${topicColor.color}")}
	}
	
    /* search engine configuration */
	int blockSize
	
	/* preparation configuration */
	int resultTitleWeight
	int resultSummaryWeight
	int resultUrlWeight

	/* clustering configuration */
	int numberOfClusters
	double sensitivity
	double minimalClusterDistance
	
	/* algorithm configuration */
	int clusteringBase
	public ClusteringAlgorithm getClusteringAlgorithm() {
		return Class.forName(clusteringAlgorithmClassName, false, classLoader).newInstance()
	}
	public PreparationAlgorithm getPreparationAlgorithm() {
		return Class.forName(preparationAlgorithmClassName, false, classLoader).newInstance()
	}
	public TopicGeneratorAlgorithm getTopicGeneratorAlgorithm() {
		return Class.forName(topicGeneratorAlgorithmClassName, false, classLoader).newInstance()
	}
	public DistanceAlgorithm getDistanceAlgorithm() {
		return Class.forName(distanceAlgorithmClassName, false, classLoader).newInstance()
	}
	public SearchEngine getSearchEngine() {
		return Class.forName(searchEngineClassName, false, classLoader).newInstance()
	}

	/* Persistance Helper Attributes */
    List topicColors
    static hasMany = [ topicColors : TopicColor ]

	String clusteringAlgorithmClassName
	String preparationAlgorithmClassName
	String topicGeneratorAlgorithmClassName
	String searchEngineClassName
	String distanceAlgorithmClassName
	
	static mapping = {
    	id generator: 'assigned'
		version false
	}
	
    static constraints = {
    	id(blank:false, nullable:false, maxLength:64) 
		clusteringAlgorithmClassName(validator: {val,obj ->
			try {
				def clazz = Class.forName(val, false, classLoader)
				def inst  = clazz.newInstance()
				def cast  = eu.tilsner.cubansea.cluster.ClusteringAlgorithm.class.cast(inst)
				return true
			} catch(Exception e) {
				return false
			}
		})
		preparationAlgorithmClassName(validator: {val,obj ->
			try {
				def clazz = Class.forName(val, false, classLoader)
				def inst  = clazz.newInstance()
				def cast  = eu.tilsner.cubansea.prepare.PreparationAlgorithm.class.cast(inst)
				return true
			} catch(Exception e) {
				return false
			}
		})
		topicGeneratorAlgorithmClassName(validator: {val,obj ->
			try {
				def clazz = Class.forName(val, false, classLoader)
				def inst  = clazz.newInstance()
				def cast  = eu.tilsner.cubansea.topic.TopicGeneratorAlgorithm.class.cast(inst)
				return true
			} catch(Exception e) {
				return false
			}
		})
		distanceAlgorithmClassName(validator: {val,obj ->
			try {
				def clazz = Class.forName(val, false, classLoader)
				def inst  = clazz.newInstance()
				def cast  = eu.tilsner.cubansea.distance.DistanceAlgorithm.class.cast(inst)
				return true
			} catch(Exception e) {
				return false
			}
		})
		searchEngineClassName(validator: {val,obj ->
			try {
				def clazz = Class.forName(val, false, classLoader)
				def inst  = clazz.newInstance()
				def cast  = eu.tilsner.cubansea.search.SearchEngine.class.cast(inst)
				return true
			} catch(Exception e) {
				return false
			}
		})
    }
}
