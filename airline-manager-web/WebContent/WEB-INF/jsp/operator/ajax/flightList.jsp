<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Search</div>
		<div class="panel-body">
			<s:form id="reservationFilterForm" action="flightList!search" namespace="/operator/ajax" method="post" cssClass="form-inline">
				<div class="form-group">
			    	<label for="flightDate">Flight Date:</label>
			    	<sj:datepicker name="flightDate" title="Flight Date" displayFormat="dd/mm/yy" cssClass="form-control" />
			  	</div>
			  	<div class="form-group">
			    	<label for="flightNumber">Flight Number</label>
			    	<s:textfield name="flightNumber" title="Flight Number" cssClass="form-control" />
			  	</div>
			  	<sj:submit id="searchFlightButton" buttonIcon="ui-icon-gear" button="true" value="Search" targets="flightListDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />
			</s:form>		
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">Flights</div>
		<div class="panel-body" id="flightListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="10%">Date</td>
						<td width="15%">Number</td>
						<td width="20%">Departure Time</td>
						<td width="10%">Departure</td>
						<td width="20%">Arrival Time</td>
						<td width="10%">Arrival</td>
						<td width="10%">Airplane</td>
						<td width="5%"></td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="flights">
					<s:date name="id.flightDate" format="dd/MM/yyyy" var="currFlightDate" />
					<tr>
						<td><s:date name="id.flightDate" format="dd/MM/yyyy" /></td>
						<td><s:property value="id.airlineCode" /> <s:property value="id.flightNumber" /></td>
						<td><s:date name="departureTime" format="dd/MM/yyyy HH:mm" /></td>
						<td><span title='<s:property value="departureAirport.airportName" />'><s:property value="departureAirport.airportCode" /></span></td>
						<td><s:date name="arrivalTime" format="dd/MM/yyyy HH:mm" /></td>
						<td><span title='<s:property value="arrivalAirport.airportName" />'><s:property value="arrivalAirport.airportCode" /></span></td>
						<td><s:property value="airplane.regNumber" /></td>
						<td>
							<s:url var="selUrl" action="flightRecord!select" namespace="/operator/ajax" escapeAmp="false">
								<s:param name="flightNumber" value="%{id.flightNumber}" />
								<s:param name="flightDate" value="%{#currFlightDate}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="flightFormDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-edit"></span>Select</sj:a>
						</td>
						<td>
							<s:url var="dlManifestUrl" action="flightManifest" namespace="/operator/ajax" escapeAmp="false">
								<s:param name="flightId" value="%{id}" />
							</s:url>
							<s:a cssClass="btn btn-primary btn-lg" href="%{dlManifestUrl}" target="_blank"><span class="glyphicon glyphicon-edit"></span>Manifest</s:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>