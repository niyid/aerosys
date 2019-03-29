<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="personnelId != null">Edit </s:if><s:else>Add </s:else> Airline Employee</div>
		<div class="panel-body">
		<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
			<s:form id="airlineEmployeeForm" action="airlineEmployeeRecord!save" validate="true" method="post" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="organizationId" />
				<s:textfield label="First Name" name="personnel.firstName" required="true" readonly="true" />
				<s:textfield label="Last Name" name="personnel.lastName" required="true" readonly="true" />
				<s:textfield label="User Name" name="personnel.userName" required="true" readonly="true" />
				<s:textfield label="E-Mail" name="personnel.mail" required="true" readonly="true" />
				<s:password label="Password" name="personnel.password" required="true" readonly="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" readonly="true" />
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airlineEmployeeFormDiv" onCompleteTopics="refreshAirlineEmployeeListDiv" effect="highlight" effectDuration="500" disabled="true" />
			</s:form>
		</security:authorize>
		<security:authorize access="hasAnyRole('ROLE_ADMIN')">
			<s:form id="airlineEmployeeForm" action="airlineEmployeeRecord!save" validate="true" method="post" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="organizationId" />
				<s:textfield label="First Name" name="personnel.firstName" required="true" />
				<s:textfield label="Last Name" name="personnel.lastName" required="true" />
				<s:textfield label="User Name" name="personnel.userName" required="true" />
				<s:textfield label="E-Mail" name="personnel.mail" required="true" />
				<s:password label="Password" name="personnel.password" required="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" />
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airlineEmployeeFormDiv" onCompleteTopics="refreshAirlineEmployeeListDiv" effect="highlight" effectDuration="500" />
			</s:form>
		</security:authorize>		
		</div>
	</div>
</div>	
<s:include value="/common/msg.jsp" />	