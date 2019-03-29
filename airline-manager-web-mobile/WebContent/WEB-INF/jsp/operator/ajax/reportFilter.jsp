<%@ include file="/common/taglibs.jsp"%>


	<table>
		<tr>
			<td class="windowHeader"><div>Parameters</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
				<s:form id="searchFilterForm" action="searchFilter!search" namespace="/" theme="xhtml" method="post">
					<sj:datepicker name="departure" label="Departure Date" title="Departure date" displayFormat="dd/mm/yy" required="true" />
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
					
					<sj:datepicker name="arrival" label="Arrival Date" title="Arrival date" displayFormat="dd/mm/yy" required="true" />
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
					
					<sj:submit id="searchFilterButton" button="true" value="Search" buttonIcon="search" targets="searchFilterFormDiv" effect="highlight" effectDuration="500" onSuccessTopics="refreshSearchResultDiv" indicator="indicator" listenTopics="profileSearchChangeTopic" />
				</s:form>
				</div>
			</td>
		</tr>
	</table>

	<s:include value="/common/msg.jsp" />
