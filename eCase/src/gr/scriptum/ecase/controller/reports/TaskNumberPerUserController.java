package gr.scriptum.ecase.controller.reports;


import gr.scriptum.dao.reports.ReportTaskNumberPerUserDAO;
import gr.scriptum.domain.reports.TaskPerUser;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:05:15 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
 * MySQL Report for the number of user tasks that have been assigned by this user for all all his projects
 * to all sub users regarding their state.
 */
public class TaskNumberPerUserController extends  GenericReportController<TaskPerUser, ReportTaskNumberPerUserDAO>{

	private static final long serialVersionUID = -4764822453190267965L;

}
