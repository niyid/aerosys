<%@ include file="/common/taglibs.jsp"%>

	<s:form id="loginForm" action="j_spring_security_check" method="post" namespace="/" theme="xhtml">
		<sjm:textfield name="j_username" label="User" id="j_username" value="%{#request.appUser}" />
	
		<sjm:password name="j_password" label="Password" id="j_password" />
	</s:form>
	
	<sjm:a id="loginButton" button="true" buttonIcon="gear" formIds="loginForm">Login</sjm:a>

