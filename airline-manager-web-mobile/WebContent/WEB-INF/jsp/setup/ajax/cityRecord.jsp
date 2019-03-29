<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="cityId == null">Add</s:if><s:else>Edit</s:else> City</title>
	</head>
	<body>
		<s:form id="cityRecordForm" action="cityRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="cityId" />

					<sjm:textfield label="Name" name="entity.cityName" required="true" />
					
					<sjm:select
						key="countryCode" 
						headerKey=""
						headerValue="----------Select-----------"
						list="countryLov"
						listKey="countryCode"
						listValue="countryName"
						emptyOption="false"
						label="Country" 
						required="true"
						onchange="$.publish('countryChangeTopic')"
					/>
					
					<sjm:select
						key="countryStateId" 
						headerKey=""
						headerValue="----------Select-----------"
						list="stateLov"
						listKey="id"
						listValue="stateName"
						emptyOption="false"
						label="State" 
						required="true"
					/>
		</s:form>
		<sjm:a button="true" buttonIcon="gear" formIds="cityRecordForm" listenTopics="countryChangeTopic">Save</sjm:a>
		
		<s:include value="/common/msg.jsp" />	
	</body>
</html>
