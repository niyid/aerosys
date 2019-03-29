<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="airportCode == null">Add</s:if><s:else>Edit</s:else> Airport</title>
	</head>
	<body>
		<s:form id="airportRecordForm" action="airportRecord!save" namespace="/setup/ajax" theme="xhtml">
			<s:hidden name="airportCode" />
			<sjm:textfield label="Name" name="entity.airportName" required="true" />
			<sjm:textfield label="Code" name="entity.airportCode" required="true" />
			<sjm:select
				name="cityId" 
				headerKey=""
				headerValue="----------Select-----------"
				listKey="id"
				listValue="cityName"
				list="cityLov"
				label="City"
				emptyOption="false"
				required="true"
			/>								
		</s:form>
		<sjm:a button="true" buttonIcon="gear" formIds="airportRecordForm">Save</sjm:a>
				
		<s:include value="/common/msg.jsp" />
	</body>
</html>
