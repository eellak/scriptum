package gr.scriptum.dao;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

public abstract class GenericDAO<T, ID extends Serializable> {

	private static final String SESSION_FACTORY_NAME = "hibernate/ScriptumSessionFactory";

	private static Log log = LogFactory.getLog(GenericDAO.class);

	public static final String LIKE_OPERATOR = "%";

	private Class<T> persistentClass;

	private SessionFactory sessionFactory = null;

	@PersistenceContext
	private EntityManager entityManager;

	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(List<Criterion> criteria, List<Order> sortBy) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion criterion : criteria) {
			crit.add(criterion);
		}

		for (Order order : sortBy) {
			crit.addOrder(order);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());

		for (Criterion c : criterion) {
			if (c != null)
				crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		// avoid cglib problem with proxying generic classes
		// http://stackoverflow.com/questions/8474814/spring-cglib-why-cant-a-generic-class-be-proxied
		Type type = getClass();
		do {
			type = ((Class<T>) type).getGenericSuperclass();
		} while (!(type instanceof ParameterizedType));
		ParameterizedType pt = (ParameterizedType) type;
		this.persistentClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public void attachDirty(T instance) {
		log.debug("attaching dirty instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		log.debug("attaching clean instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public GenericDAO(Class<T> dto) {
		this.persistentClass = dto;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getSession().get(getPersistentClass(), id);
		return entity;
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findByCriteria();
	}

	@Transactional(readOnly = true)
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		return findByExample(exampleInstance, null, null, null, null, null, excludeProperty);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, MatchMode matchMode, List<Order> sortBy, Integer firstResult,
			Integer maxResults, String... excludeProperty) {
		return findByExample(exampleInstance, matchMode, null, sortBy, firstResult, maxResults, excludeProperty);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, MatchMode matchMode, List<Criterion> additionalCriteria,
			List<Order> sortBy, Integer firstResult, Integer maxResults, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);

		if (matchMode != null) {
			example.enableLike(matchMode);
		}

		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}

		if (additionalCriteria != null) {
			for (Criterion criterion : additionalCriteria) {
				crit.add(criterion);
			}
		}

		if (sortBy != null) {
			for (Order order : sortBy) {
				crit.addOrder(order);
			}
		}
		if (firstResult != null) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			crit.setMaxResults(maxResults);
		}

		crit.add(example);
		List<T> results = crit.list();
		log.debug("Rows fetched ("+persistentClass.getSimpleName()+") : " + results.size());
		return results;
	}

	@Transactional(readOnly = true)
	public Integer countByExample(T exampleInstance, String... excludeProperty) {
		return countByExample(exampleInstance, null, null, excludeProperty);
	}

	@Transactional(readOnly = true)
	public Integer countByExample(T exampleInstance, MatchMode matchMode, List<Criterion> additionalCriteria,
			String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);

		if (matchMode != null) {
			example.enableLike(matchMode);
		}

		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}

		if (additionalCriteria != null) {
			for (Criterion criterion : additionalCriteria) {
				crit.add(criterion);
			}
		}

		crit.add(example);
		crit.setProjection(Projections.rowCount());
		Integer count = ((Long) crit.uniqueResult()).intValue();
		log.debug("Rows counted ("+persistentClass.getSimpleName()+") : " + count);
		return count;
	}

	@Transactional
	public T makePersistent(T entity) {
		try {
//			 getEntityManager().persist(entity);
			getSession().persist(entity);
			// Serializable id = getSession().save(entity);
			// log.debug(id);
			log.debug("Persist successfull ("+persistentClass.getSimpleName()+")");
			return entity;
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void refresh(T entity) {
		getSession().refresh(entity);
	}

	@Transactional
	public void update(T entity) {
		getSession().update(entity);
	}

//	@Transactional
	@SuppressWarnings("unchecked")
	public T merge(T detachedInstance) {
		log.debug("Merging instance ("+persistentClass.getSimpleName()+")");
		try {
			T result = (T) getSession().merge(detachedInstance);
			log.debug("Merge successfull");
			return result;
		} catch (RuntimeException re) {
			log.error("Merge failed", re);
			throw re;
		}
	}

	@Transactional
	public void deleteById(ID id) {
		T ent = findById(id, false);
		delete(ent);
	}

	@Transactional
	public void deleteByIds(ID[] ids) {
		for (ID id : ids) {
			deleteById(id);
		}
	}

	@Transactional
	public void delete(T entity) {
		try {
			getSession().delete(entity);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Transactional
	public void flush() {
		getSession().flush();
		// getEntityManager().flush();
	}

	public void evict(T entity) {
		getSession().evict(entity);
	}
	
	@Transactional
	public void clear() {
		getSession().clear();
	}

	@Transactional
	public boolean isDeletable(ID id) {
		return isDeletable(id, null);
	}

	@Transactional
	public boolean isDeletable(ID id, String... excludedProperties) {
		T ent = findById(id, false);

		// check all collections (ie relationships)
		List props = Arrays.asList(PropertyUtils.getPropertyDescriptors(ent));
		Iterator it = props.iterator();

		boolean isDeletable = true;
		while (it.hasNext()) {
			PropertyDescriptor pd = (PropertyDescriptor) it.next();
			String propertyName = pd.getName();
			if(excludedProperties!=null && Arrays.asList(excludedProperties).contains(propertyName)) {
				//skip excluded property
				continue;
			}
			Object propertyValue;
			try {
				propertyValue = PropertyUtils.getProperty(ent, propertyName);

			} catch (Exception e) {
				// property was not accessible - this should be safe to swallow
				// and continue
				continue;
			}
			if (propertyValue instanceof Set) {
				try {

					log.debug("Checking property: " + propertyName);
					isDeletable = isDeletable & ((Set) propertyValue).isEmpty();

				} catch (Exception e) {
					// property was not accessible - this should be safe to
					// swallow
					// and continue
					log.warn(e);
					continue;
				}
			}
			if (!isDeletable) { // no need to check any further
				break;
			}
		}

		return isDeletable;

	}

}
