package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.vasworks.entity.Designation;

@Entity
public class CrewDesignationRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3413539957783528903L;
	private CrewDesignationRuleId id;

	private AirplaneModel airplaneModel;
	
	private List<Designation> designations;

	@EmbeddedId
	public CrewDesignationRuleId getId() {
		return id;
	}

	public void setId(CrewDesignationRuleId id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "airplaneModelName", referencedColumnName = "modelName", insertable = false, updatable = false)
	public AirplaneModel getAirplaneModel() {
		return airplaneModel;
	}

	public void setAirplaneModel(AirplaneModel airplaneModel) {
		this.airplaneModel = airplaneModel;
	}

	@ManyToMany
	public List<Designation> getDesignations() {
		return designations;
	}

	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}
}
