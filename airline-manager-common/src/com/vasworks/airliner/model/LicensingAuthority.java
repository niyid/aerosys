package com.vasworks.airliner.model;

import javax.persistence.Entity;

import com.vasworks.entity.Organization;

@Entity
public class LicensingAuthority extends Organization {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5404805509671941574L;
	
	private String authorityAcronym;

	public String getAuthorityAcronym() {
		return authorityAcronym;
	}

	public void setAuthorityAcronym(String authorityAcronym) {
		this.authorityAcronym = authorityAcronym;
	}
}
