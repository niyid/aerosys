/**
 * travelauth.Struts May 12, 2009
 */
package com.vasworks.service;

import com.vasworks.util.PagedResult;

/**
 * @author developer
 * 
 */
public interface SimpleDaoService<T> {
	PagedResult<T> list(int startAt, int maxResults);

	PagedResult<T> list(int startAt, int maxResults, String[] orderBy);

	T find(Object id);

	void add(T entity);
	
	void addOrMerge(T entity);

	void refresh(T entity);

	void merge(T entity);

	void remove(T entity);
}
