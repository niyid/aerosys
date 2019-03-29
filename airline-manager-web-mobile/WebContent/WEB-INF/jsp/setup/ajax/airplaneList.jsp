<%@ include file="/common/taglibs.jsp"%>

<table>
	<tr>
		<td class="windowHeader"><div>Airplanes</div></td>
	</tr>
	<tr>
		<td class="windowPanel">
			<div id="airplaneListInnerDiv" style="border: solid 1px #4f81bd; background: #ffffff; padding: 4px; height: 200px; overflow: auto;">
				<table class="data-listing">
					<thead>
						<tr>
							<td width="25%">Registration</td>
							<td width="25%" align="right">Make</td>
							<td width="25%" align="right">Model</td>
							<td width="20%" align="right">Manufactured</td>
							<td width="5%"></td>
						</tr>
					</thead>
					<s:iterator value="airplanes">
						<tr>
							<td><s:property value="regNumber" /></td>
							<td align="right"><s:property value="model.airplaneMake.makeName" /></td>
							<td align="right"><s:property value="model.modelName" /></td>
							<td align="right"><s:date name="manufactureDate" format="dd/MM/yyyy"/></td>
							<td align="right">
								<s:url var="selUrl" action="airplaneRecord!select" namespace="/setup/ajax">
									<s:param name="regNumber" value="%{regNumber}" />
								</s:url>
								<sj:a button="false" buttonIcon="ui-icon-gear" href="%{selUrl}" targets="airplaneFormDiv">Edit</sj:a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>