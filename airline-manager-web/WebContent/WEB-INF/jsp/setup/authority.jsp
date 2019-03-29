<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Authority</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="authorityFormUrl" action="authorityRecord" namespace="/setup/ajax" />
				<sj:div id="authorityFormDiv" href="%{authorityFormUrl}" />
			</td>
			<td>
				<s:url var="authorityListUrl" action="authorityList" namespace="/setup/ajax" />
				<sj:div id="authorityListDiv" href="%{authorityListUrl}" reloadTopics="reloadAuthorityListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="authorityDialog" autoOpen="false" modal="true" title="Authority Details" width="600" />
	<sj:dialog id="authorityEmployeeDialog" autoOpen="false" modal="true" title="Authority Employees" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
	<script type="text/javascript" >
		$.subscribe('refreshAuthorityListDiv', function(event,data) {
	        $('#authorityListDiv').publish('reloadAuthorityListDiv');
		});
	</script>	
</body>
</html>