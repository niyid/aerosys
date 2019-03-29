<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Training Programs</div>
		<div class="panel-body" id="trainingListInnerDiv">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="50%">Program Title</td>
						<td width="10%" align="right">Completion</td>
						<td width="10%" align="right">Expiration</td>
						<td width="25%" align="right">Documentation</td>
						<td width="5%"></td>
					</tr>
				</thead>
				<s:iterator value="trainings">
					<tr>
						<td><s:property value="trainingType.description" /></td>
						<td align="right"><s:date name="completionDate" format="dd/mm/yyyy" /></td>
						<td align="right"><s:date name="expirationDate" format="dd/mm/yyyy" /></td>
						<td align="right">
							<s:url var="fileDownloadUrl" action="fileView" namespace="/download">
								<s:param name="documentId" value="%{documentFile.id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="trainingFormDiv">Download</sj:a>
						</td>
						<td align="right">
							<s:url var="selUrl" action="trainingRecord!select" namespace="/setup/ajax">
								<s:param name="trainingId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="trainingFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>