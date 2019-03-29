<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="authorityId == null">Add</s:if><s:else>Edit</s:else> Authority
			<span align="right">
				<s:if test="authorityId != null">
					<s:url var="newRecordUrl" action="authorityRecord!initNew" namespace="/setup/ajax" />
					<s:url var="authorityEmployeeUrl" action="showAuthorityEmployeePage" namespace="/setup" />		
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="authorityFormDiv"><span class="glyphicon glyphicon-eye-open">New Authority</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{authorityEmployeeUrl}" openDialog="employeeDialog"><span class="glyphicon glyphicon-eye-open"></span>Employees</sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="authorityRecordForm" action="authorityRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="authorityId" />
				<s:hidden name="entity.contactInfo.id" />
				<s:hidden name="entity.address.id" />
						<s:textfield label="Organization Name" name="entity.organizationName" required="true" />
						<s:textfield label="Acryonym" name="entity.authorityAcronym" required="true" />
						<s:select
							name="currencyCode" 
							headerKey=""
							headerValue="----------Select-----------"
							listKey="currencyCode"
							listValue="currencyName"
							list="currencyLov"
							label="Currency"
							emptyOption="false"
							required="true"
						/>			
						<s:textfield label="Primary Phone" name="entity.contactInfo.primaryPhone" required="true" />
						<s:textfield label="Primary Email" name="entity.contactInfo.primaryEmail" required="true" />
						<s:textfield label="Secondary Phone" name="entity.contactInfo.secondaryPhone" />
						<s:textfield label="Secondary Email" name="entity.contactInfo.secondaryEmail" />
						<s:textfield label="Line 1" name="entity.address.addressLine1" cssStyle="width: 300px;" required="true" />
						<s:textfield label="Line 2" name="entity.address.addressLine2" cssStyle="width: 300px;" />
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

				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="authorityFormDiv" onCompleteTopics="refreshAuthorityListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	