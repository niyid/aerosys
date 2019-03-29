<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Authority <s:if test="organization != null">[<s:property value="%{organization.organizationName}" />]</s:if> Employees</div>
		<div class="panel-body" id="authorityEmployeeListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="30%">Name</td>
						<td width="15%">Mail</td>
						<td width="20%">Designation</td>
						<td width="20%">Department</td>
						<td width="10%">Status</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="employees">
					<tr>
						<td><s:property value="fullName" /></td>
						<td><s:property value="mail" /></td>
						<td><s:property value="designation.designationName" /></td>
						<td><s:property value="department" /></td>
						<td><s:property value="status" /></td>
						<td align="right">
							<s:url var="selUrl" action="authorityEmployeeRecord!select" namespace="/setup/ajax" escapeAmp="false">
								<s:param name="personnelId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="authorityEmployeeFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>
