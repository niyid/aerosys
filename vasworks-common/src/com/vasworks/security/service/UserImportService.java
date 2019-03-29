/**
 * vasworks-common Jun 18, 2009
 */
package com.vasworks.security.service;

import java.util.List;

import com.vasworks.security.model.AppUser;

/**
 * Defines protocol of importing user data on request.
 * 
 * @author developer
 */
public interface UserImportService {
	/**
	 * Finds user information in underlying system and converts to AppUser object that can then be persisted.
	 * 
	 * @param username
	 * @return
	 */
	AppUser findUser(String username);

	/**
	 * Finds all users in underlying system, converts to AppUser object
	 * 
	 * @return
	 */
	List<AppUser> findUsers();

	/**
	 * @param filter
	 * @return
	 */
	List<AppUser> findAll(String filter);
}
