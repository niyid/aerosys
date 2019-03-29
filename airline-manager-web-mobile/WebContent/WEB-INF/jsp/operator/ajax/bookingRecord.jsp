<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title><s:if test="bookingId == null">Add</s:if><s:else>Edit</s:else> Booking</title>
	</head>
	<body>
				<s:form id="bookingRecordForm" action="bookingRecord!save" namespace="/operator/ajax" theme="xhtml">
					<s:hidden name="bookingId" />
					<s:hidden name="entity.primaryPassenger.id" />
					
					<sjm:textfield label="First Name" name="entity.primaryPassenger.firstName" required="true" />
					<sjm:textfield label="Middle Name" name="entity.primaryPassenger.middleName" required="false" />
					<sjm:textfield label="Last Name" name="entity.primaryPassenger.lastName" required="true" />
					<sjm:textfield label="Passport Number" name="entity.primaryPassenger.passportNumber" required="true" />
					<sjm:textfield label="Primary Phone" name="entity.primaryPassenger.phoneNumber1" required="true" />
					<sjm:textfield label="Secondary Phone" name="entity.primaryPassenger.phoneNumber2" required="false" />
					<sjm:textfield label="Primary Email" name="entity.primaryPassenger.email1" required="true" />
					<sjm:textfield label="Secondary Email" name="entity.primaryPassenger.email2" required="false" />
					<sj:datepicker label="Birth Date" name="entity.primaryPassenger.dob" required="true" displayFormat="dd/mm/yy" />
					<sjm:checkbox label="Minor" title="Passenger below the age of 14" name="minor" required="true" labelposition="right" labelSeparator="" />
					<sjm:select 
							name="titleId" 
							headerKey=""
							headerValue="----------Select-----------"
							list="userTitleLov"
							listKey="id"
							listValue="description"
							emptyOption="false"
							label="Title Prefix" 
							labelSeparator=":"
							required="false" 
					/>															
					<sjm:select 
							name="entity.primaryPassenger.gender" 
							headerKey=""
							headerValue="----------Select-----------"
							list="genderLov"
							listKey="name()"
							listValue="name()"
							emptyOption="false"
							label="Gender" 
							labelSeparator=":"
							required="false" 
					/>																													
					<sjm:textfield label="Line 1" name="address.addressLine1" required="true" />
					<sjm:textfield label="Line 2" name="address.addressLine2" />
					<sjm:textfield label="City" name="address.city" required="true" />
					<sjm:textfield label="State/Country" name="address.state" required="true" />
				</s:form>
				<sjm:a button="true" buttonIcon="gear" formIds="bookingRecordForm">Save</sjm:a>
				<sjm:a button="true" buttonIcon="arrow-l" data-rel="back">Back</sjm:a>	
		<s:include value="/common/msg.jsp" />
	</body>
</html>