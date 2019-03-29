<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_FINANCE')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="taxId == null">Add</s:if><s:else>Edit</s:else> Custom Tax</div>
			<span align="right">
				<s:if test="taxId != null">
					<s:url var="newRecordUrl" action="contextTaxRecord!initNew" namespace="/operator/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="taxFormDiv"><span class="glyphicon glyphicon-eye-open">New Tax</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body" id="bookingListInnerDiv">
			<s:form id="contextTaxRecordForm" action="contextTaxRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="taxId" />
				<s:select 
					name="clientId" 
					headerKey=""
					headerValue="----------Select----------"
					list="customerLov"
					listKey="id"
					listValue="organizationName"
					emptyOption="false"
					required="true"
				/>																													
				<s:textfield label="Rate Code" name="rateCode" required="true" />
				<s:textfield label="Rate Percent" name="entity.ratePercentage" />
				<s:textfield label="Fixed Rate" name="entity.fixedRate" />
				<s:checkbox label="Auto-added" name="entity.autoAdded" />
				<s:checkbox label="Regional Flight?" name="entity.regional" />
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="contextTaxFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshContextTaxListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>	
		</div>
	</div>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-eye-open"></span>Viewing Custom Tax
		</div>
		<div class="panel-body" id="contextTaxFormInnerDiv">
			<s:form id="contextTaxRecordForm" action="contextTaxRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="taxId" />
				<s:select 
					name="clientId" 
					headerKey=""
					headerValue="----------Select----------"
					list="customerLov"
					listKey="id"
					listValue="organizationName"
					emptyOption="false"
					required="true"
				/>																													
				<s:textfield label="Rate Percent" name="entity.ratePercentage" disabled="true" />
				<s:textfield label="Fixed Rate" name="entity.fixedRate" disabled="true" />
				<s:checkbox label="Auto-added" name="entity.autoAdded" disabled="true" />
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" disabled="true" />
			
				<sj:submit disabled="true" button="true" value="Save" buttonIcon="ui-icon-gear" targets="contextTaxFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshContextTaxListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>	
		</div>
	</div>
	</security:authorize>
</div>

<s:include value="/common/msg.jsp" />	