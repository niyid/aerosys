/**
 * vasworks-common-web.struts Jun 18, 2009
 */
package com.vasworks.security.service;

import com.vasworks.security.model.AppUser;


/**
 * @author developer
 *
 */
public interface AuthenticationService {

	/**
	 * Authenticate user with provided credentials.
	 * 
	 * @param username
	 * @param password
	 * @throws Exception 
	 */
	boolean authenticate(String username, String password, AppUser user) throws Exception;	
}
