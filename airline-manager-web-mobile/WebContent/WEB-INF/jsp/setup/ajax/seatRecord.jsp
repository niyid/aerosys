<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>Update Seat</title>
	</head>
	<body>
				<s:form id="seatRecordForm" action="seatRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="rowNumber" />
					<s:hidden name="columnId" />
					<s:hidden name="regNumber" />
					<sjm:select
						name="entity.seatStatus" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatStatusLov"
						label="Status"
						emptyOption="false"
						required="true"
					/>
					<sjm:select
						name="entity.seatClass" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="name()"
						listValue="name()"
						list="seatClassLov"
						label="Class"
						emptyOption="false"
						required="true"
					/>
					<sjm:checkbox name="entity.exitRow" label="Exit Row?" />
				</s:form>	
				<s:if test="rowNumber != null && columnId != null && regNumber != null">
					<sjm:a button="true" value="Save" formIds="seatRecordForm" buttonIcon="gear" />							
				</s:if>
	</body>
	<s:include value="/common/msg.jsp" />
</html>
