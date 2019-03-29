<%@ include file="/common/taglibs.jsp"%>

<div>
	<s:form name="scheduleViewForm" action="listSchedule" namespace="/operator">
		<s:hidden name="crewMemberId" />
		<s:select
			name="calendarPeriod" 
			headerKey=""
			headerValue="----------Select-----------"
			listKey="name()"
			listValue="name()"
			list="calendarPeriodLov"
			label="Calendar Period"
			emptyOption="false"
			required="true"			
		/>
		<sj:submit button="true" value="Update" buttonIcon="ui-icon-gear" targets="scheduleListDiv" effect="highlight" effectDuration="500" />
	</s:form>
	<table>
	<s:iterator value="scheduleMap">
		<tr>
		<s:iterator value="top">
			<td> <s:property value="description" /> <s:property value="startTime" /> <s:property value="endTime" /> </td>
		</s:iterator>
		</tr>	
	</s:iterator>	
	</table>
</div>