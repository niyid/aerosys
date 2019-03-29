<%@ include file="/common/taglibs.jsp"%>
				
<s:form id="paymentRecordForm" action="popupPaymentRecord!save" namespace="/" theme="xhtml">
	<s:hidden name="bookingId" />
	<s:select
		name="cardType" 
		headerKey=""
		headerValue="-----Select-----"
		list="{ 'MASTERCARD','VISA','AMERICANEX' }"
		label="Card Type"
		emptyOption="false"
		required="true"
	/>				
	<s:textfield label="Name" name="cardName" required="true" />
	<s:textfield label="Card Number" name="cardNumber" required="true" />
	<s:password label="PIN" name="cardPin" required="true" />
	<s:select
		name="expirationDay" 
		headerKey=""
		headerValue="Select"
		list="{ '01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31' }"
		label="Expiration Day"
		emptyOption="false"
		required="true"
	/>				
	<s:select
		name="expirationYear" 
		headerKey=""
		headerValue="Select"
		list="{ '2018','2019','2020','2021' }"
		label="Expiration Year"
		emptyOption="false"
		required="true"
	/>				
	<s:textfield label="CVV Code" name="cardCvv" required="true" />
	<s:textfield label="Amount" name="amount" required="false" readonly="true" />
</s:form>	
