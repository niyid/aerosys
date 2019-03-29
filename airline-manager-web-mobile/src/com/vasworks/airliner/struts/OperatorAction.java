package com.vasworks.airliner.struts;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Flight.FlightFrequency;
import com.vasworks.airliner.model.Gender;
import com.vasworks.airliner.model.TrainingDoctor;
import com.vasworks.airliner.model.TrainingHandler;
import com.vasworks.airliner.model.TrainingPilot;
import com.vasworks.airliner.model.TrainingSupervisor;
import com.vasworks.airliner.model.TrainingType;
import com.vasworks.airliner.model.UserTitle;
import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.airliner.service.OperatorService;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;
import com.vasworks.struts.BaseAction;

public class OperatorAction extends BaseAction implements SessionAware {
	public static final Log LOG = LogFactory.getLog(OperatorAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7587429391502948285L;

	protected OperatorService operatorService;

	protected Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}
	
	public Long getUserId() {
		return session != null ? (Long) session.get("basket_id") : null;
	}
	
	public String getBasketId() {
		return session != null ? (String) session.get("basket_id") : null;
	}
	
	public Country getDefaultCountry() {
		return operatorService.fetchDefaultCountry();
	}
	
	public Country getCountry() {
		return getOrganization().getAddress().getCountry();
	}
	
	public Currency getDefaultCurrency() {
		return getUserId() != null ? operatorService.findDefaultCurrency(getUserId()) : operatorService.fetchDefaultCountry().getCurrency();
	}
	
	public Currency getUsdCurrency() {
		return (Currency) operatorService.find("USD", Currency.class);
	}
	
	public Currency getCurrency() {
		return getOrganization() != null ? getOrganization().getCurrency() : operatorService.fetchDefaultCountry().getCurrency();
	}

	public String getAirlineCode() {
		return (String) session.get("airline_code");
	}
	
	public Long getOrganizationId() {
		return (Long) session.get("org_id");
	}
	
	public Long getAirlineId() {
		return (Long) session.get("airline_id");
	}
	
	public Airline getDefaultAirline() {
		return (Airline) operatorService.find(getAirlineId(), Airline.class);
	}
	
	public Airline getAirline() {
		Airline airline = (Airline) session.get("airline");
		
		return airline != null && getOrganization() != null ? airline : (getOrganization() instanceof Airline ? (Airline) operatorService.find(getOrganization().getId(), Airline.class) : findFirstAirline());
	}
	
	public List<Airport> getAirportLov() {
		return operatorService.listAirports(getDefaultCountry().getCountryCode());
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountryLov() {
		return (List<Country>) operatorService.list(Country.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyLov() {
		return (List<Currency>) operatorService.list(Currency.class);
	}
	
	public Organization getOrganization() {
		return getPersonnel() != null ? getPersonnel().getOrganization() : null;
	}
	
	public Personnel getPersonnel() {
		return getUser() != null ? (Personnel) operatorService.find(getUser().getId(), Personnel.class) : null;
	}
	
	@SuppressWarnings("unchecked")
	private Airline findFirstAirline() {
		List<Airline> airlines = (List<Airline>) operatorService.list(Airline.class);
		
		Airline airline = null; 
		if(airlines != null && !airlines.isEmpty()) {
			airline = airlines.get(0);
		}
		
		return airline;
	}
	
	protected Class<?> fetchTrainerClass(TrainingType trainingType) {
		Class<?> trainerClass = TrainingHandler.class;
		
		if(trainingType != null) {
			switch (trainingType) {
			case CRM:
				trainerClass = TrainingSupervisor.class;
				break;
			case EandS:
				trainerClass = TrainingSupervisor.class;
				break;
			case DG:
				trainerClass = TrainingSupervisor.class;
				break;
			case FED:
				trainerClass = TrainingSupervisor.class;
				break;
			case FIRST_AID:
				trainerClass = TrainingSupervisor.class;
				break;
			case IRR:
				trainerClass = TrainingPilot.class;
				break;
			case MEDICALS:
				trainerClass = TrainingDoctor.class;
				break;
			case LINE_CHECK:
				trainerClass = TrainingPilot.class;
				break;
			case OPC:
				trainerClass = TrainingPilot.class;
				break;
			case SIM:
				trainerClass = TrainingPilot.class;
				break;
			}
		}
		
		return trainerClass;
	}
	
	public Gender[] getGenderLov() {
		return Gender.values();
	}
	
	public SeatClass[] getSeatClassLov() {
		return SeatClass.values();
	}
	
	public FlightFrequency[] getFlightFrequencyLov() {
		return FlightFrequency.values();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTitle> getUserTitleLov() {
		return (List<UserTitle>) operatorService.list(UserTitle.class);
	}
	
	public List<Customer> getCustomerLov() {
		return getPersonnel() != null ? (List<Customer>) operatorService.listCustomers(getOrganizationId()) : operatorService.listCustomers(getAirlineId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Airline> getAirlineLov() {
		return (List<Airline>) operatorService.list(Airline.class);
	}
}
