package com.vasworks.airliner.struts.operator;

import java.util.Collection;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.struts.OperatorAction;

public class RebateAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private RateId rebateId;
	
	private Rebate entity;
	
	private Collection<? extends RateInterface> rebates;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("rebate_id", null);
		return super.execute();
	}
	
	public String load() {
		if(rebateId != null) {
			if(entity == null) {
				entity = (Rebate) operatorService.find(rebateId, Rebate.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required.")
			}
		)
	public String save() {
		operatorService.saveRebate(entity, rebateId, getUser().getId());
		
		entity = new Rebate();
		rebateId = null;
		
		session.put("rebate_id", null);
		
		addActionMessage("Rebate successfully saved.");
		
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
		entity = new Rebate();
		
		session.put("rebate_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(rebateId != null) {
			entity = (Rebate) operatorService.find(rebateId, Rebate.class);
			
			session.put("rebate_id", rebateId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(rebateId != null) {
			operatorService.remove(rebateId, Rebate.class);
		}
		
		return list();
	}
	
	public String list() {
		rebates = operatorService.listRebates(getAirline().getId());
		
		return SUCCESS;
	}

	public RateId getRebateId() {
		return rebateId;
	}

	public void setRebateId(RateId rebateId) {
		this.rebateId = rebateId;
	}

	public Rebate getEntity() {
		return entity;
	}

	public void setEntity(Rebate entity) {
		this.entity = entity;
	}

	public Collection<? extends RateInterface> getRebates() {
		return rebates;
	}
	
}
