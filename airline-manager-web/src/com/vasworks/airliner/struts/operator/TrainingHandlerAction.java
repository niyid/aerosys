package com.vasworks.airliner.struts.operator;

import java.io.File;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.TrainingDoctor;
import com.vasworks.airliner.model.TrainingHandler;
import com.vasworks.airliner.model.TrainingPilot;
import com.vasworks.airliner.model.TrainingSupervisor;
import com.vasworks.airliner.model.TrainingType;
import com.vasworks.airliner.struts.OperatorAction;

public class TrainingHandlerAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7236661111332939802L;

	private Long trainerId;
	
	private TrainingHandler entity;
	
	private TrainingType trainingType;
	
	private File uploadedFile;
	
	private String fileDescription;
	
	private List<? extends TrainingHandler> trainers;

	@Override
	public void prepare() {
		Class<?> trainerClass = fetchTrainerClass(trainingType);
		
		try {
			entity = (TrainingHandler) (trainerClass != null ? trainerClass.newInstance(): null);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String execute() {
		return SUCCESS;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "uploadedFile", message = "'Credential' is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.handlerName", message = "'Name' is required."),
			@RequiredStringValidator(fieldName = "fileDescription", message = "'File Description' is required.")
		}
	)
	public String saveSupervisor() {
		operatorService.saveTrainingSupervisor(trainerId, (TrainingSupervisor) entity, uploadedFile, fileDescription, getUserId());
		return SUCCESS;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.handlerName", message = "'Name' is required."),
			@RequiredStringValidator(fieldName = "entity.licenseNumber", message = "'License #' is required.")
		}
	)
	public String savePilot() {
		operatorService.saveTrainingPilot(trainerId, (TrainingPilot) entity, getUserId());
		return SUCCESS;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.handlerName", message = "'Name' is required."),
			@RequiredStringValidator(fieldName = "entity.facilityName", message = "'Facility Name' is required."),
			@RequiredStringValidator(fieldName = "entity.address.addressLine1", message = "'Line 1' is required."),
			@RequiredStringValidator(fieldName = "entity.address.addressLine1", message = "'Line 1' is required."),
			@RequiredStringValidator(fieldName = "entity.address.city", message = "'City' is required."),
			@RequiredStringValidator(fieldName = "entity.address.state", message = "'State' is required.")			
		}
	)
	public String saveDoctor() {
		operatorService.saveTrainingDoctor(trainerId, (TrainingDoctor) entity, getUserId());
		return SUCCESS;
	}
	
	public String select() {
		return SUCCESS;
	}
	
	public String list() {
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(entity instanceof TrainingSupervisor) {
			
		} else
		if(entity instanceof TrainingPilot) {
				
		} else
		if(entity instanceof TrainingDoctor) {
				
		} 		
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainingHandlerId) {
		this.trainerId = trainingHandlerId;
	}

	public TrainingHandler getEntity() {
		return entity;
	}

	public void setEntity(TrainingHandler entity) {
		this.entity = entity;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public File getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public List<? extends TrainingHandler> getTrainers() {
		return trainers;
	}
}
