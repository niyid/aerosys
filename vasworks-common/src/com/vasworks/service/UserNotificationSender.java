/**
 * vasworks-common Jan 30, 2010
 */
package com.vasworks.service;

import java.util.List;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserNotification.NotificationFrequency;
import com.vasworks.security.model.UserNotification.NotificationPriority;

/**
 * {@link UserNotificationSender} defines base notification delivery methods. Classes only sending out messages should use this interface instead of fully
 * functional {@link UserNotificationService}.
 * 
 * @author developer
 */
public interface UserNotificationSender {

	public abstract void sendBroadcast(String appName, List<AppUser> users, String notificationTitle, String notificationMsg);

	public abstract void sendBroadcast(String appName, List<AppUser> users, String notificationTitle, String notificationMsg, NotificationFrequency frequency,
			NotificationPriority priority);

	public abstract void sendBroadcast(String appName, AppUser user, String notificationTitle, String notificationMsg);

	public abstract void sendBroadcast(String appName, AppUser user, String notificationTitle, String notificationMsg, NotificationFrequency frequency,
			NotificationPriority priority);

}