/**
 * vasworks-common-web.struts Jan 30, 2010
 */
package com.vasworks.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vasworks.security.model.AppUser;

/**
 * @author developer
 * 
 */
public class WebArchitecture {
	private static final Log LOG = LogFactory.getLog(WebArchitecture.class);
	private ApplicationNotifications applicationNotifications;

	/**
	 * @param projectTaskNotifications
	 */
	public WebArchitecture(ApplicationNotifications applicationNotifications) {
		this.applicationNotifications = applicationNotifications;
	}

	@AfterReturning(pointcut = "execution(* com.vasworks.security.UserAuthentication.authenticate(..))", returning = "authentication")
	public void userLoggedIn(JoinPoint jp, Authentication authentication) {
		LOG.info("AppUser logged in: " + authentication.getPrincipal());
		this.applicationNotifications.userLoggedIn((AppUser) authentication.getPrincipal());
	}

	@AfterThrowing(pointcut = "execution(* com.vasworks.security.UserAuthentication.authenticate(..))", throwing = "ex")
	public void authenticationFailed(JoinPoint jp, Throwable ex) {
		LOG.info("AppUser authentication failed: " + ex.getMessage());
		this.applicationNotifications.authenticationFailed(ex.getMessage());
	}

	@Before("execution(* org.springframework.security.ui.logout.LogoutHandler.logout(..))")
	public void userLoggingOut(JoinPoint jp) {
		// LOG.info("Before " + jp.getKind() + " " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "(..)");
		// for (Object arg : jp.getArgs()) {
		// if (arg==null)
		// LOG.info("\targ null");
		// else
		// LOG.info("\targ " + arg.getClass().getName() + " " + arg);
		// }
		Authentication authentication = (Authentication) jp.getArgs()[2];
		if (authentication != null) {
			LOG.info("AppUser logging out: " + authentication.getPrincipal());
			this.applicationNotifications.userLoggingOut((AppUser) authentication.getPrincipal());
		}
	}

	@AfterReturning("execution(* com.vasworks.security.service.UserService.switchUser(..))")
	public void userSwitched(JoinPoint jp) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser currentAuthentication = (AppUser) authentication.getPrincipal();
		LOG.info("AppUser " + currentAuthentication.getImpersonator().getPrincipal() + " switched to " + currentAuthentication);
		this.applicationNotifications.userSwitched((AppUser) currentAuthentication.getImpersonator().getPrincipal(), currentAuthentication);
	}

	@Before("execution(* com.vasworks.security.service.UserService.unswitchUser())")
	public void userUnswitched(JoinPoint jp) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser currentAuthentication = (AppUser) authentication.getPrincipal();
		LOG.info("AppUser " + currentAuthentication.getImpersonator().getPrincipal() + " unswitched from " + currentAuthentication);
		this.applicationNotifications.userUnswitched((AppUser) currentAuthentication.getImpersonator().getPrincipal(), currentAuthentication);
	}
}
