package com.vasworks.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vasworks.entity.Auditable;
import com.vasworks.security.model.AppUser;

public class AuditInterceptor extends EmptyInterceptor {
	/**
	 * 
	 */
	private static final String DEFAULTUSERNAME = "SYSTEM";

	private static final Log LOG = LogFactory.getLog(AuditInterceptor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

	}

	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

		boolean changedFlag = false;
		if (entity instanceof Auditable) {
			LOG.trace("Auditing " + entity.getClass());
			String authenticationName = DEFAULTUSERNAME;
			Date auditDate = new Date();
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Object u = null;
				if (authentication != null)
					u = authentication.getPrincipal();
				if (u == null) {
					// No change
				} else if (u instanceof AppUser) {
					AppUser user = (AppUser) u;
					authenticationName = getAuditedName(user);
				}
			} catch (Exception e) {
				LOG.warn(e);
			}

			for (int i = 0; i < propertyNames.length; i++) {
				if ("lastUpdated".equals(propertyNames[i])) {
					currentState[i] = auditDate;
					LOG.trace("Auditing lastUpdate: " + currentState[i]);
					changedFlag = true;
				} else if ("createdDate".equals(propertyNames[i]) && currentState[i] == null) {
					// only update if null
					if (previousState[i] == null) {
						LOG.trace("Current value createdDate: " + currentState[i] + " " + previousState[i]);
						currentState[i] = auditDate;
						LOG.trace("Auditing createdDate: " + currentState[i]);
					} else {
						currentState[i] = previousState[i];
					}
					changedFlag = true;
				} else if ("lastUpdatedBy".equals(propertyNames[i])) {
					currentState[i] = authenticationName;
					LOG.trace("Auditing lastUpdatedBy: " + currentState[i]);
					changedFlag = true;
				} else if ("createdBy".equals(propertyNames[i]) && currentState[i] == null) {
					LOG.trace("Current value createdBy: " + currentState[i] + " " + previousState[i]);
					// only update if null
					if (previousState[i] == null) {
						currentState[i] = authenticationName;
						LOG.trace("Auditing createdBy: " + currentState[i]);
						changedFlag = true;
					} else {
						currentState[i] = previousState[i];
					}
				}
			}
		}
		return changedFlag;
	}

	/**
	 * @param user
	 * @return
	 */
	private String getAuditedName(AppUser user) {
		if (user == null)
			return DEFAULTUSERNAME;
		if (user.getImpersonator() == null)
			return user.getUsername();
		else
			return user.getUsername() + "/" + user.getImpersonator().getName();
	}

	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return false;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		boolean changedFlag = false;

		if (entity instanceof Auditable) {
			String authenticationName = DEFAULTUSERNAME;
			Date auditDate = new Date();
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				Object u = null;
				if (authentication != null)
					u = authentication.getPrincipal();
				if (u == null) {
					// No change
				} else if (u instanceof AppUser) {
					AppUser user = (AppUser) u;
					authenticationName = getAuditedName(user);
				}
			} catch (Exception e) {
				LOG.warn(e);
			}

			for (int i = 0; i < propertyNames.length; i++) {
				if ("lastUpdated".equals(propertyNames[i])) {
					state[i] = auditDate;
					LOG.trace("Auditing lastUpdated: " + state[i]);
					changedFlag = true;
				} else if ("createdDate".equals(propertyNames[i])) {
					state[i] = auditDate;
					LOG.trace("Auditing createdDate: " + state[i]);
					changedFlag = true;
				} else if ("lastUpdatedBy".equals(propertyNames[i])) {
					state[i] = authenticationName;
					LOG.trace("Auditing lastUpdatedBy: " + state[i]);
					changedFlag = true;
				} else if ("createdBy".equals(propertyNames[i])) {
					state[i] = authenticationName;
					LOG.trace("Auditing createdBy: " + state[i]);
					changedFlag = true;
				}
			}
		}
		return changedFlag;
	}

	public void afterTransactionCompletion(Transaction tx) {
		// if (tx.wasCommitted()) {
		// // LOG.debug("Creations: " + creates + ", Updates: " + updates + ", Loads: " + loads);
		// }
	}

}