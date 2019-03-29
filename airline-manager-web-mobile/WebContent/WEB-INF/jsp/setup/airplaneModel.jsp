<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airplane Model</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airplaneModelFormUrl" action="airplaneModelRecord" namespace="/setup/ajax" />
				<sj:div id="airplaneModelFormDiv" href="%{airplaneModelFormUrl}" />
			</td>
			<td>
				<s:url var="airplaneModelListUrl" action="airplaneModelList" namespace="/setup/ajax" />
				<sj:div id="airplaneModelListDiv" href="%{airplaneModelListUrl}" reloadTopics="reloadAirplaneModelListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="airplaneModelDialog" autoOpen="false" modal="true" title="Airplane Model Details" width="600" />
	
	<script type="text/javascript" >
		$.subscribe('refreshAirplaneModelListDiv', function(event,data) {
	        $('#airplaneModelListDiv').publish('reloadAirplaneModelListDiv');
		});
	</script>	
</body>
</html>