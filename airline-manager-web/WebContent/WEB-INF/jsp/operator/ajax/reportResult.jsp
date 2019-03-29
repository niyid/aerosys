<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Parameters</div>
		<div class="panel-body">
			<s:form id="reportForm" name="reportForm" action="reportResult" cssClass="well form-search" namespace="/operator/ajax" theme="simple" method="post">
				<s:label for="startDate" value="From:" /><sj:datepicker name="startDate" displayFormat="dd/mm/yy" />
				<s:label for="endDate" value="To:" /><sj:datepicker name="endDate" label="To" displayFormat="dd/mm/yy" />
				<s:label for="reportID" value="Report:" />
				<s:select 
					name="reportId" 
					headerKey=""
					headerValue="----------Select----------"
					list="reportLov"
					listKey="id"
					listValue="description"
					emptyOption="false"
					required="true"
				/>
				<s:if test="records != null && !records.isEmpty()">
					<s:checkbox id="exportData" name="exportData" /> <s:label for="exportData" value="Export to Excel" cssStyle="font-weight:bold" />
					<sj:submit id="generateButton" button="true" value="Generate" buttonIcon="ui-icon-gear" targets="#" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />
				</s:if>
				<s:else>
					<sj:submit id="generateButton" button="true" value="Generate" buttonIcon="ui-icon-gear" targets="reportListDiv" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />
				</s:else>																													
			</s:form>		
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">Report Results</div>
		<div class="panel-body">
			<s:if test="records == null || records.isEmpty()">
				No available data.
			</s:if>
			<s:else>
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<s:iterator value="headers">
								<td align="right"><b><s:property value="top"/></b></td>
							</s:iterator>
						</tr>
					</thead>
					<s:iterator value="records">
						<tr>
							<s:iterator value="top">
								<s:if test="top instanceof java.lang.String">
									<td align="right"><s:property value="top"/></td>
								</s:if>
								<s:elseif test="top instanceof java.util.Date">
									<td><s:date name="top" format="dd/MM/yyyy" /></td>
								</s:elseif>
								<s:elseif test="top instanceof java.sql.Timestamp">
									<td><s:date name="top" format="dd/MM/yyyy HH:mm" /></td>
								</s:elseif>
								<s:elseif test="top instanceof java.lang.Boolean">
									<td align="right"><s:if test="top">YES</s:if><s:else>NO</s:else></td>
								</s:elseif>
								<s:else>
									<td align="right"><s:property value="top"/></td>
								</s:else>
							</s:iterator>
						</tr>
					</s:iterator>
				</table>				
			</s:else>
		</div>
	</div>
</div>
<script>
	function updateGenerateButton() {
		if(document.getElementById('exportData').checked) {
			document.getElementById('generateButton').style.visibility = 'visible';
			document.getElementById('generateButton2').style.visibility = 'hidden';
		
			document.getElementById('generateButton').style.display = 'inline';
			document.getElementById('generateButton2').style.display = 'none';
		} else {
			document.getElementById('generateButton').style.visibility = 'hidden';
			document.getElementById('generateButton2').style.visibility = 'visible';
			
			document.getElementById('generateButton').style.display = 'none';
			document.getElementById('generateButton2').style.display = 'inline';
		}
	};
</script>
