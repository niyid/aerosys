<!DOCTYPE html>

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<sjm:head jqueryui="true" jquerytheme="flick" />
    <title><decorator:title default="Untitled page" /> | <fmt:message key="webapp.name" /></title>
	<decorator:head />
	<%@ include file="/common/meta.jsp"%>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>
       
       <s:url var="loginUrl" action="login" namespace="/" />
       <div data-role="page" id="main" data-theme="a">
            <div data-role="header" data-position="fixed">                
				 <table style="width: 100%">
					<tr>
						<td style="width: 30%;">
							<table>
								<tr>
									<td style="vertical-align: top;">
										<table>
											<tr>
												<td style="vertical-align: top;">
													<img src="<c:url value='/img/logo.png'/>" alt="Logo" style="float: left; width: 30px; height: 30px;" />
												</td>
											</tr>
											<tr>
												<td><span class="slogan">Nigerian Civil Aviation Authority</span></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td style="width: 39%; text-align: center;">
							<h1><decorator:title default="Untitled page" /> <s:if test="user != null"></s:if></h1>
						</td>						
						<td style="width: 31%; text-align: right;">
							<s:if test="user != null">
								<span style="white-space: nowrap;">
									<s:url var="logoutUrl" value="/j_spring_security_logout" />
									<sjm:a id="logoutButton" href="%{#logoutUrl}" button="true" buttonIcon="gear">Logout: <s:property value="user.fullName" /></sjm:a>
								</span>
							</s:if>
							<s:else>
								<span style="white-space: nowrap;">
									<sjm:a id="toLoginButton" href="%{#loginUrl}" button="true" buttonIcon="gear">Login</sjm:a>
								</span>
							</s:else>
						</td>								
					</tr>
				</table>
				
				<s:url var="helperUrl" action="showHelpPage" namespace="/" />
				<s:url var="searchPageUrl" action="searchFilter" namespace="/" />
				<s:url var="seatPageUrl" action="seatList" namespace="/setup/ajax" />
				<s:url var="airportPageUrl" action="airportList" namespace="/setup/ajax" />
				<s:url var="cityPageUrl" action="cityList" namespace="/setup/ajax" />
				
				<s:url var="rebatePageUrl" action="rebateList" namespace="/operator/ajax" />
				<s:url var="taxPageUrl" action="taxList" namespace="/operator/ajax" />
				<s:url var="flightPageUrl" action="flightList" namespace="/operator/ajax" />
				<s:url var="reservationUrl" action="bookingList" namespace="/operator/ajax" />
				<s:url var="reportUrl" action="showReportPage" namespace="/operator" />	

				<s:if test="user != null">
					<sjm:div id="mainTabs" role="navbar">
						<ul>								
							<li><sjm:a href="%{searchPageUrl}" id="searchTab" dataTheme="b">Search</sjm:a></li>
							
							<li><sjm:a href="%{cityPageUrl}" id="cityListTab" dataTheme="b">Cities</sjm:a></li>
							
							<li><sjm:a href="%{seatPageUrl}" id="airplaneSeatListTab" dataTheme="b">Airplane Seats</sjm:a></li>
													
							<li><sjm:a href="%{airportPageUrl}" id="airportTab" dataTheme="b">Airports</sjm:a></li>
							
							<li><sjm:a href="%{helperUrl}" id="helpTab" dataTheme="b">Help</sjm:a></li>	
						</ul>
					</sjm:div>
				</s:if>
            </div><!-- /header -->
 
            <div data-role="content" id="mainDiv" style="border: 1px solid rgb(111, 163, 223); padding: 4px; -moz-background-clip: initial; -moz-background-origin: initial; -moz-background-inline-policy: initial; height: 450px; overflow: auto;">
            	<s:if test="user != null">
					<security:authorize access="!hasAnyRole('ROLE_OPERATOR','ROLE_AUTHORITY')">
						<span style="font-weight: bold; color: red; font-size: 12px;">Please check your email to activate your membership; and to complete your registration.</span>
					</security:authorize>
				</s:if>
            	
                <decorator:body />
        
            </div><!-- /content -->
            <div data-role="footer">
           		<s:if test="user != null">
					<sjm:div id="userTabs" role="navbar">
			            <ul>
							<li><sjm:a id="rebateTab" href="%{rebatePageUrl}" dataTheme="b">Rebates</sjm:a></li>
											
							<li><sjm:a id="taxTab" href="%{taxPageUrl}" dataTheme="b">Taxes and Charges</sjm:a></li>		
																
							<li><sjm:a id="flightTab" href="%{flightPageUrl}" dataTheme="b">Flights</sjm:a></li>
			
							<li><sjm:a id="reservationTab" href="%{reservationUrl}" dataTheme="b">Reservations</sjm:a></li>
																
							<li><sjm:a id="reportTab" href="%{reportUrl}" dataTheme="b">Reports</sjm:a></li>					
						</ul>
					</sjm:div>				
				</s:if>
            </div><!-- /footer -->
        </div><!-- /page -->
         			            
		<s:if test="user == null">
			<div id="loginRequiredDialog" data-role="page">
				<div data-role="header">
					<h1>Login Required</h1>
				</div>
				<div data-role="content">
					<h2>You are currently not logged in.</h2>
					<sjm:a button="true" buttonIcon="gear" href="%{loginUrl}">Log in</sjm:a>
				</div>
			</div>
		</s:if>        
        
</body>
</html>
