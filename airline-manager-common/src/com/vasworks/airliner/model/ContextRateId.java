package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.vasworks.util.CompositeId;

@Embeddable
public class ContextRateId implements Serializable, CompositeId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2731296857997460192L;
	
	private String rateCode;

	private Long airlineId;
	
	private Long clientId;
	
	public ContextRateId() {
		super();
	}

	public ContextRateId(Long airlineId, String rateCode, Long clientId) {
		super();
		this.rateCode = rateCode;
		this.airlineId = airlineId;
		this.clientId = clientId;
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

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rateCode == null) ? 0 : rateCode.hashCode());
		result = prime * result
				+ ((airlineId == null) ? 0 : airlineId.hashCode());
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
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
		ContextRateId other = (ContextRateId) obj;
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
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
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
		b.append("__clientId;");
		b.append(clientId);
		
		return b.toString();
	}	
}
