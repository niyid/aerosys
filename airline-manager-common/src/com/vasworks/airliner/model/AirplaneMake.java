package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class AirplaneMake implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016261546883704973L;

	private String makeName;
	
	private String description;
	
	private List<AirplaneModel> models;

	@Id
	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "airplaneMake")
	public List<AirplaneModel> getModels() {
		return models;
	}

	public void setModels(List<AirplaneModel> models) {
		this.models = models;
	}
	
}
