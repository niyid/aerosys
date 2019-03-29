package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vasworks.entity.Currency;

@Entity
public class FlightSchedule implements Serializable, Activity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5291261638603144031L;
	
	public static enum ScheduleStatus {FLIGHT_OPS, COMMERCIALS, FRONT_DESK, COMPLETE};
	
	private Long id;
	
	private String description;
	
	private String flightNumber;

	private Date departureTime;
	
	private Date arrivalTime;
	
	private Airport departureAirport;
	
	private Airport arrivalAirport;
	
	private Airplane airplane;
	
	private Double airFareEconomy;
	
	private Double airFareBizClass;
	
	private Double airFareFirstClass;
	
	private Route flightRoute;
	
	private boolean autogenerate;
	
	private List<Tax> taxes;
	
	private List<Rebate> rebates;
	
	private Currency currency;

	private boolean openSeating = true;
	
	private Date startDate;
	
	private int numOfWeeks;
	
	private boolean hasEconomy = true;
	
	private boolean hasBusiness;
	
	private boolean hasFirst;

	private String economyTravelName = "Business Travel";
	
	private String businessTravelName = "Business Class";
	
	private String firstTravelName = "First Class";
	
	private List<CrewMember> flightCrew;
	
	private List<FlightScheduleDay> flightScheduleDays;
	
	private ScheduleStatus scheduleStatus = ScheduleStatus.FLIGHT_OPS;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	@Temporal(TemporalType.TIME)
	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@Temporal(TemporalType.TIME)
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@ManyToOne
	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	@ManyToOne
	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	@ManyToOne
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public Double getAirFareEconomy() {
		return airFareEconomy;
	}

	public void setAirFareEconomy(Double airFair) {
		this.airFareEconomy = airFair;
	}

	public Double getAirFareBizClass() {
		return airFareBizClass;
	}

	public void setAirFareBizClass(Double airFairBizClass) {
		this.airFareBizClass = airFairBizClass;
	}

	public Double getAirFareFirstClass() {
		return airFareFirstClass;
	}

	public void setAirFareFirstClass(Double airFairFirstClass) {
		this.airFareFirstClass = airFairFirstClass;
	}

	@ManyToOne
	public Route getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(Route flightRoute) {
		this.flightRoute = flightRoute;
	}

	@ManyToMany
	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

	@ManyToMany
	public List<Rebate> getRebates() {
		return rebates;
	}

	public void setRebates(List<Rebate> rebates) {
		this.rebates = rebates;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	@Transient
	public Date getStartTime() {
		return departureTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		this.departureTime = startTime;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	@Transient
	public Date getEndTime() {
		return arrivalTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.arrivalTime = endTime;
	}

	@Override
	@Transient
	public String getDescription() {
		StringBuilder b = new StringBuilder("Schedule number ");
		b.append(id);
		b.append(" from ");
		b.append(departureAirport.getAirportCode());
		b.append(" to ");
		b.append(arrivalAirport.getAirportCode());
		b.append(".");
		
		return description != null ? description : b.toString();
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public ActivityType getActivityType() {
		return ActivityType.FLIGHT;
	}

	@Override
	public void setActivityType(ActivityType activityType) {
		//Do nothing
	}

	@ManyToOne
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public boolean isOpenSeating() {
		return openSeating;
	}

	public void setOpenSeating(boolean openSeating) {
		this.openSeating = openSeating;
	}

	@Override
	@ManyToMany
	public List<CrewMember> getFlightCrew() {
		return flightCrew;
	}

	@Override
	public void setFlightCrew(List<CrewMember> flightCrew) {
		this.flightCrew = flightCrew;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNumOfWeeks() {
		return numOfWeeks;
	}

	public void setNumOfWeeks(int numOfWeeks) {
		this.numOfWeeks = numOfWeeks;
	}

	public boolean isHasEconomy() {
		return hasEconomy;
	}

	public void setHasEconomy(boolean hasEconomy) {
		this.hasEconomy = hasEconomy;
	}

	public boolean isHasBusiness() {
		return hasBusiness;
	}

	public void setHasBusiness(boolean hasBusiness) {
		this.hasBusiness = hasBusiness;
	}

	public boolean isHasFirst() {
		return hasFirst;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}
	
	public String getEconomyTravelName() {
		return economyTravelName;
	}

	public void setEconomyTravelName(String economyTravelName) {
		this.economyTravelName = economyTravelName;
	}

	public String getBusinessTravelName() {
		return businessTravelName;
	}

	public void setBusinessTravelName(String businessTravelName) {
		this.businessTravelName = businessTravelName;
	}

	public String getFirstTravelName() {
		return firstTravelName;
	}

	public void setFirstTravelName(String firstTravelName) {
		this.firstTravelName = firstTravelName;
	}
	
	@OneToMany(mappedBy = "flightSchedule")
	public List<FlightScheduleDay> getFlightScheduleDays() {
		return flightScheduleDays;
	}

	public void setFlightScheduleDays(List<FlightScheduleDay> flightScheduleDays) {
		this.flightScheduleDays = flightScheduleDays;
	}

	public ScheduleStatus getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(ScheduleStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public boolean isAutogenerate() {
		return autogenerate;
	}

	public void setAutogenerate(boolean autogenerate) {
		this.autogenerate = autogenerate;
	}

}
