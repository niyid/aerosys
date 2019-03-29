package com.vasworks.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vasworks.entity.Notification;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.service.UserService;
import com.vasworks.service.UserNotificationReader;
import com.vasworks.util.PagedResult;

public class NotificationAction extends BaseAction {
	private static final Log log = LogFactory.getLog(NotificationAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -4462924003640701300L;

	private static final int PAGE_SIZE = 20;

	private Long id;

	private Notification notification;

	private UserNotificationReader notificationService;

	@SuppressWarnings("unused")
	private UserService userService;

	private PagedResult<Notification> paged = null;

	private int startAt;

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setNotificationService(UserNotificationReader notificationService) {
		this.notificationService = notificationService;
	}

	public PagedResult<Notification> getPaged() {
		return paged;
	}

	@Override
	public void prepare() {
		log.debug("prepare");
	}

	@Override
	public String execute() {
		log.debug("execute");

		AppUser user = getUser();

		if (user != null) {
			paged = notificationService.listMessages(user, startAt, PAGE_SIZE, false);
		}

		return SUCCESS;
	}

	public String unread() {
		log.debug("Unread");
		if (getUser() != null) {
			paged = notificationService.listUnread(getUser(), startAt, PAGE_SIZE, false);
		}

		return SUCCESS;
	}

	public String read() {
		log.debug("read");
		notification = notificationService.findNotification(id);
		notificationService.markAsRead(notification);

		return "detail";
	}

	public String toggle() {
		log.debug("toggle");
		notification = notificationService.findNotification(id);

		if (notification.isRead()) {
			notificationService.markAsUnread(notification);
		} else if (!notification.isRead()) {
			notificationService.markAsRead(notification);
		}

		return "refresh";
	}

	public String del() {
		log.debug("del");
		notificationService.deleteMessage(id);

		return "refresh";
	}

	public String delAll() {
		log.debug("delAll");
		notificationService.deleteAllMessages(getUser());

		return "refresh";
	}
}
