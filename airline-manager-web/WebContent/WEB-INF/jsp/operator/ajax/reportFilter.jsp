<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Parameters</div>
		<div class="panel-body" id="bookingListInnerDiv">
			<s:form id="reportForm" name="reportForm" action="showReportPage" namespace="/operator" theme="xhtml" method="post">
				<sj:datepicker name="fromDate" displayFormat="dd/mm/yy" />
				<sj:datepicker name="toDate" displayFormat="dd/mm/yy" />
				<s:checkbox name="exportData" />
				<sj:submit button="true" value="Save" buttonIcon="ui-icon-gear" targets="reportListDiv" onCompleteTopics="refreshReportListDiv" effect="highlight" effectDuration="500" />
			</s:form>			
		</div>
	</div>
</div>

	<s:include value="/common/msg.jsp" />
