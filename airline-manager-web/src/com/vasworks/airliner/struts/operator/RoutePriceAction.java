package com.vasworks.airliner.struts.operator;

import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Route;
import com.vasworks.airliner.model.RoutePrice;
import com.vasworks.airliner.model.RoutePriceId;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.security.Authorize;

public class RoutePriceAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long routeId;

	private Long clientId;
	
	private String currencyCode;
	
	private RoutePrice entity;
	
	private List<RoutePrice> routePrices;
	
	@Override
	public void prepare() {
		entity = new RoutePrice();
		entity.setId(new RoutePriceId(routeId, clientId, currencyCode));
	}

	@Override
	public String execute() {
		session.put("route_price_id", null);
		return super.execute();
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "currencyCode", message = "'Currency' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "routeId", message = "'Route' is required."),
				@RequiredFieldValidator(fieldName = "clientId", message = "'Client' is required."),
				@RequiredFieldValidator(fieldName = "entity.currencyRate", message = "'Currency Rate' is required."),
				@RequiredFieldValidator(fieldName = "entity.airFareEconomy", message = "'Economy Fare' is required.")
			}
		)
	public String save() {		
		operatorService.saveRoutePrice(entity, getUserId());
		routeId = null;
		clientId = null;
		currencyCode = null;
		
		session.put("route_price_id", null);
		
		addActionMessage("Route price successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		Object o = operatorService.find(new RoutePriceId(routeId, clientId, currencyCode), RoutePrice.class);
		if(o != null) {
			addActionError("Route price already exists for given route, client and currency");
		}
	}
	
	public String initNew() {
		entity = new RoutePrice();
		routeId = null;
		clientId = null;
		currencyCode = null;
		entity.setId(new RoutePriceId(routeId, clientId, currencyCode));
		
		return SUCCESS;
	}
	
	@SkipValidation
	public String select() {
		LOG.info("routeId=" + routeId + ", clientId=" + clientId + ", currencyCode=" + currencyCode);
		if(routeId != null && clientId != null && currencyCode != null) {
			entity = (RoutePrice) operatorService.find(new RoutePriceId(routeId, clientId, currencyCode), RoutePrice.class);
			
			session.put("route_price_id", new RoutePriceId(routeId, clientId, currencyCode));
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(routeId != null && clientId != null && currencyCode != null) {
			operatorService.remove(new RoutePriceId(routeId, clientId, currencyCode), RoutePrice.class);
		}
		
		return list();
	}
	
	@SkipValidation
	public String listPrices() {
		routePrices = operatorService.listRoutePrices(clientId);

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		if (hasAny("POST_ETL")) {
			routePrices = operatorService.listRoutePrices(getOrganizationId());
		} else
		if (Authorize.hasAny("ROLE_OPERATOR,ROLE_FINANCE,ROLE_ADMIN")) {
			routePrices = (List<RoutePrice>) operatorService.list(RoutePrice.class);
		}

		return SUCCESS;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public RoutePrice getEntity() {
		return entity;
	}

	public List<RoutePrice> getRoutePrices() {
		return routePrices;
	}
	
	@SuppressWarnings("unchecked")
	public List<Route> getRouteLov() {
		return (List<Route>) operatorService.list(Route.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Currency> getCurrencyLov() {
		return (List<Currency>) operatorService.list(Currency.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> getOrganizationLov() {
		return (List<Organization>) operatorService.list(Organization.class);
	}
}
