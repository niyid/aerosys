<%@ include file="/common/taglibs.jsp"%>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Routes</div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<td width="90%">Description</td>
						<td width="10%"></td>
					</tr>
				</thead>
				<s:iterator value="routes">
					<tr>
						<td><s:property value="description" /></td>
						<td>
							<s:url var="selUrl" action="routeRecord!select" namespace="/operator/ajax" escapeAmp="false">
								<s:param name="routeId" value="%{id}" />
							</s:url>
							<sj:a cssClass="btn btn-primary btn-lg" href="%{selUrl}" targets="routeFormDiv"><span class="glyphicon glyphicon-edit"></span>Edit</sj:a>
						</td>
					</tr>
				</s:iterator>
			</table>		
		</div>
	</div>
</div>