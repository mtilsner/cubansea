var cubansea = {
	configuration : {
		OVERVIEW_CHILDREN : 20,
		MAX_TRIES :			 5,
//		EFFECT_DURATION :	0.2,
		STATIC_LOADING_HANDLE : 'loading more results '
	},
	
	try : 0,
	filterValues: {},
	windows: {},
	
	addToOverview : function(clusterid, resultList) {
		var pane = $(clusterid+"_overview").down(".clusterOverview_results_content");
		while(resultList.length > 0 && cubansea.configuration.OVERVIEW_CHILDREN > pane.childElements().length) {
			pane.insert({bottom: resultList.shift().overview});
		}
	},
	
	addToCluster : function(clusterid, resultList) {
		if(!$(clusterid+"_resultList")) return
		var handle = $(clusterid+"_handle");
		while(resultList.length > 0) {
			handle.insert({before: resultList.shift().details});
//			new Effect.SlideDown(handle.previous(0), {duration:cubansea.configuration.EFFECT_DURATION, queue:'end'});
		}
	},
	
	addResults : function(resultsObject) {
		for(var cluster in resultsObject){
			cubansea.addToOverview(cluster,$A(resultsObject[cluster]));
			if($(cluster+"_resultList")) cubansea.addToCluster(cluster, $A(resultsObject[cluster]));
			cubansea.filter(cluster);
		}
	},
	
	openCluster: function(clusterid,clustertitle) {
		if(cubansea.windows[clusterid]) {
			cubansea.windows[clusterid].show();
			cubansea.windows[clusterid].toFront();
		} else {
			var win = new Window({
				title: clustertitle,
				top: 80,
				left: (document.viewport.getWidth()-900)/2,
				height: document.viewport.getHeight()-150,
				width: 900,
				minimizable: false,
				className: 'mac_os_x',
				effectOptions: {duration: 0.5}
			});
			win.show();
			win.setZIndex(20);
			win.toFront();
			var upb = new UndefinedProgressBar({id: clusterid+'loader', width: 300});
			win.setHTMLContent(upb.toHTML());
			var dim = win.getSize();
			$(clusterid+'loader').setStyle({position: 'absolute', left: (dim.width-300)/2+'px', top: (dim.height-25)/2+'px'});
			upb.start();
			new Ajax.Request('/CubanSea/cluster/?cluster='+clusterid, {
				method: 'get',
				onComplete : (function(window,undefinedProgressBar,request) {
					upb.stop();
					win.setHTMLContent(request.responseText);
				}).curry(win).curry(upb)
			})
			cubansea.windows[clusterid] = win;
		}
	},
	
	hideCluster: function(clusterid) {
		$(clusterid).hide();
	},
	
	fetchNext : function() {
		if(!AUTO_FETCHING) $$(".cluster_handle").each(function(handle) { handle.update(cubansea.configuration.STATIC_LOADING_HANDLE) }); 
		new Ajax.Request('/CubanSea/next', {
			method: 'get',
			onComplete: function(response) { 
				try {
					var result = response.responseText;
					if(result == "done") {
						$$(".cluster_handle").invoke("replace","");
						return;
					} else if(result == "retry") {
						cubansea.fetchNext();
						return;
					} else
						cubansea.addResults(result.evalJSON(true));
					cubansea.try = 0;
				}catch(e) {
					cubansea.try++;
					if(cubansea.try >= cubansea.configuration.MAX_TRIES) return;
				}
				if(AUTO_FETCHING)
					cubansea.fetchNext();
				else
					$$(".cluster_handle").each(function(handle) { handle.update("<a href='/CubanSea/next' onClick='javascript:cubansea.fetchNext();return false;'>[load more results]</a>") });
			}
		});
	},
	
	matches: function(value, whole) {
		return whole.toLowerCase().indexOf(value.toLowerCase()) != -1;
	},
	
	filter: function(clusterid) {
		if(!$(clusterid+"_filter")) return;
		var filter  		= $(clusterid+"_filter").value;
		var checkSummary	= ($(clusterid+"_filter_summary").value == "on");
		if(!cubansea.filterValues[clusterid]) cubansea.filterValues[clusterid] = {filter: "", checkSummary: false}
		if(cubansea.filterValues[clusterid].filter == filter && cubansea.filterValues[clusterid].checkSummary == checkSummary) return;
		$(clusterid+"_resultList").childElements().each(function(element){
			if(element.hasClassName("cluster_handle")) return;
			var title 	= element.readAttribute("resultTitle")
			var summary = element.readAttribute("resultSummary")
			var url		= element.readAttribute("resultURL");
			if(cubansea.matches(filter,title) || cubansea.matches(filter,url) || (checkSummary && cubansea.matches(filter,summary)))
				element.show();
			else
				element.hide();
		});
		cubansea.filterValues[clusterid].filter = filter;
		cubansea.filterValues[clusterid].checkSummary == checkSummary;
	},
	
	checkFilter: function(pe) {
		$$(".cluster_resultList").each(function(cluster){
			var id = cluster.readAttribute("clusterId");
			if($(id+"_filter")) cubansea.filter(id);
		});
	},
	
	toggleSummary: function(resultid) {
		$(resultid+"_summary").toggle();
		$(resultid+"_relevanceSquare").toggleClassName("expanded");
		$(resultid+"_relevanceSquare").toggleClassName("collapsed");
	},
	
	animateLoadingHandle: function() {
		$$(".cluster_handle").each(function(handle){
			if(handle.down("a")) return;
			var current = handle.innerHTML.length - cubansea.configuration.STATIC_LOADING_HANDLE.length;
			current = (current+1)%4;
			var replace = cubansea.configuration.STATIC_LOADING_HANDLE;
			for(i=0;i<current;i++) { replace += "."; }
			handle.update(replace);
		});
	}
}
 
if(AUTO_FETCHING) Event.observe(window, "load", cubansea.fetchNext);
new PeriodicalExecuter(cubansea.checkFilter,0.1);
new PeriodicalExecuter(cubansea.animateLoadingHandle,0.4);