<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Booking</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="bookingRecordUrl" action="bookingRecord" namespace="/operator/ajax" />
				<sj:div id="bookingRecordDiv" href="%{bookingRecordUrl}" />
			</td>
			<td>
				<s:url var="bookingListUrl" action="bookingList" namespace="/operator/ajax" />
				<sj:div id="bookingListDiv" href="%{bookingListUrl}" reloadTopics="reloadBookingListDiv" />
			</td>
		</tr>
	</table>
<script type="text/javascript" >
	$.subscribe('refreshBookingListDiv', function(event,data) {
        $('#bookingListDiv').publish('reloadBookingListDiv');
	});
</script>
<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />	
</body>
</html>