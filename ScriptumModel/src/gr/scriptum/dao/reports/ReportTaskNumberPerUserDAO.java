package gr.scriptum.dao.reports;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.reports.TaskPerProject;
import gr.scriptum.domain.reports.TaskPerUser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

public class ReportTaskNumberPerUserDAO extends
		GenericDAO<TaskPerProject, Integer> implements ReportProducerDAO {

	private static Log log = LogFactory.getLog(ReportTaskPerProjectDAO.class);

	@Override
	public Integer countReportRows(Users user) {
		Query query = getSession()
				.createSQLQuery(
						"SELECT COUNT(*) as rcount FROM ( "
								+ " SELECT usr.username, usr.name, usr.surname, COUNT(*), ts.name"
								+ " FROM   project_task pt  LEFT JOIN project p ON pt.project_id = p.id "
								+ " LEFT JOIN users usr ON pt.user_dispatcher_id  = usr.id "
								+ " LEFT JOIN task_state ts ON pt.task_state_id = ts.id "
								+ " WHERE  p.user_creator_id = :myUserId  OR    ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) "
								+ " GROUP BY pt.user_dispatcher_id, pt.task_state_id "
								+ " ORDER BY pt.user_dispatcher_id ) AS tabl").addScalar("rcount");

		query.setParameter("myUserId", user.getId());
		log.info("countReportRows() finished.");
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@Override
	public List createReport(Users user, Integer firstResult,
			Integer maxResults) {
		log.info("createReport() started.");
		Query query = getSession()
				.createSQLQuery( " SELECT usr.username as userName, usr.name as uName, usr.surname as uSurname, COUNT(*) as taskNumber, ts.name as taskState "
						+ " FROM   project_task pt  LEFT JOIN project p ON pt.project_id = p.id "
						+ " LEFT JOIN users usr ON pt.user_dispatcher_id  = usr.id "
						+ " LEFT JOIN task_state ts ON pt.task_state_id = ts.id "
						+ " WHERE  p.user_creator_id = :myUserId  OR    ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) "
						+ " GROUP BY pt.user_dispatcher_id, pt.task_state_id "
						+ " ORDER BY pt.user_dispatcher_id  LIMIT " + firstResult + "," + maxResults)
						.addScalar("userName").addScalar("uName").addScalar("uSurname").addScalar("taskNumber").addScalar("taskState");

		query.setParameter("myUserId", user.getId());

		List list = query.list();
		log.info("createReport() Report fetched : " + list.size());
		
		List<TaskPerUser> results = new ArrayList<TaskPerUser>();
		for (Object result : list) {
			Object[] row = (Object[]) result;
			TaskPerUser tp = new TaskPerUser();
			tp.setUserName((String) row[0]);
			String name = (String) row[1];
			String sname =(String) row[2];
			tp.setNameSurname( name + " " + sname );
			tp.setTaskNumber((Integer) row[3]);
			tp.setTaskState((String) row[4]);
			results.add(tp);
		}

		log.info("createReport() finished.");
		return  results;
	}

}
