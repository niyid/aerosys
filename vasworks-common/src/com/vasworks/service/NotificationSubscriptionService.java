/**
 * projecttask.Struts Feb 1, 2010
 */
package com.vasworks.service;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.vasworks.notifications.ApplicationEventNotification.EventNotificationType;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserNotification;

/**
 * @author developer
 *
 */
public interface NotificationSubscriptionService {
	List<Method> listAvailableNotifications();

	/**
	 * @param user
	 * @return
	 */
	List<Method> listAvailableNotifications(AppUser user);

	/**
	 * @param user
	 * @return
	 */
	Hashtable<String, UserNotification> getSubscriptions(AppUser user);

	/**
	 * @param user 
	 * @param subscriptions
	 */
	void updateSubscriptions(AppUser user, Dictionary<String, UserNotification> subscriptions);

	/**
	 * @param eventName
	 * @param eventNotificationType
	 * @param allowedSubscribers
	 * @return
	 */
	List<UserNotification> getUserNotifications(String eventName, EventNotificationType eventNotificationType, List<AppUser> allowedSubscribers);
}
