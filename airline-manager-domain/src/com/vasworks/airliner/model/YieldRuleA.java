package com.vasworks.airliner.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class YieldRuleA {
	
	private String id;
	
	private int precedence = 1;
	
	private double xMin = 7;
	
	private double xMax = 19;
	
	private double coeffA = -1;
	
	private double coeffB = xMax - xMin;
	
	private double coeffC = 0;
	
	private double yMaxPercent = 0.4;
	
	private double yMultiplier = 1 / 36;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPrecedence() {
		return precedence;
	}

	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getCoeffA() {
		return coeffA;
	}

	public void setCoeffA(double coeffA) {
		this.coeffA = coeffA;
	}

	public double getCoeffB() {
		return coeffB;
	}

	public void setCoeffB(double coeffB) {
		this.coeffB = coeffB;
	}

	public double getCoeffC() {
		return coeffC;
	}

	public void setCoeffC(double coeffC) {
		this.coeffC = coeffC;
	}

	public double getyMaxPercent() {
		return yMaxPercent;
	}

	public void setyMaxPercent(double yMaxPercent) {
		this.yMaxPercent = yMaxPercent;
	}

	public double getyMultiplier() {
		return yMultiplier;
	}

	public void setyMultiplier(double yMultiplier) {
		this.yMultiplier = yMultiplier;
	}

}
