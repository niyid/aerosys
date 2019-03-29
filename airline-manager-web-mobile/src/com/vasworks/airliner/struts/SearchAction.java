package com.vasworks.airliner.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Customer.CustomerType;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.RateInterface;

public class SearchAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Flight entity;
	
	private Integer flightNumber;
	
	private Integer flightNumber2;
	
	private Date flightDate;
	
	private Date flightDate2;
	
	private String departureAirportId;
	
	private String arrivalAirportId;
	
	private boolean connections;
	
	private boolean thruFlights;
	
	private List<Flight> flightResult;
	
	private List<Flight> flightResult2;
	
	private Double totalTaxesEco;
	
	private Double totalRebatesEco;
	
	private Double totalChargesEco;
	
	private Double totalTaxesBiz;
	
	private Double totalRebatesBiz;
	
	private Double totalChargesBiz;
	
	private Double totalTaxesFst;
	
	private Double totalRebatesFst;
	
	private Double totalChargesFst;
	
	private String crossSection;
	
	private Collection<? extends RateInterface> taxes = new ArrayList<>();
	
	private Collection<? extends RateInterface> rebates = new ArrayList<>();
	
	private List<FlightSeat> seats;
	
	private Integer passengerCount;
	
	private List<Passenger> passengers;
	
	private List<Flight> selectedFlights = new ArrayList<Flight>();
	
	private FlightId flightId;
	
	private FlightId flightId2;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.remove("flight_result");
		session.remove("flight_result2");
		
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "departureAirportId", message = "'Departure Airport' is required."),
				@RequiredStringValidator(fieldName = "arrivalAirportId", message = "'Arrival Airport' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "flightDate", message = "'Flight Date' is required.")
			}
	)
	public String search() {
		LOG.debug("AppUser search(): " + getUser());
		
		flightResult = operatorService.findFlights(flightDate, departureAirportId, arrivalAirportId, connections, thruFlights, (Long) session.get("client_id"));
		
		if(flightDate2 != null) {
			flightResult2 = operatorService.findFlights(flightDate2, arrivalAirportId, departureAirportId, connections, thruFlights, (Long) session.get("client_id"));
		}
		
		session.put("flight_result", flightResult);
		
		if(flightDate2 != null) {
			session.put("flight_result2", flightResult2);
		} else {
			session.remove("flight_result2");
		}
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
	}

	@Validations(
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "flightId", message = "'Arrival Flight' is required."), 
			@RequiredFieldValidator(fieldName = "flightId2", message = "'Departure Flight' is required.") ,
			@RequiredFieldValidator(fieldName = "passengerCount", message = "'Number of Passengers' is required.")
		}
	)
	public String select() {
		LOG.info("select: " + flightId + " " + flightId2);
		
		if(flightId != null) {			
			
			LOG.info("flightId (select): " + flightId);
			
			entity = (Flight) operatorService.find(flightId, Flight.class);
			
			session.put("selected_flight", flightId);
			
			selectedFlights.add(entity);
		}
		if(flightId2 != null) {			
			
			LOG.info("flightId2 (select): " + flightId2);
			
			entity = (Flight) operatorService.find(flightId2, Flight.class);
			
			session.put("selected_flight2", flightId2);
			
			selectedFlights.add(entity);
		}
		if(passengerCount == null) {
			passengerCount = 1;
		}
		
		passengers = new ArrayList<Passenger>(passengerCount);
		for(int i = 0; i < passengerCount; i++) {
			passengers.add(new Passenger());
		}
		
		return SUCCESS;
	}
	
	public String pick() {
		LOG.info("pick: " + flightNumber + " " + flightNumber2 + " " + flightDate + " " + flightDate2);
		
		Customer client = (Customer) operatorService.find(getUserId(), Customer.class);
		if(flightNumber != null) {			
			FlightId flightId = new FlightId(flightNumber, getAirlineCode(), flightDate);
			
			LOG.info("pickedId (pick): " + flightId);
			
			entity = (Flight) operatorService.find(flightId, Flight.class);
			
			if(client != null && (CustomerType.PRE_ETL_POSTPAID.equals(client.getCustomerType()) || CustomerType.PRE_ETL_PREPAID.equals(client.getCustomerType()))) {
				taxes = operatorService.listAutoTaxes(getAirlineId(), getUserId());
				rebates = operatorService.listAutoRebates(getAirlineId(), getUserId());
			} else {
				taxes = operatorService.listAutoTaxes(getAirlineId());
				rebates = operatorService.listAutoRebates(getAirlineId());
			}
			
			totalTaxesEco = operatorService.sumChargesOrRebates(taxes, entity.getAirFareEconomy());
			totalRebatesEco = operatorService.sumChargesOrRebates(rebates, entity.getAirFareEconomy());
			totalChargesEco = entity.getAirFareEconomy() + totalTaxesEco - totalRebatesEco;
			
			totalTaxesBiz = operatorService.sumChargesOrRebates(taxes, entity.getAirFareBizClass());
			totalRebatesBiz = operatorService.sumChargesOrRebates(rebates, entity.getAirFareBizClass());
			totalChargesBiz = entity.getAirFareBizClass() + totalTaxesBiz - totalRebatesBiz;
			
			totalTaxesFst = operatorService.sumChargesOrRebates(taxes, entity.getAirFareFirstClass());
			totalRebatesFst = operatorService.sumChargesOrRebates(rebates, entity.getAirFareFirstClass());
			totalChargesFst = entity.getAirFareFirstClass() + totalTaxesFst - totalRebatesFst;
			
			session.put("selected_flight", flightId);
			
			seats = operatorService.listFlightSeats(flightId);
			crossSection = entity.getAirplane().getModel().getCrossSection();			
		}
		if(flightNumber2 != null) {			
			FlightId flightId = new FlightId(flightNumber2, getAirlineCode(), flightDate2);
			
			LOG.debug("pickedId (pick): " + flightId);
			
			entity = (Flight) operatorService.find(flightId, Flight.class);
			
			taxes = operatorService.listAutoTaxes(getAirlineId());
			rebates = operatorService.listAutoRebates(getAirlineId());
			
			if(CustomerType.PRE_ETL_POSTPAID.equals(client.getCustomerType()) || CustomerType.PRE_ETL_PREPAID.equals(client.getCustomerType())) {
				taxes = operatorService.listAutoTaxes(getAirlineId(), getUserId());
				rebates = operatorService.listAutoRebates(getAirlineId(), getUserId());
			} else {
				taxes = operatorService.listAutoTaxes(getAirlineId());
				rebates = operatorService.listAutoRebates(getAirlineId());
			}
			
			totalTaxesEco = operatorService.sumChargesOrRebates(taxes, entity.getAirFareEconomy());
			totalRebatesEco = operatorService.sumChargesOrRebates(rebates, entity.getAirFareEconomy());
			totalChargesEco = entity.getAirFareEconomy() + totalTaxesEco - totalRebatesEco;
			
			totalTaxesBiz = operatorService.sumChargesOrRebates(taxes, entity.getAirFareBizClass());
			totalRebatesBiz = operatorService.sumChargesOrRebates(rebates, entity.getAirFareBizClass());
			totalChargesBiz = entity.getAirFareEconomy() + totalTaxesBiz - totalRebatesBiz;
			
			totalTaxesFst = operatorService.sumChargesOrRebates(taxes, entity.getAirFareFirstClass());
			totalRebatesFst = operatorService.sumChargesOrRebates(rebates, entity.getAirFareFirstClass());
			totalChargesFst = entity.getAirFareEconomy() + totalTaxesFst - totalRebatesFst;
			
			session.put("selected_flight2", flightId);
			
			seats = operatorService.listFlightSeats(flightId);
			crossSection = entity.getAirplane().getModel().getCrossSection();
		}
		
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String list() {
		if(session.get("flight_result") != null) {
			flightResult = (List<Flight>) session.get("flight_result");
		}
		
		if(session.get("flight_result2") != null) {
			flightResult2 = (List<Flight>) session.get("flight_result2");
		}
	
		return SUCCESS;
	}
	
	public Flight getEntity() {
		return entity;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Integer getFlightNumber2() {
		return flightNumber2;
	}

	public void setFlightNumber2(Integer flightNumber2) {
		this.flightNumber2 = flightNumber2;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date initialDeparture) {
		this.flightDate = initialDeparture;
	}

	public Date getFlightDate2() {
		return flightDate2;
	}

	public void setFlightDate2(Date flightDate2) {
		this.flightDate2 = flightDate2;
	}

	public String getDepartureAirportId() {
		return departureAirportId;
	}

	public void setDepartureAirportId(String initialDepartureAirportId) {
		this.departureAirportId = initialDepartureAirportId;
	}

	public String getArrivalAirportId() {
		return arrivalAirportId;
	}

	public void setArrivalAirportId(String initialArrivalAirportId) {
		this.arrivalAirportId = initialArrivalAirportId;
	}

	public boolean isConnections() {
		return connections;
	}

	public void setConnections(boolean nonStop) {
		this.connections = nonStop;
	}

	public boolean isThruFlights() {
		return thruFlights;
	}

	public void setThruFlights(boolean thruFlights) {
		this.thruFlights = thruFlights;
	}

	public List<Flight> getFlightResult() {
		return flightResult;
	}
	
	public List<Flight> getFlightResult2() {
		return flightResult2;
	}

	public Collection<? extends RateInterface> getTaxes() {
		return taxes;
	}

	public Collection<? extends RateInterface> getRebates() {
		return rebates;
	}

	public Double getTotalTaxesEco() {
		return totalTaxesEco;
	}

	public Double getTotalRebatesEco() {
		return totalRebatesEco;
	}

	public Double getTotalChargesEco() {
		return totalChargesEco;
	}

	public Double getTotalTaxesBiz() {
		return totalTaxesBiz;
	}

	public Double getTotalRebatesBiz() {
		return totalRebatesBiz;
	}

	public Double getTotalChargesBiz() {
		return totalChargesBiz;
	}
	public Double getTotalTaxesFst() {
		return totalTaxesFst;
	}

	public Double getTotalRebatesFst() {
		return totalRebatesFst;
	}

	public Double getTotalChargesFst() {
		return totalChargesFst;
	}

	public String getCrossSection() {
		return crossSection;
	}

	public List<FlightSeat> getSeats() {
		return seats;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<Flight> getSelectedFlights() {
		return selectedFlights;
	}

	public FlightId getFlightId() {
		return flightId;
	}

	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
	}

	public FlightId getFlightId2() {
		return flightId2;
	}

	public void setFlightId2(FlightId flightId2) {
		this.flightId2 = flightId2;
	}
}
