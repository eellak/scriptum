package gr.scriptum.dao.reports;

import java.util.List;

import gr.scriptum.dao.GenericDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.reports.TaskPerProject;

/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 1, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportTasksWaitingExternalDAO extends
		GenericDAO<TaskPerProject, Integer> implements ReportProducerDAO {

	@Override
	public Integer countReportRows(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List createReport(Users user, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

}