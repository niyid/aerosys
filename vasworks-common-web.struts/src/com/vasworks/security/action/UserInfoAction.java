/**
 * vasworks-common-web.struts Jun 8, 2009
 */
package com.vasworks.security.action;


import com.opensymphony.xwork2.Action;
import com.vasworks.security.service.UserService;
import com.vasworks.struts.BaseAction;

/**
 * AppUser information action exposes the currently logged in user (or switched-to user)
 * 
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class UserInfoAction extends BaseAction {
	private UserService userService = null;

	/**
	 * @param userService
	 * 
	 */
	public UserInfoAction(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		return Action.SUCCESS;
	}
	
	public boolean isSwitched() {
		return this.userService.isUserSwitched();
	}
}
