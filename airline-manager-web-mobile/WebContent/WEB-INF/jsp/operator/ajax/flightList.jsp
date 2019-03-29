<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>Flights</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="flightRecord!initNew" namespace="/operator/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Flight</sjm:a>
	
		<s:url var="selUrl" action="flightRecord!select" namespace="/operator/ajax" escapeAmp="false">
			<s:param name="flightId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="flightListView"
		    inset="true"
		    list="flights"
		    listKey="top.id"
		    listValue="top.id.airlineCode + ' ' + top.id.flightNumber + ' - ' + top.departureAirport.airportCode + ' [' + getText('format.date-time', {top.departureTime}) + '] to ' + top.arrivalAirport.airportCode + ' [' + getText('format.date-time', {top.arrivalTime}) + ']'"
		    listHref="%{selUrl}"
		    listParam="flightId"
		/>	
	</body>
</html>