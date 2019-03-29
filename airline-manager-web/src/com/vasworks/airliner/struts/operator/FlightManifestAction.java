package com.vasworks.airliner.struts.operator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.struts.OperatorAction;

public class FlightManifestAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private InputStream inputStream;
	
	private String contentType;
	
	private String contentDisposition;
	
	private String[] headers;

	private FlightId flightId;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		contentDisposition = "attachment;filename=\"FlightManifest.pdf\"";
		contentType = "application/pdf";
		File file = operatorService.printFlightManifest(flightId);
		
		
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public FlightId getFlightId() {
		return flightId;
	}

	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
	}
}
