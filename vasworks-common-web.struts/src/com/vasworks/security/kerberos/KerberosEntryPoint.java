/**
 * vasworks-common-web.struts Feb 12, 2010
 */
package com.vasworks.security.kerberos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.vasworks.security.Authorize;
import com.vasworks.security.model.AppUser;

/**
 * @author developer
 * 
 */
public class KerberosEntryPoint implements AuthenticationEntryPoint {
	private static final Log LOG = LogFactory.getLog(KerberosEntryPoint.class);
	private String authenticationFailureUrl;

	/**
	 * @param authenticationFailureUrl the authenticationFailureUrl to set
	 */
	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}

	/**
	 * ExceptionTranslationFilter will populate the HttpSession attribute named AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY with the requested
	 * target URL before calling this method.
	 * 
	 * Implementations should modify the headers on the ServletResponse as necessary to commence the authentication process.
	 * 
	 * @see org.springframework.security.ui.AuthenticationEntryPoint#commence(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 *      org.springframework.security.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException,
			ServletException {
		final HttpServletResponse resp = (HttpServletResponse) response;

		LOG.info("AUTH EXCEPTION: " + authenticationException);

		AppUser user = Authorize.getUser();
		LOG.warn("AppUser: " + user);

		String url = authenticationFailureUrl;
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = ((HttpServletRequest) request).getContextPath() + url;
		}
		resp.sendRedirect(resp.encodeRedirectURL(url));
	}
}
