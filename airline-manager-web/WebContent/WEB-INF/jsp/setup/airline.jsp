<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airline</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airlineFormUrl" action="airlineRecord" namespace="/setup/ajax" />
				<sj:div id="airlineFormDiv" href="%{airlineFormUrl}" />
			</td>
			<td>
				<s:url var="airlineListUrl" action="airlineList" namespace="/setup/ajax" />
				<sj:div id="airlineListDiv" href="%{airlineListUrl}" reloadTopics="reloadAirlineListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="airlineEmployeeDialog" autoOpen="false" modal="true" title="Airline Employees" width="1200" />
	<sj:dialog id="airlineCrewMemberDialog" autoOpen="false" modal="true" title="Crew Members" width="1200" />
	<sj:dialog id="activityDialog" autoOpen="false" modal="true" title="Crew Activities" width="1200" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
	<script type="text/javascript" >
		$.subscribe('refreshAirlineListDiv', function(event,data) {
	        $('#airlineListDiv').publish('reloadAirlineListDiv');
		});
	</script>	
</body>
</html>