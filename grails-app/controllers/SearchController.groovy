import java.util.concurrent.Semaphore;

import eu.tilsner.cubansea.api.NoMoreResultsException
import eu.tilsner.cubansea.utilities.TechnicalError

class SearchController {

	final static String DEFAULT_CONFIGURATION_NAME = "DEFAULT"	
	SearchService searchService
	
	def next = {
		session["current"]["lock"].acquire()
		try {
			render(contentType:'text/json') {
				searchService.next(session["current"]["search"]).each {key,value ->
					def resultIndex = key.currentResults.size()
					"${key.id}" {
						value.each { result ->
							resultIndex++
							output(overview:g.render(template:"overviewElement",model:[result:result,cluster:key]),
								   details:g.render(template:"element",model:[result:result,cluster:key, terms:session["current"]["terms"]], 
										                                      collapsed:(session["config"].initiallyExpandedResults!=-1 && resultIndex>session["config"].initiallyExpandedResults)))
						}
					}
				}
			}
		} catch(NoMoreResultsException ne) {
			render("done")
		} catch(TechnicalError te) {
			render("retry")
		}
		session["current"]["lock"].release()
	}
	
	def cluster = {
		session["current"]["lock"].acquire()
		def cluster = searchService.cluster(session["current"]["search"],params.cluster)
		render(template: "cluster", model: [cluster:cluster, terms:session["current"]["terms"], 
		                                    initiallyExpandedResults:session["config"].initiallyExpandedResults,
		                                    autoFetching: session["config"].autoFetching,
		                                    done: session["current"]["search"].isDone()])
		session["current"]["lock"].release()
	}
	
    def index = {
		if(!session["config"] || params?.config) {
			session["config"] = (params.config && Configuration.findByName(params.config)) ? Configuration.findByName(params.config) : Configuration.findByName(DEFAULT_CONFIGURATION_NAME)
		}
		if(!session["search"]) session["search"] = [:]
    	if(params.q){
    		if(!session["search"][params.q]) {
    			try {
    				session["search"][params.q] = [:]
    				session["search"][params.q]["terms"]  = searchService.terms(params.q)
    				session["search"][params.q]["search"] = searchService.search(session["search"][params.q]["terms"],session["config"])
    				session["search"][params.q]["lock"]	  = new Semaphore(1)
    			} catch(Throwable e) {
    				session["search"][params.q] = null
    				throw e
    			}
        	}
    		session["current"] 	= session["search"][params.q]
    	}
    	if(session["current"]) {
    		session["current"]["lock"].acquire()
    		render(view: "search", model: [q: params.q, terms: session["current"]["terms"],
    		                               clusters: session["current"]["search"].clusters,
    		                               autoFetching: session["config"].autoFetching,
    		                               clusterCount: session["current"]["search"].clusters.size(),
    		                               totalResultCount: session["current"]["search"].totalResultCount])
           	session["current"]["lock"].release()
    	} else
    		render(view: "portal",model: [q: params.q])
    }
}
