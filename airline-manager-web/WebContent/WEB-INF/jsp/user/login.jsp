<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Login</title>
</head>
<body onload="changeColor('alertDivId');">

<div style="margin: 0px auto 0px auto; width: 400px; padding-top: 10%;">
<s:include value="login-form.jsp" />

<p>Contact <a href="mailto:neeyeed@gmail.com" title="Site administrator">VasWorks Nigeria Limited</a> for help.</p>

<c:if test="${not empty param.login_error}">
	<div id="errorMessages"><span class="errorMessage">Invalid user name or password!</span></div>
</c:if>
</div>

<script type="text/javascript">
	$('j_username').focus();
</script>
</body>
</html>