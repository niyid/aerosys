/**
 * 
 */
package com.vasworks.security.service;

import java.util.List;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserSupervisor;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserSupervisionService.
 * 
 * @author aafolayan
 */
public interface UserSupervisionService {

	/**
	 * Get list of users that have delegated access to this user.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the supervised users
	 */
	@Deprecated
	public List<AppUser> getSupervisedUsers(AppUser user, String application);
	
	/**
	 * Get list of users that have delegated access to this user.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the supervised users
	 */
	public List<AppUser> getSupervisedUsers(AppUser user);

	/**
	 * Get list of users that this user has delegated access to.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the supervisors
	 */
	@Deprecated
	public List<AppUser> getSupervisors(AppUser user, String application);
	
	/**
	 * Get list of users that this user has delegated access to.
	 * 
	 * @param user the user
	 * @param application the application
	 * 
	 * @return the supervisors
	 */
	public List<AppUser> getSupervisors(AppUser user);

	/**
	 * Delegate access to another user.
	 * 
	 * @param owner the owner
	 * @param application the application
	 * @param supervisor the supervisor
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	void addSupervisor(AppUser owner, AppUser supervisor, String application) throws UserServiceException;
	

	/**
	 * Delegate access to another user.
	 * 
	 * @param owner the owner
	 * @param application the application
	 * @param supervisor the supervisor
	 * 
	 * @throws UserServiceException the user service exception
	 */
	void addSupervisor(AppUser owner, AppUser supervisor) throws UserServiceException;

	/**
	 * Delete supervisor.
	 * 
	 * @param user the user
	 * @param supervisor the supervisor
	 * @param application the application
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	void deleteSupervisor(AppUser user, AppUser supervisor, String application) throws UserServiceException;
	
	/**
	 * Delete supervisor.
	 * 
	 * @param user the user
	 * @param supervisor the supervisor
	 * @param application the application
	 * 
	 * @throws UserServiceException the user service exception
	 */
	void deleteSupervisor(AppUser user, AppUser supervisor) throws UserServiceException;


	/**
	 * Find supervisor.
	 * 
	 * @param user the user
	 * @param supervisor the supervisor
	 * @param application the application
	 * 
	 * @return the user supervisor
	 * 
	 * @throws UserServiceException the user service exception
	 */
	@Deprecated
	UserSupervisor findSupervisor(AppUser user, AppUser supervisor, String application) throws UserServiceException;
	
	/**
	 * Find supervisor.
	 * 
	 * @param user the user
	 * @param supervisor the supervisor
	 * @param application the application
	 * 
	 * @return the user supervisor
	 * 
	 * @throws UserServiceException the user service exception
	 */
	UserSupervisor findSupervisor(AppUser user, AppUser supervisor) throws UserServiceException;

	/**
	 * Switch user.
	 * 
	 * @param user the user
	 * @param supervisor the supervisor
	 */
	void switchUser(AppUser user, UserSupervisor supervisor);

	/**
	 * Unswitch user.
	 * 
	 * @param user the user
	 */
	void unswitchUser(AppUser user);

	/**
	 * Get a list of all supervised users (no matter who the supervisor is) for an application.
	 * 
	 * @param application the application
	 * 
	 * @return the supervised users
	 */
	@Deprecated
	public List<AppUser> getSupervisedUsers(String application);
	

	/**
	 * Get a list of all supervised users (no matter who the supervisor is) for an application.
	 * 
	 * @param application the application
	 * 
	 * @return the supervised users
	 */
	public List<AppUser> getSupervisedUsers();


}
