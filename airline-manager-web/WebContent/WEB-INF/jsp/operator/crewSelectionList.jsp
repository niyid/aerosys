<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Crew Selection for flight <s:property value="%{flightId.flightNumber}"/> on <s:property value="%{flightId.flightDate}"/></div>
		<div class="panel-body" id="crewSelectionListDiv">
			<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
			<s:form id="crewSelectionForm" action="showCrewSelectionPage!save" namespace="/operator" theme="xhtml">
				<s:checkboxlist
					name="selCrewMemberIds" 
					list="crewMemberLov"
					label="List of available crew members"
					labelposition="top"
					labelSeparator=""
					listKey="id"
					listValue="fullName + ' [' + crewType + ']"
					disabled="true"
				/>

				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="crewSelectionListDiv" effect="highlight" effectDuration="500" disabled="true" />
			</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
			<s:form id="crewSelectionForm" action="showCrewSelectionPage!save" namespace="/operator" theme="xhtml">
				<s:checkboxlist
					name="selCrewMemberIds" 
					list="crewMemberLov"
					label="List of available crew members"
					labelposition="top"
					labelSeparator=""
					listKey="id"
					listValue="fullName + ' [' + crewType + ']"
				/>

				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="crewSelectionListDiv" effect="highlight" effectDuration="500" />
			</s:form>
			</security:authorize>
		
		</div>
	</div>
</div>
