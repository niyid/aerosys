package com.vasworks.airliner.struts.operator;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.ContextRateId;
import com.vasworks.airliner.model.ContextTax;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

public class ContextTaxAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private ContextRateId taxId;
	
	private ContextTax entity;
	
	private List<ContextTax> taxes;
	
	private String rateCode;
	
	private Long clientId;
	
	@Override
	public void prepare() {
		entity = new ContextTax();
		if(taxId == null) {
			entity.setId(new ContextRateId(getAirlineId(), rateCode, clientId));
		}
	}

	@Override
	public String execute() {
		session.put("context_tax_id", null);
		return super.execute();
	}
	
	public String load() {
		if(taxId != null) {
			if(entity == null) {
				entity = (ContextTax) operatorService.find(taxId, ContextTax.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required."),
				@RequiredStringValidator(fieldName = "rateCode", message = "'Rate Code' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "clientId", message = "'Client' is required.")
			}
		)
	public String save() {
		operatorService.saveContextTax(entity, new ContextRateId(getAirlineId(), rateCode, clientId), getUserId());
		
		entity = new ContextTax();
		taxId = null;
		rateCode = null;
		clientId = null;
		
		session.put("context_tax_id", null);
		
		addActionMessage("Tax successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(entity.getRatePercentage() == null && entity.getFixedRate() == null) {
			addFieldError("entity.ratePercentage", "One of 'Rate Percent' or 'Fixed Rate' must have a value.");
		}
		
		if(entity.getRatePercentage() != null && entity.getFixedRate() != null) {
			addFieldError("entity.ratePercentage", "ONLY one of 'Rate Percent' or 'Fixed Rate' can be used.");
		}
		
		if(entity.getRatePercentage() != null && entity.getRatePercentage() > 100.0) {
			addFieldError("entity.ratePercentage", "'Rate Percent' cannot exceed 100%.");
		}
	}
	
	public String initNew() {
		entity = new ContextTax();
		
		session.put("context_tax_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(taxId != null) {
			entity = (ContextTax) operatorService.find(taxId, ContextTax.class);
			rateCode = entity.getRateCode();
			
			session.put("context_tax_id", taxId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(taxId != null) {
			operatorService.remove(taxId, Tax.class);
		}
		
		return list();
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		if (hasAny("POST_ETL")) {
			taxes = operatorService.listContextTaxes(getOrganizationId());
		} else if (Authorize.hasAny("ROLE_ADMIN,ROLE_FINANCE,ROLE_OPERATOR")) {
			taxes = (List<ContextTax>) operatorService.list(ContextTax.class);
		}

		return SUCCESS;
	}

	public ContextRateId getTaxId() {
		return taxId;
	}

	public void setTaxId(ContextRateId taxId) {
		this.taxId = taxId;
	}

	public ContextTax getEntity() {
		return entity;
	}

	public void setEntity(ContextTax entity) {
		this.entity = entity;
	}

	public List<ContextTax> getTaxes() {
		return taxes;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
}
