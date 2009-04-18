

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="scaffolding" />
        <title>Configuration List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Configuration</g:link></span>
        </div>
        <div class="body">
            <h1>Configuration List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="name" title="Name" />

                   	        <g:sortableColumn property="numberOfClusters" title="Max.&nbsp;Number&nbsp;of&nbsp;Clusters" />

                   	        <g:sortableColumn property="sensitivity" title="Sensitivity" />

                   	        <g:sortableColumn property="autoFetching" title="Auto&nbsp;Fetching" />

                   	        <g:sortableColumn property="clusteringAlgorithmClassName" title="Clustering Algorithm Class" style="width:100px;" />

                   	        <g:sortableColumn property="searchEngineClassName" title="Search Engine Class" style="width:100px;" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${configurationInstanceList}" status="i" var="configurationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${configurationInstance.id}">${fieldValue(bean:configurationInstance, field:'name')}</g:link></td>
                        
                            <td>${fieldValue(bean:configurationInstance, field:'numberOfClusters')}</td>
                        
                            <td>${fieldValue(bean:configurationInstance, field:'sensitivity')}</td>
                        
                            <td>${fieldValue(bean:configurationInstance, field:'autoFetching')}</td>
                        
                            <td><span class="classCutter">${fieldValue(bean:configurationInstance, field:'clusteringAlgorithmClassName')}</span></td>

                            <td><span class="classCutter">${fieldValue(bean:configurationInstance, field:'searchEngineClassName')}</span></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${configurationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
