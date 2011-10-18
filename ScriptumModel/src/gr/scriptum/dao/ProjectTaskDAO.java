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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProjectTaskDAO extends GenericDAO<ProjectTask, Integer> {

	private static Log log = LogFactory.getLog(ProjectTaskDAO.class);

	private Criteria buildSearchCriteria(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo,
			Users dispatcher, Users creator) {

		Criteria crit = getSession().createCriteria(ProjectTask.class);

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

		if (dispatcher != null) {
			crit.add(Restrictions.eq("usersByUserDispatcherId", dispatcher));
		}

		if (creator != null) {
			crit.add(Restrictions.eq("usersByUserCreatorId", creator));
		}

		return crit;

	}

	public Integer countSearch(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo,
			Users dispatcher, Users creator) {

		Criteria crit = buildSearchCriteria(name, startDateFrom, startDateTo,
				endDateFrom, endDateTo, dispatcher, creator);

		crit.setProjection(Projections.rowCount());
		Integer count = (Integer) crit.uniqueResult();
		log.info("Rows counted:" + count);
		return count;
	}

	public List<ProjectTask> search(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo,
			Users dispatcher, Users creator, Integer firstResult,
			Integer maxResults, Order... sortBy) {

		Criteria crit = buildSearchCriteria(name, startDateFrom, startDateTo,
				endDateFrom, endDateTo, dispatcher, creator);

		for (Order order : sortBy) {
			crit.addOrder(order);
		}

		if (firstResult != null) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			crit.setMaxResults(maxResults);
		}

		List<ProjectTask> results = crit.list();
		log.info("Rows fetched:" + results.size());
		return results;

	}

}
