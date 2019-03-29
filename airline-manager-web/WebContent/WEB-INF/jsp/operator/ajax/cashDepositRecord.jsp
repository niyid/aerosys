<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_FINANCE')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="depositId == null">Add</s:if><s:else>Edit</s:else> Cash Deposit
			<span align="right">
				<s:if test="depositId != null">
					<s:url var="newRecordUrl" action="cashDepositRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="cashDepositFormDiv"><span class="glyphicon glyphicon-eye-open">New CashDeposit</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="cashDepositRecordForm" action="cashDepositRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="depositId" />

				<s:textfield label="Amount" name="entity.amount" required="true" disabled="false" />
				<s:textfield label="Reference #" name="entity.referenceNumber" required="true" disabled="false" />
				<s:textfield label="Check Serial #" name="entity.checkSerialNumber" disabled="false" />
				<sj:datepicker label="Check Date" name="entity.checkDate" displayFormat="dd/mm/yy" disabled="false" />
				<s:select
					name="entity.depositStatus" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="name()"
					listValue="name()"
					list="depositStatusLov"
					label="Status"
					emptyOption="false"
					required="true"
					disabled="false"
				/>						
				<s:select 
					name="clientId" 
					headerKey=""
					headerValue="----------Select----------"
					list="depositCustomerLov"
					listKey="id"
					listValue="organizationName"
					label="Customer"
					emptyOption="false"
					required="true"
					disabled="false"
				/>																													
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="cashDepositFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshCashDepositListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-eye-open"></span>Viewing Cash Deposit
		</div>
		<div class="panel-body">
			<s:form id="cashDepositRecordForm" action="cashDepositRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="depositId" />

				<s:textfield label="Amount" name="entity.amount" required="true" disabled="true" />
				<s:textfield label="Reference #" name="entity.referenceNumber" required="true" disabled="true" />
				<s:textfield label="Check Serial #" name="entity.checkSerialNumber" disabled="true" />
				<sj:datepicker label="Check Date" name="entity.checkDate" displayFormat="dd/mm/yy" disabled="true" />
				<s:select
					name="entity.depositStatus" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="name()"
					listValue="name()"
					list="depositStatusLov"
					label="Status"
					emptyOption="false"
					required="true"
					disabled="true"
				/>						
				<s:select 
					name="clientId" 
					headerKey=""
					headerValue="----------Select----------"
					list="depositCustomerLov"
					listKey="id"
					listValue="organizationName"
					label="Customer"
					emptyOption="false"
					required="true"
					disabled="true"
				/>																													
			
				<sj:submit disabled="true" button="true" value="Save" buttonIcon="ui-icon-gear" targets="cashDepositFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshCashDepositListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
</div>

<s:include value="/common/msg.jsp" />	