<%@ include file="/common/taglibs.jsp"%>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="airportCode == null">Add</s:if><s:else>Edit</s:else> Airport
			<span align="right">
				<s:if test="airportCode != null">
					<s:url var="newRecordUrl" action="airportRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="airportFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-eye-open">New Airport</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="airportRecordForm" action="airportRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="airportCode" />
				<s:textfield label="Name" name="entity.airportName" required="true" />
				<s:textfield label="Code" name="entity.airportCode" required="true" />
				<s:select
					name="cityId" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="id"
					listValue="cityName"
					list="cityLov"
					label="City"
					emptyOption="false"
					required="true"
				/>			
				
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airportFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshAirportListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>
<s:include value="/common/msg.jsp" />	
</security:authorize>
