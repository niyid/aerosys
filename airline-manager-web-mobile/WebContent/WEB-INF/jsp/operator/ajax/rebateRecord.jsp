<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="rebateId == null">Add</s:if><s:else>Edit</s:else> Rebate</title>
	</head>
	<body>
		<s:form id="rebateRecordForm" action="rebateRecord!save" namespace="/operator/ajax" theme="xhtml">
			<s:hidden name="rebateId" />

			<sjm:textfield label="Rate Percent" name="entity.ratePercentage" />
			<sjm:textfield label="Fixed Rate" name="entity.fixedRate" />
			<sjm:checkbox label="Auto-added" name="entity.autoAdded" />
			<sjm:textarea label="Description" name="entity.description" rows="5" required="true" />
	
		</s:form>
		<sjm:a button="true" buttonIcon="gear" formIds="rebateRecordForm">Save</sjm:a>
		<sjm:a button="true" buttonIcon="arrow-l" data-rel="back">Back</sjm:a>
				
		<s:include value="/common/msg.jsp" />
	</body>
</html>	