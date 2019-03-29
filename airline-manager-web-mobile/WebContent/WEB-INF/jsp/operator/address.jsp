<%@ include file="/common/taglibs.jsp"%>

<div id="addressFormDiv" align="center" style="width: 600px;"> 		
<s:form action="showAddressForm!save" namespace="/ajax" theme="xhtml" validate="true">
	<s:hidden name="address.id" />
	<table>
		<tr>
			<td class="windowHeader"><div>Address</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
				<table>
					<tr>
						<td><s:textfield label="Line 1" labelSeparator=":" name="address.addressLine1" cssStyle="width: 200px;" required="true" /></td>
					</tr>
					<tr>
						<td><s:textfield label="Line 2" labelSeparator=":" name="address.addressLine2" cssStyle="width: 200px;" /></td>
					</tr>
					<tr>
						<td><s:textfield label="City"labelSeparator=":" name="address.city" cssStyle="width: 200px;" /></td>
					</tr>
					<tr>
						<td><s:textfield label="State" labelSeparator=":" name="address.state" cssStyle="width: 200px;" /></td>
					</tr>
					<tr>
						<td><s:textfield label="PO Box" labelSeparator=":" name="address.postOfficeBox" cssStyle="width: 200px;" /></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="addressFormDiv" effect="highlight" effectDuration="500" indicator="indicator" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>
<s:include value="/common/msg.jsp" />
</div>
