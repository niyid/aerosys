package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ActivityReportColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String columnPath;
	
	private String description;
	
	private ActivityReport activityReport;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumnPath() {
		return columnPath;
	}

	public void setColumnPath(String columnPath) {
		this.columnPath = columnPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	public ActivityReport getActivityReport() {
		return activityReport;
	}

	public void setActivityReport(ActivityReport activityReport) {
		this.activityReport = activityReport;
	}
	
}
