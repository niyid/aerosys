<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_FINANCE')">
	<div class="panel panel-primary">
		<div class="panel-heading"><s:if test="routeId == null">Add</s:if><s:else>Edit</s:else> Route Price
			<span align="right">
				<s:if test="routeId != null and clientId != null and currencyCode != null">
					<s:url var="newPriceRecordUrl" action="routePriceRecord!initNew" namespace="/operator/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newPriceRecordUrl}" targets="routePriceFormDiv"><span class="glyphicon glyphicon-eye-open"></span>New Route Price</sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="routePriceRecordForm" action="routePriceRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:select 
					headerKey=""
					headerValue="----------Select-----------"
					name="routeId" 
					list="routeLov"
					listKey="id"
					listValue="description"
					emptyOption="false"
					label="Route" 
					required="true"
				/>
				<s:select 
					headerKey=""
					headerValue="----------Select-----------"
					name="clientId" 
					list="organizationLov"
					listKey="id"
					listValue="organizationName"
					emptyOption="false"
					label="Organization" 
					required="true"
				/>
				<s:select 
					headerKey=""
					headerValue="----------Select-----------"
					name="currencyCode" 
					list="currencyLov"
					listKey="currencyCode"
					listValue="currencyName"
					emptyOption="false"
					label="Currency" 
					required="true"
				/>
				<s:textfield label="Rate" name="entity.currencyRate" required="true" />
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasEconomy">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@economyTravelName} Fare" name="entity.airFareEconomy" required="true" />
				</s:if>
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasBusiness">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@businessTravelName} Fare" name="entity.airFareBizClass" required="true" />
				</s:if>
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasFirst">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@firstTravelName} Fare" name="entity.airFareFirstClass" required="true" />
				</s:if>
				
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="routePriceFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshRoutePriceListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>	
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-eye-open"></span>Viewing Route Price
		</div>
		<div class="panel-body">
			<s:form id="routePriceRecordForm" action="routePriceRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:select 
					name="routeId" 
					list="routeLov"
					listKey="id"
					listValue="description"
					emptyOption="false"
					label="Route" 
					required="true"
					disabled="true"
				/>
				<s:select 
					name="organizationId" 
					list="organizationLov"
					listKey="id"
					listValue="organizationName"
					emptyOption="false"
					label="Organization" 
					required="true"
					disabled="true"
				/>
				<s:select 
					name="currencyCode" 
					list="currencyLov"
					listKey="currencyCode"
					listValue="currencyName"
					emptyOption="false"
					label="Currency" 
					required="true"
					disabled="true"
				/>
				<s:textfield label="Rate" name="entity.currencyRate" required="true" disabled="true" />
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasEconomy">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@economyTravelName} Fare" name="entity.airFareEconomy" required="true" disabled="true" />
				</s:if>
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasBusiness">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@businessTravelName} Fare" name="entity.airFareBizClass" required="true" disabled="true" />
				</s:if>
				<s:if test="@com.vasworks.airliner.model.RoutePrice@hasFirst">
					<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice@firstTravelName} Fare" name="entity.airFareFirstClass" required="true" disabled="true" />
				</s:if>
			
				<sj:submit disabled="true" button="true" value="Save" buttonIcon="ui-icon-gear" targets="routePriceFormDiv" onClickTopics="blockUIOn" onCompleteTopics="refreshRoutePriceListDiv,blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>	
	</security:authorize>
</div>
<s:include value="/common/msg.jsp" />	