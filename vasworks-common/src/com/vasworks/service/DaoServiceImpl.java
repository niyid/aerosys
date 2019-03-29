package com.vasworks.service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.vasworks.entity.Country;
import com.vasworks.entity.Currency;
import com.vasworks.entity.Organization;
import com.vasworks.entity.Personnel;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DaoServiceImpl implements DaoService {
	public static final Log LOG = LogFactory.getLog(DaoServiceImpl.class);
	
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;
    	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	@Transactional
	public Object insert(Object entity, Long userId) throws PersistenceException {
		entityManager.persist(entity);
		
		return entity;
	}

	@Override
	@Transactional
	public void update(Object entity, Long userId) throws PersistenceException {
		entityManager.merge(entity);
	}

	@Override
	@Transactional
	public void remove(Object entity) throws PersistenceException {
		entityManager.remove(entity);
	}

	@Override
	@Transactional
	public void remove(Object entityId, Class<?> entityType) throws PersistenceException {
		entityManager.remove(entityManager.find(entityType, entityId));
	}
	
    @Override
    @Transactional
    public Object find(Object entityId, Class<?> entityClass) throws NoResultException {
        return entityManager.find(entityClass, entityId);
    }
    
    @Override
    @Transactional
    public Object findAndPopulateLazyFields(Object entityId, Class<?> entityClass, String[] lazyProperties) throws NoResultException {
    	StringBuilder jpql = new StringBuilder("select distinct o from " + entityClass.getSimpleName() + " o");
    	
   		for(String prop : lazyProperties) {
   			LOG.debug("Lazy Property: " + prop);
    		try {
    			if (prop != null && !prop.trim().isEmpty()) {
    				jpql.append(" left join fetch o." + prop);
                }
    		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		jpql.append(" where o.id = ?1");
    	
    	return findEntity(entityClass, jpql.toString(), entityId);
    }

	@Override
	@Transactional
	public List<?> list(Class<?> entityClass) throws NoResultException {
	    return entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o").getResultList();
	    
	}
	   
    @Override
    @Transactional
    public List<?> findEntities(Class<?> entityClass, String jpqlQuery, Map<String, Object> params) throws NoResultException {
        Query q = entityManager.createQuery(jpqlQuery);
        q = setParameters(q, params);
        List<?> result = q.getResultList();
        if (result != null) {
            LOG.info("Result " + result.size());
        }
        
        return result;
    }

	@Override
	@Transactional
    public List<?> findEntities(Class<?> entityClass, String jpqlQuery, Object... params) throws NoResultException {
        Query q = entityManager.createQuery(jpqlQuery);
        int index = 1;
        for (final Object parameter : params) {
            q = q.setParameter(index, parameter);
            index++;
        }
        return q.getResultList();
    }
	   
    @Override
    @Transactional
    public List<?> callNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> params) throws NoResultException {
        Query q = entityManager.createNamedQuery(namedQuery);
        q = setParameters(q, params);
        List<?> result = q.getResultList();
        if (result != null) {
            LOG.info("Result " + result.size());
        }
        
        return result;
    }

	@Override
	@Transactional
    public List<?> callNamedQuery(Class<?> entityClass, String namedQuery, Object... params) throws NoResultException {
        Query q = entityManager.createNamedQuery(namedQuery);
        int index = 1;
        for (final Object parameter : params) {
            q = q.setParameter(index, parameter);
            index++;
        }
        return q.getResultList();
    }
	   
    @Override
    @Transactional
    public Object findEntity(Class<?> entityClass, String jpqlQuery, Map<String, Object> params) throws NoResultException {
        Query q = entityManager.createQuery(jpqlQuery);
        q = setParameters(q, params);
        Object result = q.getSingleResult();
        if (result != null) {
            LOG.info("Result " + result);
        }
        
        return result;
    }

	@Override
	@Transactional
    public Object findEntity(Class<?> entityClass, String jpqlQuery, Object... params) throws NoResultException {
        Query q = entityManager.createQuery(jpqlQuery);
        int index = 1;
        for (final Object parameter : params) {
            q = q.setParameter(index, parameter);
            index++;
        }
        return q.getSingleResult();
    }

    private Query setParameters(Query query, Map<String, Object> params) {
        for (Entry<String, Object> paramEntry : params.entrySet()) {
            if (paramEntry.getValue() instanceof Date || paramEntry.getValue() instanceof java.sql.Date) {
                query.setParameter(paramEntry.getKey(), (Date) paramEntry.getValue(), TemporalType.DATE);
            }
            else if (paramEntry.getValue() instanceof java.sql.Time) {
                query.setParameter(paramEntry.getKey(), (Time) paramEntry.getValue(), TemporalType.TIME);
            }
            else if (paramEntry.getValue() instanceof java.sql.Timestamp) {
                query.setParameter(paramEntry.getKey(), (Timestamp) paramEntry.getValue(), TemporalType.TIMESTAMP);
            }
            else {
                query.setParameter(paramEntry.getKey(), paramEntry.getValue());
            }
        }
        return query;
    }

	@Override
	public StringBuilder searchQueryBuilder(Class<?> searchtarget,
			Map<String, Object> parameters) {

		StringBuilder b = new StringBuilder();
		
		b.append("select o from ");
		b.append(searchtarget.getSimpleName());
		b.append(" o");
		
		Object value = null;
		int count = 0;
		for(String key : parameters.keySet()) {
			value = parameters.get(key);
			
			if(value != null) {				
				if(value instanceof String) {
					if(!((String) value).isEmpty()) {
						if(count > 0) {
							b.append(" and");
						} else {
							b.append(" where ");
						}
						b.append(" o.");
						b.append(key);
						b.append(" like ");
						b.append(":");
						b.append(key);						
						count++;
					}
				} else if(value instanceof Double || value instanceof Float || value instanceof Long || value instanceof Integer || value instanceof Byte || value instanceof Character || value instanceof Date || value instanceof BigDecimal || value instanceof Boolean) {
					if(count > 0) {
						b.append(" and");
					} else {
						b.append(" where ");
					}
					b.append(" o.");
					b.append(key);
					b.append("=");
					b.append(":");
					b.append(key);						
					b.append(" ");				
					count++;
				}
			}
		}
		
		return b;
	}
	
	@Override
	@Transactional
	public void insertCollectionItem(Object entity) throws PersistenceException {
		entityManager.persist(entity);
	}

	@Override
	@Transactional
	public void insertCollection(Collection<?> entities)
			throws PersistenceException {
		LOG.debug("Entities to insert: " + entities);
		
		for(Object entity : entities) {
			insertCollectionItem(entity);
		}		
	}

	@Override
	@Transactional
	public void updateCollection(Collection<?> entities)
			throws PersistenceException {
		LOG.debug("Entities to update: " + entities);

		for(Object entity : entities) {
			entityManager.merge(entity);
		}
	}
	
	@Override
	@Transactional
	public void removeCollection(Collection<?> entities)
			throws PersistenceException {
		LOG.debug("Entities to update: " + entities);

		for(Object entity : entities) {
			entityManager.remove(entity);
		}
	}

	@Override
	@Transactional
	public long count(Class<?> countTarget, String propertyOgnl) {
		StringBuilder q = new StringBuilder();
		q.append("select count(o." + propertyOgnl + ") from ");
		q.append(countTarget.getSimpleName());
		q.append(" o");
		
		Query query = entityManager.createQuery(q.toString());		
		
		long start = System.currentTimeMillis();
		long count = (Long) query.getSingleResult();
		double finish = (System.currentTimeMillis() - start) / 1000.0;
		
		LOG.info("Count completed in " + finish + " secs.");
		
		return count;
	}

	@Override
	@Transactional
	public void addPersonnel(Long userId, Long personnelId, Long organizationId) {
		LOG.debug("addPersonnel - " + personnelId + " " + organizationId);
		
		Personnel personnel = entityManager.find(Personnel.class, personnelId);
		
		Organization organization = entityManager.find(Organization.class, organizationId);
		
		personnel.setOrganization(organization);
		
		update(personnel, userId);
	}

	@Override
	@Transactional
	public void addOrganization(Long userId, String currencyCode, String countryCode, Organization organization) {
		LOG.debug("addOrganization - " + userId + " " + currencyCode + " " + countryCode + " " + organization.getOrganizationName());
		
		if(currencyCode != null) {
			organization.setCurrency((Currency) find(currencyCode, Currency.class));
		}
		
		if(countryCode != null) {
			organization.getAddress().setCountry((Country) find(countryCode, Country.class));
		}
		
		insert(organization.getAddress(), userId);
		insert(organization.getContactInfo(), userId);		
		insert(organization, userId);
		
		if(organization.getCurrency() == null) {			
			if(organization.getAddress() != null && organization.getAddress().getCountry() != null && organization.getAddress().getCountry().getCurrency() != null) {
				organization.setCurrency(organization.getAddress().getCountry().getCurrency());			
				update(organization, userId);
			}
		}
	}

	@Override
	@Transactional
	public void updateOrganization(Long userId, String currencyCode, String countryCode, Organization organization) {
		LOG.debug("updateOrganization - " + userId + " " + currencyCode + " " + countryCode + " " + organization.getOrganizationName());
		
		if(currencyCode != null) {
			organization.setCurrency((Currency) find(currencyCode, Currency.class));
		}
		
		if(countryCode != null) {
			organization.getAddress().setCountry((Country) find(countryCode, Country.class));
		}
		
		update(organization.getAddress(), userId);
		update(organization.getContactInfo(), userId);
		update(organization, userId);
		
		if(organization.getCurrency() == null) {			
			if(organization.getAddress() != null && organization.getAddress().getCountry() != null && organization.getAddress().getCountry().getCurrency() != null) {
				organization.setCurrency(organization.getAddress().getCountry().getCurrency());			
				update(organization, userId);
			}
		}
	}

	@Override
	@Transactional
	public Currency findDefaultCurrency(Long userId) {
		LOG.debug("findDefaultCurrency - " + userId);
		
		Personnel personnel = entityManager.find(Personnel.class, userId);
		
		return personnel.getOrganization().getCurrency();
	}		
}
