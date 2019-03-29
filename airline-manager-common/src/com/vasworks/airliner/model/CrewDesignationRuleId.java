package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.vasworks.airliner.model.CrewMember.CrewType;

@Embeddable
public class CrewDesignationRuleId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 905402815680991405L;
	
	private CrewType crewType;
	
	private String airplaneModelName;

	public CrewType getCrewType() {
		return crewType;
	}

	public void setCrewType(CrewType crewType) {
		this.crewType = crewType;
	}

	public String getAirplaneModelName() {
		return airplaneModelName;
	}

	public void setAirplaneModelName(String airplaneModelName) {
		this.airplaneModelName = airplaneModelName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airplaneModelName == null) ? 0 : airplaneModelName.hashCode());
		result = prime * result + ((crewType == null) ? 0 : crewType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CrewDesignationRuleId other = (CrewDesignationRuleId) obj;
		if (airplaneModelName == null) {
			if (other.airplaneModelName != null)
				return false;
		} else if (!airplaneModelName.equals(other.airplaneModelName))
			return false;
		if (crewType != other.crewType)
			return false;
		return true;
	}
}
