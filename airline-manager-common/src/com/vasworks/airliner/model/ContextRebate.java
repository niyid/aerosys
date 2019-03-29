package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class ContextRebate implements RateInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319073509574901694L;
	
	private ContextRateId id;
	
	private String description;

	private Double ratePercentage;
	
	private Double fixedRate;
	
	private boolean autoAdded;
	
	private boolean regional;
	
	private Date validityStartDate;
	
	private Date validityEndDate;
	
	private Airline airline;
	
	private Customer client;
	
	@EmbeddedId
	public ContextRateId getId() {
		return id;
	}

	public void setId(ContextRateId id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "airlineId", insertable = false, updatable = false, referencedColumnName = "id")
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	@ManyToOne
	@JoinColumn(name = "clientId", insertable = false, updatable = false, referencedColumnName = "id")
	public Customer getClient() {
		return client;
	}

	public void setClient(Customer client) {
		this.client = client;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRatePercentage() {
		return ratePercentage;
	}

	public void setRatePercentage(Double ratePercentage) {
		this.ratePercentage = ratePercentage;
	}

	public Double getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(Double fixedRate) {
		this.fixedRate = fixedRate;
	}

	public boolean isAutoAdded() {
		return autoAdded;
	}

	public void setAutoAdded(boolean autoAdded) {
		this.autoAdded = autoAdded;
	}

	public boolean isRegional() {
		return regional;
	}

	public void setRegional(boolean domestic) {
		this.regional = domestic;
	}

	@Temporal(TemporalType.DATE)
	public Date getValidityStartDate() {
		return validityStartDate;
	}

	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getValidityEndDate() {
		return validityEndDate;
	}

	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	@Override
	@Transient
	public String getRateCode() {
		return id.getRateCode();
	}

	@Override
	public void setRatecode(String rateCode) {
		id.setRateCode(rateCode);
	}
}
