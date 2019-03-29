<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="trainingId == null">Add</s:if><s:else>Edit</s:else> Training</div>
		<div class="panel-body">
			<div align="right">
				<s:if test="trainingId != null">
					<s:url var="newRecordUrl" action="trainingRecord!initNew" namespace="/setup/ajax" />
					<sj:a button="false" buttonIcon="" href="%{newRecordUrl}" targets="trainingFormDiv">New Training</sj:a>
				</s:if>
			</div>
				
			<s:form id="trainingRecordForm" action="trainingRecord!save" namespace="/setup/ajax" theme="xhtml" enctype="multipart/form-data">
				<s:hidden name="trainingId" />
				<s:hidden name="crewMemberId" />
				<sj:datepicker label="Completion Date" name="entity.completionDate" displayFormat="dd/mm/yy" required="true" />
				<s:select
					name="entity.trainingType" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="name()"
					listValue="description"
					list="trainingTypeLov"
					label="Training Type"
					emptyOption="false"
					required="true"
				/>					
				<s:select
					name="trainingHandlerId" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="id"
					listValue="handlerName"
					list="trainerLov"
					label="Trainer"
					emptyOption="false"
					required="true"
				/>
				<s:file label="Select File" name="uploadedFile" required="true" />						
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="trainingFormDiv" onCompleteTopics="refreshTrainerListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	