package com.vasworks.airliner.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.model.CountryState;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.entity.Country;
import com.vasworks.entity.Organization;
import com.vasworks.service.DaoService;

public interface TravelerService extends DaoService {
	
    static final String JAI_STREAM_ACTION = "stream";
 
    static final String JAI_SUBSAMPLE_AVERAGE_ACTION = "SubsampleAverage";
    
    static final String JAI_ENCODE_ACTION = "encode";
    
	static final String FIND_PASSENGER_BY_EMAIL = "select o from Passenger o where o.user.mail = :email";
	
	static final String LIST_STATES_BY_COUNTRY = "select o from CountryState o where o.country.countryCode = :countryCode";
	
	static final String LIST_CITIES_BY_COUNTRY = "select o from City o where o.countryState.country.countryCode = :countryCode";
	
	static final String LIST_AIRPORTS_BY_COUNTRY = "select o from Airport o where o.city.countryState.country.countryCode = :countryCode";
	
	static final String LIST_FLIGHTS_BY_AIRLINE = "select o from Flight o where o.airplane.airline.id = :airlineId order by o.id.flightDate desc, o.id.flightNumber asc";
	
	static final String LIST_FLIGHT_SCHEDULES_BY_AIRLINE = "select o from FlightSchedule o where o.airplane.airline.id = :airlineId order by o.id asc";
	
	static final String LIST_BOOKING_TICKETS = "select o from Ticket o where o.booking.id = :bookingId";
	
	static final String LIST_LGAS_BY_STATE = "select o from LocalGovArea o where o.countryState.id = :countryStateId";
	
	static final String LIST_AIRPLANE_SEATS = "select o from Seat o where o.airplane.regNumber = :regNumber order by o.seatIndex";
	
	static final String FETCH_CURRENCY_BY_COUNTRY = "select o from Currency o where o.currencyName like :countryName";
	
	static final String FETCH_DEFAULT_COUNTRY = "select o from Country o where o.countryName = 'Nigeria'";
	
	static final String SEARCH_FLIGHTS = "select o from Flight o where o.departureTime > :flightDepartureCutoff and year(o.id.flightDate) = :flightDateYear and month(o.id.flightDate) = :flightDateMonth and day(o.id.flightDate) = :flightDateDay and o.departureAirport.airportCode = :departureAirportCode and o.arrivalAirport.airportCode = :arrivalAirportCode and o.flightStatus is null";
	
	static final String LIST_CUSTOMERS = "select o from Customer o where o.airline.id = :airlineId";

	static final String LIST_AIRPLANES_BY_AIRLINE = "select o from Airplane o where o.airline.id = :airlineId";
	
	static final String LIST_REBATES_BY_AIRLINE = "select o from Rebate o where o.airline.id = :airlineId";
	
	static final String LIST_CONTEXT_REBATES_BY_AIRLINE = "select o from ContextRebate o where o.id.airlineId = :airlineId and o.id.clientId = :clientId";
	
	static final String LIST_TAXES_BY_AIRLINE = "select o from Tax o where o.airline.id = :airlineId";
	
	static final String LIST_CONTEXT_TAXES_BY_AIRLINE = "select o from ContextTax o where o.id.airlineId = :airlineId and o.id.clientId = :clientId";
	
	static final String LIST_ROUTES_BY_AIRLINE = "select o from Route o where";
	
	static final String LIST_REBATES_NON_AUTO_BY_AIRLINE = "select o from Rebate o where o.airline.id = :airlineId and o.autoAdded = false";
	
	static final String LIST_CONTEXT_REBATES_NON_AUTO_BY_AIRLINE = "select o from ContextRebate o where o.id.airlineId = :airlineId and o.id.clientId = :clientId and o.autoAdded = false";
	
	static final String LIST_TAXES_NON_AUTO_BY_AIRLINE = "select o from Tax o where o.airline.id = :airlineId and o.autoAdded = false";
	
	static final String LIST_CONTEXT_TAXES_NON_AUTO_BY_AIRLINE = "select o from ContextTax o where o.id.airlineId = :airlineId and o.id.clientId = :clientId and o.autoAdded = false";
	
	static final String LIST_REBATES_AUTO_BY_AIRLINE = "select o from Rebate o where o.airline.id = :airlineId and o.autoAdded = true";
	
	static final String LIST_CONTEXT_REBATES_AUTO_BY_AIRLINE = "select o from ContextRebate o where o.id.airlineId = :airlineId and o.id.clientId = :clientId and o.autoAdded = true";
	
	static final String LIST_TAXES_AUTO_BY_AIRLINE = "select o from Tax o where o.airline.id = :airlineId and o.autoAdded = true";
	
	static final String LIST_CONTEXT_TAXES_AUTO_BY_AIRLINE = "select o from ContextTax o where o.id.airlineId = :airlineId and o.id.clientId = :clientId and o.autoAdded = true";
	
	static final String CHECK_UP_2_DATE = "select count(o.id) from Invoice o where o.customer.id = :clientId and o.invoiceStatus = :paidStatus and o.overdueDate < current_date";
	
	static final String LIST_OVERDUE_INVOICES = "select o from Invoice o where o.customer.id = :clientId and o.invoiceStatus = :paidStatus and o.overdueDate < current_date";
	
	static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("hh:mm a");
	
	static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("E, dd MMM yyyy");
	
	static final SimpleDateFormat FORMAT_DAY2 = new SimpleDateFormat("dd MMMM yyyy");

	/**
	 * 
	 * @param travelerId
	 * @param activationCode
	 */
	@PreAuthorize("principal.id == userId")
	void activateMember(String travelerId, String activationCode);
	
	/**
	 * 
	 * @param data
	 * @param latitude
	 * @param longitude
	 * @param locationName
	 * @param userId
	 * @return
	 */
	@PreAuthorize("principal.id == userId")
	Passenger addMember(Passenger data, Long userId);
	
	/**
	 * 
	 * @param data
	 * @param titleId
	 * @param latitude
	 * @param longitude
	 * @param locationName
	 * @param userId
	 * @return
	 */
	@PreAuthorize("principal.id == userId")
	Passenger updateMember(Passenger data, Long titleId, Long userId);
	
	/**
	 * 
	 * @return
	 */
	Country fetchDefaultCountry();
	
	/**
	 * 
	 * @param flightDate
	 * @param departureAirportCode
	 * @param arrivalAirportCode
	 * @param connections
	 * @param thruFlights
	 * @param clientId 
	 * @return
	 */
	List<Flight> findFlights(Date flightDate, String departureAirportCode, String arrivalAirportCode, boolean connections, boolean thruFlights, Long clientId);
	
	/**
	 * 
	 * @param bookingId
	 * @param userId
	 * @return
	 */
	Booking cancelBooking(Long bookingId, Long userId);
	
	/**
	 * 
	 * @param bookingId
	 * @param userId
	 * @return
	 */
	List<Ticket> payForBooking(Long bookingId, Long userId);
	
	/**
	 * 
	 * @param bookingId
	 * @param flightId
	 * @param userId
	 * @return
	 */
	List<Flight> addFlight(Long bookingId, FlightId flightId, Long userId);
	
	/**
	 * 
	 * @param bookingId
	 * @param flightId
	 * @param userId
	 * @return
	 */
	List<Flight> removeFlight(Long bookingId, FlightId flightId, Long userId);
	
	/**
	 * 
	 * @param bookingId
	 * @param passenger
	 * @param userId
	 * @return
	 */
	List<Passenger> addPassenger(Long bookingId, Passenger passenger, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param passengerId
	 * @param userId
	 * @return
	 */
	List<Passenger> removePassenger(Long bookingId, Long passengerId, Long userId);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	Passenger findPassengerByEmail(String email);
	
	/**
	 * 
	 * @param bookingId
	 * @return
	 */
	List<Ticket> listBookingTickets(Long bookingId);
	
	/**
	 * 
	 * @param bookingId
	 * @return
	 */
	List<Passenger> listPassengers(Long bookingId);
	
	/**
	 * 
	 * @param countryCode
	 * @return
	 */
	List<CountryState> listCountryStates(String countryCode);

	/**
	 * 
	 * @param countryCode
	 * @return
	 */
	List<City> listCities(String countryCode);
	
	/**
	 * 
	 * @param countryCode
	 * @return
	 */
	List<Airport> listAirports(String countryCode);
	
	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	List<Flight> listFlights(Long airlineId);
	
	/**
	 * 
	 * @param airplaneReg
	 * @return
	 */
	List<Seat> listSeats(String airplaneReg);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<FlightSeat> listFlightSeats(FlightId flightId);

	/**
	 * 
	 * @param flightIds
	 * @param passengers
	 * @param titleIds
	 * @param customerId
	 * @param userId
	 * @return
	 */
	List<Long> createBooking(List<FlightId> flightIds, List<Passenger> passengers, List<Long> titleIds, Long customerId, Long userId);

	/**
	 * 
	 * @param booking
	 * @param userId
	 * @return
	 */
	void flightCheckin(Booking booking, Long userId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Booking> listBookings(Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param id
	 * @return
	 */
	Booking confirmBooking(Long bookingId, Long userId);
	
	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	List<Customer> listCustomers(Long airlineId);
	
	/**
	 * 
	 * @param rates
	 * @param airFare
	 * @return
	 */
	Double sumChargesOrRebates(Collection<? extends RateInterface> rates, Double airFare);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	List<Airplane> listAirplanes(Long airlineId);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	Collection<? extends RateInterface> listTaxes(FlightId flightId);


	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listRebates(Long airlineId, Long clientId);

	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listTaxes(Long airlineId, Long clientId);

	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listNonAutoRebates(Long airlineId, Long clientId);

	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listNonAutoTaxes(Long airlineId, Long clientId);

	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listAutoRebates(Long airlineId, Long clientId);

	/**
	 * 
	 * @param airlineId
	 * @param clientId
	 * @return
	 */
	Collection<? extends RateInterface> listAutoTaxes(Long airlineId, Long clientId);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	Collection<? extends RateInterface> listRebates(FlightId flightId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listRebates(Long airlineId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listTaxes(Long airlineId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listNonAutoRebates(Long airlineId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listNonAutoTaxes(Long airlineId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listAutoRebates(Long airlineId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	Collection<? extends RateInterface> listAutoTaxes(Long airlineId);

	/**
	 * 
	 * @param reservationCode
	 * @param flightNumber
	 * @param airlineId
	 * @param flightDate
	 * @param lastName 
	 * @return
	 */
	List<Object[]> searchBookings(String reservationCode, Integer flightNumber, Long airlineId, Date flightDate, String lastName);

	/**
	 * 
	 * @param reservationCode
	 * @param flightNumber
	 * @param airlineId
	 * @param organizationId
	 * @param flightDate
	 * @return
	 */
	List<Object[]> searchMyBookings(String reservationCode, Integer flightNumber, Long airlineId, Long organizationId, Date flightDate);

	/**
	 * 
	 * @param bookingId
	 * @param seatId
	 * @param ticketIndex
	 * @param userId
	 * @return
	 */
	Booking selectSeat(Long bookingId, Long seatId, int ticketIndex, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param userId
	 * @return
	 */
	int nextSeat(Long bookingId, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param currentIndex
	 * @param userId
	 * @return
	 */
	int skipSeat(Long bookingId, int currentIndex, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param flightId
	 * @param passengers
	 * @param titleIds
	 * @param customerId
	 * @param userId
	 * @return
	 */
	Booking updateBooking(Long bookingId, FlightId flightId, List<Passenger> passengers, List<Long> titleIds, Long customerId, Long userId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	boolean checkInvoicesOverdue(Long userId);
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	boolean checkBalanceLow(Long clientId);


	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Invoice> listOverdueInvoices(Long clientId);

	/**
	 * 
	 * @param bookingId
	 * @param userId
	 * @return
	 */
	String payInvoice(Long bookingId, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param userId
	 * @return
	 */
	String payBooking(Long bookingId, Long userId);

	/**
	 * 
	 * @param flightIds
	 * @param passengers
	 * @param customerId
	 * @return
	 */
	Double computePriceTotal(List<FlightId> flightIds, List<Passenger> passengers, Long customerId);
	
	/**
	 * 
	 * @param bookingId
	 * @return
	 */
	List<Ticket> listTickets(Long bookingId);

	/**
	 * 
	 * @param airlineCode
	 * @param flightDate
	 * @param flightNumber
	 * @param departureAirportCode
	 * @param arrivalAirportCode
	 * @return
	 */
	List<Flight> searchFlights(String airlineCode, Date flightDate, Integer flightNumber, String departureAirportCode, String arrivalAirportCode);

	/**
	 * 
	 * @param bookingIds
	 * @param userId
	 */
	void confirmBookings(List<Long> bookingIds, Long userId);

	/**
	 * 
	 * @param bookingId
	 * @param seatId
	 * @param ticketIndex
	 * @param userId
	 * @return
	 */
	Booking deselectSeat(Long bookingId, Long seatId, int ticketIndex, Long userId);

}