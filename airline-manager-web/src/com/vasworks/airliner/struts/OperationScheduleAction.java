package com.vasworks.airliner.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vasworks.airliner.model.Activity;
import com.vasworks.airliner.service.OperatorService.CalendarPeriod;


public class OperationScheduleAction extends OperatorAction {
	
	private static HashMap<Integer, String> DAYS_OF_WEEK = new HashMap<Integer, String>();
	static {
		DAYS_OF_WEEK.put(1, "Sunday");
		DAYS_OF_WEEK.put(2, "Monday");
		DAYS_OF_WEEK.put(3, "Tuesday");
		DAYS_OF_WEEK.put(4, "Wednesday");
		DAYS_OF_WEEK.put(5, "Thursday");
		DAYS_OF_WEEK.put(6, "Friday");
		DAYS_OF_WEEK.put(7, "Saturday");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -978288662080145465L;
	
	private Long crewMemberId;
	
	private CalendarPeriod calendarPeriod = CalendarPeriod.WEEK;
	
	private Map<Integer, List<Activity>> scheduleMap;

	@Override
	public String execute() {
		if(crewMemberId != null) {
			scheduleMap = operatorService.listAllActivitySchedule(crewMemberId, calendarPeriod);
		}
		
		return SUCCESS;
	}

	public Long getCrewMemberId() {
		return crewMemberId;
	}

	public void setCrewMemberId(Long crewMemberId) {
		this.crewMemberId = crewMemberId;
	}

	public CalendarPeriod getCalendarPeriod() {
		return calendarPeriod;
	}

	public void setCalendarPeriod(CalendarPeriod calendarPeriod) {
		this.calendarPeriod = calendarPeriod;
	}
	
	public CalendarPeriod[] getCalendarPeriodLov() {
		return CalendarPeriod.values();
	}

	public String weekdayName(Integer index) {
		return DAYS_OF_WEEK.get(index);
	}
	
	public Map<Integer, List<Activity>> getScheduleMap() {
		return scheduleMap;
	}
}
