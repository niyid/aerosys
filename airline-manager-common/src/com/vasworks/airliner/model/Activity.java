package com.vasworks.airliner.model;

import java.util.Date;
import java.util.List;

public interface Activity {
	public static enum ActivityType {FLIGHT, PREPARATION, BREAK, TRAINING};
	
	/**
	 * 
	 * @return
	 */
	public Date getStartTime();
	
	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime);
	
	/**
	 * 
	 * @return
	 */
	public Date getEndTime();
	
	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime);
	
	/**
	 * 
	 * @return
	 */
	public String getDescription();
	
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * 
	 * @return
	 */
	public ActivityType getActivityType();
	
	/**
	 * 
	 * @param activityType
	 */
	public void setActivityType(ActivityType activityType);
	
	/**
	 * 
	 * @return
	 */
	public List<CrewMember> getFlightCrew();
	
	/**
	 * 
	 * @param flightCrew
	 */
	public void setFlightCrew(List<CrewMember> flightCrew);
}
