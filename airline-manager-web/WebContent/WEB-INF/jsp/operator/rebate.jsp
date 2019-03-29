<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Rebate</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="rebateFormUrl" action="rebateRecord" namespace="/operator/ajax" />
				<sj:div id="rebateFormDiv" href="%{rebateFormUrl}" />
			</td>
			<td>
				<s:url var="rebateListUrl" action="rebateList" namespace="/operator/ajax" />
				<sj:div id="rebateListDiv" href="%{rebateListUrl}" reloadTopics="reloadRebateListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshRebateListDiv', function(event,data) {
        $('#rebateListDiv').publish('reloadRebateListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>