<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Authorities</div>
		<div class="panel-body" id="authorityListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="35%">Name</td>
						<td width="10%" align="right">Acronym</td>
						<td width="25%" align="right">Phone</td>
						<td width="25" align="right">Email</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="authorities">
					<tr>
						<td><s:property value="organizationName" /></td>
						<td align="right"><s:property value="authorityAcronym" /></td>
						<td align="right"><s:property value="contactInfo.primaryPhone" /></td>
						<td align="right"><s:property value="contactInfo.primaryEmail" /></td>
						<td align="right">
							<s:url var="selUrl" action="authorityRecord!select" namespace="/setup/ajax">
								<s:param name="authorityId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="authorityFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>