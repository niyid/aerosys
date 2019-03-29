package com.vasworks.airliner.struts.operator;

import java.util.Date;
import java.util.List;

import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

public class ReservationAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String reservationCode;
	
	private Integer flightNumber;
	
	private Date flightDate = new Date();
	
	private Long bookingId;
	
	private Long passengerId;
	
	private Long titleId;
	
	private Long aircraftOwnerId;
	
	private String lastName;
	
	private Booking entity;
	
	private List<Object[]> bookings;
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		return super.execute();
	}
	
	public String load() {
		if(bookingId != null) {
			if(entity == null) {
				entity = (Booking) operatorService.find(bookingId, Booking.class);
			}
		}
		return SUCCESS;
	}
	
	public String confirm() {
		entity = operatorService.confirmBooking(bookingId, 1L);
		
		addActionMessage("Confirmation completed.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String addFlight() {
		List<FlightId> itineraryIds = (List<FlightId>) session.get("itinerary_ids");
		
		itineraryIds.add(new FlightId(flightNumber, getAirlineCode(), flightDate));
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String removeFlight() {
		List<FlightId> itineraryIds = (List<FlightId>) session.get("itinerary_ids");
		
		itineraryIds.remove(new FlightId(flightNumber, getAirlineCode(), flightDate));
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
	}

	public String initNew() {
		entity = new Booking();
		bookingId = null;
		titleId = null;
		
		session.put("booking_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(bookingId != null) {
			entity = (Booking) operatorService.find(bookingId, Booking.class);

			session.put("booking_id", bookingId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(bookingId != null) {
			operatorService.remove(bookingId, Booking.class);
		}
		
		return list();
	}
	
	public String list() {
		if (hasAny("POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID,PRE_ETL_DEPOSIT")) {
			bookings = operatorService.searchMyBookings(reservationCode, flightNumber, getAirlineId(), getOrganizationId(), flightDate);
		} else {
			bookings = operatorService.searchBookings(reservationCode, flightNumber, getAirlineId(), flightDate, lastName);
		}
		
		return SUCCESS;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getAircraftOwnerId() {
		return aircraftOwnerId;
	}

	public void setAircraftOwnerId(Long aircraftOwnerId) {
		this.aircraftOwnerId = aircraftOwnerId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Booking getEntity() {
		return entity;
	}

	public void setEntity(Booking entity) {
		this.entity = entity;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public List<Object[]> getBookings() {
		return bookings;
	}

	public void setBookings(List<Object[]> bookings) {
		this.bookings = bookings;
	}
}
