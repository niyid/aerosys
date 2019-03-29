<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Cash Deposit</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="cashDepositFormUrl" action="cashDepositRecord" namespace="/operator/ajax" />
				<sj:div id="cashDepositFormDiv" href="%{cashDepositFormUrl}" />
			</td>
			<td>
				<s:url var="cashDepositListUrl" action="cashDepositList" namespace="/operator/ajax" />
				<sj:div id="cashDepositListDiv" href="%{cashDepositListUrl}" reloadTopics="reloadCashDepositListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript">
	$.subscribe('refreshCashDepositListDiv', function(event,data) {
        $('#cashDepositListDiv').publish('reloadCashDepositListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>