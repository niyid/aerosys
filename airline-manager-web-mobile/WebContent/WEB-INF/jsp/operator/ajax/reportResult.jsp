<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		Income/Loss Analysis
	</head>
	<body>
				<s:form id="reportForm" name="reportForm" action="showReportPage" namespace="/operator" theme="xhtml" method="post">
					<sj:datepicker name="fromDate" displayFormat="dd/mm/yy" />
					<sj:datepicker name="toDate" displayFormat="dd/mm/yy" />
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="reportListDiv" onCompleteTopics="refreshReportListDiv" effect="highlight" effectDuration="500" indicator="indicator" />
				</s:form>
				
				<h3>Ticket Sales</h3>
				<s:if test="tickets == null || tickets.isEmpty()">
					No available data.
				</s:if>
				<s:else>
					<sjm:list
					    id="ticketListView"
					    inset="true"
					    list="tickets"
					    listKey="top.ticketCode"
					    listValue="top.ticketCode + ' ' + getText('format.date', {top.booking.bookingDate}) + ' ' + defaultCurrency.currencySymbol + getText({top.booking.totalPrice})"
					    listHref="%{selUrl}"
					    listParam="flightId"
					/>				
				</s:else>
				
				<h3>Lost income (vacant seats)</h3>
				<s:if test="unsoldSeats == null || unsoldSeats.isEmpty()">
					No available data.
				</s:if>
				<s:else>
					<sjm:list
					    id="unsoldSeatListView"
					    inset="true"
					    list="unsoldSeats"
					    listKey="top.id"
					    listValue="top.flight.id.airlineCode + ' ' + top.flight.id.flightNumber + ' ' + getText('format.date', {top.flight.id.flightDate}) + ' ' + defaultCurrency.currencySymbol + getText({top.booking.flightFare})"
					    listHref="%{selUrl}"
					    listParam="flightId"
					/>	
				</s:else>	
	</body>
</html>