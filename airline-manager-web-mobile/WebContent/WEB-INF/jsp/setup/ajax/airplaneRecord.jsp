<%@ include file="/common/taglibs.jsp"%>

	<table>
		<tr>
			<td class="windowHeader"><div><s:if test="regNumber == null">Add</s:if><s:else>Edit</s:else> Airplane</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
			<div>
				<div align="right">
					<s:if test="regNumber != null">
						<s:url var="newRecordUrl" action="airplaneRecord!initNew" namespace="/setup/ajax" />
						<sj:a button="false" buttonIcon="" href="%{newRecordUrl}" targets="airplaneFormDiv">New Airplane</sj:a>
					</s:if>
				</div>
				
				<s:form id="airplaneRecordForm" action="airplaneRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="regNumber" />
					<s:textfield label="Registration" name="entity.regNumber" cssStyle="width: 200px;" required="true" />
					<sj:datepicker label="Manufacture Date" name="entity.manufactureDate" cssStyle="width: 200px;" displayFormat="dd/mm/yy" required="true" />
					<s:select
						name="makeName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="makeName"
						listValue="makeName"
						list="airplaneMakeLov"
						label="Make"
						emptyOption="false"
						required="true"
						onchange="$.publish('makeChangeTopic')"
					/>			
					<s:select
						name="modelName" 
						headerKey=""
						headerValue="----------Select-----------"
						listKey="modelName"
						listValue="modelName"
						list="airplaneModelLov"
						label="Model"
						emptyOption="false"
						required="true"
					/>			
					
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneFormDiv" onCompleteTopics="refreshAirplaneListDiv" listenTopics="makeChangeTopic" effect="highlight" effectDuration="500" indicator="indicator" />
				</s:form>
			</div>
			</td>
		</tr>		
	</table>

<s:include value="/common/msg.jsp" />	