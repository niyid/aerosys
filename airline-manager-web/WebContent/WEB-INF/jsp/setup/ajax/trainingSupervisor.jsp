<%@ include file="/common/taglibs.jsp"%>

<s:form id="trainerRecordForm" action="trainerRecord!saveSupervisor" namespace="/setup/ajax" theme="xhtml">
	<s:hidden name="trainerId" />
	<s:textfield label="Name" name="entity.handlerName" required="true" />
			
	<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="trainerFormDiv" onCompleteTopics="refreshTrainerListDiv" effect="highlight" effectDuration="500" />
</s:form>