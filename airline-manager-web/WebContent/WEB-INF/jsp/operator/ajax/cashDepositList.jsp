<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Search Filter</div>
		<div class="panel-body">
			<s:form id="remittanceFilterForm" action="cashDepositList" namespace="/operator/ajax" method="post" cssClass="form-inline">
				<div class="form-group">
			    	<label for="startDate">Start Date:</label>
			    	<sj:datepicker name="startDate" title="Start Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="endDate">End Date:</label>
			    	<sj:datepicker name="endDate" title="End Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="depositStatus">Status</label>
							<s:select 
								name="depositStatus" 
								headerKey=""
								headerValue="----------Select----------"
								list="depositStatusLov"
								listKey="name()"
								listValue="name()"
								emptyOption="false"
								required="true"
							/>																													
			  	</div>
			  	<sj:submit id="searchFilterButton" buttonIcon="ui-icon-gear" button="true" value="Search" targets="cashDepositListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Cash Deposits</div>
		<div class="panel-body" id="cashDepositListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="30%">Customer</td>
						<td width="15%" style="text-align:right">Amount</td>
						<td width="15%" style="text-align:right">Reference #</td>
						<td width="15%">Deposit Date</td>
						<td width="15%">Check Serial #</td>
						<td width="15%">Check Date</td>
						<td width="15%" style="text-align:right">Status</td>
						<td width="5%" style="text-align:right"></td>
					</tr>
				</thead>
				<s:iterator value="deposits">
					<tr>
						<td><s:property value="customer.organizationName" /></td>
						<td style="text-align:right">
							<div title='<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${amount / defaultCurrency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${airline.currency.currencySymbol}" value="${amount}" maxFractionDigits="2" minFractionDigits="2"/></div>
						</td>
						<td style="text-align:right"><s:property value="referenceNumber" /></td>
						<td><s:date name="depositDate" format="dd/MM/yyyy" /></td>
						<td style="text-align:right"><s:property value="checkSerialNumber" /></td>
						<td><s:date name="checkDate" format="dd/MM/yyyy" /></td>
						<td style="text-align:right"><s:property value="depositStatus" /></td>
						<td style="text-align:right">
							<s:url var="selUrl" action="cashDepositRecord!select" namespace="/operator/ajax">
								<s:param name="cashDepositId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" targets="cashDepositFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>