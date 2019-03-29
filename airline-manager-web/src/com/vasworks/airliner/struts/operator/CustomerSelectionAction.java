package com.vasworks.airliner.struts.operator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.struts.OperatorAction;

// TODO: Auto-generated Javadoc
/**
 * The Class AgencyEmployeeAction.
 */
public class CustomerSelectionAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CustomerSelectionAction.class);
	
	private Long clientId;
	
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
		String forwardId = SUCCESS;
		
		clientId = (Long) session.get("client_id");

		return forwardId;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "clientId", message = "Customer is required.")
		}
	)
	public String save() {
		LOG.debug("save()");
		String forwardId = SUCCESS;

		session.put("client_id", clientId);
		Customer customer = (Customer) operatorService.find(clientId, Customer.class);
		session.put("client_name", customer.getOrganizationName());
		addActionMessage("Customer successfully selected.");
		LOG.info("Customer successfully selected - " + clientId);
		
		return forwardId;
		
	}
	
	@SkipValidation
	public String deselect() {
		session.remove("client_name");
		session.remove("client_id");
		addActionMessage("Customer deselected.");
		LOG.info("Customer deselected.");
		
		return SUCCESS;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
