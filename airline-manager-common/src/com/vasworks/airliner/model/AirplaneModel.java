package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AirplaneModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016261546883704973L;

	private String modelName;
	
	private String description;
	
	private String crossSection;
	
	private String bizClassRows;
	
	private String fstClassRows;
	
	private String exitRows;
	
	private Integer numberOfRows;
	
	private Double luggageCapacity;
	
	private Double airplaneWeight;
	
	private AirplaneMake airplaneMake;

	@Id
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String makeName) {
		this.modelName = makeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCrossSection() {
		return crossSection;
	}

	public void setCrossSection(String crossSection) {
		this.crossSection = crossSection;
	}

	public String getBizClassRows() {
		return bizClassRows;
	}

	public void setBizClassRows(String bizClassRows) {
		this.bizClassRows = bizClassRows;
	}

	public String getFstClassRows() {
		return fstClassRows;
	}

	public void setFstClassRows(String fstClassRows) {
		this.fstClassRows = fstClassRows;
	}

	public String getExitRows() {
		return exitRows;
	}

	public void setExitRows(String exitRows) {
		this.exitRows = exitRows;
	}

	public Integer getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(Integer numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public Double getLuggageCapacity() {
		return luggageCapacity;
	}

	public void setLuggageCapacity(Double luggageCapacity) {
		this.luggageCapacity = luggageCapacity;
	}
	
	public Double getAirplaneWeight() {
		return airplaneWeight;
	}

	public void setAirplaneWeight(Double airplaneWeight) {
		this.airplaneWeight = airplaneWeight;
	}

	@ManyToOne
	public AirplaneMake getAirplaneMake() {
		return airplaneMake;
	}

	public void setAirplaneMake(AirplaneMake airplaneMake) {
		this.airplaneMake = airplaneMake;
	}
	
}
