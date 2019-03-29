<%@ include file="/common/taglibs.jsp"%>

<s:form id="loginForm" action="j_spring_security_check" method="post" namespace="/" theme="xhtml">
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Enter Credentials</div>
		<div class="panel-body">
			<table>
				<tr>
					<td><s:textfield name="j_username" label="User" id="j_username" value="%{#request.appUser}" /></td>
				</tr>
				<tr>
					<td><s:password name="j_password" label="Password" id="j_password" /></td>
				</tr>
				<tr><td height="20"></td></tr>
				<tr>
					<td>
						<sj:submit id="loginButton" button="true" value="Login" buttonIcon="gear" effect="highlight" effectDuration="500" />
					</td>
				</tr>					
			</table>
		</div>
	</div>
</div>
</s:form>
<script lang="Javascript">
$(document).ready(function () {
	count = $('div[id="topLevelDiv"]').length;
	if(count > 1) {
		location.reload();
	}
});
</script>