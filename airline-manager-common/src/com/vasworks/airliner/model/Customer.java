package com.vasworks.airliner.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.vasworks.airliner.model.BillingCycle.CycleRange;
import com.vasworks.entity.Organization;

@Entity
public class Customer extends Organization {
	
	public static enum CustomerType {PRE_ETL_PREPAID, PRE_ETL_POSTPAID, POST_ETL, PRE_ETL_DEPOSIT};

	/**
	 * 
	 */
	private static final long serialVersionUID = 2941932520785762641L;
	
	private CycleRange cycleRange;
	
	private Airline airline;
	
	private CustomerType customerType = CustomerType.POST_ETL;
	
	private Double depositAmount = 0.0;
	
	private Double depositLowerLimit = 200000.0;
	
	private List<RoutePrice> routePrices;

	public CycleRange getCycleRange() {
		return cycleRange;
	}

	public void setCycleRange(CycleRange cycleRange) {
		this.cycleRange = cycleRange;
	}

	@ManyToOne
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@OneToMany(mappedBy = "customer")
	public List<RoutePrice> getRoutePrices() {
		return routePrices;
	}

	public void setRoutePrices(List<RoutePrice> routePrices) {
		this.routePrices = routePrices;
	}

	public Double getDepositLowerLimit() {
		return depositLowerLimit;
	}

	public void setDepositLowerLimit(Double depositLowerLimit) {
		this.depositLowerLimit = depositLowerLimit;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
}
