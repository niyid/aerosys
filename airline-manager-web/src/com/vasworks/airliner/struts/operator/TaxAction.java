package com.vasworks.airliner.struts.operator;

import java.util.Collection;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.struts.OperatorAction;

public class TaxAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private RateId taxId;
	
	private Tax entity;
	
	private Collection<? extends RateInterface> taxes;
	
	private String rateCode;
	
	@Override
	public void prepare() {
		entity = new Tax();
		if(taxId != null) {
			entity.setId(new RateId(getAirlineId(), rateCode));
		}
	}

	@Override
	public String execute() {
		session.put("tax_id", null);
		return super.execute();
	}
	
	public String load() {
		if(taxId != null) {
			if(entity == null) {
				entity = (Tax) operatorService.find(taxId, Tax.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required."),
				@RequiredStringValidator(fieldName = "rateCode", message = "'Rate Code' is required.")
			}
		)
	public String save() {
		operatorService.saveTax(entity, rateCode, getAirlineId(), getUserId());
		
		entity = new Tax();
		taxId = null;
		rateCode = null;
		
		session.put("tax_id", null);
		
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
		entity = new Tax();
		
		session.put("tax_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(taxId != null) {
			entity = (Tax) operatorService.find(taxId, Tax.class);
			rateCode = entity.getRateCode();
			
			session.put("tax_id", taxId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(taxId != null) {
			operatorService.remove(taxId, Tax.class);
		}
		
		return list();
	}
	
	public String list() {
		taxes = operatorService.listTaxes(getOrganizationId());

		return SUCCESS;
	}

	public RateId getTaxId() {
		return taxId;
	}

	public void setTaxId(RateId taxId) {
		this.taxId = taxId;
	}

	public Tax getEntity() {
		return entity;
	}

	public void setEntity(Tax entity) {
		this.entity = entity;
	}

	public Collection<? extends RateInterface> getTaxes() {
		return taxes;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	
}
