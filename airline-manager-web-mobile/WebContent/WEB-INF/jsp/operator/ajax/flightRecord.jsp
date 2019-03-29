<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>
			<s:if test="flightId == null">Add</s:if><s:else>Edit</s:else> Flight
		</title>
	</head>
	<body>
				<s:form id="flightRecordForm" action="flightRecord!save" namespace="/operator/ajax" theme="xhtml">
					<sjm:textfield name="entity.id.flightNumber" label="Flight Number" required="true" />

					<sjm:select 
						name="airplaneRegNumber" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airplaneLov"
						listKey="regNumber"
						listValue="model.modelName + ' - ' + regNumber"
						emptyOption="false"
						label="Airplane" 
						required="true"
					/>
					
					<sjm:textfield name="entity.departureTime" label="Departure Time" required="true" />
					<sjm:select 
						name="departureAirportCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Departure Airport" 
						required="true"
					/>						
					
					<sjm:textfield name="entity.arrivalTime" label="Arrival Time" required="true" />
					<sjm:select 
						name="arrivalAirportCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Arrival Airport" 
						required="true"
					/>
					<sjm:select 
						name="currencyCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="currencyLov"
						listKey="currencyCode"
						listValue="currencyName"
						emptyOption="false"
						label="Currency" 
						required="true"
					/>
					
					<sjm:textfield name="entity.airFareEconomy" label="Economy-class Fare" required="true" />
					<sjm:textfield name="entity.airFareBizClass" label="Business-class Fare" required="true" />
					<sjm:textfield name="entity.airFareFirstClass" label="First-class Fare" required="true" />
					
					<sjm:textfield name="entity.estimatedLuggageWeight" label="Estimated Luggage Weight" />
					
					<s:if test="flightNumber != null && flightDate != null">
						<sjm:textfield name="entity.actualLuggageWeight" label="Actual Luggage Weight" />
						
						<sjm:select
							name="entity.flightStatus" 
							headerKey=""
							headerValue="----------Select-----------"
							listKey="name()"
							listValue="name()"
							list="flightStatusLov"
							label="Status"
							emptyOption="false"
							required="true"
						/>						
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<sjm:select 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Taxes/Charges" 
							required="true"
							multiple="true"
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<sjm:select 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Rebates" 
							required="true"
							multiple="true"
						/>
					</s:if>	
					
					<s:if test="!(airportLov == null || airportLov.isEmpty())">
						<sjm:select 
							name="selThruFlightIds" 
							list="airportLov"
							listKey="airportCode"
							listValue="airportName"
							emptyOption="false"
							label="Arrival Airport" 
							required="true"
							multiple="true"
							
						/>
					</s:if>	
				
				</s:form>	
		<sjm:a button="true" buttonIcon="gear" formIds="flightRecordForm">Save</sjm:a>
		<sjm:a button="true" buttonIcon="arrow-l" data-rel="back">Back</sjm:a>
		<s:include value="/common/msg.jsp" />
	</body>
</html>