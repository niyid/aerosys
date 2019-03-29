<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<title>Register</title>
</head>
<body>
	<s:form id="registerForm" action="register!save" validate="true" method="post" namespace="/" theme="xhtml">
		<div data-role="content" id="registerFormDiv">
			<sjm:textfield label="First Name" name="newUser.user.firstName" required="true" />
			<sjm:textfield label="Last Name" name="newUser.user.lastName" required="true" />
			<sjm:textfield label="E-Mail" name="newUser.user.mail" required="true" />
			<sjm:password label="Password" name="newUser.user.password" required="true" />
			<sjm:password label="Confirm Password" name="pwdConfirm" required="true" />
			<sjm:a id="registerButton" button="true" buttonIcon="gear" formIds="registerForm">Submit</sjm:a>
		</div>
	</s:form>
</body>
</html>
