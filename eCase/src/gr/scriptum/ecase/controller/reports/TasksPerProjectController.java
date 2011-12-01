package gr.scriptum.ecase.controller.reports;


import gr.scriptum.dao.reports.ReportTaskPerProjectDAO;
import gr.scriptum.domain.reports.TaskPerProject;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:09:00 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
   MySQL Report for all tasks (and free tasks) that have been assigned by this user from all his projects
   to all sub users.
 */
public class TasksPerProjectController  extends GenericReportController<TaskPerProject, ReportTaskPerProjectDAO>{

	private static final long serialVersionUID = -7816129622700754097L;

}
