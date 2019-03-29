<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Trainers</div>
		<div class="panel-body" id="trainerListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="75%">Name</td>
						<td width="20%" align="right">Designation</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="trainers">
					<tr>
						<td><s:property value="handlerName" /></td>
						<td align="right"><s:property value="trainerDesignation" /></td>
						<td align="right">
							<s:url var="selUrl" action="trainerRecord!select" namespace="/setup/ajax">
								<s:param name="trainerId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="trainerFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>