<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Route Price</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="routePriceFormUrl" action="routePriceRecord" namespace="/operator/ajax" />
				<sj:div id="routePriceRecordDiv" href="%{routePriceFormUrl}" />
			</td>
			<td>
				<s:url var="routePriceListUrl" action="routePriceList" namespace="/operator/ajax" />
				<sj:div id="routePriceListDiv" href="%{routePriceListUrl}" reloadTopics="reloadRoutePriceListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript">
	$.subscribe('refreshRoutePriceListDiv', function(event,data) {
        $('#routePriceListDiv').publish('reloadRoutePriceListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>