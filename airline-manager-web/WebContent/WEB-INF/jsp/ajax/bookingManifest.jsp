<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/search_header.jsp"%>
<s:form id="bookingRecordForm" action="bookingManifest!confirm" namespace="/">
<s:hidden name="passengerCount" />
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Selected Flights</div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%">Number</td>
						<td width="25%">Departure</td>
						<td width="20%">Departure Date</td>
						<td width="25%">Arrival</td>
						<td width="20%">Arrival Date</td>
					</tr>
				</thead>
				<s:iterator value="selectedFlights">
					<tr>
						<td><s:property value="id.flightNumber" /></td>
						<td><s:property value="departureAirport.airportName" /></td>
						<td><s:date name="departureTime" format="dd/MM/yyyy hh:mm a" /></td>
						<td><s:property value="arrivalAirport.airportName" /></td>
						<td><s:date name="arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">Booking Info</div>
		<div class="panel-body">
			<span></span><s:checkbox name="entity.paid" /><s:label cssStyle="display:inline-block; font-weight:bold;" for="entity.paid" value="Paid Flight" />
		</div>
	</div>
	</security:authorize>
	<div class="panel panel-primary">
		<div class="panel-heading">Booking Manifest</div></div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="5%">#</td>
						<td width="12%">Title</td>
						<td width="17%">First Name</td>
						<td width="15%">Mid Name</td>
						<td width="17%">Last Name</td>
						<td width="12%">Phone</td>
						<td width="12%">Email</td>
						<td width="5%">Gender</td>
						<td width="5%">Minor</td>
					</tr>
				</thead>
				<s:iterator value="passengers" status="status">
					<tr>
						<td><s:property value="%{#status.index + 1}"/></td>
						<td>
							<s:select 
								name="titleIds[%{#status.index}]" 
								headerKey=""
								headerValue="--Select--"
								list="userTitleLov"
								listKey="id"
								listValue="description"
								emptyOption="false"
								required="true"
								cssStyle="width: 70px;" 
							/>																													
						</td>
						<td><s:textfield name="passengers[%{#status.index}].firstName" required="true" cssStyle="width: 120px;" /></td>
						<td><s:textfield name="passengers[%{#status.index}].middleName" required="false" cssStyle="width: 70px;" /></td>
						<td><s:textfield name="passengers[%{#status.index}].lastName" required="true" cssStyle="width: 120px;" /></td>
						<td><s:textfield name="passengers[%{#status.index}].phoneNumber1" required="true" cssStyle="width: 110px;" /></td>
						<td><s:textfield name="passengers[%{#status.index}].email1" required="false" cssStyle="width: 110px;" /></td>
						<td>
							<s:select 
								name="passengers[%{#status.index}].gender" 
								headerKey=""
								headerValue="--Select--"
								list="genderLov"
								listKey="name()"
								listValue="name()"
								emptyOption="false"
								required="true"
								cssStyle="width: 70px;" 
							/>																													
						</td>
						<td>
							<s:select 
								name="passengers[%{#status.index}].minorPassenger" 
								headerKey=""
								headerValue="--Select--"
								list="minorLov"
								listKey="name()"
								listValue="name()"
								emptyOption="false"
								required="false"
								cssStyle="width: 70px;" 
							/>																													
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
</div>
</s:form>
<sj:submit formIds="bookingRecordForm" button="true" value="Next" buttonIcon="ui-icon-gear" targets="searchMainDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />


<s:include value="/common/msg.jsp" />	