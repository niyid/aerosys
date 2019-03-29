<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<sj:head jqueryui="true" jquerytheme="flick" />
	<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/script/jquery.ui.map.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/script/jquery.ui.map.services.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/script/jquery.ui.map.extensions.js"></script>
    <title><decorator:title default="Untitled page" /> | <fmt:message key="webapp.name" /></title>
<%@ include file="/common/meta.jsp"%>
<decorator:head />
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<div style="margin-bottom: 10px;" class="noprint">
<table style="width: 100%">
	<tr>
		<td style="width: 20%;">
			<table>
				<tr>
					<td style="vertical-align: top; width: 44px;" rowspan="2"><a href="<c:url value="/" />"><img src="<c:url value='/img/swappaws_icon.png'/>" alt="Logo"
			style="float: left; width: 50px; height: 50px;" /></a>							
					</td>
					<td style="vertical-align: top;">
						<table>
							<tr>
								<td style="vertical-align: top;">
									<img src="<c:url value='/img/swappaws_banner.png'/>" alt="Banner" style="float: left; width: 225px; height: 33px;" />
								</td>
							</tr>
							<tr>
								<td><span class="slogan">...mine for thine.</span></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<security:authorize access="hasAnyRole('POST_ETL','PRE_ETL_POSTPAID','PRE_ETL_PREPAID')">
			<td align="right">
				<span style="white-space: nowrap;">
					<a id="logoutAnchor" href="<s:url value="/j_spring_security_logout" />">Logout</a>
				</span>
			</td>		
		</security:authorize>
	</tr>
</table>
</div>

<div class="noprint" style="border-top: solid 1px black; background-color: #4f81bd; color: #ffffff; min-height: 16px; padding: 3px 10px 2px 10px; font-weight: bold;"><div style="float: right"><s:action name="user-info" namespace="/" executeResult="true" ignoreContextParams="true" /></div><decorator:title
	default="Untitled page" /></div>

<div style="margin: 10px 10px 0px 10px;">

<div id="main"><%@ include file="/common/messages.jsp"%> <decorator:body /></div>
</div>

<jsp:include page="/common/footer.jsp" />
<script type="text/javascript" src="<s:url value="/script/jquery-idleTimeout.js" />"></script>
</body>
</html>
