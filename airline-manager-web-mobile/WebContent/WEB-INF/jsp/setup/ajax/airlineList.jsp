<%@ include file="/common/taglibs.jsp"%>

<table>
	<tr>
		<td class="windowHeader"><div>Airlines</div></td>
	</tr>
	<tr>
		<td class="windowPanel">
			<div id="airlineListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 200px; overflow: auto;">
				<table class="data-listing">
					<thead>
						<tr>
							<td width="35%">Name</td>
							<td width="10%" align="right">Code</td>
							<td width="25%" align="right">Phone</td>
							<td width="25" align="right">Email</td>
							<td width="5%"></td>
						</tr>
					</thead>
					<s:iterator value="airlines">
						<tr>
							<td><s:property value="organizationName" /></td>
							<td align="right"><s:property value="airlineCode" /></td>
							<td align="right"><s:property value="contactInfo.primaryPhone" /></td>
							<td align="right"><s:property value="contactInfo.primaryEmail" /></td>
							<td align="right">
								<s:url var="selUrl" action="airlineRecord!select" namespace="/setup/ajax">
									<s:param name="airlineId" value="%{id}" />
								</s:url>
								<sj:a button="false" buttonIcon="ui-icon-gear" href="%{selUrl}" targets="airlineFormDiv">Edit</sj:a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>