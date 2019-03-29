<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Trainer</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="trainerFormUrl" action="trainerRecord" namespace="/setup/ajax" />
				<sj:div id="trainerFormDiv" href="%{trainerFormUrl}" />
			</td>
			<td>
				<s:url var="trainerListUrl" action="trainerList" namespace="/setup/ajax" />
				<sj:div id="trainerListDiv" href="%{trainerListUrl}" reloadTopics="reloadTrainerListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="trainerDialog" autoOpen="false" modal="true" title="Trainer Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshTrainerListDiv', function(event,data) {
	        $('#trainerListDiv').publish('reloadTrainerListDiv');
		});
	</script>	
</body>
</html>