package gr.scriptum.dao;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;

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

public class GenericDAO<T, ID extends Serializable> {

	private static final String SESSION_FACTORY_NAME = "hibernate/ScriptumSessionFactory";

	private static Log log = LogFactory.getLog(GenericDAO.class);

	public static final String LIKE_OPERATOR = "%";

	private final SessionFactory sessionFactory = getSessionFactory();

	private Class<T> persistentClass;

	private Session session;

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup(SESSION_FACTORY_NAME);
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	protected Session getSession() {
		if (session == null) {
			session = sessionFactory.getCurrentSession();
		}
		return session;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(List<Criterion> criteria,
			List<Order> sortBy) {
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
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void attachDirty(T instance) {
		log.info("attaching dirty ApplicationLog instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.info("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		log.info("attaching clean ApplicationLog instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.info("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public GenericDAO(Class<T> dto) {
		this.persistentClass = dto;
	}

	public void setSession(Session s) {
		this.session = s;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().get(getPersistentClass(), id,
					LockMode.UPGRADE);
		else
			entity = (T) getSession().get(getPersistentClass(), id);
		return entity;
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		return findByExample(exampleInstance, null, null, null, null, null,
				excludeProperty);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, MatchMode matchMode,
			List<Order> sortBy, Integer firstResult, Integer maxResults,
			String... excludeProperty) {
		return findByExample(exampleInstance, matchMode, null, sortBy,
				firstResult, maxResults, excludeProperty);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, MatchMode matchMode,
			List<Criterion> additionalCriteria, List<Order> sortBy,
			Integer firstResult, Integer maxResults, String... excludeProperty) {
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
		log.info("Rows fetched:" + results.size());
		return results;
	}

	public Integer countByExample(T exampleInstance, String... excludeProperty) {
		return countByExample(exampleInstance, null, null, excludeProperty);
	}

	public Integer countByExample(T exampleInstance, MatchMode matchMode,
			List<Criterion> additionalCriteria, String... excludeProperty) {
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
		Integer count = (Integer) crit.uniqueResult();
		log.info("Rows counted:" + count);
		return count;
	}

	public T makePersistent(T entity) {
		// getSession().saveOrUpdate(entity);
		try {
			getSession().persist(entity);
			log.info("persist successfull");
			return entity;
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public T merge(T detachedInstance) {
		log.info("merging ApplicationLog instance");
		try {
			T result = (T) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.info("merge successfull");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void deleteById(ID id) {
		T ent = findById(id, false);
		delete(ent);
	}

	public void deleteByIds(ID[] ids) {
		for (ID id : ids) {
			deleteById(id);
		}
	}

	public void delete(T entity) {
		try {
			getSession().delete(entity);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public boolean isDeletable(ID id) {
		T ent = findById(id, false);
		
		//check all collections (ie relationships)
		List props = Arrays.asList(PropertyUtils
				.getPropertyDescriptors(ent));
		Iterator it = props.iterator();

		boolean isDeletable = true;
		while (it.hasNext()) {
			PropertyDescriptor pd = (PropertyDescriptor) it.next();
			String propertyName = pd.getName();
			Object propertyValue;
			try {
				propertyValue = PropertyUtils
						.getProperty(ent, propertyName);
				
			} catch (Exception e) {
				// property was not accessible - this should be safe to swallow
				// and continue
				continue;
			}
			if(propertyValue instanceof Set) {
				try {
					
				log.info("Checking property: "+propertyName);
				isDeletable = isDeletable & ((Set)propertyValue).isEmpty();
				
				} catch (Exception e) {
					// property was not accessible - this should be safe to swallow
					// and continue
					log.warn(e);
					continue;
				}
			}
			if(!isDeletable) { //no need to check any further
				break;
			}
		}

		return isDeletable;
		
	}
	
}
