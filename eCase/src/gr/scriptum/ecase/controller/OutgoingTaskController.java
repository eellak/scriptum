/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskDocument;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;
import gr.scriptum.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 * @author aanagnostopoulos
 * 
 */
public class OutgoingTaskController extends TaskController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4338541320808714303L;

	public static final String PAGE = "task.zul";

	public static final String PARAM_KEY_CLONE = "c";

	public static final String PARAM_CLONE_TRUE = "1";

	private static Log log = LogFactory.getLog(OutgoingTaskController.class);

	private void addHierarchyBranch(UserHierarchy root) {
		userHierarchies.add(root);
		for (UserHierarchy child : root.getUserHierarchies()) {
			addHierarchyBranch(child);
		}
	}

	private void refreshUserHierarchies() {
		userHierarchies = new ArrayList<UserHierarchy>();
		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();

		List<UserHierarchy> hierarchies = null;
		// TODO: figure out if users will be filtered, based on project
		// participants
		hierarchies = userHierarchyDAO.findByUser(getUserInSession());
		// if (projectTask.getProject() == null) {
		// hierarchies = userHierarchyDAO.findByUser(getUserInSession());
		// } else {
		// hierarchies = userHierarchyDAO.findByUser(getUserInSession(),
		// projectTask.getProject());
		// }

		for (UserHierarchy hierarchy : hierarchies) {
			addHierarchyBranch(hierarchy);
		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) { // existing task

			ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
			projectTask = projectTaskDAO.findById(Integer.valueOf(idString),
					false);

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
					.getUsersByUserCreatorId().getId().intValue()) {
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

			String cloneString = execution.getParameter(PARAM_KEY_CLONE);
			if (cloneString != null
					&& cloneString.equalsIgnoreCase(PARAM_CLONE_TRUE)) {
				/* clone task */
				// copy fields
				ProjectTask clone = new ProjectTask();
//				clone.setProject(projectTask.getProject());
				clone.setUsersByUserCreatorId(projectTask
						.getUsersByUserCreatorId());
				clone.setUsersByUserDispatcherId(projectTask
						.getUsersByUserDispatcherId());
//				clone.setProjectTask(projectTask.getProjectTask());
				clone.setTaskType(projectTask.getTaskType());
				clone.setTaskPriority(projectTask.getTaskPriority());
				clone.setName(projectTask.getName());
				clone.setDescription(projectTask.getDescription());
				clone.setComments(projectTask.getComments());
				clone.setDispatcherCloseable(projectTask
						.getDispatcherCloseable());

				// copy task documents (including byte content)
				for (TaskDocument taskDocument : projectTask.getTaskDocuments()) {
					TaskDocument cloneTaskDocument = new TaskDocument();
					cloneTaskDocument.setProjectTask(clone);
					cloneTaskDocument.setDocumentName(taskDocument
							.getDocumentName());
					cloneTaskDocument.setDocumentType(taskDocument
							.getDocumentType());
					cloneTaskDocument.setDocumentKeywords(taskDocument
							.getDocumentKeywords());
					cloneTaskDocument.setDocumentSize(taskDocument
							.getDocumentSize());
					cloneTaskDocument.setDocumentNumber(taskDocument
							.getDocumentNumber());
					// Document content is stored in OpenKM, fetch and copy
					ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(taskDocument);
					cloneTaskDocument.setContent(responseSendDocument
							.getContent());
					taskDocuments.add(cloneTaskDocument);
				}
//				projects = new ArrayList<Project>();
//				projects.add(clone.getProject());
				refreshProjects();
				
				projectTask = clone;

			} else {

				projects = new ArrayList<Project>();
				projects.add(projectTask.getProject());
				taskState = projectTask.getTaskState();
				taskResult = projectTask.getTaskResult();
				taskDocuments.addAll(projectTask.getTaskDocuments());
			}

		} else { // new task
			initTask();
			refreshProjects();
			refreshUserHierarchies();

			// set parent task, if any
			String idParentTaskString = execution
					.getParameter(PARAM_KEY_PARENT_TASK);
			if (idParentTaskString != null) {
				Integer idParentTask = null;
				try {
					idParentTask = Integer.valueOf(idParentTaskString);
				} catch (Exception e) {
					log.error(e);
					projectTask = null;
					return;
				}

				ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
				ProjectTask parentTask = projectTaskDAO.findById(idParentTask,
						false);
				// TODO: check if user has access to the given parent task
				if (parentTask != null) {
					projectTask.setProjectTask(parentTask);
					projectTask.setProject(parentTask.getProject());

					// copy certain fields
					projectTask
							.setName(parentTaskPrefix + parentTask.getName());
					projectTask.setDescription(parentTask.getDescription());
					projectTask.setTaskType(parentTask.getTaskType());
					projectTask.setTaskPriority(parentTask.getTaskPriority());
					projectTask.setTaskState(parentTask.getTaskState());

					// copy parent task documents (including byte content)
					for (TaskDocument parentTaskDocument : parentTask
							.getTaskDocuments()) {
						TaskDocument taskDocument = new TaskDocument();
						taskDocument.setProjectTask(projectTask);
						taskDocument.setDocumentName(parentTaskDocument
								.getDocumentName());
						taskDocument.setDocumentType(parentTaskDocument
								.getDocumentType());
						taskDocument.setDocumentKeywords(parentTaskDocument
								.getDocumentKeywords());
						taskDocument.setDocumentSize(parentTaskDocument
								.getDocumentSize());
						taskDocument.setDocumentNumber(parentTaskDocument
								.getDocumentNumber());
						// Document content is stored in OpenKM, fetch and copy
						ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(parentTaskDocument);
						taskDocument.setContent(responseSendDocument
								.getContent());
						taskDocuments.add(taskDocument);
					}

				}
			}
		}
	}

	public void onSelect$projectCbx(SelectEvent event) {
		refreshUserHierarchies();
		getBinder(taskWin).loadAll();
	}

	public void onSelect$userHierarchiesLstbx(SelectEvent event) {
		userHierarchyBndbx.close();
		projectTask
				.setUsersByUserDispatcherId(selectedUserHierarchy.getUsers());
		getBinder(taskWin).loadAll();
	}

	public void onChanging$contactBndbx(InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		searchContacts(0);
		getBinder(taskWin).loadAll();
	}

	public void onOpen$contactBndbx(OpenEvent event) {
		if (!contacts.isEmpty()) {
			return;
		}
		term = "";
		searchContacts(0);

		getBinder(taskWin).loadAll();
	}

	public void onPaging$contactsPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * contactsPgng.getPageSize();
		searchContacts(startIndex);
		getBinder(taskWin).loadAll();
	}

	public void onSelect$contactsLstbx(SelectEvent event) {
		// populateContactBndbx();
		contactBndbx.close();
		getBinder(taskWin).loadAll();
	}

	public void onSelect$stateCbx(SelectEvent event)
			throws InterruptedException {
		getBinder(taskWin).loadAll();
	}

	public void onClick$cloneBtn() {
		Executions.getCurrent().sendRedirect(
				PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ projectTask.getId() + "&" + PARAM_KEY_CLONE + "="
						+ PARAM_CLONE_TRUE);
	}

	public boolean isProjectCbxVisible() {

		if (isTaskNotCreated() && !hasParentTask()) {
			return true;
		}

		return false;
	}

	public boolean isProjectHbxVisible() {
		return !(isProjectCbxVisible());
	}

	public boolean isResultCbxDisabled() {

		if (isTaskNotCreated()) {
			return true;
		}

		if (isTaskClosed()) {
			return true;
		}

		if (taskState != null
				&& taskState.getId().intValue() != taskStateClosedId) {
			return true;
		}

		return false;
	}

}
