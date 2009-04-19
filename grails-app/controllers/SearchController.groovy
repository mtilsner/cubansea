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
										                                      collapsed:(session["current"]["config"].initiallyExpandedResults!=-1 && resultIndex>session["current"]["config"].initiallyExpandedResults)))
						}
					}
				}
			}
		} catch(NoMoreResultsException ne) {
			render(contentType:'text/json') {
				message(status:"done")
			}
		} catch(TechnicalError te) {
			render(contentType:'text/json') {
				message(status:"retry")
			}
		}
		session["current"]["lock"].release()
	}
	
	def cluster = {
		session["current"]["lock"].acquire()
		def cluster = searchService.cluster(session["current"]["search"],params.cluster)
		render(template: "cluster", model: [cluster:cluster, terms:session["current"]["terms"], initiallyExpandedResults:session["current"]["config"].initiallyExpandedResults])
		session["current"]["lock"].release()
	}
	
    def index = {
		if(!session["search"]) session["search"] = [:]
    	if(params.q){
    		if(!session["search"][params.q]) {
    			try {
					def config = (params.config && Configuration.findByName(params.config)) ? Configuration.findByName(params.config) : Configuration.findByName(DEFAULT_CONFIGURATION_NAME)
    				session["search"][params.q] = [:]
    				session["search"][params.q]["terms"]  = searchService.terms(params.q)
    				session["search"][params.q]["search"] = searchService.search(session["search"][params.q]["terms"],config)
    				session["search"][params.q]["lock"]	  = new Semaphore(1)
					session["search"][params.q]["config"] = config
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
    		                               clusters: session["current"]["search"].clusters])
           	session["current"]["lock"].release()
    	} else
    		render(view: "portal",model: [q: params.q])
    }
}
