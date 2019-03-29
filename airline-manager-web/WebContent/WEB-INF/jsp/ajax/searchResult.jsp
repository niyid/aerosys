<%@ include file="/common/taglibs.jsp"%>

	<s:form id="flightSelectionForm" action="popupFlightsSelect" namespace="/" method="post">
		<div class="panel-group">

			<div class="panel panel-primary">
				<div class="panel-heading">Departure Flights</div>
				<div class="panel-body">
					<s:if test="flightResult == null || flightResult.isEmpty()">
						No matching flights found.
					</s:if>
					<s:else>
						<table class="table table-hover table-striped">
							<thead>
								<tr>
									<td width="15%">Flight Number</td>
									<td width="15%">Departure Time</td>
									<td width="10%">Departure</td>
									<td width="15%">Arrival Time</td>
									<td width="10%">Arrival</td>
									<td width="10%">Price</td>
									<td width="13%">Detail</td>
									<td width="15%">Seats</td>
									<td width="2%"></td>
								</tr>
							</thead>
							<s:iterator value="flightResult" status="status">
								<s:if test="routePrice == null">
									<tr title='<s:if test="hasEconomy"><s:property value="economyTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="hasBusiness"><s:property value="businessTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="hasFirst"><s:property value="firstTravelName" />:<fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>'>
								</s:if>
								<s:else>
									<tr title='<s:if test="routePrice.hasEconomy"><s:property value="routePrice.economyTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="routePrice.hasBusiness"><s:property value="routePrice.businessTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="routePrice.hasFirst"><s:property value="routePrice.firstTravelName" />:<fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>'>
								</s:else>
									<td><s:property value="id.airlineCode" /> <s:property
											value="id.flightNumber" /></td>
									<td><s:date name="departureTime"
											format="dd/MM/yyyy hh:mm a" /></td>
									<td><span
										title='<s:property value="departureAirport.airportName" />'><s:property
												value="departureAirport.airportCode" /></span></td>
									<td><s:date name="arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
									<td><span
										title='<s:property value="arrivalAirport.airportName" />'><s:property
												value="arrivalAirport.airportCode" /></span></td>
									<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></td>
									<td><s:url var="selUrl" action="popupFlightDetails"
											namespace="/" escapeAmp="false">
											<s:param name="regNumber" value="%{airplane.regNumber}" />
											<s:param name="flightNumber" value="%{id.flightNumber}" />
											<s:param name="flightDate" value="%{getText(id.flightDate)}" />
										</s:url> <sj:a button="true" href="%{selUrl}"
											openDialog="flightDetailsDialog"><span class="glyphicon glyphicon-new-window"></span>Open</sj:a></td>
									<td><s:property value="seatsLeft" /> left</td>
									<td>
										<s:if test="seatsLeft gt 0">
											<s:if test="#status.index == 0">
												<input css="form-control" type="radio" name="flightId" checked="true" value="<s:property value='id' />"  />
											</s:if>
											<s:else>
												<input css="form-control" type="radio" name="flightId" value="<s:property value='id' />" />
											</s:else>
										</s:if>
										<s:else>Full</s:else>
									</td>
								</tr>
							</s:iterator>
						</table>
					</s:else>
				</div>
			</div>
			
			<div class="panel panel-primary">
				<div class="panel-heading">Return Flights</div>
				<div class="panel-body">
					<s:if test="flightResult2 == null || flightResult2.isEmpty()">
						No matching flights found.
					</s:if>
					<s:else>
						<table class="table table-hover table-striped">
							<thead>
								<tr>
									<td width="15%">Flight Number</td>
									<td width="15%">Departure Time</td>
									<td width="10%">Departure</td>
									<td width="15%">Arrival Time</td>
									<td width="10%">Arrival</td>
									<td width="10%">Price</td>
									<td width="13%">Detail</td>
									<td width="15%">Seats</td>
									<td width="2%"></td>
								</tr>
							</thead>
							<s:iterator value="flightResult2" status="status">
								<s:set var="routePrice" value="routePrice(userId, currency.id)" />
								<s:if test="routePrice == null">
									<tr title='<s:if test="hasEconomy"><s:property value="economyTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="hasBusiness"><s:property value="businessTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="hasFirst"><s:property value="firstTravelName" />:<fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>'>
								</s:if>
								<s:else>
									<tr title='<s:if test="routePrice.hasEconomy"><s:property value="routePrice.economyTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="routePrice.hasBusiness"><s:property value="routePrice.businessTravelName" />: <fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>&#10;<s:if test="routePrice.hasFirst"><s:property value="routePrice.firstTravelName" />:<fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${routePrice.airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></s:if>'>
								</s:else>
									<td><s:property value="id.airlineCode" /> <s:property
											value="id.flightNumber" /></td>
									<td><s:date name="departureTime"
											format="dd/MM/yyyy hh:mm a" /></td>
									<td><span
										title='<s:property value="departureAirport.airportName" />'><s:property
												value="departureAirport.airportCode" /></span></td>
									<td><s:date name="arrivalTime" format="dd/MM/yyyy hh:mm a" /></td>
									<td><span
										title='<s:property value="arrivalAirport.airportName" />'><s:property
												value="arrivalAirport.airportCode" /></span></td>
									<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></td>
									<td>
										<s:url var="selUrl2" action="popupFlightDetails"
											namespace="/" escapeAmp="false">
											<s:param name="regNumber" value="%{airplane.regNumber}" />
											<s:param name="flightNumber" value="%{id.flightNumber}" />
											<s:param name="flightDate" value="%{getText(id.flightDate)}" />
										</s:url> <sj:a button="true" href="%{selUrl2}"
											openDialog="flightDetailsDialog"><span class="glyphicon glyphicon-new-window"></span>Open</sj:a></td>

									<td><s:property value="seatsLeft" /> left</td>
									<td>
										<s:if test="seatsLeft gt 0">
											<s:if test="#status.index == 0">
												<input css="form-control" type="radio" name="flightId2" checked="true" value="<s:property value='id' />" />
											</s:if>
											<s:else>
												<input css="form-control" type="radio" name="flightId2" value="<s:property value='id' />" />
											</s:else>
										</s:if>
										<s:else>Full</s:else>
									</td>
								</tr>
							</s:iterator>
						</table>
					</s:else>
				</div>
			</div>
				<s:if test="!flightResult.isEmpty()">
				<div class="panel panel-primary">
					<div class="panel-heading">Other Information</div>
					<div class="panel-body">
						<s:label for="passengerCount" value="Number of Passengers" cssClass="label" /><br />
						<s:textfield id="passengerCount" name="passengerCount" required="true" />
					</div>
				</div>
				</s:if>
		</div>
	</s:form>
	<s:if test="!flightResult.isEmpty()">
		<sj:submit id="flightSelectionButton" formIds="flightSelectionForm"
			buttonIcon="ui-icon-gear" button="true" value="Book Flights" targets="searchMainDiv" onClickTopics="blockUIOn" onSuccessTopics="blockUIOff" />
	</s:if>
