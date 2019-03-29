<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Rebates</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="rebateRecord!initNew" namespace="/setup/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Rebate</sjm:a>
	
		<s:url var="selUrl" action="rebateRecord!select" namespace="/operator/ajax">
			<s:param name="rebateId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="rebateListView"
		    inset="true"
		    list="rebates"
		    listKey="top.id"
		    listValue="top.description + ' ' + (top.ratePercentage != null ? (getText({top.ratePercentage}) + '%') : (defaultCurrency.currencySymbol + getText({top.fixedRate}))) + ' ' + (autoAdded ? '[Auto-added]' : '[Manually added]')"
		    listHref="%{selUrl}"
		    listParam="rebateId"
		/>	
	</body>
</html>