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
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
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

	@Transactional(readOnly=true)
	public Integer countSearch(String name, Date startDateFrom,
			Date startDateTo, Date endDateFrom, Date endDateTo,
			Users dispatcher, Users creator) {

		Criteria crit = buildSearchCriteria(name, startDateFrom, startDateTo,
				endDateFrom, endDateTo, dispatcher, creator);

		crit.setProjection(Projections.rowCount());
		Integer count = ((Long) crit.uniqueResult()).intValue();
		log.info("Rows counted:" + count);
		return count;
	}

	@Transactional(readOnly=true)
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

	@Transactional(readOnly=true)
	public List<ProjectTask> findTaskAboutToExpire(Integer defaultDaysNum) {

		Session session = getSession();

		Query q1 = session
				.createQuery("from ProjectTask pt where  pt.endDt > current_date() and (day(pt.endDt) - day(current_date()))<pt.reminderDays");
//		q1.setInteger("defaultReminderDays", defaultDaysNum);
		List<ProjectTask> results = q1.list();
		if (results != null)
			log.info("Rows fetched:" + results.size());
		else
			log.info("Rows fetched: resultSet is null");
		return results;
	}

}
