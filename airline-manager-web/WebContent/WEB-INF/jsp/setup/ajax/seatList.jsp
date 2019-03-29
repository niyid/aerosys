<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Seat Map</div>
		<div class="panel-body">
			<s:form id="seatListForm" action="seatList" namespace="/setup/ajax" theme="xhtml">
				<s:select
					name="regNumber" 
					headerKey=""
					headerValue="----------Select-----------"
					listKey="regNumber"
					listValue="model.modelName + ' - ' + regNumber"
					list="airplaneLov"
					label="Airplane"
					emptyOption="false"
					required="true"
					onchange="$.publish('airplaneSelectedTopic')"					
				/>
				<sj:submit button="true" cssStyle="display: none;" value="Submit" targets="seatListDiv" listenTopics="airplaneSelectedTopic" effect="highlight" effectDuration="500" />			
			</s:form>
			<table>
				<tr>
					<td width="30%">
						<div>
							<table style="border-spacing: 5px; border-collapse: separate;">
								<tr>
									<td colspan="3"><h2>Legend</h2></td>
								</tr>
								<s:if test="hasEconomy">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="economyTravelName"/></span></td>
									<td width="30"></td>
									<td class="seat-vacant-economy"></td>
								</tr>
								</s:if>
								<s:if test="hasBusiness">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="businessTravelName"/></span></td>
									<td width="30"></td>
									<td class="seat-vacant-business"></td>
								</tr>
								</s:if>
								<s:if test="hasFirst">
								<tr>
									<td><span style="white-space: nowrap;">Vacant <s:property value="firstTravelName"/></span></td>
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
									<td><span style="white-space: nowrap;">Unavailable</span></td>
									<td width="30"></td>
									<td class="seat-unavailable"></td>
								</tr>
							</table>
						</div>
					</td>
					<td width="70%">
						<div id="seatListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 400px; overflow: auto;">
							<table style="border-spacing: 5px; border-collapse: separate; margin: 10px; width: 20%; margin: 0 auto;">
							<s:iterator value="seats" status="row">
								<s:if test="#row.index % (crossSection.length() - 1) == 0"><tr></s:if>
									<s:if test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@ECONOMY && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-economy" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:if>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@BUSINESS && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-business" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@FIRST && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == false">
										<td class="seat-vacant-first" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@ECONOMY && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-vacant-economy" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@BUSINESS && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-vacant-business" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatClass == @com.vasworks.airliner.model.SeatInterface$SeatClass@FIRST && seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@VACANT && exitRow == true">
										<td class="seat-vacant-first" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@RESERVED">
										<td class="seat-reserved" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
										</td>
									</s:elseif>
									<s:elseif test="seatStatus == @com.vasworks.airliner.model.SeatInterface$SeatStatus@UNAVAILABLE">
										<td class="seat-unavailable" title="<s:property value='seatClass' /> <s:property value='seatStatus' />"> 
											<s:property value="id.rowNumber" /><s:property value="id.columnId" /><br />
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
		</div>
	</div>
</div>