<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Update Seat</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
				<s:form id="seatRecordForm" action="seatRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="rowNumber" />
					<s:hidden name="columnId" />
					<s:hidden name="regNumber" />
					<s:select
						name="entity.seatStatus" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatStatusLov"
						label="Status"
						emptyOption="false"
						required="true"
						disabled="true"
					/>
					<s:select
						name="entity.seatClass" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatClassLov"
						label="Class"
						emptyOption="false"
						required="true"
						disabled="true"
					/>
					<s:checkbox name="entity.exitRow" label="Exit Row?" disabled="true" />
					<s:if test="rowNumber != null && columnId != null && regNumber != null">
						<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="seatFormDiv" onCompleteTopics="refreshSeatListDiv" effect="highlight" effectDuration="500" disabled="true" />							
					</s:if>
				</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<s:form id="seatRecordForm" action="seatRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="rowNumber" />
					<s:hidden name="columnId" />
					<s:hidden name="regNumber" />
					<s:select
						name="entity.seatStatus" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatStatusLov"
						label="Status"
						emptyOption="false"
						required="true"
					/>
					<s:select
						name="entity.seatClass" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatClassLov"
						label="Class"
						emptyOption="false"
						required="true"
					/>
					<s:checkbox name="entity.exitRow" label="Exit Row?" />
					<s:if test="rowNumber != null && columnId != null && regNumber != null">
						<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="seatFormDiv" onCompleteTopics="refreshSeatListDiv" effect="highlight" effectDuration="500" />							
					</s:if>
				</s:form>
			</security:authorize>		
		</div>
	</div>
</div>
<s:include value="/common/msg.jsp" />	