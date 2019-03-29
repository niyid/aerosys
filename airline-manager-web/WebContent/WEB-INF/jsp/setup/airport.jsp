<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airport</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airportFormUrl" action="airportRecord" namespace="/setup/ajax" />
				<sj:div id="airportFormDiv" href="%{airportFormUrl}" />
			</td>
			<td>
				<s:url var="airportListUrl" action="airportList" namespace="/setup/ajax" />
				<sj:div id="airportListDiv" href="%{airportListUrl}" reloadTopics="reloadAirportListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="airportDialog" autoOpen="false" modal="true" title="Airport Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshAirportListDiv', function(event,data) {
	        $('#airportListDiv').publish('reloadAirportListDiv');
		});
	</script>	
</body>
</html>