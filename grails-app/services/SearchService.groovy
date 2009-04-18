import eu.tilsner.cubansea.api.Search
import eu.tilsner.cubansea.utilities.StringHelper

class SearchService {

    boolean transactional = true
    
    def cluster(search, clusterid) {
		return search.clusters.find { result ->
			result.id == clusterid
		}
	}
    
    def next(search) {
		return search.fetchNextBlock()
	}
    
    def search(terms, config) {
    	def search = new Search(terms, config)
    	return search
    }
	
	def terms(query) {
		return StringHelper.split(query, " ")
	}
}
