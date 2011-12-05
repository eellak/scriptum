package gr.scriptum.ecase.controller.reports;

import java.util.List;
import org.zkoss.zk.ui.Component;


import gr.scriptum.dao.TaskResultDAO;
import gr.scriptum.dao.TaskStateDAO;
import gr.scriptum.dao.reports.ReportTasksViewDAO;
import gr.scriptum.domain.TaskResult;
import gr.scriptum.domain.TaskState;
import gr.scriptum.domain.reports.TaskPerProject;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:09:00 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
 */
public class TasksViewController  extends GenericReportController<TaskPerProject, ReportTasksViewDAO>{

	private static final long serialVersionUID = 8527048384587364950L;
	
	
	protected List<TaskState> taskStates = null;
	protected List<TaskResult> taskResults = null;
	protected TaskState taskState = null;
	protected TaskResult taskResult = null;
	protected boolean showLateTasksOnly = false;
	
	
	
	protected void refreshReferenceTables() {
		TaskStateDAO taskStateDAO = new TaskStateDAO();
		taskStates = taskStateDAO.findAll();
		TaskResultDAO taskResultDAO = new TaskResultDAO();
		taskResults = taskResultDAO.findAll();
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);
		refreshReferenceTables();
	}
	
	@Override
	protected void createReport(int startIndex) throws Exception {
		// Use the Domain to load the daos.
		ReportTasksViewDAO dao = new ReportTasksViewDAO();
		if(taskState != null )
			dao.setTaskState(taskState.getId());
		if(taskResult != null )
			dao.setTaskResult(taskResult.getId());
		dao.setShowLateTasksOnly(showLateTasksOnly);
		
		// set up paging by counting records first
		Integer totalSize = dao.countReportRows(getUserInSession());
		reportPgng.setTotalSize(totalSize);
		reportResults = (List<TaskPerProject>)dao.createReport(getUserInSession(),
						startIndex, reportPgng.getPageSize());
	}
	
	
	public List<TaskState> getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(List<TaskState> taskStates) {
		this.taskStates = taskStates;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public boolean isShowLateTasksOnly() {
		return showLateTasksOnly;
	}

	public void setShowLateTasksOnly(boolean showLateTasksOnly) {
		this.showLateTasksOnly = showLateTasksOnly;
	}

	public List<TaskResult> getTaskResults() {
		return taskResults;
	}

	public void setTaskResults(List<TaskResult> taskResults) {
		this.taskResults = taskResults;
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}		
	
	
}
