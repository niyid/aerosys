package com.vasworks.security.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "8")
public class StringPreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150974546797325646L;

	private String preferenceValue;

	/**
	 * 
	 */
	public StringPreference() {
		
	}
	
	/**
	 * @param value
	 */
	public StringPreference(String value) {
		this.preferenceValue = value;
	}

	public void setPreferenceValue(String preferenceValue) {
		this.preferenceValue = preferenceValue;
	}

	@Override
	@Column(name = "stringVal")
	public String getPreferenceValue() {
		return preferenceValue;
	}
	
	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((String) value);
	}

	/// Workaround for XWork
	public void setPreferenceValueX(String value) {
		setPreferenceValue(value);
	}
}
