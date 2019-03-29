<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="cityId == null">Add</s:if><s:else>Edit</s:else> City
			<span align="right">
				<s:if test="cityId != null">
					<s:url var="newRecordUrl" action="cityRecord!initNew" namespace="/setup/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="cityFormDiv"><span class="glyphicon glyphicon-eye-open">New City</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="cityRecordForm" action="cityRecord!save" namespace="/setup/ajax" theme="xhtml">
				<s:hidden name="cityId" />

				<s:textfield label="Name" name="entity.cityName" required="true" />
				
				<s:select
					key="countryCode" 
					headerKey=""
					headerValue="----------Select-----------"
					list="countryLov"
					listKey="countryCode"
					listValue="countryName"
					emptyOption="false"
					label="Country" 
					required="true"
					onchange="$.publish('countryChangeTopic')"
				/>
				
				<s:select
					key="countryStateId" 
					headerKey=""
					headerValue="----------Select-----------"
					list="stateLov"
					listKey="id"
					listValue="stateName"
					emptyOption="false"
					label="State" 
					required="true"
				/>
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="cityFormDiv" onCompleteTopics="refreshCityListDiv" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	