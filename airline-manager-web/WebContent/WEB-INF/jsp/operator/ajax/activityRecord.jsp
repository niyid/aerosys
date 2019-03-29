<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="activityId == null">Add</s:if><s:else>Edit</s:else> Activity</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
				<s:form id="activityRecordForm" action="activityRecord!save" namespace="/operator/ajax" theme="xhtml" method="post">
					<s:hidden name="relatedFlightId" />
					<sj:datepicker name="entity.startTime" displayFormat="dd/mm/yy" timepicker="true" timepickerFormat="hh:mm tt" label="Start" readonly="true" />
					<sj:datepicker name="entity.endTime" displayFormat="dd/mm/yy" timepicker="true" timepickerFormat="hh:mm tt" label="End" readonly="true" />
					<s:select
						name="entity.activityType" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="activityTypeLov"
						label="Activity Type"
						emptyOption="false"
						required="true"
						disabled="true"
					/>
					<s:select
						name="selCrewMemberIds" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="id"
						listValue="fullName"
						list="crewMemberLov"
						label="Crew Members"
						emptyOption="false"
						required="true"
						multiple="true"
						disabled="true"
					/>					
					<s:textarea name="entity.description" label="Description" cols="30" rows="5" readonly="true" />					
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="activityFormDiv" onCompleteTopics="refreshActivityListDiv" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>			
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<s:form id="activityRecordForm" action="activityRecord" namespace="/operator/ajax" theme="xhtml" method="post">
					<s:hidden name="relatedFlightId" />
					<sj:datepicker name="entity.startTime" displayFormat="dd/mm/yy" timepicker="true" timepickerFormat="hh:mm tt" label="Start" />
					<sj:datepicker name="entity.endTime" displayFormat="dd/mm/yy" timepicker="true" timepickerFormat="hh:mm tt" label="End" />
					<s:select
						name="entity.activityType" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="activityTypeLov"
						label="Activity Type"
						emptyOption="false"
						required="true"
					/>
					<s:select
						name="selCrewMemberIds" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="id"
						listValue="fullName"
						list="crewMemberLov"
						label="Crew Members"
						emptyOption="false"
						required="true"
						multiple="true"
					/>					
					<s:textarea name="entity.description" label="Description" cols="30" rows="5" />					
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="activityFormDiv" onCompleteTopics="refreshActivityListDiv" effect="highlight" effectDuration="500" />
				</s:form>			
			</security:authorize>		
		</div>
	</div>
</div>

	<s:include value="/common/msg.jsp" />
