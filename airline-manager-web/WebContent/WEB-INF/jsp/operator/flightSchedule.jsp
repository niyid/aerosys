<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Flight Schedule</title>
</head>
<body>
	<div class="panel-group">
		<div class="panel panel-primary">
			<div class="panel-body">
				<div class="container">
		           <div id="flightScheduleStateDiv" class="row bs-wizard" style="border-bottom:0;">
		               <s:iterator value="scheduleStatuses" status="status">
			               <div class="col-xs-3 bs-wizard-step <s:property value="state" />">
			                 <div class="text-center bs-wizard-stepnum"><s:property value="%{status.index + 1}"/>. <s:property value="statusName" /></div>
			                 <div class="progress"><div class="progress-bar"></div></div>
			                 <a href="#" class="bs-wizard-dot"></a>
			               </div>
		               </s:iterator>
		           </div>
				</div>		
			</div>
		</div>
	</div>		
	<table>
		<tr>
			<td width="40%">
				<s:url var="flightScheduleFormUrl" action="flightScheduleRecord" namespace="/operator/ajax" />
				<sj:div id="flightScheduleFormDiv" href="%{flightScheduleFormUrl}" />
			</td>
			<td>
				<s:url var="flightScheduleListUrl" action="flightScheduleList" namespace="/operator/ajax" />
				<sj:div id="flightScheduleListDiv" href="%{flightScheduleListUrl}" reloadTopics="reloadFlightScheduleListDiv" />
			</td>
		</tr>
	</table>
	
	<sj:dialog id="crewDialog" autoOpen="false" modal="true" title="Flight Crew" width="800" />
	<sj:dialog id="activityDialog" autoOpen="false" modal="true" title="Crew Activities" width="800" />
	<img id="indicator" style="position: absolute; top: 50%; left: 50%; display:none;" src="./img/indicator.gif" alt="Processing..." />
<script type="text/javascript" >
	$.subscribe('refreshFlightScheduleListDiv', function(event,data) {
        $('#flightScheduleListDiv').publish('reloadFlightScheduleListDiv');
	});
</script>	
</body>
</html>