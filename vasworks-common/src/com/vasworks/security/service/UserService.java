/**
 * 
 */
package com.vasworks.security.service;

import java.util.List;

import com.vasworks.security.model.Preference;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserDelegation;
import com.vasworks.security.model.UserRole;
import com.vasworks.util.PagedResult;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 * 
 * @author developer
 */
public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * Load user by username.
	 * 
	 * @param username the username
	 * 
	 * @return the user
	 * 
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	AppUser loadUserByUsername(String username);

	/**
	 * Find.
	 * 
	 * @param id the id
	 * 
	 * @return the user
	 */
	public AppUser find(long id);

	/**
	 * Save.
	 * 
	 * @param user the user
	 * 
	 * @return the string
	 */
	public void save(AppUser user);

	/**
	 * Removes the.
	 * 
	 * @param id the id
	 * 
	 * @return the string
	 */
	public String remove(long id);

	/**
	 * Find all.
	 * 
	 * @return the list< user>
	 */
	public List<AppUser> findAll();

	/**
	 * Find all.
	 * 
	 * @param start the start
	 * @param maxResults the max results
	 * 
	 * @return the paged result< user>
	 */
	public PagedResult<AppUser> findAll(int start, int maxResults);

	/**
	 * Find user by lookup value. Will automatically try to import user data.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return the user
	 */
	public AppUser lookup(String identifier);

	/**
	 * Find user by lookup value.
	 * 
	 * @param identifier the identifier
	 * @param allowImport allow user import
	 * 
	 * @return the user
	 */
	public AppUser lookup(String identifier, boolean allowImport);

	/**
	 * Get list of users that have delegated access to this user. This method has been deprecated in favour of {@link #getDelegatedFrom(AppUser)} that requires no
	 * application identifier.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the delegated from
	 */
	@Deprecated
	public List<AppUser> getDelegatedFrom(AppUser user, String application);

	/**
	 * Get list of users that have delegated access to this user.
	 * 
	 * @param user
	 */
	List<AppUser> getDelegatedFrom(AppUser user);

	/**
	 * Get list of users that this user has delegated access to. This method has been deprecated in favour of {@link #getDelegatedTo(AppUser)} that requires no
	 * application identifier.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the delegated to
	 */
	@Deprecated
	public List<AppUser> getDelegatedTo(AppUser user, String application);

	/**
	 * Get list of users that this user has delegated access to.
	 * 
	 * @param user the user
	 * 
	 * @return the delegated to
	 */
	List<AppUser> getDelegatedTo(AppUser user);

	/**
	 * Delegate access to another user.
	 * 
	 * @param owner the owner
	 * @param delegate the delegate
	 * @param application the application
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	void addDelegation(AppUser owner, AppUser delegate, String application) throws UserServiceException;

	void addDelegation(AppUser owner, AppUser delegate) throws UserServiceException;

	/**
	 * Delete delegation.
	 * 
	 * @param user the user
	 * @param identifier the identifier
	 * @param application the application
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	void deleteDelegation(AppUser user, String identifier, String application) throws UserServiceException;

	void deleteDelegation(AppUser user, String identifier) throws UserServiceException;

	/**
	 * Find delegation.
	 * 
	 * @param user the user
	 * @param identifier the identifier
	 * @param application the application
	 * 
	 * @return the user delegation
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	UserDelegation findDelegation(AppUser user, String identifier, String application) throws UserServiceException;

	UserDelegation findDelegation(AppUser user, String identifier) throws UserServiceException;

	/**
	 * Switch user.
	 * 
	 * @param user the user
	 * @param delegation the delegation
	 */
	void switchUser(AppUser user, UserDelegation delegation);

	/**
	 * Switch user.
	 * 
	 * @param targetUser the target user
	 */
	void switchUser(AppUser targetUser);

	/**
	 * Unswitch user.
	 */
	void unswitchUser();

	/**
	 * Sets the password.
	 * 
	 * @param passwd1 the passwd1
	 * @param user the user
	 */
	void setPassword(AppUser user, String passwd1);

	
	/**
	 * Sets the password.
	 * 
	 * @param passwd1 the passwd1
	 * @param user the user
	 * @param changeAuthenticationType Should authentication type be changed as well
	 */
	void setPassword(AppUser user, String passwd1, boolean changeAuthenticationType);

	/**
	 * Clear password.
	 * 
	 * @param user the user
	 */
	void clearPassword(AppUser user);

	/**
	 * Checks if is password valid.
	 * 
	 * @param password the password
	 * @param user the user
	 * 
	 * @return true, if checks if is password valid
	 */
	boolean isPasswordValid(AppUser user, String password);

	/**
	 * Gets the user roles.
	 * 
	 * @param userdetails the userdetails
	 * @param application the application
	 * 
	 * @return the user roles
	 */
	@Deprecated
	List<UserRole> getUserRoles(AppUser user, String application);

	List<UserRole> getUserRoles(AppUser user);

	/**
	 * Send a password request confirmation email to user. When they click the link, @generatePassword(AppUser) will take care of the rest.
	 * 
	 * @param user AppUser\
	 * 
	 * @return key to be used to generate a password
	 */
	String requestPassword(AppUser user);

	/**
	 * Generate a new random password for the user and send it to their email. This is the stage two of password request system.
	 * 
	 * @param user the user
	 * @param key the that was generated in password request
	 * 
	 * @return new user password
	 */
	String generatePassword(AppUser user, String key);

	/**
	 * Find by role.
	 * 
	 * @param application Application name
	 * @param role Role name
	 * 
	 * @return the list< user>
	 */
	@Deprecated
	List<AppUser> findByRole(String application, String role);

	List<AppUser> findByRole(String role);

	/**
	 * Checks if is user switched.
	 * 
	 * @return true, if is user switched
	 */
	boolean isUserSwitched();

	/**
	 * @param username
	 * @return
	 */
	AppUser importUser(String username);

	/**
	 * Find users matching the filter string
	 */
	PagedResult<AppUser> findAll(int startAt, int maxResults, String filter, boolean includeImportService);

	/**
	 * @param lookup
	 * @return
	 */
	List<AppUser> findByName(String lookup, int maxResults);
	
	/**
	 * @param staffID
	 * @return
	 */
	AppUser findByStaffID(String staffID);

	/**
	 * Get all user roles defined in this application
	 */
	List<String> getUserRoles();

	/**
	 * Find all users with a particular role
	 * 
	 * @param role
	 * @param startAt
	 * @param maxResults
	 * @return
	 */
	PagedResult<AppUser> findByRole(String role, int startAt, int maxResults);

	void updateLoginData(AppUser user);
	
	public void addPreference(Preference pref) throws Exception;
	
	public void addPreference(AppUser user, String key, Object value) throws Exception;
	
	public void setPreference(AppUser user, String key, Object value);
	
	public void setPreference(Long id, Object value);
	
	public Preference getPreference(AppUser user, String key);
	
	public Preference getPreference(Long id);
}
