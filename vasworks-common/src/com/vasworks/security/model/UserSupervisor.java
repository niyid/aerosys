/**
 * 
 */
package com.vasworks.security.model;

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
@Table(name = "UserSupervisor")
public class UserSupervisor {
	private Long id;
	private String application;
	private AppUser user;
	private AppUser supervisor;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * @return the application
	 */
	@Column(length = 100)
	public String getApplication() {
		return this.application;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(cascade = {})
	@JoinColumn(name = "userId")
	public AppUser getUser() {
		return this.user;
	}

	/**
	 * @return the supervisor
	 */
	@ManyToOne(cascade = {})
	@JoinColumn(name = "supervisorId")
	public AppUser getSupervisor() {
		return this.supervisor;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AppUser user) {
		this.user = user;
	}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(AppUser supervisor) {
		this.supervisor = supervisor;
	}
}
