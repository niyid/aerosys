package com.vasworks.airliner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class YieldRule implements YieldManagementRule {
	
	private Long id;
	
	private double maxPercent;
	
	private YieldRuleType ruleType;
	
	private int precedence;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public double getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(double maxPercent) {
		this.maxPercent = maxPercent;
	}

	public YieldRuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(YieldRuleType ruleType) {
		this.ruleType = ruleType;
	}

	public int getPrecedence() {
		return precedence;
	}

	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

}
