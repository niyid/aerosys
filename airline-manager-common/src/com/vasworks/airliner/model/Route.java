package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Route implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 642254475886279218L;

	private Long id;
	
	private String description;
	
	private Airport sourceAirport;
	
	private Airport destinationAirport;
	
	private List<Tax> taxes;
	
	private List<Rebate> rebates;
	
	private List<RoutePrice> routePrices;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Lob
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	public Airport getSourceAirport() {
		return sourceAirport;
	}

	public void setSourceAirport(Airport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	@ManyToOne
	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	@ManyToMany
	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

	@ManyToMany
	public List<Rebate> getRebates() {
		return rebates;
	}

	public void setRebates(List<Rebate> rebates) {
		this.rebates = rebates;
	}

	@OneToMany(mappedBy = "route")
	public List<RoutePrice> getRoutePrices() {
		return routePrices;
	}

	public void setRoutePrices(List<RoutePrice> routePrices) {
		this.routePrices = routePrices;
	}
}
