package com.vasworks.security.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "2")
public class CharacterPreference extends Preference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514571631549445305L;

	private Character preferenceValue;

	/**
	 * 
	 */
	public CharacterPreference() {

	}

	/**
	 * @param value
	 */
	public CharacterPreference(Character value) {
		this.preferenceValue = value;
	}

	@Override
	@Column(name = "charVal")
	public Character getPreferenceValue() {
		return preferenceValue;
	}

	public void setPreferenceValue(Character value) {
		this.preferenceValue = value;
	}

	/**
	 * @see com.vasworks.entity.IPreference#castValue(java.lang.Object)
	 */
	@Override
	public void setPreferenceValue(Object value) {
		setPreferenceValue((Character) value);
	}
}
