<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<title>Search Parameters</title>
</head>
<body>
	<s:form id="searchFilterForm" action="searchFilter!search" namespace="/" theme="xhtml" method="post">
				
		<sjm:checkbox label="Connection flights OK?" name="connections" required="true" />
				
		<sjm:checkbox label="Thru-flights OK?" name="thruFlights" required="true" />
					
		<sjm:checkbox label="Round trip?" name="roundTrip" required="true" />
				
		<sjm:textfield name="flightDate" label="Flight Date" title="Date" required="true" />
					
		<sjm:select 
						name="departureAirportId" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Departure Airport" 
						required="true"
		/>						
					
		<sjm:select 
						name="arrivalAirportId" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Arrival Airport" 
						required="true"
		/>						
					
		<sjm:textfield name="flightDate2" label="Return Date" title="Return Date" required="false" />
	</s:form>
					
	<sjm:a id="searchFilterButton" button="true" buttonIcon="gear" formIds="searchFilterForm">Search</sjm:a>
	
	<s:include value="/common/msg.jsp" />
</body>
</html>
