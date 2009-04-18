

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="scaffolding" />
        <title>Edit Configuration</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Configuration List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Configuration</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Configuration</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${configurationInstance}">
            <div class="errors">
                <g:renderErrors bean="${configurationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${configurationInstance?.id}" />
                <input type="hidden" name="version" value="${configurationInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:configurationInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numberOfClusters">Max. Number of Clusters:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'numberOfClusters','errors')}">
                                    <input type="text" id="numberOfClusters" name="numberOfClusters" value="${fieldValue(bean:configurationInstance,field:'numberOfClusters')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clusteringBase">Initial Result Load for building Clusters:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'clusteringBase','errors')}">
                                    <input type="text" id="clusteringBase" name="clusteringBase" value="${fieldValue(bean:configurationInstance,field:'clusteringBase')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sensitivity">Sensitivity:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'sensitivity','errors')}">
                                    <input type="text" id="sensitivity" name="sensitivity" value="${fieldValue(bean:configurationInstance,field:'sensitivity')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="minimalClusterDistance">Minimal Distance between Clusters:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'minimalClusterDistance','errors')}">
                                    <input type="text" id="minimalClusterDistance" name="minimalClusterDistance" value="${fieldValue(bean:configurationInstance,field:'minimalClusterDistance')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="resultTitleWeight">Weight of Result Titles in Cluster Generation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'resultTitleWeight','errors')}">
                                    <input type="text" id="resultTitleWeight" name="resultTitleWeight" value="${fieldValue(bean:configurationInstance,field:'resultTitleWeight')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="resultSummaryWeight">Weight of Result Summary in Cluster Generation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'resultSummaryWeight','errors')}">
                                    <input type="text" id="resultSummaryWeight" name="resultSummaryWeight" value="${fieldValue(bean:configurationInstance,field:'resultSummaryWeight')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="resultUrlWeight">Weight of Result URL in Cluster Generation:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'resultUrlWeight','errors')}">
                                    <input type="text" id="resultUrlWeight" name="resultUrlWeight" value="${fieldValue(bean:configurationInstance,field:'resultUrlWeight')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="autoFetching">Auto Fetching:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'autoFetching','errors')}">
                                    <g:checkBox name="autoFetching" value="${configurationInstance?.autoFetching}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="initiallyExpandedResults">Initially Expanded Results (-1 for all):</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'initiallyExpandedResults','errors')}">
                                    <input type="text" id="initiallyExpandedResults" name="initiallyExpandedResults" value="${fieldValue(bean:configurationInstance,field:'initiallyExpandedResults')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="blockSize">Results fetched per Subsequent Fetch:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'blockSize','errors')}">
                                    <input type="text" id="blockSize" name="blockSize" value="${fieldValue(bean:configurationInstance,field:'blockSize')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="clusteringAlgorithmClassName">Clustering Algorithm Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'clusteringAlgorithmClassName','errors')}">
                                    <input type="text" id="clusteringAlgorithmClassName" name="clusteringAlgorithmClassName" value="${fieldValue(bean:configurationInstance,field:'clusteringAlgorithmClassName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="preparationAlgorithmClassName">Preparation Algorithm Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'preparationAlgorithmClassName','errors')}">
                                    <input type="text" id="preparationAlgorithmClassName" name="preparationAlgorithmClassName" value="${fieldValue(bean:configurationInstance,field:'preparationAlgorithmClassName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="topicGeneratorAlgorithmClassName">Topic Generator Algorithm Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'topicGeneratorAlgorithmClassName','errors')}">
                                    <input type="text" id="topicGeneratorAlgorithmClassName" name="topicGeneratorAlgorithmClassName" value="${fieldValue(bean:configurationInstance,field:'topicGeneratorAlgorithmClassName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distanceAlgorithmClassName">Distance Algorithm Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'distanceAlgorithmClassName','errors')}">
                                    <input type="text" id="distanceAlgorithmClassName" name="distanceAlgorithmClassName" value="${fieldValue(bean:configurationInstance,field:'distanceAlgorithmClassName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="searchEngineClassName">Search Engine Class:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'searchEngineClassName','errors')}">
                                    <input type="text" id="searchEngineClassName" name="searchEngineClassName" value="${fieldValue(bean:configurationInstance,field:'searchEngineClassName')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="topicColors">Topic Colors:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:configurationInstance,field:'topicColors','errors')}">
									<g:render template="colorChooser" model="[selectedColors: configurationInstance.topicColors]" />
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
