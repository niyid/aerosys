<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Taxes</div>
		<div class="panel-body" id="taxListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="30%">Description</td>
						<td width="15%">Code</td>
						<td width="30%" style="text-align:right">Rate</td>
						<td width="20%" style="text-align:right">Auto-added?</td>
						<td width="20%" style="text-align:right">Regional?</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="taxes">
					<tr>
						<td><s:property value="description" /></td>
						<td><s:property value="rateCode" /></td>
						<td style="text-align:right">
							<s:if test="ratePercentage != null">
								<fmt:formatNumber type="percent" maxIntegerDigits = "4" value="${ratePercentage / 100}" />
							</s:if>
							<s:else>
								<div title='<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${fixedRate / airline.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>'><fmt:formatNumber type="currency" currencySymbol="${airline.currency.currencySymbol}" value="${fixedRate}" maxFractionDigits="2" minFractionDigits="2"/></div>
							</s:else>
						</td>
						<td style="text-align:right"><s:property value="autoAdded ? 'YES' : 'NO'" /></td>
						<td style="text-align:right"><s:property value="regional ? 'YES' : 'NO'" /></td>
						<td style="text-align:right">
							<s:url var="selUrl" action="taxRecord!select" namespace="/operator/ajax">
								<s:param name="taxId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" targets="taxFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>