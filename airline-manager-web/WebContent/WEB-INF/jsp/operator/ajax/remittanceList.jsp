<%@ include file="/common/taglibs.jsp"%>

<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Search Filter</div>
		<div class="panel-body">
			<s:form id="remittanceFilterForm" action="remittanceList" namespace="/operator/ajax" method="post" cssClass="form-inline">
				<div class="form-group">
			    	<label for="startDate">Ticket Start Date:</label>
			    	<sj:datepicker name="startDate" title="Start Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="endDate">Ticket End Date:</label>
			    	<sj:datepicker name="endDate" title="End Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="remittanceStatus">Status</label>
							<s:select 
								name="remittanceStatus" 
								headerKey=""
								headerValue="----------Select----------"
								list="remittanceStatusLov"
								listKey="name()"
								listValue="name()"
								emptyOption="false"
								required="true"
							/>																													
			  	</div>
			  	<sj:submit id="searchFilterButton" buttonIcon="ui-icon-gear" button="true" value="Search" targets="remittanceListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">TSC Remittance</div>
		<div class="panel-body" id="remittanceListInnerDiv">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<td width="20%">Flight</td>
							<td width="5%">Ticket</td>
							<td width="15%" style="text-align:right">Ticket Price</td>
							<td width="15%" style="text-align:right">Remittance Amount</td>
							<td width="20%">Ticket Date</td>
							<td width="20%">Remittance Date</td>
							<td width="5%">Status</td>
						</tr>
					</thead>
					<s:iterator value="remittances">
						<tr>
							<td><s:property value="ticket.booking.flight.id.airlineCode" /><s:property value="ticket.booking.flight.id.flightNumber" /></td>
							<td><s:property value="ticket.ticketCode" /></td>
							<td style="text-align:right" title='<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${ticket.price / entity.flight.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${entity.flight.currency.currencySymbol}" value="${ticket.price}" maxFractionDigits="2" minFractionDigits="2"/></td>
							<td style="text-align:right" title='<fmt:formatNumber type="currency" currencySymbol="${remitCurrency.currencySymbol}" value="${remitAmount}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${remitCurrency.currencySymbol}" value="${remitAmount}" maxFractionDigits="2" minFractionDigits="2"/></td>
							<td><s:date name="ticketDate" format="dd/MM/yyyy hh:mm a" /></td>
							<td><s:date name="remittanceDate" format="dd/MM/yyyy hh:mm a" /></td>
							<td>
								<s:if test="remittanceStatus == @com.vasworks.airliner.model.TscRemittance$RemittanceStatus@PENDING">
									<span class="btn btn-warning"><s:property value="remittanceStatus" /></span>
								</s:if>
								<s:if test="remittanceStatus == @com.vasworks.airliner.model.TscRemittance$RemittanceStatus@SENT">
									<span class="btn btn-success"><s:property value="remittanceStatus" /></span>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</table>
		</div>
	</div>
</div>