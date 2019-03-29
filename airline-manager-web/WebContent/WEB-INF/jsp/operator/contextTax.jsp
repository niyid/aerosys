<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Custom Tax</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="contextTaxRecordUrl" action="contextTaxRecord" namespace="/operator/ajax" />
				<sj:div id="contextTaxFormDiv" href="%{contextTaxRecordUrl}" />
			</td>
			<td>
				<s:url var="contextTaxListUrl" action="contextTaxList" namespace="/operator/ajax" />
				<sj:div id="contextTaxListDiv" href="%{contextTaxListUrl}" reloadTopics="reloadContextTaxListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshContextTaxListDiv', function(event,data) {
        $('#contextTaxListDiv').publish('reloadContextTaxListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>