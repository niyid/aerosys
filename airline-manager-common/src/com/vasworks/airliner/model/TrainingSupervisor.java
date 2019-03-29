package com.vasworks.airliner.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TrainingSupervisor extends TrainingHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5826072274849408399L;
	
	private DocumentFile credentialFile;

	@OneToOne
	public DocumentFile getCredentialFile() {
		return credentialFile;
	}

	public void setCredentialFile(DocumentFile credentialFile) {
		this.credentialFile = credentialFile;
	}

}
