/**
 * vasworks-common Jan 30, 2010
 */
package com.vasworks.service;

import java.util.List;

import com.vasworks.entity.Notification;
import com.vasworks.security.model.AppUser;
import com.vasworks.util.PagedResult;

/*
 * *
 * 
 * @author developer
 */
public interface UserNotificationReader {

	public abstract Notification findNotification(Long id);

	public abstract PagedResult<Notification> listUnread(AppUser user, int startAt, int maxResults, boolean ascending);

	public abstract PagedResult<Notification> listMessages(AppUser user, int pageSize, int pageNumber, boolean ascending);

	public abstract long countMessages(AppUser user);

	public abstract long countUnread(AppUser user);

	public abstract List<Notification> listAllMessages(AppUser user);

	public abstract void deleteMessage(Notification notification);

	public abstract void deleteMessage(Long id);

	public abstract int deleteAllMessages(AppUser user);

	public abstract void markAsRead(Notification notification);

	public abstract void markAsUnread(Notification notification);

}