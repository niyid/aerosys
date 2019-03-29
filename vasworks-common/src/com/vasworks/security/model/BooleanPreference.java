package com.vasworks.security.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "1")
public class BooleanPreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150974546797325646L;

	private Boolean preferenceValue;

	/**
	 * 
	 */
	public BooleanPreference() {

	}

	/**
	 * @param value
	 */
	public BooleanPreference(Boolean value) {
		this.preferenceValue = value;
	}

	@Override
	@Column(name = "boolVal")
	public Boolean getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(Boolean value) {
		this.preferenceValue = value;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((Boolean) value);
	}
	
	// Workaround for XWork
	public void setPreferenceValueX(Boolean value) {
		setPreferenceValue(value);
	}
}
