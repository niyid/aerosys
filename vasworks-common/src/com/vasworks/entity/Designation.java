package com.vasworks.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Designation extends MySqlBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781922571479711510L;
	
	private String designationName;
	
	private String description;

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
