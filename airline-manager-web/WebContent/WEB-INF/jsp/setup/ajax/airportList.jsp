<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Airports</div>
		<div class="panel-body" id="airportListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="40%">Name</td>
						<td width="10%" align="right">Code</td>
						<td width="45%" align="right">City, State, Country</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="airports">
					<tr>
						<td><s:property value="airportName" /></td>
						<td align="right"><s:property value="airportCode" /></td>
						<td align="right"><s:property value="city.cityName" />, <s:property value="city.countryState.stateName" />, <s:property value="city.countryState.country.countryName" /></td>
						<td align="right">
							<s:url var="selUrl" action="airportRecord!select" namespace="/setup/ajax">
								<s:param name="airportCode" value="%{airportCode}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" targets="airportFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>