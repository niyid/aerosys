package com.vasworks.airliner.struts;

import java.io.Serializable;

import com.vasworks.airliner.model.FlightSchedule.ScheduleStatus;

public class FlightScheduleStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162950820249320713L;

	private int index;
	
	private String statusName;
	
	private ScheduleStatus scheduleStatus;
	
	private String state;
	
	public FlightScheduleStatus() {
		
	}

	public FlightScheduleStatus(int index, String name, ScheduleStatus scheduleStatus, String state) {
		super();
		this.index = index;
		this.statusName = name;
		this.scheduleStatus = scheduleStatus;
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String name) {
		this.statusName = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ScheduleStatus getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(ScheduleStatus scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	
}
