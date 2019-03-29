<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="crewMemberId != null">Edit </s:if><s:else>Add </s:else> Crew Member</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
			<s:form id="crewMemberRecordForm" action="crewMemberRecord!save" validate="true" method="post" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="airlineId" />
				<s:textfield label="First Name" name="crewMember.firstName" required="true" readonly="true" />
				<s:textfield label="Last Name" name="crewMember.lastName" required="true" readonly="true" />
				<s:textfield label="User Name" name="crewMember.userName" required="true" readonly="true" />
				<s:textfield label="E-Mail" name="crewMember.mail" required="true" readonly="true" />
				<s:password label="Password" name="crewMember.password" required="true" readonly="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" readonly="true" />
				<sj:datepicker label="Date of Birth" name="crewMember.dob" required="true" displayFormat="dd/mm/yy" readonly="true" />
				<s:select
					name="crewMember.crewType" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="name()"
					listValue="name()"
					list="crewTypeLov"
					label="Designation"
					emptyOption="false"
					required="true"
					disabled="true"
				/>					
				<s:file label="Select Photo" name="employeePhoto" required="true" disabled="true" />					
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="crewMemberRecordFormDiv" onCompleteTopics="refreshCrewMemberListDiv" effect="highlight" effectDuration="500" disabled="true" />
			</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
			<s:form id="crewMemberRecordForm" action="crewMemberRecord!save" validate="true" method="post" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="airlineId" />
				<s:textfield label="First Name" name="crewMember.firstName" required="true" />
				<s:textfield label="Last Name" name="crewMember.lastName" required="true" />
				<s:textfield label="User Name" name="crewMember.userName" required="true" />
				<s:textfield label="E-Mail" name="crewMember.mail" required="true" />
				<s:password label="Password" name="crewMember.password" required="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" />
				<sj:datepicker label="Date of Birth" name="crewMember.dob" required="true" displayFormat="dd/mm/yy" />
				<s:select
					name="crewMember.crewType" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="name()"
					listValue="name()"
					list="crewTypeLov"
					label="Designation"
					emptyOption="false"
					required="true"
				/>					
				<s:file label="Select Photo" name="employeePhoto" required="true" />					
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="crewMemberRecordFormDiv" onCompleteTopics="refreshCrewMemberListDiv" effect="highlight" effectDuration="500" />
			</s:form>
			</security:authorize>		
		</div>
	</div>
</div>	
<s:include value="/common/msg.jsp" />	