package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623323133016026800L;
	
	private Long id;
	
	private double weightMinor;
	
	private double weightMale;
	
	private double weightFemale;
	
	private double weightCarryOn;
	
	private Airline airline;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getWeightMinor() {
		return weightMinor;
	}

	public void setWeightMinor(double weightMinor) {
		this.weightMinor = weightMinor;
	}

	public double getWeightMale() {
		return weightMale;
	}

	public void setWeightMale(double weightMale) {
		this.weightMale = weightMale;
	}

	public double getWeightFemale() {
		return weightFemale;
	}

	public void setWeightFemale(double weightFemale) {
		this.weightFemale = weightFemale;
	}

	public double getWeightCarryOn() {
		return weightCarryOn;
	}

	public void setWeightCarryOn(double weightCarryOn) {
		this.weightCarryOn = weightCarryOn;
	}

	@ManyToOne
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
}
