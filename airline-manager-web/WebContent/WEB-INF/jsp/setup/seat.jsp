<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Seat</title>
</head>
<body>

	<table>
		<tr>
			<td width="40%">
				<s:url var="seatFormUrl" action="seatRecord" namespace="/setup/ajax" />
				<sj:div id="seatFormDiv" href="%{seatFormUrl}" />
			</td>
			<td>
				<s:url var="seatListUrl" action="seatList" namespace="/setup/ajax" />
				<sj:div id="seatListDiv" href="%{seatListUrl}" reloadTopics="reloadSeatListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="seatDialog" autoOpen="false" modal="true" title="Seat Details" width="600" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
	<script type="text/javascript" >
		$.subscribe('refreshSeatListDiv', function(event,data) {
	        $('#seatListDiv').publish('reloadSeatListDiv');
		});
	</script>	
</body>
</html>