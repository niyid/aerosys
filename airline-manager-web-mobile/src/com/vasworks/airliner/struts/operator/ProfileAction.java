package com.vasworks.airliner.struts.operator;

import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Gender;
import com.vasworks.airliner.model.MaritalStatus;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.model.UserTitle;
import com.vasworks.airliner.struts.OperatorAction;

public class ProfileAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6786462982659418739L;
	
	private Passenger member;
	
	private String pwdConfirm;
	
	private Long titleId;
	
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
		
		member = (Passenger) operatorService.find(getBasketId(), Passenger.class);
		
		titleId = member.getTitlePrefix() != null ? member.getTitlePrefix().getId() : null;

		return forwardId;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "member.user.firstName", message = "'First Name' is required."),
			@RequiredStringValidator(fieldName = "member.user.lastName", message = "'Last Name' is required."),
			@RequiredStringValidator(fieldName = "member.user.password", message = "'Password' is required.")
		}
	)
	public String save() {
		LOG.debug("save()");
		String forwardId = "emptyPage";

		try {
			operatorService.updateMember(member, titleId, getUser().getId());
			addActionMessage("Profile successfully updated.");
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
		if(!(pwdConfirm != null && member != null && pwdConfirm.equals(member.getUser().getPassword()))) {
			addActionError("Password and confirmation do not match.");
		}
	}
	
	public Passenger getMember() {
		return member;
	}

	public void setMember(Passenger newUser) {
		this.member = newUser;
	}

	public String getPwdConfirm() {
		return pwdConfirm;
	}

	public void setPwdConfirm(String pwdConfirm) {
		this.pwdConfirm = pwdConfirm;
	}
	
	public Gender[] getGenderLov() {
		return Gender.values();
	}
	
	public MaritalStatus[] getMaritalStatusLov() {
		return MaritalStatus.values();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserTitle> getUserTitleLov() {
		return (List<UserTitle>) operatorService.list(UserTitle.class);
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
}
