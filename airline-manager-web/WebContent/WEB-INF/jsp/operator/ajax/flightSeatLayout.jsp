<%@ include file="/common/taglibs.jsp"%>

			<table>
				<tr>
					<td width="40%">
						<div style="background: #ffffff;">
							<table style="border-spacing: 5px; border-collapse: separate;">
								<tr>
									<td colspan="3"><h4>Legend</h4></td>
								</tr>
								<s:if test="flight.hasEconomy">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="flight.economyTravelName" /></span></td>
									<td width="30"></td>
									<td class="seat-vacant-economy"></td>
								</tr>
								</s:if>
								<s:if test="flight.hasBusiness">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="flight.businessTravelName" /></span></td>
									<td width="30"></td>
									<td class="seat-vacant-business"></td>
								</tr>
								</s:if>
								<s:if test="flight.hasFirst">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="flight.firstTravelName" /></span></td>
									<td width="30"></td>
									<td class="seat-vacant-first"></td>
								</tr>
								</s:if>
								<tr>
									<td><span style="white-space: nowrap;">Reserved</span></td>
									<td width="30"></td>
									<td class="seat-reserved"></td>
								</tr>
								<tr>
									<td><span style="white-space: nowrap;">Checked-In</span></td>
									<td width="30"></td>
									<td class="seat-taken"></td>
								</tr>
								<tr>
									<td><span style="white-space: nowrap;">Unavailable</span></td>
									<td width="30"></td>
									<td class="seat-unavailable"></td>
								</tr>
							</table>
						</div>
					</td>
					<td width="60%">
						<div id="seatListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 400px; overflow: auto;">
							<table style="border-spacing: 5px; border-collapse: separate; margin: 10px; width: 20%; margin: 0 auto;">
							<s:iterator value="seats" status="row">
								<s:if test="#row.index % (crossSection.length() - 1) == 0"><tr></s:if>
									<s:if test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@ECONOMY && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-economy" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:url var="selSeatUrl" action="popupFlightDetailsSeat" namespace="/" escapeAmp="false">
												<s:param name="flightNumber" value="%{flightNumber}" />
												<s:param name="flightDate" value="%{flightDate}" />
												<s:param name="regNumber" value="%{id.regNumber}" />
												<s:param name="rowNumber" value="%{rowNumber}" />
												<s:param name="columnId" value="%{columnId}" />
											</s:url>
											<sj:a button="false" href="%{selSeatUrl}" openDialog="searchDialog">
												<s:property value="rowNumber" /><s:property value="columnId" />
											</sj:a>								
										</td>
									</s:if>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@BUSINESS && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-business" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@FIRST && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-first" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@ECONOMY && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-exit-economy" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@BUSINESS && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-exit-business" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@FIRST && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-exit-first" title="<fmt:formatNumber type='currency' currencySymbol='${flight.currency.currencySymbol}' value='${flightFare}' maxFractionDigits='2' minFractionDigits='2'/>"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@RESERVED">
										<td class="seat-reserved" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@TAKEN">
										<td class="seat-taken" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@UNAVAILABLE">
										<td class="seat-unavailable" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="rowNumber" /><s:property value="columnId" /><br />
										</td>
									</s:elseif>
									<s:if test="#row.index % (crossSection.length() - 1)  == (crossSection.indexOf('|') - 1)">
										<td class="isle"></td>
									</s:if>
								<s:if test="#row.index % (crossSection.length() - 1) == (crossSection.length() - 1)"></tr></s:if>
							</s:iterator>
							</table>
						</div>					
					</td>
				</tr>
			</table>