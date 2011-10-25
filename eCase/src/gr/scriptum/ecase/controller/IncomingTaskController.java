/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskDocument;
import gr.scriptum.domain.TaskState;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Messagebox;

/**
 * @author aanagnostopoulos
 * 
 */
public class IncomingTaskController extends TaskController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6843247927747571570L;

	private static Log log = LogFactory.getLog(IncomingTaskController.class);

	/* data binding */
	private TaskState taskState = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString == null) {
			Messagebox.show(Labels.getLabel("fetch.notFound"),
					Labels.getLabel("fetch.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		// TODO: check if user has access to the given task
		ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
		projectTask = projectTaskDAO.findById(Integer.valueOf(idString), false);

		if (projectTask == null) {
			Messagebox.show(Labels.getLabel("fetch.notFound"),
					Labels.getLabel("fetch.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		initProjects();
		projects.add(projectTask.getProject());
		taskState = projectTask.getTaskState();
		taskDocuments.addAll(projectTask.getTaskDocuments());
	}

	public void onClick$newSubTaskBtn() {

		Executions.getCurrent().sendRedirect(
				PAGE + "?" + PARAM_KEY_PARENT_TASK + "=" + projectTask.getId());

	}

	public void onSelect$stateCbx(SelectEvent event)
			throws InterruptedException {
		if (taskState.getId().intValue() == taskStateClosedId
				&& isTaskNonClosable()) {
			Messagebox.show(Labels.getLabel("incomingTaskPage.nonClosable"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			getBinder(taskWin).loadAll();
			return;
		}

		projectTask.setTaskState(taskState);

		getBinder(taskWin).loadAll();
	}

	public boolean isTaskClosable() {
		if (isTaskCreated() && projectTask.getDispatcherCloseable()) {
			return true;
		}
		return false;
	}

	public boolean isTaskNonClosable() {
		return !isTaskClosable();
	}

	public boolean isResultCbxDisabled() {

		if (isTaskNotCreated()) {
			return true;
		}

		if (projectTask.getTaskState().getId().intValue() != taskStateClosedId) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSaveBtnDisabled() {

		if (projectTask == null) {
			return true;
		}
		if (!projectTask.getDispatcherCloseable()
				&& taskState.getId().intValue() == taskStateClosedId) {
			return true;
		}

		return false;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}
}
