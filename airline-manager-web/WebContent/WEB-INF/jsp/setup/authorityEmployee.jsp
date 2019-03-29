<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Authority Employee</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="authorityEmployeeFormUrl" action="authorityEmployeeRecord" namespace="/setup/ajax" />
				<sj:div id="authorityEmployeeFormDiv" href="%{authorityEmployeeFormUrl}" />
			</td>
			<td>
				<s:url var="authorityEmployeeListUrl" action="authorityEmployeeList" namespace="/setup/ajax" />
				<sj:div id="authorityEmployeeListDiv" href="%{authorityEmployeeListUrl}" reloadTopics="reloadAuthorityEmployeeListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="authorityEmployeeDialog" autoOpen="false" modal="true" title="Authority Employee Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshAuthorityEmployeeListDiv', function(event,data) {
	        $('#authorityEmployeeListDiv').publish('reloadAuthorityEmployeeListDiv');
		});
	</script>	
</body>
</html>