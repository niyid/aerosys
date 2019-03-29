<%@ include file="/common/taglibs.jsp"%>

	<table>
		<tr>
			<td class="windowHeader"><div><s:if test="airlineId == null">Add</s:if><s:else>Edit</s:else> Airline</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
			<div>
				<div align="right">
					<s:if test="airlineId != null">
						<s:url var="newRecordUrl" action="airlineRecord!initNew" namespace="/setup/ajax" />
						<sj:a button="false" buttonIcon="" href="%{newRecordUrl}" targets="airlineFormDiv">New Airline</sj:a>
					</s:if>
				</div>
				
				<s:form id="airlineRecordForm" action="airlineRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="airlineId" />
					<s:hidden name="entity.contactInfo.id" />
					<s:hidden name="entity.address.id" />
							<s:textfield label="Organization Name" name="entity.organizationName" cssStyle="width: 200px;" required="true" />
							<s:textfield label="Airline Code" name="entity.airlineCode" cssStyle="width: 200px;" required="true" />
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
							<s:textfield label="Primary Phone" name="entity.contactInfo.primaryPhone" cssStyle="width: 200px;" required="true" />
							<s:textfield label="Primary Email" name="entity.contactInfo.primaryEmail" cssStyle="width: 200px;" required="true" />
							<s:textfield label="Secondary Phone" name="entity.contactInfo.secondaryPhone" cssStyle="width: 200px;" />
							<s:textfield label="Secondary Email" name="entity.contactInfo.secondaryEmail" cssStyle="width: 200px;" />
							<s:textfield label="Line 1" name="entity.address.addressLine1" cssStyle="width: 300px;" required="true" />
							<s:textfield label="Line 2" name="entity.address.addressLine2" cssStyle="width: 300px;" />
							<s:textfield label="City" name="entity.address.city" cssStyle="width: 200px;" required="true" />
							<s:textfield label="State" name="entity.address.state" cssStyle="width: 200px;" required="true" />
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

					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airlineFormDiv" onCompleteTopics="refreshAirlineListDiv" effect="highlight" effectDuration="500" indicator="indicator" />
				</s:form>
			</div>
			</td>
		</tr>		
	</table>

<s:include value="/common/msg.jsp" />	