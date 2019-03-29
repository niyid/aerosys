package com.vasworks.airliner.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CrewActivity implements Activity {
	
	private Long id;
	
	private Date startTime;
	
	private Date endTime;
	
	private String description;
	
	private ActivityType activityType;
	
	private List<CrewMember> flightCrew;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	@Lob
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public ActivityType getActivityType() {
		return activityType;
	}

	@Override
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	
	@ManyToMany
	public List<CrewMember> getFlightCrew() {
		return flightCrew;
	}

	public void setFlightCrew(List<CrewMember> flightCrew) {
		this.flightCrew = flightCrew;
	}
}
