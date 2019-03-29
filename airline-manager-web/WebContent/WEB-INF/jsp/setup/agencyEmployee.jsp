<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Agency Employee</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="agencyEmployeeFormUrl" action="agencyEmployeeRecord" namespace="/setup/ajax" />
				<sj:div id="agencyEmployeeFormDiv" href="%{agencyEmployeeFormUrl}" />
			</td>
			<td>
				<s:url var="agencyEmployeeListUrl" action="agencyEmployeeList" namespace="/setup/ajax" />
				<sj:div id="agencyEmployeeListDiv" href="%{agencyEmployeeListUrl}" reloadTopics="reloadAgencyEmployeeListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="agencyEmployeeDialog" autoOpen="false" modal="true" title="Agency Employee Details" width="600" />
	
	<script type="text/javascript" >
		$.subscribe('refreshAgencyEmployeeListDiv', function(event,data) {
	        $('#agencyEmployeeListDiv').publish('reloadAgencyEmployeeListDiv');
		});
	</script>	
</body>
</html>