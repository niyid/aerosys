package com.vasworks.airliner.struts.operator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.struts.OperatorAction;

public class BookingAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Integer flightNumber;
	
	private Date flightDate;
	
	private Long bookingId;
	
	private Long passengerId;
	
	private Long aircraftOwnerId;
	
	private Booking entity;
	
	private List<Booking> bookings;

	private List<Long> titleIds;

	private List<Passenger> passengers;
	
	private Integer flightNumber2;
	
	private Integer passengerCount;
	
	private List<Flight> selectedFlights;
	
	private List<FlightId> flightIds;
	
	private Long customerId;
	
	@Override
	public void prepare() {
		LOG.info("prepare - " + passengerCount);

		if(passengerCount != null) {
			titleIds = new ArrayList<Long>(passengerCount);
			for(int i = 0; i < passengerCount; i++) {
				titleIds.add(null);
			}
			passengers = new ArrayList<Passenger>(passengerCount);
			for(int i = 0; i < passengerCount; i++) {
				passengers.add(new Passenger());
			}
		}
		
		FlightId flightId = (FlightId) session.get("selected_flight");
		if(flightId != null) {
			flightIds = new ArrayList<FlightId>();
			selectedFlights = new ArrayList<Flight>();
			flightIds.add(flightId);
			selectedFlights.add((Flight) operatorService.find(flightId, Flight.class));
		}
		
		flightId = (FlightId) session.get("selected_flight2");
		if(flightId != null) {
			flightIds.add(flightId);
			selectedFlights.add((Flight) operatorService.find(flightId, Flight.class));
		}
	}

	@Override
	public String execute() {
		session.put("booking_id", null);
		session.put("itinerary_ids", new ArrayList<FlightId>());
		
		if(entity == null) {
			entity = new Booking();
		}
		
		return super.execute();
	}
	
	@SkipValidation
	public String load() {
		LOG.info("load - " + bookingId);
		if(bookingId != null) {
			if(entity == null) {
				entity = (Booking) operatorService.find(bookingId, Booking.class);
				for(Ticket t : entity.getTickets()) {
					LOG.info("load - ticket - " + t.getTicketCode());
				}
			}
		}
		return SUCCESS;
	}
	
	public String confirm() {
		if(bookingId != null) {
			operatorService.confirmBooking(bookingId, getUserId());
		}
		
		addActionMessage("Booking successfully confirmed.");
		
		return SUCCESS;
	}
	
	public String cancel() {
		if(bookingId != null) {
			operatorService.cancelBooking(bookingId, getUserId());
		}
		
		addActionMessage("Booking successfully cancelled.");
		
		return SUCCESS;
	}
	
	public String save() {
		LOG.info("save - " + passengerCount + " " + flightIds + " " + titleIds);
		operatorService.createBooking(flightIds, passengers, titleIds, customerId, getUserId());
		
		entity = new Booking();
		bookingId = null;
		
		session.remove("selected_flight");
		session.remove("selected_flight2");
		
		addActionMessage("Booking successfully saved.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String addFlight() {
		List<FlightId> itineraryIds = (List<FlightId>) session.get("itinerary_ids");
		
		itineraryIds.add(new FlightId(flightNumber, getAirline().getAirlineCode(), flightDate));
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String removeFlight() {
		List<FlightId> itineraryIds = (List<FlightId>) session.get("itinerary_ids");
		
		itineraryIds.remove(new FlightId(flightNumber, getAirline().getAirlineCode(), flightDate));
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(bookingId == null) {
			if(selectedFlights == null || selectedFlights.size() == 0) {
				addActionError("At least one flight must be selected or booking already saved.");
			}
			
			if(passengerCount == 0 || passengers == null) {
				addActionError("Booking must contain passengers.");
			}
			
			if(passengers != null) {
				Passenger p;
				for(int i = 0; i < passengers.size(); i++) {
					p = passengers.get(i);
					if(StringUtils.isEmpty(p.getFirstName())) {
						addActionError("ROW[" + (i + 1) + "] " + "'First Name' is required.");
					}
					if(StringUtils.isEmpty(p.getLastName())) {
						addActionError("ROW[" + (i + 1) + "] " + "'Last Name' is required.");
					}
					if(StringUtils.isEmpty(p.getEmail1())) {
						addActionError("ROW[" + (i + 1) + "] " + "'Email' is required.");
					}
					if(StringUtils.isEmpty(p.getPhoneNumber1())) {
						addActionError("ROW[" + (i + 1) + "] " + "'Cellphone' is required.");
					}
					if(StringUtils.isEmpty(p.getPassportNumber())) {
						addActionError("ROW[" + (i + 1) + "] " + "'ID/Passport Number' is required.");
					}
					if(p.getGender() == null) {
						addActionError("ROW[" + (i + 1) + "] " + "'Gender' is required.");
					}
					if(p.getTicketClass() == null) {
						addActionError("ROW[" + (i + 1) + "] " + "'Ticket Class' is required.");
					}
				}
			}
			if(getUser().hasRole("ROLE_OPERATOR") || getUser().hasRole("ROLE_ADMIN")) {
				if(customerId == null) {
					addFieldError("customerId", "You must select a customer");
				}
			} else {
				customerId = getUserId();
			}
		}
	}

	public String initNew() {
		entity = new Booking();
		bookingId = null;
		
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
		bookings = operatorService.listBookings(new FlightId(flightNumber, getAirline().getAirlineCode(), flightDate));
		
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

	public Long getAircraftOwnerId() {
		return aircraftOwnerId;
	}

	public void setAircraftOwnerId(Long aircraftOwnerId) {
		this.aircraftOwnerId = aircraftOwnerId;
	}

	public Booking getEntity() {
		return entity;
	}

	public void setEntity(Booking entity) {
		this.entity = entity;
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

	public List<Booking> getBookings() {
		return bookings;
	}

	public Integer getFlightNumber2() {
		return flightNumber2;
	}

	public void setFlightNumber2(Integer departureFlight) {
		this.flightNumber2 = departureFlight;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public List<Flight> getSelectedFlights() {
		return selectedFlights;
	}

	public void setSelectedFlights(List<Flight> selectedFlights) {
		this.selectedFlights = selectedFlights;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<FlightId> getFlightIds() {
		return flightIds;
	}

	public void setFlightIds(List<FlightId> flightIds) {
		this.flightIds = flightIds;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
}
