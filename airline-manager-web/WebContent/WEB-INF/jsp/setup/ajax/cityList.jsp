<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Cities</div>
		<div class="panel-body" id="cityListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="35%">Name</td>
						<td width="30%" align="right">State</td>
						<td width="30%" align="right">Country</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="cities">
					<tr>
						<td><s:property value="cityName" /></td>
						<td align="right"><s:property value="countryState.stateName" /></td>
						<td align="right"><s:property value="countryState.country.countryName" /></td>
						<td align="right">
							<s:url var="selUrl" action="cityRecord!select" namespace="/setup/ajax">
								<s:param name="cityId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="cityFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>