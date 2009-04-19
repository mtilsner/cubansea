<div class="cluster_topbar">
	<span class="filter_message">search results:</span>
	<input type="text" name="filter" id="${cluster.id}_filter" class="filter" />
	<input type="checkbox" name="summary" valign="bottom" id="${cluster.id}_filter_summary" class="filter_summary" />
	<span class="filter_message filter_summary_message">include summary in search</span>
</div>
<div class="cluster_scroll">
	<div id="${cluster.id}_resultList" class="cluster_resultList" clusterId="${cluster.id}">
		<g:set var="resultIndex" value="${0}" />
		<g:each var="result" in="${cluster.currentResults}">
			<g:set var="resultIndex" value="${resultIndex+1}" />
			<g:render template="element" model="${[result:result,cluster:cluster,terms:terms,collapsed:(initiallyExpandedResults!=-1 && resultIndex>initiallyExpandedResults)]}" />
		</g:each>
		<g:if test="${!done}">
			<div id="${cluster.id}_handle" class="cluster_handle">
				<g:if test="${autoFetching}">
					loading more results ...
				</g:if>
				<g:else>
					<a href="${createLink(controller:'search',action:'next')}" onClick="javascript:cubansea.fetchNext();return false;">
						[load more results]
					</a>
				</g:else>
			</div>
		</g:if>
	</div>	
</div>
<div class="cluster_background" style="background-color:#${cluster.cssColor};"></div>