/**
 * 
 */
package com.vasworks.security.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author developer
 * 
 */
@Entity
@Table(name = "UserDelegation")
public class UserDelegation {
	private Long id;
	private AppUser owner;
	private AppUser delegatedTo;
	private String application;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * @return the owner
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "ownerId")
	public AppUser getOwner() {
		return this.owner;
	}

	/**
	 * @return the delegatedTo
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "delegatedTo")
	public AppUser getDelegatedTo() {
		return this.delegatedTo;
	}

	/**
	 * @return the application
	 */
	@Column(length = 50, nullable = false)
	public String getApplication() {
		return this.application;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	/**
	 * @param delegatedTo the delegatedTo to set
	 */
	public void setDelegatedTo(AppUser delegatedTo) {
		this.delegatedTo = delegatedTo;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

}
