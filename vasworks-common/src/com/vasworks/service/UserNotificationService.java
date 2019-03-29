/**
 * 
 */
package com.vasworks.service;


import com.vasworks.security.model.AppUser;

/**
 * UserNotificationService defines full Notifications functionality
 * 
 * @author odada
 * @author developer
 */
public interface UserNotificationService extends UserNotificationSender, UserNotificationReader {

	public void updateUser(AppUser user);

}
