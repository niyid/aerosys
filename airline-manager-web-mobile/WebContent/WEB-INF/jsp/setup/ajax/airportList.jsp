<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Airports</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="airportRecord!initNew" namespace="/setup/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Airport</sjm:a>
	
		<s:url var="selUrl" action="airportRecord!select" namespace="/setup/ajax" escapeAmp="false">
			<s:param name="airportCode" value="%{airportCode}" />
		</s:url>
	
		<sjm:list
		    id="airportListView"
		    inset="true"
		    list="airports"
		    listKey="top.airportCode"
		    listValue="top.airportName + ' [' + top.airportCode + '] ' + top.city.cityName + ', ' + top.city.countryState.stateName + ', ' + top.city.countryState.country.countryName"
		    listHref="%{selUrl}"
		    listParam="airportCode"
		/>	
	</body>
</html>