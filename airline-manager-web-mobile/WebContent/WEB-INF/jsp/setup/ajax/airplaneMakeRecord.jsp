<%@ include file="/common/taglibs.jsp"%>

	<table>
		<tr>
			<td class="windowHeader"><div><s:if test="makeName == null">Add</s:if><s:else>Edit</s:else> Airplane Make</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
			<div>
				<div align="right">
					<s:if test="makeName != null">
						<s:url var="newRecordUrl" action="airplaneMakeRecord!initNew" namespace="/setup/ajax" />
						<sj:a button="false" buttonIcon="" href="%{newRecordUrl}" targets="airplaneMakeFormDiv">New Airplane Make</sj:a>
					</s:if>
				</div>
				
				<s:form id="airplaneMakeRecordForm" action="airplaneMakeRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="makeName" />

					<s:textfield label="Name" name="entity.makeName" cssStyle="width: 200px;" required="true" />
					
					<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneMakeFormDiv" onCompleteTopics="refreshAirplaneMakeListDiv" listenTopics="countryChangeTopic" effect="highlight" effectDuration="500" indicator="indicator" />
				</s:form>	
			</div>
			</td>
		</tr>		
	</table>

<s:include value="/common/msg.jsp" />	