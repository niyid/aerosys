package com.vasworks.airliner.struts.setup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.LicensingAuthority;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountAction.
 */
public class AuthorityAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AuthorityAction.class);
	
	private Long authorityId;
	
	private LicensingAuthority entity;
	
	private String countryCode;
	
	private String currencyCode;
	
	private List<LicensingAuthority> authorities;
	
	
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

		session.remove("authority_id");
		
		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.organizationName", message = "'Organization Name' is required."),
			@RequiredStringValidator(fieldName = "entity.authorityAcronym", message = "'Acronym' is required."),
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

		operatorService.saveOrganization(authorityId, entity, currencyCode, countryCode, getUserId());
			
		countryCode = null;
		currencyCode = null;
		authorityId = null;
		session.remove("authority_id");
			
		addActionMessage("Airline successfully saved.");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		LOG.debug("list()");
		String forwardId = SUCCESS;
		try {
			authorities = (List<LicensingAuthority>) operatorService.list(LicensingAuthority.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forwardId;
	}
	
	public String select() {
		if(authorityId != null) {
			entity = (LicensingAuthority) operatorService.find(authorityId, LicensingAuthority.class);
			
			countryCode = entity.getAddress().getCountry().getCountryCode();
			currencyCode = entity.getCurrency().getCurrencyCode();
			authorityId = entity.getId();
			
			session.put("authority_id", authorityId);
		}
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new LicensingAuthority();
		countryCode = null;
		currencyCode = null;
		authorityId = null;
		session.remove("authority_id");
		
		return SUCCESS;
	}
	
	public LicensingAuthority getEntity() {
		return entity;
	}

	public void setEntity(LicensingAuthority entity) {
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

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long airlineId) {
		this.authorityId = airlineId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public List<LicensingAuthority> getAuthorities() {
		return authorities;
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
