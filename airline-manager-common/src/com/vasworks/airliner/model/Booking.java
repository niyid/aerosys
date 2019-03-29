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

import com.vasworks.entity.Personnel;
import com.vasworks.security.model.AppUser;

@Entity
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5688075153735739770L;
	
	public static enum BookingStatus {NEW, PAID, CONFIRMED, CANCELED, INVOICED};

	private Long id;
	
	private String reservationCode;
	
	private boolean paid = true;
	
	private boolean oneWay;
	
	private boolean nonStop = true;
	
	private Double currencyRate;
	
	private Double totalPrice;
	
	private Date bookingDate = new Date();
	
	private Date confirmationDate;
	
	private Date checkinDate;
	
	private Date cancellationDate;
	
	private BookingStatus bookingStatus = BookingStatus.NEW;
	
	private Flight flight;
	
	private Customer owner;
	
	private Personnel agent;
	
	private AppUser confirmedBy;
	
	private List<Tax> taxes;
	
	private List<ContextTax> contextTax;
	
	private List<Rebate> rebates;
	
	private List<Ticket> tickets;
	
	private List<Ticket> fetchedTickets;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public boolean isOneWay() {
		return oneWay;
	}

	public void setOneWay(boolean oneWay) {
		this.oneWay = oneWay;
	}

	public boolean isNonStop() {
		return nonStop;
	}

	public void setNonStop(boolean nonStop) {
		this.nonStop = nonStop;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	@ManyToOne
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	@ManyToOne
	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	@ManyToOne
	public Personnel getAgent() {
		return agent;
	}

	public void setAgent(Personnel agent) {
		this.agent = agent;
	}

	@ManyToOne
	public AppUser getConfirmedBy() {
		return confirmedBy;
	}

	public void setConfirmedBy(AppUser confirmedBy) {
		this.confirmedBy = confirmedBy;
	}

	@ManyToMany
	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

	@ManyToMany
	public List<ContextTax> getContextTax() {
		return contextTax;
	}

	public void setContextTax(List<ContextTax> contextTax) {
		this.contextTax = contextTax;
	}

	@ManyToMany
	public List<Rebate> getRebates() {
		return rebates;
	}

	public void setRebates(List<Rebate> rebates) {
		this.rebates = rebates;
	}

	@OneToMany(mappedBy="booking")
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Transient
	public List<Ticket> getFetchedTickets() {
		return fetchedTickets;
	}

	public void setFetchedTickets(List<Ticket> fetchedTickets) {
		this.fetchedTickets = fetchedTickets;
	}
}
