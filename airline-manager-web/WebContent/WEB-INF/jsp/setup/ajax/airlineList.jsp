<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Airlines</div>
		<div class="panel-body" id="airlineListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="35%">Name</td>
						<td width="10%" align="right">Code</td>
						<td width="25%" align="right">Phone</td>
						<td width="25" align="right">Email</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="airlines">
					<tr>
						<td><s:property value="organizationName" /></td>
						<td align="right"><s:property value="airlineCode" /></td>
						<td align="right"><s:property value="contactInfo.primaryPhone" /></td>
						<td align="right"><s:property value="contactInfo.primaryEmail" /></td>
						<td align="right">
							<s:url var="selUrl" action="airlineRecord!select" namespace="/setup/ajax">
								<s:param name="airlineId" value="%{id}" />
							</s:url>
							
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="airlineFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff">
								<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">		
									View
								</security:authorize>
								<security:authorize access="hasAnyRole('ROLE_ADMIN')">		
									Edit
								</security:authorize>
							</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>