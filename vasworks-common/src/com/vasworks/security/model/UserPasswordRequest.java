/**
 * 
 */
package com.vasworks.security.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * PasswordRequest bean links the AppUser's request to a specific key, which can be used only once to generate a password.
 * 
 * @author developer
 */
@Entity
public class UserPasswordRequest {
	private Long id;
	private AppUser user;
	private String key;
	private int status = 0;
	private Date dateGenerated;
	private Date dateUsed;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	public AppUser getUser() {
		return this.user;
	}

	/**
	 * @return the key
	 */
	@Column(name = "`key`")
	public String getKey() {
		return this.key;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * @return the dateGenerated
	 */
	public Date getDateGenerated() {
		return this.dateGenerated;
	}

	/**
	 * @return the dateUsed
	 */
	public Date getDateUsed() {
		return this.dateUsed;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AppUser user) {
		this.user = user;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @param dateGenerated the dateGenerated to set
	 */
	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	/**
	 * @param dateUsed the dateUsed to set
	 */
	public void setDateUsed(Date dateUsed) {
		this.dateUsed = dateUsed;
	}

}
