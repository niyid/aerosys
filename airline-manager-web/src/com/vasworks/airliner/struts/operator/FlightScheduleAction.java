package com.vasworks.airliner.struts.operator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.Flight.FlightStatus;
import com.vasworks.airliner.model.FlightSchedule;
import com.vasworks.airliner.model.FlightSchedule.ScheduleStatus;
import com.vasworks.airliner.model.FlightScheduleDay;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.service.ScheduleException;
import com.vasworks.airliner.struts.FlightScheduleStatus;
import com.vasworks.airliner.struts.OperatorAction;

public class FlightScheduleAction extends OperatorAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	private Long flightScheduleId;
	
	private FlightSchedule entity;
	
	private List<FlightSchedule> flightSchedules;
	
	private String departureAirportCode;
	
	private String arrivalAirportCode;
	
	private String airplaneRegNumber;
	
	private String currencyCode;
	
	private RateId[] selTaxIds;
	
	private RateId[] selRebateIds;
	
	private int[] selWeekDays;
	
	private List<FlightScheduleStatus> scheduleStatuses;
	
	@Override
	public void prepare() {
		entity = new FlightSchedule();
	}

	@Override
	public String execute() {
		session.put("flight_id", null);
		return super.execute();
	}
	
	public String load() {
		if(flightScheduleId != null) {
			if(entity == null) {
				entity = (FlightSchedule) operatorService.find(flightScheduleId, FlightSchedule.class);
				selectStatus();
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.flightNumber", message = "'Flight Number' is required."),
				@RequiredStringValidator(fieldName = "departureAirportCode", message = "'Departure' is required."),
				@RequiredStringValidator(fieldName = "arrivalAirportCode", message = "'Arrival' is required."),
				@RequiredStringValidator(fieldName = "airplaneRegNumber", message = "'Airplane' is required."),
				@RequiredStringValidator(fieldName = "currencyCode", message = "'Currency' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "entity.startDate", message = "'Start Date' is required."),
				@RequiredFieldValidator(fieldName = "entity.departureTime", message = "'Departure Time' is required."),
				@RequiredFieldValidator(fieldName = "entity.arrivalTime", message = "'Arrival Time' is required."),
				@RequiredFieldValidator(fieldName = "entity.airFareEconomy", message = "'Economy-class Fair' is required."),
				@RequiredFieldValidator(fieldName = "entity.numOfWeeks", message = "'Number of Weeks' is required.")
			}
		)
	public String save() {
		
		entity = operatorService.saveFlightSchedule(entity, flightScheduleId, departureAirportCode, arrivalAirportCode, airplaneRegNumber, currencyCode, selWeekDays, selTaxIds, selRebateIds, getUserId());
		
		session.put("flight_schedule_id", flightScheduleId);

		entity = new FlightSchedule();
		flightScheduleId = null;
		departureAirportCode = null;
		arrivalAirportCode = null;
		airplaneRegNumber = null;
		currencyCode = null;
		
		selWeekDays = null;
		selTaxIds = null;
		selRebateIds = null;
		
		addActionMessage("Flight Schedule successfully saved.");
		selectStatus();
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(selWeekDays == null || selWeekDays.length == 0) {
			addFieldError("selWeekDays", "'Select Days of Week' is required.");
		}
		if(departureAirportCode != null && arrivalAirportCode != null && departureAirportCode.equals(arrivalAirportCode)) {
			addFieldError("arrivalAirportCode", "'Departure Airport' and 'Arrival Airport' cannot be the same.");
		}
		if(entity != null) {
			if(entity.getArrivalTime() != null && entity.getDepartureTime() != null && entity.getArrivalTime().before(entity.getDepartureTime())) {
				addFieldError("arrivalTime", "'Arrival Time' cannot be before 'Departure Time'.");
			}		
		}
	}

	public String initNew() {
		entity = new FlightSchedule();
		
		session.put("flight_schedule_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(flightScheduleId != null) {
			entity = (FlightSchedule) operatorService.find(flightScheduleId, FlightSchedule.class);
			selectStatus();
			
			departureAirportCode = entity.getDepartureAirport().getAirportCode();
			arrivalAirportCode = entity.getArrivalAirport().getAirportCode();
			airplaneRegNumber = entity.getAirplane().getRegNumber();
			currencyCode = entity.getCurrency().getCurrencyCode();
			
			initSelTaxIds();
			initSelRebateIds();
			initSelWeekDays();
			
			session.put("flight_schedule_id", flightScheduleId);
		}
		return SUCCESS;
	}
	
	@SkipValidation
	public String generate() {
		if(flightScheduleId != null) {
			try {
				entity = (FlightSchedule) operatorService.find(flightScheduleId, FlightSchedule.class);
				
				departureAirportCode = entity.getDepartureAirport().getAirportCode();
				arrivalAirportCode = entity.getArrivalAirport().getAirportCode();
				airplaneRegNumber = entity.getAirplane().getRegNumber();
				currencyCode = entity.getCurrency().getCurrencyCode();
				
				initSelTaxIds();
				initSelRebateIds();
				initSelWeekDays();
				
				session.put("flight_schedule_id", flightScheduleId);
				List<Flight> flights = operatorService.generateFlights(flightScheduleId, getUserId());
				addActionMessage(flights.size() + " flights successfully generated.");
			} catch(ScheduleException e) {
				addActionError(e.getMessage());
			} catch (Exception e) {
				addActionError("An error occurred; please contact the Administrator for more information.");
				e.printStackTrace();
			}
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
	
	private void initSelWeekDays() {
		if(entity != null) {
			List<FlightScheduleDay> list = entity.getFlightScheduleDays();
			selWeekDays = new int[list.size()];
			
			int i = 0;
			for(FlightScheduleDay o : list) {
				selWeekDays[i] = o.getDayOfWeek();
				i++;
			}
		}
	}
	
	public String remove() {
		if(flightScheduleId != null) {
			operatorService.remove(flightScheduleId, FlightSchedule.class);
		}
		
		return list();
	}
	
	public String list() {
		flightSchedules = operatorService.listFlightSchedules(getAirlineId());
		
		return SUCCESS;
	}

	public Long getFlightScheduleId() {
		return flightScheduleId;
	}

	public void setFlightScheduleId(Long flightScheduleId) {
		this.flightScheduleId = flightScheduleId;
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

	public FlightSchedule getEntity() {
		return entity;
	}

	public void setEntity(FlightSchedule entity) {
		this.entity = entity;
	}

	public List<FlightSchedule> getFlightSchedules() {
		return flightSchedules;
	}
	
	public FlightStatus[] getFlightStatusLov() {
		return FlightStatus.values();
	}
	
	public DayOfWeek[] getWeekDayLov() {
		return DayOfWeek.values();
	}
	
	public Collection<? extends RateInterface> getTaxLov() {
		return operatorService.listNonAutoTaxes(getAirlineId());
	}
	
	public Collection<? extends RateInterface> getRebateLov() {
		return operatorService.listNonAutoRebates(getAirlineId());
	}
	
	public List<Airplane> getAirplaneLov() {
		return operatorService.listAirplanes(getAirlineId());
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

	public int[] getSelWeekDays() {
		return selWeekDays;
	}

	public void setSelWeekDays(int[] selWeekDays) {
		this.selWeekDays = selWeekDays;
	}

	public String getDepartureTime() {
		return entity.getDepartureTime() != null ? TIME_FORMAT.format(entity.getDepartureTime()) : null;
//		return entity.getDepartureTime();
	}

	public void setDepartureTime(String departureTime) {
		try {
			if(departureTime != null) {
				entity.setDepartureTime(TIME_FORMAT.parse(departureTime));
				LOG.info("setDepartureTime - " + departureTime + " -> " + entity.getDepartureTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getArrivalTime() {
		return entity.getArrivalTime() != null ? TIME_FORMAT.format(entity.getArrivalTime()) : null;
//		return entity.getArrivalTime();
	}

	public void setArrivalTime(String arrivalTime) {
		try {
			if(arrivalTime != null) {
				entity.setArrivalTime(TIME_FORMAT.parse(arrivalTime));
				LOG.info("setArrivalTime - " + arrivalTime + " -> " + entity.getArrivalTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectStatus() {
		LOG.info("selectStatus - " + entity.getScheduleStatus());
		if(entity != null) {
			switch (entity.getScheduleStatus()) {
			case FLIGHT_OPS:
			default:
				statusFlightOperations();
				break;
			case COMMERCIALS:
				statusCommercials();
				break;
			case FRONT_DESK:
				statusFrontDesk();
				break;
			case COMPLETE:
				statusComplete();
				break;
			}
		}
	}
	
	public void initScheduleStatuses() {
		scheduleStatuses = new ArrayList<FlightScheduleStatus>(4);
		scheduleStatuses.add(new FlightScheduleStatus(1, "Flight Operations", ScheduleStatus.FLIGHT_OPS, "active"));
		scheduleStatuses.add(new FlightScheduleStatus(2, "Commercials", ScheduleStatus.COMMERCIALS, "disabled"));
		scheduleStatuses.add(new FlightScheduleStatus(3, "Front Desk", ScheduleStatus.FRONT_DESK, "disabled"));
		scheduleStatuses.add(new FlightScheduleStatus(4, "Complete", ScheduleStatus.COMPLETE, "disabled"));
	}
	
	public void statusFlightOperations() {
		scheduleStatuses = new ArrayList<FlightScheduleStatus>(4);
		scheduleStatuses.add(new FlightScheduleStatus(1, "Flight Operations", ScheduleStatus.FLIGHT_OPS, "active"));
		scheduleStatuses.add(new FlightScheduleStatus(2, "Commercials", ScheduleStatus.COMMERCIALS, "disabled"));
		scheduleStatuses.add(new FlightScheduleStatus(3, "Front Desk", ScheduleStatus.FRONT_DESK, "disabled"));
		scheduleStatuses.add(new FlightScheduleStatus(4, "Complete", ScheduleStatus.COMPLETE, "disabled"));
	}
	
	public void statusCommercials() {
		scheduleStatuses = new ArrayList<FlightScheduleStatus>(4);
		scheduleStatuses.add(new FlightScheduleStatus(1, "Flight Operations", ScheduleStatus.FLIGHT_OPS, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(2, "Commercials", ScheduleStatus.COMMERCIALS, "active"));
		scheduleStatuses.add(new FlightScheduleStatus(3, "Front Desk", ScheduleStatus.FRONT_DESK, "disabled"));
		scheduleStatuses.add(new FlightScheduleStatus(4, "Complete", ScheduleStatus.COMPLETE, "disabled"));
	}
	
	public void statusFrontDesk() {
		scheduleStatuses = new ArrayList<FlightScheduleStatus>(4);
		scheduleStatuses.add(new FlightScheduleStatus(1, "Flight Operations", ScheduleStatus.FLIGHT_OPS, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(2, "Commercials", ScheduleStatus.COMMERCIALS, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(3, "Front Desk", ScheduleStatus.FRONT_DESK, "active"));
		scheduleStatuses.add(new FlightScheduleStatus(4, "Complete", ScheduleStatus.COMPLETE, "disabled"));
	}
	
	public void statusComplete() {
		scheduleStatuses = new ArrayList<FlightScheduleStatus>(4);
		scheduleStatuses.add(new FlightScheduleStatus(1, "Flight Operations", ScheduleStatus.FLIGHT_OPS, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(2, "Commercials", ScheduleStatus.COMMERCIALS, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(3, "Front Desk", ScheduleStatus.FRONT_DESK, "complete"));
		scheduleStatuses.add(new FlightScheduleStatus(4, "Complete", ScheduleStatus.COMPLETE, "active"));
	}

	public List<FlightScheduleStatus> getScheduleStatuses() {
		return scheduleStatuses;
	}
}
