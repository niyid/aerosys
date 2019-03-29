<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Activities</div>
		<div class="panel-body" id="activityListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%">Type</td>
						<td width="15%" align="right">Start</td>
						<td width="15%" align="right">End</td>
						<td width="60%" align="right">Description</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="activities">
					<tr>
						<td><s:property value="activityType" /></td>
						<td align="right"><s:date name="startTime" format="dd/mm/yyyy hh:mm a" /></td>
						<td align="right"><s:date name="endTime" format="dd/mm/yyyy hh:mm a" /></td>
						<td><s:property value="description" /></td>
						<td align="right">
							<s:url var="selUrl" action="activityRecord!select" namespace="/setup/ajax">
								<s:param name="activityId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="activityFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>