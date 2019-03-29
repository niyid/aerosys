<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_FINANCE')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="rebateId == null">Add</s:if><s:else>Edit</s:else> Rebate
			<span align="right">
				<s:if test="rebateId != null">
					<s:url var="newRecordUrl" action="rebateRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="rebateFormDiv"><span class="glyphicon glyphicon-eye-open">New Rebate</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="rebateRecordForm" action="rebateRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="rebateId" />

				<s:textfield label="Rate Code" name="rateCode" required="true" />
				<s:textfield label="Rate Percent" name="entity.ratePercentage" />
				<s:textfield label="Fixed Rate" name="entity.fixedRate" />
				<s:checkbox label="Auto-added" name="entity.autoAdded" />
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="rebateFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshRebateListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-eye-open"></span>Viewing Rebate
		</div>
		<div class="panel-body">
			<s:form id="rebateRecordForm" action="rebateRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="rebateId" />

				<s:textfield label="Rate Percent" name="entity.ratePercentage" disabled="true" />
				<s:textfield label="Fixed Rate" name="entity.fixedRate" disabled="true" />
				<s:checkbox label="Auto-added" name="entity.autoAdded" disabled="true" />
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" disabled="true" />
			
				<sj:submit disabled="true" button="true" value="Save" buttonIcon="ui-icon-gear" targets="rebateFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshRebateListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
</div>

<s:include value="/common/msg.jsp" />	