/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.dao.TaskDocumentDAO;
import gr.scriptum.dao.TaskPriorityDAO;
import gr.scriptum.dao.TaskResultDAO;
import gr.scriptum.dao.TaskStateDAO;
import gr.scriptum.dao.TaskTypeDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.TaskDocument;
import gr.scriptum.domain.TaskPriority;
import gr.scriptum.domain.TaskResult;
import gr.scriptum.domain.TaskState;
import gr.scriptum.domain.TaskType;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.ecase.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestAddDocumentToNode;
import gr.scriptum.eprotocol.ws.RequestDelete;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.RequestSendDocument;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseAddDocumentToNode;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;
import gr.scriptum.util.Callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

/**
 * @author aanagnostopoulos
 * 
 */
public class TaskController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7291754158464890911L;

	private static Log log = LogFactory.getLog(TaskController.class);

	public static final String PARAM_KEY_PARENT_TASK = "t";

	/* components */
	Window taskWin;
	Window uploadWin;
	Paging contactsPgng;
	Bandbox userHierarchyBndbx;
	Bandbox contactBndbx;
	Listbox documentsLstbx;

	/* data binding */
	protected List<Project> projects = null;
	protected ProjectTask projectTask = null;
	protected List<UserHierarchy> userHierarchies = null;
	protected List<TaskType> taskTypes = null;
	protected List<TaskState> taskStates = null;
	protected List<TaskPriority> taskPriorities = null;
	protected List<TaskResult> taskResults = null;
	protected List<Contact> contacts = null;
	protected UserHierarchy selectedUserHierarchy = null;
	protected List<TaskDocument> taskDocuments = null;
	protected List<TaskDocument> taskDocumentsToBeDeleted = null;
	protected TaskDocument taskDocument = null;
	protected String term = null;

	protected String okmNodeTask = null;
	protected String parentTaskPrefix = null;
	protected Integer taskStateClosedId = null;

	protected TaskState taskState = null;
	protected TaskResult taskResult = null;

	protected void initTask() {
		projectTask = new ProjectTask();
		projectTask.setDispatcherCloseable(false);
		projectTask.setUsersByUserCreatorId(getUserInSession());
	}

	protected void initContacts() {
		contacts = new ArrayList<Contact>();
	}

	protected void initDocuments() {
		taskDocuments = new LinkedList<TaskDocument>();
	}

	protected void initDocumentsToBeDeleted() {
		taskDocumentsToBeDeleted = new ArrayList<TaskDocument>();
	}

	protected void initProjects() {
		projects = new ArrayList<Project>();
	}

	protected void refreshProjects() {
		ProjectDAO projectDAO = new ProjectDAO();
		projects = projectDAO.findByCreator(getUserInSession());
	}

	protected void refreshTaskTypes() {
		TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
		taskTypes = taskTypeDAO.findAll();
	}

	protected void refreshTaskPriorities() {
		TaskPriorityDAO taskPriorityDAO = new TaskPriorityDAO();
		taskPriorities = taskPriorityDAO.findAll();
	}

	protected void refreshTaskStates() {
		TaskStateDAO taskStateDAO = new TaskStateDAO();
		taskStates = taskStateDAO.findAll();
	}

	protected void refreshTaskResults() {
		TaskResultDAO taskResultDAO = new TaskResultDAO();
		taskResults = taskResultDAO.findAll();
	}

	protected void searchContacts(Integer startIndex) {
		ContactDAO contactDAO = new ContactDAO();

		// set up paging by counting records first
		Integer totalSize = contactDAO.countByTerm(term);
		contactsPgng.setTotalSize(totalSize);
		int pageSize = contactsPgng.getPageSize();

		contacts = contactDAO.findByTerm(term, startIndex, pageSize);
		for (Contact contact : contacts) {
			contact.getCompany().getName().toString(); // force hibernate to
														// fetch company
		}
	}

	private void renumberTaskDocuments() {
		for (int i = 0; i < taskDocuments.size(); i++) {
			TaskDocument document = taskDocuments.get(i);
			document.setDocumentNumber(i + 1);
		}
	}

	protected void save() throws Exception {

		// get the current transaction and commit it. We want to perform saving
		// in a new transaction, for better isolation.
		UserTransaction tx = null;

		try {
			tx = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			tx.commit();
			log.info("Committed transaction: " + tx);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

		Date now = new Date();
		projectTask.setUpdateTs(now);
		projectTask.setTaskState(taskState);
		projectTask.setTaskResult(taskResult);

		try {

			tx.begin();
			log.info("Saving Task (Started transaction): " + tx);

			ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
			TaskDocumentDAO taskDocumentDAO = new TaskDocumentDAO();
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			if (projectTask.getId() == null) { // new task, create

				projectTask.setCreateDt(now);
				if (projectTask.getTaskState().getId().intValue() == taskStateClosedId) {
					projectTask.setClosedDt(now);
				}

				/* local database actions */
				projectTaskDAO.makePersistent(projectTask);

				// create documents
				for (TaskDocument document : taskDocuments) {
					document.setProjectTask(projectTask);
					taskDocumentDAO.makePersistent(document);
					projectTask.getTaskDocuments().add(document);
				}

				/* OpenKM actions */

				// create new OKM pending node, including documents
				RequestNewNode requestNewNode = new RequestNewNode(
						getUserInSession().getUsername(), getUserInSession()
								.getId(), getIp(), IConstants.SYSTEM_NAME,
						getOkmToken());

				requestNewNode
						.setFolderPath(okmNodeTask
								+ IConstants.OKM_FOLDER_DELIMITER
								+ projectTask.getId());

				for (TaskDocument document : taskDocuments) {
					requestNewNode.addDocument(document);
				}

				ResponseNewNode newNodeResponse = okmDispatcher
						.createNewNode(requestNewNode);
				if (newNodeResponse.isError()) {
					log.error(newNodeResponse.toString());
					throw new RuntimeException(newNodeResponse.toString());
				}

				// update documents, storing OKM Path, UUID and size
				for (TaskDocument document : taskDocuments) {
					taskDocumentDAO.update(document);
				}

			} else { // existing task, update

				/* local database actions */
				projectTaskDAO.update(projectTask);

				// update documents
				List<TaskDocument> newlyAdded = new LinkedList<TaskDocument>();
				for (TaskDocument document : taskDocuments) {
					if (document.getId() != null) { // existing document, update

						taskDocumentDAO.update(document);

					} else {// Create newly added documents
						document.setProjectTask(projectTask);
						taskDocumentDAO.makePersistent(document);
						projectTask.getTaskDocuments().add(document);
						newlyAdded.add(document);
					}
				}

				// Delete documents marked respectively
				for (TaskDocument document : taskDocumentsToBeDeleted) {
					taskDocumentDAO.delete(document);
					projectTask.getTaskDocuments().remove(document);
				}

				// if task is being closed, close all, if any, children tasks as
				// well
				if (projectTask.getTaskState().getId().intValue() == taskStateClosedId) {
					Date closedDt = new Date();
					projectTask.setClosedDt(closedDt);
					projectTaskDAO.update(projectTask);

					// recursively close all children tasks
					ProjectTask root = projectTask;
					log.info("Root:" + projectTask.getId());
					while (!root.getProjectTasks().isEmpty()) {
						Set<ProjectTask> children = root.getProjectTasks();
						for (ProjectTask child : children) {
							log.info("Child:" + child.getId());
							child.setClosedDt(closedDt);
							child.setTaskState(projectTask.getTaskState());
							child.setTaskResult(projectTask.getTaskResult());
							projectTaskDAO.update(child);
							root = child;
						}
					}
				}

				/* OpenKM actions */

				// add newly added documents to OpenKM
				for (TaskDocument document : newlyAdded) {
					RequestAddDocumentToNode request = new RequestAddDocumentToNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					request.setNodePath(okmNodeTask
							+ IConstants.OKM_FOLDER_DELIMITER
							+ projectTask.getId());
					request.setDocument(document);

					ResponseAddDocumentToNode response = okmDispatcher
							.addDocumentToNode(request);
					if (response.isError()) {
						log.error(response.toString());
						throw new RuntimeException(response.toString());
					}

					// update local document, storing OKM Path, UUID and size
					taskDocumentDAO.update(document);
				}

				// delete documents marked respectively from OpenKM
				for (TaskDocument document : taskDocumentsToBeDeleted) {

					RequestDelete requestDelete = new RequestDelete(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					requestDelete.setDirectory(false);
					requestDelete.setPath(document.getOkmPath());

					Response responseDelete = okmDispatcher
							.delete(requestDelete);
					if (responseDelete.isError()) {
						log.error(responseDelete.toString());
						throw new RuntimeException(responseDelete.toString());
					}
				}
			}

			tx.commit();
			log.info("Saved Task (Committed transaction): " + tx);

			// clean up
			taskDocumentsToBeDeleted.clear();

			tx.begin();
			
		} catch (Exception e) {
			log.error(e);
			if (tx.getStatus() == Status.STATUS_ACTIVE) {
				tx.rollback();
				log.info("Rolled back transaction: " + tx);
			}
			throw e;
		}

	}

	protected ResponseSendDocument fetchDocumentFromOpenKM(
			TaskDocument taskDocument) {

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();
		RequestSendDocument requestSendDocument = new RequestSendDocument(
				getUserInSession().getUsername(), getUserInSession().getId(),
				getIp(), IConstants.SYSTEM_NAME, getOkmToken());
		requestSendDocument.setDocumentPath(taskDocument.getOkmPath());
		ResponseSendDocument responseSendDocument = okmDispatcher
				.sendDocument(requestSendDocument);
		if (responseSendDocument.isError()) {
			log.error(responseSendDocument.toString());
			throw new RuntimeException(responseSendDocument.toString());
		}

		return responseSendDocument;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		ParameterDAO parameterDAO = new ParameterDAO();
		okmNodeTask = parameterDAO.getAsString(IConstants.PARAM_OKM_NODE_TASKS);
		parentTaskPrefix = parameterDAO
				.getAsString(IConstants.PARAM_PARENT_TASK_PREFIX);
		taskStateClosedId = parameterDAO
				.getAsInteger(IConstants.PARAM_TASK_STATED_CLOSED);

		initContacts();
		initDocuments();
		initDocumentsToBeDeleted();
		refreshTaskPriorities();
		refreshTaskStates();
		refreshTaskTypes();
		refreshTaskResults();
	}

	public void onSelect$documentsLstbx(SelectEvent event) {
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

	public void onClick$addFileBtn() throws SuspendNotAllowedException,
			InterruptedException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback(documentsLstbx,
				"onDocumentAdded"));
		uploadWin = (Window) Executions.createComponents(UploadController.PAGE,
				taskWin, params);
		uploadWin.setClosable(true);
		uploadWin.doModal();
	}

	public void onDocumentAdded$documentsLstbx(Event event) {
		TaskDocument document = (TaskDocument) ((ForwardEvent) event)
				.getOrigin().getData();
		document.setDocumentNumber(taskDocuments.size() + 1);
		taskDocuments.add(document);
		getBinder(taskWin).loadAll();
	}

	public void onClick$removeFileBtn() {

		taskDocuments.remove(taskDocument);
		if (taskDocument.getId() != null) { // only mark already created
			// documents for deletion
			taskDocumentsToBeDeleted.add(taskDocument);
		}
		renumberTaskDocuments();
		taskDocument = null;
		getBinder(taskWin).loadAll();

	}

	public void onClick$downloadFileBtn() throws InterruptedException {

		try {

			if (taskDocument.getId() == null) { // not stored in OpenKM,
												// available locally
				Filedownload.save(taskDocument.getContent(),
						taskDocument.getDocumentMime(),
						taskDocument.getDocumentName());
			} else { // document stored in OpenKM, retrieve content

				ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(taskDocument);

				Filedownload.save(responseSendDocument.getContent(),
						responseSendDocument.getMimeType(),
						taskDocument.getDocumentName());
			}

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("error"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void onClick$sendMessageBtn() {

		Executions.getCurrent().sendRedirect(
				MessageController.PAGE + "?" + MessageController.PARAM_KEY_TASK
						+ "=" + projectTask.getId());

	}

	public boolean isTaskCreated() {
		if (projectTask != null && projectTask.getId() != null) {
			return true;
		}
		return false;
	}

	public boolean isTaskNotCreated() {
		return !isTaskCreated();
	}

	public boolean isTaskClosed() {
		if (isTaskCreated()
				&& projectTask.getTaskState().getId().intValue() == taskStateClosedId
				&& projectTask.getTaskResult() != null) {
			return true;
		}
		return false;
	}

	public boolean isTaskOpen() {
		return !isTaskClosed();
	}

	public boolean hasParentTask() {
		if (projectTask != null && projectTask.getProjectTask() != null) {
			return true;
		}
		return false;
	}

	public boolean isAddFileBtnDisabled() {

		if (projectTask == null) {
			return true;
		}

		return isTaskClosed();

		// return false;
	}

	public boolean isRemoveFileBtnDisabled() {
		if (projectTask == null) {
			return true;
		}

		if (taskDocument == null) {
			return true;
		}

		return isTaskClosed();

		// return false;

	}

	public boolean isDownloadFileBtnDisabled() {

		if (taskDocument == null) {
			return true;
		}

		return false;

	}

	public boolean isSaveBtnDisabled() {
		if (projectTask == null) {
			return true;
		}

		return isTaskClosed();

		// return false;
	}

	public boolean isResultCbxEnabled() {
		if (isTaskCreated()
				&& projectTask.getTaskState().getId().intValue() == taskStateClosedId) {
			return true;
		}
		return false;
	}

	public boolean isResultCbxDisabled() {
		return !isResultCbxEnabled();
	}

	public String getContactFullName() {
		if (projectTask == null) {
			return "";
		}
		if (projectTask.getContact() == null) {
			return "";
		}
		return (projectTask.getContact().getName() != null ? projectTask
				.getContact().getName() : "")
				+ " "
				+ (projectTask.getContact().getSurname() != null ? projectTask
						.getContact().getSurname() : "")
				+ " ("
				+ projectTask.getContact().getCompany().getName() + ")";
	}

	public String getUserFullName() {
		if (projectTask == null) {
			return "";
		}
		if (projectTask.getUsersByUserDispatcherId() == null) {
			return "";
		}
		return (projectTask.getUsersByUserDispatcherId().getName() != null ? projectTask
				.getUsersByUserDispatcherId().getName() : "")
				+ " "
				+ (projectTask.getUsersByUserDispatcherId().getSurname() != null ? projectTask
						.getUsersByUserDispatcherId().getSurname() : "");
	}

	public void setuserFullName(String userFullName) {
		// stub
	}

	public void setContactFullName(String contactFullName) {
		// stub
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public ProjectTask getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTask task) {
		this.projectTask = task;
	}

	public List<UserHierarchy> getUserHierarchies() {
		return userHierarchies;
	}

	public void setUserHierarchies(List<UserHierarchy> userHierarchies) {
		this.userHierarchies = userHierarchies;
	}

	public List<TaskType> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskType> taskTypes) {
		this.taskTypes = taskTypes;
	}

	public List<TaskState> getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(List<TaskState> taskStates) {
		this.taskStates = taskStates;
	}

	public List<TaskPriority> getTaskPriorities() {
		return taskPriorities;
	}

	public void setTaskPriorities(List<TaskPriority> taskPriorities) {
		this.taskPriorities = taskPriorities;
	}

	public List<TaskResult> getTaskResults() {
		return taskResults;
	}

	public void setTaskResults(List<TaskResult> taskResults) {
		this.taskResults = taskResults;
	}

	public UserHierarchy getSelectedUserHierarchy() {
		return selectedUserHierarchy;
	}

	public void setSelectedUserHierarchy(UserHierarchy selectedUserHierarchy) {
		this.selectedUserHierarchy = selectedUserHierarchy;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<TaskDocument> getTaskDocuments() {
		return taskDocuments;
	}

	public void setTaskDocuments(List<TaskDocument> taskDocuments) {
		this.taskDocuments = taskDocuments;
	}

	public TaskDocument getTaskDocument() {
		return taskDocument;
	}

	public void setTaskDocument(TaskDocument taskDocument) {
		this.taskDocument = taskDocument;
	}

	public List<TaskDocument> getTaskDocumentsToBeDeleted() {
		return taskDocumentsToBeDeleted;
	}

	public void setTaskDocumentsToBeDeleted(
			List<TaskDocument> taskDocumentsToBeDeleted) {
		this.taskDocumentsToBeDeleted = taskDocumentsToBeDeleted;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}
}
