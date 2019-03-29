/**
 * vasworks-common Jun 18, 2009
 */
package com.vasworks.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.AppUser.AuthenticationType;
import com.vasworks.security.service.AuthenticationService;
import com.vasworks.security.service.UserImportService;
import com.vasworks.security.service.UserServiceException;

/**
 * The Class LDAPUserService.
 * 
 * @author developer
 */
public class LDAPUserService implements UserImportService, AuthenticationService {

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(LDAPUserService.class);

	/** The ldap host. */
	private String ldapHost;

	/** The timeout. */
	private int timeout = 10000;

	/** LDAP authentication username for lookups. */
	private String username;

	/** LDAP password for lookups. */
	private String password;

	/**
	 * Find user.
	 * 
	 * @param username the username
	 * 
	 * @return the user
	 * 
	 * @see com.vasworks.security.service.UserImportService#findUser(java.lang.String)
	 */
	@Override
	public AppUser findUser(String username) {
		try {
			NamingEnumeration<SearchResult> results = searchUser(username, true);
			SearchResult searchResult = null;
			if (results.hasMore()) {
				searchResult = (SearchResult) results.next();
			}
			if (searchResult != null) {
				LOG.info("Found object: " + searchResult.getName() + " " + searchResult);
			}
			if (results.hasMoreElements() && results.hasMore()) {
				results.close();
				throw new UserServiceException("LDAP has several users with username=" + username + ". Don't know what to do!");
			}
			if (searchResult == null) {
				LOG.debug("Search found no matching users for query: " + username);
				return null;
			} else {
				AppUser user = convertToUser(searchResult);
				if (user.getUsername()!=null) { 
					if (user.getMail()==null) {
						user.setMail(user.getUsername() + "@localhost");
						LOG.warn("AppUser has no email address configured, using " + user.getMail());
					}
					return user;
				} else
					return null;
			}
		} catch (NamingException e) {
			LOG.error(e);
			return null;
		} catch (UserServiceException e) {
			LOG.error(e);
			return null;
		}
	}

	/**
	 * @see com.vasworks.security.service.UserImportService#findAll(java.lang.String)
	 */
	@Override
	public List<AppUser> findAll(String filter) {
		List<AppUser> ldapSearch = new ArrayList<AppUser>();
		try {
			NamingEnumeration<SearchResult> results = searchUser(filter, false);
			while (results.hasMore()) {
				SearchResult searchResult = (SearchResult) results.next();
				AppUser user = convertToUser(searchResult);
				if (user.getUsername()!=null && user.getMail()!=null)
					ldapSearch.add(user);
			}
		} catch (NamingException e) {
			LOG.error(e);
			return null;
		} catch (UserServiceException e) {
			LOG.error(e);
			return null;
		}

		Collections.sort(ldapSearch, new Comparator<AppUser>() {
			/**
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(AppUser arg0, AppUser arg1) {
				return arg0.getFullName().compareTo(arg1.getFullName());
			}
		});
		return ldapSearch;
	}

	/**
	 * Search for user. Only works fine with unique usernames!
	 * 
	 * @param username the username2
	 * 
	 * @return the search result
	 * 
	 * @throws NamingException the naming exception
	 * @throws UserServiceException When error occurs in searching for unique user
	 */
	private NamingEnumeration<SearchResult> searchUser(String filter, boolean fullMatch) throws NamingException, UserServiceException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, this.ldapHost);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, this.username);
		env.put(Context.SECURITY_CREDENTIALS, this.password);
		env.put(Context.REFERRAL, "follow");
		env.put("com.sun.jndi.ldap.read.timeout", "" + this.timeout);
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);

			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			LOG.info("Search LDAP for user: " + filter);
			NamingEnumeration<SearchResult> results;
			if (fullMatch)
				results= ctx.search("", "(&(|(sAMAccountName=" + filter + ")(mail=" + filter + "))(objectclass=user))",
					controls);
			else
				results= ctx.search("", "(&(|(sAMAccountName=*" + filter + "*)(mail=*" + filter + "*))(objectclass=user))",
						controls);
			return results;

		} catch (javax.naming.AuthenticationException e) {
			LOG.warn("LDAP authentication failed for " + this.username + ". Details: " + e);
			throw (e);
		} catch (CommunicationException up) {
			LOG.error(up);
			throw up;
		} catch (NamingException e) {
			LOG.error(e);
			throw e;
		}
	}

	/**
	 * Convert to user.
	 * 
	 * @param searchResult the search result
	 * 
	 * @return the user
	 */
	private AppUser convertToUser(SearchResult searchResult) {
		if (searchResult == null) {
			LOG.debug("Will not convert null search result to AppUser object");
			return null;
		}

		// register user
		AppUser newUser = new AppUser();
		// register user data
		LOG.debug("Populating user object data.");
		fillUserData(newUser, searchResult);
		// set default authentication type
		LOG.debug("Setting auth type to LDAP");
		newUser.setAuthenticationType(AuthenticationType.LDAP);
		// return new user instance
		LOG.debug("AppUser data converted. Returning user object.");
		return newUser;
	}

	/**
	 * Copies sAMAccountName, sn, givenName, mail, displayName, department, description.
	 * 
	 * @param user the user
	 * @param searchResult the search result
	 */
	private void fillUserData(AppUser user, SearchResult searchResult) {
		Attributes attributes = searchResult.getAttributes();
		try {
			user.setUserName(attributes.get("sAMAccountName").get().toString().trim());
			LOG.debug("Username: " + user.getUsername());
		} catch (Exception e2) {
			LOG.debug("LDAP username: " + e2.getMessage());
		}
		try {
			user.setLastName(attributes.get("sn").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP sn: " + e2.getMessage());
		}
		try {
			user.setFirstName(attributes.get("givenName").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP givenName: " + e2.getMessage());
		}
		try {
			user.setMail(attributes.get("mail").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP mail: " + e2.getMessage());
		}
		try {
			user.setDisplayName(attributes.get("displayName").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP displayName: " + e2.getMessage());
		}
		try {
			user.setDepartment(attributes.get("department").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP department: " + e2.getMessage());
		}
		try {
			user.setDescription(attributes.get("description").get().toString().trim());
		} catch (Exception e2) {
			LOG.debug("LDAP description: " + e2.getMessage());
		}
		try {
			String staffId = attributes.get("extensionAttribute1").get().toString().trim();
			if (staffId == null || staffId.length() == 0 || staffId.equalsIgnoreCase("E00000"))
				staffId = null;
			else
				LOG.warn("Staff ID for " + user.getDisplayName() + ": " + staffId);
			user.setStaffId(staffId);
		} catch (Exception e2) {
			LOG.debug("LDAP extensionAttribute1: " + e2.getMessage());
		}

		try {
			if (attributes.get("mailNickname") != null) {
				NamingEnumeration<?> aliases = attributes.get("mailNickname").getAll();
				while (aliases.hasMore()) {
					String alias = aliases.next().toString();
					user.addAlias(alias);
				}

				aliases = attributes.get("proxyAddresses").getAll();
				while (aliases.hasMore()) {
					String alias = aliases.next().toString();
					if (alias.startsWith("SMTP:") || alias.startsWith("smtp:")) {
						user.addAlias(alias.substring(alias.indexOf(':') + 1));
					}
				}
			}
		} catch (NamingException e) {
			LOG.debug("LDAP mailNickname: " + e);
		}
	}

	/**
	 * Find users.
	 * 
	 * @return the list< user>
	 * 
	 * @see com.vasworks.security.service.UserImportService#findUsers()
	 */
	@Override
	public List<AppUser> findUsers() {
		// TODO Find all users in LDAP
		return null;
	}

	/**
	 * Authenticate.
	 * 
	 * @param username the username
	 * @param password the password
	 * 
	 * @return true, if authenticate
	 * @throws CommunicationException If communication with LDAP host is down
	 * 
	 * @see com.vasworks.security.service.AuthenticationService#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate(String username, String password, AppUser user) throws CommunicationException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, this.ldapHost);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (username != null && username.contains("\\")) {
			LOG.info("Authenticating against LDAP: " + username);
			env.put(Context.SECURITY_PRINCIPAL, username);
		} else {
			LOG.info("Authenticating against LDAP: " + username);
			env.put(Context.SECURITY_PRINCIPAL, username);
		}
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put("com.sun.jndi.ldap.read.timeout", "" + this.timeout);
		try {
			new InitialDirContext(env);
			
			//TODO need to fetch user object from LDAP
			//TODO if fetched, then update some properties of {@link AppUser) entity
			
			return true;
		} catch (javax.naming.AuthenticationException e) {
			LOG.warn("LDAP authentication failed for " + username + ". Details: " + e);
		} catch (CommunicationException up) {
			LOG.error(up);
			throw up;
		} catch (NamingException e) {
			LOG.error(e);
		}

		return false;
	}
	
	/**
	 * Sets the ldap host.
	 * 
	 * @param ldapHost the ldapHost to set
	 */
	public void setLdapHost(String ldapHost) {
		this.ldapHost = ldapHost;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the timeout.
	 * 
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
