/**
 * 
 */
package com.vasworks.security.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.vasworks.security.model.Preference;
import com.vasworks.security.model.AppUser;
import com.vasworks.security.model.AppUser.AuthenticationType;
import com.vasworks.security.model.UserDelegation;
import com.vasworks.security.model.UserLookup;
import com.vasworks.security.model.UserPasswordRequest;
import com.vasworks.security.model.UserRole;
import com.vasworks.security.model.UserStatus;
import com.vasworks.security.service.UserImportService;
import com.vasworks.security.service.UserService;
import com.vasworks.security.service.UserServiceException;
import com.vasworks.util.PagedResult;
import com.vasworks.util.StringUtil;

/**
 * @author developer
 * 
 */
public class UserServiceImpl implements UserService {
	private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

	private UserImportService userImportService = null;
	
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	private String applicationName = null;

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplication(String applicationId) {
		LOG.warn("Application ID configured to: " + applicationId);
		this.applicationName = applicationId;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @param userImportService the userImportService to set
	 */
	public void setUserImportService(UserImportService userImportService) {
		this.userImportService = userImportService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public AppUser loadUserByUsername(String username) {
		LOG.debug("loadUserByUsername - " + username);
		LOG.debug("loadUserByUsername - " + this.entityManager);

		
		if (username == null)
			return null;

		try {			
			AppUser u = (AppUser) this.entityManager.createQuery("from AppUser u where u.userName=:username").setParameter("username", username).setMaxResults(1)
					.getSingleResult();
			return u;
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AppUser find(long id) {
		return entityManager.find(AppUser.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> findAll() {
		Query query = this.entityManager.createQuery("from AppUser u where u.status!=? order by u.lastName, u.firstName").setParameter(1, UserStatus.DELETED);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public PagedResult<AppUser> findAll(int start, int maxResults) {
		PagedResult<AppUser> paged = new PagedResult<AppUser>(start, maxResults);

		paged.setResults(this.entityManager.createQuery("from AppUser u where u.status!=:status order by u.lastName, u.firstName").setParameter("status",
				UserStatus.DELETED).setFirstResult(start).setMaxResults(maxResults).getResultList());
		if (paged.getResults() != null && paged.getResults().size() > 0) {
			// find max records
			paged.setTotalHits(((Long) this.entityManager.createQuery("select count (*) from AppUser u where u.status!=:status").setParameter("status",
					UserStatus.DELETED).getSingleResult()).intValue());
		}
		return paged;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public PagedResult<AppUser> findAll(int startAt, int maxResults, String filter, boolean includeImportService) {
		PagedResult<AppUser> paged = new PagedResult<AppUser>(startAt, maxResults);

		String filterLike = "%" + filter + "%";

		paged
				.setResults(this.entityManager
						.createQuery(
								"from AppUser u where u.status!=:status and (u.lastName like :filterLike or u.userName like :filterLike or u.mail like :filterLike or :filter in elements(u.lookups)) order by u.lastName, u.firstName")
						.setParameter("status", UserStatus.DELETED).setParameter("filterLike", filterLike).setParameter("filter", filter).setFirstResult(
								startAt).setMaxResults(maxResults).getResultList());

		if (paged.getResults() != null && paged.getResults().size() > 0) {
			// find max records
			paged
					.setTotalHits(((Long) this.entityManager
							.createQuery(
									"select count (*) from AppUser u where u.status!=:status and (u.lastName like :filterLike or u.userName like :filterLike or u.mail like :filterLike or :filter in elements(u.lookups))")
							.setParameter("status", UserStatus.DELETED).setParameter("filterLike", filterLike).setParameter("filter", filter).getSingleResult())
							.intValue());
		}

		if (this.userImportService != null && (paged.getTotalHits() == 0 || includeImportService)) {
			LOG.debug("DB search found no matching user " + filterLike + ". Trying import service.");
			// try import service search
			List<AppUser> imported = this.userImportService.findAll(filter);
			if (imported != null) {
				paged.getResults().addAll(0, imported);
				paged.setTotalHits(paged.getTotalHits() + imported.size());
				paged.setMaxResults((int) paged.getTotalHits());
			}
		}

		return paged;
	}

	@Override
	@Transactional(readOnly = true)
	public String remove(long id) {
		AppUser user = find(id);
		if (user != null) {
			user.setStatus(UserStatus.DELETED);
			entityManager.merge(user);
			return "success";
		} else {
			return "input";
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void save(AppUser user) {
		AppUser userPreSave = this.entityManager.find(AppUser.class, user.getId());
		try {
			try {
				List<UserLookup> lookups = userPreSave.getLookups();
				if (lookups != null)
					for (int i = lookups.size() - 1; i >= 0; i--)
						if (lookups.get(i).getIdentifier() == null || lookups.get(i).getIdentifier().trim().length() == 0) {
							UserLookup lookup = lookups.remove(i);
							this.entityManager.remove(lookup);
						} else
							lookups.get(i).setUser(user);

				List<UserRole> roles = userPreSave.getRoles();
				if (roles != null)
					for (int i = roles.size() - 1; i >= 0; i--)
						if (roles.get(i).getRole() == null || roles.get(i).getRole().trim().length() == 0) {
							UserRole role = roles.remove(i);
							this.entityManager.remove(role);
						} else
							roles.get(i).setUser(user);
			} catch (LazyInitializationException e) {

			}

			if (user.getId() == null) {
				LOG.debug("new user id is:  " + user.getId());
				entityManager.persist(user);
				LOG.debug("new user id is: now persisted!");
			} else {
				// update driver info
				LOG.trace("Merging user data for id=" + user.getId());
				entityManager.merge(user);
			}
		} catch (RuntimeException e) {
			LOG.error(e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateLoginData(AppUser user) {
		try {
			if (user.getId() == null) {
				LOG.debug("new user id is:  " + user.getId());
				entityManager.persist(user);
				LOG.debug("new user id is: now persisted!");
			} else {
				// update driver info
				LOG.trace("Merging user data for id=" + user.getId());
				entityManager.merge(user);
			}
		} catch (RuntimeException e) {
			LOG.error(e);
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#lookup(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public AppUser lookup(String identifier) {
		return lookup(identifier, true);
	}

	/*
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public AppUser lookup(String identifier, boolean allowImport) {
		LOG.info("Looking up user with identifier: " + identifier);
		try {
			return (AppUser) entityManager.createQuery("select ul.user from UserLookup ul where ul.identifier like :identifier").setParameter("identifier",
					identifier).getSingleResult();
		} catch (NoResultException e) {
			if (allowImport)
				return this.importUser(identifier);
		} catch (Exception e) {
			LOG.info("Lookup service: '" + identifier + "' did not find any matches. Error: " + e.getMessage());
		}
		return null;
	}

	/**
	 * @return
	 * @see com.vasworks.security.service.UserService#getDelegatedFrom(com.vasworks.security.model.AppUser)
	 */
	@Override
	public List<AppUser> getDelegatedFrom(AppUser user) {
		return getDelegatedFrom(user, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#getDelegatedFrom(com.devworks.par.model. AppUser, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> getDelegatedFrom(AppUser user, String application) {
		LOG.info("Listing user delegations to " + user + " in " + application);
		return entityManager.createQuery("select ud.owner from UserDelegation ud where ud.delegatedTo=:user and ud.application=:application").setParameter(
				"user", user).setParameter("application", application).getResultList();
	}

	@Override
	public List<AppUser> getDelegatedTo(AppUser user) {
		return this.getDelegatedTo(user, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#getDelegatedTo(com.devworks.par.model.User, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> getDelegatedTo(AppUser user, String application) {
		LOG.info("Listing user delegations of " + user + " in " + application);
		return entityManager.createQuery("select ud.delegatedTo from UserDelegation ud where ud.owner=:user and ud.application=:application").setParameter(
				"user", user).setParameter("application", application).getResultList();
	}

	@Override
	@Transactional
	public void addDelegation(AppUser owner, AppUser delegate) throws UserServiceException {
		this.addDelegation(owner, delegate, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#addDelegation(com.devworks.par.model.User, com.devworks.par.model.User)
	 */
	@Override
	@Transactional
	public void addDelegation(AppUser owner, AppUser delegate, String application) throws UserServiceException {
		LOG.info("Adding delegation from " + owner + " to " + delegate + " in " + application);
		List<AppUser> delegatedTo = getDelegatedTo(owner, application);
		if (delegatedTo.contains(delegate)) {
			LOG.info("Delegation exists from " + owner + " to " + delegate + " in " + application);
			throw new UserServiceException("AppUser is already on delegation list");
		}

		UserDelegation userDelegation = new UserDelegation();
		userDelegation.setApplication(application);
		userDelegation.setOwner(owner);
		userDelegation.setDelegatedTo(delegate);
		entityManager.persist(userDelegation);
		LOG.info("Delegation added from " + owner + " to " + delegate + " in " + application);
	}

	@Override
	@Transactional
	public void deleteDelegation(AppUser user, String identifier) throws UserServiceException {
		this.deleteDelegation(user, identifier, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#deleteDelegation(com.devworks.par.model. AppUser, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteDelegation(AppUser user, String email, String application) throws UserServiceException {
		LOG.info("Removing delegation from " + user + " to " + email + " in " + application);
		AppUser delegate = lookup(email);
		if (delegate == null) {
			throw new UserServiceException("Could not find delegated user.");
		}
		// list delegations
		List<UserDelegation> delegations = entityManager.createQuery(
				"from UserDelegation ud where ud.application=:application and ud.owner=:owner and ud.delegatedTo=:delegatedTo").setParameter("application",
				application).setParameter("owner", user).setParameter("delegatedTo", delegate).getResultList();

		// Remove delegations
		for (UserDelegation delegation : delegations) {
			entityManager.remove(delegation);
		}
	}

	@Override
	public UserDelegation findDelegation(AppUser user, String identifier) throws UserServiceException {
		return this.findDelegation(user, identifier, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#deleteDelegation(com.devworks.par.model. AppUser, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDelegation findDelegation(AppUser user, String identifier, String application) throws UserServiceException {
		LOG.info("Looking up delegation from " + user + " to " + identifier + " in " + application);
		AppUser delegate = lookup(identifier);
		if (delegate == null) {
			throw new UserServiceException("Could not find delegated user.");
		}
		try {
			// return delegation
			return (UserDelegation) entityManager.createQuery(
					"from UserDelegation ud where ud.application=:application and ud.owner=:owner and ud.delegatedTo=:delegatedTo").setParameter("application",
					application).setParameter("owner", delegate).setParameter("delegatedTo", user).getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			LOG.error("AppUser " + user.getUsername() + " tried to access delegation of " + identifier + ". Delegation does not exist.");
			return null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#switchUser(com.devworks.par.model.User, com.devworks.par.model.UserDelegation)
	 */
	@Override
	public void switchUser(AppUser user, UserDelegation delegation) {
		if (delegation.getDelegatedTo().getId().equals(user.getId())) {
			LOG.info("AppUser " + user.getUsername() + " switching TO user " + delegation.getOwner().getUsername());
			switchUser(delegation.getOwner());
		} else {
			LOG
					.error("AppUser " + user.getUsername() + " tried switching to user " + delegation.getOwner().getUsername()
							+ ", but the delegation does not match.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#switchUser(com.devworks.par.model.User)
	 */
	@Override
	public void switchUser(AppUser targetUser) {
		LOG.info("Switching TO user " + targetUser.getUsername());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser currentAuthentication = (AppUser) authentication.getPrincipal();
		if (currentAuthentication.getImpersonator() != null) {
			LOG.warn("Already contains old principal!");
			authentication = currentAuthentication.getImpersonator();
		}
		LOG.debug("Has granted authorities: " + targetUser.getAuthorities().size());
		for (GrantedAuthority x : targetUser.getAuthorities()) {
			LOG.debug("Granted auth: " + x);
		}
		targetUser.setImpersonator(authentication);
		UsernamePasswordAuthenticationToken impersonatedAuthentication = new UsernamePasswordAuthenticationToken(targetUser, "nopassword", targetUser
				.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(impersonatedAuthentication);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#unswitchUser()
	 */
	@Override
	public void unswitchUser() {
		LOG.info("Unswitching user.");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser currentAuthentication = (AppUser) authentication.getPrincipal();
		if (currentAuthentication.getImpersonator() == null) {
			LOG.warn("Old authentication not found. Cannot unswitch.");
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(currentAuthentication.getImpersonator());
	}

	/**
	 * @see com.vasworks.security.service.UserService#isUserSwitched()
	 */
	@Override
	public boolean isUserSwitched() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			return false;
		AppUser currentAuthentication = (AppUser) authentication.getPrincipal();
		if (currentAuthentication == null)
			return false;
		return currentAuthentication.getImpersonator() != null;
	}

	@Override
	@Transactional(readOnly = false)
	public void setPassword(AppUser user, String passwd1) {
		this.setPassword(user, passwd1, true);
	}

	/**
	 * Sets user's password and configures the account to use PASSWORD login instead of LDAP.
	 * 
	 * @see com.devworks.par.service.UserService#setPassword(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void setPassword(AppUser user, String passwd1, boolean changeAuthenticationType) {
		LOG.info("Setting password for " + user.getUsername() + ".");
		if (changeAuthenticationType) {
			LOG.info("Authentication type set to PASSWORD");
			user.setAuthenticationType(AuthenticationType.PASSWORD);
		}
		user.setPassword(Sha512DigestUtils.shaHex(passwd1));
		updateLoginData(user);
	}

	/**
	 * Clears user's password and configures the account to use DEFAULT authentication mechanism (probably LDAP)
	 * 
	 * @see com.devworks.par.service.UserService#clearPassword(com.devworks.AppUser.model.User)
	 */
	@Override
	@Transactional
	public void clearPassword(AppUser user) {
		LOG.info("Clearing PASSWORD authentication for " + user.getUsername() + ". Switching to DEFAULT.");
		user.setAuthenticationType(AuthenticationType.DEFAULT);
		user.setPassword(null);
		updateLoginData(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#isPasswordValid(com.devworks.par.model.User, java.lang.String)
	 */
	@Override
	public boolean isPasswordValid(AppUser user, String password) {
		LOG.info("Checking password for " + user);
		if (password == null) {
			LOG.debug("Provided null password for " + user);
			return false;
		}
		String hash = Sha512DigestUtils.shaHex(password);
		LOG.debug("Pass-hash: " + hash);
		LOG.info("Password check for " + user + ": " + hash.equals(user.getPassword()));
		return hash.equals(user.getPassword());
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserRole> getUserRoles(AppUser user) {
		return this.getUserRoles(user, this.applicationName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#getUserRoles(com.devworks.par.model.User, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<UserRole> getUserRoles(AppUser user, String application) {
		LOG.info("Loading roles " + application + ".* for " + user);
		return this.entityManager.createQuery("from UserRole ur where ur.application=:application and ur.user=:user").setParameter("application", application)
				.setParameter("user", user).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#requestPassword(com.devworks.par.model.User)
	 */
	@Override
	@Transactional
	public String requestPassword(AppUser user) {
		LOG.info("Creating password request for " + user);
		UserPasswordRequest request = new UserPasswordRequest();
		request.setDateGenerated(Calendar.getInstance().getTime());
		request.setKey(com.vasworks.util.StringUtil.getRandomAlphaNumericString(10));
		request.setUser(user);
		request.setStatus(0);
		this.entityManager.persist(request);
		LOG.info("Password request for " + user + " has key " + request.getKey());
		return request.getKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#generatePassword(com.devworks.par.model. AppUser)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String generatePassword(AppUser user, String key) {
		LOG.info("Generating password for " + user + " with key " + key);
		for (UserPasswordRequest request : (List<UserPasswordRequest>) this.entityManager.createQuery(
				"from UserPasswordRequest r where r.user=:user and r.key=:key and r.status=0").setParameter("user", user).setParameter("key", key)
				.getResultList()) {
			LOG.info("Regenerating password for user " + user);
			String newPassword = StringUtil.getRandomAlphaNumericString(7);
			request.setDateUsed(Calendar.getInstance().getTime());
			request.setStatus(1);
			this.entityManager.merge(request);
			this.setPassword(user, newPassword);
			return newPassword;
		}
		LOG.info("No matching password request was found for " + user.getUsername() + " and key " + key);
		return null;
	}

	@Override
	public List<AppUser> findByRole(String role) {
		return this.findByRole(this.applicationName, role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.devworks.par.service.UserService#findByRole(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AppUser> findByRole(String application, String role) {
		return this.entityManager.createQuery(
				"select ur.user from UserRole ur where ur.application=:application and ur.role=:role order by ur.user.lastName, ur.user.firstName")
				.setParameter("application", application).setParameter("role", role).getResultList();
	}

	/**
	 * @see com.vasworks.security.service.UserService#importUser(java.lang.String)
	 */
	@Override
	@Transactional
	public AppUser importUser(String username) {
		// try loading existing user
		AppUser existingUser = this.loadUserByUsername(username);
		if (existingUser != null) {
			LOG.debug("Tried to import existing user: " + username + ", returning existing entity.");
			return existingUser;
		}

		if (userImportService == null) {
			LOG.warn("AppUser import service not specified. Cannot import users.");
			return null;
		}

		AppUser importedUser = userImportService.findUser(username);
		if (importedUser == null) {
			LOG.warn("AppUser " + username + " not found by user import service.");
		} else {
			LOG.warn("AppUser " + username + " found by import service. Persisting now.");
			this.entityManager.persist(importedUser);
		}
		return importedUser;
	}

	/**
	 * @see com.vasworks.security.service.UserService#findByName(java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AppUser> findByName(String lookup, int maxResults) {
		lookup = lookup + "%";
		return this.entityManager.createQuery("from AppUser u where u.lastName like :lookup or u.firstName like :lookup order by u.lastName, u.firstName")
				.setParameter("lookup", lookup).setMaxResults(maxResults).getResultList();
	}

	/**
	 * @see com.vasworks.security.service.UserService#getUserRoles()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getUserRoles() {
		return this.entityManager.createQuery("select distinct ur.role from UserRole ur order by ur.role").getResultList();
	}

	/**
	 * @see com.vasworks.security.service.UserService#findByRole(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PagedResult<AppUser> findByRole(String role, int startAt, int maxResults) {
		PagedResult<AppUser> paged = new PagedResult<AppUser>(startAt, maxResults);

		paged.setResults(this.entityManager.createQuery(
				"select u from AppUser u inner join u.roles r where u.status!=:status and r.role=:role order by u.lastName, u.firstName").setParameter("status",
				UserStatus.DELETED).setParameter("role", role).setFirstResult(startAt).setMaxResults(maxResults).getResultList());
		if (paged.getResults() != null && paged.getResults().size() > 0) {
			// find max records
			paged.setTotalHits(((Long) this.entityManager.createQuery(
					"select count (u) from AppUser u inner join u.roles r where u.status!=:status and r.role=:role").setParameter("status", UserStatus.DELETED)
					.setParameter("role", role).getSingleResult()).intValue());
		}
		return paged;
	}

	@Override
	public AppUser findByStaffID(String staffID) {
		try {
			return (AppUser) this.entityManager.createQuery("from AppUser u where u.staffId=:staffID").setParameter("staffID", staffID).getSingleResult();
		} catch (NoResultException e) {
			LOG.error("No staff found for ID: " + staffID, e);
			return null;
		} catch (NonUniqueResultException e) {
			LOG.error("Multiple staff found for ID: " + staffID, e);
			return null;
		}
	}

	@Override
	@Transactional
	public Preference getPreference(AppUser user, String key) {
		Preference pref = null;
		try {
			pref = (Preference) entityManager.createQuery("FROM Preference p WHERE p.user=:user AND p.preferenceKey=:key").setParameter("user", user).setParameter("key", key).getSingleResult();			
		} catch (NoResultException e) {
			LOG.info("No preference found for user '" + user + "' and key '" + key + "'.");
		}

		return pref; 
	}

	@Override
	@Transactional
	public Preference getPreference(Long id) {
		Preference pref = null;
		try {
			pref = entityManager.find(Preference.class, id);			
		} catch (NoResultException e) {
			LOG.info("No preference found for id '" + id + "'.");
		}

		return pref;
	}

	@Override
	@Transactional
	public void addPreference(AppUser user, String key, Object value) throws Exception {
		LOG.debug("addPreference " + user + " " + key + " " + value);
		
		if(getPreference(user, key) == null) {
			Preference pref = Preference.newInstance(key, value);
			
			entityManager.persist(pref);
		} else {
			throw new Exception("Preference already exists.");
		}
	}

	@Override
	@Transactional
	public void addPreference(Preference pref) throws Exception {
		LOG.debug("addPreference " + pref);
		
		if(getPreference(pref.getUser(), pref.getPreferenceKey()) == null) {
			entityManager.persist(pref);	
		} else {
			throw new Exception("Preference already exists.");
		}
	}
	
	@Override
	@Transactional
	public void setPreference(AppUser user, String key, Object value) {
		Preference pref = getPreference(user, key);
		pref.setPreferenceValue(value);
		
		entityManager.merge(pref);
	}
	
	@Override
	@Transactional
	public void setPreference(Long id, Object value) {
		Preference pref = entityManager.find(Preference.class, id);
		pref.setPreferenceValue(value);
		
		entityManager.merge(pref);
	}
}
