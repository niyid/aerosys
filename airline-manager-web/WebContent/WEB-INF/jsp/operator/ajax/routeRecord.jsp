<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATIONS')">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="routeId == null">Add</s:if><s:else>Edit</s:else> Route
			<span align="right">
				<s:if test="routeId != null">
					<s:url var="newRecordUrl" action="routeRecord!initNew" namespace="/operator/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="routeFormDiv"><span class="glyphicon glyphicon-eye-open">New Route</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="routeRecordForm" action="routeRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
				
				<s:if test="!(airportLov == null || airportLov.isEmpty())">
					<s:select 
						name="selAirportCodes" 
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Select two (2) Airports" 
						required="true"
						multiple="true"
					/>
				</s:if>	

				<s:if test="!(taxLov == null || taxLov.isEmpty())">
					<s:select 
						name="selTaxIds" 
						list="taxLov"
						listKey="id"
						listValue="description"
						emptyOption="false"
						label="Select from Available Taxes/Charges" 
						required="true"
						multiple="true"
					/>
				</s:if>
				
				<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
					<s:select 
						name="selRebateIds" 
						list="rebateLov"
						listKey="id"
						listValue="description"
						emptyOption="false"
						label="Select from Available Rebates" 
						required="true"
						multiple="true"
					/>
				</s:if>	
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="routeFormDiv" onCompleteTopics="refreshRouteListDiv" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-eye-open"></span>Viewing Route
		</div>
		<div class="panel-body">
			<s:form id="routeRecordForm" action="routeRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" disabled="true" />
				
				<s:if test="!(airportLov == null || airportLov.isEmpty())">
					<s:select 
						name="selAirportCodes" 
						list="airportLov"
						listKey="airportCode"
						listValue="airportName"
						emptyOption="false"
						label="Select two (2) Airports" 
						required="true"
						multiple="true"
						disabled="true"
					/>
				</s:if>	

				<s:if test="!(taxLov == null || taxLov.isEmpty())">
					<s:select 
						name="selTaxIds" 
						list="taxLov"
						listKey="id"
						listValue="description"
						emptyOption="false"
						label="Select from Available Taxes/Charges" 
						required="true"
						multiple="true"
						disabled="true"
					/>
				</s:if>
				
				<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
					<s:select 
						name="selRebateIds" 
						list="rebateLov"
						listKey="id"
						listValue="description"
						emptyOption="false"
						label="Select from Available Rebates" 
						required="true"
						multiple="true"
						disabled="true"
					/>
				</s:if>	
			
				<sj:submit disabled="true" button="true" value="Save" buttonIcon="ui-icon-gear" targets="routeFormDiv" onCompleteTopics="refreshRouteListDiv" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
	</security:authorize>
</div>
<s:include value="/common/msg.jsp" />	