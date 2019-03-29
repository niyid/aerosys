package com.vasworks.airliner.service;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vasworks.airliner.model.Activity;
import com.vasworks.airliner.model.ActivityReport;
import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.AirplaneMake;
import com.vasworks.airliner.model.AirplaneModel;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.CashDeposit;
import com.vasworks.airliner.model.CashDeposit.DepositStatus;
import com.vasworks.airliner.model.City;
import com.vasworks.airliner.model.ContextRateId;
import com.vasworks.airliner.model.ContextTax;
import com.vasworks.airliner.model.CrewActivity;
import com.vasworks.airliner.model.CrewMember;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.Flight.FlightStatus;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSchedule;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.Invoice.InvoiceStatus;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.Route;
import com.vasworks.airliner.model.RoutePrice;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.SeatId;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.model.Ticket;
import com.vasworks.airliner.model.Training;
import com.vasworks.airliner.model.TrainingDoctor;
import com.vasworks.airliner.model.TrainingPilot;
import com.vasworks.airliner.model.TrainingSupervisor;
import com.vasworks.airliner.model.TscRemittance;
import com.vasworks.airliner.model.TscRemittance.RemittanceStatus;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;

public interface OperatorService extends TravelerService {
	static enum ActivityPeriod {DAY_1, DAY_7, DAY_30, DAY_90};
	
	static enum CalendarPeriod {WEEK, MONTH};
	
	static final String LIST_UNSENT_TICKETS = "select o from Ticket o where o.filePath is not null and o.ticketStatus = :newStatus and (createDate < :filterDate or createDate is null)";
	
	static final String LIST_INVOICABLE_TICKETS = "select o from Ticket o where o.booking.paid = true and o.booking.bookingStatus = :confirmedStatus and o.booking.flight.departureTime < current_timestamp";
	
	static final String LIST_ORGANIZATION_PERSONNEL = "select o from Personnel o where o.organization.id = :organizationId";
	
	static final String LIST_TRAINERS_OF_TYPE = "select o from TrainingHandler o where type(p) = :classType";
	
	static final String LIST_CURRENT_TRAINING = "select o from Training o where o.trainingType = :trainingType and o.crewMember.id = :crewMemberId order by o.completionDate desc";
	
	static final String FIND_FLIGHT_ROUTE = "select o from Route o where o.destinationAirport.airportCode = :departureAirportCode and o.sourceAirport.airportCode = :arrivalAirportCode"; 
			
	static final String FIND_OVERLAPPING_ACTIVITIES = "select o from CrewActivity o where :crewMember member of o.flightCrew and (:startTime between o.startTime and o.endTime or :endTime between o.startTime and o.endTime or o.startTime between :startTime and :endTime or o.endTime between :startTime and :endTime)";
	
	static final String FIND_OVERLAPPING_FLIGHTS = "select o from Flight o where :crewMember member of o.flightCrew and (:startTime between o.departureTime and o.arrivalTime or :endTime between o.departureTime and o.arrivalTime or o.departureTime between :startTime and :endTime or o.arrivalTime between :startTime and :endTime)";
	
	static final String LIST_WEEK_ACTIVITIES = "select o from CrewActivity o where :crewMember member of o.flightCrew and o.startTime between :weekStartDate and :weekEndDate";
	
	static final String LIST_WEEK_FLIGHTS = "select o from Flight o where :crewMember member of o.flightCrew and o.departureTime between :weekStartDate and :weekEndDate";
	
	static final String LIST_MONTH_ACTIVITIES = "select o from CrewActivity o where :crewMember member of o.flightCrew and month(o.startTime) = month(current_date)";
	
	static final String LIST_MONTH_FLIGHTS = "select o from Flight o where :crewMember member of o.flightCrew and month(o.departureTime) = month(current_date)";
	
	static final String LIST_PERIOD_ACTIVITY_RANGE = "select o.startTime, o.endTime from CrewActivity o where :crewMember member of o.flightCrew and o.startTime between :periodStartDate and :periodEndDate";
	
	static final String LIST_PERIOD_FLIGHT_RANGE = "select o.departureTime, o.arrivalTime from Flight o where :crewMember member of o.flightCrew and o.departureTime between :periodStartDate and :periodEndDate";
	
	static final String LIST_ORGANIZATION_ROUTE_PRICE = "select o from RoutePrice o where o.id.customerId = :organizationId";
	
	static final String LIST_POSTPAID_USERS = "select o.id from Customer o where o.customerType = 1 or o.customerType = 3";
	
	static final String LIST_DEPOSIT_USERS = "select o.id from Customer o where o.customerType = 3";
	
	static final String LIST_CREW_MEMBERS = "select o from CrewMember o where o.organization.id = :organizationId";

	
	/**
	 * 
	 * @param flight
	 * @param flightNumber
	 * @param airlineId
	 * @param flightDate
	 * @param departureAirportCode
	 * @param arrivalAirportCode
	 * @param aircraftReg
	 * @param currencyCode
	 * @param selTaxIds
	 * @param selRebateIds
	 * @param selThruFlightIds
	 * @param userId
	 * @return
	 */
	List<Flight> saveFlight(Flight flight, Integer flightNumber, Long airlineId, Date flightDate, String departureAirportCode, String arrivalAirportCode, String aircraftReg, String currencyCode, RateId[] selTaxIds, RateId[] selRebateIds, String[] selThruFlightIds, Long userId);
	
	/**
	 * 
	 * @param flightId
	 * @param flightStatus
	 * @param userId
	 * @return
	 */
	Flight changeFlightStatus(FlightId flightId, FlightStatus flightStatus, Long userId);

	/**
	 * 
	 * @param airlineId
	 * @param airline
	 * @param currencyCode
	 * @param countryCode
	 * @param userId
	 * @return
	 */
	List<Airline> saveAirline(Long airlineId, Airline airline, String currencyCode, String countryCode, Long userId);
	
	/**
	 * 
	 * @param organizationId
	 * @param organization
	 * @param currencyCode
	 * @param countryCode
	 * @param userId
	 * @return
	 */
	List<Organization> saveOrganization(Long organizationId, Organization organization, String currencyCode, String countryCode, Long userId);

	/**
	 * 
	 * @param route
	 * @param routeId
	 * @param selAirportCodes
	 * @param selRebateIds 
	 * @param selTaxIds 
	 * @param userId
	 * @return
	 */
	List<Route> saveRoute(Route route, Long routeId, String[] selAirportCodes, RateId[] selTaxIds, RateId[] selRebateIds, Long userId);
	
	/**
	 * 
	 * @param airport
	 * @param airportCode
	 * @param cityId
	 * @param userId
	 * @return
	 */
	List<Airport> saveAirport(Airport airport, String airportCode, Long cityId, Long userId);
	
	/**
	 * 
	 * @param airplane
	 * @param aircraftReg
	 * @param airlineId 
	 * @param modelName 
	 * @param userId
	 * @return
	 */
	List<Airplane> saveAirplane(Airplane airplane, String aircraftReg, Long airlineId, String modelName, Long userId);
	
	/**
	 * 
	 * @param airplaneMake
	 * @param makeName
	 * @param userId
	 * @return
	 */
	List<AirplaneMake> saveAirplaneMake(AirplaneMake airplaneMake, String makeName, Long userId);

	/**
	 * 
	 * @param airplaneModel
	 * @param modelName
	 * @param makeName
	 * @param userId
	 * @return
	 */
	List<AirplaneModel> saveAirplaneModel(AirplaneModel airplaneModel, String modelName, String makeName, Long userId);

	/**
	 * 
	 * @param tax
	 * @param rateCode
	 * @param airlineId
	 * @param userId
	 * @return
	 */
	List<Tax> saveTax(Tax tax, String rateCode, Long airlineId, Long userId);

	/**
	 * 
	 * @param seat
	 * @param seatId
	 * @param userId
	 * @return
	 */
	List<Seat> saveSeat(Seat seat, SeatId seatId, Long userId);

	/**
	 * 
	 * @param city
	 * @param cityId
 	 * @param countryStateId
	 * @param userId
	 * @return
	 */
	List<City> saveCity(City city, Long cityId, Long countryStateId, Long userId);
	
	/**
	 * 
	 * @param rebate
	 * @param rebateId
	 * @param userId
	 * @return
	 */
	List<Rebate> saveRebate(Rebate rebate, RateId rebateId, Long userId);

	/**
	 * 
	 * @param trainingId
	 * @param entity
	 * @param crewMemberId
	 * @param trainingHandlerId
	 * @param userId
	 */
	List<Training> saveTraining(Long trainingId, Training entity, Long crewMemberId, Long trainingHandlerId, Long userId);

	/**
	 * 
	 * @param personnelId
	 * @param personnel
	 * @param organizationId
	 * @param photo
	 * @param userId
	 */
	void savePersonnel(Long personnelId, Personnel personnel, Long organizationId, File photo, Long userId);
	
	/**
	 * 
	 * @param activityId
	 * @param activity
	 * @param selCrewMemberIds
	 * @param userId
	 */
	void saveCrewActivity(Long activityId, CrewActivity activity, Long[] selCrewMemberIds, Long userId);

	/**
	 * 
	 * @param flightId
	 * @param selCrewMemberIds
	 * @param userId
	 */
	void saveFlightCrew(FlightId flightId, Long[] selCrewMemberIds, Long userId);

	/**
	 * 
	 * @param trainingHandlerId
	 * @param entity
	 * @param uploadedFile
	 * @param fileDescription
	 * @param userId
	 * @return
	 */
	List<TrainingSupervisor> saveTrainingSupervisor(Long trainingHandlerId, TrainingSupervisor entity, File uploadedFile, String fileDescription, Long userId);

	/**
	 * 
	 * @param trainingHandlerId
	 * @param entity
	 * @param userId
	 * @return
	 */
	List<TrainingPilot> saveTrainingPilot(Long trainingHandlerId, TrainingPilot entity, Long userId);

	/**
	 * 
	 * @param trainingHandlerId
	 * @param entity
	 * @param userId
	 * @return
	 */
	List<TrainingDoctor> saveTrainingDoctor(Long trainingHandlerId, TrainingDoctor entity, Long userId);
	
	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<Ticket> listTicketSales(FlightId flightId);
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<Ticket> listTicketSales(Date fromDate, Date toDate);
	
	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<Seat> listUnsoldSeats(FlightId flightId);
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<Seat> listUnsoldSeats(Date fromDate, Date toDate);
	
	/**
	 * 
	 * @param flightId
	 * @return
	 */
	Double sumTicketSales(FlightId flightId);
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Double sumTicketSales(Date fromDate, Date toDate);
	
	/**
	 * 
	 * @param tickets
	 * @return
	 */
	Double sumTicketSales(List<Ticket> tickets);
	
	/**
	 * 
	 * @param flightId
	 * @return
	 */
	Double[] flightIncomeAndLoss(FlightId flightId);
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Double[] flightIncomeAndLoss(Date fromDate, Date toDate);
	
	/**
	 * 
	 * @param unsoldSeats
	 * @return
	 */
	Double sumFlightLosses(List<Seat> unsoldSeats);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<Passenger> listCheckedPassengers(FlightId flightId);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<Passenger> listUncheckedPassengers(FlightId flightId);

	/**
	 * 
	 * @param makeName
	 * @return
	 */
	List<AirplaneModel> listAirplaneModels(String makeName);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<FlightSeat> listFlightSeats(FlightId flightId);
	
	/**
	 * 
	 * @param crewMemberId
	 */
	List<Training> listTrainings(Long crewMemberId);

	/**
	 * 
	 * @param classType
	 * @return
	 */
	List<?> listTrainers(Class<?> classType);
	
	/**
	 * 
	 * @param organizationId
	 * @return
	 */
	List<Personnel> listPersonnel(Long organizationId);

	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	List<CrewMember> listCrewMembers(Long airlineId);
	
	/**
	 * 
	 * @param relatedFlightId
	 * @return
	 */
	List<CrewActivity> listFlightRelatedActivities(FlightId relatedFlightId);
	
	/**
	 * 
	 * @param crewMemberId
	 * @param calendarPeriod
	 * @return
	 */
	Map<Integer, List<Activity>> listAllActivitySchedule(Long crewMemberId, CalendarPeriod calendarPeriod);

	/**
	 * 
	 * @param selCrewMemberIds
	 */
	String validateCrewSelection(Long[] selCrewMemberIds);
	
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param crewMemberIds
	 * @return
	 */
	Map<Activity, CrewMember> validateActivityOverlap(Date startTime, Date endTime, Long[] crewMemberIds);
	
	/**
	 * 
	 * @param crewMemberIds
	 * @param referenceDate
	 * @return
	 */
	Map<ActivityPeriod, CrewMember> validateWorkLimit(Long[] crewMemberIds, Date referenceDate);

	/**
	 * 
	 * @param flight
	 * @param userId
	 * @return
	 */
	List<FlightSeat> cloneFlightSeats(Flight flight, Long userId);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	List<Booking> listBookings(FlightId flightId);

	/**
	 * 
	 * @param customerId
	 * @param defaultAirlineId 
	 * @param customer
	 * @param countryCode
	 * @param id
	 * @return
	 */
	List<Organization> saveCustomer(Long customerId, Long defaultAirlineId, Customer customer, String countryCode, Long userId);

	/**
	 * 
	 * @param entity
	 * @param userId
	 */
	void saveRoutePrice(RoutePrice routePrice, Long userId);

	/**
	 * 
	 * @param organizationId
	 * @return
	 */
	List<RoutePrice> listRoutePrices(Long organizationId);

	/**
	 * 
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @param invoiceStatus 
	 * @param exportData 
	 * @return
	 */
	List<Object[]> listInvoices(Long organizationId, Date startDate, Date endDate, InvoiceStatus invoiceStatus, boolean exportData);

	/**
	 * 
	 * @param flightSchedule
	 * @param scheduleId
	 * @param departureAirportCode
	 * @param arrivalAirportCode
	 * @param aircraftReg
	 * @param currencyCode
	 * @param selWeekDays
	 * @param selTaxIds
	 * @param selRebateIds
	 * @param userId
	 * @return
	 */
	FlightSchedule saveFlightSchedule(FlightSchedule flightSchedule, Long scheduleId, String departureAirportCode, String arrivalAirportCode,
			String aircraftReg, String currencyCode, int[] selWeekDays, RateId[] selTaxIds, RateId[] selRebateIds, Long userId);


	/**
	 * 
	 * @param airlineId
	 * @return
	 */
	List<FlightSchedule> listFlightSchedules(Long airlineId);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param remittanceStatus
	 * @param exportData
	 * @return
	 */
	List<TscRemittance> listRemittances(Date startDate, Date endDate, RemittanceStatus remittanceStatus, boolean exportData);

	/**
	 * 
	 * @param scheduleId
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	List<Flight> generateFlights(Long scheduleId, Long userId) throws Exception;
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param reportId
	 * @param customerId
	 * @param userId
	 * @return
	 */
	List<Object[]> generateReport(Date startDate, Date endDate, Long reportId, Long customerId, Long userId);

	/**
	 * 
	 * @param clientId
	 * @param userId
	 * @return
	 */
	List<ActivityReport> listReports(Long clientId, Long userId);

	/**
	 * 
	 * @param tax
	 * @param taxId
	 * @param userId
	 */
	List<ContextTax> saveContextTax(ContextTax tax, ContextRateId taxId, Long userId);

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	List<ContextTax> listContextTaxes(Long clientId);
	
	/**
	 * 
	 * @param reportId
	 * @param reportData
	 * @param userId
	 * @return
	 */
	String generateReport(Long reportId, List<Object[]> reportData, Long userId);

	/**
	 * 
	 * @param booking
	 * @param userId
	 * @return
	 */
	Double computerElc(Booking booking, Long userId);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param clientId
	 * @param depositStatus
	 * @param exportData
	 * @return
	 */
	List<CashDeposit> listDeposits(Date startDate, Date endDate, Long clientId, DepositStatus depositStatus, boolean exportData);
	
	/**
	 * 
	 * @return
	 */
	Set<Organization> listPostpaidCustomers();
	
	/**
	 * 
	 * @return
	 */
	Set<Organization> listDepositCustomers();

	/**
	 * 
	 * @param entity
	 * @param clientId
	 * @param userId
	 * @return
	 */
	CashDeposit saveCashDeposit(CashDeposit cashDeposit, Long clientId, Long userId);

	/**
	 * 
	 * @param flightId
	 * @return
	 */
	File printFlightManifest(FlightId flightId);

	/**
	 * 
	 * @param depositId
	 * @param userId
	 * @return
	 */
	CashDeposit approveCashDeposit(Long depositId, Long userId);

	/**
	 * 
	 * @param depositId
	 * @param userId
	 * @return
	 */
	CashDeposit cancelCashDeposit(Long depositId, Long userId);

}