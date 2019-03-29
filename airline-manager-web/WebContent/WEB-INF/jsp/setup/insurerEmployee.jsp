<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Insurer Employee</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="insurerEmployeeFormUrl" action="insurerEmployeeRecord" namespace="/setup/ajax" />
				<sj:div id="insurerEmployeeFormDiv" href="%{insurerEmployeeFormUrl}" />
			</td>
			<td>
				<s:url var="insurerEmployeeListUrl" action="insurerEmployeeList" namespace="/setup/ajax" />
				<sj:div id="insurerEmployeeListDiv" href="%{insurerEmployeeListUrl}" reloadTopics="reloadInsurerEmployeeListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="insurerEmployeeDialog" autoOpen="false" modal="true" title="Insurer Employees" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshInsurerEmployeeListDiv', function(event,data) {
	        $('#insurerEmployeeListDiv').publish('reloadInsurerEmployeeListDiv');
		});
	</script>	
</body>
</html>