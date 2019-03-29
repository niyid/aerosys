package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BillingCycle implements Serializable {
	
	public static enum CycleRange {WEEK, FORTNIGHT, MONTH, YEAR};

	/**
	 * 
	 */
	private static final long serialVersionUID = 6708440065986884657L;
	
	private Long id;
	
	private int startDay;
	
	private int endDay;
	
	private CycleRange cycleRange = CycleRange.WEEK;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public CycleRange getCycleRange() {
		return cycleRange;
	}

	public void setCycleRange(CycleRange cycleRange) {
		this.cycleRange = cycleRange;
	}

}
