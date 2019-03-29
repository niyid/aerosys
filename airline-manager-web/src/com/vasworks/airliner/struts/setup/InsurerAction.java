package com.vasworks.airliner.struts.setup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.InsuranceIssuer;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountAction.
 */
public class InsurerAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(InsurerAction.class);
	
	private Long insurerId;
	
	private InsuranceIssuer entity;
	
	private String countryCode;
	
	private String currencyCode;
	
	private List<InsuranceIssuer> insurers;
	
	
	/* (non-Javadoc)
	 * @see com.vasworks.struts.BaseAction#prepare()
	 */
	@Override
	@SkipValidation
	public void prepare() {
		LOG.debug("prepare");
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SkipValidation
	public String execute() {
		LOG.debug("execute()");
		String forwardId = INPUT;

		session.remove("insurer_id");
		
		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.organizationName", message = "'Organization Name' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryPhone", message = "'Primary Phone' is required."),
			@RequiredStringValidator(fieldName = "entity.contactInfo.primaryEmail", message = "'Primary Email' is required."),
			@RequiredStringValidator(fieldName = "entity.address.addressLine1", message = "'Line 1' is required."),
			@RequiredStringValidator(fieldName = "countryCode", message = "'Country' is required."),
			@RequiredStringValidator(fieldName = "currencyCode", message = "'Currency' is required."),
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

		operatorService.saveOrganization(insurerId, entity, currencyCode, countryCode, getUserId());
			
		countryCode = null;
		currencyCode = null;
		insurerId = null;
		session.remove("insurer_id");
			
		addActionMessage("Insurance issuer successfully saved.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		LOG.debug("list()");
		String forwardId = SUCCESS;
		try {
			insurers = (List<InsuranceIssuer>) operatorService.list(InsuranceIssuer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forwardId;
	}
	
	public String select() {
		if(insurerId != null) {
			entity = (InsuranceIssuer) operatorService.find(insurerId, InsuranceIssuer.class);
			
			countryCode = entity.getAddress().getCountry().getCountryCode();
			currencyCode = entity.getCurrency().getCurrencyCode();
			insurerId = entity.getId();
			
			session.put("insurer_id", insurerId);
		}
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new InsuranceIssuer();
		countryCode = null;
		currencyCode = null;
		insurerId = null;
		session.remove("insurer_id");
		
		return SUCCESS;
	}
	
	public InsuranceIssuer getEntity() {
		return entity;
	}

	public void setEntity(InsuranceIssuer entity) {
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

	public Long getInsurerId() {
		return insurerId;
	}

	public void setInsurerId(Long airlineId) {
		this.insurerId = airlineId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public List<InsuranceIssuer> getInsurers() {
		return insurers;
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
