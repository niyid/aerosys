<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Flight Schedules</div>
		<div class="panel-body" id="flightScheduleListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="15%">Number</td>
						<td width="15%">Reference Date</td>
						<td width="10%">Weeks</td>
						<td width="25%">Departure</td>
						<td width="25%">Arrival</td>
						<td width="10%">Airplane</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="flightSchedules">
					<tr>
						<td><s:property value="flightNumber" /></td>
						<td><s:date name="startDate" format="dd/MM/yyyy" /></td>
						<td><s:property value="numOfWeeks" /></td>
						<td><s:date name="departureTime" format="HH:mm" /> -> <span title='<s:property value="departureAirport.airportName" />'><s:property value="departureAirport.airportCode" /></span></td>
						<td><s:date name="arrivalTime" format="HH:mm" /> -> <span title='<s:property value="arrivalAirport.airportName" />'><s:property value="arrivalAirport.airportCode" /></span></td>
						<td><s:property value="airplane.regNumber" /></td>
						<td>
							<s:url var="selUrl" action="flightScheduleRecord!select" namespace="/operator/ajax" escapeAmp="false">
								<s:param name="flightScheduleId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" targets="flightScheduleFormDiv"><span class="glyphicon glyphicon-edit"></span>Select</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>