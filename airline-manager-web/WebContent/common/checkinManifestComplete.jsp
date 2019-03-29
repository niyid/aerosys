<%@ include file="/common/taglibs.jsp"%>
<div class="panel panel-primary">
	<div class="panel-heading">Booking Manifest</div>
	<div class="panel-body">
			<s:hidden name="bookingId" />
			<security:authorize access="hasAnyRole('ROLE_OPERATOR_BRISTOW')">
				<s:checkbox name="entity.paid" />
			</security:authorize>
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td>#</td>
						<td>Ticket</td>
						<td>Name</td>
						<td>Class</td>
						<td style="text-align:right">Price</td>
						<td>Carry On</td>
						<td>Luggage Count</td>
						<td>Luggage Weight</td>
						<td></td>
					</tr>
				</thead>
				<s:iterator value="entity.fetchedTickets" status="status">
					<tr>
						<td><s:property value="%{#status.index + 1}"/></td>
						<td><s:property value="ticketCode" /></td>
						<td><s:property value="passenger.fullName" /></td>
						<td>
							<s:if test="passenger.ticketClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@ECONOMY">
								Business Travel
							</s:if>
							<s:elseif test="passenger.ticketClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@BUSINESS">
								Business Class
							</s:elseif>
							<s:elseif test="passenger.ticketClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@FIRST">
								First Class
							</s:elseif>
						<td style="text-align:right" title='<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${price / usdCurrency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${entity.flight.currency.currencySymbol}" value="${price}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td><s:property value="passenger.carryOnWeight" /></td>
						<td><s:property value="passenger.luggageCount" /></td>
						<td><s:property value="passenger.luggageWeight" /></td>
					</tr>
				</s:iterator>
			</table>
	</div>
</div>
<s:include value="/common/msg.jsp" />	
