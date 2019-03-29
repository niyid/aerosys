/**
 * 
 */
package com.vasworks.security.action;

import java.util.List;


import com.opensymphony.xwork2.Action;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserDelegation;
import com.vasworks.security.service.UserService;
import com.vasworks.security.service.UserServiceException;
import com.vasworks.struts.BaseAction;

/**
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class DelegationAction extends BaseAction {
	private UserService userService;

	private List<AppUser> delegatedTo = null, delegatedFrom = null;
	private String email;

	/**
	 * 
	 */
	public DelegationAction(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the delegatedTo
	 */
	public List<AppUser> getDelegatedTo() {
		if (this.delegatedTo == null) {
			this.delegatedTo = userService.getDelegatedTo(getUser());
		}
		return this.delegatedTo;
	}

	/**
	 * @return the delegatedFrom
	 */
	public List<AppUser> getDelegatedFrom() {
		if (this.delegatedFrom == null) {
			this.delegatedFrom = userService.getDelegatedFrom(getUser());
		}
		return this.delegatedFrom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String execute() {
		return Action.SUCCESS;
	}

	/**
	 * Method executed to grant access to somebody
	 * 
	 * @return
	 */
	public String to() {
		if (this.email == null || this.email.trim().length() == 0) {
			addFieldError("email", "Please provide the email of the delegatee you wish to remove from the list.");
			return Action.INPUT;
		}

		AppUser delegate = userService.lookup(this.email);
		if (delegate == null) {
			addFieldError("email", "Could not find user with email: " + this.email);
			return Action.INPUT;
		}

		try {
			userService.addDelegation(getUser(), delegate);
			return "reload";
		} catch (UserServiceException e) {
			addActionError(e.getMessage());
			return "input";
		}
	}

	public String delete() {
		try {
			userService.deleteDelegation(getUser(), this.email);
		} catch (UserServiceException e) {
			addActionError(e.getMessage());
			return "input";
		}
		return "reload";
	}

	public String switchuser() {
		try {
			UserDelegation delegation = userService.findDelegation(getPrincipal(), this.email);
			if (delegation == null) {
				addActionError("You are not delegated to manage data for " + this.email + "");
				return Action.INPUT;
			} else {
				userService.switchUser(getPrincipal(), delegation);
				return "switch";
			}
		} catch (UserServiceException e) {
			addActionError(e.getMessage());
			return Action.INPUT;
		}
	}

	public String unswitch() {
		userService.unswitchUser();
		return "switch";
	}
}
