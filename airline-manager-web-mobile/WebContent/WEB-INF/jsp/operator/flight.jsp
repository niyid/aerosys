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
				<s:url var="flightListUrl" action="flightList" namespace="/operator/ajax" />
				<sj:div id="flightListDiv" href="%{flightListUrl}" reloadTopics="reloadFlightListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshFlightListDiv', function(event,data) {
        $('#flightListDiv').publish('reloadFlightListDiv');
	});
</script>	
</body>
</html>