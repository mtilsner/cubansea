import eu.tilsner.cubansea.distance.cosinusangle.CosinusAngleDistanceAlgorithm
import eu.tilsner.cubansea.prepare.PreparedResult

import grails.test.*

public class AngleDistanceAlgorithmTests extends GrailsUnitTestCase{
	def algorithm
	
	protected void setUp() {
        super.setUp()
        algorithm = new CosinusAngleDistanceAlgorithm();
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNullAngle() {
    	def item1 = [getSearchResult: {return null},
    	             getWords: {return ["hello", "world", "test", "one"]},
    	             getAllFrequencies: {return ["hello":1.0D, "world":2.0D, "test":1.0D, "one":2.0D]},
    	             getFrequency: {word -> return (double) ["hello":1.0D, "world":2.0D, "test":1.0D, "one":2.0D].get(word)}] as PreparedResult
      	def item2 = [getSearchResult: {return null},
      	             getWords: {return ["hello", "world", "test", "one"]},
      	             getAllFrequencies: {return ["hello":2.0D, "world":4.0D, "test":2.0D, "one":4.0D]},
      	             getFrequency: {word -> return (double) ["hello":2.0D, "world":4.0D, "test":2.0D, "one":4.0D].get(word)}] as PreparedResult
      	             
      	assertEquals 0.0D, algorithm.getDistance(item1,item2)
      	assertEquals 0.0D, algorithm.getDistance(item2,item1)
    }
    
    void testRightAngle() {
    	def item1 = [getSearchResult: {return null},
    	             getWords: {return ["hello"]},
    	             getAllFrequencies: {return ["hello":1.0D,"world":0.0D]},
    	             getFrequency: {word -> return ["hello":1.0D,"world":0.0D].get(word)}].asType( PreparedResult )
      	def item2 = [getSearchResult: {return null},
      	             getWords: {return ["world"]},
      	             getAllFrequencies: {return ["hello":0.0D, "world":1.0D]},
      	             getFrequency: {word -> ["hello":0.0D, "world":1.0D].get(word)}] as PreparedResult
      	             
      	assertEquals Math.PI/2, algorithm.getDistance(item1,item2)
      	assertEquals Math.PI/2, algorithm.getDistance(item2,item1)
    }
	
    void testEqualAngles() {
    	def item1 = [getSearchResult: null,
    	             getWords: {return ["hello", "world", "test", "one"]},
    	             getAllFrequencies: {return ["hello":0.0D, "world":3.0D, "test":1.0D, "one":4.0D]},
    	             getFrequency: {word -> return ["hello":0.0D, "world":3.0D, "test":1.0D, "one":4.0D].get(word)}] as PreparedResult
      	def item2 = [getSearchResult: {return null},
      	             getWords: {return ["hello", "world", "test", "one"]},
      	             getAllFrequencies: {return ["hello":2.0D, "world":4.0D, "test":0.0D, "one":1.0D]},
      	             getFrequency: {word -> return ["hello":2.0D, "world":4.0D, "test":0.0D, "one":1.0D].get(word)}] as PreparedResult
      	             
      	assertEquals algorithm.getDistance(item1,item2), algorithm.getDistance(item2,item1)
    }
}
