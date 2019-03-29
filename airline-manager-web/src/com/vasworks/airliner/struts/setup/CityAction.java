package com.vasworks.airliner.struts.setup;

import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.model.CountryState;
import com.vasworks.airliner.struts.OperatorAction;

public class CityAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long cityId;
	
	private Long countryStateId;
	
	private String countryCode;
	
	private City entity;
	
	private List<City> cities;
	
	@Override
	public void prepare() {
		entity = new City();
	}

	@Override
	public String execute() {
		session.put("city_id", null);
		
		countryCode = getCountry().getCountryCode();
		
		return super.execute();
	}
	
	public String load() {
		if(cityId != null) {
			if(entity == null) {
				entity = (City) operatorService.find(cityId, City.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.cityName", message = "'Name' is required."),
				@RequiredStringValidator(fieldName = "countryCode", message = "'Country' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "countryStateId", message = "'State' is required.")
			}
		)
	public String save() {
		operatorService.saveCity(entity, cityId, countryStateId, getUserId());
		
		entity = new City();
		cityId = null;
		countryStateId = null;
		countryCode = null;
		
		session.put("city_id", null);
		session.put("country_id", countryCode);		
		
		addActionMessage("City successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new City();
		
		countryCode = getCountry().getCountryCode();
		
		session.put("city_id", null);
		session.put("country_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(cityId != null) {
			entity = (City) operatorService.find(cityId, City.class);
			countryStateId = entity.getCountryState().getId();
			countryCode = entity.getCountryState().getCountry().getCountryCode();
			
			session.put("city_id", cityId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(cityId != null) {
			operatorService.remove(cityId, City.class);
		}
		
		return list();
	}
	
	public String list() {
		countryCode = session.get("country_id") == null ? getCountry().getCountryCode() : (String) session.get("country_id");
		
		cities = operatorService.listCities(countryCode);
		
		return SUCCESS;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountryStateId() {
		return countryStateId;
	}

	public void setCountryStateId(Long countryStateId) {
		this.countryStateId = countryStateId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public City getEntity() {
		return entity;
	}

	public void setEntity(City entity) {
		this.entity = entity;
	}

	public List<City> getCities() {
		return cities;
	}
	
	@SuppressWarnings("unchecked")
	public List<CountryState> getStateLov() {
		return (List<CountryState>) (countryCode != null ? operatorService.listCountryStates(countryCode) : Collections.emptyList());
	}
}
