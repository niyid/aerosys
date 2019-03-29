package com.vasworks.airliner.struts.operator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

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
	
	private List<Long> bookingIds;
	
	private Double amount;
	
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
		flightIds = new ArrayList<FlightId>();
		if(flightId != null) {
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
		stepConfirm();
		if (Authorize.hasAny("ROLE_ADMIN,ROLE_OPERATOR")) {
			amount = operatorService.computePriceTotal(flightIds, passengers, (Long) session.get("client_id"));
		} else if (Authorize.hasAny("ROLE_CLIENT")) {
			amount = operatorService.computePriceTotal(flightIds, passengers, getUserId());
			if (hasAny("PRE_ETL_DEPOSIT")) {
				Customer customer = (Customer) operatorService.find(getUserId(), Customer.class);
				if((customer.getDepositAmount() - amount) < customer.getDepositLowerLimit()) {
					addActionError("Balance cannot cover cost: " + CURRENCY_FORMAT.format(customer.getDepositAmount() - amount) + " less than " + CURRENCY_FORMAT.format(customer.getDepositLowerLimit()));
				}
			}
		}
		
		if(!getActionErrors().isEmpty()) {
			return ERROR;
		}

		return "confirm";
	}
	
	@SkipValidation
	public String cancel() {
		if(bookingId != null) {
			operatorService.cancelBooking(bookingId, getUserId());
			addActionMessage("Booking successfully cancelled.");
			stepCancel();
		}
		
		return SUCCESS;
	}
	
	public String summary() {
		LOG.info("summary - " + bookingIds);
		if(bookingIds != null) {
			bookings = new ArrayList<>();
			
			for(Long bid : bookingIds) {
				Booking bk = (Booking) operatorService.find(bid, Booking.class);
				bk.setFetchedTickets(operatorService.listTickets(bid));
				bookings.add(bk);
			}
			stepSummary();
		}
		
		return SUCCESS;
	}
	
	public String save() {
		LOG.info("save - " + passengerCount + " " + flightIds + " " + titleIds);
		
		String returnTo = "summary";
		if(session.get("client_id") != null) {
			boolean booked = false;
			if (session.get("roles") == null) {
				bookingIds = operatorService.createBooking(flightIds, passengers, titleIds, (Long) session.get("client_id"), (Long) session.get("basket_id"));
				operatorService.confirmBookings(bookingIds, getUserId());
				booked = true;
			} else if (hasAny("POST_ETL,PRE_ETL_POSTPAID,PRE_ETL_PREPAID")) {
				bookingIds = operatorService.createBooking(flightIds, passengers, titleIds, getUserId(), getUserId());
				operatorService.confirmBookings(bookingIds, getUserId());
				booked = true;
			}
			
			session.remove("selected_flight");
			session.remove("selected_flight2");
			
			if(booked) {
				if(bookingIds == null) {
					addActionError("Not enough seats left on selected flight");
					stepConfirm();
					returnTo = ERROR;
				} else {
					stepSummary();
				}
			} else {
				addActionError("Wrong user permission configuration; please contact Administrator.");
				stepConfirm();
				returnTo = ERROR;
			}			
		} else {
			addActionError("You must select a customer from the user pull-down menu - 'Choose Customer'.");
			stepConfirm();
			returnTo = ERROR;
		}
		
		return returnTo;
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
		if(bookingId == null) {
			if (Authorize.hasAny("ROLE_ADMIN,ROLE_OPERATOR")) {
				if(session.get("client_id") == null) {
					addActionError("No client selected - please select from user profile menu.");
				}
			}
			
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
					if(p.getGender() == null) {
						addActionError("ROW[" + (i + 1) + "] " + "'Gender' is required.");
					}
				}
			}
//			if(getUser().hasRole("ROLE_OPERATOR") || getUser().hasRole("ROLE_ADMIN")) {
//				if(customerId == null) {
//					addFieldError("customerId", "You must select a customer");
//				}
//			} else {
//				customerId = getOrganizationId();
//			}
		}
		
		LOG.info("ActionMessages - " + getActionMessages());
		LOG.info("ActionErrors - " + getActionErrors());
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
		bookings = operatorService.listBookings(new FlightId(flightNumber, getAirlineCode(), flightDate));
		
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

	public List<Long> getBookingIds() {
		return bookingIds;
	}

	public void setBookingIds(List<Long> bookingIds) {
		this.bookingIds = bookingIds;
	}

	public List<Long> getTitleIds() {
		return titleIds;
	}

	public void setTitleIds(List<Long> titleIds) {
		this.titleIds = titleIds;
	}
	
	public boolean isPrepaidUser() {
		return hasAny("PRE_ETL_PREPAID");
	}
	
	public boolean isPostUser() {
		return hasAny("PRE_ETL_POSTPAID");
	}
	
	public boolean isDepositUser() {
		return hasAny("PRE_ETL_DEPOSIT");
	}
	
	public boolean isPostAtl() {
		return hasAny("POST_ETL");
	}

	public Double getAmount() {
		return amount;
	}
}
