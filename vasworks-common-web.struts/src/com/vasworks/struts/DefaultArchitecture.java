/**
 * vasworks-common-web.struts Feb 2, 2010
 */
package com.vasworks.struts;

import org.aspectj.lang.annotation.Aspect;

/**
 *
 * @author developer
 */
@Aspect
public class DefaultArchitecture extends WebArchitecture {

	/**
	 * @param applicationNotifications
	 */
	public DefaultArchitecture(ApplicationNotifications applicationNotifications) {
		super(applicationNotifications);
	}

}
