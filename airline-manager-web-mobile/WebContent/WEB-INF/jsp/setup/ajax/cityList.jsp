<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Cities</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="airportRecord!initNew" namespace="/setup/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New City</sjm:a>
	
		<s:url var="selUrl" action="cityRecord!select" namespace="/setup/ajax" escapeAmp="false">
			<s:param name="cityId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="airportListView"
		    inset="true"
		    list="cities"
		    listKey="top.id"
		    listValue="top.cityName + ', ' + top.countryState.stateName + ', ' + top.countryState.country.countryName"
		    listHref="%{selUrl}"
		    listParam="cityId"
		/>	
	</body>
</html>