<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airplane</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airplaneFormUrl" action="airplaneRecord" namespace="/setup/ajax" />
				<sj:div id="airplaneFormDiv" href="%{airplaneFormUrl}" />
			</td>
			<td>
				<s:url var="airplaneListUrl" action="airplaneList" namespace="/setup/ajax" />
				<sj:div id="airplaneListDiv" href="%{airplaneListUrl}" reloadTopics="reloadAirplaneListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="airplaneDialog" autoOpen="false" modal="true" title="Airplane Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshAirplaneListDiv', function(event,data) {
	        $('#airplaneListDiv').publish('reloadAirplaneListDiv');
		});
	</script>	
</body>
</html>