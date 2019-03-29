<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Airplane Model</div>
		<div class="panel-body" id="airplaneModelListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="25%">Make</td>
						<td width="25%">Model</td>
						<td width="45%">Description</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="airplaneModels">
					<tr>
						<td><s:property value="airplaneMake.makeName" /></td>
						<td><s:property value="modelName" /></td>
						<td><s:property value="description" /></td>
						<td align="right">
							<s:url var="selUrl" action="airplaneModelRecord!select" namespace="/setup/ajax" escapeAmp="false">
								<s:param name="modelName" value="%{modelName}" />
								<s:param name="makeName" value="%{airplaneMake.makeName}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="airplaneModelFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>