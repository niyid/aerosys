<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<security:authorize access="hasAnyRole('ROLE_ADMIN')">
	<div class="panel panel-primary">
		<div class="panel-heading">New Route Price</div>
		<div class="panel-body">
			<s:form id="routePriceRecordForm" action="popupNewRoutePrice!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="clientId" />
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
					name="currencyCode" 
					list="currencyLov"
					listKey="currencyCode"
					listValue="currencyName"
					emptyOption="false"
					label="Currency" 
					required="true"
				/>
				<s:textfield label="Rate" name="entity.currencyRate" required="true" />
				<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice.economyTravelName} Fare" name="entity.airFareEconomy" required="true" />
				<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice.businessTravelName} Fare" name="entity.airFareBizClass" required="true" />
				<s:textfield label="%{@com.vasworks.airliner.model.RoutePrice.firstTravelName} Fare" name="entity.airFareFirstClass" required="true" />
				
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="priceListDialog" onCompleteTopics="reloadCustomerRecordDiv" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>	
	</security:authorize>
</div>
<s:include value="/common/msg.jsp" />	