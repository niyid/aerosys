package com.vasworks.airliner.struts.operator;

import java.util.Collection;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airport;
import com.vasworks.airliner.model.RateId;
import com.vasworks.airliner.model.RateInterface;
import com.vasworks.airliner.model.Rebate;
import com.vasworks.airliner.model.Route;
import com.vasworks.airliner.model.Tax;
import com.vasworks.airliner.struts.OperatorAction;

public class RouteAction extends OperatorAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long routeId;
	
	private Route entity;
	
	private List<Route> routes;
	
	private RateId[] selTaxIds;
	
	private RateId[] selRebateIds;
	
	private String[] selAirportCodes;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("flight_id", null);
		return super.execute();
	}
	
	public String load() {
		if(routeId != null) {
			if(entity == null) {
				entity = (Route) operatorService.find(routeId, Route.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "selAirportCodes", message = "You must select 2 airports.")
			}
		)
	public String save() {
		operatorService.saveRoute(entity, routeId, selAirportCodes, selTaxIds, selRebateIds, getUserId());
		
		session.put("route_id", routeId);

		entity = new Route();
		routeId = null;
		
		addActionMessage("Route successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(selAirportCodes != null && selAirportCodes.length != 2) {
			addFieldError("selAirportCodes", "You must select exactly 2 airports.");
		}
	}

	public String initNew() {
		entity = new Route();
		
		session.put("route_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(routeId != null) {
			entity = (Route) operatorService.find(routeId, Route.class);
	
			initSelTaxIds();
			initSelRebateIds();
			initSelAirportCodes();
			
			session.put("route_id", routeId);
		}
		return SUCCESS;
	}
	
	private void initSelTaxIds() {
		if(entity != null) {
			List<Tax> list = entity.getTaxes();
			selTaxIds = new RateId[list.size()];
			
			int i = 0;
			for(Tax o : list) {
				selTaxIds[i] = o.getId();
				i++;
			}
		}
	}
	
	private void initSelRebateIds() {
		if(entity != null) {
			List<Rebate> list = entity.getRebates();
			selRebateIds = new RateId[list.size()];
			
			int i = 0;
			for(Rebate o : list) {
				selRebateIds[i] = o.getId();
				i++;
			}
		}
	}
	
	private void initSelAirportCodes() {
		if(entity != null) {
			selAirportCodes = new String[2];
			selAirportCodes[0] = entity.getSourceAirport().getAirportCode();
			selAirportCodes[1] = entity.getDestinationAirport().getAirportCode();
		}
	}
	
	public String remove() {
		if(routeId != null) {
			operatorService.remove(routeId, Route.class);
		}
		
		return list();
	}
	
	@SuppressWarnings("unchecked")
	public String list() {
		routes = (List<Route>) operatorService.list(Route.class);
		
		return SUCCESS;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Route getEntity() {
		return entity;
	}

	public void setEntity(Route entity) {
		this.entity = entity;
	}

	public List<Route> getRoutes() {
		return routes;
	}
	
	public Collection<? extends RateInterface> getTaxLov() {
		return operatorService.listNonAutoTaxes(getAirlineId());
	}
	
	public Collection<? extends RateInterface> getRebateLov() {
		return operatorService.listNonAutoRebates(getAirlineId());
	}
	
	@SuppressWarnings("unchecked")
	public List<Airport> getAirportLov() {
		return (List<Airport>) operatorService.list(Airport.class);
	}
	
	public RateId[] getSelTaxIds() {
		return selTaxIds;
	}

	public void setSelTaxIds(RateId[] selTaxIds) {
		this.selTaxIds = selTaxIds;
	}

	public RateId[] getSelRebateIds() {
		return selRebateIds;
	}

	public void setSelRebateIds(RateId[] selRebateIds) {
		this.selRebateIds = selRebateIds;
	}
	
	public String[] getSelAirportCodes() {
		return selAirportCodes;
	}

	public void setSelAirportCodes(String[] selThruFlightIds) {
		this.selAirportCodes = selThruFlightIds;
	}
}
