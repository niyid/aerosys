package com.vasworks.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;

public interface DaoService {
	
	/**
	 * 
	 * @param entity
	 * @param userId
	 * @return
	 * @throws PersistenceException
	 */
	public Object insert(Object entity, Long userId) throws PersistenceException;
	
	/**
	 * 
	 * @param entities
	 * @throws PersistenceException
	 */
	public void insertCollection(Collection<?> entities) throws PersistenceException;
	
	/**
	 * 
	 * @param entity
	 * @throws PersistenceException
	 */
	public void insertCollectionItem(Object entity) throws PersistenceException;
	
	/**
	 * 
	 * @param entity
	 * @param userId
	 * @throws PersistenceException
	 */
	public void update(Object entity, Long userId) throws PersistenceException;
	
	/**
	 * 
	 * @param entities
	 * @param userId
	 * @throws PersistenceException
	 */
	public void updateCollection(Collection<?> entities) throws PersistenceException;
	
	/**
	 * 
	 * @param entity
	 * @param userId
	 * @throws PersistenceException
	 */
	public void remove(Object entity) throws PersistenceException;
	
	/**
	 * 
	 * @param entityId
	 * @param entityType
	 * @throws PersistenceException
	 */
	public void remove(Object entityId, Class<?> entityType) throws PersistenceException;
	
	/**
	 * 
	 * @param entities
	 * @param userId
	 * @throws PersistenceException
	 */
	public void removeCollection(Collection<?> entities) throws PersistenceException;
	
	/**
	 * 
	 * @param entityId
	 * @param entityClass
	 * @return
	 * @throws NoResultException
	 */
	public Object find(Object entityId, Class<?> entityClass) throws NoResultException;

	/**
	 * 
	 * @param entityId
	 * @param entityClass
	 * @param lazyProperties
	 * @return
	 * @throws NoResultException
	 */
	public Object findAndPopulateLazyFields(Object entityId, Class<?> entityClass, String[] lazyProperties) throws NoResultException;

	/**
	 * 
	 * @param entityClass
	 * @return
	 * @throws NoResultException
	 */
	public List<?> list(Class<?> entityClass) throws NoResultException;
	
    /**
     * 
     * @param entityClass
     * @param jpqlQuery
     * @param params
     * @return
     * @throws NoResultException
     */    
	public List<?> findEntities(Class<?> entityClass, String jpqlQuery, Map<String, Object> params) throws NoResultException;

    /**
     * 
     * @param entityClass
     * @param jpqlQuery
     * @param params
     * @return
     * @throws Exception
     */
    public List<?> findEntities(Class<?> entityClass, String jpqlQuery, Object... params) throws NoResultException;
	
    /**
     * 
     * @param entityClass
     * @param namedQuery
     * @param params
     * @return
     * @throws NoResultException
     */    
	public List<?> callNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> params) throws NoResultException;

    /**
     * 
     * @param entityClass
     * @param namedQuery
     * @param params
     * @return
     * @throws NoResultException
     */
    public List<?> callNamedQuery(Class<?> entityClass, String namedQuery, Object... params) throws NoResultException;
	
    /**
     * 
     * @param entityClass
     * @param jpqlQuery
     * @param params
     * @return
     * @throws NoResultException
     */    
	public Object findEntity(Class<?> entityClass, String jpqlQuery, Map<String, Object> params) throws NoResultException;

    /**
     * 
     * @param entityClass
     * @param jpqlQuery
     * @param params
     * @return
     * @throws NoResultException
     */
    public Object findEntity(Class<?> entityClass, String jpqlQuery, Object... params) throws NoResultException;
	
	/**
	 * 
	 * @param searchtarget
	 * @param parameters
	 * @return
	 */
	public StringBuilder searchQueryBuilder(Class<?> searchtarget, Map<String, Object> parameters);
	
	/**
	 * 
	 * @param countTarget
	 * @return
	 */
	public long count(Class<?> countTarget, String propertyOgnl);
	
	/**
	 * 
	 * @param userId
	 * @param personnelId
	 * @param organizationId
	 */
	void addPersonnel(Long userId, Long personnelId, Long organizationId);
	
	/**
	 * 
	 * @param userId
	 * @param currencyCode
	 * @param countryCode
	 * @param organization
	 */
	void addOrganization(Long userId, String currencyCode, String countryCode, Organization organization);

	/**
	 * 
	 * @param userId
	 * @param currencyCode
	 * @param countryCode
	 * @param organization
	 */
	void updateOrganization(Long userId, String currencyCode, String countryCode, Organization organization);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	Currency findDefaultCurrency(Long userId);
}
