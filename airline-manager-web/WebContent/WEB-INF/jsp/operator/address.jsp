<%@ include file="/common/taglibs.jsp"%>

<div id="addressFormDiv" align="center" style="width: 600px;"> 		
<s:form action="showAddressForm!save" namespace="/ajax" theme="xhtml" validate="true">
	<s:hidden name="address.id" />
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Address</div>
		<div class="panel-body">
			<s:textfield label="Line 1" name="address.addressLine1" required="true" />
			<s:textfield label="Line 2" name="address.addressLine2" />
			<s:textfield label="City" name="address.city" />
			<s:textfield label="State" name="address.state" />
			<s:textfield label="PO Box" name="address.postOfficeBox" />
		</div>
	</div>
</div>	
</s:form>
<s:include value="/common/msg.jsp" />
</div>
