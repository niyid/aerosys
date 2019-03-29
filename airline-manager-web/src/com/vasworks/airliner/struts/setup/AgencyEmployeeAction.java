package com.vasworks.airliner.struts.setup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;

// TODO: Auto-generated Javadoc
/**
 * The Class AgencyEmployeeAction.
 */
public class AgencyEmployeeAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(AgencyEmployeeAction.class);
	
	private Long organizationId;
	
	private Long personnelId;
	
	private Personnel personnel;
	
	private String pwdConfirm;
	
	private List<Personnel> employees;
	
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
		String forwardId = INPUT;

		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "personnel.firstName", message = "First name is required."),
			@RequiredStringValidator(fieldName = "personnel.lastName", message = "Last name is required."),
			@RequiredStringValidator(fieldName = "personnel.mail", message = "Email is required."),
			@RequiredStringValidator(fieldName = "personnel.userName", message = "AppUser-name is required."),
			@RequiredStringValidator(fieldName = "personnel.password", message = "Password is required."),
			@RequiredStringValidator(fieldName = "pwdConfirm", message = "Confirmation is required.")
		},
		emails = {
			@EmailValidator(fieldName = "personnel.email", message = "Email must be a valid email address.")	
		}
	)
	public String save() {
		LOG.debug("save()");
		String forwardId = SUCCESS;

		try {
			operatorService.savePersonnel(personnelId, personnel, organizationId, null, getUserId());
			if(personnelId == null) {
				addActionMessage("Registration successful; please proceed to login.");
			} else {
				addActionMessage("Personnel info update successful.");
			}
		} catch (Exception e) {
			LOG.error("save", e);
			e.printStackTrace();
			addActionError("An error occurred. Contact your administrator for more information.");
			forwardId = ERROR;
		}
		return forwardId;
		
	}
	
	public String select() {
		if(personnelId != null) {
			personnel = (Personnel) operatorService.find(personnelId, Personnel.class);
		}
		
		return SUCCESS;
	}
	
	public String list() {
		if(organizationId != null) {
			employees = operatorService.listPersonnel(organizationId);
		}
		
		return SUCCESS;
	}

	@Override
	public void validate() {
		if(!(pwdConfirm != null && personnel != null && pwdConfirm.equals(personnel.getPassword()))) {
			addActionError("Password and confirmation do not match.");
		}
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long airlineId) {
		this.organizationId = airlineId;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel newUser) {
		this.personnel = newUser;
	}

	public Long getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(Long personnelId) {
		this.personnelId = personnelId;
	}

	public String getPwdConfirm() {
		return pwdConfirm;
	}

	public void setPwdConfirm(String pwdConfirm) {
		this.pwdConfirm = pwdConfirm;
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> getOrganizationLov() {
		return (List<Organization>) operatorService.list(Organization.class);
	}
	
	public Organization getOrganization() {
		return (Organization) operatorService.find(organizationId, Organization.class);
	}

	public List<Personnel> getEmployees() {
		return employees;
	}
}
