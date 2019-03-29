package com.vasworks.airliner.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vasworks.util.CompositeId;

@Embeddable
public class FlightId implements Serializable, CompositeId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5775205218534359514L;
	
	private Integer flightNumber;
	
	private String airlineCode;
	
	private Date flightDate;

	public FlightId() {
		
	}
	
	public FlightId(Integer flightNumber, String airlineCode, Date flightDate) {
		this.flightNumber = flightNumber;
		this.airlineCode = airlineCode;
		this.flightDate = flightDate;
	}
	
	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	@Temporal(TemporalType.DATE)
	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((airlineCode == null) ? 0 : airlineCode.hashCode());
		result = prime * result
				+ ((flightDate == null) ? 0 : flightDate.hashCode());
		result = prime * result
				+ ((flightNumber == null) ? 0 : flightNumber.hashCode());
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
		FlightId other = (FlightId) obj;
		if (airlineCode == null) {
			if (other.airlineCode != null)
				return false;
		} else if (!airlineCode.equals(other.airlineCode))
			return false;
		if (flightDate == null) {
			if (other.flightDate != null)
				return false;
		} else if (!flightDate.equals(other.flightDate))
			return false;
		if (flightNumber == null) {
			if (other.flightNumber != null)
				return false;
		} else if (!flightNumber.equals(other.flightNumber))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getClass().getName());
		b.append("__flightNumber;");
		b.append(flightNumber);
		b.append("__airlineCode;");
		b.append(airlineCode);
		b.append("__flightDate;");
		b.append(flightDate != null ? new SimpleDateFormat("dd/MM/yyyy").format(flightDate) : "");
		
		return b.toString();
	}	
}
