<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Airplane Make</div>
		<div class="panel-body" id="airplaneMakeListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="25%">Make</td>
						<td width="70%">Description</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="airplaneMakes">
					<tr>
						<td><s:property value="makeName" /></td>
						<td><s:property value="description" /></td>
						<td align="right">
							<s:url var="selUrl" action="airplaneMakeRecord!select" namespace="/setup/ajax">
								<s:param name="makeName" value="%{makeName}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="airplaneMakeFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>