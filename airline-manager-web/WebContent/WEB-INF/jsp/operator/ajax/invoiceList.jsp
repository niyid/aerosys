<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Search Filter</div>
		<div class="panel-body">
			<s:form id="invoiceFilterForm" action="invoiceList" namespace="/operator/ajax" method="post" cssClass="form-inline">
				<s:if test="%{hasAny('ROLE_FINANCE','ROLE_OPERATOR','ROLE_ADMIN')}">
			  	<div class="form-group">
		    		<label for="clientId">Status</label>
					<s:select 
						name="clientId" 
						headerKey=""
						headerValue="----------Select----------"
						list="clientLov"
						listKey="id"
						listValue="organizationName"
						emptyOption="false"
						required="false"
					/>																													
			  	</div>
				</s:if>
			  	<div class="form-group">
			    	<label for="invoiceNumber">Invoice Number</label>
			    	<s:textfield name="invoiceNumber" title="Invoice Number" cssClass="form-control" />
			  	</div>
				<div class="form-group">
			    	<label for="startDate">Start Date:</label>
			    	<sj:datepicker name="startDate" title="Start Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="endDate">End Date:</label>
			    	<sj:datepicker name="endDate" title="End Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="invoiceStatus">Status</label>
					<s:select 
						name="invoiceStatus" 
						headerKey=""
						headerValue="----------Select----------"
						list="invoiceStatusLov"
						listKey="name()"
						listValue="name()"
						emptyOption="false"
						required="true"
					/>																													
			  	</div>
			  	<sj:submit id="searchFilterButton" buttonIcon="ui-icon-gear" button="true" value="Search" targets="invoiceListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">Invoices</div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%">Number</td>
						<td width="10%">Invoice Date</td>
						<td width="10%">Overdue Date</td>
						<td width="10%">Amount</td>
						<td width="10%">Status</td>
						<td width="10%"></td>
					</tr> 
				</thead>
				<s:iterator value="invoices">
					<tr>
						<td><s:property value="id" /></td>
						<td><s:date name="invoiceDate" format="dd/MM/yyyy" /></td>
						<td><s:date name="overdueDate" format="dd/MM/yyyy" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${totalAmount}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td><s:property value="invoiceStatus" /></td>
						<td>
							<s:url var="selUrl" action="popupInvoicePayment" namespace="/" escapeAmp="false">
								<s:param name="invoiceId" value="%{id}" />
							</s:url>						
							<sj:a button="true" href="%{selUrl}" openDialog="invoiceDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-credit-card"></span>Pay</sj:a>						
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>