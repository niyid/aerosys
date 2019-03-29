/**
 * projecttask.Struts Jan 30, 2010
 */
package com.vasworks.struts;

import com.vasworks.security.model.AppUser;

/**
 * @author developer
 *
 */
public interface ApplicationNotifications {

	/**
	 * @param principal
	 */
	void userLoggedIn(AppUser principal);

	/**
	 * @param message
	 */
	void authenticationFailed(String message);

	/**
	 * @param principal
	 */
	void userLoggingOut(AppUser principal);

	/**
	 * @param principal
	 * @param currentAuthentication
	 */
	void userSwitched(AppUser principal, AppUser delegatedUser);

	/**
	 * @param principal
	 * @param currentAuthentication
	 */
	void userUnswitched(AppUser principal, AppUser delegatedUser);

}
