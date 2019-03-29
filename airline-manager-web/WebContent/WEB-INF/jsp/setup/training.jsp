<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Training</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="trainingFormUrl" action="trainingRecord" namespace="/setup/ajax" />
				<sj:div id="trainingFormDiv" href="%{trainingFormUrl}" />
			</td>
			<td>
				<s:url var="trainingListUrl" action="trainingList" namespace="/setup/ajax" />
				<sj:div id="trainingListDiv" href="%{trainingListUrl}" reloadTopics="reloadTrainingListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="trainingDialog" autoOpen="false" modal="true" title="Training Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshTrainingListDiv', function(event,data) {
	        $('#trainingListDiv').publish('reloadTrainingListDiv');
		});
	</script>	
</body>
</html>