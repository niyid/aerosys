/**
 * vasworks-common-web.struts Oct 15, 2009
 */
package com.vasworks.security.action;

import java.util.List;


import com.opensymphony.xwork2.Action;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.service.UserService;
import com.vasworks.struts.BaseAction;
import com.vasworks.util.PagedResult;

/**
 * Action class to manage application roles and users in those roles.
 * 
 * @author developer
 */
@SuppressWarnings("serial")
public class UserRolesAction extends BaseAction {
	private UserService userService;
	private List<String> allRoles;
	private String role=null;
	private int startAt = 0, maxResults = 50;
	private PagedResult<AppUser> paged;
	
	/**
	 *  
	 * @param userService 
	 */
	public UserRolesAction(UserService userService) {
		this.userService=userService;
	}
	
	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}
	
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * @return the allRoles
	 */
	public List<String> getAllRoles() {
		return this.allRoles;
	}
	
	/**
	 * @return the paged
	 */
	public PagedResult<AppUser> getPaged() {
		return this.paged;
	}
	
	/**
	 * Default action method lists all roles available in the application
	 * 
	 * @see com.vasworks.struts.BaseAction#execute()
	 */
	@Override
	public String execute() {
		this.allRoles=this.userService.getUserRoles();
		if (this.role!=null) {
			this.paged=this.userService.findByRole(this.role, this.startAt, this.maxResults);
		}
		return Action.SUCCESS;
	}
}
