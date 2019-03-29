<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Customer</title>
</head>
<body>

	<table>
		<tr>
			<td width="50%">
				<s:url var="customerFormUrl" action="customerRecord" namespace="/setup/ajax" />
				<sj:div id="customerFormDiv" href="%{customerFormUrl}" />
			</td>
			<td>
				<s:url var="customerListUrl" action="customerList" namespace="/setup/ajax" />
				<sj:div id="customerListDiv" href="%{customerListUrl}" reloadTopics="reloadCustomerListDiv" />
			</td>
		</tr>
	</table>
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<sj:dialog id="priceListDialog" autoOpen="false" modal="true" title="Price List" width="1000" />	
	<script type="text/javascript" >
		$.subscribe('refreshCustomerListDiv', function(event,data) {
	        $('#customerListDiv').publish('reloadCustomerListDiv');
		});
		$.subscribe('refreshCustomerRecordDiv', function(event,data) {
	        $('#routePriceListDiv').publish('reloadCustomerRecordDiv');
		});
	</script>	
</body>
</html>