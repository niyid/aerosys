package com.vasworks.airliner.struts;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vasworks.airliner.model.DocumentFile;

public class DocumentFileViewAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5571653743212255372L;

	private Long documentId;
	
	private String contentDisposition;
	
	private InputStream inputStream;

	@Override
	public String execute() {
		DocumentFile documentFile = (DocumentFile) operatorService.find(documentId, DocumentFile.class);
		
		contentDisposition = "attachment;filename=\"" + documentFile.getFileName() + "\"";
		
		inputStream = new ByteArrayInputStream(documentFile.getRawContent());
		
		return super.execute();
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
