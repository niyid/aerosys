<%@ include file="/common/taglibs.jsp"%>
<div class="panel panel-primary">
	<div class="panel-heading">Booking Manifest</div>
	<div class="panel-body">
		<s:form id="checkinForm" action="popupSaveCheckin" namespace="/">
			<s:hidden name="bookingId" />
			<security:authorize access="hasAnyRole('ROLE_OPERATOR_BRISTOW')">
				<s:checkbox name="entity.paid" />
			</security:authorize>
			<table>
				<tr>
					<td>
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
							<s:iterator value="entity.tickets" status="status">
								<s:hidden name="entity.tickets[%{#status.index}].passenger.id" />
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
									<s:if test="entity.tickets.size() gt ticketIndex">
										<td><s:textfield name="passenger.carryOnWeight" disabled="true" cssStyle="width: 70px;" /></td>
										<td><s:textfield name="passenger.luggageCount" disabled="true" cssStyle="width: 70px;" /></td>
										<td><s:textfield name="passenger.luggageWeight" disabled="true" cssStyle="width: 70px;" /></td>
									</s:if>
									<s:else>
										<td><s:textfield name="entity.tickets[%{#status.index}].passenger.carryOnWeight" required="true" readonly="true" cssStyle="width: 70px;" /></td>
										<td><s:textfield name="entity.tickets[%{#status.index}].passenger.luggageCount" required="true" readonly="true" cssStyle="width: 70px;" /></td>
										<td><s:textfield name="entity.tickets[%{#status.index}].passenger.luggageWeight" required="true" readonly="true" cssStyle="width: 70px;" /></td>
									</s:else>
									<td>
										<s:if test="#status.index == ticketIndex"><span class="glyphicon glyphicon-hand-left"></span></s:if>
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
					<td>
						<s:if test="amount != null">
						<div class="panel panel-primary">
							<div class="panel-heading">Excess Luggage Charge</div>
							<div class="panel-body">
								<span style="font-size: x-large;"></span>
								<h1>
									Total Charge: <fmt:formatNumber type="currency" currencySymbol="${entity.flight.currency.currencySymbol}" value="${price}" maxFractionDigits="2" minFractionDigits="2"/>
								</h1>
							</div>
						</div>
						<div class="panel panel-primary">
							<div class="panel-heading">Payment Details</div>
							<div class="panel-body" id="paymentFormDiv">
								<s:url var="invoicePaymentUrl" action="popupInvoicePayment" namespace="/" escapeAmp="false">
									<s:param name="bookingId" value="%{bookingId}" />
									<s:param name="amount" value="%{amount}" />
								</s:url>						
								<sj:div href="%{invoicePaymentUrl}" />
							</div>
						</div>		
						</s:if>
					</td>
				</tr>
			</table>
		</s:form>
		<s:if test="!(entity.tickets.size() gt ticketIndex)">
			<sj:submit formIds="checkinForm" button="true" value="Process" buttonIcon="ui-icon-gear" targets="checkinManifest" effect="highlight" effectDuration="500" />
		</s:if>
	</div>
</div>
<s:include value="/common/msg.jsp" />	
