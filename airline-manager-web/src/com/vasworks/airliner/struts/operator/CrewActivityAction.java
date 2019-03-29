package com.vasworks.airliner.struts.operator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Activity;
import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.CrewActivity;
import com.vasworks.airliner.model.CrewMember;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.Activity.ActivityType;
import com.vasworks.airliner.struts.OperatorAction;

public class CrewActivityAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 761913034583621082L;

	private Long activityId;
	
	private Long airlineId;
	
	private CrewActivity entity;
	
	private FlightId relatedFlightId;
	
	private Long[] selCrewMemberIds;
	
	@Override
	public String execute() {
		initSelCrewMemberIds();
		airlineId = (Long) session.get("airline_id");
		
		return SUCCESS;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "entity.startTime", message = "'Start' is required."),
			@RequiredFieldValidator(fieldName = "entity.endTime", message = "'End' is required."),
			@RequiredFieldValidator(fieldName = "entity.activityType", message = "'Type' is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required."),	
		}
	)
	public String save() {
		operatorService.saveCrewActivity(activityId, entity, selCrewMemberIds, getUserId());
		
		return SUCCESS;
	}
	
	public String list() {
		if(relatedFlightId != null) {
			operatorService.listFlightRelatedActivities(relatedFlightId);
		}
		return SUCCESS;
	}
	
	public String select() {
		if(activityId != null) {
			entity = (CrewActivity) operatorService.find(activityId, CrewActivity.class);
		}
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(selCrewMemberIds == null || selCrewMemberIds.length == 0) {
			addActionError("No crew members selected.");
		}
		
		if(entity.getStartTime() != null && entity.getStartTime().before(new Date())) {
			addFieldError("entity.startTime", "'Start Time' must be in the future.");
		}
		
		if(entity.getEndTime() != null && entity.getEndTime().before(new Date())) {
			addFieldError("entity.endTime", "'End Time' must be in the future.");
		}
		
		if(entity.getEndTime() != null && entity.getStartTime() != null && entity.getEndTime().before(entity.getStartTime())) {
			addFieldError("entity.endTime", "'End Time' cannot be before 'Start Time'.");
		}		
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public CrewActivity getEntity() {
		return entity;
	}

	public void setEntity(CrewActivity entity) {
		this.entity = entity;
	}

	public FlightId getRelatedFlightId() {
		return relatedFlightId;
	}

	public void setRelatedFlightId(FlightId relatedFlightId) {
		this.relatedFlightId = relatedFlightId;
	}

	public List<CrewMember> getCrewMemberLov() {
		List<CrewMember> crew = new ArrayList<CrewMember>();
		if(airlineId != null) {			
			Airline airline = (Airline) operatorService.find(airlineId, Airline.class);
			if(airline != null && operatorService.listCrewMembers(airlineId) != null) {
				crew = operatorService.listCrewMembers(airlineId);
			}
		}
		return crew;
	}

	public Long[] getSelCrewMemberIds() {
		return selCrewMemberIds;
	}

	public void setSelCrewMemberIds(Long[] selCrewMemberIds) {
		this.selCrewMemberIds = selCrewMemberIds;
	}
	
	public ActivityType[] getActivityTypeLov() {
		return ActivityType.values();
	}
	
	private void initSelCrewMemberIds() {
		if(activityId != null) {
			List<CrewMember> list = ((Activity) operatorService.find(activityId, CrewActivity.class)).getFlightCrew();
			selCrewMemberIds = new Long[list.size()];
			
			int i = 0;
			for(CrewMember o : list) {
				selCrewMemberIds[i] = o.getId();
				i++;
			}
		}
	}
}
