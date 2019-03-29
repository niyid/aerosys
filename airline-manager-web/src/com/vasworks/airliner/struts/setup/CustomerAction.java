package com.vasworks.airliner.struts.setup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.BillingCycle;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.ContactInfo;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountAction.
 */
public class CustomerAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CustomerAction.class);
	
	private Long customerId;
	
	private Long defaultAirlineId;
	
	private Customer entity;
	
	private String countryCode;
	
	private List<Customer> customers;
	
	
	/* (non-Javadoc)
	 * @see com.vasworks.struts.BaseAction#prepare()
	 */
	@Override
	@SkipValidation
	public void prepare() {
		LOG.debug("prepare");
		
		entity = new Customer();
		entity.setContactInfo(new ContactInfo());
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SkipValidation
	public String execute() {
		LOG.debug("execute()");
		String forwardId = INPUT;

		session.remove("customer_id");
		session.remove("customer");
		
		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "countryCode", message = "'Country' is required."),
			@RequiredStringValidator(fieldName = "entity.organizationName", message = "'Customer Name' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryPhone", message = "'Primary Phone' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryEmail", message = "'Primary Email' is required."),
			@RequiredStringValidator(fieldName = "entity.address.addressLine1", message = "'Line 1' is required."),
			@RequiredStringValidator(fieldName = "entity.address.city", message = "'City' is required."),
			@RequiredStringValidator(fieldName = "entity.address.state", message = "'State' is required.")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "entity.customerType", message = "'Customer Type' is required.")	
		},
		emails = {
			@EmailValidator(fieldName = "entity.primaryEmail", message = "'Primary Email' must be a valid email address.")	,
			@EmailValidator(fieldName = "entity.secondaryEmail", message = "'Secondary Email' must be a valid email address.")
		}
	)
	public String save() {
		LOG.debug("save()");
		
		if(defaultAirlineId == null) {
			defaultAirlineId = getAirlineId();
		}

		operatorService.saveCustomer(customerId, defaultAirlineId, entity, countryCode, getUserId());
			
		countryCode = null;
		customerId = null;
		session.remove("customer_id");
		session.remove("customer");
		entity = new Customer();
			
		addActionMessage("Customer successfully saved.");
		
		return SUCCESS;
	}
	
	public String list() {
		LOG.debug("list()");
		String forwardId = SUCCESS;
		try {
			customers = operatorService.listCustomers(getOrganizationId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forwardId;
	}
	
	public String select() {
		if(customerId != null) {
			entity = (Customer) operatorService.find(customerId, Customer.class);
			
			if(entity != null) {
				defaultAirlineId = entity.getAirline() != null ? entity.getAirline().getId() : getAirlineId();
				countryCode = entity.getAddress() != null && entity.getAddress().getCountry() != null ? entity.getAddress().getCountry().getCountryCode() : null;
				customerId = entity.getId();
			}
			
			session.put("customer_id", customerId);
			session.put("customer", entity);
		}
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new Customer();
		countryCode = null;
		customerId = null;
		session.remove("customer_id");
		session.remove("customer");
		
		return SUCCESS;
	}
	
	public Customer getEntity() {
		return entity;
	}

	public void setEntity(Customer entity) {
		this.entity = entity;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDefaultAirlineId() {
		return defaultAirlineId;
	}

	public void setDefaultAirlineId(Long defaultAirlineId) {
		this.defaultAirlineId = defaultAirlineId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public List<Customer> getCustomers() {
		return customers;
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
