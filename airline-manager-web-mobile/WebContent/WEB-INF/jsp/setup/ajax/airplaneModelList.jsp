<%@ include file="/common/taglibs.jsp"%>

<table>
	<tr>
		<td class="windowHeader"><div>Airplane Model</div></td>
	</tr>
	<tr>
		<td class="windowPanel">
			<div id="airplaneModelListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 200px; overflow: auto;">
				<table class="data-listing">
					<thead>
						<tr>
							<td width="25%">Make</td>
							<td width="25%">Model</td>
							<td width="45%">Description</td>
							<td width="5%"></td>
						</tr>
					</thead>
					<s:iterator value="airplaneModels">
						<tr>
							<td><s:property value="airplaneMake.makeName" /></td>
							<td><s:property value="modelName" /></td>
							<td><s:property value="description" /></td>
							<td align="right">
								<s:url var="selUrl" action="airplaneModelRecord!select" namespace="/setup/ajax" escapeAmp="false">
									<s:param name="modelName" value="%{modelName}" />
									<s:param name="makeName" value="%{airplaneMake.makeName}" />
								</s:url>
								<sj:a button="false" buttonIcon="ui-icon-gear" href="%{selUrl}" targets="airplaneModelFormDiv">Edit</sj:a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>