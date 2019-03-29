/**
 * vasworks-common May 18, 2009
 */
package com.vasworks.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.vasworks.service.GeneralDAOService;
import com.vasworks.util.StringUtil;

/**
 * @author developer
 *
 */
public class GeneralDAOServiceImpl implements GeneralDAOService {

	private EntityManager entityManager;
	
	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * @see com.vasworks.service.GeneralDAOService#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	@Transactional(readOnly=true)
	public Object find(Class<?> clazz, Object id) {
		return this.entityManager.find(clazz, id);
	}

	/**
	 * @see com.vasworks.service.GeneralDAOService#list(java.lang.Class, java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<? extends Object> list(Class<?> clazz, String... orderBy) {
		return this.entityManager.createQuery("from " + clazz.getCanonicalName() + getOrderBy(orderBy)).getResultList();
	}

	/**
	 * @param orderBy
	 * @return
	 */
	private String getOrderBy(String[] orderBy) {
		if (orderBy==null || orderBy.length==0) return StringUtil.EMPTY;
		StringBuffer sb=new StringBuffer();
		for (String o : orderBy ) {
			if (o==null || o.length()==0) continue;
			sb.append(sb.length()==0 ? " order by " : ", ").append(o);
		}
		return sb.toString();
	}

}
