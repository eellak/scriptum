/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.dao.TaskPriorityDAO;
import gr.scriptum.dao.TaskStateDAO;
import gr.scriptum.dao.TaskTypeDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskDocument;
import gr.scriptum.domain.TaskPriority;
import gr.scriptum.domain.TaskResult;
import gr.scriptum.domain.TaskState;
import gr.scriptum.domain.TaskType;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.ecase.util.IConstants;

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

	private void initTask() {
		projectTask = new ProjectTask();
		projectTask.setUsersByUserCreatorId(getUserInSession());
	}

	private void initContacts() {
		contacts = new ArrayList<Contact>();
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		initContacts();
		refreshUserHierarchies();
		refreshTaskPriorities();
		refreshTaskStates();
		refreshTaskTypes();

		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) { // existing task

		} else { // new task
			initTask();
			refreshProjects();
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
