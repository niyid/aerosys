<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Agency</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="agencyFormUrl" action="agencyRecord" namespace="/setup/ajax" />
				<sj:div id="agencyFormDiv" href="%{agencyFormUrl}" />
			</td>
			<td>
				<s:url var="agencyListUrl" action="agencyList" namespace="/setup/ajax" />
				<sj:div id="agencyListDiv" href="%{agencyListUrl}" reloadTopics="reloadAgencyListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="agencyDialog" autoOpen="false" modal="true" title="Agency Details" width="600" />
	<sj:dialog id="agencyEmployeeDialog" autoOpen="false" modal="true" title="Agency Employees" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
	<script type="text/javascript" >
		$.subscribe('refreshAgencyListDiv', function(event,data) {
	        $('#agencyListDiv').publish('reloadAgencyListDiv');
		});
	</script>	
</body>
</html>