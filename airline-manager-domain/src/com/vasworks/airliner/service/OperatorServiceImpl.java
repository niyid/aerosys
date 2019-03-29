package com.vasworks.airliner.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vasworks.airliner.model.Activity;
import com.vasworks.airliner.model.ActivityReport;
import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.AirplaneMake;
import com.vasworks.airliner.model.AirplaneModel;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.Booking.BookingStatus;
import com.vasworks.airliner.model.CashDeposit;
import com.vasworks.airliner.model.CashDeposit.DepositStatus;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.model.ContextRateId;
import com.vasworks.airliner.model.ContextTax;
import com.vasworks.airliner.model.CountryState;
import com.vasworks.airliner.model.CrewActivity;
import com.vasworks.airliner.model.CrewMember;
import com.vasworks.airliner.model.CrewMember.CrewType;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Customer.CustomerType;
import com.vasworks.airliner.model.DocumentFile;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.Flight.FlightStatus;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSchedule;
import com.vasworks.airliner.model.FlightScheduleDay;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Gender;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.Invoice.InvoiceStatus;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.Route;
import com.vasworks.airliner.model.RoutePrice;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.SeatId;
import com.vasworks.airliner.model.SeatInterface;
import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.model.Ticket.TicketStatus;
import com.vasworks.airliner.model.TicketData;
import com.vasworks.airliner.model.Training;
import com.vasworks.airliner.model.TrainingDoctor;
import com.vasworks.airliner.model.TrainingHandler;
import com.vasworks.airliner.model.TrainingPilot;
import com.vasworks.airliner.model.TrainingSupervisor;
import com.vasworks.airliner.model.TrainingType;
import com.vasworks.airliner.model.TscRemittance;
import com.vasworks.airliner.model.TscRemittance.RemittanceStatus;
import com.vasworks.airliner.model.YieldRuleA;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserRole;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@Transactional
public class OperatorServiceImpl extends TravelerServiceImpl implements	OperatorService {
	private static HashMap<ActivityPeriod, Integer> MAX_HOURS_PER_PERIOD = new HashMap<ActivityPeriod, Integer>(4);
	
	private static HashMap<String, String> IMAGE_ENCODING_TYPE_MAP = new HashMap<String, String>();
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final SimpleDateFormat DATE_TIME_FORMAT2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	static {
		MAX_HOURS_PER_PERIOD.put(ActivityPeriod.DAY_1, 8);
		MAX_HOURS_PER_PERIOD.put(ActivityPeriod.DAY_7, 60);
		MAX_HOURS_PER_PERIOD.put(ActivityPeriod.DAY_30, 300);
		MAX_HOURS_PER_PERIOD.put(ActivityPeriod.DAY_90, 600);
		
		IMAGE_ENCODING_TYPE_MAP.put("JPEG", "image/jpeg:jpg");
		IMAGE_ENCODING_TYPE_MAP.put("GIF", "image/gif:gif");
		IMAGE_ENCODING_TYPE_MAP.put("TIFF", "image/tiff:tif");
		IMAGE_ENCODING_TYPE_MAP.put("PNG", "image/png:png");
		IMAGE_ENCODING_TYPE_MAP.put("BMP", "image/bmp:bmp");
	}
	
	@Override
	public List<Flight> generateFlights(Long scheduleId, Long userId) throws Exception {
		LOG.info("generateFlights - " + scheduleId + " " + userId);
		
		List<Flight> flights = new ArrayList<>();
		
		TreeSet<Date> scheduleDays = null;

		try {
			FlightSchedule schedule = (FlightSchedule) find(scheduleId, FlightSchedule.class);
			
			scheduleDays = new TreeSet<Date>();
			
			Calendar cal = Calendar.getInstance();
			for(int i = 0; i < schedule.getNumOfWeeks(); i++) {
				for(FlightScheduleDay flightDay : schedule.getFlightScheduleDays()) {
					cal.setTime(schedule.getStartDate());
					cal.add(Calendar.WEEK_OF_YEAR, i);
					cal.set(Calendar.DAY_OF_WEEK, flightDay.getDayOfWeek());
					scheduleDays.add(cal.getTime());
				}
			}
			
			LOG.info("generateFlights - scheduleDays - " + scheduleDays);
			
			for(Date d : scheduleDays) {
				Flight flight = new Flight();
				flight.setAirFareBizClass(schedule.getAirFareBizClass());
				flight.setAirFareEconomy(schedule.getAirFareEconomy());
				flight.setAirFareFirstClass(schedule.getAirFareFirstClass());
				flight.setAirplane(schedule.getAirplane());
				flight.setArrivalAirport(schedule.getArrivalAirport());
				try {
					flight.setArrivalTime(DATE_TIME_FORMAT.parse(DATE_FORMAT.format(d) + " " + TIME_FORMAT.format(schedule.getArrivalTime())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				flight.setCurrency(schedule.getCurrency());
				flight.setDepartureAirport(schedule.getDepartureAirport());
				try {
					flight.setDepartureTime(DATE_TIME_FORMAT.parse(DATE_FORMAT.format(d) + " " + TIME_FORMAT.format(schedule.getDepartureTime())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				List<Tax> taxes = new ArrayList<>(schedule.getTaxes());
				taxes.addAll(listAutoTaxes(schedule.getAirplane().getAirline().getId()));
				List<Rebate> rebates = new ArrayList<>(schedule.getRebates());
				rebates.addAll(listAutoRebates(schedule.getAirplane().getAirline().getId()));
				flight.setFlightRoute(schedule.getFlightRoute());
				flight.setOpenSeating(schedule.isOpenSeating());
				flight.setTaxes(taxes);
				flight.setRebates(rebates);
				
				flight.setId(new FlightId(Integer.valueOf(schedule.getFlightNumber()), schedule.getAirplane().getAirline().getAirlineCode(), d));
				insert(flight, userId);
				flights.add(flight);
				if(flight.getFlightSeats() == null || flight.getFlightSeats().isEmpty()) {
					//Add flight seats
					cloneFlightSeats(flight, userId);
					flight.setSeatsLeft(flight.getAirplane().getSeats().size());
					update(flight, userId);
				}
			}
			
			GregorianCalendar cal1 = new GregorianCalendar();
			cal1.setTime(scheduleDays.last());
			cal1.add(Calendar.DATE, 7);                 
			cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			schedule.setStartDate(cal1.getTime());
			update(schedule, userId);
		} catch(Exception e) {
			throw new ScheduleException("Possible date overlap: Cannot create flights from dates " + scheduleDays + ".");
		}
		
		return flights;
	}

	@Override
	public FlightSchedule saveFlightSchedule(FlightSchedule flightSchedule, Long scheduleId, String departureAirportCode, String arrivalAirportCode, String aircraftReg, String currencyCode, int[] selWeekDays, RateId[] selTaxIds, RateId[] selRebateIds, Long userId) {
		LOG.info("saveFlightSchedule - " + scheduleId + " " + departureAirportCode + " " + arrivalAirportCode + " " + aircraftReg + " " + currencyCode + " " + selWeekDays + " " + selTaxIds + " " + selRebateIds + " " + userId);
		
		Airport departureAirport = (Airport) find(departureAirportCode, Airport.class);
		
		Airport arrivalAirport = (Airport) find(arrivalAirportCode, Airport.class);
		
		Airplane airplane = (Airplane) find(aircraftReg, Airplane.class);
		
		Route route = findRoute(departureAirport, arrivalAirport);
		
		Currency currency = (Currency) find(currencyCode, Currency.class);
		
		if(route == null) {
			route = new Route();
			route.setDescription(departureAirport.getAirportCode() + "/" + arrivalAirport.getAirportCode());
			String[] airportCodes = new String[2];
			airportCodes[0] = departureAirportCode;
			airportCodes[1] = arrivalAirportCode;
			saveRoute(route, null, airportCodes, null, null, userId);
		}
		
		flightSchedule.setAirplane(airplane);
		flightSchedule.setCurrency(currency);
		flightSchedule.setDepartureAirport(departureAirport);
		flightSchedule.setArrivalAirport(arrivalAirport);
		flightSchedule.setFlightRoute(route);
		
		if(scheduleId != null) {
			flightSchedule.setId(scheduleId);
			update(flightSchedule, userId);
		} else {
			insert(flightSchedule, userId);
		}
		
		if(flightSchedule.getTaxes() == null) {
			flightSchedule.setTaxes(new ArrayList<Tax>());
		} else {
			flightSchedule.getTaxes().clear();
		}
		if(selTaxIds != null) {			
			Tax t = null;
			for(RateId id : selTaxIds) {
				t = (Tax) find(id, Tax.class);
				flightSchedule.getTaxes().add(t);
			}
			update(flightSchedule, userId);
		}
		
		if(flightSchedule.getRebates() == null) {
			flightSchedule.setRebates(new ArrayList<Rebate>());
		} else {
			flightSchedule.getRebates().clear();
		}
		if(selRebateIds != null) {			
			Rebate r = null;
			for(RateId id : selRebateIds) {
				r = (Rebate) find(id, Rebate.class);
				flightSchedule.getRebates().add(r);
			}
			update(flightSchedule, userId);
		}
		
		if(flightSchedule.getFlightScheduleDays() != null) {
			for(FlightScheduleDay d : flightSchedule.getFlightScheduleDays()) {
				remove(d);
			}
		}
		if(selWeekDays != null) {			
			FlightScheduleDay f = null;
			for(int dayOfWeek : selWeekDays) {
				f = new FlightScheduleDay();
				f.setDayOfWeek(dayOfWeek);
				f.setFlightSchedule(flightSchedule);
				insert(f, userId);
			}
		}
		
		entityManager.flush();
		
		return flightSchedule;
	}
	
	@Override
	public List<Flight> saveFlight(Flight flight, Integer flightNumber, Long airlineId, Date flightDate, String departureAirportCode, String arrivalAirportCode, String aircraftReg, String currencyCode, RateId[] selTaxIds, RateId[] selRebateIds, String[] selThruFlightIds, Long userId) {
		LOG.info("saveFlight - " + flightNumber + " " + airlineId + " " + flightDate + " " + departureAirportCode + " " + arrivalAirportCode + " " + aircraftReg + " " + currencyCode + " " + selTaxIds + " " + selRebateIds + " " + selThruFlightIds + " " + userId);
		
		Airline airline = (Airline) find(airlineId, Airline.class);
		
		FlightId flightId = flightNumber != null && airlineId != null && flightDate != null ? new FlightId(flightNumber, airline.getAirlineCode(), flightDate) : null;
		
		Airport departureAirport = (Airport) find(departureAirportCode, Airport.class);
		
		Airport arrivalAirport = (Airport) find(arrivalAirportCode, Airport.class);
		
		Airplane airplane = (Airplane) find(aircraftReg, Airplane.class);
		
		Route route = findRoute(departureAirport, arrivalAirport);
		
		Currency currency = (Currency) find(currencyCode, Currency.class);
		
		if(route == null) {
			route = new Route();
			route.setDescription(departureAirport.getAirportCode() + "/" + arrivalAirport.getAirportCode());
			String[] airportCodes = new String[2];
			airportCodes[0] = departureAirportCode;
			airportCodes[1] = arrivalAirportCode;
			saveRoute(route, null, airportCodes, null, null, userId);
		}
		
		flight.setId(flightId);
		flight.setDepartureAirport(departureAirport);
		flight.setArrivalAirport(arrivalAirport);
		flight.setAirplane(airplane);
		flight.setFlightRoute(route);
		flight.setCurrency(currency);
		
		if(flightId != null) {
			update(flight, userId);
		} else {
			insert(flight, userId);
		}
		
		if(flight.getTaxes() == null) {
			flight.setTaxes(new ArrayList<Tax>());
		} else {
			flight.getTaxes().clear();
		}
		if(selTaxIds != null) {			
			Tax t = null;
			for(RateId id : selTaxIds) {
				t = (Tax) find(id, Tax.class);
				flight.getTaxes().add(t);
			}
			update(flight, userId);
		}
		
		if(flight.getRebates() == null) {
			flight.setRebates(new ArrayList<Rebate>());
		} else {
			flight.getRebates().clear();
		}
		if(selRebateIds != null) {			
			Rebate r = null;
			for(RateId id : selRebateIds) {
				r = (Rebate) find(id, Rebate.class);
				flight.getRebates().add(r);
			}
			update(flight, userId);
		}
		
		if(flight.getThruAirports() == null) {
			flight.setThruAirports(new ArrayList<Airport>());
		} else {
			flight.getThruAirports().clear();
		}
		if(selThruFlightIds != null) {			
			Airport a = null;
			for(String code : selThruFlightIds) {
				a = (Airport) find(code, Airport.class);
				if(!departureAirport.equals(a) && !arrivalAirport.equals(a)) {
					flight.getThruAirports().add(a);
				}
			}
			update(flight, userId);
		}
		
		if(flight.getFlightSeats() == null || flight.getFlightSeats().isEmpty()) {
			//Add flight seats
			cloneFlightSeats(flight, userId);
			flight.setSeatsLeft(flight.getAirplane().getSeats().size());
		}
		
		return listFlights(airlineId);
	}
	
	@Override
	public List<FlightSeat> cloneFlightSeats(Flight flight, Long userId) {
		LOG.info("cloneFlightSeats - " + flight + " " + userId);
		
		boolean hasFirst = false;
		boolean hasBusiness = false;
		boolean hasEconomy = false;
		if(flight.getFlightSeats() == null || flight.getFlightSeats().isEmpty()) {
			List<Seat> seats = flight.getAirplane().getSeats();
			for(Seat s : seats) {
				s.setFlight(flight);
				update(s, userId);
				FlightSeat flightSeat = FlightSeat.cloneSeat(s);
				if(SeatClass.FIRST.equals(flightSeat.getSeatClass())) {
					hasFirst = true;
				} else
				if(SeatClass.BUSINESS.equals(flightSeat.getSeatClass())) {
					hasBusiness = true;	
				} else
				if(SeatClass.ECONOMY.equals(flightSeat.getSeatClass())) {
					hasEconomy = true;	
				}

				insert(flightSeat, userId);
			}
		}
		flight.setHasFirst(hasFirst);
		flight.setHasBusiness(hasBusiness);
		flight.setHasEconomy(hasEconomy);
		update(flight, userId);
		
		return flight.getFlightSeats();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Route> saveRoute(Route route, Long routeId, String[] selAirportCodes, RateId[] selTaxIds, RateId[] selRebateIds, Long userId) {
		LOG.info("saveRoute - " + routeId + " " + selAirportCodes + " " + userId);
				
		if(routeId != null) {
			route.setId(routeId);
			update(route, userId);
		} else {
			insert(route, userId);
		}
		
		if(route.getTaxes() == null) {
			route.setTaxes(new ArrayList<Tax>());
		} else {
			route.getTaxes().clear();
		}
		if(selTaxIds != null) {			
			Tax t = null;
			for(RateId id : selTaxIds) {
				t = (Tax) find(id, Tax.class);
				route.getTaxes().add(t);
			}
			update(route, userId);
		}
		
		if(route.getRebates() == null) {
			route.setRebates(new ArrayList<Rebate>());
		} else {
			route.getRebates().clear();
		}
		if(selRebateIds != null) {			
			Rebate r = null;
			for(RateId id : selRebateIds) {
				r = (Rebate) find(id, Rebate.class);
				route.getRebates().add(r);
			}
			update(route, userId);
		}
		
		if(selAirportCodes != null && selAirportCodes.length == 2) {			
			Airport a = null;
			route.setSourceAirport((Airport) find(selAirportCodes[0], Airport.class));
			route.setDestinationAirport((Airport) find(selAirportCodes[1], Airport.class));
			update(route, userId);
		}
		
		return (List<Route>) list(Route.class);	
	}
	
	private Route findRoute(Airport departureAirport, Airport arrivalAirport) {
		LOG.info("findRoute - " + departureAirport.getAirportCode() + " " + arrivalAirport.getAirportCode());
		
		Query q = entityManager.createQuery(FIND_FLIGHT_ROUTE);
		
		q.setParameter("departureAirportCode", departureAirport.getAirportCode());
		q.setParameter("arrivalAirportCode", arrivalAirport.getAirportCode());
		
		Route r = null;
		try {
			r = (Route) q.getSingleResult();
		} catch(Exception e) {
			LOG.info("No route found - " + departureAirport.getAirportCode() + " " + arrivalAirport.getAirportCode());
		}
		
		return r;		
	}

	@Override
	public List<Airport> saveAirport(Airport airport, String airportCode, Long cityId, Long userId) {
		LOG.info("saveAirport - " + airportCode + " " + cityId + " " + userId);
		
		City city = (City) find(cityId, City.class);
		airport.setCity(city);
		
		if(airportCode != null && !airportCode.isEmpty()) {
			airport.setAirportCode(airportCode);
			update(airport, userId);
		} else {
			insert(airport, userId);
		}
		
		return listAirports(city.getCountryState().getCountry().getCountryCode());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AirplaneMake> saveAirplaneMake(AirplaneMake airplaneMake,
			String makeName, Long userId) {
		LOG.info("saveAirplaneMake - " + makeName + " " + userId);
		
		if(makeName != null && !makeName.isEmpty()) {
			airplaneMake.setMakeName(makeName);
			update(airplaneMake, userId);
		} else {
			insert(airplaneMake, userId);
		}
		
		return (List<AirplaneMake>) list(AirplaneMake.class);
	}

	@Override
	public List<AirplaneModel> saveAirplaneModel(AirplaneModel airplaneModel, String modelName, String makeName, Long userId) {
		LOG.info("saveAirplaneModel - " + modelName + " " + makeName + " " + userId);
		
		AirplaneMake airplaneMake = (AirplaneMake) find(makeName, AirplaneMake.class);
		airplaneModel.setAirplaneMake(airplaneMake);
		
		if(modelName != null && !modelName.isEmpty()) {
			airplaneModel.setModelName(modelName);
			update(airplaneModel, userId);
		} else {
			insert(airplaneModel, userId);
		}
		
		return listAirplaneModels(makeName);
	}
	
	@Override
	public List<Airplane> saveAirplane(Airplane airplane, String aircraftReg, Long airlineId, String modelName, Long userId) {
		LOG.info("saveAirplane - " + aircraftReg + " " + airlineId + " " + modelName + " " + userId);
		
		Airline airline = (Airline) find(airlineId, Airline.class);
		AirplaneModel airplaneModel = (AirplaneModel) find(modelName, AirplaneModel.class);
		
		airplane.setAirline(airline);
		airplane.setModel(airplaneModel);
		
		if(aircraftReg != null && !aircraftReg.isEmpty()) {
			airplane.setRegNumber(aircraftReg);
			update(airplane, userId);
		} else {
			insert(airplane, userId);
			
			addSeats(airplane, userId);
		}
		
		return listAirplanes(airlineId);
	}
	
	private void addSeats(Airplane airplane, Long userId) {
		String crxSctn = airplane.getModel().getCrossSection();
		
		int rowSize = airplane.getModel().getNumberOfRows();
		
		char[] cols = crxSctn.toCharArray();
		
		String bizClassRows = airplane.getModel().getBizClassRows();
		String fstClassRows = airplane.getModel().getFstClassRows();
		String exitRows = airplane.getModel().getFstClassRows();
		
		ArrayList<Integer> bizClassRowIndices = new ArrayList<Integer>();
		ArrayList<Integer> fstClassRowIndices = new ArrayList<Integer>();
		ArrayList<Integer> exitRowIndices = new ArrayList<Integer>();
		
		if(bizClassRows != null && !bizClassRows.isEmpty()) {
			String[] bizClassArray = bizClassRows.split(",");
			for(String index : bizClassArray) {
				LOG.info("Biz Index - " + index);
				bizClassRowIndices.add(Integer.valueOf(index.trim()));
			}
		}
		
		if(fstClassRows != null && !fstClassRows.isEmpty()) {
			String[] fstClassArray = fstClassRows.split(",");
			for(String index : fstClassArray) {
				LOG.info("Fst Index - " + index);
				fstClassRowIndices.add(Integer.valueOf(index.trim()));
			}
		}
		
		if(exitRows != null && !exitRows.isEmpty()) {
			String[] exitArray = exitRows.split(",");
			for(String index : exitArray) {
				LOG.info("Exit Index - " + index);
				exitRowIndices.add(Integer.valueOf(index.trim()));
			}
		}
		
		Seat seat = null;
		SeatId seatId = null;
		SeatInterface.SeatLocation seatLocation = null;
		SeatInterface.SeatClass seatClass = null;
		boolean exitRow = false;
		
		int isleIndex = determineIsleIndex(cols);
		
		int seatIndex = 1;
		for(int row = 1; row <= rowSize; row++) {
			for(char col : cols) {
				if(col != '|') {
					seatId = new SeatId(row, String.valueOf(col), airplane.getRegNumber());
					seatLocation = determineSeatLocation(cols, col, isleIndex);
					seatClass = determineSeatClass(row, bizClassRowIndices, fstClassRowIndices);
					exitRow = determineExitRow(row, exitRowIndices);
					
					seat = new Seat();
					seat.setId(seatId);
					seat.setSeatLocation(seatLocation);
					seat.setSeatClass(seatClass);
					seat.setSeatStatus(SeatInterface.SeatStatus.VACANT);
					seat.setSeatIndex(seatIndex);
					seat.setExitRow(exitRow);
					insert(seat, userId);
					seatIndex++;
				}
			}
		}
	}
	
	private boolean determineExitRow(int row, ArrayList<Integer> exitRowIndices) {
		boolean exitRow = false;
		if(exitRowIndices.contains(new Integer(row))) {
			exitRow = true;
		}
		
		return exitRow;
	}
	
	private SeatInterface.SeatClass determineSeatClass(int row, ArrayList<Integer> bizClassRowIndices, ArrayList<Integer> fstClassRowIndices) {
		SeatInterface.SeatClass seatClass = SeatInterface.SeatClass.ECONOMY;
		
		if(bizClassRowIndices.contains(new Integer(row))) {
			seatClass = SeatInterface.SeatClass.BUSINESS;
		}
		
		if(fstClassRowIndices.contains(new Integer(row))) {
			seatClass = SeatInterface.SeatClass.FIRST;
		}
		
		return seatClass;
	}
	
	private int determineIsleIndex(char[] cols) {
		int index = -1;
		for(int i = 0; i < cols.length; i++) {
			if(cols[i] == '|') {
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	private SeatInterface.SeatLocation determineSeatLocation(char[] cols, char currentCol, int isleIndex) {
		SeatInterface.SeatLocation seatLocation = null;
		
		if(currentCol == cols[0] || currentCol == cols[cols.length - 1]) {
			seatLocation = SeatInterface.SeatLocation.WINDOW;
		} else
		if(cols[isleIndex + 1] == currentCol || cols[isleIndex - 1] == currentCol) {
			seatLocation = SeatInterface.SeatLocation.ISLE;
		} else {
			seatLocation = SeatInterface.SeatLocation.MIDDLE;
		}
		
		return seatLocation;
	}

	@Override
	public List<Rebate> saveRebate(Rebate rebate, RateId rebateId, Long userId) {
		LOG.info("saveRebate - " + rebateId + " " + userId);
		
		if(rebateId != null) {
			rebate.setId(rebateId);
			update(rebate, userId);
		} else {
			insert(rebate, userId);
		}
		
		return listRebates(rebateId.getAirlineId());
	}
	
	@Override
	public List<City> saveCity(City city, Long cityId, Long countryStateId,
			Long userId) {
		LOG.info("saveCity - " + cityId + " " + countryStateId + " " + userId);
		
		CountryState countryState = (CountryState) find(countryStateId, CountryState.class);
		city.setCountryState(countryState);
		
		if(cityId != null) {
			city.setId(cityId);
			update(city, userId);
		} else {
			insert(city, userId);
		}
		
		return listCities(countryState.getCountry().getCountryCode());
	}

	@Override
	public List<Tax> saveTax(Tax tax, String rateCode, Long airlineId, Long userId) {
		LOG.info("saveTax - " + rateCode + " " + airlineId + " " + userId);
		
		tax.setId(new RateId(airlineId, rateCode));
		if(tax.getId() != null) {
			update(tax, userId);
		} else {
			insert(tax, userId);
		}
		
		return listTaxes(airlineId);
	}

	@Override
	public List<Seat> saveSeat(Seat seat, SeatId seatId, Long userId) {
		LOG.info("saveSeat - " + seatId + " " + userId);
		
		if(seatId != null) {
			seat.setId(seatId);
			update(seat, userId);
		}
		
		return listSeats(seatId.getRegNumber());
	}

	@Override
	public List<Training> saveTraining(Long trainingId, Training entity, Long crewMemberId, Long trainingHandlerId, Long userId) {
		LOG.info("saveTraining - " + trainingId + " " + entity + " " + crewMemberId + " " + trainingHandlerId + " " + userId);
		
		CrewMember crewMember = (CrewMember) find(crewMemberId, CrewMember.class);
		TrainingHandler trainingHandler = (TrainingHandler) find(trainingHandlerId, TrainingHandler.class);
		
		entity.setCrewMember(crewMember);
		entity.setTrainingHandler(trainingHandler);
		entity.setExpirationDate(calcTrainingExpiration(entity.getTrainingType(), entity.getCompletionDate(), crewMember.getDob(), crewMember.getCrewType()));
		if(trainingId != null) {
			entity.setId(trainingId);
			update(entity, userId);
		} else {
			insert(entity, userId);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Airline> saveAirline(Long airlineId,
			Airline airline, String currencyCode, String countryCode,
			Long userId) {
		LOG.info("saveAirline - " + airlineId + " " + currencyCode + " " + countryCode + " " + userId);
		
		if(currencyCode != null) {
			airline.setCurrency((Currency) find(currencyCode, Currency.class));
		}
		if(airline.getCurrency() == null) {			
			if(airline.getAddress() != null && airline.getAddress().getCountry() != null && airline.getAddress().getCountry().getCurrency() != null) {
				airline.setCurrency(airline.getAddress().getCountry().getCurrency());			
			}
		}			

		if(countryCode != null) {
			airline.getAddress().setCountry((Country) find(countryCode, Country.class));
		}
		
		if(airlineId != null) {
			airline.setId(airlineId);
			update(airline.getAddress(), userId);
			update(airline.getContactInfo(), userId);
			update(airline, userId);
		} else {
			insert(airline.getAddress(), userId);
			insert(airline.getContactInfo(), userId);
			insert(airline, userId);			
		}
		
		return (List<Airline>) list(Airline.class);
	}

	@Override
	public List<Organization> saveCustomer(Long customerId, Long defaultAirlineId, Customer customer, String countryCode, Long userId) {
		LOG.info("saveCustomer - " + customerId + " " + defaultAirlineId + " " + countryCode + " " + userId);
		
		Personnel personnel = (Personnel) find(userId, Personnel.class);
		Airline airline;
		if(defaultAirlineId == null) {
			airline = (Airline) personnel.getOrganization();
		} else {
			airline = (Airline) find(defaultAirlineId, Airline.class);
		}
		
		customer.setCustomer(true);
		customer.setAirline(airline);
		if(customer.getCurrency() == null) {			
			if(customer.getAddress() != null && customer.getAddress().getCountry() != null && customer.getAddress().getCountry().getCurrency() != null) {
				customer.setCurrency(customer.getAddress().getCountry().getCurrency());			
			}
		}			

		if(countryCode != null) {
			customer.getAddress().setCountry((Country) find(countryCode, Country.class));
		}
		
		if(customerId != null) {
			customer.setId(customerId);
			update(customer.getAddress(), userId);
			update(customer.getContactInfo(), userId);
			update(customer, userId);
		} else {
			insert(customer.getAddress(), userId);
			insert(customer.getContactInfo(), userId);
			insert(customer, userId);			
		}
		
		entityManager.flush();
		
		List<UserRole> roles = fetchRole(customer.getId(), customer.getCustomerType());
		if(roles == null || roles.isEmpty()) {
			UserRole userRole = new UserRole();
			userRole.setApplication("airlinemgr");
			userRole.setRole(customer.getCustomerType().name());
			AppUser user = (AppUser) find(customer.getId(), AppUser.class);
			userRole.setUser(user);
			insert(userRole, userId);
		}
		
		
		return new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private List<UserRole> fetchRole(Long customerId, CustomerType role) {
		LOG.info("fetchRole - " + customerId + " " + role);
		
		String sql = "select o from UserRole o where application='airlinemgr' and role='" + role + "' and userId=" + customerId;
		Query q = entityManager.createQuery(sql);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> saveOrganization(Long organizationId, Organization organization, String currencyCode, String countryCode, Long userId) {
		LOG.info("saveOrganization - " + organizationId + " " + currencyCode + " " + countryCode + " " + userId);
		
		if(currencyCode != null) {
			organization.setCurrency((Currency) find(currencyCode, Currency.class));
		}
		if(organization.getCurrency() == null) {			
			if(organization.getAddress() != null && organization.getAddress().getCountry() != null && organization.getAddress().getCountry().getCurrency() != null) {
				organization.setCurrency(organization.getAddress().getCountry().getCurrency());			
			}
		}			

		if(countryCode != null) {
			organization.getAddress().setCountry((Country) find(countryCode, Country.class));
		}
		
		if(organizationId != null) {
			organization.setId(organizationId);
			update(organization.getAddress(), userId);
			update(organization.getContactInfo(), userId);
			update(organization, userId);
		} else {
			insert(organization.getAddress(), userId);
			insert(organization.getContactInfo(), userId);
			insert(organization, userId);			
		}
		
		return (List<Organization>) list(organization.getClass());
	}
	@Override
	public void saveCrewActivity(Long activityId, CrewActivity activity, Long[] selCrewMemberIds, Long userId) {
		LOG.info("saveCrewActivity - " + activityId + " " + activity + " " + selCrewMemberIds + " " + userId);
		
		List<CrewMember> crew = new ArrayList<CrewMember>();
		for(Long memberId : selCrewMemberIds) {
			crew.add((CrewMember) find(memberId, CrewMember.class));
		}
		
		if(activityId == null) {
			insert(activity, userId);
			activity.setFlightCrew(crew);
			update(activity, userId);
		} else {
			activity.setId(activityId);
			activity.setFlightCrew(crew);
			update(activity, userId);
		}
	}

	@Override
	public void savePersonnel(Long personnelId, Personnel personnel, Long organizationId, File photo, Long userId) {
		LOG.info("savePersonnel - " + personnelId + " " + personnel + " " + organizationId + " " + photo + " " + userId);
		
		Organization organization = (Organization) find(organizationId, Organization.class);
		personnel.setOrganization(organization);
		personnel.setPassword(Sha512DigestUtils.shaHex(personnel.getPassword()));
		if(personnel instanceof CrewMember) {
			CrewMember crewMember = (CrewMember) personnel;
			try {
				String[] mf = mimeTypeAndFileExtension("JPEG");
				crewMember.setPhoto(resizeImage(Files.readAllBytes(photo.toPath()), 400, "JPEG"));
				crewMember.setPhotoMimeType(mf[0]);
				crewMember.setPhotoFileExtension(mf[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(personnelId != null) {
			personnel.setId(personnelId);
			update(personnel, userId);
		} else {
			insert(personnel, userId);
		}
	}

	@Override
	public void saveFlightCrew(FlightId flightId, Long[] selCrewMemberIds, Long userId) {
		LOG.info("saveFlightCrew - " + flightId + " " + selCrewMemberIds + " " + userId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		List<CrewMember> crew = new ArrayList<CrewMember>();
		
		for(Long memberId : selCrewMemberIds) {
			crew.add((CrewMember) find(memberId, CrewMember.class));
		}
		
		flight.setFlightCrew(crew);
		
		update(flight, userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingSupervisor> saveTrainingSupervisor(Long trainingHandlerId, TrainingSupervisor entity, File uploadedFile, String fileDescription, Long userId) {
		LOG.info("saveTrainingSupervisor - " + trainingHandlerId + " " + entity + " " + uploadedFile + " " + userId);
		
		if(trainingHandlerId != null) {
			DocumentFile docFile = updateDocumentFile(entity.getCredentialFile(), uploadedFile, fileDescription);
			update(docFile, userId);
			entity.setCredentialFile(docFile);
			entity.setId(trainingHandlerId);
			update(entity, userId);
		} else {
			DocumentFile docFile = createDocumentFile(uploadedFile, fileDescription);
			insert(docFile, userId);
			entity.setCredentialFile(docFile);
			insert(entity, userId);
		}
		
		return (List<TrainingSupervisor>) listTrainers(TrainingSupervisor.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingPilot> saveTrainingPilot(Long trainingHandlerId, TrainingPilot entity, Long userId) {
		LOG.info("saveTrainingPilot - " + trainingHandlerId + " " + entity + " " + userId);
		
		if(trainingHandlerId != null) {
			entity.setId(trainingHandlerId);
			update(entity, userId);
		} else {
			insert(entity, userId);
		}
		
		return (List<TrainingPilot>) listTrainers(TrainingPilot.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrainingDoctor> saveTrainingDoctor(Long trainingHandlerId, TrainingDoctor entity, Long userId) {
		LOG.info("saveTrainingDoctor - " + trainingHandlerId + " " + entity + " " + userId);
		
		if(trainingHandlerId != null) {
			entity.setId(trainingHandlerId);
			update(entity, userId);
		} else {
			insert(entity, userId);
		}
		
		return (List<TrainingDoctor>) listTrainers(TrainingDoctor.class);
	}
	
	private DocumentFile createDocumentFile(File file, String description) {
		DocumentFile docFile = new DocumentFile();
		docFile.setDescription(description);
		docFile.setFileName(file.getName());
		try {
			Path path = file.toPath();
			docFile.setMimeType(Files.probeContentType(path));
			docFile.setRawContent(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return docFile;
	}
	
	private int calcAge(Date dob) {
		Calendar dobCal = Calendar.getInstance();
		Calendar currCal = Calendar.getInstance();
		
		
		dobCal.setTime(dob);
		currCal.setTime(new Date());
		
		return currCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
	}
	
	private Date calcTrainingExpiration(TrainingType trainingType, Date completionDate, Date dob, CrewType crewType) {
		LOG.info("calcTrainingExpiration - " + trainingType + " " + completionDate + " " + dob + " " + crewType);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(completionDate);
		
		switch (crewType) {
		case SENIOR_PILOT:
		case PILOT:
		case CO_PILOT:
			switch (trainingType) {
			case CRM:
				cal.add(Calendar.YEAR, 2);
				break;
			case DG:
				cal.add(Calendar.YEAR, 2);
				break;
			case EandS:
				cal.add(Calendar.YEAR, 2);
				break;
			case FED:
				cal.add(Calendar.YEAR, 1);
				break;
			case FIRST_AID:
				cal.add(Calendar.YEAR, 3);
				break;
			case IRR:
				cal.add(Calendar.MONTH, 13);
				break;
			case LINE_CHECK:
				cal.add(Calendar.MONTH, 6);
				break;
			case MEDICALS:
				if(calcAge(dob) <= 40) {
					cal.add(Calendar.YEAR, 1);
				} else {
					cal.add(Calendar.MONTH, 6);
				}
				break;
			case OPC:
				cal.add(Calendar.MONTH, 6);
				break;
			case SIM:
				cal.add(Calendar.MONTH, 6);
				break;
			}
			break;

		case SENIOR_ATTENDANT:
		case ATTENDANT:
			switch (trainingType) {
			case CRM:
				cal.add(Calendar.YEAR, 2);
				break;
			case DG:
				cal.add(Calendar.YEAR, 2);
				break;
			case EandS:
				cal.add(Calendar.YEAR, 1);
				break;
			case FED:
				cal.add(Calendar.YEAR, 1);
				break;
			case FIRST_AID:
				cal.add(Calendar.YEAR, 2);
				break;
			case LINE_CHECK:
				cal.add(Calendar.YEAR, 1);
				break;
			case MEDICALS:
				cal.add(Calendar.YEAR, 1);
				break;
			case IRR:
			case OPC:
			case SIM:
				break;
			}
			break;
		case ENGINEER:
		case SENIOR_ENGINEER:
			break;
		case SENIOR_TRAFFIC_CONTROLLER:
		case TRAFFIC_CONTROLLER:
			break;
		default:
			break;
	
		}
		
		return cal.getTime();
	}
	
	private DocumentFile updateDocumentFile(DocumentFile docFile, File file, String description) {
		docFile.setDescription(description);
		docFile.setFileName(file.getName());
		try {
			Path path = file.toPath();
			docFile.setMimeType(Files.probeContentType(path));
			docFile.setRawContent(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return docFile;
	}
	
	@Override
	public Flight changeFlightStatus(FlightId flightId,	FlightStatus flightStatus, Long userId) {
		LOG.info("changeFlightStatus - " + flightId + " " + flightStatus + " " + userId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		flight.setFlightStatus(flightStatus);
		
		update(flight, userId);
		
		return flight;
	}
	
	@Override
	public String validateCrewSelection(Long[] selCrewMemberIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Double[] flightIncomeAndLoss(FlightId flightId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Seat> listUnsoldSeats(FlightId flightId) {
		// TODO Auto-generated method stub
		
		// TODO obtain the list of seats that were sold (with passenger having one seat each)
		// TODO filter out the list of seats unsold
		// TODO set the flight fare on each seat
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Seat> listUnsoldSeats(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		
		// TODO obtain a list of flights within the period
		// iterate through the list above and call listUnsoldSeats(flightId) for each flight
		
		return null;
	}

	@Override
	public Double sumTicketSales(List<Ticket> tickets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double sumFlightLosses(List<Seat> unsoldSeats) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket> listTicketSales(FlightId flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double sumTicketSales(FlightId flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Ticket> listTicketSales(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double sumTicketSales(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double[] flightIncomeAndLoss(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Passenger> listCheckedPassengers(FlightId flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Passenger> listUncheckedPassengers(FlightId flightId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<AirplaneModel> listAirplaneModels(String makeName) {
		LOG.info("listAirplaneModels - " + makeName);
		
		AirplaneMake airplaneMake = (AirplaneMake) find(makeName, AirplaneMake.class);
		
		return airplaneMake.getModels();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<FlightSeat> listFlightSeats(FlightId flightId) {
		LOG.info("listFlightSeats - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		return flight.getFlightSeats();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Training> listTrainings(Long crewMemberId) {
		LOG.info("listTrainings - " + crewMemberId);
		
		// Limit trainings to just the most recent record for each TrainingType instance		
		Query q = entityManager.createQuery(LIST_CURRENT_TRAINING);
		List<Training> trainings = new ArrayList<Training>();
		for(TrainingType trainingType : TrainingType.values()) {
			q.setParameter("trainingType", trainingType);
			q.setParameter("crewMemberId", crewMemberId);
			q.setMaxResults(1);
			trainings.add((Training) q.getSingleResult());
		}
		
		return trainings == null ? new ArrayList<Training>() : trainings;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<?> listTrainers(Class<?> classType) {
	LOG.info("listTrainers - " + classType);
		
		Query q = entityManager.createQuery(LIST_TRAINERS_OF_TYPE);
		
		q.setParameter("classType", classType);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Personnel> listPersonnel(Long organizationId) {
		LOG.info("listPersonnel - " + organizationId);
		
		Query q = entityManager.createQuery(LIST_ORGANIZATION_PERSONNEL);
		
		q.setParameter("organizationId", organizationId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<CrewMember> listCrewMembers(Long airlineId) {
		LOG.info("listCrewMembers - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_CREW_MEMBERS);
		
		Organization airline = (Organization) find(airlineId, Organization.class);
		q.setParameter("organizationId", airlineId);
		
		return q.getResultList();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<CrewActivity> listFlightRelatedActivities(FlightId relatedFlightId) {
		LOG.info("listFlightRelatedActivities - " + relatedFlightId);
		
		Flight flight = (Flight) find(relatedFlightId, Flight.class);
		
		List<CrewMember> crew = flight.getFlightCrew();
		
		List<CrewActivity> activities = new ArrayList<CrewActivity>();
		
		for(CrewMember c : crew) {
			for(CrewActivity a : c.getActivitySchedule()) {
				if(flight.getId().getFlightDate().equals(a.getStartTime())) {
					activities.add(a);
				}
			}
		}
		
		return activities;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Map<Integer, List<Activity>> listAllActivitySchedule(Long crewMemberId, CalendarPeriod calendarPeriod) {
		LOG.info("listAllActivitySchedule - " + crewMemberId + " " + calendarPeriod);
		
		Query q1 = null;
		Query q2 = null;
		
		Map<Integer, List<Activity>> schedule = new HashMap<Integer, List<Activity>>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		int activityPeriod = 0;
		
		CrewMember crewMember = (CrewMember) find(crewMemberId, CrewMember.class);
		
		switch (calendarPeriod) {
		case WEEK:
			activityPeriod = Calendar.DAY_OF_WEEK;
			
			q1 = entityManager.createQuery(LIST_WEEK_ACTIVITIES);
			q2 = entityManager.createQuery(LIST_WEEK_FLIGHTS);
			Date[] range = fetchWeekRange(new Date());
			q1.setParameter("weekStartDate", range[0]);
			q1.setParameter("weekEndDate", range[1]);
			q1.setParameter("crewMember", crewMember);
			
			q2.setParameter("weekStartDate", range[0]);
			q2.setParameter("weekEndDate", range[1]);
			q2.setParameter("crewMember", crewMember);
			
			int start = calendar.getMinimum(activityPeriod);
			int end = calendar.getMaximum(activityPeriod);
			for(int day = start; day <= end; day++) {
				schedule.put(day, new ArrayList<Activity>());
			}
	
			break;
		case MONTH:
			activityPeriod = Calendar.DAY_OF_MONTH;
			
			q1 = entityManager.createQuery(LIST_MONTH_ACTIVITIES);
			q2 = entityManager.createQuery(LIST_MONTH_FLIGHTS);
			
			start = calendar.getMinimum(activityPeriod);
			end = calendar.getMaximum(activityPeriod);
			for(int day = start; day <= end; day++) {
				schedule.put(day, new ArrayList<Activity>());
			}
			break;
		default:
			break;
		}
		
		List<CrewActivity> activities = q1.getResultList();
		List<Flight> flights = q1.getResultList();
		
		List<Activity> allActivities = new ArrayList<>();
		
		allActivities.addAll(activities);
		allActivities.addAll(flights);
		
		Calendar c = Calendar.getInstance();
		for(Activity a : allActivities) {
			c.setTime(a.getStartTime());
			schedule.get(c.get(activityPeriod)).add(a);
		}
		
		return schedule;
	}

	@Override
	public Map<Activity, CrewMember> validateActivityOverlap(Date startTime, Date endTime, Long[] crewMemberIds) {
		LOG.info("validateActivityOverlap - " + startTime + " " + endTime + " " + crewMemberIds);

		HashMap<Activity, CrewMember> overlapMap = new HashMap<Activity, CrewMember>(); 

		CrewMember crewMember = null;
		for(Long memberId : crewMemberIds) {
			crewMember = (CrewMember) find(memberId, CrewMember.class);
			
			Query q1 = entityManager.createQuery(FIND_OVERLAPPING_ACTIVITIES);
			Query q2 = entityManager.createQuery(FIND_OVERLAPPING_FLIGHTS);
			
			q1.setParameter("crewMember", crewMember);
			q1.setParameter("startTime", startTime);
			q1.setParameter("endTime", endTime);
			
			q2.setParameter("crewMember", crewMember);
			q2.setParameter("startTime", startTime);
			q2.setParameter("endTime", endTime);
			
			for(Object a : q1.getResultList()) {
				overlapMap.put((Activity) a, crewMember);
			}
			for(Object a : q2.getResultList()) {
				overlapMap.put((Activity) a, crewMember);
			}
		}
		
		return overlapMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<ActivityPeriod, CrewMember> validateWorkLimit(Long[] crewMemberIds, Date referenceDate) {
		LOG.info("validateWorkLimit - " + " " + crewMemberIds + " " + referenceDate);
		
		HashMap<ActivityPeriod, CrewMember> limitViolations = new HashMap<ActivityPeriod, CrewMember>();
		
		List<Object[]> allRanges = new ArrayList<Object[]>();
		
		CrewMember crewMember = null;
		for(Long memberId : crewMemberIds) {
			crewMember = (CrewMember) find(memberId, CrewMember.class);

			for(ActivityPeriod period : ActivityPeriod.values()) {
				Query q1 = entityManager.createQuery(LIST_PERIOD_ACTIVITY_RANGE);
				Query q2 = entityManager.createQuery(LIST_PERIOD_FLIGHT_RANGE);
				Date[] range = fetchPeriodRange(referenceDate, period);
				q1.setParameter("periodStartDate", range[0]);
				q1.setParameter("periodEndDate", range[1]);
				q1.setParameter("crewMember", crewMember);
				
				q2.setParameter("periodStartDate", range[0]);
				q2.setParameter("periodEndDate", range[1]);
				q2.setParameter("crewMember", crewMember);
				
				allRanges.addAll((List<Object[]>) q1.getResultList());
				allRanges.addAll((List<Object[]>) q2.getResultList());
				
				int sum = 0;
				for(Object[] a : allRanges) {
					sum += fetchHoursInDateRange(a);
				}
				
				if(sum > MAX_HOURS_PER_PERIOD.get(period)) {
					limitViolations.put(period, crewMember);
				}
			}
		}
		
		return null;
	}
	
	private Date[] fetchWeekRange(Date referenceDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(referenceDate);
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(referenceDate);
		cal1.add(Calendar.DAY_OF_WEEK, dayOfWeek - Calendar.SUNDAY);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(referenceDate);
		cal2.add(Calendar.DAY_OF_WEEK, dayOfWeek - Calendar.SUNDAY);
		
		Date[] range = new Date[2];
		
		range[0] = cal1.getTime();
		range[1] = cal2.getTime();
		
		return range;
	}
	
	private Date[] fetchPeriodRange(Date referenceDate, ActivityPeriod period) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(referenceDate);
		
		int numOfDays = 0;
		switch (period) {
		case DAY_1:
			numOfDays = 0;
			break;
		case DAY_7:
			numOfDays = 7;
			break;
		case DAY_30:
			numOfDays = 30;
			break;
		case DAY_90:
			numOfDays = 90;
			break;

		default:
			break;
		}
		
		cal.add(Calendar.DAY_OF_YEAR, numOfDays);
		
		Date[] range = new Date[2];
		
		range[0] = referenceDate;
		range[1] = cal.getTime();
		
		return range;
	}
	
	private int fetchHoursInDateRange(Object[] range) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime((Date) range[0]);
		
		int h1 = c1.get(Calendar.HOUR_OF_DAY);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime((Date) range[1]);
		
		int h2 = c2.get(Calendar.HOUR_OF_DAY);
		
		return h2 - h1;
	}
	
    private String[] mimeTypeAndFileExtension(String encodingType) {
    	LOG.info("mimeTypeAndFileExtension - " + encodingType);
    	
    	String val = IMAGE_ENCODING_TYPE_MAP.get(encodingType);
    	
    	String[] pair = null;
    	if(val != null) {
    		pair = val.split(":");
    	}
    	
    	LOG.info("mimeTypeAndFileExtension - " + (pair != null ? Arrays.asList(pair) : pair));
    	
    	return pair;
    }

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Booking> listBookings(FlightId flightId) {
		LOG.info("listBookings - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		return flight != null ? flight.getBookings() : new ArrayList<Booking>();
	}

	@Override
	public void saveRoutePrice(RoutePrice routePrice, Long userId) {
		LOG.info("saveRoutePrice - " + " " + routePrice + " " + userId);
		insert(routePrice, userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<RoutePrice> listRoutePrices(Long organizationId) {
		LOG.info("listRoutePrices - " + organizationId);
		
		Query q = entityManager.createQuery(LIST_ORGANIZATION_ROUTE_PRICE);
		
		q.setParameter("organizationId", organizationId);
		
		return q.getResultList();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public File printFlightManifest(FlightId flightId) {
		LOG.info("printFlightManifest - " + flightId);
		
		Flight flight = (Flight) find(flightId, Flight.class);
		
		List<Booking> bookings = flight.getBookings();
		LOG.info("printFlightManifest - flight-bookings=" + bookings.size());
		
		double totalCarryonWeight = 0;
		double totalLuggageWeight = 0;
		double totalPassengerWeight = 0;
		int totalLuggageCount = 0;
		Passenger p;
		ArrayList<TicketData> checkedIn = new ArrayList<>();
		int idx = 1;
		for(Booking b : bookings) {
			for(Ticket t : b.getTickets()) {
				LOG.info("printFlightManifest - ticket-bookings=" + b.getTickets().size());
				p = t.getPassenger();
				if(p != null) {
					checkedIn.add(transform2Data(t, idx));
					totalCarryonWeight += p.getCarryOnWeight();
					totalLuggageWeight += p.getLuggageWeight();
					totalPassengerWeight += p.getPassengerWeight();
					totalLuggageCount += p.getLuggageCount();
					idx++;
				}
			}
		}
		
		double grossWeight = totalCarryonWeight + totalLuggageWeight + totalPassengerWeight;
		
		File manifestDoc = null;
		
		LOG.info("printFlightManifest - generating file..." + checkedIn.size());
		try {
			InputStream in = this.getClass().getResourceAsStream("/flight_manifest_tpl.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
			Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);
			IContext context = report.createContext();
			FieldsMetadata metadata = report.createFieldsMetadata();
	
			metadata.load("tickets", TicketData.class, true);
			context.put("tickets", checkedIn);
			context.put("flight", flight);
			DecimalFormat df = new DecimalFormat("####");
			df.setDecimalSeparatorAlwaysShown(false);
			DecimalFormat df2 = new DecimalFormat("####.0");
			context.put("flightNumber", df.format(flight.getId().getFlightNumber()));
			context.put("departureTime", DATE_TIME_FORMAT.format(flight.getDepartureTime()));
			context.put("arrivalTime", DATE_TIME_FORMAT.format(flight.getArrivalTime()));
			context.put("totalCarryonWeight", df2.format(totalCarryonWeight));
			context.put("totalLuggageWeight", df2.format(totalLuggageWeight));
			context.put("totalPassengerWeight", df2.format(totalPassengerWeight));
			context.put("totalLuggageCount", df.format(totalLuggageCount));
			context.put("grossWeight", df2.format(grossWeight));
			String manifestFileName = "manifest_" + String.format("%08d", (flight.getId().getFlightNumber() + flight.getId().getFlightDate().getTime()))
					+ ".pdf";
			manifestDoc = new File("/home/odada/data/airlinemgr/manifests/" + manifestFileName);
			OutputStream out = new FileOutputStream(manifestDoc);
			byte[] fileData = new byte[(int) manifestDoc.length()];
			out.write(fileData);
			report.convert(context, options, out);
			out.close();
			LOG.info("printFlightManifest - generated.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manifestDoc;
	}
	
	private TicketData transform2Data(Ticket ticket, int manifestRow) {
		TicketData data = new TicketData();
		
		data.setManifestRow(manifestRow);
		data.setAgentName(ticket.getBooking().getAgent().getOrganization().getOrganizationName());
		data.setAirlineCode(ticket.getBooking().getFlight().getId().getAirlineCode());
		data.setArrivalAirportCode(ticket.getBooking().getFlight().getArrivalAirport().getAirportCode());
		data.setArrivalTime(ticket.getBooking().getFlight().getArrivalTime());
		data.setCarryOnWeight(String.valueOf(ticket.getPassenger().getCarryOnWeight()));
		data.setColumnId(ticket.getFlightSeat().getColumnId());
		data.setDepartureAirportCode(ticket.getBooking().getFlight().getDepartureAirport().getAirportCode());
		data.setDepartureTime(ticket.getBooking().getFlight().getDepartureTime());
		data.setFlightNumber(ticket.getBooking().getFlight().getId().getFlightNumber());
		data.setGender(new Character(ticket.getPassenger().getGender().name().charAt(0)).toString());
		data.setLuggageCount(String.valueOf(ticket.getPassenger().getLuggageCount()));
		data.setLuggageWeight(String.valueOf(ticket.getPassenger().getLuggageWeight()));
		data.setMakeName(ticket.getBooking().getFlight().getAirplane().getModel().getAirplaneMake().getMakeName());
		data.setMinorPassenger(ticket.getPassenger().getMinorPassenger() != null ? ticket.getPassenger().getMinorPassenger().name() : "N/A");
		data.setModelName(ticket.getBooking().getFlight().getAirplane().getModel().getModelName());
		data.setOrganizationName(ticket.getBooking().getOwner().getOrganizationName());
		data.setPassengerName(ticket.getPassenger().getFullName());
		double passengerWeight = ticket.getPassenger().getGender().equals(Gender.MALE) ? 85 : 75;
		passengerWeight = ticket.getPassenger().getMinorPassenger() != null ? 40 : passengerWeight;
		data.setPassengerWeight(String.valueOf(passengerWeight));
		data.setPassportNumber(ticket.getPassenger().getPassportNumber());
		data.setPrice(ticket.getPrice());
		data.setRegNumber(ticket.getBooking().getFlight().getAirplane().getRegNumber());
		data.setReservationCode(ticket.getBooking().getReservationCode());
		data.setRowNumber(ticket.getFlightSeat().getRowNumber());
		data.setTicketCode(ticket.getTicketCode());
		DecimalFormat df = new DecimalFormat("####.0");
		data.setTotalWeight(df.format(ticket.getPassenger().getLuggageWeight() + ticket.getPassenger().getCarryOnWeight() + passengerWeight));

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Object[]> listInvoices(Long clientId, Date startDate, Date endDate, InvoiceStatus invoiceStatus, boolean exportData) {
		LOG.info("listInvoices - " + clientId + " " + startDate + " " + endDate + " " + invoiceStatus + " " + exportData);
		
		StringBuilder query = new StringBuilder("select o from Invoice o where o.invoiceDate >= :startDate and o.invoiceDate <= :endDate");
		
		if(invoiceStatus != null) {
			query.append(" and o.invoiceStatus = :invoiceStatus");
		}
		if(clientId != null) {
			query.append(" and o.customer.id = :clientId");
		}

		Query q = entityManager.createQuery(query.toString()); 
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);

		if(invoiceStatus != null) {
			q.setParameter("invoiceStatus", invoiceStatus);
		}
		if(clientId != null) {
			q.setParameter("clientId", clientId);
		}
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0/5 * * * ?")
	public void mailTickets() {
		LOG.info("mailTickets - running: " + DATE_TIME_FORMAT2.format(new Date()));
		Query q = entityManager.createQuery(LIST_UNSENT_TICKETS); //TODO add date filter
		q.setParameter("newStatus", TicketStatus.NEW);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		q.setParameter("filterDate", cal.getTime());
		
		List<String> cc = Collections.emptyList();
		List<Ticket> unsentTickets = q.getResultList();
		try {
			for(Ticket ticket : unsentTickets) {
				File reservationDoc = new File(ticket.getFilePath());
				OutputStream out = new FileOutputStream(reservationDoc);
				byte[] attachmentData = new byte[(int) reservationDoc.length()];
				out.write(attachmentData);
				LOG.info("mailTickets - sending mail: " + ticket.getPassenger().getEmail1());
				try {
					StringBuilder body = new StringBuilder("Dear "); body.append(ticket.getPassenger().getFullName()); body.append(",\n\n");
					body.append("Please find the confirmation for the intended flight attached.\n\n");
					body.append("Regards,\n");
					body.append("Bristow Nigeria Limited");
					
					emailService.sendEmailWithAttachment("neeyeed@gmail.com", ticket.getPassenger().getEmail1(), cc, "Booking Confirmation - " + ticket.getTicketCode(), body.toString(), reservationDoc.getName(), attachmentData);
				} catch(Exception e) {
					e.printStackTrace();
				}
				out.close();
				ticket.setTicketStatus(TicketStatus.SENT);
				update(ticket, null);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//TODO Setup Cron job to remit TSC to NCAA 
	@SuppressWarnings({ "unchecked" })
	@Scheduled(cron = "0 0 * * * *")
	public void remitTsc() {
		LOG.info("remitTsc - " + DATE_TIME_FORMAT2.format(new Date()));
		
		//TODO Get a list of all TSC instances not yet remitted
		//TODO Iterate and sum all taxes
		//TODO Fetch NCAA Bank Account
		//TODO Fetch Bristow Bank Account
		//TODO Connect to Payment sub-system and send
	}
	
	//TODO Setup Cron job to create/recreate recurrent flights
	
	//TODO Setup Cron job to deliver invoices based on billing cycles
//	@Scheduled(cron = "0 0 * * 6")
	@SuppressWarnings({ "unchecked" })
	@Scheduled(cron = "0 0 * * * *")
	public void generateInvoices() {
		LOG.info("generateInvoices - " + DATE_TIME_FORMAT2.format(new Date()));
		
		try {
			InputStream in = this.getClass().getResourceAsStream("/invoice_tpl.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);
			Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);
			List<String> cc = Collections.emptyList();

			Query q = entityManager.createQuery(LIST_INVOICABLE_TICKETS); //TODO add date filter
			q.setParameter("confirmedStatus", BookingStatus.CONFIRMED);
			
			List<Ticket> invoicableTickets = q.getResultList();
			String invoiceFileName;
			File invoiceDoc;
			HashMap<Long, Invoice> invoices = new HashMap<>();
			Invoice invoice;
			try {
				for(Ticket t : invoicableTickets) {
					if(!invoices.containsKey(t.getBooking().getOwner().getId())) {
						invoices.put(t.getBooking().getOwner().getId(), new Invoice());
					}
					invoice = invoices.get(t.getBooking().getOwner().getId());
					invoice.setCustomer(t.getBooking().getOwner());
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.DATE, cal.get(Calendar.DATE + 7));
					invoice.setOverdueDate(cal.getTime());
					t.getBooking().setBookingStatus(BookingStatus.INVOICED);
					update(t.getBooking(), null);
					
					insert(invoice, null);
				}
				
				Organization ogz;
				for(Entry<Long, Invoice> i : invoices.entrySet()) {
					ogz = (Organization) find(i.getKey(), Organization.class);
					FieldsMetadata metadata = report.createFieldsMetadata();
					
					metadata.load( "tickets", Ticket.class, true );
					IContext context = report.createContext();
					double totalAmount = 0;
					for(Ticket t : i.getValue().getTickets()) {
						totalAmount += t.getPrice();
						t.setInvoice(i.getValue());
						update(t, null);
					}
					i.getValue().setTotalAmount(totalAmount);
					update(i.getValue(), null);
					context.put("invoice", i.getValue());
					context.put("tickets", i.getValue().getTickets());
					context.put("invoiceDate", FORMAT_DAY2.format(i.getValue().getInvoiceDate()));
					context.put("totalAmount", totalAmount);
					StringBuilder body = new StringBuilder("Dear "); body.append(ogz.getOrganizationName()); body.append(",\n\n");
					body.append("Please find your invoice for the most recent billing cycle.\n\n");
					body.append("Regards,\n");
					body.append("Bristow Nigeria Limited");
					invoiceFileName = "invoice_" + String.format("%08d", i.getValue().getId()) + ".pdf";
					invoiceDoc = new File("/home/odada/data/airlinemgr/invoices/" + invoiceFileName);
					
					OutputStream out = new FileOutputStream(invoiceDoc);
					byte[] attachmentData = new byte[(int) invoiceDoc.length()];
					out.write(attachmentData);
					report.convert(context, options, out);
					out.close();
					
				}
			} catch (PersistenceException | IOException | XDocReportException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XDocReportException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void processRules() {
		List<Flight> flights = null;
		
		for(Flight flight : flights) {
			if(flight.isStatInit()) {
				flight.setStatInit(false);
				update(flight, 1L);
			} else {
				//TODO Work on a better chaining mechanism
				flight = ruleImplD(ruleImplC(ruleImplB(ruleImplA(flight))));
			}
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Flight ruleImplA(Flight flight) {
		//TODO Time-based (hourly) Exponential Decay approaches set price limit as flight time approaches over a 2 week period.
		//TODO N(t) = N(0) / (1 + e ^ (-lambda * t))
		//TODO Constants are: t(0) (2 weeks from flight date) and lambda (exponential decay constant).
		//TODO Time t is always the difference between current date and 2 weeks from flight date in hours.
		return flight;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Flight ruleImplB(Flight flight) {
		//TODO Implement time-based parabola with peak at mid afternoon.
		//TODO The parabola Y-axis represents the difference between the Base Price and new Price (Delta Price).
		//TODO The parabola X-axis represents the difference between the Earliest Time and Current Time (Delta T) for the day.
		//TODO Should be of the form -Ax^2 + Bx + C. That is, A is negative. Solutions (cross X-Axis) should be at Earliest Time and Latest Time.
		//TODO Peak should be at average of Earliest Time and Latest Time
		//TODO Use Base Price to calculate new Prices across all classes (Standard, Business, First).
		
		YieldRuleA rule = (YieldRuleA) find("YieldRuleA", YieldRuleA.class);
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		
		double time = hour + minute / 60.0;
		
		if(time >= rule.getxMin() && time <= rule.getxMax()) {
			
			double x = time - rule.getxMin();
			
			double deltaY = (rule.getCoeffA() * x * x + rule.getCoeffB() * x) * rule.getyMultiplier() * rule.getyMaxPercent();
			
			double airFareFirst = deltaY * flight.getAirFareFirstClass() + flight.getAirFareFirstClass();
			
			double airFareBiz = deltaY * flight.getAirFareBizClass() + flight.getAirFareBizClass();
			
			double airFareEco = deltaY * flight.getAirFareEconomy() + flight.getAirFareEconomy();
			
			flight.setYmAirFareFirstClass(airFareFirst);
			
			flight.setYmAirFareBizClass(airFareBiz);
			
			flight.setYmAirFareEconomy(airFareEco);
			
			update(flight, 1L);
		}
		
		return flight;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Flight ruleImplC(Flight flight) {
		//TODO Compute delta of search hits - difference of current search hits and previous search hits
		//TODO Compute new Prices as the sum of Base Prices and the weighted delta product
		int delta = flight.getSearchHitB() - flight.getSearchHitA();

		//Finally move B to A
		flight.setSearchHitA(flight.getSearchHitB());
		update(flight, 1L);
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Flight ruleImplD(Flight flight) {
		//TODO Compute delta of booked seats - difference of current booked seats and previous booked seats
		//TODO Compute new Prices as the sum of Base Prices and the weighted delta product
		int delta = flight.getBookedSeatsB() - flight.getBookedSeatsA();
		
		//Finally move B to A
		flight.setBookedSeatsA(flight.getBookedSeatsB());
		update(flight, 1L);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<FlightSchedule> listFlightSchedules(Long airlineId) {
		LOG.info("listFlightSchedules - " + airlineId);
		
		Query q = entityManager.createQuery(LIST_FLIGHT_SCHEDULES_BY_AIRLINE);
		
		q.setParameter("airlineId", airlineId);
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<TscRemittance> listRemittances(Date startDate, Date endDate, RemittanceStatus remittanceStatus, boolean exportData) {
		LOG.info("listRemittances - " + startDate + " " + endDate + " " + remittanceStatus + " " + exportData);
		
		if(startDate == null) {
			startDate = new Date();
		}
		if(endDate == null) {
			endDate = new Date();
		}
		StringBuilder qry = new StringBuilder("select o from TscRemittance o where o.ticketDate between :startDate and :endDate");
		
		if(remittanceStatus != null) {
			qry.append(" and o.remittanceStatus = :remittanceStatus");
		}
		Query q = entityManager.createQuery(qry.toString());
		
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		if(remittanceStatus != null) {
			q.setParameter("remittanceStatus", remittanceStatus);
		}
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Object[]> generateReport(Date startDate, Date endDate, Long reportId, Long customerId, Long userId) {
		LOG.info("generateReport - " + startDate + " " + endDate + " " + reportId + " " + customerId + " " + userId);
		
		ActivityReport report = (ActivityReport) find(reportId, ActivityReport.class);
//		final String LIST_ACTIVITY_REPORT = "select o from " + report.getReportEntity() + " o where o." + report.getDateFilter() + " between :startDate and :endDate";
		
		Query q = entityManager.createQuery(report.getReportJpql());
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		
		List<Object[]> list = q.getResultList();
		LOG.info("generateReport - result count - " + (list != null ? list.size() : list));
		
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String generateReport(Long reportId, List<Object[]> reportData, Long userId) {
		LOG.info("generateReport - " + reportId + " " + userId);

		ActivityReport activityReport = (ActivityReport) find(reportId, ActivityReport.class);
		
		FileOutputStream outputStream = null;
		String fileName = "/tmp/report_" + activityReport.getId() + "_" + System.currentTimeMillis() + ".xlsx";

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(activityReport.getDescription());

//		ActivityReportColumn[] columns = new ActivityReportColumn[activityReport.getReportColumns().size()];
//		columns = activityReport.getReportColumns().toArray(columns);
		
		String[] headers = activityReport.getHeaders().split(",");

		RichTextString richText;
		XSSFFont headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setBoldweight((short) 2);
		headerFont.setColor(Font.COLOR_RED);
		Row headerRow = sheet.createRow(0);
		for (int colIdx = 0; colIdx < headers.length; colIdx++) {
			Cell cell = headerRow.createCell(colIdx);
			richText = workbook.getCreationHelper().createRichTextString(headers[colIdx]);
			richText.applyFont(headerFont);
			cell.setCellValue(richText);
		}

		int rowNum = 1;
		for (Object[] record : reportData) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : record) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Float) {
					cell.setCellValue((Float) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				} else if (field instanceof Boolean) {
					cell.setCellValue((Boolean) field);
				}
			}
		}

		try {
			outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<ActivityReport> listReports(Long clientId, Long userId) {
		LOG.info("listReports - " + " " + clientId + " " + userId);
		return null;
	}

	@Override
	public List<ContextTax> saveContextTax(ContextTax tax, ContextRateId taxId, Long userId) {
		LOG.info("saveContextTax - " + taxId + " " + userId);
		
		if(taxId != null) {
			tax.setId(taxId);
			update(tax, userId);
		} else {
			insert(tax, userId);
		}
		
		return listContextTaxes(taxId.getClientId());
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<ContextTax> listContextTaxes(Long clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double computerElc(Booking booking, Long userId) {
		LOG.info("computerElc - " + booking + " " + userId);
		
		Tax excessLuggageTax = (Tax) find(new RateId(booking.getFlight().getAirplane().getAirline().getId(), "ELC"), Tax.class);
		double amount = 0.0;
		double excessLuggageWeight = 0.0;
		for(Ticket t : booking.getTickets()) {
			excessLuggageWeight = t.getPassenger().getLuggageWeight() > 23 ? (t.getPassenger().getLuggageWeight() - 23) : 0;
			amount += (excessLuggageTax.getFixedRate() * excessLuggageWeight);
		}
		
		return amount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashDeposit> listDeposits(Date startDate, Date endDate, Long clientId, DepositStatus depositStatus, boolean exportData) {
		LOG.info("listDeposits - " + startDate + " " + endDate + " " + clientId + " " + depositStatus + " " + exportData);
		
		if(startDate == null) {
			startDate = new Date();
		}
		if(endDate == null) {
			endDate = new Date();
		}
		StringBuilder qry = new StringBuilder("select o from CashDeposit o where o.depositDate between :startDate and :endDate");
		
		if(depositStatus != null) {
			qry.append(" and o.depositStatus = :depositStatus");
		}
		Query q = entityManager.createQuery(qry.toString());
		
		q.setParameter("startDate", startDate);
		q.setParameter("endDate", endDate);
		if(depositStatus != null) {
			q.setParameter("depositStatus", depositStatus);
		}
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Set<Organization> listPostpaidCustomers() {
		LOG.info("listPostpaidCustomers - ");
		
		Query q = entityManager.createQuery(LIST_POSTPAID_USERS);
		
//		q.setParameter("airlineId", airlineId);
		
		List<Long> userIds = q.getResultList();
		
		TreeSet<Organization> customers = new TreeSet<>();
		
		for(Long uid : userIds) {
			customers.add(((Personnel) find(uid, Personnel.class)).getOrganization());
		}
		
		return customers;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Set<Organization> listDepositCustomers() {
		LOG.info("listDepositCustomers - ");
		
		Query q = entityManager.createQuery(LIST_DEPOSIT_USERS);
		
//		q.setParameter("airlineId", airlineId);
		
		List<Long> userIds = q.getResultList();
		
		TreeSet<Organization> customers = new TreeSet<>();
		
		for(Long uid : userIds) {
			customers.add(((Personnel) find(uid, Personnel.class)).getOrganization());
		}
		
		return customers;
	}

	@Override
	public CashDeposit saveCashDeposit(CashDeposit cashDeposit, Long clientId, Long userId) {
		LOG.info("saveCashDeposit - " + clientId + " " + userId);
		
		Customer customer = (Customer) find(clientId, Customer.class);
		
		cashDeposit.setCustomer(customer);
		
		insert(cashDeposit, userId);
		
		return cashDeposit;
		
	}

	@Override
	public CashDeposit approveCashDeposit(Long depositId, Long userId) {
		LOG.info("approveCashDeposit - " + depositId + " " + userId);
		
		CashDeposit cashDeposit = (CashDeposit) find(depositId, CashDeposit.class);
		cashDeposit.setApprovalDate(new Date());
		cashDeposit.setDepositStatus(DepositStatus.CLEARED);
		
		update(cashDeposit, userId);
		
		Customer customer = cashDeposit.getCustomer();
		customer.setDepositAmount(customer.getDepositAmount() + cashDeposit.getAmount());
		update(customer, userId);
		
		return cashDeposit;
		
	}

	@Override
	public CashDeposit cancelCashDeposit(Long depositId, Long userId) {
		LOG.info("cancelCashDeposit - " + depositId + " " + userId);
		
		CashDeposit cashDeposit = (CashDeposit) find(depositId, CashDeposit.class);
		cashDeposit.setCancellationDate(new Date());
		cashDeposit.setDepositStatus(DepositStatus.CANCELED);
		
		update(cashDeposit, userId);
		
		return cashDeposit;
		
	}

}