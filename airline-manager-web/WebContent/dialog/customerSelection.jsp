<%@ include file="/common/taglibs.jsp"%>
			<div class="modal-header">
		        <h2 class="modal-title"><span class="glyphicon glyphicon-lock"></span> Select Customer</h2>
		    </div>
		    <div class="modal-body">
				<s:form id="customerSelectionForm" action="customerSelection!save" namespace="/operator/ajax">
				<div class="panel-group">
					<div class="panel panel-primary">
						<div class="panel-heading">Reservation For</div>
						<div class="panel-body">
							<s:select 
								name="clientId" 
								headerKey=""
								headerValue="----------Select----------"
								list="postpaidCustomerLov"
								listKey="id"
								listValue="organizationName"
								emptyOption="false"
								required="true"
								value="#session.client_id"
							/>																													
						</div>
				    	<s:url var="clientDeselectUrl" action="customerSelection!deselect" namespace="/operator" />
				    	<s:if test="#session.client_name != null">
					    	<sj:a button="true" href="%{clientDeselectUrl}" targets="customerSelectionDialog" onClickTopics="refreshHome,blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500"><span class="glyphicon glyphicon-remove"></span>Deselect Customer</sj:a>
				    	</s:if>
					</div>
				</div>
				</s:form>
				<s:include value="/common/msg.jsp" />	
		    </div>
		    <div class="modal-footer">
		    	<sj:submit formIds="customerSelectionForm" button="true" value="Select" buttonIcon="ui-icon-gear" targets="customerSelectionDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" effect="highlight" effectDuration="500" />
		    </div>
