<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airplane Make</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airplaneMakeFormUrl" action="airplaneMakeRecord" namespace="/setup/ajax" />
				<sj:div id="airplaneMakeFormDiv" href="%{airplaneMakeFormUrl}" />
			</td>
			<td>
				<s:url var="airplaneMakeListUrl" action="airplaneMakeList" namespace="/setup/ajax" />
				<sj:div id="airplaneMakeListDiv" href="%{airplaneMakeListUrl}" reloadTopics="reloadAirplaneMakeListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="airplaneMakeDialog" autoOpen="false" modal="true" title="Airplane Make Details" width="600" />
	
	<script type="text/javascript" >
		$.subscribe('refreshAirplaneMakeListDiv', function(event,data) {
	        $('#airplaneMakeListDiv').publish('reloadAirplaneMakeListDiv');
		});
	</script>	
</body>
</html>