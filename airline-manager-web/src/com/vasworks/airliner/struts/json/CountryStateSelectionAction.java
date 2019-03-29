package com.vasworks.airliner.struts.json;

import java.util.List;

import com.vasworks.airliner.model.CountryState;
import com.vasworks.airliner.struts.OperatorAction;

public class CountryStateSelectionAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7456957464885980469L;
	
	private String countryCode;
	
	private List<CountryState> countryStateLov;

	public String getJSON() {
	    return execute();
	}
		
	public List<CountryState> getCountryStateLov() {
		return countryStateLov;
	}
	
	@Override
	public String execute() {
		countryStateLov = operatorService.listCountryStates(countryCode);
		
		return SUCCESS;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryName) {
		this.countryCode = countryName;
	}
}
