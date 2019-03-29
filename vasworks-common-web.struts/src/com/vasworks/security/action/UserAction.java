/**
 * 
 */
package com.vasworks.security.action;

import java.util.ArrayList;
import java.util.List;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.UserLookup;
import com.vasworks.security.model.UserRole;
import com.vasworks.security.service.UserService;
import com.vasworks.util.PagedResult;

/**
 * @author aafolayan
 * 
 */
@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements Preparable {
	/** Service object to be injected by DI container */
	private UserService userService;
	/** Model object to be accessed on top of the Value Stack */
	private PagedResult<AppUser> users;
	/** Id of user to work with */
	private Long id;
	/** Model object to be accessed on top of the Value Stack */
	private AppUser user;
	/** Model object to be accessed on top of the Value Stack */
	private List<UserRole> roles;
	/** Model object to be accessed on top of the Value Stack */
	private List<UserLookup> lookups;
	private String application;
	private int startAt = 0, maxResults = 50;
	/** Filter to be used when searching for users */
	private String filter = null;
	private boolean includeImportService = false;

	/**
	 * @param userLookupService
	 * @param userRoleService
	 * @param userService
	 */
	public UserAction(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Set search filter
	 * 
	 * @param filter
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	/**
	 * @param includeImportService the includeImportService to set
	 */
	public void setExtensiveSearch(boolean includeImportService) {
		this.includeImportService = includeImportService;
	}
	
	public boolean getExtensiveSearch() {
		return this.includeImportService;
	}

	/**
	 * Get search filter
	 * 
	 * @return
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param startAt the startAt to set
	 */
	public void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	public String execute() {
		users = userService.findAll(startAt, maxResults);
		return Action.SUCCESS;
	}

	public String search() {
		users = userService.findAll(startAt, maxResults, filter, this.includeImportService);
		if (users.getTotalHits() == 1) {
			user = users.getResults().get(0);
			if (user.getId() == null) {
				addActionMessage("Found one user using import service.");
				this.lookups = user.getLookups();
				this.roles = user.getRoles();
				addBlanks();
				return Action.INPUT;
			} else
				return "found-one";
		} else if (users.getTotalHits() == 0) {
			addActionMessage("No matching users found.");
			return Action.SUCCESS;
		} else
			return Action.SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	@Override
	public void prepare() {
		if (id != null) {
			user = userService.find(id);
			roles = user.getRoles();
			lookups = user.getLookups();

			addBlanks();
		} else {
			// for new entries
			roles = new ArrayList<UserRole>();
			lookups = new ArrayList<UserLookup>();
			addBlanks();
		}
	}

	private void addBlanks() {
		LOG.debug("Adding blank fields");

		// add blank role for adding
		UserRole blankRole = new UserRole();
		blankRole.setUser(user);
		blankRole.setApplication(this.application);
		roles.add(blankRole);

		// add blank lookup for adding
		UserLookup blankLookup = new UserLookup();
		blankLookup.setUser(user);
		lookups.add(blankLookup);

		LOG.debug("Blanks added");
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the users
	 */
	public PagedResult<AppUser> getPaged() {
		return users;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AppUser user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public AppUser getUser() {
		return user;
	}

	/**
	 * @return the role
	 */
	public List<UserRole> getRoles() {
		return roles;
	}

	/**
	 * @param role the role to set
	 */
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	/**
	 * @return the lookup
	 */
	public List<UserLookup> getLookups() {
		return lookups;
	}

	/**
	 * @param lookup the lookup to set
	 */
	public void setLookup(List<UserLookup> lookups) {
		this.lookups = lookups;
	}

	public String update() {
		save();
		// addBlanks();
		return "reload";
	}

	public String save() {
		user.setLookups(lookups);
		user.setRoles(roles);
		userService.save(user);
		return Action.SUCCESS;
	}

	public String input() {
		return Action.INPUT;
	}

	public String delete() {
		user.setLookups(null);
		user.setRoles(null);
		return userService.remove(id);
	}

	public String switchto() {
		userService.switchUser(this.user);
		return "switch";
	}

	public String unswitch() {
		userService.unswitchUser();
		return "switch";
	}
}
