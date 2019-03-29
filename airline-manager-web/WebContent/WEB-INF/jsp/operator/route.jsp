<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Route</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="routeFormUrl" action="routeRecord" namespace="/operator/ajax" />
				<sj:div id="routeFormDiv" href="%{routeFormUrl}" />
			</td>
			<td>
				<s:url var="routeListUrl" action="routeList" namespace="/operator/ajax" />
				<sj:div id="routeListDiv" href="%{routeListUrl}" reloadTopics="reloadRouteListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript">
	$.subscribe('refreshRouteListDiv', function(event,data) {
        $('#routeListDiv').publish('reloadRouteListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>