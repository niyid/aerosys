package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Seat implements Serializable, SeatInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430141682483163280L;
	
	private SeatId id;
	
	private int seatIndex;
	
	private SeatStatus seatStatus;
	
	private SeatStatus flightSeatStatus;
	
	private Double flightFare;
	
	private Flight flight;
	
	private SeatClass seatClass;
	
	private SeatLocation seatLocation;
	
	private boolean exitRow;
	
	private Airplane airplane;
	
	@EmbeddedId
	public SeatId getId() {
		return id;
	}

	public void setId(SeatId id) {
		this.id = id;
	}

	public int getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(int seatIndex) {
		this.seatIndex = seatIndex;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	@Transient
	public SeatStatus getFlightSeatStatus() {
		return flightSeatStatus;
	}

	public void setFlightSeatStatus(SeatStatus flightSeatStatus) {
		this.flightSeatStatus = flightSeatStatus;
	}

	@Transient
	public Double getFlightFare() {
		double fare = flight.getAirFareEconomy();
		if(seatClass.equals(SeatClass.FIRST)) {
			fare = flight.getAirFareFirstClass();	
		} else
		if(seatClass.equals(SeatClass.BUSINESS)) {
			fare = flight.getAirFareBizClass();	
		}
		
		return fare;
	}

	public void setFlightFare(Double flightFare) {
		this.flightFare = flightFare;
	}

	@Transient
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	public SeatLocation getSeatLocation() {
		return seatLocation;
	}

	public void setSeatLocation(SeatLocation seatLocation) {
		this.seatLocation = seatLocation;
	}

	public boolean isExitRow() {
		return exitRow;
	}

	public void setExitRow(boolean exitRow) {
		this.exitRow = exitRow;
	}

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name = "regNumber")
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
