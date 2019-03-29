<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Customers</div>
		<div class="panel-body" id="customerListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="30%">Name</td>
						<td width="15%" align="right">Type</td>
						<td width="15%" align="right">Cycle</td>
						<td width="20%" align="right">Phone</td>
						<td width="15" align="right">Email</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="customers">
					<tr>
						<td><s:property value="organizationName" /></td>
						<td><s:property value="customerType" /></td>
						<td><s:property value="cycleRange" /></td>
						<td align="right"><s:property value="contactInfo.primaryPhone" /></td>
						<td align="right"><s:property value="contactInfo.primaryEmail" /></td>
						<td align="right">
							<s:url var="selUrl" action="customerRecord!select" namespace="/setup/ajax">
								<s:param name="customerId" value="%{id}" />
							</s:url>
							
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="customerFormDiv">
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