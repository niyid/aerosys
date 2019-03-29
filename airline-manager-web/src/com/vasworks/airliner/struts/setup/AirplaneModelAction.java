package com.vasworks.airliner.struts.setup;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.AirplaneMake;
import com.vasworks.airliner.model.AirplaneModel;
import com.vasworks.airliner.struts.OperatorAction;

public class AirplaneModelAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String modelName;
	
	private String makeName;
	
	private AirplaneModel entity;
	
	private List<AirplaneModel> airplaneModels;
	
	@Override
	public void prepare() {
		entity = new AirplaneModel();
	}

	@Override
	public String execute() {
		session.put("airplane_model_id", null);
		
		return super.execute();
	}
	
	public String load() {
		if(modelName != null) {
			if(entity == null) {
				entity = (AirplaneModel) operatorService.find(modelName, AirplaneModel.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.modelName", message = "'Name' is required."),
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required."),
				@RequiredStringValidator(fieldName = "entity.crossSection", message = "'Cross-section' is required."),
				@RequiredStringValidator(fieldName = "entity.exitRows", message = "'Exit Rows' is required."),
				@RequiredStringValidator(fieldName = "makeName", message = "'Make' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "entity.numberOfRows", message = "'No of Seat Rows' is required.")	,
				@RequiredFieldValidator(fieldName = "entity.luggageCapacity", message = "'Luggage Capacity' is required.")
			},
			regexFields = {
				@RegexFieldValidator(fieldName = "entity.bizClassRows", message = "'Biz-class Rows' format wrong.", expression = "[0-9]+(\\s*,\\s*[0-9]+)*"),
				@RegexFieldValidator(fieldName = "entity.fstClassRows", message = "'First-class Rows' format wrong.", expression = "[0-9]+(\\s*,\\s*[0-9]+)*"),
				@RegexFieldValidator(fieldName = "entity.crossSection", message = "'Cross-section' format wrong.", expression = "[a-zA-Z]+[|][a-zA-Z]+"),
				@RegexFieldValidator(fieldName = "entity.exitRows", message = "'Exit Rows' format wrong.", expression = "[0-9]+(\\s*,\\s*[0-9]+)*")
			}
		)
	public String save() {
		operatorService.saveAirplaneModel(entity, modelName, makeName, getUserId());
		session.put("airplane_make_id", makeName);
		
		entity = new AirplaneModel();
		modelName = null;
		makeName = null;
		
		session.put("airplane_model_id", null);
		
		addActionMessage("Airplane Model successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new AirplaneModel();
		
		session.put("airplane_model_id", null);
		session.put("country_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(modelName != null) {
			entity = (AirplaneModel) operatorService.find(modelName, AirplaneModel.class);
			makeName = entity.getAirplaneMake().getMakeName();
			
			session.put("airplane_model_id", modelName);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(modelName != null) {
			operatorService.remove(modelName, AirplaneModel.class);
		}
		
		return list();
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		
		makeName = makeName == null ? (String) session.get("airplane_make_id") : makeName;
		if(makeName != null) {
			airplaneModels = operatorService.listAirplaneModels(makeName);
		} else {
			airplaneModels = (List<AirplaneModel>) operatorService.list(AirplaneModel.class);
		}
		
		return SUCCESS;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public AirplaneModel getEntity() {
		return entity;
	}

	public void setEntity(AirplaneModel entity) {
		this.entity = entity;
	}

	public List<AirplaneModel> getAirplaneModels() {
		return airplaneModels;
	}
	
	@SuppressWarnings("unchecked")
	public List<AirplaneMake> getAirplaneMakeLov() {
		return (List<AirplaneMake>) operatorService.list(AirplaneMake.class);
	}
}
