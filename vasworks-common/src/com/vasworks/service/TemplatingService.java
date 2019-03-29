/**
 * 
 */
package com.vasworks.service;

import java.util.Map;

/**
 * @author developer
 * 
 */
public interface TemplatingService {

	/**
	 * @param string
	 * @return
	 */
	String fillTemplate(String string, Map<String, Object> data) throws TemplatingException;
}
