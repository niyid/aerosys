<%@ include file="/common/taglibs.jsp"%>

<div id="loginModalDiv" style="width:30%;">
	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
		<div class="alert alert-warning">
	        Your login attempt was not successful: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
	    </div>
	</c:if>
	<s:actionerror theme="bootstrap" />
    <s:actionmessage theme="bootstrap" />
    <form class="form-signin" id="loginForm" action="j_spring_security_check" method="post" namespace="/">       
	  <input type="text" name="j_username" id="j_username" class="form-control" placeholder="User ID/Email" required autofocus />
	  <input type="password" name="j_password" id="j_password" class="form-control" placeholder="Password" required />
    </form>
  	<sj:submit id="loginButton" formIds="loginForm" cssClass="btn btn-lg btn-primary btn-block" button="true" value="Sign in" effect="highlight" effectDuration="500" />
</div>
