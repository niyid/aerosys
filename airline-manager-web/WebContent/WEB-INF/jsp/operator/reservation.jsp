<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Reservations</title>
</head>
<body>
	<table>
		<tr>
			<td>
				<s:url var="reservationListUrl" action="reservationList" namespace="/operator/ajax" />
				<sj:div id="reservationListDiv" href="%{reservationListUrl}" />
			</td>
		</tr>
	</table>
	<sj:dialog id="confirmBookingDialog" autoOpen="false" modal="true" title="Confirm Booking" width="1200" position="['center','top']" />
	<sj:dialog id="cancelBookingDialog" autoOpen="false" modal="true" title="Cancel Booking" width="1200" position="['center','top']" />
	<sj:dialog id="updateBookingDialog" autoOpen="false" modal="true" title="Alter Booking" width="1200" position="['center','top']" />
	<sj:dialog id="checkinBookingDialog" autoOpen="false" modal="true" title="Checkin Booking" width="1200" position="['center','top']" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
</body>
</html>