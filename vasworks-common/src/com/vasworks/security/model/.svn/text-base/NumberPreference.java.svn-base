package com.vasworks.security.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="7")
public class NumberPreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514571631549445305L;
	
	private BigDecimal preferenceValue;

	/**
	 * 
	 */
	public NumberPreference() {
		
	}
	
	/**
	 * @param value
	 */
	public NumberPreference(BigDecimal value) {
		this.preferenceValue=value;
	}

	@Override
	@Column(name="bigVal")
	public BigDecimal getPreferenceValue() {
		return preferenceValue;
	}
	
	public void setPreferenceValue(BigDecimal value) {
		this.preferenceValue = value;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((BigDecimal) value);
	}
}
