package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ActivityReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String reportEntity;
	
	private String description;
	
	private Customer customer;
	
	private String dateFilter;
	
	private String reportJpql;

	private String headers;
	
	private List<ActivityReportColumn> reportColumns;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportEntity() {
		return reportEntity;
	}

	public void setReportEntity(String reportEntity) {
		this.reportEntity = reportEntity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}

	@Lob
	public String getReportJpql() {
		return reportJpql;
	}

	public void setReportJpql(String reportJpql) {
		this.reportJpql = reportJpql;
	}

	@Lob
	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	@OneToMany(mappedBy = "activityReport")
	public List<ActivityReportColumn> getReportColumns() {
		return reportColumns;
	}

	public void setReportColumns(List<ActivityReportColumn> reportColumns) {
		this.reportColumns = reportColumns;
	}
	
}
