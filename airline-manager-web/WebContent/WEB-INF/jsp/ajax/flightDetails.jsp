<%@ include file="/common/taglibs.jsp"%>
	<table>
		<tr>
			<td class="windowPanel">
			<div>
				<table>
					<tr>
						<td width="50%">
							<table>
								<tr>
									<td><h5>Departure Time</h5></td><td><s:date name="entity.departureTime" format="dd/MM/yyyy hh:mm a"/></td>
								</tr>
								<tr>
									<td><h5>Departure Airport</h5></td><td><s:property value="entity.departureAirport.airportName" /></td>
								</tr>
								<tr>
									<td><h5>Arrival Time</h5></td><td><s:date name="entity.arrivalTime" format="dd/MM/yyyy hh:mm a"/></td>
								</tr>
								<tr>
									<td><h5>Arrival Airport</h5></td><td><s:property value="entity.arrivalAirport.airportName" /></td>
								</tr>
								<tr>
									<td colspan="2">
										<h5>Rebates</h5>
										<s:if test="rebates.isEmpty()">None</s:if>
										<s:else>
											<table class="table table-hover table-striped">
												<thead>
													<tr>
														<td width="40%">Description</td>
														<td width="25%" style="text-align:right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
														<td width="25%" style="text-align:right">Amount (<s:property value="usdCurrency.currencySymbol" />)</td>
														<td width="10%" style="text-align:right">Auto?</td>
													</tr>
												</thead>
												<s:iterator value="rebates">
													<tr>
														<td><s:property value="description" /></td>
														<td style="text-align:right">
															<s:if test="ratePercentage != null">
																<fmt:formatNumber type="percent" maxIntegerDigits = "4" value="${ratePercentage / 100}" />
															</s:if>
															<s:else>
																<fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${fixedRate}" maxFractionDigits="2" minFractionDigits="2"/>
															</s:else>
														</td>
														<td style="text-align:right">
															<s:if test="ratePercentage != null">
																<fmt:formatNumber type="percent" maxIntegerDigits = "4" value="${ratePercentage / 100}" />
															</s:if>
															<s:else>
																<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${fixedRate / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>
															</s:else>
														</td>
														<td style="text-align:right"><s:property value="autoAdded ? 'YES' : 'NO'" /></td>
													</tr>
												</s:iterator>
											</table>
										</s:else>
										<h5>Taxes & Charges</h5>
										<s:if test="taxes.isEmpty()">None</s:if>
										<s:else>
											<table class="table table-hover table-striped">
												<thead>
													<tr>
														<td width="40%">Description</td>
														<td width="25%" style="text-align:right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
														<td width="25%" style="text-align:right">Amount (<s:property value="usdCurrency.currencySymbol" />)</td>
														<td width="10%" style="text-align:right">Auto?</td>
													</tr>
												</thead>
												<s:iterator value="taxes">
													<tr>
														<td><s:property value="description" /></td>
														<td style="text-align:right">
															<s:if test="ratePercentage != null">
																<fmt:formatNumber type="percent" maxIntegerDigits = "4" value="${ratePercentage / 100}" />
															</s:if>
															<s:else>
																<fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${fixedRate}" maxFractionDigits="2" minFractionDigits="2"/>
															</s:else>
														</td>
														<td style="text-align:right">
															<s:if test="ratePercentage != null">
																<fmt:formatNumber type="percent" maxIntegerDigits = "4" value="${ratePercentage / 100}" />
															</s:if>
															<s:else>
																<fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${fixedRate / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/>
															</s:else>
														</td>
														<td style="text-align:right"><s:property value="autoAdded ? 'YES' : 'NO'" /></td>
													</tr>
												</s:iterator>
											</table>
										</s:else>									
									</td>
								</tr>
								<s:if test="entity.hasEconomy">
								<tr>
									<td colspan="2">
										<h5><s:property value="entity.economyTravelName" /></h5>
										<table class="table table-hover table-striped">
											<thead>
												<tr>
													<td width="50%">Description</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="usdCurrency.currencySymbol" />)</td>
												</tr>
											</thead>
											<tr>
												<td><s:property value="entity.economyTravelName" /> Basic Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${entity.airFareEconomy}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${entity.airFareEconomy / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.economyTravelName" /> Rebates</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalRebatesEco}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalRebatesEco / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.economyTravelName" /> Charges</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalTaxesEco}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalTaxesEco / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td>Total <s:property value="entity.economyTravelName" /> Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalChargesEco}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalChargesEco / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
										</table>
									</td>						
								</tr>
								</s:if>
								<s:if test="entity.hasBusiness">
								<tr>
									<td colspan="2">
										<h5><s:property value="entity.businessTravelName" /></h5>
										<table class="table table-hover table-striped">
											<thead>
												<tr>
													<td width="50%">Description</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="usdCurrency.currencySymbol" />)</td>
												</tr>
											</thead>
											<tr>
												<td><s:property value="entity.businessTravelName" /> Basic Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${entity.airFareBizClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${entity.airFareBizClass / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.businessTravelName" /> Rebates</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalRebatesBiz}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalRebatesBiz / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.businessTravelName" /> Charges</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalTaxesBiz}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalTaxesBiz / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td>Total <s:property value="entity.businessTravelName" /> Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalChargesBiz}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalChargesBiz / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
										</table>
									</td>											
								</tr>
								</s:if>
								<s:if test="entity.hasFirst">
								<tr>
									<td colspan="2">
										<h5><s:property value="entity.firstTravelName" /></h5>
										<table class="table table-hover table-striped">
											<thead>
												<tr>
													<td width="50%">Description</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
													<td width="25%" style="text-align:right">Amount (<s:property value="usdCurrency.currencySymbol" />)</td>
												</tr>
											</thead>
											<tr>
												<td><s:property value="entity.firstTravelName" /> Basic Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${entity.airFareFirstClass}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${entity.airFareFirstClass / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.firstTravelName" /> Rebates</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalRebatesFst}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalRebatesFst / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td><s:property value="entity.firstTravelName" /> Charges</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalTaxesFst}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalTaxesFst / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
											<tr>
												<td>Total <s:property value="entity.firstTravelName" /> Fare</td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${entity.currency.currencySymbol}" value="${totalChargesFst}" maxFractionDigits="2" minFractionDigits="2"/></td>
												<td style="text-align:right"><fmt:formatNumber type="currency" currencySymbol="${usdCurrency.currencySymbol}" value="${totalChargesFst / entity.currency.exchangeRate}" maxFractionDigits="2" minFractionDigits="2"/></td>
											</tr>
										</table>
									</td>											
								</tr>
								</s:if>
							</table>							
						</td>
						<td>
							<s:action name="flightSeatLayout" namespace="/operator/ajax" flush="true" executeResult="true" />
						</td>
					</tr>
				</table>
			</div>
			</td>
		</tr>		
	</table>
