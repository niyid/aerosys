package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

public class TicketData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332355732060079879L;


	private String ticketCode;
	
	private Integer flightNumber;
	
	private String airlineCode;

	private Date departureTime;
	
	private Date arrivalTime;
	
	private String departureAirportCode;
	
	private String arrivalAirportCode;
	
	private String organizationName;

	private String regNumber;

	private String modelName;

	private String makeName;
	
	private String reservationCode;
	
	private Integer rowNumber;
	
	private String columnId;
	
	private String minorPassenger;
	
	private String passportNumber;
	
	private String passengerName;
	
	private String gender;
	
	private String passengerWeight;
	
	private String carryOnWeight;
	
	private String totalWeight;
	
	private String luggageCount;
	
	private String luggageWeight;
	
	private Double price;
	
	private String agentName;
	
	private Integer manifestRow;

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getMinorPassenger() {
		return minorPassenger;
	}

	public void setMinorPassenger(String minorPassenger) {
		this.minorPassenger = minorPassenger;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String firstName) {
		this.passengerName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassengerWeight() {
		return passengerWeight;
	}

	public void setPassengerWeight(String passengerWeight) {
		this.passengerWeight = passengerWeight;
	}

	public String getCarryOnWeight() {
		return carryOnWeight;
	}

	public void setCarryOnWeight(String carryOnWeight) {
		this.carryOnWeight = carryOnWeight;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getLuggageCount() {
		return luggageCount;
	}

	public void setLuggageCount(String luggageCount) {
		this.luggageCount = luggageCount;
	}

	public String getLuggageWeight() {
		return luggageWeight;
	}

	public void setLuggageWeight(String luggageWeight) {
		this.luggageWeight = luggageWeight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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

	public Integer getManifestRow() {
		return manifestRow;
	}

	public void setManifestRow(Integer manifestRow) {
		this.manifestRow = manifestRow;
	}

}
