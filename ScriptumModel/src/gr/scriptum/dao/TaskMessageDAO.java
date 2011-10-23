/**
 * 
 */
package gr.scriptum.dao;

import gr.scriptum.domain.TaskMessage;
import gr.scriptum.domain.Users;

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

/**
 * @author aanagnostopoulos
 * 
 */
public class TaskMessageDAO extends GenericDAO<TaskMessage, Integer> {

	private static Log log = LogFactory.getLog(TaskMessageDAO.class);

	private Criteria buildSearchCriteria(String subject, Date dateFrom,
			Date dateTo, Users sender, Users receiver) {

		Criteria crit = getSession().createCriteria(TaskMessage.class);

		if (subject != null) {
			crit.add(Restrictions.like("subject", subject, MatchMode.ANYWHERE));
		}

		if (dateFrom != null) {
			crit.add(Restrictions.ge("createdTs", dateFrom));
		}

		if (dateTo != null) {
			/*
			 * add one day to make date search more user-friendly (since the
			 * user tends to think in terms of <= , when it comes to dates)
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			crit.add(Restrictions.lt("createdTs", calendar.getTime()));
		}

		if (sender != null) {
			crit.add(Restrictions.eq("usersByUserSenderId", sender));
		}

		if (receiver != null) {
			crit.add(Restrictions.eq("usersByUserReceiverId", receiver));
		}

		return crit;

	}

	public Integer countSearch(String subject, Date dateFrom, Date dateTo,
			Users sender, Users receiver) {

		Criteria crit = buildSearchCriteria(subject, dateFrom, dateTo, sender,
				receiver);

		crit.setProjection(Projections.rowCount());
		Integer count = (Integer) crit.uniqueResult();
		log.info("Rows counted:" + count);
		return count;
	}

	public List<TaskMessage> search(String subject, Date dateFrom, Date dateTo,
			Users sender, Users receiver, Integer firstResult,
			Integer maxResults, Order... sortBy) {

		Criteria crit = buildSearchCriteria(subject, dateFrom, dateTo, sender,
				receiver);

		for (Order order : sortBy) {
			crit.addOrder(order);
		}

		if (firstResult != null) {
			crit.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			crit.setMaxResults(maxResults);
		}

		List<TaskMessage> results = crit.list();
		log.info("Rows fetched:" + results.size());
		return results;

	}

}
