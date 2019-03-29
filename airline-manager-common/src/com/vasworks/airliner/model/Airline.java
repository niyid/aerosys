package com.vasworks.airliner.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.vasworks.entity.Organization;

@Entity
public class Airline extends Organization {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6212001639740623252L;
	
	private String airlineCode;
	
	@Column(unique = true)
	public String getAirlineCode() {
		return airlineCode;
	}
	
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
}
