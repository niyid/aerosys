package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AircraftMaintenanceCheck implements Serializable {

	public static enum MaintenanceCheckType {CHECK_A, CHECK_B, CHECK_C, CHECK_D};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1347690666628398807L;

	private Long id;
	
	private Date startDate;
	
	private Date endDate;
	
	private MaintenanceCheckType maintenanceCheckType;
	
	private Airplane airplane;
	
	private AircraftMaintenanceOrg maintenanceOrg;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public MaintenanceCheckType getMaintenanceCheckType() {
		return maintenanceCheckType;
	}

	public void setMaintenanceCheckType(MaintenanceCheckType maintenanceCheckType) {
		this.maintenanceCheckType = maintenanceCheckType;
	}

	@ManyToOne
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	@ManyToOne
	public AircraftMaintenanceOrg getMaintenanceOrg() {
		return maintenanceOrg;
	}

	public void setMaintenanceOrg(AircraftMaintenanceOrg maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}
}
