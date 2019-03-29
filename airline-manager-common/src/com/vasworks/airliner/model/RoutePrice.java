package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vasworks.entity.Currency;

@Entity
public class RoutePrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1599668537054397418L;

	private RoutePriceId id;
	
	private Double airFareEconomy;
	
	private Double airFareBizClass;
	
	private Double airFareFirstClass;
	
	private Double currencyRate;
	
	private Currency currency;
	
	private Route route;
	
	private Customer customer;
	
	public static final boolean hasEconomy = true;
	
	public static final boolean hasBusiness = false;
	
	public static final boolean hasFirst = false;
	
	public static final String economyTravelName = "Business Travel";
	
	public static final String businessTravelName = "Business Class";
	
	public static final String firstTravelName = "First Class";
	
	@EmbeddedId
	public RoutePriceId getId() {
		return id;
	}

	public void setId(RoutePriceId id) {
		this.id = id;
	}

	public Double getAirFareEconomy() {
		return airFareEconomy;
	}

	public void setAirFareEconomy(Double airFareEconomy) {
		this.airFareEconomy = airFareEconomy;
	}

	public Double getAirFareBizClass() {
		return airFareBizClass;
	}

	public void setAirFareBizClass(Double airFareBizClass) {
		this.airFareBizClass = airFareBizClass;
	}

	public Double getAirFareFirstClass() {
		return airFareFirstClass;
	}

	public void setAirFareFirstClass(Double airFareFirstClass) {
		this.airFareFirstClass = airFareFirstClass;
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	@ManyToOne
	@JoinColumn(name = "routeId", insertable = false, updatable = false, referencedColumnName = "id")
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@ManyToOne
	@JoinColumn(name = "customerId", insertable = false, updatable = false, referencedColumnName = "id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne
	@JoinColumn(name = "currencyCode", insertable = false, updatable = false, referencedColumnName = "currencyCode")
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
