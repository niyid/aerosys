package com.vasworks.airliner.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vasworks.entity.Personnel;

@Entity
public class CrewMember extends Personnel {
	public static enum CrewType {SENIOR_PILOT, PILOT, CO_PILOT, SENIOR_ENGINEER, ENGINEER, SENIOR_TRAFFIC_CONTROLLER, TRAFFIC_CONTROLLER, SENIOR_ATTENDANT, ATTENDANT};

	/**
	 * 
	 */
	private static final long serialVersionUID = 5512714139955962425L;
	
	private CrewType crewType;
	
	private Date dob;
	
	private byte[] photo;
	
	private String photoMimeType;
	
	private String photoFileExtension;
	
	private List<Flight> flightSchedule;
	
	private List<CrewActivity> activitySchedule;
	
	private List<Activity> activities;
	
	private List<Training> trainings;

	public CrewType getCrewType() {
		return crewType;
	}

	public void setCrewType(CrewType crewType) {
		this.crewType = crewType;
	}

	@Temporal(TemporalType.DATE)
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Lob
	@Column(length = 15000)
	@Basic(fetch = FetchType.LAZY)
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoMimeType() {
		return photoMimeType;
	}

	public void setPhotoMimeType(String photoMimeType) {
		this.photoMimeType = photoMimeType;
	}

	public String getPhotoFileExtension() {
		return photoFileExtension;
	}

	public void setPhotoFileExtension(String photoFileExtension) {
		this.photoFileExtension = photoFileExtension;
	}

	@ManyToMany(mappedBy = "flightCrew")
	public List<Flight> getFlightSchedule() {
		return flightSchedule;
	}

	public void setFlightSchedule(List<Flight> flightSchedule) {
		this.flightSchedule = flightSchedule;
	}

	@ManyToMany(mappedBy = "flightCrew")
	public List<CrewActivity> getActivitySchedule() {
		return activitySchedule;
	}

	public void setActivitySchedule(List<CrewActivity> activitySchedule) {
		this.activitySchedule = activitySchedule;
	}

	@Transient
	public List<Activity> getActivities() {
		return this.activities;
	}
	
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@OneToMany(mappedBy = "crewMember")
	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}
}
