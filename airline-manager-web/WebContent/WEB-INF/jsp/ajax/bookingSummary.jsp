<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/search_header.jsp"%>

<s:iterator value="bookings" status="bookingStatus">
<div class="panel-group">
	<s:if test="%{hasAny('ROLE_OPERATOR')}">
	<div class="panel panel-primary">
		<div class="panel-heading">Reservation For</div>
		<div class="panel-body">
			<s:property value="owner.organizationName" /> [<b><s:property value="bookingStatus" /></b>]																											
		</div>
	</div>
	</s:if>
	<div class="panel panel-primary">
		<div class="panel-heading">Flight <s:property value="%{#bookingStatus.index + 1}"/></div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%"><b>Number</b></td>
						<td width="25%"><b>Departure</b></td>
						<td width="20%"><b>Departure Date</b></td>
						<td width="25%"><b>Arrival</b></td>
						<td width="20%"><b>Arrival Date</b></td>
					</tr>
				</thead>
				<tr>
					<td><s:property value="flight.id.flightNumber" /></td>
					<td><s:property value="flight.departureAirport.airportName" /></td>
					<td><s:date name="flight.departureTime" format="dd/MM/yyyy hh:mm a" /></td>
					<td><s:property value="flight.arrivalAirport.airportName" /></td>
					<td><s:date name="flight.arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
				</tr>
			</table>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Booked Tickets</div></div>
			<div class="panel-body">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<td><b>#</b></td>
							<td><b>Code</b></td>
							<td><b>Title</b></td>
							<td><b>Name</b></td>
							<td><b>Phone</b></td>
							<td><b>Email</b></td>
							<td><b>Gender</b></td>
							<td style="text-align:right"><b>Price</b></td>
							<td><b>Minor</b></td>
						</tr>
					</thead>
					<s:iterator value="fetchedTickets" status="status">
						<tr>
							<td><s:property value="%{#status.index + 1}"/></td>
							<td><s:property value="ticketCode" /></td>
							<td><s:property value="passenger.titlePrefix.description" /></td>
							<td><s:property value="passenger.fullName" /></td>
							<td><s:property value="passenger.phoneNumber1" /></td>
							<td><s:property value="passenger.email1" /></td>
							<td><s:property value="passenger.gender" /></td>
							<td style="text-align:right" title='<fmt:formatNumber type="currency" currencySymbol="${defaultCurrency.currencySymbol}" value="${price * defaultCurrency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${entity.flight.currency.currencySymbol}" value="${price}" maxFractionDigits="2" minFractionDigits="2"/></td>
							<td><s:property value="passenger.minorPassenger" /></td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</div>
</div>
</s:iterator>
