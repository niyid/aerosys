package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class TrainingHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6975558576784492335L;
	
	private Long id;
	
	private String handlerName;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	
	@Transient
	public String getTrainerDesignation() {
		String dsg = null;
		if(this instanceof TrainingSupervisor) {
			dsg = "Supervisor";
		} else
		if(this instanceof TrainingDoctor) {
			dsg = "Doctor";
		} else
		if(this instanceof TrainingPilot) {
			dsg = "Pilot";
		}
		
		return dsg;
	}
}
