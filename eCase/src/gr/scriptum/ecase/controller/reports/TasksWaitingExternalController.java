package gr.scriptum.ecase.controller.reports;

import gr.scriptum.dao.reports.ReportTasksWaitingExternalDAO;
import gr.scriptum.domain.reports.TaskPerProject;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:09:00 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
   MySQL Report for all tasks (and free tasks) that have been assigned by this user from all his projects
   to all sub users that are in state 3 = waiting from an external contact.
 */
public class TasksWaitingExternalController  extends GenericReportController<TaskPerProject, ReportTasksWaitingExternalDAO>{

	private static final long serialVersionUID = 8527048384587364950L;

	
}
