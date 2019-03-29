/**
 * 
 */
package com.vasworks.security.service;

import java.util.List;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserLookup;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserLookupService.
 * 
 * @author aafolayan
 */
public interface UserLookupService {
	
	/**
	 * Find.
	 * 
	 * @param id the id
	 * 
	 * @return the user lookup
	 */
	public UserLookup find(long id);

	/**
	 * Find by user.
	 * 
	 * @param user the user
	 * 
	 * @return the list< user lookup>
	 */
	public List<UserLookup> findByUser(AppUser user);

	/**
	 * Save.
	 * 
	 * @param userLookup the user lookup
	 * 
	 * @return the string
	 */
	public String save(UserLookup userLookup);

	/**
	 * Removes the.
	 * 
	 * @param lookup the lookup
	 */
	public void remove(UserLookup lookup);

	/**
	 * Find all.
	 * 
	 * @return the list< user lookup>
	 */
	public List<UserLookup> findAll();

	/**
	 * Find all.
	 * 
	 * @param start the start
	 * @param maxResults the max results
	 * 
	 * @return the list< user lookup>
	 */
	public List<UserLookup> findAll(int start, int maxResults);

	/**
	 * Find by example.
	 * 
	 * @param example the example
	 * 
	 * @return the list< user lookup>
	 */
	public List<UserLookup> findByExample(UserLookup example);

	/**
	 * Find by example.
	 * 
	 * @param example the example
	 * @param start the start
	 * @param maxResults the max results
	 * 
	 * @return the list< user lookup>
	 */
	public List<UserLookup> findByExample(UserLookup example, int start, int maxResults);

}
