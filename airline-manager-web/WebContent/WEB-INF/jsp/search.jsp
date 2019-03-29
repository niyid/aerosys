<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Search</title>
</head>
<body>
	<div id="searchMainDiv">
		<%@ include file="/common/search_header.jsp"%>	
		<table>
			<tr>
				<td width="35%">
					<s:url var="searchFilterUrl" action="searchFilter" namespace="/" />
					<sj:div id="searchFilterFormDiv" href="%{searchFilterUrl}" reloadTopics="reloadFilterFormDiv" />
				</td>
				<td>
					<s:url var="searchResultUrl" action="searchResult" namespace="/" />
					<sj:div id="searchResultDiv" href="%{searchResultUrl}" reloadTopics="reloadSearchResultDiv" />
				</td>
			</tr>
		</table>
		
		<sj:dialog id="flightDetailsDialog" autoOpen="false" modal="true" title="Flight Details" width="1000" position="['center','top']" />
		<sj:dialog id="bookingDialog" autoOpen="false" modal="true" title="Booking" width="1200" position="['center','top']" />
		<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
		
		<script type="text/javascript" >
			$.subscribe('refreshSearchResultDiv', function(event,data) {
		        $('#searchResultDiv').publish('reloadSearchResultDiv');
			});
		</script>
	</div>
</body>
</html>