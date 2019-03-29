<%@ include file="/common/taglibs.jsp"%>

<table>
	<tr>
		<td class="windowHeader"><div>Airplane Make</div></td>
	</tr>
	<tr>
		<td class="windowPanel">
			<div id="airplaneMakeListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 200px; overflow: auto;">
				<table class="data-listing">
					<thead>
						<tr>
							<td width="25%">Make</td>
							<td width="70%">Description</td>
							<td width="5%"></td>
						</tr>
					</thead>
					<s:iterator value="airplaneMakes">
						<tr>
							<td><s:property value="makeName" /></td>
							<td><s:property value="description" /></td>
							<td align="right">
								<s:url var="selUrl" action="airplaneMakeRecord!select" namespace="/setup/ajax">
									<s:param name="makeName" value="%{makeName}" />
								</s:url>
								<sj:a button="false" buttonIcon="ui-icon-gear" href="%{selUrl}" targets="airplaneMakeFormDiv">Edit</sj:a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>