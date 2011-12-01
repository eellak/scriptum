package gr.scriptum.ecase.controller.reports;


import gr.scriptum.dao.reports.ReportTaskNumberPerUserDAO;
import gr.scriptum.domain.reports.TaskPerUser;



/**
 * MySQL Report for the number of user tasks that have been assigned by this user for all all his projects
 * to all sub users regarding their state.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class TaskNumberPerUserController extends  GenericReportController<TaskPerUser, ReportTaskNumberPerUserDAO>{

	private static final long serialVersionUID = -4764822453190267965L;

}
