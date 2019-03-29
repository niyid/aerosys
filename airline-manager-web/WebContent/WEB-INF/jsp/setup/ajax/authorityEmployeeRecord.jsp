<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="personnelId != null">Edit </s:if><s:else>Add </s:else> Authority Employee</div>
		<div class="panel-body">
			<s:form id="authorityEmployeeForm" action="authorityEmployeeRecord!save" validate="true" method="post" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="organizationId" />
				<s:textfield label="First Name" name="personnel.firstName" required="true" />
				<s:textfield label="Last Name" name="personnel.lastName" required="true" />
				<s:textfield label="User Name" name="personnel.userName" required="true" />
				<s:textfield label="E-Mail" name="personnel.mail" required="true" />
				<s:password label="Password" name="personnel.password" required="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" />
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="authorityEmployeeFormDiv" onCompleteTopics="refreshAuthorityEmployeeListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>	
<s:include value="/common/msg.jsp" />	