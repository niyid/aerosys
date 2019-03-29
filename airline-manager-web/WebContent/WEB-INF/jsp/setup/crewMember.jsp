<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Crew Member</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="crewMemberFormUrl" action="crewMemberRecord" namespace="/setup/ajax" />
				<sj:div id="crewMemberFormDiv" href="%{crewMemberFormUrl}" />
			</td>
			<td>
				<s:url var="crewMemberListUrl" action="crewMemberList" namespace="/setup/ajax" />
				<sj:div id="crewMemberListDiv" href="%{crewMemberListUrl}" reloadTopics="reloadCrewMemberListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="crewMemberDialog" autoOpen="false" modal="true" title="Crew Member Details" width="600" />
	<sj:dialog id="scheduleDialog" autoOpen="false" modal="true" title="Activity Schedule" width="800" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshCrewMemberListDiv', function(event,data) {
	        $('#crewMemberListDiv').publish('reloadCrewMemberListDiv');
		});
	</script>	
</body>
</html>