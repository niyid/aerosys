package com.vasworks.airliner.struts.setup;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.AirplaneMake;
import com.vasworks.airliner.struts.OperatorAction;

public class AirplaneMakeAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String makeName;
	
	private AirplaneMake entity;
	
	private List<AirplaneMake> airplaneMakes;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("airplane_make_id", null);
		
		return super.execute();
	}
	
	public String load() {
		if(makeName != null) {
			if(entity == null) {
				entity = (AirplaneMake) operatorService.find(makeName, AirplaneMake.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.makeName", message = "'Name' is required."),
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required.")
			}
		)
	public String save() {
		operatorService.saveAirplaneMake(entity, makeName, getUser().getId());
		
		entity = new AirplaneMake();
		makeName = null;
		
		session.put("airplane_make_id", null);
		
		addActionMessage("Airplane Make successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new AirplaneMake();
		
		session.put("airplane_make_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(makeName != null) {
			entity = (AirplaneMake) operatorService.find(makeName, AirplaneMake.class);
			
			session.put("airplane_make_id", makeName);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(makeName != null) {
			operatorService.remove(makeName, AirplaneMake.class);
		}
		
		return list();
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		airplaneMakes = (List<AirplaneMake>) operatorService.list(AirplaneMake.class);
		
		return SUCCESS;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public AirplaneMake getEntity() {
		return entity;
	}

	public void setEntity(AirplaneMake entity) {
		this.entity = entity;
	}

	public List<AirplaneMake> getAirplaneMakes() {
		return airplaneMakes;
	}
}
