<%@ include file="/common/taglibs.jsp"%>

<table>
	<tr>
		<td class="windowPanel">
			<s:if test="crewMember != null">
			<div>
				<s:if test="user != null">
					<s:url var="scheduleUrl" action="showSchedule" namespace="/operator">
						<s:param name="crewMemberId" value="%{crewMember.id}" />
					</s:url>
					<sj:a button="true" href="%{scheduleUrl}" openDialog="scheduleDialog">Operation Schedule</sj:a>

				</s:if>
				<s:else>
					<sj:a button="true" openDialog="loginRequiredDialog">Operation Schedule</sj:a>
				</s:else>				
			
				<fieldset>
					<legend><b>Profile</b></legend>
					<table>
						<tr>
							<td>
								Designated as <b><s:property value="crewMember.crewType" /></b><br />
								Born on <b><s:property value="crewMember.dob" /></b><br />
								Email <b><s:property value="crewMember.mail" /></b>
							</td>
							<td align="right">
								<s:if test="crewMember.photo == null">No Photo</s:if>
								<s:else>
									<img style='height: 75px; width: 100px; border: solid 1px #4f81bd;' src='<s:url action="photograph" namespace="/download"><s:param name="memberId" value="%{crewMember.id}" /></s:url>' />
								</s:else>
							</td>
						</tr>
						<tr>
							<td></td>
							<td align="right"><b><s:property value="crewMember.fullName" /></b></td>
						</tr>
					</table>
				</fieldset>
			</div>
			</s:if>
		</td>
	</tr>
</table>