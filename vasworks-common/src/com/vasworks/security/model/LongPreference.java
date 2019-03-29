package com.vasworks.security.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "6")
public class LongPreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514571631549445305L;

	private Long preferenceValue;

	/**
	 * 
	 */
	public LongPreference() {
		
	}
	
	/**
	 * @param value
	 */
	public LongPreference(Long value) {
		this.preferenceValue=value;
	}

	@Override
	@Column(name = "longVal")
	public Long getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(Long value) {
		this.preferenceValue = value;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((Long) value);
	}
}
