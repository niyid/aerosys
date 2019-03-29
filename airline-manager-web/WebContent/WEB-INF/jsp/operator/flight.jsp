<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Flight</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="flightFormUrl" action="flightRecord" namespace="/operator/ajax" />
				<sj:div id="flightFormDiv" href="%{flightFormUrl}" />
			</td>
			<td>
				<s:url var="flightListUrl" action="flightList" namespace="/operator/ajax">
				</s:url>
				<sj:div id="flightListDiv" href="%{flightListUrl}" reloadTopics="reloadFlightListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="crewDialog" autoOpen="false" modal="true" title="Flight Crew" width="800" />
	<sj:dialog id="activityDialog" autoOpen="false" modal="true" title="Crew Activities" width="800" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
<script type="text/javascript" >
	$.subscribe('refreshFlightListDiv', function(event,data) {
        $('#flightListDiv').publish('reloadFlightListDiv');
	});
</script>	
</body>
</html>