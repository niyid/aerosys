package com.vasworks.security.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="4")
public class DoublePreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514571631549445305L;
	
	private Double preferenceValue;

	/**
	 * 
	 */
	public DoublePreference() {
		
	}
	
	/**
	 * @param value
	 */
	public DoublePreference(Double value) {
		this.preferenceValue=value;
	}


	@Override
	@Column(name="doubleVal")
	public Double getPreferenceValue() {
		return preferenceValue;
	}

	
	public void setPreferenceValue(Double value) {
		this.preferenceValue = value;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((Double) value);
	}
}
