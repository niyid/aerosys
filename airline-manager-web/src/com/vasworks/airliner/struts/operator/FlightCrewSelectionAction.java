package com.vasworks.airliner.struts.operator;

import java.util.ArrayList;
import java.util.List;

import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.CrewMember;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.struts.OperatorAction;

public class FlightCrewSelectionAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6189034326495293911L;
	
	private FlightId flightId;
	
	private Long[] selCrewMemberIds;

	@Override
	public String execute() {
		initSelCrewMemberIds();
		return SUCCESS;
	}
	
	public String save() {
		operatorService.saveFlightCrew(flightId, selCrewMemberIds, getUserId());
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(selCrewMemberIds == null || selCrewMemberIds.length == 0) {
			addActionError("No crew members selected.");
		} else {
			String error = operatorService.validateCrewSelection(selCrewMemberIds);
			if(error != null) {
				addActionError(error);
			}
		}
	}

	public FlightId getFlightId() {
		return flightId;
	}

	public void setFlightId(FlightId entityId) {
		this.flightId = entityId;
	}

	public List<CrewMember> getCrewMemberLov() {
		Airline airline = (Airline) operatorService.find(getAirlineId(), Airline.class);
		LOG.info("Crew Members for Airline '" + airline + "'");
		return airline != null ? operatorService.listCrewMembers(airline.getId()) : new ArrayList<CrewMember>();
	}

	public Long[] getSelCrewMemberIds() {
		return selCrewMemberIds;
	}

	public void setSelCrewMemberIds(Long[] selCrewMemberIds) {
		this.selCrewMemberIds = selCrewMemberIds;
	}
	
	private void initSelCrewMemberIds() {

		if(flightId != null) {
			List<CrewMember> list = ((Flight) operatorService.find(flightId, Flight.class)).getFlightCrew();
			selCrewMemberIds = new Long[list.size()];
			
			int i = 0;
			for(CrewMember o : list) {
				selCrewMemberIds[i] = o.getId();
				i++;
			}
		}
	}
}
