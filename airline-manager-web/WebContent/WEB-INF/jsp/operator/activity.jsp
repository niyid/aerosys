<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Activity</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="activityFormUrl" action="activityRecord" namespace="/operator/ajax" />
				<sj:div id="activityFormDiv" href="%{activityFormUrl}" />
			</td>
			<td>
				<s:url var="activityListUrl" action="activityList" namespace="/operator/ajax" />
				<sj:div id="activityListDiv" href="%{activityListUrl}" reloadTopics="reloadActivityListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshActivityListDiv', function(event,data) {
        $('#activityListDiv').publish('reloadActivityListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>