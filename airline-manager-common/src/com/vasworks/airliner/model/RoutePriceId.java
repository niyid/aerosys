package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.vasworks.util.CompositeId;

@Embeddable
public class RoutePriceId implements Serializable, CompositeId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3082325125096046378L;

	private Long routeId;
	
	private Long customerId;
	
	private String currencyCode;
	
	public RoutePriceId() {
		
	}

	public RoutePriceId(Long routeId, Long customerId, String currencyCode) {
		super();
		this.routeId = routeId;
		this.customerId = customerId;
		this.setCurrencyCode(currencyCode);
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long organizationId) {
		this.customerId = organizationId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
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
		RoutePriceId other = (RoutePriceId) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getClass().getName());
		b.append("__routeId;");
		b.append(routeId);
		b.append("__customerId;");
		b.append(customerId);
		b.append("__currencyCode;");
		b.append(currencyCode);
		
		return b.toString();
	}	
}
