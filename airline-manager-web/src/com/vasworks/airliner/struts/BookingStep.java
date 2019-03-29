package com.vasworks.airliner.struts;

import java.io.Serializable;

public class BookingStep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162950820249320713L;

	private int index;
	
	private String stepName;
	
	private String state;
	
	public BookingStep() {
		
	}

	public BookingStep(int index, String name, String state) {
		super();
		this.index = index;
		this.stepName = name;
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String name) {
		this.stepName = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
