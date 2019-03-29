<%@ include file="/common/taglibs.jsp"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="modelName == null">Add</s:if><s:else>Edit</s:else> Airplane Model
			<span align="right">
				<s:if test="modelName != null">
					<s:url var="newRecordUrl" action="airplaneModelRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="airplaneModelFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">New Airplane Model</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="airplaneModelRecordForm" action="airplaneModelRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="modelName" />
				
				<s:select
					key="makeName" 
					headerKey=""
					headerValue="----------Select-----------"
					list="airplaneMakeLov"
					listKey="makeName"
					listValue="makeName"
					emptyOption="false"
					label="Airplane Make" 
					required="true"
				/>
				
				<s:textfield label="Name" name="entity.modelName" required="true" />
				<s:textfield label="No of Seat Rows" name="entity.numberOfRows" required="true" />
				<s:textfield label="Cross-section" name="entity.crossSection" required="true" title="The letters for each column; '|' for isle. Example: AB|CD" />
				<s:textfield label="Weight (kgs)" name="entity.airplaneWeight" required="true" />
				<s:textfield label="Luggage Capacity (kgs)" name="entity.luggageCapacity" required="true" />
				<s:textfield label="Exit Rows" name="entity.exitRows" required="true" title="Comma-separated list of rows. Example: 1,2,3,4" />
				<s:textfield label="Biz-class Rows" name="entity.bizClassRows" required="false" title="Comma-separated list of rows. Example: 1,2,3,4" />
				<s:textfield label="First-class Rows" name="entity.fstClassRows" required="false" title="Comma-separated list of rows. Example: 1,2,3,4" />
				
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneModelFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirplaneModelListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	
</security:authorize>
