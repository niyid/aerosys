<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="refresh" content="<s:property value="#session['getMaxInactiveInterval']" />;url=/index.jspx" />
	<sj:head jqueryui="true" jquerytheme="flick" />
    <!-- This files are needed for AJAX Validation of XHTML Forms -->
    <script language="JavaScript" src="${pageContext.request.contextPath}/struts/utils.js" type="text/javascript"></script>
    <script language="JavaScript" src="${pageContext.request.contextPath}/struts/xhtml/validation.js" type="text/javascript"></script>
    <title>
        <decorator:title default="AeroSys" /> 
    </title>
	<%@ include file="/common/meta.jsp"%>
	<decorator:head />
	<sb:head />
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    	@media screen and (max-width: 767px) {
	      .sidenav {
	        height: auto;
	        padding: 15px;
	      	}
	      .row.content {height:auto;} 
    
    	}
    
       	.panel-heading {
       		font-weight:bold;
			background:#054177;
			color:#ffffff;
		}
		
    	.panel-body {
			background:#c2d1f0;
		}
		
		.bg { 
		    /* The image used */
		    background-image: url("${pageContext.request.contextPath}/img/airline_back4.jpg");
		
		    /* Full height */
		    height: 840px;
		    overflow:scroll;
		
		    /* Center and scale the image nicely */
		    background-position: center;
		    background-repeat: no-repeat;
		    background-size: cover;
		}		
  </style>
</head>
<body>
	<s:if test="overdue">
		<sj:dialog id="overdueDialog" autoOpen="true" modal="true" width="1000" position="['center','top']">
			<div class="modal-header">
				<table>
					<tr>
						<td>
					        <h2 class="modal-title"><span class="glyphicon glyphicon-lock"></span> Invoices Overdue</h2>
						</td>
						<td align="right">
					        <s:a id="logoutButtonOverdue" href='/airline-manager-web/j_spring_security_logout'><i class="fa fa-fw fa-power-off"></i>Sign out</s:a>
						</td>
					</tr>
				</table>
		    </div>
		    <div class="modal-body">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<td width="10%">Number</td>
							<td width="10%">Invoice Date</td>
							<td width="10%">Overdue Date</td>
							<td width="10%">Amount</td>
							<td width="10%">Status</td>
							<td width="10%"></td>
						</tr> 
					</thead>
					<s:iterator value="overdueInvoices">
						<tr>
							<td><s:property value="id" /></td>
							<td><s:date name="invoiceDate" format="dd/MM/yyyy" /></td>
							<td><s:date name="overdueDate" format="dd/MM/yyyy" /></td>
							<td><fmt:formatNumber type="currency" currencySymbol="${currency.currencySymbol}" value="${totalAmount}" maxFractionDigits="2" minFractionDigits="2"/></td>
							<td><s:property value="invoiceStatus" /></td>
							<td>
								<s:url var="invoicePaymentUrl" action="popupInvoicePayment" namespace="/" escapeAmp="false">
									<s:param name="invoiceId" value="%{id}" />
									<s:param name="targetDiv" value="overdueDialog" />
								</s:url>						
								<sj:a button="true" href="%{invoicePaymentUrl}" targets="overdueDialog" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff"><span class="glyphicon glyphicon-credit-card"></span>Pay</sj:a>						
							</td>
						</tr>
					</s:iterator>
				</table>				    	
		    </div>
		    <div class="modal-footer">
		    	<sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" cssClass="btn btn-lg btn-primary btn-block" button="true" effect="highlight" effectDuration="500" targets="overdueDialog"><span class="glyphicon glyphicon-credit-card"></span>Pay All</sj:a>
		    </div>
		</sj:dialog>
	</s:if>
	<s:if test="balanceLow">
		<sj:dialog id="lowBalanceDialog" autoOpen="true" modal="true" width="1000" position="['center','top']">
			<div class="modal-header">
				<table>
					<tr>
						<td>
					        <h2 class="modal-title"><span class="glyphicon glyphicon-lock"></span> Balance Low</h2>
						</td>
						<td align="right">
					        <s:a id="logoutButtonOverdue" href='/airline-manager-web/j_spring_security_logout'><i class="fa fa-fw fa-power-off"></i>Sign out</s:a>
						</td>
					</tr>
				</table>
		    </div>
		    <div class="modal-body">
			    Your current balance is <fmt:formatNumber type="currency" currencySymbol="${defaultCurrency.currencySymbol}" value="${customer.depositAmount}" maxFractionDigits="2" minFractionDigits="2"/>. This is below the acceptable limit of <fmt:formatNumber type="currency" currencySymbol="${defaultCurrency.currencySymbol}" value="${customer.depositLowerLimit}" maxFractionDigits="2" minFractionDigits="2"/>.
			    <br/>	
			    <br/>
			    Please contact the Finance Department to top-up.	
		    </div>
		</sj:dialog>
	</s:if>
	<s:if test="!overdue && !balanceLow">
		<div id="topLevelDiv">
			<nav class="navbar navbar-inverse">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNavBar">
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>                        
			      </button>
			    </div>
		
				<s:url var="helperUrl" action="showHelpPage" namespace="/" />
				<s:url var="searchPageUrl" action="showSearchPage" namespace="/" />
				<s:url var="priceListUrl" action="showPriceListPage" namespace="/operator" />
				<s:url var="invoiceListUrl" action="showInvoiceListPage" namespace="/operator" />
				<s:url var="airplanePageUrl" action="showAirplanePage" namespace="/setup" />
				<s:url var="airplaneMakePageUrl" action="showAirplaneMakePage" namespace="/setup" />
				<s:url var="airplaneModelPageUrl" action="showAirplaneModelPage" namespace="/setup" />
				<s:url var="seatPageUrl" action="showSeatPage" namespace="/setup" />
				<s:url var="airlinePageUrl" action="showAirlinePage" namespace="/setup" />
				<s:url var="customerPageUrl" action="showCustomerPage" namespace="/setup" />
				<s:url var="agencyPageUrl" action="showAgencyPage" namespace="/setup" />
				<s:url var="airportPageUrl" action="showAirportPage" namespace="/setup" />
				<s:url var="cityPageUrl" action="showCityPage" namespace="/setup" />
				<s:url var="trainingHandlerUrl" action="showTrainerPage" namespace="/setup" />
				<s:url var="insuranceIssuerUrl" action="showInsurerPage" namespace="/setup" />
				<s:url var="licensingAuthorityUrl" action="showAuthorityPage" namespace="/setup" />
				<s:url var="permissionDefUrl" action="showPermissionDefPage" namespace="/setup" />	
				<s:url var="routePageUrl" action="showRoutePage" namespace="/operator" />
				<s:url var="routePricePageUrl" action="showRoutePricePage" namespace="/operator" />
				<s:url var="flightSchedulePageUrl" action="showFlightSchedulePage" namespace="/operator" />
				<s:url var="flightPageUrl" action="showFlightPage" namespace="/operator" />
				<s:url var="reservationUrl" action="showReservationPage" namespace="/operator" />
				<s:url var="remittanceUrl" action="showRemittanceListPage" namespace="/operator" />
				<s:url var="rebatePageUrl" action="showRebatePage" namespace="/operator" />
				<s:url var="taxPageUrl" action="showTaxPage" namespace="/operator" />
				<s:url var="cashDepositPageUrl" action="showCashDepositPage" namespace="/operator" />
				<s:url var="contextTaxPageUrl" action="showContextTaxPage" namespace="/operator" />
				<s:url var="reportUrl" action="showActivityReportPage" namespace="/operator" />
			
			    <div style="float:left;background-color:white;">
			    	<img style="border-width: 1px; border-style: solid; border-color: red;" src="<c:url value='/img/logo_bristow.png'/>" alt="Logo" vertical-align: top; width: 141px; height: 40px;" />
			    </div>
			    <div class="collapse navbar-collapse" id="mainNavBar">
			      <ul class="nav navbar-nav">
			      	<s:if test="hasAny('POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{reservationUrl}" targets="mainContentDiv">Reservations</sj:a></li>
			      	</s:if>
			      	<s:if test="hasAny('PRE_ETL_POSTPAID')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{priceListUrl}" targets="mainContentDiv">Price-list</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{invoiceListUrl}" targets="mainContentDiv">Invoices</sj:a></li>
			      	</s:if>
			      	<s:if test="hasAny('PRE_ETL_DEPOSIT')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{cashDepositPageUrl}" targets="mainContentDiv">Cash Deposits</sj:a></li>
			      	</s:if>
			      	<security:authorize access="hasAnyRole('ROLE_USER','ROLE_ANONYMOUS')">
				        <li class="active"><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{searchPageUrl}" targets="mainContentDiv">Search</sj:a></li>
			      	</security:authorize>
			      	<security:authorize access="hasAnyRole('ROLE_FINANCE')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{taxPageUrl}" targets="mainContentDiv">Global Charges</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{contextTaxPageUrl}" targets="mainContentDiv">Client Charges</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{routePricePageUrl}" targets="mainContentDiv">Price-list</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{invoiceListUrl}" targets="mainContentDiv">Invoices</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{cashDepositPageUrl}" targets="mainContentDiv">Cash Deposits</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{remittanceUrl}" targets="mainContentDiv">TSCs</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{reportUrl}" targets="mainContentDiv">Reports</sj:a></li>
			      	</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_OPERATIONS')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{routePageUrl}" targets="mainContentDiv">Routes</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{flightPageUrl}" targets="mainContentDiv">Flights</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airlinePageUrl}" targets="mainContentDiv">Airlines</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{customerPageUrl}" targets="mainContentDiv">Customers</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airportPageUrl}" targets="mainContentDiv">Airports</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneMakePageUrl}" targets="mainContentDiv">Makes</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneModelPageUrl}" targets="mainContentDiv">Models</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplanePageUrl}" targets="mainContentDiv">Airplanes</sj:a></li>
					</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{reservationUrl}" targets="mainContentDiv">Reservations</sj:a></li>
					</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{rebatePageUrl}" targets="mainContentDiv">Rebates</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{routePageUrl}" targets="mainContentDiv">Routes</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{flightSchedulePageUrl}" targets="mainContentDiv">Schedules</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{flightPageUrl}" targets="mainContentDiv">Flights</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{reportUrl}" targets="mainContentDiv">Reports</sj:a></li>
					</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_ADMIN')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airlinePageUrl}" targets="mainContentDiv">Airlines</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{customerPageUrl}" targets="mainContentDiv">Customers</sj:a></li>
				 <%--        <li><sj:a href="%{cityPageUrl}" targets="mainContentDiv">Cities</sj:a></li> --%>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airportPageUrl}" targets="mainContentDiv">Airports</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneMakePageUrl}" targets="mainContentDiv">Makes</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneModelPageUrl}" targets="mainContentDiv">Models</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplanePageUrl}" targets="mainContentDiv">Airplanes</sj:a></li>
				  <%--       <li><sj:a href="%{seatPageUrl}" targets="mainContentDiv">Seats</sj:a></li> --%>
					</security:authorize>        
					<security:authorize access="hasAnyRole('ROLE_DISPATCH')">
				 <%--        <li><sj:a href="%{cityPageUrl}" targets="mainContentDiv">Cities</sj:a></li> --%>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airportPageUrl}" targets="mainContentDiv">Airports</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneMakePageUrl}" targets="mainContentDiv">Makes</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplaneModelPageUrl}" targets="mainContentDiv">Models</sj:a></li>
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{airplanePageUrl}" targets="mainContentDiv">Airplanes</sj:a></li>
				  <%--       <li><sj:a href="%{seatPageUrl}" targets="mainContentDiv">Seats</sj:a></li> --%>
					</security:authorize>        
			        <security:authorize access="hasAnyRole('ROLE_AUTHORITY')">
			<%--         <li><sj:a href="%{agencyPageUrl}" targets="mainContentDiv">Agencies</sj:a></li> --%>
			<%--         <li><sj:a href="%{trainingHandlerUrl}" targets="mainContentDiv">Trainers</sj:a></li> --%>
			<%--         <li><sj:a href="%{insuranceIssuerUrl}" targets="mainContentDiv">Insurance</sj:a></li> --%>
			<%--         <li><sj:a href="%{licensingAuthorityUrl}" targets="mainContentDiv">Authorities</sj:a></li> --%>
			        </security:authorize>
			        <s:if test="hasAny('POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID,PRE_ETL_DEPOSIT')">
				        <li><sj:a onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" href="%{helperUrl}" targets="mainContentDiv">Help</sj:a></li>
			        </s:if>
			      	<security:authorize access="isAuthenticated()">
						<li class="dropdown pull-right">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><s:property value="user.fullName" /> (<s:property value="user.organization.organizationName" />)</a>
							<ul class="dropdown-menu">
							 	<li>
									<sj:a id="logoutButton" onClickTopics="blockUIOn" onCompleteTopics="refreshHome,blockUIOff" href='/airline-manager-web/j_spring_security_logout' indicator='indicator' targets="topLevelDiv"><i class="fa fa-fw fa-power-off"></i>Sign out</sj:a>
								</li>
						      	<security:authorize access="hasAnyRole('ROLE_OPERATOR')">
							        <li><s:a id="customerSelectionDialogButton" href='#customerSelectionDialog'><span class="glyphicon glyphicon-user"></span>Choose Customer <s:if test="#session.client_name != null">(<s:property value="#session.client_name" />)</s:if></s:a></li>
							    </security:authorize>
							</ul>
						</li>
					</security:authorize>
					<security:authorize access="isAnonymous()">
						<li><s:a id="loginDialogButton" href='#loginDialog'><span class="glyphicon glyphicon-log-in"></span>Sign in</s:a></li>
					</security:authorize>
			      </ul>
			    </div>
			  </div>
			</nav>
			<div class="bg" id="mainContentDiv" <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>><%@ include file="/common/messages.jsp"%> <decorator:body /></div>
			
			<footer class="container-fluid text-center">
				<jsp:include page="/common/footer.jsp" />
			</footer>
		</div>
		<s:if test="sexp == true">
			<sj:dialog id="loginDialog" autoOpen="true" modal="true" width="800" position="['center','center']">
				<div class="modal-header">
			        <h2 class="modal-title"><span class="glyphicon glyphicon-lock"></span> Session expired</h2>
			    </div>
			    <div class="modal-body">
					<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
						<div class="alert alert-warning">
					        Your login attempt was not successful: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
					    </div>
					</c:if>
					<s:actionerror theme="bootstrap" />
				    <s:actionmessage theme="bootstrap" />
				    <form class="form-signin" id="loginForm" action="j_spring_security_check" method="post" namespace="/">       
						<input type="text" name="j_username" id="j_username" class="form-control" style="height: 40px; font-size: 30pt" placeholder="User ID/Email" required autofocus />
						<input type="password" name="j_password" id="j_password" class="form-control" style="height: 40px; font-size: 30pt" placeholder="Password" required />
				    </form>
			    </div>
			    <div class="modal-footer">
			    	<sj:submit id="loginButton" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" formIds="loginForm" cssClass="btn btn-lg btn-primary btn-block" style="height: 60px; font-size: 30pt" button="true" value="Sign in" effect="highlight" effectDuration="500" />
			    </div>
			</sj:dialog>
		</s:if>
		<s:else>
			<sj:dialog id="loginDialog" autoOpen="false" modal="true" width="800" position="['center','center']">
				<div class="modal-header">
			        <h2 class="modal-title"><span class="glyphicon glyphicon-lock"></span> Please sign in</h2>
			    </div>
			    <div class="modal-body">
					<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
						<div class="alert alert-warning">
					        Your login attempt was not successful: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
					    </div>
					</c:if>
					<s:actionerror theme="bootstrap" />
				    <s:actionmessage theme="bootstrap" />
				    <form class="form-signin" id="loginForm" action="j_spring_security_check" method="post" namespace="/">       
						<input type="text" name="j_username" id="j_username" class="form-control" style="height: 40px; font-size: 30pt" placeholder="User ID/Email" required autofocus />
						<input type="password" name="j_password" id="j_password" class="form-control" style="height: 40px; font-size: 30pt" placeholder="Password" required />
				    </form>
			    </div>
			    <div class="modal-footer">
			    	<sj:submit id="loginButton" onClickTopics="blockUIOn" onCompleteTopics="blockUIOff" formIds="loginForm" cssClass="btn btn-lg btn-primary btn-block" style="height: 60px; font-size: 30pt" button="true" value="Sign in" effect="highlight" effectDuration="500" />
			    </div>
			</sj:dialog>
		</s:else>
	
		<sj:dialog id="customerSelectionDialog" autoOpen="false" modal="true" width="800" position="['center','top']">
			<jsp:include page="/dialog/customerSelection.jsp" />
		</sj:dialog>
	
		<script type="text/javascript">
			$.subscribe('blockUIOn', function(event,data) {
				$.blockUI({ message: '<h1><img src="img/wait.gif" /> Just a moment...</h1>' });
			});
			
			$.subscribe('blockUIOff', function(event,data) {
				$.unblockUI();
			});
	
			$(document).ready(function(){
			    $("#loginDialogButton").click(function() {
			        $("#loginDialog").dialog('open');
			    });
	
			    $("#customerSelectionDialogButton").click(function() {
			        $("#customerSelectionDialog").dialog('open');
			    });
			});
			
			$.subscribe('refreshHome', function(event,data) {
				location.reload();
			});
			
			$('#mainNavBar a').click(function(e) {
				  e.preventDefault();
				  $(this).tab('show');
				});
			
				// store the currently selected tab in the hash value
				$("ul.nav-tabs > li > a").on("shown.bs.tab", function(e) {
				  var id = $(e.target).attr("href").substr(1);
				  window.location.hash = id;
				});
			
				// on load of the page: switch to the currently selected tab
				var hash = window.location.hash;
				$('#mainNavBar a[href="' + hash + '"]').tab('show');
		</script>		
	</s:if>   
</body>
</html>
