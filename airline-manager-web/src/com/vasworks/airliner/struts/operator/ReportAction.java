package com.vasworks.airliner.struts.operator;

import java.util.Date;
import java.util.List;

import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.struts.OperatorAction;

public class ReportAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private List<Ticket> tickets;
	
	private List<Seat> unsoldSeats;
	
	private Date fromDate;
	
	private Date toDate;
	
	private Date flightDate;
	
	private Integer flightNumber;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		if(flightNumber != null && flightDate != null) {
			tickets = operatorService.listTicketSales(new FlightId(flightNumber, getAirlineCode(), flightDate));
			unsoldSeats = operatorService.listUnsoldSeats(new FlightId(flightNumber, getAirlineCode(), flightDate));
		} else {
			if(fromDate != null && toDate != null) {				
				tickets = operatorService.listTicketSales(fromDate, toDate);
				unsoldSeats = operatorService.listUnsoldSeats(fromDate, toDate);
			}
		}
		
		return SUCCESS;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public List<Seat> getUnsoldSeats() {
		return unsoldSeats;
	}
	
	public Double getTotalSales() {
		return operatorService.sumTicketSales(tickets);
	}
	
	public Double getTotalLosses() {
		return operatorService.sumFlightLosses(unsoldSeats);
	}
	
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}
	
}
