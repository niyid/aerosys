package com.vasworks.airliner.service;

import java.awt.RenderingHints;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;
import javax.persistence.Query;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sun.media.jai.codec.SeekableStream;
import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.Booking.BookingStatus;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.model.ContextRateId;
import com.vasworks.airliner.model.ContextTax;
import com.vasworks.airliner.model.CountryState;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Customer.CustomerType;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Gender;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.Invoice.InvoiceStatus;
import com.vasworks.airliner.model.MemberActivation;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.RoutePrice;
import com.vasworks.airliner.model.RoutePriceId;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.airliner.model.SeatInterface.SeatStatus;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.model.Ticket.TicketStatus;
import com.vasworks.airliner.model.TscRemittance;
import com.vasworks.airliner.model.TscRemittance.RemittanceStatus;
import com.vasworks.airliner.model.UserTitle;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.AppUser.AuthenticationType;
import com.vasworks.security.model.UserRole;
import com.vasworks.security.model.UserStatus;
import com.vasworks.service.DaoServiceImpl;
import com.vasworks.service.EmailException;
import com.vasworks.service.impl.JavaMail;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@Transactional
public class TravelerServiceImpl extends DaoServiceImpl implements
		TravelerService { 
	public static final Log LOG = LogFactory.getLog(TravelerServiceImpl.class);	

	protected JavaMail emailService;
	
	public void setEmailService(JavaMail emailService) {
		this.emailService = emailService;
	}

	@Override
	public Currency findDefaultCurrency(Long userId) {
		LOG.info("findDefaultCurrency - " + userId);
		
		Currency c = (Currency) find("NGN", Currency.class);
		
		return c;
	}

	@Override
	@PreAuthorize("principal.id == userId")
	public void activateMember(String travelerId, String activationCode) {
		LOG.info("activateMember - " + travelerId + " " + activationCode);
		
		MemberActivation activation = (MemberActivation) find(travelerId, MemberActivation.class);
		
		if(activationCode != null && activationCode.equals(activation.getActivationCode())) {			
			Passenger passenger = (Passenger) find(travelerId, Passenger.class);
			
			AppUser user = passenger.getUser();
			user.setStatus(UserStatus.ENABLED);
			
			UserRole role = new UserRole();
			role.setApplication("airlinemgr");
			role.setRole("ROLE_USER");
			role.setUser(user);
			
			entityManager.merge(passenger);
			
			entityManager.merge(role);
			
			activation.setActivationCode(null);
			
			entityManager.merge(activation);
		}		
	}

	@Override
	@PreAuthorize("principal.id == userId")
	public Passenger addMember(Passenger data, Long userId) {
		LOG.info("addMember - " + userId);
		
		AppUser user = data.getUser();
		
		user.setMail(user.getMail().toLowerCase());
		
		data.setRegistrationDate(new Date());
		
		user.setUserName(user.getMail());
		user.setAuthenticationType(AuthenticationType.PASSWORD);
		user.setPassword(Sha512DigestUtils.shaHex(user.getPassword()));
		user.setStaffId(data.getPhoneNumber1());
		user.setDisplayName(user.getFirstName() + " " + user.getLastName());
		user.setStatus(UserStatus.DISABLED);
		
		insert(user, userId);
		
		UserRole role = new UserRole();
		role.setApplication("airlinemgr");
		role.setRole("ROLE_VIEWER");
		role.setUser(user);
		
		MemberActivation activation = new MemberActivation();
		activation.setMemberId(data.getId());
		activation.setPassenger(data);
		activation.setActivationCode(RandomStringUtils.randomAlphanumeric(16));
		
		insert(role, userId);

		insert(data, userId);
		
		insert(activation, userId);
		
		StringBuilder b = new StringBuilder("http://localhost:8080/airliner-web/activate.jspx?sid=");
		b.append(user.getMail());
		b.append("&acd=");
		b.append(activation.getActivationCode());
		
		String emailBody = "Please click on the following link for activation: " + b;
		
		try {
			emailService.sendEmail("neeyeed@gmail.com", user.getMail(), "Airliner Registration Activation", emailBody);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	@PreAuthorize("principal.id == userId")
	public Passenger updateMember(Passenger data, Long titleId, Long userId) {
		LOG.info("updateMember - " + titleId + " " + userId);
		
		UserTitle title = titleId != null ? (UserTitle) find(titleId, UserTitle.class) : null;
		
		AppUser oldUser = (AppUser) find(userId, AppUser.class);
		
		AppUser user = data.getUser();
		
		Passenger p = findPassengerByEmail(oldUser.getMail());
		
		if(p != null) {
			data.setId(p.getId());
		}
		data.setTitlePrefix(title);
		
		user.setMail(oldUser.getMail());
		user.setUserName(oldUser.getMail());
		user.setFirstName(data.getFirstName());
		user.setLastName(data.getLastName());
		user.setId(userId);
		user.setAuthenticationType(AuthenticationType.PASSWORD);
		user.setStaffId(data.getPhoneNumber1());
		user.setPassword(Sha512DigestUtils.shaHex(user.getPassword()));
		user.setDisplayName(user.getFirstName() + " " + user.getLastName());
		user.setRoles(oldUser.getRoles());
		
		update(user, userId);
		
		update(data, userId);
		
		return data;
	}

	@Override
	public Country fetchDefaultCountry() {
		LOG.info("fetchDefaultCountry()");
		
		Query q = entityManager.createQuery(FETCH_DEFAULT_COUNTRY);
		
		return (Country) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Flight> findFlights(Date flightDate, String departureAirportCode, String arrivalAirportCode, boolean connections, boolean thruFlights, Long clientId) {
		LOG.info("findFlights - " + flightDate + " " + departureAirportCode + " " + arrivalAirportCode + " " + connections + " " + thruFlights + " " + clientId);
		
		Query q = entityManager.createQuery(SEARCH_FLIGHTS + (connections ? "" : " and o.connections is empty") + (thruFlights ? "" : " and o.thruAirports is empty"));
		
		Calendar cCutoff = new GregorianCalendar();
		cCutoff.setTime(new Date());
		cCutoff.add(Calendar.HOUR, 1);
		
		Calendar c = new GregorianCalendar();
		c.setTime(flightDate);
		
		int flightDateYear = c.get(Calendar.YEAR);
		int flightDateMonth = c.get(Calendar.MONTH) + 1;
		int flightDateDay = c.get(Calendar.DATE);
		
		LOG.info("flightDateYear = " + flightDateYear);
		LOG.info("flightDateMonth = " + flightDateMonth);
		LOG.info("flightDateDay = " + flightDateDay);
		
		q.setParameter("flightDateYear", flightDateYear);
		q.setParameter("flightDateMonth", flightDateMonth);
		q.setParameter("flightDateDay", flightDateDay);
		
		q.setParameter("departureAirportCode", departureAirportCode);
		q.setParameter("arrivalAirportCode", arrivalAirportCode);
		
		q.setParameter("flightDepartureCutoff", cCutoff.getTime());
		
		List<Flight> result = q.getResultList();
		//Process results
		RoutePrice routePrice;
		for(Flight flight : result) {
			if(flight.isStatInit()) {
				flight.setSearchHitA(flight.getSearchHitA() + 1);
			} else {
				flight.setSearchHitB(flight.getSearchHitB() + 1);
			}
			update(flight, 1L);
			routePrice = (RoutePrice) find(new RoutePriceId(flight.getFlightRoute().getId(), clientId, flight.getCurrency().getCurrencyCode()), RoutePrice.class);
			flight.setRoutePrice(routePrice);
		}
				
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Flight> searchFlights(String airlineCode, Date flightDate, Integer flightNumber, String departureAirportCode, String arrivalAirportCode) {
		LOG.info("searchFlights - " + airlineCode + " " + flightDate + " " + flightNumber + " " + departureAirportCode + " " + arrivalAirportCode);
		
		if(flightDate == null) {
			flightDate = new Date();
		}
		
		StringBuilder qry = new StringBuilder("select o from Flight o where o.id.airlineCode = :airlineCode and year(o.id.flightDate) = :flightDateYear and month(o.id.flightDate) = :flightDateMonth and day(o.id.flightDate) = :flightDateDay");
		if(departureAirportCode != null) {
			qry.append(" and o.departureAirport.airportCode = :departureAirportCode");
		}
		if(arrivalAirportCode != null) {
			qry.append(" and o.arrivalAirport.airportCode = :arrivalAirportCode");
		}
		if(flightNumber != null) {
			qry.append(" and o.id.flightNumber = :flightNumber");
		}
		Query q = entityManager.createQuery(qry.toString());
		
		q.setParameter("airlineCode", airlineCode);
		
		Calendar c = new GregorianCalendar();
		c.setTime(flightDate);
		
		int flightDateYear = c.get(Calendar.YEAR);
		int flightDateMonth = c.get(Calendar.MONTH) + 1;
		int flightDateDay = c.get(Calendar.DATE);
		
		LOG.info("flightDateYear = " + flightDateYear);
		LOG.info("flightDateMonth = " + flightDateMonth);
		LOG.info("flightDateDay = " + flightDateDay);
		
		q.setParameter("flightDateYear", flightDateYear);
		q.setParameter("flightDateMonth", flightDateMonth);
		q.setParameter("flightDateDay", flightDateDay);
		
		if(departureAirportCode != null) {
			q.setParameter("departureAirportCode", departureAirportCode);
		}
		if(arrivalAirportCode != null) {
			q.setParameter("arrivalAirportCode", arrivalAirportCode);
		}
		if(flightNumber != null) {
			q.setParameter("flightNumber", flightNumber);
		}
		
		return q.getResultList();
	}

	@Override
	public Passenger findPassengerByEmail(String email) {
		LOG.info("findPassengerByEmail - " + email);
		
		Query q = entityManager.createQuery(FIND_PASSENGER_BY_EMAIL);
		
		q.setParameter("email", email);
		
		return (Passenger) q.getSingleResult();
	}

	@Override
	public Booking updateBooking(Long bookingId, FlightId flightId, List<Passenger> passengers, List<Long> titleIds, Long customerId, Long userId) {
		LOG.info("updateBooking - " + bookingId + " " + flightId + " " + passengers + " " + titleIds + " " + customerId + " " + userId);
		
		RandomString codeGenerator = new RandomString();
		Personnel personnel = (Personnel) find(userId, Personnel.class);
		Customer customer = null;
		if(customerId != null) {
			customer = (Customer) find(customerId, Customer.class); 
		}
		LOG.info("updateBooking - personnel - " + personnel);
		
		Booking oldBooking = (Booking) find(bookingId, Booking.class);

		int leg = oldBooking.getTickets().get(0).getLeg();
		
		
		//TODO Cancel previous booking.
		cancelBooking(bookingId, userId);
		
		double totalPrice = 0;
		Flight flight = (Flight) find(flightId, Flight.class);
		Booking booking = new Booking();
		booking.setBookingStatus(BookingStatus.NEW);
		booking.setBookingDate(new Date());
		booking.setReservationCode(codeGenerator.nextString());
		booking.setFlight(flight);
		booking.setOwner(customer);
		RoutePrice routePrice = (RoutePrice) find(new RoutePriceId(flight.getFlightRoute().getId(), customer.getId(), flight.getCurrency().getCurrencyCode()), RoutePrice.class);
		booking.setCurrencyRate(routePrice != null ? routePrice.getCurrency().getExchangeRate() : flight.getCurrency().getExchangeRate());
		insert(booking, userId);
		
		UserTitle title;
		Ticket ticket;
		int ticketCount = 0;
		flight.getRebates();
		flight.getTaxes();
		Passenger passenger;
		for(int i = 0; i < passengers.size(); i++) {
			passenger = passengers.get(i);
			if(titleIds.get(i) != null) {
				title = (UserTitle) find(titleIds.get(i), UserTitle.class);
				passengers.get(i).setTitlePrefix(title);
			}
			ticket = new Ticket();
			ticket.setCreateDate(new Date());
			ticket.setLeg(leg);
			ticket.setBooking(booking);
			ticket.setPassenger(passengers.get(i));
			ticket.setTicketCode(codeGenerator.nextString());
			
			ticket.setPrice(computeFlightFare(flight, booking, routePrice, passenger.getTicketClass()));
			totalPrice += ticket.getPrice();
			insert(passengers.get(i), userId);
			insert(ticket, userId);
			ticketCount++;
		}
		booking.setTotalPrice(totalPrice);
		update(booking, userId);
		
		if(flight.getSeatsLeft() == 0) {
			flight.setSeatsLeft(flight.getFlightSeats().size());
		}
		int count = flight.getSeatsLeft();
			
		count -= ticketCount;
		flight.setSeatsLeft(count);
		update(flight, userId);
		
		return booking;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Double computePriceTotal(List<FlightId> flightIds, List<Passenger> passengers, Long customerId) {
		LOG.info("computePriceTotal - " + flightIds + " " + passengers + " " + customerId);
		//TODO Check if seats still available
		for(FlightId flightId : flightIds) {
			Flight flight = (Flight) find(flightId, Flight.class);
			if(flight.getSeatsLeft() < passengers.size()) {
				return null;
			}
		}
		
		Personnel customerContact = (Personnel) find(customerId, Personnel.class);
		Organization organization = customerContact.getOrganization();
		Customer customer = (Customer) find(organization.getId(), Customer.class);
		double totalPrice = 0;
		for(FlightId flightId : flightIds) {
			Flight flight = (Flight) find(flightId, Flight.class);
			RoutePrice routePrice = (RoutePrice) find(new RoutePriceId(flight.getFlightRoute().getId(), customer.getId(), flight.getCurrency().getCurrencyCode()), RoutePrice.class);
			
			flight.getRebates();
			flight.getTaxes();
			Passenger passenger;
			for(int i = 0; i < passengers.size(); i++) {
				passenger = passengers.get(i);
				totalPrice += (dryComputeFlightFare(flight, routePrice, passenger.getTicketClass()));
			}
		}

		return totalPrice;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Long> createBooking(List<FlightId> flightIds, List<Passenger> passengers, List<Long> titleIds, Long customerId, Long userId) {
		LOG.info("createBooking - " + flightIds + " " + passengers + " " + titleIds + " " + customerId + " " + userId);
		//TODO Check if seats still available
		for(FlightId flightId : flightIds) {
			Flight flight = (Flight) find(flightId, Flight.class);
			if(flight.getSeatsLeft() < passengers.size()) {
				LOG.info("Full :- " + flight.getSeatsLeft() + " < " + passengers.size());
				return null;
			}
		}
		
		List<Long> bookingIds = new ArrayList<>();
		
		RandomString codeGenerator = new RandomString();
		Personnel personnel = (Personnel) find(userId, Personnel.class);
		Personnel customerContact = (Personnel) find(customerId, Personnel.class);
		Organization organization = customerContact.getOrganization();
		Customer customer = (Customer) find(organization.getId(), Customer.class);
		LOG.info("createBooking - personnel - " + personnel);
		int leg = 1;
		for(FlightId flightId : flightIds) {
			Booking booking = new Booking();
			double totalPrice = 0;
			Flight flight = (Flight) find(flightId, Flight.class);
			booking.setBookingStatus(BookingStatus.CONFIRMED);
			booking.setBookingDate(new Date());
			booking.setReservationCode(codeGenerator.nextString());
			booking.setFlight(flight);
			booking.setOwner(customer);
			booking.setAgent(personnel);
			RoutePrice routePrice = (RoutePrice) find(new RoutePriceId(flight.getFlightRoute().getId(), customer.getId(), flight.getCurrency().getCurrencyCode()), RoutePrice.class);
			booking.setCurrencyRate(routePrice != null ? routePrice.getCurrency().getExchangeRate() : flight.getCurrency().getExchangeRate());
			insert(booking, userId);
			
			UserTitle title;
			Ticket ticket;
			int ticketCount = 0;
			flight.getRebates();
			flight.getTaxes();
			Passenger passenger;
			for(int i = 0; i < passengers.size(); i++) {
				passenger = passengers.get(i);
				if(passenger.getMinorPassenger() == null) {
					passenger.setPassengerWeight(passenger.getGender().equals(Gender.MALE) ? 85 : 75);
				} else {
					passenger.setPassengerWeight(35);
				}

				if(titleIds.get(i) != null) {
					title = (UserTitle) find(titleIds.get(i), UserTitle.class);
					passenger.setTitlePrefix(title);
				}
				ticket = new Ticket();
				ticket.setCreateDate(new Date());
				ticket.setLeg(leg);
				ticket.setBooking(booking);
				ticket.setPassenger(passenger);
				ticket.setTicketCode(codeGenerator.nextString());
				ticket.setTicketStatus(TicketStatus.NEW);
				
				ticket.setPrice(computeFlightFare(flight, booking, routePrice, passenger.getTicketClass()));
				totalPrice += ticket.getPrice();
				insert(passenger, userId);
				insert(ticket, userId);
				ticketCount++;
			}
			booking.setTotalPrice(totalPrice);
			update(booking, userId);
			//TODO If customer is DEPOSIT, draw amount from balance
			
			if(flight.getSeatsLeft() == 0) {
				flight.setSeatsLeft(flight.getFlightSeats().size());
			}
			int count = flight.getSeatsLeft();
				
			count -= ticketCount;
			flight.setSeatsLeft(count);
			update(flight, userId);
			leg++;
			bookingIds.add(booking.getId());
		}
		
		entityManager.flush();

		return bookingIds;
	}
	
	private Double computeFlightFare(Flight flight, Booking booking, RoutePrice routePrice, SeatClass seatClass) {
		double fare = routePrice == null ? flight.getAirFareEconomy() : routePrice.getAirFareEconomy();
		double taxTotal = 0;
		double rebateTotal = 0;
		
		List<Tax> taxes = new ArrayList<>(flight.getTaxes());
		taxes.addAll(listAutoTaxes(flight.getAirplane().getAirline().getId()));
		//If less than 2 hours to flight, add lateness penalty
		double hoursLeft = (new Date().getTime() - flight.getDepartureTime().getTime()) / 3600.0;
		if(hoursLeft < 2) {
			Tax latenessTax = (Tax) find(new RateId(flight.getAirplane().getAirline().getId(), "LSC"), Tax.class);
			if(latenessTax != null) {
				taxes.add(latenessTax);
			}
		}
		
		List<Rebate> rebates = new ArrayList<>(flight.getRebates());
		rebates.addAll(listAutoRebates(flight.getAirplane().getAirline().getId()));
		
		booking.setTaxes(taxes);
		booking.setRebates(rebates);
		
		switch (seatClass) {
		case ECONOMY:
			fare = routePrice == null ? flight.getAirFareEconomy() : routePrice.getAirFareEconomy();
			break;
		case BUSINESS:
			fare = routePrice == null ? flight.getAirFareBizClass() : routePrice.getAirFareBizClass();
			break;
		case FIRST:
			fare = routePrice == null ? flight.getAirFareFirstClass() : routePrice.getAirFareFirstClass();
			break;
		}
		taxTotal = sumChargesOrRebates(taxes, fare);
		rebateTotal = sumChargesOrRebates(rebates, fare);
		fare = fare + taxTotal - rebateTotal;
		
		return fare;
	}
	
	private Double dryComputeFlightFare(Flight flight, RoutePrice routePrice, SeatClass seatClass) {
		double fare = routePrice == null ? flight.getAirFareEconomy() : routePrice.getAirFareEconomy();
		double taxTotal = 0;
		double rebateTotal = 0;
		
		List<Tax> taxes = new ArrayList<>(flight.getTaxes());
		taxes.addAll(listAutoTaxes(flight.getAirplane().getAirline().getId()));
		//If less than 2 hours to flight, add lateness penalty
		double hoursLeft = (new Date().getTime() - flight.getDepartureTime().getTime()) / 3600.0;
		if(hoursLeft < 2) {
			Tax latenessTax = (Tax) find(new RateId(flight.getAirplane().getAirline().getId(), "LSC"), Tax.class);
			if(latenessTax != null) {
				taxes.add(latenessTax);
			}
		}
		
		List<Rebate> rebates = new ArrayList<>(flight.getRebates());
		rebates.addAll(listAutoRebates(flight.getAirplane().getAirline().getId()));
		
		switch (seatClass) {
		case ECONOMY:
			fare = routePrice == null ? flight.getAirFareEconomy() : routePrice.getAirFareEconomy();
			break;
		case BUSINESS:
			fare = routePrice == null ? flight.getAirFareBizClass() : routePrice.getAirFareBizClass();
			break;
		case FIRST:
			fare = routePrice == null ? flight.getAirFareFirstClass() : routePrice.getAirFareFirstClass();
			break;
		}
		taxTotal = sumChargesOrRebates(taxes, fare);
		rebateTotal = sumChargesOrRebates(rebates, fare);
		fare = fare + taxTotal - rebateTotal;
		
		return fare;
	}
	
	@Override
	public Double sumChargesOrRebates(Collection<? extends RateInterface> rates, Double airFare) {
		LOG.info("sumChargesOrRebates - " + airFare);
		
		//TODO Do currency conversion for fixed rates
		Double total = 0.0;
		for(RateInterface r : rates) {
			total += (r.getFixedRate() != null ? r.getFixedRate() : airFare * r.getRatePercentage() / 100.0);
		}
		
		return total;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void flightCheckin(Booking booking, Long userId) {
		LOG.info("flightCheckin - " + booking.getId() + " " + userId);
		booking.setCheckinDate(new Date());
		// Allocate weights per passenger (male/female/minor)
		// Allocate carry-on luggage weight if any
		// Allocate checked-in luggage weights if any
		//TODO Generate check-in tags for passengers (with seats assigned)
		Passenger p;
		for(Ticket t : booking.getTickets()) {
			p = (Passenger) find(t.getPassenger().getId(), Passenger.class);
			p.setCarryOnWeight(t.getPassenger().getCarryOnWeight());
			p.setLuggageCount(t.getPassenger().getLuggageCount());
			p.setLuggageWeight(t.getPassenger().getLuggageWeight());
			p.setTotalWeight(t.getPassenger().getLuggageWeight() + t.getPassenger().getCarryOnWeight());
			//TODO If excess luggage, add Excess Luggage Charge
			double excessLuggageWeight = p.getLuggageWeight() > 23 ? (p.getLuggageWeight() - 23) : 0;
			if(excessLuggageWeight > 0) {
				//TODO If client is POSTPAID or DEPOSIT, find the client excess luggage rate.
				if(CustomerType.PRE_ETL_POSTPAID.equals(booking.getOwner().getCustomerType()) || CustomerType.PRE_ETL_DEPOSIT.equals(booking.getOwner().getCustomerType())) {
					ContextTax customerExcessLuggageTax = (ContextTax) find(new ContextRateId(booking.getFlight().getAirplane().getAirline().getId(), "ELC", booking.getOwner().getId()), ContextTax.class);
					if(customerExcessLuggageTax != null) {
						t.setPrice(t.getPrice() + customerExcessLuggageTax.getFixedRate() * excessLuggageWeight);
						booking.getContextTax().add(customerExcessLuggageTax);
						update(t, userId);
					} else {
						Tax excessLuggageTax = (Tax) find(new RateId(booking.getFlight().getAirplane().getAirline().getId(), "ELC"), Tax.class);
						if(excessLuggageTax != null) {
							double excessCost = excessLuggageTax.getFixedRate() * excessLuggageWeight;
							t.setPrice(t.getPrice() + excessCost);
							booking.getTaxes().add(excessLuggageTax);
							update(t, userId);
							if(CustomerType.PRE_ETL_DEPOSIT.equals(booking.getOwner().getCustomerType())) {
								booking.getOwner().setDepositAmount(booking.getOwner().getDepositAmount() - excessCost);
								update(booking.getOwner(), userId);
							}
						}
					}
				} else {
					Tax excessLuggageTax = (Tax) find(new RateId(booking.getFlight().getAirplane().getAirline().getId(), "ELC"), Tax.class);
					if(excessLuggageTax != null) {
						t.setPrice(t.getPrice() + excessLuggageTax.getFixedRate() * excessLuggageWeight);
						booking.getTaxes().add(excessLuggageTax);
						update(t, userId);
					}
				}
			}
			update(p, userId);
		}
		update(booking, userId);
		entityManager.flush();
	}
	
	@Override
	public void confirmBookings(List<Long> bookingIds, Long userId) {
		LOG.info("confirmBookings - " + bookingIds + " " + userId);
		
		for(Long bookingId : bookingIds) {
			confirmBooking(bookingId, userId);
		}
	}

	@Override
	public Booking confirmBooking(Long bookingId, Long userId) {
		LOG.info("confirmBooking - " + bookingId + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		AppUser confirmedBy = (AppUser) find(userId, AppUser.class);
		booking.setBookingStatus(BookingStatus.CONFIRMED);
		booking.setConfirmationDate(new Date());
		booking.setConfirmedBy(confirmedBy);
		
		List<Ticket> tickets = listTickets(bookingId);
		List<OutputStream> outStreams = new ArrayList<>();
				
		//Create tickets
		try {
			String ticketFileName;
			File reservationDoc;
			LOG.info("confirmBooking - CurrDir: " + new File(".").getAbsolutePath());
			URL templateUrl = this.getClass().getResource("/booking_confirmation_tpl.odt");
			LOG.info("confirmBooking - templateUrl: " + templateUrl);
			InputStream in = this.getClass().getResourceAsStream("/booking_confirmation_tpl.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
			Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);
			Currency remitCurrency = (Currency) find("NGN", Currency.class);
			//TODO Fix tax, rebate addition to flight in flight generate from schedule.
			Tax pscTax = (Tax) find(new RateId(booking.getFlight().getAirplane().getAirline().getId(), "TSC"), Tax.class);
			
			for(Ticket ticket : tickets) {
				
				if(pscTax != null) {
					TscRemittance tsc = new TscRemittance();
					tsc.setRemittanceStatus(RemittanceStatus.PENDING);
					tsc.setTicket(ticket);
					tsc.setTicketDate(new Date());
					tsc.setRemitAmount(pscTax.getRatePercentage() * booking.getFlight().getAirFareEconomy() / 100.0);
					tsc.setRemitCurrency(remitCurrency);
					insert(tsc, userId);
				}
				
				//TODO Generate PDF from ODT template for each ticket
				IContext context = report.createContext();
				
				context.put("ticket", ticket);
				context.put("flightNumber", ticket.getBooking().getFlight().getId().getAirlineCode() + ticket.getBooking().getFlight().getId().getFlightNumber());
				context.put("departureTime", FORMAT_TIME.format(ticket.getBooking().getFlight().getDepartureTime()));
				context.put("arrivalTime", FORMAT_TIME.format(ticket.getBooking().getFlight().getArrivalTime()));
				context.put("day", FORMAT_DAY.format(ticket.getBooking().getFlight().getDepartureTime()));
				context.put("confirmationDate", FORMAT_DAY2.format(ticket.getBooking().getConfirmationDate()));
				context.put("details", ticket.getBooking().isNonStop() ? "Non-Stop" : "Connection");
				ticketFileName = "booking_confirm_" + ticket.getTicketCode() + ".pdf";
				reservationDoc = new File("/home/odada/data/airlinemgr/tickets/" + ticketFileName);
				LOG.info("confirmBooking - generating: " + reservationDoc.getAbsolutePath());
				OutputStream out = new FileOutputStream(reservationDoc);
				outStreams.add(out);
				report.convert(context, options, out);
				ticket.setFilePath(reservationDoc.getAbsolutePath());
				update(ticket, userId);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XDocConverterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XDocReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return booking;
	}
	
	@Override
	public Booking cancelBooking(Long bookingId, Long userId) {
		LOG.info("cancelBooking - " + bookingId + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		// Only set to canceled if less than 2 hours to flight; otherwise no show
		Calendar cal = Calendar.getInstance();
		cal.setTime(booking.getFlight().getDepartureTime());
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY - 2));
		if(new Date().before(cal.getTime())) {
			booking.setBookingStatus(BookingStatus.CANCELED);
			booking.setCancellationDate(new Date());
		}
		
		//If already Checked-in; then release seats
		FlightSeat seat;
		for(Ticket t : booking.getTickets()) {
			seat = t.getFlightSeat();
			seat.setTicket(null);
			seat.setSeatStatus(SeatStatus.VACANT);
			update(seat, userId);
		}

		//Remember to increment flight seatsLeft by number of passengers on booking
		booking.getTickets().clear();
		booking.getFlight().setSeatsLeft(booking.getFlight().getSeatsLeft() + booking.getTickets().size());

		return booking;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchMyBookings(String reservationCode, Integer flightNumber, Long airlineId, Long organizationId, Date flightDate) {
		LOG.info("searchMyBookings - " + reservationCode + " " + flightNumber + " " + airlineId + " " + organizationId + " " + flightDate);
		
		if(flightDate == null) {
			flightDate = new Date();
		}
		
		Airline airline = (Airline) find(airlineId, Airline.class);
		Organization organization = (Organization) find(organizationId, Organization.class);
		
		StringBuilder b = new StringBuilder("select o.booking,o.passenger from Ticket o where o.booking.owner.id = :ownerId and o.booking.flight.id.airlineCode = :airlineCode");
		
		if(!StringUtils.isEmpty(reservationCode)) {
			b.append(" and o.booking.reservationCode = :reservationCode");
		}
		if(flightDate != null) {
			b.append(" and o.booking.flight.id.flightDate = :flightDate");
		}
		if(flightNumber != null) {
			b.append(" and o.booking.flight.id.flightNumber = :flightNumber");
		}
		b.append(" order by o.booking.flight.id.flightDate asc");
		
		Query q = entityManager.createQuery(b.toString());
		q.setParameter("ownerId", organization.getId());
		q.setParameter("airlineCode", airline.getAirlineCode());
		if(!StringUtils.isEmpty(reservationCode)) {
			q.setParameter("reservationCode", reservationCode);
		}
		if(flightDate != null) {
			q.setParameter("flightDate", flightDate);
		}
		if(flightNumber != null) {
			q.setParameter("flightNumber", flightNumber);
		}
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchBookings(String reservationCode, Integer flightNumber, Long airlineId, Date flightDate, String lastName) {
		LOG.info("searchBookings - " + reservationCode + " " + flightNumber + " " + airlineId + " " + flightDate + " " + lastName);
		
		if(flightDate == null) {
			flightDate = new Date();
		}		
		
		Airline airline = (Airline) find(airlineId, Airline.class);
		
		StringBuilder b = new StringBuilder("select o.booking,o.passenger from Ticket o where o.booking.flight.id.airlineCode = :airlineCode");
		if(!StringUtils.isEmpty(reservationCode)) {
			b.append(" and o.booking.reservationCode = :reservationCode");
		}
		if(flightDate != null) {
			b.append(" and o.booking.flight.id.flightDate = :flightDate");
		}
		if(flightNumber != null) {
			b.append(" and o.passenger.lastName like '%" + lastName + "%'");
		}
		if(lastName != null) {
			b.append(" and o.booking.flight.id.flightNumber = :flightNumber");
		}
		b.append(" order by o.booking.flight.id.flightDate asc");
		
		Query q = entityManager.createQuery(b.toString());
		q.setParameter("airlineCode", airline.getAirlineCode());
		if(!StringUtils.isEmpty(reservationCode)) {
			q.setParameter("reservationCode", reservationCode);
		}
		if(flightDate != null) {
			q.setParameter("flightDate", flightDate);
		}
		if(flightNumber != null) {
			q.setParameter("flightNumber", flightNumber);
		}
		
		List<Object[]> result = q.getResultList();
		
		LOG.info("searchBookings - " + result.size());
		
		return result;
	}
	
	@Override
	public List<Ticket> payForBooking(Long bookingId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Flight> addFlight(Long bookingId, FlightId flightId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Passenger> addPassenger(Long bookingId,	Passenger passenger, Long userId) {
		// TODO Auto-generated method stub
		// TODO If instanceof CoPassenger add to list of co-passengers else the primary passenger
		return null;
	}

	@Override
	public List<Flight> removeFlight(Long bookingId, FlightId flightId,
			Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Passenger> removePassenger(Long bookingId, Long passengerId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Ticket> listBookingTickets(Long bookingId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Passenger> listPassengers(Long bookingId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<City> listCities(String countryCode) {
		LOG.info("listCities - " + countryCode);
		
		Query q = entityManager.createQuery(LIST_CITIES_BY_COUNTRY);
		
		q.setParameter("countryCode", countryCode);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<CountryState> listCountryStates(String countryCode) {
		LOG.info("listCountryStates - " + countryCode);
		
		Query q = entityManager.createQuery(LIST_STATES_BY_COUNTRY);
		
		q.setParameter("countryCode", countryCode);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Airport> listAirports(String countryCode) {
		LOG.info("listAirports - " + countryCode);
		
		Query q = entityManager.createQuery(LIST_AIRPORTS_BY_COUNTRY);
		
		q.setParameter("countryCode", countryCode);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Flight> listFlights(Long airlineId) {
		LOG.info("listFlights - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_FLIGHTS_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Seat> listSeats(String airplaneReg) {
		LOG.info("listSeats - " + airplaneReg);
		
		Query q = entityManager.createQuery(LIST_AIRPLANE_SEATS);
		
		q.setParameter("regNumber", airplaneReg);
		
		return q.getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<FlightSeat> listFlightSeats(FlightId flightId) {
		LOG.info("listFlightSeats - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		return flight.getFlightSeats();
	}
	
	protected byte[] resizeImageUsingEncoding(byte[] pImageData, int pMaxWidth, String encodingFormat) throws IOException {
	    InputStream imageInputStream = new ByteArrayInputStream(pImageData);
	    // read in the original image from an input stream
	    SeekableStream seekableImageStream = SeekableStream.wrapInputStream(imageInputStream, true);
	    RenderedOp originalImage = JAI.create(JAI_STREAM_ACTION, seekableImageStream);
	    ((OpImage) originalImage.getRendering()).setTileCache(null);
	    int origImageWidth = originalImage.getWidth();
	    // now resize the image
	    double scale = 1.0;
	    if (pMaxWidth > 0 && origImageWidth > pMaxWidth) {
	        scale = (double) pMaxWidth / originalImage.getWidth();
	    }
	    ParameterBlock paramBlock = new ParameterBlock();
	    paramBlock.addSource(originalImage); // The source image
	    paramBlock.add(scale); // The xScale
	    paramBlock.add(scale); // The yScale
	    paramBlock.add(0.0); // The x translation
	    paramBlock.add(0.0); // The y translation
	 
	    RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
	        RenderingHints.VALUE_RENDER_QUALITY);
	 
	    RenderedOp resizedImage = JAI.create(JAI_SUBSAMPLE_AVERAGE_ACTION, paramBlock, qualityHints);
	 
	    // lastly, write the newly-resized image to an output stream, in a specific encoding
	    ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
	    JAI.create(JAI_ENCODE_ACTION, resizedImage, encoderOutputStream, encodingFormat, null);
	    // Export to Byte Array
	    byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
	    return resizedImageByteArray;
	}
	
	protected byte[] resizeImage(byte[] originalImageData, int maxImageWidth, String encodingFormat) {
		byte[] finalImageData = originalImageData;
		if(originalImageData.length > 10240) {
			try {
				finalImageData = resizeImageUsingEncoding(originalImageData, maxImageWidth, encodingFormat);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return finalImageData;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Booking> listBookings(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Customer> listCustomers(Long airlineId) {
		LOG.info("listCustomers - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_CUSTOMERS);
		
		q.setParameter("airlineId", airlineId);
		
		return (List<Customer>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Airplane> listAirplanes(Long airlineId) {
		LOG.info("listAirplanes - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_AIRPLANES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listRebates(Long airlineId) {
		LOG.info("listRebates - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_REBATES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listTaxes(Long airlineId) {
		LOG.info("listTaxes - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_TAXES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listNonAutoRebates(Long airlineId) {
		LOG.info("listNonAutoRebates - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_REBATES_NON_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listNonAutoTaxes(Long airlineId) {
		LOG.info("listNonAutoTaxes - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_TAXES_NON_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listAutoRebates(Long airlineId) {
		LOG.info("listAutoRebates - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_REBATES_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listAutoTaxes(Long airlineId) {
		LOG.info("listAutoTaxes - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_TAXES_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listTaxes(FlightId flightId) {
		LOG.info("listTaxes - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		List<Tax> taxes = flight.getTaxes();
		
		return taxes == null ? new ArrayList<Tax>() : taxes;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listRebates(FlightId flightId) {
		LOG.info("listRebates - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		List<Rebate> rebates = flight.getRebates();
		
		return rebates == null ? new ArrayList<Rebate>() : rebates;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listRebates(Long airlineId, Long clientId) {
		LOG.info("listRebates - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_REBATES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listTaxes(Long airlineId, Long clientId) {
		LOG.info("listTaxes - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_TAXES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listNonAutoRebates(Long airlineId, Long clientId) {
		LOG.info("listNonAutoRebates - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_REBATES_NON_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listNonAutoTaxes(Long airlineId, Long clientId) {
		LOG.info("listNonAutoTaxes - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_TAXES_NON_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Rebate> listAutoRebates(Long airlineId, Long clientId) {
		LOG.info("listAutoRebates - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_REBATES_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Tax> listAutoTaxes(Long airlineId, Long clientId) {
		LOG.info("listAutoTaxes - " + airlineId + " " + clientId);
		
		Query q = entityManager.createQuery(LIST_CONTEXT_TAXES_AUTO_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int nextSeat(Long bookingId, Long userId) {
		LOG.info("nextSeat - " + bookingId + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		
		List<Ticket> tickets = booking.getTickets();
		int ticketIndex = 0;
		for(Ticket t : tickets) {
			if(t.getFlightSeat() != null) {
				ticketIndex++;
			} else {
				break;
			}
		}
		LOG.info("nextSeat - ticketIndex - " + ticketIndex);
		
		return ticketIndex;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Booking selectSeat(Long bookingId, Long seatId, int ticketIndex, Long userId) {
		LOG.info("selectSeat - " + bookingId + " " + seatId + " " + ticketIndex + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		
		FlightSeat flightSeat = (FlightSeat) find(seatId, FlightSeat.class);
		flightSeat.setSeatStatus(SeatStatus.TAKEN);
		update(flightSeat, userId);
		
		Ticket ticket = booking.getTickets().get(ticketIndex);
		ticket.setFlightSeat(flightSeat);
		update(ticket, userId);
		
		return booking;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int skipSeat(Long bookingId, int ticketIndex, Long userId) {
		LOG.info("skipSeat - " + bookingId + " " + ticketIndex + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		
		ticketIndex = ticketIndex + 1;
		if(ticketIndex > booking.getTickets().size() - 1) {
			ticketIndex = 0;
		}
		
		return ticketIndex;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean checkInvoicesOverdue(Long clientId) {
		LOG.info("checkInvoicesOverdue - " + clientId);
		//TODO If customer is Postpaid, Check if no invoices have exceeded grace period.
		
		Query q = entityManager.createQuery(CHECK_UP_2_DATE);
		q.setParameter("paidStatus", InvoiceStatus.PAID);
		q.setParameter("clientId", clientId);
		Long count = (Long) q.getSingleResult();
		
		LOG.info("checkInvoicesOverdue - count=" + count);
		
		return count > 0;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean checkBalanceLow(Long clientId) {
		LOG.info("checkBalanceLow - " + clientId);
		
		Customer customer = (Customer) find(clientId, Customer.class);
		
		return customer.getDepositAmount() < customer.getDepositLowerLimit();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Invoice> listOverdueInvoices(Long clientId) {
		LOG.info("listOverdueInvoices - " + clientId);
		//TODO If customer is Postpaid, Check if no invoices have exceeded grace period.
		
		Query q = entityManager.createQuery(LIST_OVERDUE_INVOICES);
		q.setParameter("paidStatus", InvoiceStatus.PAID);
		q.setParameter("clientId", clientId);
		
		return q.getResultList();
	}

	@Override
	public String payInvoice(Long invoiceId, Long userId) {
		LOG.info("payInvoice - " + invoiceId + " " + userId);
		//TODO Get invoice amount and send to payment service
		return null;
	}

	@Override
	public String payBooking(Long bookingId, Long userId) {
		LOG.info("payBooking - " + bookingId + " " + userId);
		//TODO Get booking amount and send to payment service
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> listTickets(Long bookingId) {
		LOG.info("listTickets - " + bookingId);
		
		Query q = entityManager.createQuery(LIST_BOOKING_TICKETS);
		q.setParameter("bookingId", bookingId);
		
		return q.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Booking deselectSeat(Long bookingId, Long seatId, int ticketIndex, Long userId) {
		LOG.info("deselectSeat - " + bookingId + " " + seatId + " " + ticketIndex + " " + userId);
		Booking booking = (Booking) find(bookingId, Booking.class);
		
		FlightSeat flightSeat = (FlightSeat) find(seatId, FlightSeat.class);
		flightSeat.setSeatStatus(SeatStatus.VACANT);
		update(flightSeat, userId);
		
		Ticket ticket = booking.getTickets().get(ticketIndex);
		ticket.setFlightSeat(null);
		update(ticket, userId);
		
		return booking;
	}
}