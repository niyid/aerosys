package com.vasworks.airliner.struts.operator;

import java.io.File;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Training;
import com.vasworks.airliner.model.TrainingType;
import com.vasworks.airliner.struts.OperatorAction;

public class TrainingAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480104326736853154L;
	
	private Long trainingId;
	
	private Long crewMemberId;
	
	private Long trainingHandlerId;
	
	private Training entity;
	
	private File uploadedFile;
	
	private String uploadedFileContentType;
	
	private String uploadedFileFileName;
	
	private List<Training> trainings;

	@Override
	public String execute() {
		return SUCCESS;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "entity.completionDate", message = "'Start' is required."),
			@RequiredFieldValidator(fieldName = "uploadedFile", message = "'Check' is required."),
			@RequiredFieldValidator(fieldName = "entity.trainingType", message = "'Type' is required."),
			@RequiredFieldValidator(fieldName = "trainingHandlerId", message = "'Trainer' is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required."),	
		}
	)
	public String save() {
		operatorService.saveTraining(trainingId, entity, crewMemberId, trainingHandlerId, getUserId());
		return SUCCESS;
	}
	
	public String select() {
		return SUCCESS;
	}
	
	public String list() {
		operatorService.listTrainings(crewMemberId);
		return SUCCESS;
	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}

	public Long getCrewMemberId() {
		return crewMemberId;
	}

	public void setCrewMemberId(Long crewMemberId) {
		this.crewMemberId = crewMemberId;
	}

	public Long getTrainingHandlerId() {
		return trainingHandlerId;
	}

	public void setTrainingHandlerId(Long trainingHandlerId) {
		this.trainingHandlerId = trainingHandlerId;
	}

	public Training getEntity() {
		return entity;
	}

	public void setEntity(Training entity) {
		this.entity = entity;
	}
		
	public File getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getUploadedFileContentType() {
		return uploadedFileContentType;
	}

	public void setUploadedFileContentType(String uploadedFileContentType) {
		this.uploadedFileContentType = uploadedFileContentType;
	}

	public String getUploadedFileFileName() {
		return uploadedFileFileName;
	}

	public void setUploadedFileFileName(String uploadedFileFileName) {
		this.uploadedFileFileName = uploadedFileFileName;
	}

	public TrainingType[] getTrainingTypeLov() {
		return TrainingType.values();
	}

	public List<Training> getTrainings() {
		return trainings;
	}
	
	public List<?> getTrainerLov() {
		return operatorService.listTrainers(fetchTrainerClass(entity.getTrainingType()));
	}
}
