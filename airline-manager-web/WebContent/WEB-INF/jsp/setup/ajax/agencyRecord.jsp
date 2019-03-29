<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="agencyId == null">Add</s:if><s:else>Edit</s:else> Agency
			<span align="right">
				<s:if test="agencyId != null">
					<s:url var="newRecordUrl" action="agencyRecord!initNew" namespace="/setup/ajax" />
					<s:url var="agencyEmployeeUrl" action="showAgencyEmployee" namespace="/setup" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="agencyFormDiv"><span class="glyphicon glyphicon-eye-open">New Agency</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{agencyEmployeeUrl}" openDialog="agencyEmployeeDialog"><span class="glyphicon glyphicon-eye-open">Employees</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="agencyRecordForm" action="agencyRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="agencyId" />
				<s:hidden name="entity.contactInfo.id" />
				<s:hidden name="entity.address.id" />
						<s:textfield label="Organization Name" name="entity.organizationName" required="true" />
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

				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="agencyFormDiv" onCompleteTopics="refreshAgencyListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	