/**
 * 
 */
package com.vasworks.security.service;

import java.util.List;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserRole;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserRoleService.
 * 
 * @author aafolayan
 */
public interface UserRoleService {
	
	/**
	 * Find.
	 * 
	 * @param id the id
	 * 
	 * @return the user role
	 */
	public UserRole find(long id);

	/**
	 * Find by user.
	 * 
	 * @param user the user
	 * 
	 * @return the list< user role>
	 */
	public List<UserRole> findByUser(AppUser user);

	/**
	 * Save.
	 * 
	 * @param userRole the user role
	 * 
	 * @return the string
	 */
	public String save(UserRole userRole);

	/**
	 * Removes the.
	 * 
	 * @param role the role
	 */
	public void remove(UserRole role);

	/**
	 * Find all.
	 * 
	 * @return the list< user role>
	 */
	public List<UserRole> findAll();

	/**
	 * Find all.
	 * 
	 * @param start the start
	 * @param maxResults the max results
	 * 
	 * @return the list< user role>
	 */
	public List<UserRole> findAll(int start, int maxResults);
}
