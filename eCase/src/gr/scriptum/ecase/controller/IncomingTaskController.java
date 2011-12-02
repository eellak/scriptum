/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskDocument;
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

	public static final String PAGE_INCOMING = "incomingTask.zul";

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
		ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
		projectTask = projectTaskDAO.findById(Integer.valueOf(idString), false);

		if (projectTask == null) {
			Messagebox.show(Labels.getLabel("fetch.notFound"),
					Labels.getLabel("fetch.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// check if user has access to the given task
		boolean authorized = true;
		if (getUserInSession().getId().intValue() != projectTask
				.getUsersByUserCreatorId().getId().intValue()
				&& getUserInSession().getId().intValue() != projectTask
						.getUsersByUserDispatcherId().getId().intValue()) {
			// logged in user is neither the task creator nor the assignee
			authorized = false;
		} else if (getUserInSession().getId().intValue() != projectTask
				.getUsersByUserDispatcherId().getId().intValue()) {
			// logged in user is not the task assignee
			authorized = false;
		}

		if (!authorized) {
			Messagebox.show(Labels.getLabel("fetch.notFound"),
					Labels.getLabel("fetch.title"), Messagebox.OK,
					Messagebox.ERROR);
			projectTask = null;
			return;
		}

		initProjects();
		projects.add(projectTask.getProject());
		taskState = projectTask.getTaskState();
		taskResult = projectTask.getTaskResult();
		taskDocuments.addAll(projectTask.getTaskDocuments());
	}

	public void onClick$newSubTaskBtn() {

		Executions.getCurrent().sendRedirect(
				OutgoingTaskController.PAGE + "?" + PARAM_KEY_PARENT_TASK + "="
						+ projectTask.getId());

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

		// projectTask.setTaskState(taskState);

		getBinder(taskWin).loadAll();
	}

	public void onClick$saveBtn() throws InterruptedException {

		validateFields(taskWin);

		if (taskState.getId().intValue() == taskStateClosedId
				&& taskResult == null) {

			Messagebox.show(Labels.getLabel("taskPage.noResultSet"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		try {

			save();

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("taskPage.errorSaving"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			getBinder(taskWin).loadAll();
			return;
		}

		Messagebox.show(Labels.getLabel("save.OK"),
				Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);
		getBinder(taskWin).loadAll();

	}
	
	public boolean isTaskClosable() {
		if (projectTask == null) {
			return false;
		}

		if (projectTask.getDispatcherCloseable()) {
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

		if (isTaskClosed()) {
			return true;
		}

		if (!isTaskClosable()) {
			return true;
		}

		if (taskState != null
				&& taskState.getId().intValue() != taskStateClosedId) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSaveBtnDisabled() {

		if (projectTask == null) {
			return true;
		}

		if (isTaskClosed()) {
			return true;
		}

		if (isTaskNonClosable()
				&& taskState.getId().intValue() == taskStateClosedId) {
			return true;
		}

		return false;
	}
}
