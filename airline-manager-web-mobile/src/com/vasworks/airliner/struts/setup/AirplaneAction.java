package com.vasworks.airliner.struts.setup;

import java.util.Collections;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.AirplaneMake;
import com.vasworks.airliner.model.AirplaneModel;
import com.vasworks.airliner.struts.OperatorAction;

public class AirplaneAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String regNumber;
	
	private Airplane entity;
	
	private String modelName;
	
	private String makeName;
	
	private List<Airplane> airplanes;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("airplane_id", null);
		return super.execute();
	}
	
	public String load() {
		if(regNumber != null) {
			if(entity == null) {
				entity = (Airplane) operatorService.find(regNumber, Airplane.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.regNumber", message = "'Registration' is required."),
				@RequiredStringValidator(fieldName = "makeName", message = "'Make' is required."),
				@RequiredStringValidator(fieldName = "modelName", message = "'Model' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "entity.manufactureDate", message = "'Manufacture Date' is required.")
			}
		)
	public String save() {
		operatorService.saveAirplane(entity, regNumber, getAirline().getId(), modelName, getUser().getId());
		
		entity = new Airplane();
		regNumber = null;
		modelName = null;
		makeName = null;
		
		session.put("airplane_id", null);
		
		addActionMessage("Airplane successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new Airplane();
		regNumber = null;
		modelName = null;
		makeName = null;
		
		session.put("airplane_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(regNumber != null) {
			entity = (Airplane) operatorService.find(regNumber, Airplane.class);
			
			makeName = entity.getModel().getAirplaneMake().getMakeName();
			modelName = entity.getModel().getModelName();
			
			session.put("airplane_id", regNumber);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(regNumber != null) {
			operatorService.remove(regNumber, Airplane.class);
		}
		
		return list();
	}
	
	public String list() {
		airplanes = operatorService.listAirplanes(getAirline().getId());
			
		return SUCCESS;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String airplaneId) {
		this.regNumber = airplaneId;
	}

	public Airplane getEntity() {
		return entity;
	}

	public void setEntity(Airplane entity) {
		this.entity = entity;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<Airplane> getAirplanes() {
		return airplanes;
	}
	
	@SuppressWarnings("unchecked")
	public List<AirplaneMake> getAirplaneMakeLov() {
		return (List<AirplaneMake>) operatorService.list(AirplaneMake.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<AirplaneModel> getAirplaneModelLov() {
		return (List<AirplaneModel>) (makeName != null ? operatorService.listAirplaneModels(makeName) : Collections.emptyList());
	}	
}
