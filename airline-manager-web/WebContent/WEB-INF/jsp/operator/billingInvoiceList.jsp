<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Invoices</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<s:url var="invoiceListUrl" action="invoiceList" namespace="/operator/ajax" />
				<sj:div id="invoiceListDiv" href="%{invoiceListUrl}" />
			</td>
		</tr>
	</table>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>