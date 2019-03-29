<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Report</title>
</head>
<body>
	<s:url var="reportListUrl" action="reportResult" namespace="/operator/ajax" />
	<sj:div id="reportListDiv" href="%{reportListUrl}" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
</body>
</html>