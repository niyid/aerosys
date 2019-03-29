<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>City</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="cityFormUrl" action="cityRecord" namespace="/setup/ajax" />
				<sj:div id="cityFormDiv" href="%{cityFormUrl}" />
			</td>
			<td>
				<s:url var="cityListUrl" action="cityList" namespace="/setup/ajax" />
				<sj:div id="cityListDiv" href="%{cityListUrl}" reloadTopics="reloadCityListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="cityDialog" autoOpen="false" modal="true" title="City Details" width="600" />
	
	<script type="text/javascript" >
		$.subscribe('refreshCityListDiv', function(event,data) {
	        $('#cityListDiv').publish('reloadCityListDiv');
		});
	</script>	
</body>
</html>