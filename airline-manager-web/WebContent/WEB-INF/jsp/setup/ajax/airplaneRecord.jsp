<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="regNumber == null">Add</s:if><s:else>Edit</s:else> Aircraft
			<div align="right">
				<s:if test="regNumber != null">
					<s:url var="newRecordUrl" action="airplaneRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" targets="airplaneFormDiv"><span class="glyphicon glyphicon-eye-open"></span>New Airplane</sj:a>
				</s:if>
			</div>
		</div>
		<div class="panel-body">
			<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				<s:form id="airplaneRecordForm" action="airplaneRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="regNumber" />
					<s:textfield label="Registration" name="entity.regNumber" required="true" readonly="true" />
					<s:textfield label="Serial Number" name="entity.serialNumber" required="true" readonly="true" />
					<s:textfield label="Owner" name="entity.ownerName" required="true" readonly="true" />
					<sj:datepicker label="Manufacture Date" name="entity.manufactureDate" displayFormat="dd/mm/yy" required="true" disabled="true" />
					<sj:datepicker label="Arrived in Country" name="entity.arrivalDate" displayFormat="dd/mm/yy" required="true" disabled="true" />
					<s:select
						name="entity.operationType" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="operationTypeLov"
						label="Operation Type"
						emptyOption="false"
						required="true"
						disabled="true"
					/>			
					<s:select
						name="makeName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="makeName"
						listValue="makeName"
						list="airplaneMakeLov"
						label="Make"
						emptyOption="false"
						required="true"
						onchange="$.publish('makeChangeTopic')"
						disabled="true"
					/>			
					<s:select
						name="modelName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="modelName"
						listValue="modelName"
						list="airplaneModelLov"
						label="Model"
						emptyOption="false"
						required="true"
						disabled="true"
					/>			
					
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneFormDiv" onCompleteTopics="refreshAirplaneListDiv" listenTopics="makeChangeTopic" effect="highlight" effectDuration="500" disabled="true" />
				</s:form>
			</security:authorize>
			<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				<s:form id="airplaneRecordForm" action="airplaneRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="regNumber" />
					<s:textfield label="Registration" name="entity.regNumber" required="true" />
					<s:textfield label="Serial Number" name="entity.serialNumber" required="true" />
					<s:textfield label="Owner" name="entity.ownerName" required="true" />
					<sj:datepicker label="Manufacture Date" name="entity.manufactureDate" displayFormat="dd/mm/yy" required="true" />
					<sj:datepicker label="Arrived in Country" name="entity.arrivalDate" displayFormat="dd/mm/yy" required="true" />
					<s:select
						name="entity.operationType" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="operationTypeLov"
						label="Operation Type"
						emptyOption="false"
						required="true"
					/>			
					<s:select
						name="makeName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="makeName"
						listValue="makeName"
						list="airplaneMakeLov"
						label="Make"
						emptyOption="false"
						required="true"
						onchange="$.publish('makeChangeTopic')"
					/>			
					<s:select
						name="modelName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="modelName"
						listValue="modelName"
						list="airplaneModelLov"
						label="Model"
						emptyOption="false"
						required="true"
					/>			
					
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirplaneListDiv,blockUIOff" listenTopics="makeChangeTopic" effect="highlight" effectDuration="500" />
				</s:form>
			</security:authorize>		
		</div>
	</div>
</div>
<s:include value="/common/msg.jsp" />	