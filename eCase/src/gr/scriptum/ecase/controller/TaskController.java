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
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.RequestSendDocument;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;
import gr.scriptum.util.Callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

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

	/* componenents */
	Window taskWin;
	Window uploadWin;
	Paging contactsPgng;
	Bandbox userHierarchyBndbx;
	Bandbox contactBndbx;
	Listbox documentsLstbx;

	/* data binding */
	private List<Project> projects = null;
	private ProjectTask projectTask = null;
	private List<UserHierarchy> userHierarchies = null;
	private List<TaskType> taskTypes = null;
	private List<TaskState> taskStates = null;
	private List<TaskPriority> taskPriorities = null;
	private List<TaskResult> taskResults = null;
	private List<Contact> contacts = null;
	private UserHierarchy selectedUserHierarchy = null;
	private List<TaskDocument> taskDocuments = null;
	private TaskDocument taskDocument = null;
	private String term = null;

	private String okmNodeTask = null;

	private void initTask() {
		projectTask = new ProjectTask();
		projectTask.setUsersByUserCreatorId(getUserInSession());
	}

	private void initContacts() {
		contacts = new ArrayList<Contact>();
	}

	private void initDocuments() {
		taskDocuments = new LinkedList<TaskDocument>();
	}

	private void addHierarchyBranch(UserHierarchy root) {
		userHierarchies.add(root);
		for (UserHierarchy child : root.getUserHierarchies()) {
			addHierarchyBranch(child);
		}
	}

	private void refreshProjects() {
		ProjectDAO projectDAO = new ProjectDAO();
		projects = projectDAO.findByCreator(getUserInSession());
	}

	private void refreshTaskTypes() {
		TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
		taskTypes = taskTypeDAO.findAll();
	}

	private void refreshTaskPriorities() {
		TaskPriorityDAO taskPriorityDAO = new TaskPriorityDAO();
		taskPriorities = taskPriorityDAO.findAll();
	}

	private void refreshTaskStates() {
		TaskStateDAO taskStateDAO = new TaskStateDAO();
		taskStates = taskStateDAO.findAll();
	}

	private void refreshTaskResults() {
		TaskResultDAO taskResultDAO = new TaskResultDAO();
		taskResults = taskResultDAO.findAll();
	}

	private void refreshUserHierarchies() {
		userHierarchies = new ArrayList<UserHierarchy>();
		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();
		List<UserHierarchy> hierarchies = userHierarchyDAO
				.findByUser(getUserInSession());

		for (UserHierarchy hierarchy : hierarchies) {
			addHierarchyBranch(hierarchy);
		}
	}

	private void searchContacts(Integer startIndex) {
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

	private void save() throws Exception {

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

		try {

			tx.begin();
			log.info("Saving Task (Started transaction): " + tx);

			ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
			TaskDocumentDAO taskDocumentDAO = new TaskDocumentDAO();
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			if (projectTask.getId() == null) { // new task, create

				projectTask.setCreateDt(now);

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
				projectTaskDAO.update(projectTask); //TODO: Which task fields are modifiable?
				
				//TODO: Should documents be modifiable?????
				
			}

			tx.commit();
			log.info("Saved Task (Committed transaction): " + tx);

		} catch (Exception e) {
			log.error(e);
			if (tx.getStatus() == Status.STATUS_ACTIVE) {
				tx.rollback();
				log.info("Rolled back transaction: " + tx);
			}
			throw e;
		}

	}

	private ResponseSendDocument fetchDocumentFromOpenKM(
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

		initContacts();
		initDocuments();
		refreshTaskPriorities();
		refreshTaskStates();
		refreshTaskTypes();
		refreshTaskResults();

		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) { // existing task

			ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
			projectTask = projectTaskDAO.findById(Integer.valueOf(idString),
					false);

			projects = new ArrayList<Project>();
			projects.add(projectTask.getProject());

			taskDocuments.addAll(projectTask.getTaskDocuments());

		} else { // new task
			initTask();
			refreshProjects();
			refreshUserHierarchies();
		}
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

	public void onSelect$documentsLstbx(SelectEvent event) {
		getBinder(taskWin).loadAll();
	}

	public void onClick$saveBtn() throws InterruptedException {

		validateFields(taskWin);

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

	public boolean isAddFileBtnDisabled() {
		if (projectTask.getId() != null) {
			return true;
		}
		return false;
	}

	public boolean isRemoveFileBtnDisabled() {
		if (projectTask.getId() != null) {
			return true;
		}

		if (taskDocument == null) {
			return true;
		}

		return false;

	}

	public boolean isDownloadFileBtnDisabled() {

		if (taskDocument == null) {
			return true;
		}

		return false;

	}

	public boolean isParentTaskVisible() {
		if (projectTask != null && projectTask.getProjectTask() != null) {
			return true;
		}
		return false;
	}

	public boolean isSaveBtnDisabled() {
		if (projectTask == null) {
			return true;
		}
		return false;
	}

	public boolean isProjectCbxReadonly() {
		if (projectTask == null) {
			return true;
		}

		if (projectTask.getId() != null) {
			return true;
		}
		return false;
	}

	public boolean isProjectCbxVisible() {
		if (projectTask != null && projectTask.getId() == null) {
			return true;
		}
		return false;
	}

	public boolean isProjectHbxVisible() {
		return !(isProjectCbxVisible());
	}

	public boolean isUserHierarchyBndbxVisible() {
		if (projectTask != null && projectTask.getId() == null) {
			return true;
		}
		return false;
	}

	public boolean isUsersByUserDispatcherIdHbxVisible() {
		return !(isUserHierarchyBndbxVisible());
	}

	public String getContactFullName() {
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

	}

	public void setContactFullName(String contactFullName) {

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
}
