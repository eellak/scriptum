package gr.scriptum.ecase.controller.reports;

import gr.scriptum.dao.reports.ReportTaskNumberWaitingPerCompanyDAO;
import gr.scriptum.domain.reports.TaskPerProject;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:09:00 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
   MySQL Report for all tasks (and free tasks) that have been assigned by this user from all his projects
   to all sub users that are in state 3 = waiting from an external contact merged by the external company the 
   contact that was previously assigned the task belongs to.
 */
public class TasksNumberWaitingExternalPerCompanyController  
    extends GenericReportController<TaskPerProject, ReportTaskNumberWaitingPerCompanyDAO>{

	private static final long serialVersionUID = 8527034534587364950L;

	
}
