<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Airline <s:if test="organization != null">[<s:property value="%{organization.organizationName}" />]</s:if> Crew</div>
		<div class="panel-body" id="crewMemberListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="35%">Name</td>
						<td width="20%">Mail</td>
						<td width="20%">Designation</td>
						<td width="10%">Status</td>
						<td width="15%"></td>
					</tr>
				</thead>
				<s:iterator value="crewMembers">
					<tr>
						<td><s:property value="fullName" /></td>
						<td><s:property value="mail" /></td>
						<td><s:property value="crewType" /></td>
						<td><s:property value="status" /></td>
						<td align="right">
							<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
								<s:url var="viewUrl" action="showCrewMemberProfile" namespace="/operator" escapeAmp="false">
									<s:param name="crewMemberId" value="%{id}" />
								</s:url>
								<sj:a cssClass="btn btn-primary btn-lg" href="%{viewUrl}" openDialog="crewMemberDialog">View</sj:a>
							</security:authorize>
							<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')">
								<s:url var="viewUrl" action="showCrewMemberProfile" namespace="/operator" escapeAmp="false">
									<s:param name="crewMemberId" value="%{id}" />
								</s:url>
								<sj:a cssClass="btn btn-primary btn-lg" href="%{viewUrl}" openDialog="crewMemberDialog">View</sj:a>
								<s:url var="selUrl" action="crewMemberRecord!select" namespace="/setup/ajax" escapeAmp="false">
									<s:param name="crewMemberId" value="%{id}" />
								</s:url>
								<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="crewMemberFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
							</security:authorize>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>
