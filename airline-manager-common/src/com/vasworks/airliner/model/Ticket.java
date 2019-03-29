package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ticket implements Serializable {
	
	public static enum TicketStatus {NEW, SENT, CANCELLED};
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -842547555262200973L;

	private String ticketCode;
	
	private Booking booking;
	
	private FlightSeat flightSeat;
	
	private Passenger passenger;
	
	private Double price;
	
	private Invoice invoice;
	
	private int leg = 1;
	
	private String filePath;
	
	private TicketStatus ticketStatus;
	
	private Date createDate;
	
	@Id
	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public int getLeg() {
		return leg;
	}

	public void setLeg(int leg) {
		this.leg = leg;
	}
	
	@ManyToOne
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@OneToOne
	public FlightSeat getFlightSeat() {
		return flightSeat;
	}

	public void setFlightSeat(FlightSeat flightSeat) {
		this.flightSeat = flightSeat;
	}

	@ManyToOne
	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@ManyToOne
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
