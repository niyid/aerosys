/**
 * vasworks-common-web.struts Aug 5, 2009
 */
package com.vasworks.security.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vasworks.security.service.ApplicationLockService;

/**
 * This particular {@link ApplicationLockService} implementation uses a static mutex to control application locking.
 * 
 * @author developer
 * 
 */
public class ApplicationLockServiceImpl implements ApplicationLockService {
	private static final Log LOG = LogFactory.getLog(ApplicationLockService.class);
	private static Object mutex = null;
	private String message;
	private boolean locked;

	static {
		synchronized (ApplicationLockServiceImpl.class) {
			if (ApplicationLockServiceImpl.mutex != null) {
				LOG.error("Mutex already exists. ApplicationLockService needs to be a singleton!");
				throw new RuntimeException("Mutex already exists. ApplicationLockService needs to be a singleton!");
			} else {
				mutex = new Object();
			}
		}
	}

	/**
	 * Get notification message
	 * 
	 * @see com.vasworks.security.ApplicationLockService#getLockMessage()
	 */
	@Override
	public String getLockMessage() {
		return this.message;
	}

	/**
	 * @see com.vasworks.security.ApplicationLockService#isLocked()
	 */
	@Override
	public boolean isLocked() {
		synchronized (ApplicationLockServiceImpl.class) {
			return this.locked;
		}
	}

	/**
	 * @see com.vasworks.security.ApplicationLockService#lock(java.lang.String)
	 */
	@Override
	public void lock(String message) {
		synchronized (ApplicationLockServiceImpl.class) {
			LOG.warn("Locking application, reason: " + message);
			this.locked = true;
			this.message = message;
			LOG.warn("Application now locked.");
		}
	}

	/**
	 * @see com.vasworks.security.ApplicationLockService#unlock()
	 */
	@Override
	public void unlock() {
		synchronized (ApplicationLockServiceImpl.class) {
			if (this.locked) {
				LOG.warn("Application currently locked, unlocking now.");
				this.locked = false;
				this.message = null;
				LOG.warn("Application unlocked.");
			} else {
				LOG.info("Application was not locked, not unlocking");
			}
		}
	}

}
