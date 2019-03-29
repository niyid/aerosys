<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
		<div class="panel-heading">
			<s:if test="flightScheduleId == null">Add</s:if><s:else>Edit</s:else> Schedule
			<span align="right">
				<s:if test="flightScheduleId != null">
					<s:url var="newRecordUrl" action="flightScheduleRecord!initNew" namespace="/operator/ajax" />
					<s:url var="generateUrl" action="flightScheduleRecord!generate" namespace="/operator/ajax" escapeAmp="false">
						<s:param name="flightScheduleId" value="%{flightScheduleId}" />
					</s:url>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open"></span>New Flight Schedule</sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{generateUrl}" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-cog"></span>Generate Flights</sj:a>
				</s:if>
			</div>
		</div>
		</security:authorize>
		<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-eye-open"></span>Viewing Schedule
			</div>
		</security:authorize>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				<s:form id="flightScheduleRecordForm" action="flightScheduleRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:hidden name="entity.scheduleStatus" value="%{@com.vasworks.airliner.model.FlightSchedule$ScheduleStatus@COMPLETE}" />
					<s:hidden name="entity.openSeating" />
					<s:hidden name="entity.autogenerate" />
					<s:hidden name="entity.numOfWeeks" />
					<s:hidden name="airplaneRegNumber" />
					<s:hidden name="arrivalAirportCode" />
					<s:hidden name="departureAirportCode" />
					<s:hidden name="currencyCode" />
					<s:iterator value="selWeekDays" status="status">
						<s:hidden name="selWeekDays[%{#status.index}]" />
					</s:iterator>
					<s:iterator value="selTaxIds" status="status">
						<s:hidden name="selTaxIds[%{#status.index}]" />
					</s:iterator>
					<s:iterator value="selRebateIds" status="status">
						<s:hidden name="selRebateIds[%{#status.index}]" />
					</s:iterator>
					
					<s:checkbox name="entity.openSeating" label="Open Seating" disabled="true" />
					<s:checkbox name="entity.autogenerate" label="Autogenerate" disabled="true" />

					<s:textfield name="entity.flightNumber" label="Flight Number" required="true" disabled="true" />
					<sj:datepicker label="Anchor Date" name="entity.startDate" displayFormat="dd/mm/yy" required="true" disabled="true" />
					
					<s:select 
						name="entity.numOfWeeks" 
						headerKey=""
						headerValue="----Select----"
						list="#{ 1,2,3,4 }"
						emptyOption="false"
						label="Number of Weeks" 
						required="true"
						disabled="true"
					/>

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
					
					<sj:datepicker name="departureTime" label="Departure Time" displayFormat="hh:mm" timepickerFormat="hh:mm" timepicker="true" timepickerOnly="true" required="true" readonly="true" />
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
					
					<sj:datepicker name="arrivalTime" label="Arrival Time" displayFormat="hh:mm" timepickerFormat="hh:mm" timepicker="true" timepickerOnly="true" required="true" readonly="true" />
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
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" readonly="true" />
					
					<s:if test="!(weekDayLov == null || weekDayLov.isEmpty())">
						<s:checkboxlist 
							name="selWeekDays" 
							list="weekDayLov"
							listKey="value"
							listValue="name()"
							label="Select Days of Week" 
							disabled="true"
							required="true"
						/>
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<s:checkboxlist 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							label="Select from Available Taxes/Charges" 
							disabled="true"
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<s:checkboxlist 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							label="Select from Available Rebates" 
							disabled="true"
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightScheduleListDiv,blockUIOff" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>	
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<s:form id="flightScheduleRecordForm" action="flightScheduleRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:hidden name="entity.scheduleStatus" value="%{@com.vasworks.airliner.model.FlightSchedule$ScheduleStatus@COMPLETE}" />
					<s:hidden name="flightScheduleId" />
					
					<s:checkbox name="entity.openSeating" label="Open Seating" />
					<s:checkbox name="entity.autogenerate" label="Autogenerate" />

					<s:textfield name="entity.flightNumber" label="Flight Number" required="true" />
					<sj:datepicker label="Anchor Date" name="entity.startDate" displayFormat="dd/mm/yy" required="true" />
					<s:select 
						name="entity.numOfWeeks" 
						headerKey=""
						headerValue="----Select----"
						list="#{ 1,2,3,4 }"
						emptyOption="false"
						label="Number of Weeks" 
						required="true"
					/>
										
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
					
					<sj:datepicker name="departureTime" label="Departure Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" />
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
					
					<sj:datepicker name="arrivalTime" label="Arrival Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" />
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
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" />
										
					<s:if test="!(weekDayLov == null || weekDayLov.length == 0)">
						<s:checkboxlist 
							name="selWeekDays" 
							list="weekDayLov"
							listKey="value"
							listValue="name()"
							label="Select Days of Week"
							required="true" 
						/>
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<s:checkboxlist 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							label="Select from Available Taxes/Charges" 
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<s:checkboxlist 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							label="Select from Available Rebates" 
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightScheduleListDiv,blockUIOff" effect="highlight" effectDuration="500" />
				</s:form>	
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_OPERATIONS')">
				<s:form id="flightScheduleRecordForm" action="flightScheduleRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:hidden name="entity.scheduleStatus" value="%{@com.vasworks.airliner.model.FlightSchedule$ScheduleStatus@COMMERCIALS}" />
					<s:hidden name="flightScheduleId" />
					<s:hidden name="currencyCode" />
					<s:iterator value="selWeekDays" status="status">
						<s:hidden name="selWeekDays[%{#status.index}]" />
					</s:iterator>
					
					<s:checkbox name="entity.openSeating" label="Open Seating" />
					<s:checkbox name="entity.autogenerate" label="Autogenerate" />

					<s:textfield name="entity.flightNumber" label="Flight Number" required="true" />
					<sj:datepicker label="Anchor Date" name="entity.startDate" displayFormat="dd/mm/yy" required="true" />
					<s:select 
						name="entity.numOfWeeks" 
						headerKey=""
						headerValue="----Select----"
						list="#{ 1,2,3,4 }"
						emptyOption="false"
						label="Number of Weeks" 
						required="true"
					/>
					
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
					
					<sj:datepicker name="departureTime" label="Departure Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" />
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
					
					<sj:datepicker name="arrivalTime" label="Arrival Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" />
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
						disabled="true"
					/>
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" readonly="true" />
										
					<s:if test="!(weekDayLov == null || weekDayLov.length == 0)">
						<s:checkboxlist 
							name="selWeekDays" 
							list="weekDayLov"
							listKey="value"
							listValue="name()"
							label="Select Days of Week"
							required="true" 
						/>
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<s:checkboxlist 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							label="Select from Available Taxes/Charges"
							disabled="true" 
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<s:checkboxlist 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							label="Select from Available Rebates"
							disabled="true" 
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightScheduleListDiv,blockUIOff" effect="highlight" effectDuration="500" />
				</s:form>	
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_COMMERCIALS')">
				<s:form id="flightScheduleRecordForm" action="flightScheduleRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:hidden name="entity.scheduleStatus" value="%{@com.vasworks.airliner.model.FlightSchedule$ScheduleStatus@FRONT_DESK}" />
					<s:hidden name="flightScheduleId" />
					<s:hidden name="entity.openSeating" />
					<s:hidden name="entity.autogenerate" />
					<s:hidden name="entity.numOfWeeks" />
					
					<s:checkbox name="entity.openSeating" label="Open Seating" disabled="true" />
					<s:checkbox name="entity.autogenerate" label="Autogenerate" disabled="true" />

					<s:textfield name="entity.flightNumber" label="Flight Number" required="true" readonly="true" />
					<sj:datepicker label="Anchor Date" name="entity.startDate" displayFormat="dd/mm/yy" required="true" readonly="true" />
					<s:textfield name="entity.numOfWeeks" label="Number of Weeks" required="true" readonly="true" />
					<s:select 
						name="entity.numOfWeeks" 
						headerKey=""
						headerValue="----Select----"
						list="#{ 1,2,3,4 }"
						emptyOption="false"
						label="Number of Weeks" 
						required="true"
						disabled="true"
					/>
					
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
						readonly="true"
					/>
					
					<sj:datepicker name="departureTime" label="Departure Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" readonly="true" />
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
						readonly="true"
					/>						
					
					<sj:datepicker name="arrivalTime" label="Arrival Time" displayFormat="hh:mm" timepickerOnly="true" timepickerFormat="hh:mm" timepicker="true" required="true" readonly="true" />
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
						readonly="true"
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
						readonly="true"
					/>
					
					<s:textfield name="entity.airFareEconomy" label="%{entity.economyTravelName} Fare" required="false" />
										
					<s:if test="!(weekDayLov == null || weekDayLov.length == 0)">
						<s:checkboxlist 
							name="selWeekDays" 
							list="weekDayLov"
							listKey="value"
							listValue="name()"
							label="Select Days of Week"
							required="true"
							disabled="true"
						/>
					</s:if>
					
					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<s:checkboxlist 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							label="Select from Available Taxes/Charges" 
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<s:checkboxlist 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							label="Select from Available Rebates" 
						/>
					</s:if>	
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="flightScheduleFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshFlightScheduleListDiv,blockUIOff" effect="highlight" effectDuration="500" />
				</s:form>	
			</security:authorize>										
		</div>
	</div>
</div>
<s:include value="/common/msg.jsp" />	