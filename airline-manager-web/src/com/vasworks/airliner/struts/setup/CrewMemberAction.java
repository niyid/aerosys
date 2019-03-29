package com.vasworks.airliner.struts.setup;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.CrewMember;
import com.vasworks.airliner.model.CrewMember.CrewType;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.entity.Organization;

// TODO: Auto-generated Javadoc
/**
 * The Class AgencyEmployeeAction.
 */
public class CrewMemberAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(CrewMemberAction.class);
	
	private Long crewMemberId;
	
	private CrewMember crewMember;
	
	private String pwdConfirm;
	
	private File employeePhoto;
	
	private String employeePhotoContentType;
	
	private String employeePhotoFileName;
	
	private List<CrewMember> crewMembers;
	
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
	
	@SkipValidation
	public String view() {

		if(crewMemberId != null) {
			crewMember = (CrewMember) operatorService.find(crewMemberId, CrewMember.class);
		}

		return SUCCESS;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "crewMember.firstName", message = "First name is required."),
			@RequiredStringValidator(fieldName = "crewMember.lastName", message = "Last name is required."),
			@RequiredStringValidator(fieldName = "crewMember.mail", message = "Email is required."),
			@RequiredStringValidator(fieldName = "crewMember.userName", message = "AppUser-name is required."),
			@RequiredStringValidator(fieldName = "crewMember.password", message = "Password is required."),
			@RequiredStringValidator(fieldName = "pwdConfirm", message = "Confirmation is required.")
		},requiredFields = {
			@RequiredFieldValidator(fieldName = "crewMember.crewType", message = "'Designation' is required."),
			@RequiredFieldValidator(fieldName = "crewMember.dob", message = "'Date of Birth' is required.")
		},
		emails = {
			@EmailValidator(fieldName = "crewMember.email", message = "Email must be a valid email address.")	
		}
	)
	public String save() {
		LOG.debug("save()");
		String forwardId = SUCCESS;

		try {
			operatorService.savePersonnel(crewMemberId, crewMember, getAirlineId(), employeePhoto, getUserId());
			if(crewMemberId == null) {
				addActionMessage("Registration of crew member successful.");
			} else {
				addActionMessage("Crew member info update successful.");
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
		if(crewMemberId != null) {
			crewMember = (CrewMember) operatorService.find(crewMemberId, CrewMember.class);
		}
		
		return SUCCESS;
	}
	
	public String list() {
		if(getAirlineId() != null) {
			crewMembers = operatorService.listCrewMembers(getAirlineId());
		}
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if(!(pwdConfirm != null && crewMember != null && pwdConfirm.equals(crewMember.getPassword()))) {
			addActionError("Password and confirmation do not match.");
		}
	}

	public CrewMember getCrewMember() {
		return crewMember;
	}

	public void setCrewMember(CrewMember newUser) {
		this.crewMember = newUser;
	}

	public Long getCrewMemberId() {
		return crewMemberId;
	}

	public void setCrewMemberId(Long crewMemberId) {
		this.crewMemberId = crewMemberId;
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
	
	public CrewType[] getCrewTypeLov() {
		return CrewType.values();
	}

	public Organization getOrganization() {
		return (Organization) (getAirlineId() != null ? operatorService.find(getAirlineId(), Organization.class) : null);
	}

	public File getEmployeePhoto() {
		return employeePhoto;
	}

	public void setEmployeePhoto(File employeePhoto) {
		this.employeePhoto = employeePhoto;
	}

	public String getEmployeePhotoContentType() {
		return employeePhotoContentType;
	}

	public void setEmployeePhotoContentType(String employeePhotoContentType) {
		this.employeePhotoContentType = employeePhotoContentType;
	}

	public String getEmployeePhotoFileName() {
		return employeePhotoFileName;
	}

	public void setEmployeePhotoFileName(String employeePhotoFileName) {
		this.employeePhotoFileName = employeePhotoFileName;
	}

	public List<CrewMember> getCrewMembers() {
		return crewMembers;
	}
}
