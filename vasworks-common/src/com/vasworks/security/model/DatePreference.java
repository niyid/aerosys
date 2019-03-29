package com.vasworks.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="3")
public class DatePreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150974546797325646L;
	
	private Date preferenceValue;

	
	public void setPreferenceValue(Date value) {
		this.preferenceValue = value;
	}

	@Override
	@Column(name="dateVal")
	public Date getPreferenceValue() {
		return preferenceValue;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((Date) value);
	}
}
