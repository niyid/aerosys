<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>Price-list</title>
	</head>
	<body>
		<div class="panel-group">
			<div class="panel panel-primary">
				<div class="panel-heading">Price-lists</div>
				<div class="panel-body">
					<table class="table table-hover table-striped">
						<thead>
							<tr>
								<td width="10%">Route</td>
								<td width="15%">Organization</td>
								<td width="10%">Currency</td>
								<td width="10%">Rate</td>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasEconomy">
									<td width="15%"><s:property value="@com.vasworks.airliner.model.RoutePrice.economyTravelName" /> Class</td>
								</s:if>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasBusiness">
									<td width="15%"><s:property value="@com.vasworks.airliner.model.RoutePrice.businessTravelName" /> Class</td>
								</s:if>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasFirst">
									<td width="15%"><s:property value="@com.vasworks.airliner.model.RoutePrice.firstTravelName" /> Class</td>
								</s:if>
								<td width="5%"></td>
							</tr> 
						</thead>
						<s:iterator value="routePrices">
							<tr>
								<td><s:property value="route.description" /></td>
								<td><s:property value="organization.organizationName" /></td>
								<td><s:property value="currency.currencyCode" /></td>
								<td><fmt:formatNumber type="currency" currencySymbol="${defaultCurrency.currencySymbol}" value="${currencyRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasEconomy">
									<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></td>
								</s:if>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasBusiness">
									<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
								</s:if>
								<s:if test="@com.vasworks.airliner.model.RoutePrice.hasFirst">
									<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
								</s:if>
								<td>
									<s:url var="selUrl" action="routeRecord!select" namespace="/operator/ajax" escapeAmp="false">
										<s:param name="routeId" value="%{id}" />
									</s:url>
									<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="routeFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
								</td>
							</tr>
						</s:iterator>
					</table>		
				</div>
			</div>
		</div>
	</body>
</html>