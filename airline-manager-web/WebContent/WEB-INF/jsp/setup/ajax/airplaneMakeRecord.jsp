<%@ include file="/common/taglibs.jsp"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="makeName == null">Add</s:if><s:else>Edit</s:else> Airplane Make
			<span align="right">
				<s:if test="makeName != null">
					<s:url var="newRecordUrl" action="airplaneMakeRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="airplaneMakeFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">New Airplane Make</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="airplaneMakeRecordForm" action="airplaneMakeRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="makeName" />

				<s:textfield label="Name" name="entity.makeName" required="true" />
				
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneMakeFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirplaneMakeListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	
</security:authorize>
