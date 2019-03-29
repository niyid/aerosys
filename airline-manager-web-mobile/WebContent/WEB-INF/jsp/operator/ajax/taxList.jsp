<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Taxes</title>
	</head>
	<body>
		<s:url var="newRecordUrl" action="taxRecord!initNew" namespace="/setup/ajax" />
		<sjm:a button="true" buttonIcon="gear" href="%{newRecordUrl}">Add New Tax/Charge</sjm:a>
	
		<s:url var="selUrl" action="taxRecord!select" namespace="/operator/ajax">
			<s:param name="taxId" value="%{id}" />
		</s:url>
	
		<sjm:list
		    id="taxListView"
		    inset="true"
		    list="taxes"
		    listKey="top.id"
		    listValue="top.description + ' ' + (top.ratePercentage != null ? (getText({top.ratePercentage}) + '%') : (defaultCurrency.currencySymbol + getText({top.fixedRate}))) + ' ' + (autoAdded ? '[Auto-added]' : '[Manually added]')"
		    listHref="%{selUrl}"
		    listParam="taxId"
		/>	
	</body>
</html>