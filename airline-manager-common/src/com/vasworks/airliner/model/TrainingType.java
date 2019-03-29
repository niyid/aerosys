package com.vasworks.airliner.model;


public enum TrainingType {
	OPC("Operators Proficiency Check"), 
	MEDICALS("Medicals"), 
	LINE_CHECK("Line Check"), 
	IRR("Instrument Rating Renewal"), 
	EandS("Emergency and Survival"), 
	CRM(" Crew Resource Management"), 
	DG("Dangerous Goods"), 
	FED("Fire Emergency Drill"), 
	SIM("Simulator"), 
	FIRST_AID("First Aid");
	
	private final String description;
	
	private TrainingType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
