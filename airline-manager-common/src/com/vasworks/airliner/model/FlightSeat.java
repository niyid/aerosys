package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class FlightSeat implements Serializable, SeatInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430141682483163280L;
	
	private Long id;
	
	private int seatIndex;
	
	private SeatStatus seatStatus;
	
	private Double flightFare;
	
	private Flight flight;
	
	private SeatClass seatClass;
	
	private SeatLocation seatLocation;
	
	private boolean exitRow;

	private Integer rowNumber;
	
	private String columnId;
	
	private Ticket ticket;
	
	public static FlightSeat cloneSeat(Seat seat) {
		FlightSeat flightSeat = new FlightSeat();
		
		double fare = seat.getFlight().getAirFareEconomy();
		if(seat.getSeatClass().equals(SeatClass.FIRST)) {
			fare = seat.getFlight().getAirFareFirstClass();	
		} else
		if(seat.getSeatClass().equals(SeatClass.BUSINESS)) {
			fare = seat.getFlight().getAirFareBizClass();	
		}
		flightSeat.setFlight(seat.getFlight());
		flightSeat.setExitRow(seat.isExitRow());
		flightSeat.setFlightFare(fare);
		flightSeat.setSeatStatus(SeatStatus.VACANT);
		flightSeat.setSeatClass(seat.getSeatClass());
		flightSeat.setSeatIndex(seat.getSeatIndex());
		flightSeat.setSeatLocation(seat.getSeatLocation());
		flightSeat.setSeatStatus(seat.getSeatStatus());
		flightSeat.setRowNumber(seat.getId().getRowNumber());
		flightSeat.setColumnId(seat.getId().getColumnId());
		
		return flightSeat;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Double getFlightFare() {
		return flightFare;
	}

	public void setFlightFare(Double flightFare) {
		this.flightFare = flightFare;
	}

	@ManyToOne
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

	@OneToOne
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
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
		FlightSeat other = (FlightSeat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
