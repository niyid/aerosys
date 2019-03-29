<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
		<div class="panel-heading">
			<s:if test="flightNumber == null && flightDate == null">Add</s:if><s:else>Edit</s:else> Flight
			<span align="right">
				<s:if test="flightNumber != null && flightDate != null">
					<s:url var="newRecordUrl" action="flightRecord!initNew" namespace="/operator/ajax" />
					<s:url var="flightCrewSelectionUrl" action="showCrewSelectionPage" namespace="/operator">
						<s:param name="flightId" value="%{flightId}" />
					</s:url>
					<s:url var="crewActivityUrl" action="showActivityPage" namespace="/operator">
						<s:param name="relatedFlightId" value="%{flightId}" />
					</s:url>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="flightFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">New Flight</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{flightCrewSelectionUrl}" openDialog="crewDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">Crew</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{crewActivityUrl}" openDialog="activityDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">Related Activities</span></sj:a>
				</s:if>
			</div>
		</div>
		</security:authorize>
		<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-eye-open"></span>Viewing Flight
			</div>
		</security:authorize>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				<s:form id="flightRecordForm" action="flightRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:textfield name="flightNumber" label="Flight Number" required="true" readonly="true" />

					<s:select 
						name="airplaneRegNumber" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airplaneLov"
						listKey="regNumber"
						listValue="model.modelName + ' - ' + regNumber"
						emptyOption="false"
						label="Airplane" 
						required="true"
						disabled="true"
					/>
					
					<sj:datepicker name="entity.departureTime" label="Departure Time" displayFormat="dd/mm/yy" timepickerFormat="hh:mm" timepicker="true" required="true" disabled="true" />
					<s:select 
						name="departureAirportCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Departure Airport" 
						required="true"
						disabled="true"
					/>						
					
					<sj:datepicker name="entity.arrivalTime" label="Arrival Time" displayFormat="dd/mm/yy" timepickerFormat="hh:mm" timepicker="true" required="true" disabled="true" />
					<s:select 
						name="arrivalAirportCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Arrival Airport" 
						required="true"
						disabled="true"
					/>
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" readonly="true" />
					<s:textfield name="entity.airFareBizClass" label="%{entity.businessTravelName} Fare" required="false" readonly="true" />
					<s:textfield name="entity.airFareFirstClass" label="%{entity.firstTravelName} Fare" required="false" readonly="true" />
					
					<s:textfield name="entity.estimatedLuggageWeight" label="Estimated Luggage Weight" readonly="true" />
					<s:select 
						name="currencyCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="currencyLov"
						listKey="currencyCode"
						listValue="currencyName"
						emptyOption="false"
						label="Currency" 
						required="true"
						disabled="true"
					/>
					<s:select
						name="entity.flightFrequency" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="flightFrequencyLov"
						label="Frequency"
						emptyOption="false"
						required="true"
						disabled="true"
					/>						
					
					<s:if test="flightNumber != null && flightDate != null">
						<s:textfield name="entity.actualLuggageWeight" label="Actual Luggage Weight" readonly="true" />
						
						<s:select
							name="entity.flightStatus" 
							headerKey=""
							headerValue="----------Select-----------"
							listKey="name()"
							listValue="name()"
							list="flightStatusLov"
							label="Status"
							emptyOption="false"
							required="true"
							disabled="true"
						/>						
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<s:select 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Taxes/Charges" 
							required="true"
							multiple="true"
							disabled="true"
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<s:select 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Rebates" 
							required="true"
							multiple="true"
							disabled="true"
						/>
					</s:if>	
					
					<s:if test="!(airportLov == null || airportLov.isEmpty())">
						<s:select 
							name="selThruFlightIds" 
							list="airportLov"
							listKey="airportCode"
							listValue="airportName"
							emptyOption="false"
							label="Select Thru-flight Airports" 
							required="true"
							multiple="true"
							disabled="true"
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightListDiv,blockUIOff" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>	
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<s:form id="flightRecordForm" action="flightRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:textfield name="flightNumber" label="Flight Number" required="true" />

					<s:select 
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
					
					<sj:datepicker name="entity.departureTime" label="Departure Time" displayFormat="dd/mm/yy" timepickerFormat="hh:mm" timepicker="true" required="true" />
					<s:select 
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
					
					<sj:datepicker name="entity.arrivalTime" label="Arrival Time" displayFormat="dd/mm/yy" timepickerFormat="hh:mm" timepicker="true" required="true" />
					<s:select 
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
					<s:select 
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
					<s:select
						name="entity.flightFrequency" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="flightFrequencyLov"
						label="Frequency"
						emptyOption="false"
						required="true"
					/>						
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" />
					<s:textfield name="entity.airFareBizClass" label="%{entity.businessTravelName} Fare" required="false" />
					<s:textfield name="entity.airFareFirstClass" label="%{entity.firstTravelName} Fare" required="false" />
					
					<s:textfield name="entity.estimatedLuggageWeight" label="Estimated Luggage Weight" />
					
					<s:if test="flightNumber != null && flightDate != null">
						<s:textfield name="entity.actualLuggageWeight" label="Actual Luggage Weight" />
						
						<s:select
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
						<s:select 
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
						<s:select 
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
						<s:select 
							name="selThruFlightIds" 
							list="airportLov"
							listKey="airportCode"
							listValue="airportName"
							emptyOption="false"
							label="Select Thru-flight Airports" 
							required="true"
							multiple="true"
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightListDiv,blockUIOff" effect="highlight" effectDuration="500" />
				</s:form>	
			</security:authorize>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	