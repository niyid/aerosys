<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Insurers</div>
		<div class="panel-body" id="insurerListInnerDiv">
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
				<s:iterator value="insurers">
					<tr>
						<td><s:property value="organizationName" /></td>
						<td align="right"><s:property value="insurerCode" /></td>
						<td align="right"><s:property value="contactInfo.primaryPhone" /></td>
						<td align="right"><s:property value="contactInfo.primaryEmail" /></td>
						<td align="right">
							<s:url var="selUrl" action="insurerRecord!select" namespace="/setup/ajax">
								<s:param name="insurerId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="insurerFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>