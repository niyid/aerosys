/**
 * vasworks-common May 18, 2009
 */
package com.vasworks.service;

import java.util.List;

/**
 * @author developer
 * 
 */
public interface GeneralDAOService {
	Object find(Class<?> clazz, Object id);

	List<? extends Object> list(Class<?> clazz, String... orderBy);
}
