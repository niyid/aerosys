<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<s:if test="bookingId == null">Add</s:if><s:else>Edit</s:else> Booking
			<span align="right">
				<s:if test="bookingId != null">
					<s:url var="newRecordUrl" action="bookingRecord!initNew" namespace="/operator/ajax" />
					<sj:a cssClass="btn btn-primary btn-lg" href="%{newRecordUrl}" targets="bookingFormDiv"><span class="glyphicon glyphicon-eye-open">New Booking</span></sj:a>
				</s:if>
			</span>
		</div>
		<div class="panel-body">
			<s:form id="bookingRecordForm" action="bookingRecord!save" namespace="/operator/ajax" theme="xhtml">
				<s:hidden name="bookingId" />
					
					<s:hidden name="entity.primaryPassenger.id" />
					<s:textfield label="First Name" name="entity.primaryPassenger.firstName" required="true" />
					<s:textfield label="Middle Name" name="entity.primaryPassenger.middleName" required="false" />
					<s:textfield label="Last Name" name="entity.primaryPassenger.lastName" required="true" />
					<s:textfield label="Passport Number" name="entity.primaryPassenger.passportNumber" required="true" />
					<s:textfield label="Primary Phone" name="entity.primaryPassenger.phoneNumber1" required="true" />
					<s:textfield label="Secondary Phone" name="entity.primaryPassenger.phoneNumber2" required="false" />
					<s:textfield label="Primary Email" name="entity.primaryPassenger.email1" required="true" />
					<s:textfield label="Secondary Email" name="entity.primaryPassenger.email2" required="false" />
					<sj:datepicker label="Birth Date" name="entity.primaryPassenger.dob" required="true" displayFormat="dd/mm/yy" />
					<s:checkbox label="Minor" title="Passenger below the age of 14" name="minor" required="true" labelposition="right" labelSeparator="" />
					<s:select 
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
					<s:select 
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
					<s:textfield label="Line 1" name="address.addressLine1" required="false" />
					<s:textfield label="Line 2" name="address.addressLine2" />
					<s:textfield label="City" name="address.city" required="true" />
					<s:textfield label="State/Country" name="address.state" required="false" />
			
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="bookingFormDiv" onCompleteTopics="refreshBookingListDiv" effect="highlight" effectDuration="500" />
			</s:form>	
		</div>
	</div>
</div>

<s:include value="/common/msg.jsp" />	