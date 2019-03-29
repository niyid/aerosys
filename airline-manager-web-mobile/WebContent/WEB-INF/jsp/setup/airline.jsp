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
	
	<sj:dialog id="airlineDialog" autoOpen="false" modal="true" title="Airline Details" width="600" />
		
	<script type="text/javascript" >
		$.subscribe('refreshAirlineListDiv', function(event,data) {
	        $('#airlineListDiv').publish('reloadAirlineListDiv');
		});
	</script>	
</body>
</html>