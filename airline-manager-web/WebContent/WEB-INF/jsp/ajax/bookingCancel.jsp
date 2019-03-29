<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/search_header.jsp"%>
<s:form id="bookingCancelForm" action="bookingRecord!cancel" namespace="/operator/ajax">
<s:hidden name="bookingId" />
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Customer</div>
		<div class="panel-body">
			<s:property value="entity.customer.organizationName"/>
		</div>
	</div>
</div>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Flight</div>
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
				<tr>
					<td><s:property value="entity.flight.id.flightNumber" /></td>
					<td><s:property value="entity.flight.departureAirport.airportName" /></td>
					<td><s:date name="entity.flight.departureTime" format="dd/MM/yyyy hh:mm a" /></td>
					<td><s:property value="entity.flight.arrivalAirport.airportName" /></td>
					<td><s:date name="entity.flight.arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
				</tr>
			</table>
		</div>
	</div>
</div>
</s:form>
<sj:submit formIds="bookingCancelForm" button="true" value="Complete" buttonIcon="ui-icon-gear" targets="reservationListDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />

<s:include value="/common/msg.jsp" />
