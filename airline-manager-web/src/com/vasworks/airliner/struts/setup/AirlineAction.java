package com.vasworks.airliner.struts.setup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Address;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountAction.
 */
public class AirlineAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AirlineAction.class);
	
	private Long airlineId;
	
	private Airline entity;
	
	private String countryCode;
	
	private String currencyCode;
	
	private List<Airline> airlines;
	
	
	/* (non-Javadoc)
	 * @see com.vasworks.struts.BaseAction#prepare()
	 */
	@Override
	@SkipValidation
	public void prepare() {
		LOG.debug("prepare");
		entity = new Airline();
		entity.setAddress(new Address());
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SkipValidation
	public String execute() {
		LOG.debug("execute()");
		String forwardId = INPUT;

		session.remove("airline_id");
		session.remove("airline");
		
		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "countryCode", message = "'Country' is required."),
			@RequiredStringValidator(fieldName = "currencyCode", message = "'Currency' is required."),
			@RequiredStringValidator(fieldName = "entity.organizationName", message = "'Organization Name' is required."),
			@RequiredStringValidator(fieldName = "entity.airlineCode", message = "'Airline Code' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryPhone", message = "'Primary Phone' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryEmail", message = "'Primary Email' is required."),
			@RequiredStringValidator(fieldName = "entity.address.addressLine1", message = "'Line 1' is required."),
			@RequiredStringValidator(fieldName = "entity.address.city", message = "'City' is required."),
			@RequiredStringValidator(fieldName = "entity.address.state", message = "'State' is required.")
		},
		emails = {
			@EmailValidator(fieldName = "entity.primaryEmail", message = "'Primary Email' must be a valid email address.")	,
			@EmailValidator(fieldName = "entity.secondaryEmail", message = "'Secondary Email' must be a valid email address.")
		}
	)
	public String save() {
		LOG.debug("save()");

		operatorService.saveAirline(airlineId, entity, currencyCode, countryCode, getUserId());
			
//		countryCode = null;
//		currencyCode = null;
//		airlineId = null;
		session.remove("airline_id");
		session.remove("airline");
			
		addActionMessage("Airline successfully saved.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		LOG.debug("list()");
		String forwardId = SUCCESS;
		try {
			airlines = (List<Airline>) operatorService.list(Airline.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forwardId;
	}
	
	public String select() {
		if(airlineId != null) {
			entity = (Airline) operatorService.find(airlineId, Airline.class);
			
			countryCode = entity.getAddress().getCountry().getCountryCode();
			currencyCode = entity.getCurrency().getCurrencyCode();
			airlineId = entity.getId();
			
			session.put("airline_id", airlineId);
			session.put("airline", entity);
		}
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new Airline();
		countryCode = null;
		currencyCode = null;
		airlineId = null;
		session.remove("airline_id");
		session.remove("airline");
		
		return SUCCESS;
	}
	
	public Airline getEntity() {
		return entity;
	}

	public void setEntity(Airline entity) {
		this.entity = entity;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public Airline getAirline() {
		return entity;
	}

	public void setAirline(Airline organization) {
		this.entity = organization;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public List<Airline> getAirlines() {
		return airlines;
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountryLov() {
		return (List<Country>) operatorService.list(Country.class);
	}

	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyLov() {
		return (List<Currency>) operatorService.list(Currency.class);
	}
}
