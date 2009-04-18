<table>
	<tr>
		<td>
			<h5>Your currently chosen colors:</h5>
			<div id="selected" class="colorList">
				<g:each in="${selectedColors}" var="t"><g:if test="${t}">
					<div id="item_${t.id}" class="colorPicker" style="background:#${t.color}" title="Color: ${t.id}"></div>
				</g:if></g:each>
			</ul>
		</td><td>
			<h5>Other available colors:</h5>
			<div id="all" class="colorList">
				<g:each in="${TopicColor.list()}" var="t"><g:if test="${t}">
					<g:if test="${!selectedColors.contains(t)}">
						<div id="item_${t.id}" class="colorPicker" style="background:#${t.color}" title="Color: ${t.id}"></div>
					</g:if>
				</g:if></g:each>
			</div>
			<div style="display:none;"><div id="topicColors">
				<g:each in="${selectedColors}" var="t"><g:if test="${t}">
					<li>${t.id}</li>
				</g:if></g:each>
			</div></div>
			<script type="text/javascript">
			// <![CDATA[
				var lists = $$(".colorList");
				Sortable.create("all", {tag:'div',containment: lists, constraint: false, dropOnEmpty:true});
				Sortable.create("selected", {tag:'div',containment: lists, constraint: false, dropOnEmpty:true, onChange:function(){
					$("topicColors").update(Sortable.serialize($("selected")).replace(/selected\[\]=/g,"<input type='hidden' name='topicColors' value='").replace(/\&/g,"' />")+"' />");
				}});
			// ]]>
			</script>			
		</td>
	</tr>
</table>