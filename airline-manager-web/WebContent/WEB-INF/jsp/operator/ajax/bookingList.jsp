<%@ include file="/common/taglibs.jsp"%>

<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Bookings</div>
		<div class="panel-body" id="bookingListInnerDiv">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<td width="15%">Reservation</td>
							<td width="20%">Flight</td>
							<td width="30%">Departure</td>
							<td width="30%">Arrival</td>
							<td width="5%"></td>
						</tr>
					</thead>
					<s:iterator value="bookings">
						<tr>
							<td><s:property value="id" /></td>
							<td><s:property value="flight.id.flightNumber" /></td>
							<td><s:property value="flight.departureTime" /></td>
							<td><s:property value="flight.arrivalTime" /></td>
							<td align="right">
								<s:url var="selUrl" action="bookingRecord!select" namespace="/operator/ajax">
									<s:param name="bookingId" value="%{id}" />
								</s:url>
								<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="bookingFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
							</td>
						</tr>
					</s:iterator>
				</table>
		</div>
	</div>
</div>