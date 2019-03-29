<%@ include file="/common/taglibs.jsp"%>
	<div class="panel-group">
		<div class="panel panel-primary">
			<div class="panel-body">
				<div class="container">
		           <div id="bookingStepsDiv" class="row bs-wizard" style="border-bottom:0;">
		               <s:iterator value="bookingSteps">
			               <div class="col-xs-3 bs-wizard-step <s:property value="state" />">
			                 <div class="text-center bs-wizard-stepnum"><s:property value="index"/>. <s:property value="stepName" /></div>
			                 <div class="progress"><div class="progress-bar"></div></div>
			                 <a href="#" class="bs-wizard-dot"></a>
			               </div>
		               </s:iterator>
		           </div>
				</div>		
			</div>
		</div>
	</div>		