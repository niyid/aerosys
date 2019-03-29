<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Routes</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="routeRecord!initNew" namespace="/setup/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Route</sjm:a>
	
		<s:url var="selUrl" action="routeRecord!select" namespace="/operator/ajax">
			<s:param name="routeId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="routeListView"
		    inset="true"
		    list="routes"
		    listKey="top.id"
		    listValue="top.description"
		    listHref="%{selUrl}"
		    listParam="routeId"
		/>	
	</body>
</html>