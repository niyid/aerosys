<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Price-list</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<s:url var="routePriceRecordUrl" action="routePriceRecord" namespace="/operator/ajax" />
				<sj:div id="routePriceRecordDiv" href="%{routePriceRecordUrl}" />
			</td>
			<td>
				<s:url var="routePriceListUrl" action="routePriceList" namespace="/operator/ajax" />
				<sj:div id="routePriceListDiv" href="%{routePriceListUrl}" />
			</td>
		</tr>
	</table>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>