/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.dao.ProjectTaskDAO;
import gr.scriptum.dao.TaskMessageDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.TaskMessage;
import gr.scriptum.ecase.util.IConstants;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

/**
 * @author aanagnostopoulos
 * 
 */
public class IndexController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7471983169677591144L;

	private static Log log = LogFactory.getLog(IndexController.class);

	public static final String PAGE = "index.zul";

	public static final String PARAM_SELECTED_TAB = "tab";

	/* Components */
	Window indexWin;
	Tabbox indexTbx;

	// incoming tasks tab related
	Tab incomingTasksTb;
	Listbox incomingTasksLstbx;
	Paging incomingTasksPgng;
	Listheader incomingTaskStateLsthdr;

	// outgoing tasks tab related
	Tab outgoingTasksTb;
	Listbox outgoingTasksLstbx;
	Paging outgoingTasksPgng;
	Listitem outgoingTaskLstItm;
	Listheader outgoingTaskStateLsthdr;

	// projects tab related
	Tab projectsTb;
	Listbox projectsLstbx;
	Paging projectsPgng;

	// incoming messages tab related
	Tab incomingMessagesTb;
	Listbox incomingMessagesLstbx;
	Paging incomingMessagesPgng;

	// outgoing messages tab related
	Tab outgoingMessagesTb;
	Listbox outgoingMessagesLstbx;
	Paging outgoingMessagesPgng;

	/* Data binding */
	// incoming tasks tab related
	private ProjectTask incomingTask = null;
	private List<ProjectTask> incomingTasks = null;
	private ProjectTask selectedIncomingTask = null;
	private Date incomingTaskStartDateFrom = null;
	private Date incomingTaskStartDateTo = null;
	private Date incomingTaskEndDateFrom = null;
	private Date incomingTaskEndDateTo = null;

	// outgoing tasks tab related
	private ProjectTask outgoingTask = null;
	private List<ProjectTask> outgoingTasks = null;
	private ProjectTask selectedOutgoingTask = null;
	private Date outgoingTaskStartDateFrom = null;
	private Date outgoingTaskStartDateTo = null;
	private Date outgoingTaskEndDateFrom = null;
	private Date outgoingTaskEndDateTo = null;

	// project tab related
	private Project project = null;
	private List<Project> projects = null;
	private Project selectedProject = null;
	private Date projectStartDateFrom = null;
	private Date projectStartDateTo = null;
	private Date projectEndDateFrom = null;
	private Date projectEndDateTo = null;

	// incoming messages tab related
	private TaskMessage incomingMessage = null;
	private List<TaskMessage> incomingMessages = null;
	private TaskMessage selectedIncomingMessage = null;
	private Date incomingMessageDateFrom = null;
	private Date incomingMessageDateTo = null;

	// outgoing messages tab related
	private TaskMessage outgoingMessage = null;
	private List<TaskMessage> outgoingMessages = null;
	private TaskMessage selectedOutgoingMessage = null;
	private Date outgoingMessageDateFrom = null;
	private Date outgoingMessageDateTo = null;

	private void initIncomingTasks() {
		incomingTask = new ProjectTask();
		incomingTasks = null;
		selectedIncomingTask = null;
		incomingTaskStartDateFrom = null;
		incomingTaskStartDateTo = null;
		incomingTaskEndDateFrom = null;
		incomingTaskEndDateTo = null;
	}

	private void initOutgoingTasks() {
		outgoingTask = new ProjectTask();
		outgoingTasks = null;
		selectedOutgoingTask = null;
		outgoingTaskStartDateFrom = null;
		outgoingTaskStartDateTo = null;
		outgoingTaskEndDateFrom = null;
		outgoingTaskEndDateTo = null;
	}

	private void initProjects() {
		project = new Project();
		projects = null;
		selectedProject = null;
		projectStartDateFrom = null;
		projectStartDateTo = null;
		projectEndDateFrom = null;
		projectEndDateTo = null;
	}

	private void initIncomingMessages() {
		incomingMessage = new TaskMessage();
		incomingMessages = null;
		selectedIncomingMessage = null;
		incomingMessageDateFrom = null;
		incomingMessageDateTo = null;
	}

	private void initOutgoingMessages() {
		outgoingMessage = new TaskMessage();
		outgoingMessages = null;
		selectedOutgoingMessage = null;
		outgoingMessageDateFrom = null;
		outgoingMessageDateTo = null;
	}

	@Override
	protected List<Order> getSortBy(Listheader header) {
		
		if (header.equals(incomingTaskStateLsthdr)
				|| header.equals(outgoingTaskStateLsthdr)) {
			// perform custom sorting for these columns
			String[] tokens = header.getValue().toString()
					.split(IConstants.SORTING_DELIMITER);
			List<Order> sortBy = new LinkedList<Order>();
			if (header.getSortDirection().equals("ascending")) {
				sortBy.add(Order.asc(tokens[0]));
				sortBy.add(Order.desc(tokens[1]));
				sortBy.add(Order.desc(tokens[2]));
			} else {
				sortBy.add(Order.desc(tokens[0]));
				sortBy.add(Order.asc(tokens[1]));
				sortBy.add(Order.asc(tokens[2]));
			}
			return sortBy;

		} else { // perfrorm normal sorting, as defined by super implementation
			return super.getSortBy(header);
		}
	}

	private void searchIncomingTasks(Integer startIndex) {
		incomingTask = (ProjectTask) trimStringProperties(incomingTask);
		ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
		// set up paging by counting records first
		Integer totalSize = projectTaskDAO.countSearch(incomingTask.getName(),
				incomingTaskStartDateFrom, incomingTaskStartDateTo,
				incomingTaskEndDateFrom, incomingTaskEndDateTo,
				getUserInSession(), null);
		incomingTasksPgng.setTotalSize(totalSize);
		int pageSize = incomingTasksPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(incomingTasksLstbx);
		List<Order> sortBy = getSortBy(header);

		incomingTasks = projectTaskDAO.search(incomingTask.getName(),
				incomingTaskStartDateFrom, incomingTaskStartDateTo,
				incomingTaskEndDateFrom, incomingTaskEndDateTo,
				getUserInSession(), null, startIndex, pageSize,
				sortBy.toArray(new Order[0]));

		Date now = new Date();
		for (ProjectTask incomingTask : incomingTasks) {
			if (incomingTask.getEndDt() == null) {
				incomingTask.setIsTaskExpired(false);
				continue;
			}
			if (now.after(incomingTask.getEndDt())) {
				incomingTask.setIsTaskExpired(true);
			} else {
				incomingTask.setIsTaskExpired(false);
			}
		}

	}

	private void searchOutgoingTasks(Integer startIndex) {
		outgoingTask = (ProjectTask) trimStringProperties(outgoingTask);
		ProjectTaskDAO projectTaskDAO = new ProjectTaskDAO();
		// set up paging by counting records first
		Integer totalSize = projectTaskDAO.countSearch(outgoingTask.getName(),
				outgoingTaskStartDateFrom, outgoingTaskStartDateTo,
				outgoingTaskEndDateFrom, outgoingTaskEndDateTo, null,
				getUserInSession());
		outgoingTasksPgng.setTotalSize(totalSize);
		int pageSize = outgoingTasksPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(outgoingTasksLstbx);
		List<Order> sortBy = getSortBy(header);
		outgoingTasks = projectTaskDAO.search(outgoingTask.getName(),
				outgoingTaskStartDateFrom, outgoingTaskStartDateTo,
				outgoingTaskEndDateFrom, outgoingTaskEndDateTo, null,
				getUserInSession(), startIndex, pageSize,
				sortBy.toArray(new Order[0]));

		Date now = new Date();
		for (ProjectTask outgoingTask : outgoingTasks) {
			if (outgoingTask.getEndDt() == null) {
				outgoingTask.setIsTaskExpired(false);
				continue;
			}
			if (now.after(outgoingTask.getEndDt())) {
				outgoingTask.setIsTaskExpired(true);
			} else {
				outgoingTask.setIsTaskExpired(false);
			}
		}

	}

	private void searchProjects(Integer startIndex) {

		project = (Project) trimStringProperties(project);
		ProjectDAO projectDAO = new ProjectDAO();
		// set up paging by counting records first
		Integer totalSize = projectDAO.countSearch(project.getName(),
				projectStartDateFrom, projectStartDateTo, projectEndDateFrom,
				projectEndDateTo);
		projectsPgng.setTotalSize(totalSize);
		int pageSize = projectsPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(projectsLstbx);
		List<Order> sortBy = getSortBy(header);

		projects = projectDAO.search(project.getName(), projectStartDateFrom,
				projectStartDateTo, projectEndDateFrom, projectEndDateTo,
				startIndex, pageSize, sortBy.toArray(new Order[0]));
	}

	private void searchIncomingMessages(Integer startIndex) {

		incomingMessage = (TaskMessage) trimStringProperties(incomingMessage);
		TaskMessageDAO taskMessageDAO = new TaskMessageDAO();
		// set up paging by counting records first
		Integer totalSize = taskMessageDAO.countSearch(
				incomingMessage.getSubject(), incomingMessageDateFrom,
				incomingMessageDateTo, null, getUserInSession());
		incomingMessagesPgng.setTotalSize(totalSize);
		int pageSize = incomingMessagesPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(incomingMessagesLstbx);
		List<Order> sortBy = getSortBy(header);

		incomingMessages = taskMessageDAO.search(incomingMessage.getSubject(),
				incomingMessageDateFrom, incomingMessageDateTo, null,
				getUserInSession(), startIndex, pageSize,
				sortBy.toArray(new Order[0]));

	}

	private void searchOutgoingMessages(Integer startIndex) {

		outgoingMessage = (TaskMessage) trimStringProperties(outgoingMessage);
		TaskMessageDAO taskMessageDAO = new TaskMessageDAO();
		// set up paging by counting records first
		Integer totalSize = taskMessageDAO.countSearch(
				outgoingMessage.getSubject(), outgoingMessageDateFrom,
				outgoingMessageDateTo, getUserInSession(), null);
		outgoingMessagesPgng.setTotalSize(totalSize);
		int pageSize = outgoingMessagesPgng.getPageSize();

		// figure out which header to sort by
		Listheader header = getSortingListheader(outgoingMessagesLstbx);
		List<Order> sortBy = getSortBy(header);

		outgoingMessages = taskMessageDAO.search(outgoingMessage.getSubject(),
				outgoingMessageDateFrom, outgoingMessageDateTo,
				getUserInSession(), null, startIndex, pageSize,
				sortBy.toArray(new Order[0]));
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		// initProjects();
		String tab = execution.getParameter(PARAM_SELECTED_TAB);

		if (tab != null) {

			if (tab.equals(incomingTasksTb.getId())) {
				initIncomingTasks();
				indexTbx.setSelectedTab(incomingTasksTb);
				searchIncomingTasks(0);
			} else if (tab.equals(outgoingTasksTb.getId())) {
				initOutgoingTasks();
				indexTbx.setSelectedTab(outgoingTasksTb);
				searchOutgoingTasks(0);
			} else if (tab.equals(projectsTb.getId())) {
				initProjects();
				indexTbx.setSelectedTab(projectsTb);
				searchProjects(0);
			} else if (tab.equals(incomingMessagesTb.getId())) {
				initIncomingMessages();
				indexTbx.setSelectedTab(incomingMessagesTb);
				searchIncomingMessages(0);
			} else if (tab.equals(incomingMessagesTb.getId())) {
				initOutgoingMessages();
				indexTbx.setSelectedTab(outgoingMessagesTb);
				searchOutgoingMessages(0);
			}

		} else {
			initIncomingTasks();
			indexTbx.setSelectedTab(incomingTasksTb);
			searchIncomingTasks(0);
		}
	}

	/**
	 * Custom sorting event listener, overriding default sorting mechanism.
	 * Instead, database sorting is used. The property to be sorted by gets
	 * picked up from the 'value' attribute of the Listheader triggering the
	 * event. The sorting order (asc,desc) used the Listheader's sortDirection
	 * attribute.
	 * 
	 * @param event
	 *            The sorting event
	 */
	public void onSort(Event event) {

		Event sortEvent = ((ForwardEvent) event).getOrigin();
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		Listbox parent = (Listbox) header.getParent().getParent();
		setSortingListheader(header);

		if (parent.equals(projectsLstbx)) {
			searchProjects(0);
			projectsPgng.setActivePage(0);
		} else if (parent.equals(incomingTasksLstbx)) {
			searchIncomingTasks(0);
			incomingTasksPgng.setActivePage(0);
		} else if (parent.equals(outgoingTasksLstbx)) {
			searchOutgoingTasks(0);
			outgoingTasksPgng.setActivePage(0);
		} else if (parent.equals(incomingMessagesLstbx)) {
			searchIncomingMessages(0);
			incomingMessagesPgng.setActivePage(0);
		} else if (parent.equals(outgoingMessagesLstbx)) {
			searchOutgoingMessages(0);
			outgoingMessagesPgng.setActivePage(0);
		}

		getBinder(indexWin).loadAll();
	}

	/* incoming Tasks tab related */
	public void onSelect$incomingTasksTb(SelectEvent event) {

		initIncomingTasks();
		searchIncomingTasks(0);
		getBinder(indexWin).loadAll();
	}

	public void onPaging$incomingTasksPgng(PagingEvent event) {

		int activePage = event.getActivePage();
		int startIndex = activePage * incomingTasksPgng.getPageSize();
		searchIncomingTasks(startIndex);
		getBinder(indexWin).loadAll();

	}

	public void onSelect$incomingTasksLstbx(SelectEvent event) {

		log.info(event);
		Integer id = selectedIncomingTask.getId();

		Executions.getCurrent().sendRedirect(
				IncomingTaskController.PAGE_INCOMING + "?"
						+ IConstants.PARAM_KEY_ID + "=" + id);
	}

	public void onClick$searchIncomingTasksBtn() throws InterruptedException {

		searchIncomingTasks(0);
		getBinder(indexWin).loadAll();

		if (incomingTasks.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearIncomingTasksBtn() {

		initIncomingTasks();
		getBinder(indexWin).loadAll();
	}

	// public void onClick$newIncomingTaskBtn() {
	// Executions.getCurrent().sendRedirect(TaskController.PAGE);
	// }

	/* outgoing Tasks tab related */
	public void onSelect$outgoingTasksTb(SelectEvent event) {
		initOutgoingTasks();
		searchOutgoingTasks(0);
		getBinder(indexWin).loadAll();
	}

	public void onPaging$outgoingTasksPgng(PagingEvent event) {

		int activePage = event.getActivePage();
		int startIndex = activePage * outgoingTasksPgng.getPageSize();
		searchOutgoingTasks(startIndex);
		getBinder(indexWin).loadAll();

	}

	public void onSelect$outgoingTasksLstbx(SelectEvent event) {
		log.info(event);
		Integer id = selectedOutgoingTask.getId();

		Executions.getCurrent().sendRedirect(
				OutgoingTaskController.PAGE + "?" + IConstants.PARAM_KEY_ID
						+ "=" + id);
	}

	public void onClick$searchOutgoingTasksBtn() throws InterruptedException {
		searchOutgoingTasks(0);
		getBinder(indexWin).loadAll();

		if (outgoingTasks.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearOutgoingTasksBtn() {
		initOutgoingTasks();
		getBinder(indexWin).loadAll();
	}

	public void onClick$newOutgoingTaskBtn() {
		Executions.getCurrent().sendRedirect(OutgoingTaskController.PAGE);
	}

	/* projects tab related */
	public void onSelect$projectsTb(SelectEvent event) {
		initProjects();
		searchProjects(0);
		getBinder(indexWin).loadAll();
	}

	public void onClick$searchProjectsBtn() throws InterruptedException {
		searchProjects(0);
		getBinder(indexWin).loadAll();

		if (projects.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearProjectsBtn() {
		initProjects();
		getBinder(indexWin).loadAll();
	}

	public void onClick$newProjectBtn() {
		Executions.getCurrent().sendRedirect(ProjectController.PAGE);
	}

	public void onSelect$projectsLstbx(SelectEvent event) {
		Integer id = selectedProject.getId();

		Executions.getCurrent().sendRedirect(
				ProjectController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);

	}

	/* incoming messages tab related */
	public void onSelect$incomingMessagesTb(SelectEvent event) {
		initIncomingMessages();
		searchIncomingMessages(0);
		getBinder(indexWin).loadAll();
	}

	public void onSelect$incomingMessagesLstbx(SelectEvent event) {
		Integer id = selectedIncomingMessage.getId();

		Executions.getCurrent().sendRedirect(
				MessageController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onClick$searchIncomingMessagesBtn() throws InterruptedException {
		searchIncomingMessages(0);
		getBinder(indexWin).loadAll();

		if (incomingMessages.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearIncomingMessagesBtn() {
		initIncomingMessages();
		getBinder(indexWin).loadAll();
	}

	/* outoing messages tab related */
	public void onSelect$outgoingMessagesTb(SelectEvent event) {
		initOutgoingMessages();
		searchOutgoingMessages(0);
		getBinder(indexWin).loadAll();
	}

	public void onSelect$outgoingMessagesLstbx(SelectEvent event) {
		Integer id = selectedOutgoingMessage.getId();

		Executions.getCurrent().sendRedirect(
				MessageController.PAGE + "?" + IConstants.PARAM_KEY_ID + "="
						+ id);
	}

	public void onClick$searchOutgoingMessagesBtn() throws InterruptedException {
		searchOutgoingMessages(0);
		getBinder(indexWin).loadAll();

		if (outgoingMessages.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"),
					Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onClick$clearOutgoingMessagesBtn() {
		initOutgoingMessages();
		getBinder(indexWin).loadAll();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}

	public Date getProjectStartDateFrom() {
		return projectStartDateFrom;
	}

	public void setProjectStartDateFrom(Date projectStartDateFrom) {
		this.projectStartDateFrom = projectStartDateFrom;
	}

	public Date getProjectStartDateTo() {
		return projectStartDateTo;
	}

	public void setProjectStartDateTo(Date projectStartDateTo) {
		this.projectStartDateTo = projectStartDateTo;
	}

	public Date getProjectEndDateFrom() {
		return projectEndDateFrom;
	}

	public void setProjectEndDateFrom(Date projectEndDateFrom) {
		this.projectEndDateFrom = projectEndDateFrom;
	}

	public Date getProjectEndDateTo() {
		return projectEndDateTo;
	}

	public void setProjectEndDateTo(Date projectEndDateTo) {
		this.projectEndDateTo = projectEndDateTo;
	}

	public ProjectTask getIncomingTask() {
		return incomingTask;
	}

	public void setIncomingTask(ProjectTask incomingTask) {
		this.incomingTask = incomingTask;
	}

	public List<ProjectTask> getIncomingTasks() {
		return incomingTasks;
	}

	public void setIncomingTasks(List<ProjectTask> incomingTasks) {
		this.incomingTasks = incomingTasks;
	}

	public ProjectTask getSelectedIncomingTask() {
		return selectedIncomingTask;
	}

	public void setSelectedIncomingTask(ProjectTask selectedIncomingTask) {
		this.selectedIncomingTask = selectedIncomingTask;
	}

	public Date getIncomingTaskStartDateFrom() {
		return incomingTaskStartDateFrom;
	}

	public void setIncomingTaskStartDateFrom(Date incomingTaskStartDateFrom) {
		this.incomingTaskStartDateFrom = incomingTaskStartDateFrom;
	}

	public Date getIncomingTaskStartDateTo() {
		return incomingTaskStartDateTo;
	}

	public void setIncomingTaskStartDateTo(Date incomingTaskStartDateTo) {
		this.incomingTaskStartDateTo = incomingTaskStartDateTo;
	}

	public Date getIncomingTaskEndDateFrom() {
		return incomingTaskEndDateFrom;
	}

	public void setIncomingTaskEndDateFrom(Date incomingTaskEndDateFrom) {
		this.incomingTaskEndDateFrom = incomingTaskEndDateFrom;
	}

	public Date getIncomingTaskEndDateTo() {
		return incomingTaskEndDateTo;
	}

	public void setIncomingTaskEndDateTo(Date incomingTaskEndDateTo) {
		this.incomingTaskEndDateTo = incomingTaskEndDateTo;
	}

	public ProjectTask getOutgoingTask() {
		return outgoingTask;
	}

	public void setOutgoingTask(ProjectTask outgoingTask) {
		this.outgoingTask = outgoingTask;
	}

	public List<ProjectTask> getOutgoingTasks() {
		return outgoingTasks;
	}

	public void setOutgoingTasks(List<ProjectTask> outgoingTasks) {
		this.outgoingTasks = outgoingTasks;
	}

	public ProjectTask getSelectedOutgoingTask() {
		return selectedOutgoingTask;
	}

	public void setSelectedOutgoingTask(ProjectTask selectedOutgoingTask) {
		this.selectedOutgoingTask = selectedOutgoingTask;
	}

	public Date getOutgoingTaskStartDateFrom() {
		return outgoingTaskStartDateFrom;
	}

	public void setOutgoingTaskStartDateFrom(Date outgoingTaskStartDateFrom) {
		this.outgoingTaskStartDateFrom = outgoingTaskStartDateFrom;
	}

	public Date getOutgoingTaskStartDateTo() {
		return outgoingTaskStartDateTo;
	}

	public void setOutgoingTaskStartDateTo(Date outgoingTaskStartDateTo) {
		this.outgoingTaskStartDateTo = outgoingTaskStartDateTo;
	}

	public Date getOutgoingTaskEndDateFrom() {
		return outgoingTaskEndDateFrom;
	}

	public void setOutgoingTaskEndDateFrom(Date outgoingTaskEndDateFrom) {
		this.outgoingTaskEndDateFrom = outgoingTaskEndDateFrom;
	}

	public Date getOutgoingTaskEndDateTo() {
		return outgoingTaskEndDateTo;
	}

	public void setOutgoingTaskEndDateTo(Date outgoingTaskEndDateTo) {
		this.outgoingTaskEndDateTo = outgoingTaskEndDateTo;
	}

	public TaskMessage getIncomingMessage() {
		return incomingMessage;
	}

	public void setIncomingMessage(TaskMessage incomingMessage) {
		this.incomingMessage = incomingMessage;
	}

	public List<TaskMessage> getIncomingMessages() {
		return incomingMessages;
	}

	public void setIncomingMessages(List<TaskMessage> incomingMessages) {
		this.incomingMessages = incomingMessages;
	}

	public TaskMessage getSelectedIncomingMessage() {
		return selectedIncomingMessage;
	}

	public void setSelectedIncomingMessage(TaskMessage selectedIncomingMessage) {
		this.selectedIncomingMessage = selectedIncomingMessage;
	}

	public Date getIncomingMessageDateFrom() {
		return incomingMessageDateFrom;
	}

	public void setIncomingMessageDateFrom(Date incomingMessageDateFrom) {
		this.incomingMessageDateFrom = incomingMessageDateFrom;
	}

	public Date getIncomingMessageDateTo() {
		return incomingMessageDateTo;
	}

	public void setIncomingMessageDateTo(Date incomingMessageDateTo) {
		this.incomingMessageDateTo = incomingMessageDateTo;
	}

	public TaskMessage getOutgoingMessage() {
		return outgoingMessage;
	}

	public void setOutgoingMessage(TaskMessage outgoingMessage) {
		this.outgoingMessage = outgoingMessage;
	}

	public List<TaskMessage> getOutgoingMessages() {
		return outgoingMessages;
	}

	public void setOutgoingMessages(List<TaskMessage> outgoingMessages) {
		this.outgoingMessages = outgoingMessages;
	}

	public TaskMessage getSelectedOutgoingMessage() {
		return selectedOutgoingMessage;
	}

	public void setSelectedOutgoingMessage(TaskMessage selectedOutgoingMessage) {
		this.selectedOutgoingMessage = selectedOutgoingMessage;
	}

	public Date getOutgoingMessageDateFrom() {
		return outgoingMessageDateFrom;
	}

	public void setOutgoingMessageDateFrom(Date outgoingMessageDateFrom) {
		this.outgoingMessageDateFrom = outgoingMessageDateFrom;
	}

	public Date getOutgoingMessageDateTo() {
		return outgoingMessageDateTo;
	}

	public void setOutgoingMessageDateTo(Date outgoingMessageDateTo) {
		this.outgoingMessageDateTo = outgoingMessageDateTo;
	}

}
