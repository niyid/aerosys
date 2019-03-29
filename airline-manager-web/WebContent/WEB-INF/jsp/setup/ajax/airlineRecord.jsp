<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="airlineId == null">Add</s:if><s:else>Edit</s:else> Aircraft Owner
			<div align="right">
				<s:if test="airlineId != null">
					<s:url var="newRecordUrl" action="airlineRecord!initNew" namespace="/setup/ajax" />
					<s:url var="airlineEmployeeUrl" action="showAirlineEmployeePage" namespace="/setup">
						<s:param name="organizationId" value="%{airlineId}" />
					</s:url>
					<s:url var="crewMemberUrl" action="showCrewMemberPage" namespace="/setup">
						<s:param name="airlineId" value="%{airlineId}" />
					</s:url>
					<s:url var="crewActivityUrl" action="showActivityPage" namespace="/operator">
						<s:param name="airlineId" value="%{airlineId}" />
					</s:url>		
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="airlineFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">New Airline</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{airlineEmployeeUrl}" openDialog="airlineEmployeeDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">Employees</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{crewMemberUrl}" openDialog="airlineCrewMemberDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">Crew Members</span></sj:a>
					<sj:a cssClass="btn btn-primary btn-lg" href="%{crewActivityUrl}" openDialog="activityDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">Crew Activities</span></sj:a>
				</s:if>
			</div>
		</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				<s:form id="airlineRecordForm" action="airlineRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="airlineId" />
					<s:hidden name="entity.contactInfo.id" />
					<s:hidden name="entity.address.id" />
							<s:textfield label="Organization Name" name="entity.organizationName" required="true" readonly="true" />
							<s:textfield label="Airline Code" name="entity.airlineCode" required="true" readonly="true" />
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
								disabled="true"
							/>			
							<s:textfield label="Primary Phone" name="entity.contactInfo.primaryPhone" required="true" readonly="true" />
							<s:textfield label="Primary Email" name="entity.contactInfo.primaryEmail" required="true" readonly="true" />
							<s:textfield label="Secondary Phone" name="entity.contactInfo.secondaryPhone" readonly="true" />
							<s:textfield label="Secondary Email" name="entity.contactInfo.secondaryEmail" readonly="true" />
							<s:textfield label="Line 1" name="entity.address.addressLine1" cssStyle="width: 300px;" required="true" readonly="true" />
							<s:textfield label="Line 2" name="entity.address.addressLine2" cssStyle="width: 300px;" readonly="true" />
							<s:textfield label="City" name="entity.address.city" required="true" readonly="true" />
							<s:textfield label="State" name="entity.address.state" required="true" readonly="true" />
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

					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airlineFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirlineListDiv,blockUIOff" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
				<s:form id="airlineRecordForm" action="airlineRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="airlineId" />
					<s:hidden name="entity.contactInfo.id" />
					<s:hidden name="entity.address.id" />
							<s:textfield label="Organization Name" name="entity.organizationName" required="true" />
							<s:textfield label="Airline Code" name="entity.airlineCode" required="true" />
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

					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airlineFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirlineListDiv,blockUIOff" effect="highlight" effectDuration="500" />
				</s:form>
			</security:authorize>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	