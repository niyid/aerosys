package com.vasworks.airliner.struts.operator;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.Flight.FlightStatus;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.struts.OperatorAction;

public class FlightAction extends OperatorAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private FlightId flightId;
	
	private Flight entity;
	
	private List<Flight> flights;
	
	private String departureAirportCode;
	
	private String arrivalAirportCode;
	
	private String airplaneRegNumber;
	
	private String currencyCode;

	private RateId[] selTaxIds;

	private RateId[] selRebateIds;
	
	private String[] selThruFlightIds;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("flight_id", null);
		return super.execute();
	}
	
	public String load() {
		if(flightId != null) {
			if(entity == null) {
				entity = (Flight) operatorService.find(flightId, Flight.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "departureAirportCode", message = "'Departure' is required."),
				@RequiredStringValidator(fieldName = "arrivalAirportCode", message = "'Arrival' is required."),
				@RequiredStringValidator(fieldName = "airplaneRegNumber", message = "'Airplane' is required."),
				@RequiredStringValidator(fieldName = "currencyCode", message = "'Currency' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "flightNumber", message = "'Flight Number' is required."),
				@RequiredFieldValidator(fieldName = "entity.departureTime", message = "'Departure Time' is required."),
				@RequiredFieldValidator(fieldName = "entity.arrivalTime", message = "'Arrival Time' is required."),
				@RequiredFieldValidator(fieldName = "entity.airFareEconomy", message = "'Economy-class Fair' is required."),
				@RequiredFieldValidator(fieldName = "entity.airFareBizClass", message = "'Business-class Fair' is required."),
				@RequiredFieldValidator(fieldName = "entity.airFareFirstClass", message = "'First-class Fair' is required.")
			}
		)
	public String save() {
		operatorService.saveFlight(entity, entity.getId().getFlightNumber(), getAirline().getId(), entity.getDepartureTime(), departureAirportCode, arrivalAirportCode, airplaneRegNumber, currencyCode, selTaxIds, selRebateIds, selThruFlightIds, getUser().getId());
		
		session.put("flight_id", flightId);
		
		addActionMessage("Flight successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(departureAirportCode != null && arrivalAirportCode != null && departureAirportCode.equals(arrivalAirportCode)) {
			addFieldError("arrivalAirportCode", "'Departure Airport' and 'Arrival Airport' cannot be the same.");
		}
		
		if(entity.getDepartureTime() != null && entity.getDepartureTime().before(new Date())) {
			addFieldError("entity.departureTime", "'Departure Time' must be in the future.");
		}
		
		if(entity.getArrivalTime() != null && entity.getArrivalTime().before(new Date())) {
			addFieldError("entity.arrivalTime", "'Arrival Time' must be in the future.");
		}
		
		if(entity.getArrivalTime() != null && entity.getDepartureTime() != null && entity.getArrivalTime().before(entity.getDepartureTime())) {
			addFieldError("entity.arrivalTime", "'Arrival Time' cannot be before 'Departure Time'.");
		}		
	}

	public String initNew() {
		entity = new Flight();
		
		session.put("flight_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(flightId != null) {
			entity = (Flight) operatorService.find(flightId, Flight.class);
			
			departureAirportCode = entity.getDepartureAirport().getAirportCode();
			arrivalAirportCode = entity.getArrivalAirport().getAirportCode();
			airplaneRegNumber = entity.getAirplane().getRegNumber();
			
			initSelTaxIds();
			initSelRebateIds();
			initSelThruFlightIds();
			
			session.put("flight_id", flightId);
		}
		return SUCCESS;
	}
	
	private void initSelTaxIds() {
		if(entity != null) {
			List<Tax> list = entity.getTaxes();
			selTaxIds = new RateId[list.size()];
			
			int i = 0;
			for(Tax o : list) {
				selTaxIds[i] = o.getId();
				i++;
			}
		}
	}
	
	private void initSelRebateIds() {
		if(entity != null) {
			List<Rebate> list = entity.getRebates();
			selRebateIds = new RateId[list.size()];
			
			int i = 0;
			for(Rebate o : list) {
				selRebateIds[i] = o.getId();
				i++;
			}
		}
	}
	
	private void initSelThruFlightIds() {
		if(entity != null) {
			List<Airport> list = entity.getThruAirports();
			selThruFlightIds = new String[list.size()];
			
			int i = 0;
			for(Airport o : list) {
				selThruFlightIds[i] = o.getAirportCode();
				i++;
			}
		}
	}
	
	public String remove() {
		if(flightId != null) {
			operatorService.remove(flightId, Flight.class);
		}
		
		return list();
	}
	
	public String list() {
		flights = operatorService.listFlights(getAirline().getId());
		
		return SUCCESS;
	}

	public FlightId getFlightId() {
		return flightId;
	}

	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public String getAirplaneRegNumber() {
		return airplaneRegNumber;
	}

	public void setAirplaneRegNumber(String airplaneId) {
		this.airplaneRegNumber = airplaneId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Flight getEntity() {
		return entity;
	}

	public void setEntity(Flight entity) {
		this.entity = entity;
	}

	public List<Flight> getFlights() {
		return flights;
	}
	
	public FlightStatus[] getFlightStatusLov() {
		return FlightStatus.values();
	}
	
	public List<Tax> getTaxLov() {
		return (List<Tax>) operatorService.listNonAutoTaxes(getAirline().getId());
	}
	
	public List<Rebate> getRebateLov() {
		return (List<Rebate>) operatorService.listNonAutoRebates(getAirline().getId());
	}
	
	public List<Airplane> getAirplaneLov() {
		return operatorService.listAirplanes(getAirline().getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Airport> getAirportLov() {
		return (List<Airport>) operatorService.list(Airport.class);
	}
	
	public RateId[] getSelTaxIds() {
		return selTaxIds;
	}

	public void setSelTaxIds(RateId[] selTaxIds) {
		this.selTaxIds = selTaxIds;
	}

	public RateId[] getSelRebateIds() {
		return selRebateIds;
	}

	public void setSelRebateIds(RateId[] selRebateIds) {
		this.selRebateIds = selRebateIds;
	}
	
	public String[] getSelThruFlightIds() {
		return selThruFlightIds;
	}

	public void setSelThruFlightIds(String[] selThruFlightIds) {
		this.selThruFlightIds = selThruFlightIds;
	}
}
