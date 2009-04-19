

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="scaffolding" />
        <title>Show Configuration</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Configuration List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Configuration</g:link></span>
        </div>
        <div class="body">
            <h1>Show Configuration</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Name:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'name')}</td>
                            
                        </tr>
                                        
                        <tr class="prop">
                            <td valign="top" class="name">Max. Number of Clusters:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'numberOfClusters')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Initial Result Load for building Clusters:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'clusteringBase')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Sensitivity:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'sensitivity')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Minimal Distance between Clusters:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'minimalClusterDistance')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Weight of Result Titles in Cluster Generation:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'resultTitleWeight')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Weight of Result Summary in Cluster Generation:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'resultSummaryWeight')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Weight of Result URL in Cluster Generation:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'resultUrlWeight')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Auto Fetching:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'autoFetching')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Initially Expanded Results (-1 for all):</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'initiallyExpandedResults')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Results fetched per Subsequent Fetch:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'blockSize')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Clustering Algorithm Class:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'clusteringAlgorithmClassName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Preparation Algorithm Class:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'preparationAlgorithmClassName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Topic Generator Algorithm Class:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'topicGeneratorAlgorithmClassName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Distance Algorithm Class:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'distanceAlgorithmClassName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Search Engine Class:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:configurationInstance, field:'searchEngineClassName')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Topic Colors:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
								<div id="selected" class="colorList">
									<g:each in="${configurationInstance?.topicColors}" var="t"><g:if test="${t}">
										<div class="colorPicker static" style="background:#${t?.color}" title="Color: ${t?.id}"></div>
									</g:if></g:each>
								</div>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${configurationInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
