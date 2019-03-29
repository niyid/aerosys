<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Price-lists
			<s:if test="customerId != null">
				<s:url var="newRoutePriceUrl" action="popupNewRoutePrice" namespace="/operator/ajax" escapeAmp="false">
					<s:param name="clientId" value="%{customerId}" />
				</s:url>
				<sj:a cssClass="btn btn-primary btn-lg" href="%{newRoutePriceUrl}" openDialog="priceListDialog"><span class="glyphicon glyphicon-plus"></span>New Price-list</sj:a>
			</s:if>
		</div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%">Route</td>
						<td width="10%">Currency</td>
						<td width="15%">Rate</td>
						<td width="20%">Economy</td>
						<td width="20%">Business</td>
						<td width="20%">First</td>
						<td width="5%">
						</td>
					</tr> 
				</thead>
				<s:iterator value="entity.routePrices">
					<tr>
						<td><s:property value="route.description" /></td>
						<td><s:property value="currency.currencyCode" /></td>
						<td><fmt:formatNumber type="currency" currencySymbol="${defaultCurrency.currencySymbol}" value="${currencyRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
						<td>
							<s:url var="selUrl" action="popupEditRoutePrice!select" namespace="/operator/ajax" escapeAmp="false">
								<s:param name="routeId" value="%{route.id}" />
								<s:param name="clientId" value="%{organization.id}" />
								<s:param name="currencyCode" value="%{currency.currencyCode}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" openDialog="priceListDialog"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>