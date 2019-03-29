package com.vasworks.airliner.model;

import javax.persistence.Entity;

@Entity
public class TrainingPilot extends TrainingHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5974481245229680991L;

	private String licenseNumber;

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
}
