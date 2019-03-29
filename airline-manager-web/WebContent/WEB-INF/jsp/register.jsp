<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<title>Register</title>
</head>
<body>
<div class="panel-group">
	<div class="panel panel-primary">
		<div class="panel-heading">Enter Details</div>
		<div class="panel-body">
			<s:form action="register!save" validate="true" method="post" namespace="/" theme="xhtml">
				<s:textfield label="First Name" name="newUser.user.firstName" required="true" />
				<s:textfield label="Last Name" name="newUser.user.lastName" required="true" />
				<s:textfield label="E-Mail" name="newUser.user.mail" required="true" />
				<s:password label="Password" name="newUser.user.password" required="true" />
				<s:password label="Confirm Password" name="pwdConfirm" required="true" />
				<s:submit id="registerButton" value="Register" />
			</s:form>
		</div>
	</div>
</div>
</body>
</html>
