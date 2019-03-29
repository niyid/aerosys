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
									<td><h3>Departure Time</h3></td><td><s:date name="entity.departureTime" format="dd/MM/yyyy hh:mm a"/></td>
								</tr>
								<tr>
									<td><h3>Departure Airport</h3></td><td><s:property value="entity.departureAirport.airportName" /></td>
								</tr>
								<tr>
									<td><h3>Arrival Time</h3></td><td><s:date name="entity.arrivalTime" format="dd/MM/yyyy hh:mm a"/></td>
								</tr>
								<tr>
									<td><h3>Arrival Airport</h3></td><td><s:property value="entity.arrivalAirport.airportName" /></td>
								</tr>
								<tr>
									<td colspan="2">
										<h3>Rebates</h3>
										<table class="data-listing">
											<thead>
												<tr>
													<td width="40%">Description</td>
													<td width="20%" align="right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
													<td width="20%" align="right">Amount ($)</td>
													<td width="20%" align="right">Auto-added?</td>
												</tr>
											</thead>
											<s:iterator value="rebates">
												<tr>
													<td><s:property value="description" /></td>
													<td align="right">
														<s:if test="ratePercentage != null">
															<s:property value="getText({ratePercentage})" />%
														</s:if>
														<s:else>
															<s:property value="entity.currency.currencySymbol" /><s:property value="getText({fixedRate})" />
														</s:else>
													</td>
													<td align="right">
														<s:if test="ratePercentage != null">
															<s:property value="getText({ratePercentage})" />%
														</s:if>
														<s:else>
															<s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({fixedRate * entity.currency.exchangeRate})" />
														</s:else>
													</td>
													<td align="right"><s:property value="autoAdded ? 'YES' : 'NO'" /></td>
												</tr>
											</s:iterator>
										</table>
										<h3>Taxes & Charges</h3>
										<table class="data-listing">
											<thead>
												<tr>
													<td width="40%">Description</td>
													<td width="20%" align="right">Amount (<s:property value="entity.currency.currencySymbol" />)</td>
													<td width="20%" align="right">Amount ($)</td>
													<td width="20%" align="right">Auto-added?</td>
												</tr>
											</thead>
											<s:iterator value="taxes">
												<tr>
													<td><s:property value="description" /></td>
													<td align="right">
														<s:if test="ratePercentage != null">
															<s:property value="getText({ratePercentage})" />%
														</s:if>
														<s:else>
															<s:property value="entity.currency.currencySymbol" /><s:property value="getText({fixedRate})" />
														</s:else>
													</td>
													<td align="right">
														<s:if test="ratePercentage != null">
															<s:property value="getText({ratePercentage})" />%
														</s:if>
														<s:else>
															<s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({fixedRate * entity.currency.exchangeRate})" />
														</s:else>
													</td>
													<td align="right"><s:property value="autoAdded ? 'YES' : 'NO'" /></td>
												</tr>
											</s:iterator>
										</table>									
									</td>
								</tr>
								<tr>
									<td>
										<h3>Economy Class</h3>
										<table class="data-listing">
											<thead>
												<tr>
													<td width="70%">Description</td>
													<td width="30%" align="right">Amount</td>
												</tr>
											</thead>
											<tr>
												<td>Economy-class Basic Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({entity.airFareEconomy})" /></td>
											</tr>
											<tr>
												<td>Economy-class Rebates</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalRebatesEco})" /></td>
											</tr>
											<tr>
												<td>Economy-class Charges</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalTaxesEco})" /></td>
											</tr>
											<tr>
												<td>Total Economy-class Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalChargesEco})" /></td>
											</tr>
										</table>
									</td>						
								</tr>
								<tr>
									<td>
										<h3>Business Class</h3>
										<table class="data-listing">
											<thead>
												<tr>
													<td width="70%">Description</td>
													<td width="30%" align="right">Amount</td>
												</tr>
											</thead>
											<tr>
												<td>Business-class Basic Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({entity.airFareBizClass})" /></td>
											</tr>
											<tr>
												<td>Business-class Rebates</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalRebatesBiz})" /></td>
											</tr>
											<tr>
												<td>Business-class Charges</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalTaxesBiz})" /></td>
											</tr>
											<tr>
												<td>Total Business-class Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalChargesBiz})" /></td>
											</tr>
										</table>
									</td>											
								</tr>
								<tr>
									<td>
										<h3>First Class</h3>
										<table class="data-listing">
											<thead>
												<tr>
													<td width="70%">Description</td>
													<td width="30%" align="right">Amount</td>
												</tr>
											</thead>
											<tr>
												<td>First-class Basic Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({entity.airFareFirstClass})" /></td>
											</tr>
											<tr>
												<td>First-class Rebates</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalRebatesFst})" /></td>
											</tr>
											<tr>
												<td>First-class Charges</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalTaxesFst})" /></td>
											</tr>
											<tr>
												<td>Total First-class Fare</td><td align="right"><s:property value="defaultCurrency.currencySymbol" /><s:property value="getText({totalChargesFst})" /></td>
											</tr>
										</table>
									</td>											
								</tr>
							</table>							
						</td>
						<td>
							<s:include value="/WEB-INF/jsp/ajax/flightSeats.jsp" />
						</td>
					</tr>
				</table>
			</div>
			</td>
		</tr>		
	</table>
