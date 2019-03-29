package com.vasworks.airliner.struts.setup;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.struts.OperatorAction;

public class AirportAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String airportCode;
	
	private Long cityId;
	
	private Airport entity;
	
	private List<Airport> airports;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("airport_id", null);
		return super.execute();
	}
	
	public String load() {
		if(airportCode != null) {
			if(entity == null) {
				entity = (Airport) operatorService.find(airportCode, Airport.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.airportCode", message = "'Code' is required."),
				@RequiredStringValidator(fieldName = "entity.airportName", message = "'Name' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "cityId", message = "'City' is required.")
			}
		)
	public String save() {
		operatorService.saveAirport(entity, airportCode, cityId, getUser().getId());
		
		entity = new Airport();
		airportCode = null;
		cityId = null;
		
		session.put("airport_id", null);
		
		addActionMessage("Airport successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new Airport();
		airportCode = null;
		cityId = null;
		
		session.put("airport_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(airportCode != null) {
			entity = (Airport) operatorService.find(airportCode, Airport.class);
			cityId = entity.getCity().getId();
			
			session.put("airport_id", airportCode);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(airportCode != null) {
			operatorService.remove(airportCode, Airport.class);
		}
		
		return list();
	}
	
	public String list() {
		airports = operatorService.listAirports(getCountry().getCountryCode());

		return SUCCESS;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportId) {
		this.airportCode = airportId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Airport getEntity() {
		return entity;
	}

	public void setEntity(Airport entity) {
		this.entity = entity;
	}

	public List<Airport> getAirports() {
		return airports;
	}
	
	public List<City> getCityLov() {
		return operatorService.listCities(getDefaultCountry().getCountryCode());
	}
}
