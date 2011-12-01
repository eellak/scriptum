package gr.scriptum.dao.reports;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.reports.TaskPerProject;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 1, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportTaskNumberWaitingPerCompanyDAO extends
		GenericDAO<TaskPerProject, Integer> implements ReportProducerDAO {

	private static Log log = LogFactory.getLog(ReportTaskNumberWaitingPerCompanyDAO.class);

	@Override
	public Integer countReportRows(Users user) {
		
		log.info("countReportRows() started.");
		Query query = getSession()
				.createSQLQuery("SELECT COUNT(*) AS rcount FROM( " + 
								" SELECT p.name AS pName, COUNT(*) AS numExtTasks, com.name AS comName " + 
                                " FROM   project_task pt  LEFT JOIN project p     ON pt.project_id = p.id " + 
                                " LEFT JOIN users usr     ON pt.user_dispatcher_id  = usr.id " + 
                                " LEFT JOIN task_state ts ON pt.task_state_id = ts.id  " +
                                " LEFT JOIN contact co    ON co.id = pt.contact_id " +
                                " LEFT JOIN company com   ON com.id = co.company_id " +
                                " WHERE  ( p.user_creator_id = :myUserId OR    ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) ) " + 
                                " AND pt.task_state_id = 3 " +
                                " GROUP BY p.id, com.name ) AS rset ");
						

		query.setParameter("myUserId", user.getId());
		log.info("countReportRows() finished.");
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@Override
	public List createReport(Users user, Integer firstResult,
			Integer maxResults) {
		log.info("createReport(1) started.");
		Query query = getSession()
				.createSQLQuery( "SELECT p.name AS pName, COUNT(*) AS numExtTasks, com.name AS comName " + 
                                " FROM   project_task pt  LEFT JOIN project p     ON pt.project_id = p.id " + 
                                " LEFT JOIN users usr     ON pt.user_dispatcher_id  = usr.id " + 
                                " LEFT JOIN task_state ts ON pt.task_state_id = ts.id  " +
                                " LEFT JOIN contact co    ON co.id = pt.contact_id " +
                                " LEFT JOIN company com   ON com.id = co.company_id " +
                                " WHERE  ( p.user_creator_id = :myUserId OR    ( pt.project_id IS NULL AND pt.user_creator_id = :myUserId ) ) " + 
                                " AND pt.task_state_id = 3 " +
                                " GROUP BY p.id, com.name " +
                                " ORDER BY com.name, co.surname, p.id, pt.expected_dt LIMIT " + firstResult + "," + maxResults)
						        .addScalar("pName").addScalar("numExtTasks").addScalar("comName");

		query.setParameter("myUserId", user.getId());

		List list = query.list();
		log.info("createReport() Report fetched : " + list.size());
		
		List<TaskPerProject> results = new ArrayList<TaskPerProject>();
		for (Object result : list) {
			Object[] row = (Object[]) result;
			TaskPerProject tp = new TaskPerProject();
			tp.setProjectName((String) row[0]);
			BigInteger tn = (BigInteger) row[1];
			tp.setTaskNumber( tn.intValue() );
			tp.setCompanyName((String ) row[2]);
			results.add(tp);
		}

		log.info("createReport() finished.");
		return  results;
	}

}
