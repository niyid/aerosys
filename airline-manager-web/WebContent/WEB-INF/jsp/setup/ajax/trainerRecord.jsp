<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="trainerId == null">Add</s:if><s:else>Edit</s:else> Trainer
			<span align="right">
				<s:if test="trainerId != null">
					<s:url var="newRecordUrl" action="trainerRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="trainerFormDiv"><span class="glyphicon glyphicon-eye-open">New Trainer</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:if test="entity instanceof com.vasworks.airliner.model.TrainingSupervisor">
				<s:include value="trainingSupervisorRecord.jsp" />
			</s:if>
			<s:if test="entity instanceof com.vasworks.airliner.model.TrainingPilot">
				<s:include value="trainingPilotRecord.jsp" />
			</s:if>
			<s:if test="entity instanceof com.vasworks.airliner.model.TrainingDoctor">
				<s:include value="trainingDoctorRecord.jsp" />
			</s:if>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	