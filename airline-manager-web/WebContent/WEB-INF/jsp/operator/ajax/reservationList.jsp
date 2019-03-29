<%@ include file="/common/taglibs.jsp"%>

<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Search Filter</div>
		<div class="panel-body">
			<s:form id="reservationFilterForm" action="reservationList" namespace="/operator/ajax" method="post" cssClass="form-inline">
			  	<div class="form-group">
			    	<label for="flightCode">Reservation Code</label>
			    	<s:textfield name="reservationCode" title="Reservation Code" cssClass="form-control" />
			  	</div>
				<div class="form-group">
			    	<label for="flightDate">Flight Date:</label>
			    	<sj:datepicker name="flightDate" title="Flight Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="flightNumber">Flight Number</label>
			    	<s:textfield name="flightNumber" title="Flight Number" cssClass="form-control" />
			  	</div>
			  	<sj:submit id="searchFilterButton" buttonIcon="ui-icon-gear" button="true" value="Search" targets="reservationListDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">Reservations</div>
		<div class="panel-body" id="bookingListInnerDiv">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<td width="5%">Reservation</td>
							<td width="10%">Agent</td>
							<td width="15%">Name</td>
							<td width="5%">Flight</td>
							<td width="10%">Booked On</td>
							<td width="5%">Departure</td>
							<td width="10%">Departure Time</td>
							<td width="5%">Arrival</td>
							<td width="10%">Arrival Time</td>
							<td width="7.5%" style="text-align:right">Total Price</td>
							<td width="7.5%">Status</td>
							<td width="3.5%"></td>
							<td width="3.5%"></td>
							<td width="3.5%"></td>
						</tr>
					</thead>
					<s:iterator value="bookings">
						<tr>
							<td><s:property value="top[0].reservationCode" /></td>
							<td><s:property value="top[0].agent.organization.organizationName" /></td>
							<td><s:property value="top[1].fullName" /></td>
							<td><s:property value="top[0].id.airlineCode" /> <s:property value="top[0].flight.id.flightNumber" /></td>
							<td><s:date name="top[0].bookingDate" format="dd/MM/yyyy hh:mm a" /></td>
							<td><span title='<s:property value="top[0].flight.departureAirport.airportName" />'><s:property value="top[0].flight.departureAirport.airportCode" /></span></td>
							<td><s:date name="top[0].flight.departureTime" format="dd/MM/yyyy hh:mm a" /></td>
							<td><span title='<s:property value="top[0].flight.arrivalAirport.airportName" />'><s:property value="top[0].flight.arrivalAirport.airportCode" /></span></td>
							<td><s:date name="top[0].flight.arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
							<td style="text-align:right" title='<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${top[0].totalPrice / top[0].flight.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${top[0].flight.currency.currencySymbol}" value="${top[0].totalPrice}" maxFractionDigits="2" minFractionDigits="2"/></td>
							<td><s:property value="top[0].bookingStatus" /></td>
							<s:if test="top[0].flight.departureTime.after(new java.util.Date()) && hasAny('POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID') || !hasAny('POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID')">
								<td align="right">
									<s:if test="top[0].bookingStatus == @com.vasworks.airliner.model.Booking$BookingStatus@NEW">
										<s:url var="confirmUrl" action="popupConfirmBooking" namespace="/" escapeAmp="false">
											<s:param name="bookingId" value="%{top[0].id}" />
										</s:url> 
										<sj:a cssClass="btn btn-primary btn-lg" href="%{confirmUrl}" openDialog="confirmBookingDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-ok"></span>Confirm</sj:a>
									</s:if>
									<s:if test="top[0].bookingStatus == @com.vasworks.airliner.model.Booking$BookingStatus@CONFIRMED && top[0].checkinDate == null">
										<s:url var="checkinUrl" action="popupLoadCheckin" namespace="/" escapeAmp="false">
											<s:param name="bookingId" value="%{top[0].id}" />
										</s:url> 
										<sj:a cssClass="btn btn-primary btn-lg" href="%{checkinUrl}" openDialog="checkinBookingDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-ok"></span>Checkin</sj:a>
									</s:if>
									<s:else>
										CHECKED_IN
									</s:else>
								</td>
								<td align="right">
									<s:url var="cancelUrl" action="popupCancelBooking" namespace="/" escapeAmp="false">
										<s:param name="bookingId" value="%{top[0].id}" />
									</s:url> 
									<sj:a cssClass="btn btn-primary btn-lg" href="%{cancelUrl}" openDialog="cancelBookingDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-ok"></span>Cancel</sj:a>
								</td>
								<td align="right">
									<s:url var="updateUrl" action="popupUpdateBooking" namespace="/" escapeAmp="false">
										<s:param name="bookingId" value="%{top[0].id}" />
									</s:url> 
									<sj:a cssClass="btn btn-primary btn-lg" href="%{updateUrl}" openDialog="updateBookingDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-ok"></span>Change</sj:a>
								</td>
							</s:if>
							<s:else>
								<td></td>
								<td></td>
								<td></td>
							</s:else>
						</tr>
					</s:iterator>
				</table>
		</div>
	</div>
</div>