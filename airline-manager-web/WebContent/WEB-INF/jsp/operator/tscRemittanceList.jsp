<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>PSC Remittance List</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<s:url var="remittanceListUrl" action="remittanceList" namespace="/operator/ajax" />
				<sj:div id="remittanceListDiv" href="%{remittanceListUrl}" />
			</td>
		</tr>
	</table>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>