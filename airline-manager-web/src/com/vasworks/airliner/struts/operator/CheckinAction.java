package com.vasworks.airliner.struts.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Organization;

public class CheckinAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long bookingId;
	
	private Long seatId;
	
	private int ticketIndex = 0;
	
	private Booking entity;
	
	private Double amount;
	
	private boolean skip;
	
	@Override
	public void prepare() {
		entity = new Booking();
		entity.setTickets(new ArrayList<Ticket>());
		if(bookingId != null) {
			if(!skip) {
				ticketIndex = operatorService.nextSeat(bookingId, getUserId());
			}
			entity = (Booking) operatorService.find(bookingId, Booking.class);
		}
	}

	@Override
	public String execute() {
		if(bookingId != null) {
			ticketIndex = operatorService.nextSeat(bookingId, getUserId());
		}
		return super.execute();
	}
	
	public String load() {
		LOG.info("CheckinAction.bookingId=" + bookingId);
		String returnTo = SUCCESS;
		if(bookingId != null) {
			ticketIndex = operatorService.nextSeat(bookingId, getUserId());
			entity = (Booking) operatorService.find(bookingId, Booking.class);
			if(entity.getCheckinDate() != null) {
				entity.setFetchedTickets(entity.getTickets());
				returnTo = "complete";
			}
		}
		return returnTo;
	}
	
	public String selectSeat() {
		LOG.info("selectSeat - CheckinAction.bookingId=" + bookingId + " " + " seatId=" + seatId + " ticketIndex=" + ticketIndex);
		if(bookingId != null) {
			entity = operatorService.selectSeat(bookingId, seatId, ticketIndex, getUserId());
			entity = (Booking) operatorService.find(bookingId, Booking.class);
			FlightSeat seat = (FlightSeat) operatorService.find(seatId, FlightSeat.class);
			addActionMessage("Seat " + seat.getRowNumber() + seat.getColumnId() + " selected.");
			ticketIndex = operatorService.nextSeat(bookingId, getUserId());
		}		
		
		return SUCCESS;
	}
	
	public String skip2Next() {
		LOG.info("skip2Next - CheckinAction.bookingId=" + bookingId + " " + " seatId=" + seatId + " ticketIndex=" + ticketIndex);
		if(bookingId != null) {
			ticketIndex = operatorService.skipSeat(bookingId, ticketIndex, getUserId());
		}		
		
		return SUCCESS;
	}
	
	public String deselectSeat() {
		LOG.info("deselectSeat - CheckinAction.bookingId=" + bookingId + " " + " seatId=" + seatId + " ticketIndex=" + ticketIndex);
		if(bookingId != null) {
			entity = operatorService.deselectSeat(bookingId, seatId, ticketIndex, getUserId());
			entity = (Booking) operatorService.find(bookingId, Booking.class);
			FlightSeat seat = (FlightSeat) operatorService.find(seatId, FlightSeat.class);
			addActionMessage("Seat " + seat.getRowNumber() + seat.getColumnId() + " deselected.");
			ticketIndex = operatorService.nextSeat(bookingId, getUserId());
		}		
		
		return SUCCESS;
	}
	
	public String confirm() {
		//TODO If customer is not POSTPAID, and carryOnWeight per passenger exceeds 23, show card payment section
		Set<Organization> postpaidCustomers = getPostpaidCustomerLov();
		
		boolean isPostPaid = false;
		for(Organization ogz : postpaidCustomers) {
			if(entity.getOwner().getId() == ogz.getId()) {
				isPostPaid = true;
				break;
			}
		}
		if(isPostPaid || amount != null || amount == 0.0) {
			entity = (Booking) operatorService.find(bookingId, Booking.class);
			List<Ticket> tickets = operatorService.listTickets(bookingId);
			entity.setFetchedTickets(tickets);
		}
		addActionMessage("Confirm to proceed.");
		
		return SUCCESS;
	}
	
	public String save() {
		//TODO If customer is not POSTPAID, and carryOnWeight per passenger exceeds 23, show card payment section
		Set<Organization> postpaidCustomers = getPostpaidCustomerLov();
		
		boolean isPostPaid = false;
		for(Organization ogz : postpaidCustomers) {
			if(entity.getOwner().getId() == ogz.getId()) {
				isPostPaid = true;
				break;
			}
		}
		if(isPostPaid || amount != null || amount == 0.0) {
			operatorService.flightCheckin(entity, getUserId());
			entity = (Booking) operatorService.find(bookingId, Booking.class);
			List<Ticket> tickets = operatorService.listTickets(bookingId);
			entity.setFetchedTickets(tickets);
			
			addActionMessage("Checkin complete.");
			
		} else {
			amount = operatorService.computerElc(entity, getUserId());
			addActionMessage("Please complete by paying Excess Luggage Charge.");
		}
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public Booking getEntity() {
		return entity;
	}

	public void setEntity(Booking entity) {
		this.entity = entity;
	}

	public int getTicketIndex() {
		return ticketIndex;
	}

	public void setTicketIndex(int ticketIndex) {
		this.ticketIndex = ticketIndex;
	}

	public Double getAmount() {
		return amount;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
