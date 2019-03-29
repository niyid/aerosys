package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.vasworks.util.CompositeId;

@Embeddable
public class RateId implements Serializable, CompositeId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2731296857997460192L;
	
	private String rateCode;

	private Long airlineId;
	
	public RateId() {
		super();
	}

	public RateId(Long airlineId, String rateCode) {
		super();
		this.rateCode = rateCode;
		this.airlineId = airlineId;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rateCode == null) ? 0 : rateCode.hashCode());
		result = prime * result
				+ ((airlineId == null) ? 0 : airlineId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateId other = (RateId) obj;
		if (rateCode == null) {
			if (other.rateCode != null)
				return false;
		} else if (!rateCode.equals(other.rateCode))
			return false;
		if (airlineId == null) {
			if (other.airlineId != null)
				return false;
		} else if (!airlineId.equals(other.airlineId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getClass().getName());
		b.append("__rateCode;");
		b.append(rateCode);
		b.append("__airlineId;");
		b.append(airlineId);
		
		return b.toString();
	}	
}
