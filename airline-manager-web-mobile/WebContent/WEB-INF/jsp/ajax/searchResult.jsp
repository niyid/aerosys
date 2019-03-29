<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>Search Result</title>
	</head>
	<body>
		<h1>First Flights</h1>
		<s:if test="flightResult == null || flightResult.isEmpty()">
			<h2>No matching flights found.</h2>
		</s:if>
		
		<s:url var="selUrl" action="popupFlightDetails" namespace="/" escapeAmp="false">
			<s:param name="flightId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="flightListView"
		    inset="true"
		    list="flightResult"
		    listKey="top.id"
		    listValue="top.id.airlineCode + ' ' + top.id.flightNumber + ' - ' + top.departureAirport.airportCode + ' [' + getText('format.date-time', {top.departureTime}) + '] to ' + top.arrivalAirport.airportCode + ' [' + getText('format.date-time', {top.arrivalTime}) + ']'"
		    listHref="%{selUrl}"
		    listParam="flightId"
		/>	
		
		<h1>Return Flights</h1>
		<s:if test="flightResult2 == null || flightResult2.isEmpty()">
			<h2>No matching flights found.</h2>
		</s:if>
		
		<s:url var="selUrl2" action="popupFlightDetails" namespace="/" escapeAmp="false">
			<s:param name="flightId2" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="flightListView2"
		    inset="true"
		    list="flightResult2"
		    listKey="top.id"
		    listValue="top.id.airlineCode + ' ' + top.id.flightNumber + ' - ' + top.departureAirport.airportCode + ' [' + getText('format.date-time', {top.departureTime}) + '] to ' + top.arrivalAirport.airportCode + ' [' + getText('format.date-time', {top.arrivalTime}) + ']'"
		    listHref="%{selUrl2}"
		    listParam="flightId2"
		/>	
	</body>
</html>