/**
 * projecttask.Struts Jan 30, 2010
 */
package com.vasworks.notifications;

import java.util.ArrayList;
import java.util.List;

import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserNotification;

/**
 * The object holds the pre-processed notification data. {@link ApplicationEventNotification}s are generated in ApplicationNotification classes and eventually
 * sent to subscribed users (see {@link UserNotification}) that are listed in {@link #allowedSubscribers}.
 * 
 * @author developer
 */
public class ApplicationEventNotification {
	public enum EventNotificationType {
		SINGLE, MULTIPLE, BROADCAST;
	}

	private Object[] params;
	private String defaultFormat;
	private String eventName;
	private String subject;
	private List<AppUser> allowedSubscribers;
	private EventNotificationType notiticationType;

	/**
	 * @param eventName
	 * @param member
	 * @param defaultFormat
	 * @param defaultFormat
	 * @param params
	 */
	public ApplicationEventNotification(String eventName, String subject, String defaultFormat, Object[] params) {
		this.eventName = eventName;
		this.defaultFormat = defaultFormat;
		this.params = params;
		this.subject = subject;
		this.notiticationType = EventNotificationType.BROADCAST;
	}

	/**
	 * @param eventName2
	 * @param allowedSubscribers
	 * @param subject2
	 * @param defaultFormat2
	 * @param params2
	 */
	public ApplicationEventNotification(String eventName, List<AppUser> allowedSubscribers, String subject, String defaultFormat, Object[] params) {
		this.eventName = eventName;
		this.allowedSubscribers = allowedSubscribers;
		this.subject = subject;
		this.defaultFormat = defaultFormat;
		this.params = params;
		this.notiticationType = EventNotificationType.MULTIPLE;
	}

	/**
	 * @param eventName2
	 * @param singleSubscriber
	 * @param subject2
	 * @param defaultFormat2
	 * @param params2
	 */
	public ApplicationEventNotification(String eventName, AppUser singleSubscriber, String subject, String defaultFormat, Object[] params) {
		this.eventName = eventName;
		this.allowedSubscribers = new ArrayList<AppUser>();
		this.allowedSubscribers.add(singleSubscriber);
		this.subject = subject;
		this.defaultFormat = defaultFormat;
		this.params = params;
		this.notiticationType = EventNotificationType.SINGLE;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {
		return this.params;
	}

	/**
	 * @return the defaultFormat
	 */
	public String getDefaultFormat() {
		return this.defaultFormat;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * @return the allowedSubscribers
	 */
	public List<AppUser> getAllowedSubscribers() {
		return this.allowedSubscribers;
	}

	/**
	 * @return the notiticationType
	 */
	public EventNotificationType getNotiticationType() {
		return this.notiticationType;
	}
}
