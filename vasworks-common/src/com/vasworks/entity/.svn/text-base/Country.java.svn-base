package com.vasworks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Country implements Serializable, Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015095329436493549L;
	
	private String countryName;
	
	private String countryCode;
	
	private Currency currency;

	private Date lastUpdated;

	private Long lastUpdatedBy;

	private Date createdDate;

	private Long createdBy;

	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Id
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@ManyToOne
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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
