package com.vasworks.entity;

public interface IPreference {
	
	public void setPreferenceKey(String key);
	
	public Object getPreferenceKey();
	
	public void setPreferenceValue(Object value);
	
	public Object getPreferenceValue();
	
	public void setDescription(String description);
	
	public String getDescription();
}
