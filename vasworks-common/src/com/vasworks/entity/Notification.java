package com.vasworks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.vasworks.security.model.AppUser;

@Entity
public class Notification extends MySqlBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809780615604671548L;

	private String title;

	private String message;

	private String originAppName;

	private AppUser subscriber;

	private boolean read;

	@Column(length = 400)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(length = 200)
	public String getOriginAppName() {
		return originAppName;
	}

	public void setOriginAppName(String originAppName) {
		this.originAppName = originAppName;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriberNotificationId")
	public AppUser getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(AppUser subscriber) {
		this.subscriber = subscriber;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Column(name = "`read`")
	public boolean isRead() {
		return read;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (title.length() > 20) {
			builder.append(title.substring(0, 20));
			builder.append("...");
		} else {
			builder.append(title);
		}

		return builder.toString();
	}
}
