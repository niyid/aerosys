<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Search</title>
</head>
<body>
	<table>
		<tr>
			<td width="40%">
				<s:url var="searchFilterUrl" action="searchFilter" namespace="/" />
				<sj:div id="searchFilterFormDiv" href="%{searchFilterUrl}" reloadTopics="reloadFilterFormDiv" />
			</td>
			<td>
				<s:url var="searchResultUrl" action="searchResult" namespace="/" />
				<sj:div id="searchResultDiv" href="%{searchResultUrl}" reloadTopics="reloadSearchResultDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="searchDialog" autoOpen="false" modal="true" title="Flight Details" width="1000" position="['center','top']" />
	
	<script type="text/javascript" >
		$.subscribe('refreshSearchResultDiv', function(event,data) {
	        $('#searchResultDiv').publish('reloadSearchResultDiv');
		});
	</script>
</body>
</html>