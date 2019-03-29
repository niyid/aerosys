/**
 * 
 */
package com.vasworks.struts;

import java.util.List;


import com.opensymphony.xwork2.Preparable;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.service.UserSupervisionService;

/**
 * Dashboard action class.
 * 
 * 
 * 
 * @author developer
 * 
 */
@SuppressWarnings("serial")
public class Dashboard extends BaseAction implements Preparable {
	private UserSupervisionService userSupervisionService=null;

	
	/**
	 * @param userSupervisionService the userSupervisionService to set
	 */
	public void setUserSupervisionService(UserSupervisionService userSupervisionService) {
		this.userSupervisionService = userSupervisionService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.action.BaseActionPar#prepare()
	 */
	@Override
	public void prepare() {
		super.prepare();
	}

	public List<AppUser> getSupervisors() {
		return this.userSupervisionService.getSupervisors(getPrincipal());
	}
}
