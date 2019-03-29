package com.vasworks.airliner.struts.json;

import java.util.List;

import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Country;

public class CountrySelectionAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7456957464885980469L;
	
	private List<Country> countryLov;

	public String getJSON() {
	    return execute();
	}
		
	public List<Country> getCountryLov() {
		return countryLov;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		countryLov = (List<Country>) operatorService.list(Country.class);
		
		return SUCCESS;
	}

}
