package com.vasworks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.vasworks.security.model.AppUser;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Personnel extends AppUser implements Serializable, Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4125003895357339569L;
	
	private Organization organization;
	
	private Designation designation;

	private Date lastUpdated;

	private Long lastUpdatedBy;

	private Date createdDate;

	private Long createdBy;
	
	public Personnel() {
		super();
	}

	@ManyToOne
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne
	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	@Override
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		
	}

	@Override
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Long getCreatedBy() {
		return createdBy;
	}
	
}
