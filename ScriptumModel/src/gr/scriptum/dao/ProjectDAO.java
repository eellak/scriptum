/**
 * 
 */
package gr.scriptum.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import gr.scriptum.domain.Project;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProjectDAO extends GenericDAO<Project, Integer> {

	private static Log log = LogFactory.getLog(ProjectDAO.class);

	private Criteria buildSearchCriteria(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo) {

		Criteria crit = getSession().createCriteria(Project.class);

		if (name != null) {
			crit.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}

		if (startDateFrom != null) {
			crit.add(Restrictions.ge("startDt", startDateFrom));
		}

		if (startDateTo != null) {
			/*
			 * add one day to make date search more user-friendly (since the
			 * user tends to think in terms of <= , when it comes to dates)
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			crit.add(Restrictions.lt("startDt", calendar.getTime()));
		}

		if (endDateFrom != null) {
			crit.add(Restrictions.ge("endDt", endDateFrom));
		}

		if (endDateTo != null) {
			/*
			 * add one day to make date search more user-friendly (since the
			 * user tends to think in terms of <= , when it comes to dates)
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			crit.add(Restrictions.lt("endDt", calendar.getTime()));
		}

		return crit;

	}

	public Integer countSearch(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo) {

		Criteria crit = buildSearchCriteria(name, startDateFrom, startDateTo,
				endDateFrom, endDateTo);

		crit.setProjection(Projections.rowCount());
		Integer count = (Integer) crit.uniqueResult();
		log.info("Rows counted:" + count);
		return count;
	}

	public List<Project> search(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo,
			Integer firstResult, Integer maxResults, Order... sortBy) {

		Criteria crit = buildSearchCriteria(name, startDateFrom, startDateTo,
				endDateFrom, endDateTo);

		for (Order order : sortBy) {
			crit.addOrder(order);
		}

		if (firstResult != null) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			crit.setMaxResults(maxResults);
		}

		List<Project> results = crit.list();
		log.info("Rows fetched:" + results.size());
		return results;

	}

	public boolean isDeletable(Integer id) {
		Project project = findById(id, false);
		if (!project.getProjectTasks().isEmpty()) {
			return false;
		}
		return true;
	}

	public List<Project> findByCreator(Users user) {
		Query query = getSession().createQuery(
				"from Project p where p.users = :user");
		query.setParameter("user", user);
		return query.list();
	}
}
