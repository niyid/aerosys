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

/**
 * @author aafolayan
 * 
 */
@Entity
public class UserLookup {

	private Long id;
	private String identifier;
	private AppUser user;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the identifier
	 */
	@Column(length = 200, nullable = false)
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "userid")
	public AppUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AppUser user) {
		this.user = user;
	}

}
