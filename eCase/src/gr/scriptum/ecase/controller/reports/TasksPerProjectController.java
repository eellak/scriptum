package gr.scriptum.ecase.controller.reports;


import gr.scriptum.dao.reports.ReportTaskPerProjectDAO;
import gr.scriptum.domain.reports.TaskPerProject;


/**
 * MySQL Report for all tasks (and free tasks) that have been assigned by this user from all his projects
 * to all sub users.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class TasksPerProjectController  extends GenericReportController<TaskPerProject, ReportTaskPerProjectDAO>{

	private static final long serialVersionUID = -7816129622700754097L;

}
