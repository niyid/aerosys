<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Reservations</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="bookingRecord!initNew" namespace="/operator/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Booking</sjm:a>
	
		<s:url var="selUrl" action="bookingRecord!select" namespace="/operator/ajax" escapeAmp="false">
			<s:param name="bookingId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="bookingListView"
		    inset="true"
		    list="bookings"
		    listKey="top.id"
		    listValue="top.primaryPassenger.fullName + ' ' + top.bookingDate"
		    listHref="%{selUrl}"
		    listParam="bookingId"
		/>	
	</body>
</html>