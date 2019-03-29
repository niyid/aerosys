package com.vasworks.airliner.struts;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.BillingCycle;
import com.vasworks.airliner.model.BillingCycle.CycleRange;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Customer.CustomerType;
import com.vasworks.airliner.model.Flight.FlightFrequency;
import com.vasworks.airliner.model.Passenger.MinorPassenger;
import com.vasworks.airliner.model.Gender;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.airliner.model.TrainingDoctor;
import com.vasworks.airliner.model.TrainingHandler;
import com.vasworks.airliner.model.TrainingPilot;
import com.vasworks.airliner.model.TrainingSupervisor;
import com.vasworks.airliner.model.TrainingType;
import com.vasworks.airliner.model.UserTitle;
import com.vasworks.airliner.service.OperatorService;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;
import com.vasworks.struts.BaseAction;

public class OperatorAction extends BaseAction implements SessionAware {
	public static final Log LOG = LogFactory.getLog(OperatorAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7587429391502948285L;
	
	public static NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
	static {
		CURRENCY_FORMAT.setCurrency(java.util.Currency.getInstance("NGN"));
	}

	protected OperatorService operatorService;

	protected Map<String, Object> session;
	
	private List<BookingStep> bookingSteps;
	
	private boolean showPay;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}
	
	public Long getUserId() {
		Long clientId = (Long) session.get("client_id");
		return session != null ? (clientId != null ? clientId : (Long) session.get("basket_id")) : null;
	}
	
	public String getBasketId() {
		return session != null ? (String) session.get("basket_id") : null;
	}
	
	public Country getDefaultCountry() {
		return operatorService.fetchDefaultCountry();
	}
	
	public Country getCountry() {
		return getOrganization().getAddress().getCountry();
	}
	
	public Currency getDefaultCurrency() {
		return getUserId() != null ? operatorService.findDefaultCurrency(getUserId()) : operatorService.fetchDefaultCountry().getCurrency();
	}
	
	public Currency getUsdCurrency() {
		return (Currency) operatorService.find("USD", Currency.class);
	}
	
	public Currency getCurrency() {
		return getOrganization() != null ? getOrganization().getCurrency() : operatorService.fetchDefaultCountry().getCurrency();
	}

	public String getAirlineCode() {
		return (String) session.get("airline_code");
	}
	
	public Long getOrganizationId() {
		return (Long) session.get("org_id");
	}
	
	public Long getAirlineId() {
		return (Long) session.get("airline_id");
	}
	
	public Airline getDefaultAirline() {
		return (Airline) operatorService.find(getAirlineId(), Airline.class);
	}
	
	public boolean isShowPay() {
		return showPay;
	}
	
	public List<Airport> getAirportLov() {
		return operatorService.listAirports(getDefaultCountry().getCountryCode());
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountryLov() {
		return (List<Country>) operatorService.list(Country.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyLov() {
		return (List<Currency>) operatorService.list(Currency.class);
	}
	
	public Organization getOrganization() {
		return getPersonnel() != null ? getPersonnel().getOrganization() : null;
	}
	
	public Customer getCustomer() {
		return getOrganization() != null ? (Customer) operatorService.find(getOrganization().getId(), Customer.class) : null;
	}
	
	public Personnel getPersonnel() {
		return getUser() != null ? (Personnel) operatorService.find(getUser().getId(), Personnel.class) : null;
	}
	
	protected Class<?> fetchTrainerClass(TrainingType trainingType) {
		Class<?> trainerClass = TrainingHandler.class;
		
		if(trainingType != null) {
			switch (trainingType) {
			case CRM:
				trainerClass = TrainingSupervisor.class;
				break;
			case EandS:
				trainerClass = TrainingSupervisor.class;
				break;
			case DG:
				trainerClass = TrainingSupervisor.class;
				break;
			case FED:
				trainerClass = TrainingSupervisor.class;
				break;
			case FIRST_AID:
				trainerClass = TrainingSupervisor.class;
				break;
			case IRR:
				trainerClass = TrainingPilot.class;
				break;
			case MEDICALS:
				trainerClass = TrainingDoctor.class;
				break;
			case LINE_CHECK:
				trainerClass = TrainingPilot.class;
				break;
			case OPC:
				trainerClass = TrainingPilot.class;
				break;
			case SIM:
				trainerClass = TrainingPilot.class;
				break;
			}
		}
		
		return trainerClass;
	}
	
	public Gender[] getGenderLov() {
		return Gender.values();
	}
	
	public SeatClass[] getSeatClassLov() {
		return SeatClass.values();
	}
	
	
	public MinorPassenger[] getMinorLov() {
		return MinorPassenger.values();
	}
	
	public FlightFrequency[] getFlightFrequencyLov() {
		return FlightFrequency.values();
	}
	
	public CustomerType[] getCustomerTypeLov() {
		return CustomerType.values();
	}
	
	public CycleRange[] getBillingCycleLov() {
		return CycleRange.values();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTitle> getUserTitleLov() {
		return (List<UserTitle>) operatorService.list(UserTitle.class);
	}
	
	public List<Customer> getCustomerLov() {
		return getPersonnel() != null ? (List<Customer>) operatorService.listCustomers(getOrganizationId()) : operatorService.listCustomers(getAirlineId());
	}
	
	public Set<Organization> getPostpaidCustomerLov() {
		return operatorService.listPostpaidCustomers();
	}
	
	public Set<Organization> getDepositCustomerLov() {
		return operatorService.listDepositCustomers();
	}
	
	@SuppressWarnings("unchecked")
	public List<Airline> getAirlineLov() {
		return (List<Airline>) operatorService.list(Airline.class);
	}
	
	public void initBookingSteps() {
		bookingSteps = new ArrayList<BookingStep>(4);
		bookingSteps.add(new BookingStep(1, "Search", "active"));
		bookingSteps.add(new BookingStep(2, "Book", "disabled"));
		if (hasAny("POST_ETL,PRE_ETL_PREPAID")) {
			showPay = true;
			bookingSteps.add(new BookingStep(3, "Pay", "disabled"));
		} else {
			showPay = false;
			bookingSteps.add(new BookingStep(3, "Confirm", "disabled"));
		}
		bookingSteps.add(new BookingStep(4, "Summary", "disabled"));
	}
	
	public void stepCancel() {
		bookingSteps = new ArrayList<BookingStep>(4);
		bookingSteps.add(new BookingStep(1, "Search", "active"));
		bookingSteps.add(new BookingStep(2, "Book", "disabled"));
		if (hasAny("POST_ETL,PRE_ETL_PREPAID")) {
			showPay = true;
			bookingSteps.add(new BookingStep(3, "Pay", "disabled"));
		} else {
			showPay = false;
			bookingSteps.add(new BookingStep(3, "Confirm", "disabled"));
		}
		bookingSteps.add(new BookingStep(4, "Summary", "disabled"));
	}
	
	public void stepBook() {
		bookingSteps = new ArrayList<BookingStep>(4);
		bookingSteps.add(new BookingStep(1, "Search", "complete"));
		bookingSteps.add(new BookingStep(2, "Book", "active"));
		if (hasAny("POST_ETL,PRE_ETL_PREPAID")) {
			showPay = true;
			bookingSteps.add(new BookingStep(3, "Pay", "disabled"));
		} else {
			showPay = false;
			bookingSteps.add(new BookingStep(3, "Confirm", "disabled"));
		}
		bookingSteps.add(new BookingStep(4, "Summary", "disabled"));
	}
	
	public void stepConfirm() {
		bookingSteps = new ArrayList<BookingStep>(4);
		bookingSteps.add(new BookingStep(1, "Search", "complete"));
		bookingSteps.add(new BookingStep(2, "Book", "complete"));
		if (hasAny("POST_ETL,PRE_ETL_PREPAID")) {
			showPay = true;
			bookingSteps.add(new BookingStep(3, "Pay", "active"));
		} else {
			showPay = false;
			bookingSteps.add(new BookingStep(3, "Confirm", "active"));
		}
		bookingSteps.add(new BookingStep(4, "Summary", "disabled"));
	}
	
	public void stepSummary() {
		bookingSteps = new ArrayList<BookingStep>(4);
		bookingSteps.add(new BookingStep(1, "Search", "complete"));
		bookingSteps.add(new BookingStep(2, "Book", "complete"));
		if (hasAny("POST_ETL,PRE_ETL_PREPAID")) {
			showPay = true;
			bookingSteps.add(new BookingStep(3, "Pay", "complete"));
		} else {
			showPay = false;
			bookingSteps.add(new BookingStep(3, "Confirm", "complete"));
		}
		bookingSteps.add(new BookingStep(4, "Summary", "active"));
	}
	
	public boolean isOverdue() {
		boolean overdue = false;
		if (hasAny("PRE_ETL_POSTPAID")) {
			overdue = operatorService.checkInvoicesOverdue(getUser().getId());
		}
		
		return overdue;
	}
	
	public List<Invoice> getOverdueInvoices() {
		List<Invoice> invoices = new ArrayList<>();
		if (hasAny("PRE_ETL_POSTPAID")) {
			invoices = operatorService.listOverdueInvoices(getUser().getId());
		}
		
		return invoices;
	}
	
	public boolean isBalanceLow() {
		boolean balanceLow = false;
		if (hasAny("PRE_ETL_DEPOSIT")) {
			balanceLow = operatorService.checkBalanceLow(getOrganizationId());
		}
		
		return balanceLow;
		
	}
	
	public List<BookingStep> getBookingSteps() {
		return bookingSteps;
	}
	
	@SkipValidation
	public boolean hasAny(String requestedAuthorities) {
		String auths = (String) session.get("roles");
		LOG.info(getClass().getName() + "@SESSION_AUTHS - " + auths);
		if(auths != null) {
			String[] actual = auths.split(",");
			String[] requested = requestedAuthorities.split(",");
			for (String r : requested) {
				for(String a : actual) {
					LOG.info(getClass().getName() + "@SESSION_AUTHS - testing " + auths + "-has-" + r);
					if (a.trim().equals(r.trim())) {
						LOG.info(getClass().getName() + "@SESSION_AUTHS - " + auths + "-has-" + r);
						return true;
					}
				}
			}
		}
		return false;
	}
}
