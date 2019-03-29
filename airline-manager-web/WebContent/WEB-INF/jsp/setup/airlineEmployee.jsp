<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Airline Employee</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="airlineEmployeeFormUrl" action="airlineEmployeeRecord" namespace="/setup/ajax" />
				<sj:div id="airlineEmployeeFormDiv" href="%{airlineEmployeeFormUrl}" />
			</td>
			<td>
				<s:url var="airlineEmployeeListUrl" action="airlineEmployeeList" namespace="/setup/ajax" />
				<sj:div id="airlineEmployeeListDiv" href="%{airlineEmployeeListUrl}" reloadTopics="reloadAirlineEmployeeListDiv" />
			</td>
		</tr>
	</table>
	
	<script type="text/javascript" >
		$.subscribe('refreshAirlineEmployeeListDiv', function(event,data) {
	        $('#airlineEmployeeListDiv').publish('reloadAirlineEmployeeListDiv');
		});
	</script>
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>