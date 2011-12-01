package gr.scriptum.dao.reports;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.reports.TaskPerProject;



/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 1, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportTaskPerProjectDAO extends
		GenericDAO<TaskPerProject, Integer> implements ReportProducerDAO {

	private static Log log = LogFactory.getLog(ReportTaskPerProjectDAO.class);

	@Override
	public Integer countReportRows(Users user) {
		log.info("countReportRows() started.");
		Query query = getSession()
				.createSQLQuery(
						"SELECT  count(*)"
								+ " FROM   project_task pt  LEFT JOIN project p ON pt.project_id = p.id "
								+ " LEFT   JOIN users usr ON pt.user_dispatcher_id  = usr.id "
								+ " LEFT   JOIN task_state ts ON pt.task_state_id = ts.id "
								+ " WHERE  p.user_creator_id = :myUserId  OR  ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) ");

		query.setParameter("myUserId", user.getId());
		log.info("countReportRows() finished.");
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@Override
	public List createReport(Users user, Integer firstResult, Integer maxResults) {
		log.info("createReport() started.");
		Query query = getSession()
				.createSQLQuery(
						"SELECT  pt.project_id as projectId, pt.id as taskId, usr.name as userName, usr.surname as userSurname, p.name as projectName, pt.name as taskName, ts.name as statusName "
								+ " FROM   project_task pt LEFT JOIN project p ON pt.project_id = p.id "
								+ " LEFT   JOIN users usr ON pt.user_dispatcher_id  = usr.id "
								+ " LEFT   JOIN task_state ts ON pt.task_state_id = ts.id "
								+ " WHERE  p.user_creator_id = :myUserId  OR  ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) "
								+ " ORDER BY pt.project_id DESC, pt.id DESC, usr.id"
								+ " LIMIT " + firstResult + "," + maxResults)
				.addScalar("projectId").addScalar("taskId")
				.addScalar("userName").addScalar("userSurname")
				.addScalar("projectName").addScalar("taskName")
				.addScalar("statusName");

		query.setParameter("myUserId", user.getId());
		List list = query.list();
		log.info("createReport() Report fetched : " + list.size());

		List<TaskPerProject> results = new ArrayList<TaskPerProject>();
		for (Object result : list) {
			Object[] row = (Object[]) result;

			TaskPerProject tp = new TaskPerProject();
			tp.setProjectId((Integer) row[0]);
			tp.setTaskId((Integer) row[1]);
			tp.setTaskDispatcher((String) (row[2] + " " + row[3]));
			tp.setProjectName((String) row[4]);
			tp.setTaskName((String) row[5]);
			tp.setTaskState((String) row[6]);
			results.add(tp);
		}

		log.info("createReport() finished.");
		return results;
	}
}
