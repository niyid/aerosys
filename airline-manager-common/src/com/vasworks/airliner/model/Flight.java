package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.entity.Currency;

@Entity
public class Flight implements Serializable, Activity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5291261638603144031L;
	
	public static enum FlightStatus {AWAITING, BOARDING, DEPARTED, ARRIVED, DELAYED, CANCELED};
	
	public static enum FlightFrequency {ONCE, DAILY, WEEKLY, MONTHLY};
	
	private FlightId id;
	
	private String description;

	private Date departureTime;
	
	private Date arrivalTime;
	
	private Airport departureAirport;
	
	private Airport arrivalAirport;
	
	private Airplane airplane;
	
	private Double airFareEconomy;
	
	private Double airFareBizClass;
	
	private Double airFareFirstClass;
	
	private Double ymAirFareEconomy;
	
	private Double ymAirFareBizClass;
	
	private Double ymAirFareFirstClass;
	
	private Double estimatedLuggageWeight;
	
	private Double actualLuggageWeight;
	
	private FlightStatus flightStatus;
	
	private FlightFrequency flightFrequency = FlightFrequency.ONCE;
	
	private Route flightRoute;
	
	private List<Flight> connections;
	
	private List<Tax> taxes;
	
	private List<Rebate> rebates;
	
	private List<FlightSeat> flightSeats;
	
	private List<Airport> thruAirports;
	
	private List<CrewMember> flightCrew;
	
	private List<Booking> bookings;
	
	private Currency currency;
	
	private int seatsLeft;
	
	private boolean openSeating = true;
	
	private boolean statInit = true;
	
	private int searchHitA;
	
	private int searchHitB;
	
	private int maxSearchDelta;
	
	private int bookedSeatsA;
	
	private int bookedSeatsB;
	
	private int maxBookedDelta;
	
	private boolean hasEconomy = true;
	
	private boolean hasBusiness;
	
	private boolean hasFirst;
	
	private String economyTravelName = "Business Travel";
	
	private String businessTravelName = "Business Class";
	
	private String firstTravelName = "First Class";
	
	private RoutePrice routePrice;

	@EmbeddedId
	public FlightId getId() {
		return id;
	}

	public void setId(FlightId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
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

	public Double getYmAirFareEconomy() {
		return ymAirFareEconomy;
	}

	public void setYmAirFareEconomy(Double ymAirFareEconomy) {
		this.ymAirFareEconomy = ymAirFareEconomy;
	}

	public Double getYmAirFareBizClass() {
		return ymAirFareBizClass;
	}

	public void setYmAirFareBizClass(Double ymAirFareBizClass) {
		this.ymAirFareBizClass = ymAirFareBizClass;
	}

	public Double getYmAirFareFirstClass() {
		return ymAirFareFirstClass;
	}

	public void setYmAirFareFirstClass(Double ymAirFareFirstClass) {
		this.ymAirFareFirstClass = ymAirFareFirstClass;
	}

	public Double getEstimatedLuggageWeight() {
		return estimatedLuggageWeight;
	}

	public void setEstimatedLuggageWeight(Double estimatedLuggageWeight) {
		this.estimatedLuggageWeight = estimatedLuggageWeight;
	}

	public Double getActualLuggageWeight() {
		return actualLuggageWeight;
	}

	public void setActualLuggageWeight(Double totalLuggageWeight) {
		this.actualLuggageWeight = totalLuggageWeight;
	}

	public FlightStatus getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(FlightStatus flightStatus) {
		this.flightStatus = flightStatus;
	}

	public FlightFrequency getFlightFrequency() {
		return flightFrequency;
	}

	public void setFlightFrequency(FlightFrequency flightFrequency) {
		this.flightFrequency = flightFrequency;
	}

	@ManyToOne
	public Route getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(Route flightRoute) {
		this.flightRoute = flightRoute;
	}

	@ManyToMany
	public List<Flight> getConnections() {
		return connections;
	}

	public void setConnections(List<Flight> connections) {
		this.connections = connections;
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

	@OneToMany(mappedBy = "flight")
	@OrderBy("rowNumber ASC, columnId ASC")
	public List<FlightSeat> getFlightSeats() {
		return flightSeats;
	}

	public void setFlightSeats(List<FlightSeat> flightSeats) {
		this.flightSeats = flightSeats;
	}

	@ManyToMany
	public List<Airport> getThruAirports() {
		return thruAirports;
	}

	public void setThruAirports(List<Airport> flightStops) {
		this.thruAirports = flightStops;
	}

	@ManyToMany
	public List<CrewMember> getFlightCrew() {
		return flightCrew;
	}

	public void setFlightCrew(List<CrewMember> flightCrew) {
		this.flightCrew = flightCrew;
	}

	@OneToMany(mappedBy = "flight")
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
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
		StringBuilder b = new StringBuilder("Flight number ");
		b.append(id.getFlightNumber());
		b.append(" from ");
		b.append(departureAirport.getAirportCode());
		b.append(" to ");
		b.append(arrivalAirport.getAirportCode());
		b.append(" on ");
		b.append(id.getFlightDate());
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
	
	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}

	public boolean isOpenSeating() {
		return openSeating;
	}

	public void setOpenSeating(boolean openSeating) {
		this.openSeating = openSeating;
	}

	public boolean isStatInit() {
		return statInit;
	}

	public void setStatInit(boolean statInit) {
		this.statInit = statInit;
	}

	public int getSearchHitA() {
		return searchHitA;
	}

	public void setSearchHitA(int searchHitA) {
		this.searchHitA = searchHitA;
	}

	public int getSearchHitB() {
		return searchHitB;
	}

	public void setSearchHitB(int searchHitB) {
		this.searchHitB = searchHitB;
	}

	public int getMaxSearchDelta() {
		return maxSearchDelta;
	}

	public void setMaxSearchDelta(int maxSearchDelta) {
		this.maxSearchDelta = maxSearchDelta;
	}

	public int getBookedSeatsA() {
		return bookedSeatsA;
	}

	public void setBookedSeatsA(int bookedSeatsA) {
		this.bookedSeatsA = bookedSeatsA;
	}

	public int getBookedSeatsB() {
		return bookedSeatsB;
	}

	public void setBookedSeatsB(int bookedSeatsB) {
		this.bookedSeatsB = bookedSeatsB;
	}

	public int getMaxBookedDelta() {
		return maxBookedDelta;
	}

	public void setMaxBookedDelta(int maxBookedDelta) {
		this.maxBookedDelta = maxBookedDelta;
	}
	
	public boolean hasSeatClass(SeatClass seatClass) {
		boolean flag = false;
		if(seatClass != null) {
			for(FlightSeat s : flightSeats) {
				if(seatClass.equals(s.getSeatClass())) {
					flag = true;
				}
			}
		}
		return flag;
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

	@Transient
	public RoutePrice getRoutePrice() {
		return routePrice;
	}

	public void setRoutePrice(RoutePrice routePrice) {
		this.routePrice = routePrice;
	}
}
