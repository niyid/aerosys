/**
 * 
 */
package com.vasworks.security;

import java.util.Date;

import javax.naming.CommunicationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vasworks.security.kerberos.KerberosAuthenticationToken;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserStatus;
import com.vasworks.security.service.AuthenticationService;
import com.vasworks.security.service.UserService;

/**
 * VasWorks Authentication module authenticates users against ActiveDirectory or other LDAP. After a successful "bind" to the directory, user's data is loaded from
 * table AppUser. If there's no record of that user in the database, but user authenticated successfully, a record is created for that user.
 * 
 * @author developer
 * 
 */
public class UserAuthentication implements AuthenticationProvider {
	private static final Log log = LogFactory.getLog(UserAuthentication.class);
	private UserService userService;
	/** Allow automatic import from LDAP */
	private boolean automaticImportAllowed = true;
	/** Authentication service */
	private AuthenticationService ldapAuthenticationService = null;

	/**
	 * This setter has been deprecated, application identifier is no longer required.
	 * 
	 * @param applicationIdentifier the applicationIdentifier to set
	 */
	@Deprecated
	public void setApplication(String application) {
	}

	public UserAuthentication(UserService userService) {
		this.userService = userService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.providers.AuthenticationProvider#authenticate (org.springframework.security.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		Object credentials = authentication.getCredentials();
		if (principal instanceof String && credentials instanceof String) {
			String username = (String) principal;
			String sAMAccountName = username != null ? username.toLowerCase() : null;
			String domainName = null;
			if (username != null && username.split("\\\\").length == 2) {
				String[] loginSplit = username.split("\\\\");
				domainName = loginSplit[0];
				sAMAccountName = loginSplit[1];
				log.info("Authenticating domain: " + domainName + " Username: " + sAMAccountName);
			}
			String password = (String) credentials;

			log.info("Authenticating username '" + username + "' with password length " + password.length());
			if (username.trim().length() == 0 || password == null || password.length() == 0) {
				log.info("Not allowing blank username");
				throw new BadCredentialsException("Provide username and password!");
			}

			// load user by username
			AppUser userdetails = userService.loadUserByUsername(sAMAccountName);

			if (userdetails == null && isAutomaticImportAllowed()) {
				// we don't have that user in the system just yet
				// let's try authenticating the user against LDAP
				log.info("AppUser " + sAMAccountName + " not registered with applicationIdentifier, trying to import with import service.");
				synchronized (this) {
					userdetails = userService.importUser(sAMAccountName);
				}
			} else if (!isAutomaticImportAllowed()) {
				log.warn("AppUser " + sAMAccountName + " not registered with application, not trying LDAP because 'automaticImportAllowed=false'");
			}

			if (userdetails == null) {
				throw new UsernameNotFoundException("AppUser not registered with application.");
			}

			if (userdetails != null) {
				// user registered with local applicationIdentifier, authenticate
				log.info("AppUser " + sAMAccountName + " registered with applicationIdentifier, authenticating.");

				if (userdetails.getStatus() != UserStatus.ENABLED) {
					log.error("The account has been disabled. Status is: " + userdetails.getStatus() + ", needs to be " + UserStatus.ENABLED);
					throw new BadCredentialsException("The account has been disabled.");
				}

				if (authentication instanceof KerberosAuthenticationToken) {
					doKerberosLogin(authentication, userdetails);
				} else {
					switch (userdetails.getAuthenticationType()) {
					case PASSWORD:
						log.info("AppUser " + sAMAccountName + " set to authenticate with PASSWORD.");
						if (!userService.isPasswordValid(userdetails, password)) {
							log.warn("Invalid password for user " + sAMAccountName);
							userdetails.setLastLoginFailed(new Date());
							userService.updateLoginData(userdetails);
							userdetails = null;
							throw new BadCredentialsException("Login failed!");
						} else {
							log.info("AppUser " + username + " authenticated with PASSWORD method.");
						}
						break;
					case LDAP:
					default:
						log.info("AppUser " + sAMAccountName + " set to authenticate with LDAP.");
						try {
							try {
								if (ldapAuthenticationService.authenticate(username, password, userdetails)) {
									log.info("AppUser " + username + " authenticated with LDAP method.");
									// cache credentials
									log.info("Caching credentials for " + username);
									// set password, but don't change authentication type!
									this.userService.setPassword(userdetails, password, false);
								} else
									throw new BadCredentialsException("LDAP authentication failed.");
							} catch (CommunicationException e) {
								log.info("Communication exception thrown. Trying cached credentials.");
								if (userService.isPasswordValid(userdetails, password)) {
									log.info("Cached credentials valid");
								} else
									throw new BadCredentialsException("Cached credentials invalid.");
							} catch (Exception e) {
								log.error(e);
								throw new BadCredentialsException("LDAP authentication failed.", e);
							}
						} catch (BadCredentialsException e) {
							userdetails.setLastLoginFailed(new Date());
							userService.updateLoginData(userdetails);
							userdetails = null;
							throw e;
						}
						break;
					}
				}
			}

			if (userdetails != null) {
				if (log.isInfoEnabled())
					log.info("AppUser " + username + " fully authenticated.");
				userdetails.setLastLogin(new Date());
				userService.updateLoginData(userdetails);

				// force load roles
				userdetails.setRoles(userService.getUserRoles(userdetails));
				
				log.info("AppUser authorities being initialized...");
				userdetails.getAuthorities();

				if (log.isInfoEnabled())
					log.info("AppUser " + username + " successfully logged in.");
				Authentication usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userdetails, userdetails, userdetails.getAuthorities());
				return usernamePasswordAuthToken;
			}
		}
		return authentication;

	}

	/**
 	 * Kerberos authentication already happens by this point, no need to check passwords.
	 *
	 * @param authentication
	 * @param userdetails
	 */
	private void doKerberosLogin(Authentication authentication, AppUser userdetails) {
		log.debug("Kerberos authentication accepted between " + authentication + " and " + userdetails);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.providers.AuthenticationProvider#supports (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(arg0));
	}

	/**
	 * @return the automaticImportAllowed
	 */
	public boolean isAutomaticImportAllowed() {
		return this.automaticImportAllowed;
	}

	/**
	 * @param automaticImportAllowed the automaticImportAllowed to set
	 */
	public void setAutomaticImportAllowed(boolean automaticImportAllowed) {
		this.automaticImportAllowed = automaticImportAllowed;
	}

	/**
	 * @param ldapAuthenticationService the ldapAuthenticationService to set
	 */
	public void setLdapAuthenticationService(AuthenticationService ldapAuthenticationService) {
		this.ldapAuthenticationService = ldapAuthenticationService;
	}
}
