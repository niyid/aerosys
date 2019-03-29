<%@ include file="/common/taglibs.jsp"%>

	<div class="panel-group">
		<div class="panel panel-primary">
			<div class="panel-heading">Parameters</div>
			<div class="panel-body">
				<s:form id="searchFilterForm" action="searchFilter!search" namespace="/" theme="xhtml" method="post">
				
					<sj:datepicker name="flightDate" label="Flight Date" title="Date" displayFormat="dd/mm/yy" required="true" />
					
					<s:select 
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
					
					<s:select 
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
					
					<sj:datepicker name="flightDate2" label="Return Date" title="Return Date" displayFormat="dd/mm/yy" required="false" />
					
					<sj:submit id="searchFilterButton" button="true" value="Search" targets="searchFilterFormDiv" effect="highlight" effectDuration="500" onClickTopics="blockUIOn" onSuccessTopics="refreshSearchResultDiv,blockUIOff" />
				</s:form>
			
			</div>
		</div>
	</div>
<s:include value="/common/msg.jsp" />
