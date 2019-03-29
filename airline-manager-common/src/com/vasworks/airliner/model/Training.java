package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Training implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7098379413145642310L;
	
	private Long id;
	
	private TrainingType trainingType;
	
	private Date completionDate;
	
	private Date expirationDate;
	
	private CrewMember crewMember;
	
	private TrainingHandler trainingHandler;

	private DocumentFile documentFile;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	@Temporal(TemporalType.DATE)
	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@ManyToOne
	public CrewMember getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(CrewMember crewMember) {
		this.crewMember = crewMember;
	}

	@ManyToOne
	public TrainingHandler getTrainingHandler() {
		return trainingHandler;
	}

	public void setTrainingHandler(TrainingHandler trainingHandler) {
		this.trainingHandler = trainingHandler;
	}

	@OneToOne
	public DocumentFile getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(DocumentFile documentFile) {
		this.documentFile = documentFile;
	}
}
