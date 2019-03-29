<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="customerId == null">Add</s:if><s:else>Edit</s:else> Customer
			<span align="right">
				<s:if test="customerId != null">
					<s:url var="newRecordUrl" action="customerRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="customerFormDiv"><span class="glyphicon glyphicon-plus"></span>New Customer</sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				<s:form id="customerRecordForm" action="customerRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="customerId" />
					<s:hidden name="entity.contactInfo.id" />
					<s:hidden name="entity.address.id" />
					<s:select 
						name="entity.customerType" 
						headerKey=""
						headerValue="----------Select----------"
						list="customerTypeLov"
						listKey="name()"
						listValue="name()"
						label="Customer Type"
						emptyOption="false"
						required="true"
						disabled="true"
					/>																													
					<s:select 
						name="entity.cycleRange" 
						headerKey=""
						headerValue="----------Select----------"
						list="billingCycleLov"
						listKey="name()"
						listValue="name()"
						label="Billing Cycle"
						emptyOption="false"
						required="false"
						disabled="true"
					/>																													
					<s:select 
						name="defaultAirlineId" 
						headerKey=""
						headerValue="----------Select----------"
						list="airlineLov"
						listKey="id"
						listValue="organizationName"
						label="Default Airline"
						emptyOption="false"
						required="false"
						disabled="true"
					/>																													
					<s:textfield label="Organization Name" name="entity.organizationName" required="true" disabled="true" />
					<s:textfield label="Primary Phone" name="entity.contactInfo.primaryPhone" required="true" disabled="true" />
					<s:textfield label="Primary Email" name="entity.contactInfo.primaryEmail" required="true" disabled="true" />
					<s:textfield label="Secondary Phone" name="entity.contactInfo.secondaryPhone" disabled="true" />
					<s:textfield label="Secondary Email" name="entity.contactInfo.secondaryEmail" disabled="true" />
					<s:textfield label="Line 1" name="entity.address.addressLine1" required="true" disabled="true" />
					<s:textfield label="Line 2" name="entity.address.addressLine2" disabled="true" />
					<s:textfield label="City" name="entity.address.city" required="true" disabled="true" />
					<s:textfield label="State" name="entity.address.state" required="true" disabled="true" />
					<s:select
						name="countryCode" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="countryCode"
						listValue="countryName"
						list="countryLov"
						label="Country"
						emptyOption="false"
						required="true"
						disabled="true"
					/>			

					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="customerFormDiv" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
				<s:form id="customerRecordForm" action="customerRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="customerId" />
					<s:hidden name="entity.contactInfo.id" />
					<s:hidden name="entity.address.id" />
					<s:select 
						name="entity.customerType" 
						headerKey=""
						headerValue="----------Select----------"
						list="customerTypeLov"
						listKey="name()"
						listValue="name()"
						label="Customer Type"
						emptyOption="false"
						required="true"
					/>																													
					<s:select 
						name="entity.cycleRange" 
						headerKey=""
						headerValue="----------Select----------"
						list="billingCycleLov"
						listKey="name()"
						listValue="name()"
						label="Billing Cycle"
						emptyOption="false"
						required="false"
					/>																													
					<s:select 
						name="defaultAirlineId" 
						headerKey=""
						headerValue="----------Select----------"
						list="airlineLov"
						listKey="id"
						listValue="organizationName"
						label="Default Airline"
						emptyOption="false"
						required="false"
					/>																													
					<s:textfield label="Organization Name" name="entity.organizationName" required="true" />
					<s:textfield label="Primary Phone" name="entity.contactInfo.primaryPhone" required="true" />
					<s:textfield label="Primary Email" name="entity.contactInfo.primaryEmail" required="true" />
					<s:textfield label="Secondary Phone" name="entity.contactInfo.secondaryPhone" />
					<s:textfield label="Secondary Email" name="entity.contactInfo.secondaryEmail" />
					<s:textfield label="Line 1" name="entity.address.addressLine1" required="true" />
					<s:textfield label="Line 2" name="entity.address.addressLine2" />
					<s:textfield label="City" name="entity.address.city" required="true" />
					<s:textfield label="State" name="entity.address.state" required="true" />
					<s:select
						name="countryCode" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="countryCode"
						listValue="countryName"
						list="countryLov"
						label="Country"
						emptyOption="false"
						required="true"
					/>			

					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="customerFormDiv" effect="highlight" effectDuration="500" />
				</s:form>
			</security:authorize>
			<s:if test="entity.customerType == @com.vasworks.airliner.model.Customer$CustomerType@PRE_ETL_POSTPAID">
				<s:include value="customerPriceList.jsp" />		
			</s:if>
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	