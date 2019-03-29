package com.vasworks.airliner.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.vasworks.entity.Address;

@Entity
public class TrainingDoctor extends TrainingHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428094123229676716L;

	private String facilityName;
	
	private Address address;

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@ManyToOne
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
