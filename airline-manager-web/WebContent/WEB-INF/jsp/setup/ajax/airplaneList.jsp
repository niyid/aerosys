<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Aircrafts</div>
		<div class="panel-body" id="airplaneListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="25%">Registration</td>
						<td width="25%" align="right">Make</td>
						<td width="25%" align="right">Model</td>
						<td width="20%" align="right">Manufactured</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="airplanes">
					<tr>
						<td><s:property value="regNumber" /></td>
						<td align="right"><s:property value="model.airplaneMake.makeName" /></td>
						<td align="right"><s:property value="model.modelName" /></td>
						<td align="right"><s:date name="manufactureDate" format="dd/MM/yyyy"/></td>
						<td align="right">
							<s:url var="selUrl" action="airplaneRecord!select" namespace="/setup/ajax">
								<s:param name="regNumber" value="%{regNumber}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="airplaneFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff">
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