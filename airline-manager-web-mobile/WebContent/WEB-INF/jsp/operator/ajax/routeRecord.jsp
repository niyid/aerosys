<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="routeId == null">Add</s:if><s:else>Edit</s:else> Route</title>
	</head>
	<body>
		<s:form id="routeRecordForm" action="routeRecord!save" namespace="/operator/ajax" theme="xhtml">
					<sjm:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
					
					<s:if test="!(airportLov == null || airportLov.isEmpty())">
						<sjm:select 
							name="selAirportCodes" 
							list="airportLov"
							listKey="airportCode"
							listValue="airportName"
							emptyOption="false"
							label="Select two (2) Airports" 
							required="true"
							multiple="true"
						/>
					</s:if>	

					<s:if test="!(taxLov == null || taxLov.isEmpty())">
						<sjm:select 
							name="selTaxIds" 
							list="taxLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Taxes/Charges" 
							required="true"
							multiple="true"
						/>
					</s:if>
					
					<s:if test="!(rebateLov == null || rebateLov.isEmpty())">
						<sjm:select 
							name="selRebateIds" 
							list="rebateLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Select from Available Rebates" 
							required="true"
							multiple="true"
						/>
					</s:if>	
		</s:form>
		<sjm:a button="true" buttonIcon="gear" formIds="routeRecordForm">Save</sjm:a>
		<sjm:a button="true" buttonIcon="arrow-l" data-rel="back">Back</sjm:a>					
		<s:include value="/common/msg.jsp" />
	</body>
</html>	