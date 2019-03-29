package com.vasworks.airliner.struts;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.core.GrantedAuthority;

import com.vasworks.airliner.model.Airline;
import com.vasworks.airliner.model.Customer;
import com.vasworks.entity.Organization;
import com.vasworks.security.Authorize;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardAction.
 */
public class DashboardAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant log. */
	private static final Log LOG = LogFactory.getLog(DashboardAction.class);
	
	/* (non-Javadoc)
	 * @see com.vasworks.struts.BaseAction#prepare()
	 */
	@Override
	@SkipValidation
	public void prepare() {
		LOG.debug("prepare()");
		
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SkipValidation
	public String execute() {
		LOG.debug("execute()");
		
		//TODO Check if client is POSTPAID and has any overdue invoices to settle.
		String forwardId = SUCCESS;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airlineCode", "BHNL");
		Airline defaultAirline = (Airline) operatorService.findEntity(Airline.class, "select o from Airline o where o.airlineCode = :airlineCode", params);
		
		session.put("airline_id", defaultAirline.getId());
		session.put("airline_code", defaultAirline.getAirlineCode());
		
		Organization organization = getOrganization();
		if(organization != null) {
			session.put("org_id", organization.getId());
		}
		
		Customer customer = getCustomer();
		LOG.info("Customer User: " + (getUser() != null ? getUser().getId() : null));
		LOG.info("Customer Personnel: " + (getPersonnel() != null ? getPersonnel().getId() : null));
		LOG.info("Customer Organization: " + (getOrganization() != null ? getOrganization().getId() : null));
		LOG.info("Customer: " + customer + " " + (customer != null ? customer.getCustomerType(): null));
		LOG.info("Authorized User: " + Authorize.getUser() + " " + (Authorize.getUser() != null ? Authorize.getUser().getUsername(): null));
		if(customer != null && Authorize.getUser() != null) {
			LOG.info("Setting Customer Role: " + customer.getCustomerType().name());
			session.put("roles", customer.getCustomerType().name());
		}
		
		if (Authorize.hasAny("ROLE_USER")) {
			LOG.info("Redirecting to home screen...");
			session.put("basket_id", getUser().getId());
			forwardId = SUCCESS;
		} else if (Authorize.hasAny("ROLE_ANONYMOUS")) {
			LOG.info("Redirecting to ROLE_ANONYMOUS role home screen...");
			forwardId = INPUT;
		}
		if (Authorize.hasAny("ROLE_CLIENT")) {
			session.put("client_id", getUser().getId());
		}

		try {
		} catch (Exception e) {
			LOG.error("save", e);
			e.printStackTrace();
			addActionError("An error occurred. Contact your administrator for more information.");
			forwardId = ERROR;
		}
		return forwardId;
	}
}
