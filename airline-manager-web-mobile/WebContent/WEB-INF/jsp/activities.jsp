<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Activities</title>
</head>
<body>

	<s:include value="activitiesTabs.jsp" />

	<h2>Press any of "Member Events", "Offer Events" and "Item Events" buttons to view the events in each category.</h2>
	
			<s:if test="#session.lon != null && #session.lat != null">
				<s:url var="topItemListUrl" action="showTopItemList" namespace="/" />
				<sjm:div id="topItemListDiv" href="%{topItemListUrl}" />
			</s:if>
</body>
</html>