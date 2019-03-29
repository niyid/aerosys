<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schedule</title>
</head>
<body>
	<s:url var="scheduleListUrl" action="listSchedule" namespace="/operator/ajax">
		<s:param name="crewMemberId" value="%{crewMemberId}" />
	</s:url>
	<sj:div id="scheduleListDiv" href="%{scheduleListUrl}" />

</body>
</html>