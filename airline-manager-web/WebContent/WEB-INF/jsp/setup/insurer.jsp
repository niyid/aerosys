<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Insurer</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="insurerFormUrl" action="insurerRecord" namespace="/setup/ajax" />
				<sj:div id="insurerFormDiv" href="%{insurerFormUrl}" />
			</td>
			<td>
				<s:url var="insurerListUrl" action="insurerList" namespace="/setup/ajax" />
				<sj:div id="insurerListDiv" href="%{insurerListUrl}" reloadTopics="reloadInsurerListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="insurerDialog" autoOpen="false" modal="true" title="Insurer Details" width="600" />
	<sj:dialog id="employeeDialog" autoOpen="false" modal="true" title="Insurer Employees" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
	<script type="text/javascript" >
		$.subscribe('refreshInsurerListDiv', function(event,data) {
	        $('#insurerListDiv').publish('reloadInsurerListDiv');
		});
	</script>	
</body>
</html>