<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="taxId == null">Add</s:if><s:else>Edit</s:else> Tax</title>
	</head>
	<body>
		<s:form id="taxRecordForm" action="taxRecord!save" namespace="/operator/ajax" theme="xhtml">
			<s:hidden name="taxId" />

			<sjm:textfield label="Rate Percent" name="entity.ratePercentage" cssStyle="width: 200px;" />
			<sjm:textfield label="Fixed Rate" name="entity.fixedRate" cssStyle="width: 200px;" />
			<sjm:checkbox label="Auto-added" name="entity.autoAdded" />
			<sjm:textarea label="Description" name="entity.description" rows="5" required="true" />
	
		</s:form>
		<sjm:a button="true" buttonIcon="gear" formIds="taxRecordForm">Save</sjm:a>
		<sjm:a button="true" buttonIcon="arrow-l" data-rel="back">Back</sjm:a>
				
		<s:include value="/common/msg.jsp" />
	</body>
</html>	