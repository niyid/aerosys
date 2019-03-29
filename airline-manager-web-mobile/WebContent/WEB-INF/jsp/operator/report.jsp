<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Report</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<s:url var="taxRecordUrl" action="taxRecord" namespace="/ajax" />
				<sj:div id="taxRecordDiv" href="%{taxRecordUrl}" />
			</td>
			<td>
				<s:url var="taxListUrl" action="taxList" namespace="/ajax" />
				<sj:div id="taxListDiv" href="%{taxListUrl}" reloadTopics="reloadTaxListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshTaxListDiv', function(event,data) {
        $('#taxListDiv').publish('reloadTaxListDiv');
	});
</script>	
</body>
</html>