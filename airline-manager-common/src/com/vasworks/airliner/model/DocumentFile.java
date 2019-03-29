package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class DocumentFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7115694715254622231L;
	
	private Long id;
	
	private String fileName;
	
	private String mimeType;

	private String description;
	
	private byte[] rawContent;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Lob
	@Column(length = 15000)
	@Basic(fetch = FetchType.LAZY)
	public byte[] getRawContent() {
		return rawContent;
	}

	public void setRawContent(byte[] rawContent) {
		this.rawContent = rawContent;
	}
}
