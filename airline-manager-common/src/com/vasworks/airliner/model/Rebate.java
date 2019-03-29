package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Rebate implements RateInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4655291698981985669L;

	private RateId id;
	
	private String description;

	private Double ratePercentage;
	
	private Double fixedRate;
	
	private boolean autoAdded;
	
	private Airline airline;

	@EmbeddedId
	public RateId getId() {
		return id;
	}

	public void setId(RateId id) {
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
