package com.vasworks.airliner.struts;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;

// TODO: Auto-generated Javadoc
/**
 * The Class AgencyEmployeeAction.
 */
public class PersonnelAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(PersonnelAction.class);
	
	private Long airlineId;
	
	private Long personnelId;
	
	private Personnel newUser;
	
	private String pwdConfirm;
	
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
		
		if(personnelId != null) {
			newUser = (Personnel) operatorService.find(personnelId, Personnel.class);
		}

		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "newUser.firstName", message = "First name is required."),
			@RequiredStringValidator(fieldName = "newUser.lastName", message = "Last name is required."),
			@RequiredStringValidator(fieldName = "newUser.mail", message = "Email is required."),
			@RequiredStringValidator(fieldName = "newUser.userName", message = "AppUser-name is required."),
			@RequiredStringValidator(fieldName = "newUser.password", message = "Password is required.")
		},
		emails = {
			@EmailValidator(fieldName = "newUser.email", message = "Email must be a valid email address.")	
		}
	)
	public String save() {
		LOG.debug("save()");
		String forwardId = SUCCESS;

		try {
			operatorService.savePersonnel(personnelId, newUser, airlineId, null, getUserId());
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

	@Override
	public void validate() {
		if(!(pwdConfirm != null && newUser != null && pwdConfirm.equals(newUser.getPassword()))) {
			addActionError("Password and confirmation do not match.");
		}
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public Personnel getNewUser() {
		return newUser;
	}

	public void setNewUser(Personnel newUser) {
		this.newUser = newUser;
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
}
