<%@ include file="/common/taglibs.jsp"%>

	<table>
		<tr>
			<td class="windowHeader"><div><s:if test="modelName == null">Add</s:if><s:else>Edit</s:else> Airplane Model</div></td>
		</tr>
		<tr>
			<td class="windowPanel">
			<div>
				<div align="right">
					<s:if test="modelName != null">
						<s:url var="newRecordUrl" action="airplaneModelRecord!initNew" namespace="/setup/ajax" />
						<sj:a button="false" buttonIcon="" href="%{newRecordUrl}" targets="airplaneModelFormDiv">New Airplane Model</sj:a>
					</s:if>
				</div>
				
				<s:form id="airplaneModelRecordForm" action="airplaneModelRecord!save" namespace="/setup/ajax" theme="xhtml">
					<s:hidden name="modelName" />
					
					<s:select
						key="makeName" 
						headerKey=""
						headerValue="----------Select-----------"
						list="airplaneMakeLov"
						listKey="makeName"
						listValue="makeName"
						emptyOption="false"
						label="Airplane Make" 
						required="true"
					/>
					
					<s:textfield label="Name" name="entity.modelName" required="true" />
					<s:textfield label="No of Seat Rows" name="entity.numberOfRows" required="true" />
					<s:textfield label="Cross-section" name="entity.crossSection" required="true" title="The letters for each column; '|' for isle. Example: AB|CD" />
					<s:textfield label="Weight (kgs)" name="entity.airplaneWeight" required="true" />
					<s:textfield label="Luggage Capacity (kgs)" name="entity.luggageCapacity" required="true" />
					<s:textfield label="Exit Rows" name="entity.exitRows" required="true" title="Comma-separated list of rows. Example: 1,2,3,4" />
					<s:textfield label="Biz-class Rows" name="entity.bizClassRows" required="false" title="Comma-separated list of rows. Example: 1,2,3,4" />
					<s:textfield label="First-class Rows" name="entity.fstClassRows" required="false" title="Comma-separated list of rows. Example: 1,2,3,4" />
					
					<s:textarea label="Description" name="entity.description" rows="5" cols="40" required="true" />
				
					<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="airplaneModelFormDiv" onCompleteTopics="refreshAirplaneModelListDiv" listenTopics="countryChangeTopic" effect="highlight" effectDuration="500" indicator="indicator" />
				</s:form>	
			</div>
			</td>
		</tr>		
	</table>

<s:include value="/common/msg.jsp" />	