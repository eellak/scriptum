/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.ecase.util.IConstants;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

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

	Tab projectsTab;

	Listbox projectsLstbx;
	Paging projectsPgng;

	/* Data binding */
	private Project project = null;
	private List<Project> projects = null;
	private Project selectedProject = null;
	private Date projectStartDateFrom = null;
	private Date projectStartDateTo = null;
	private Date projectEndDateFrom = null;
	private Date projectEndDateTo = null;

	private void initProjects() {
		project = new Project();
		projects = null;
		selectedProject = null;
		projectStartDateFrom = null;
		projectStartDateTo = null;
		projectEndDateFrom = null;
		projectEndDateTo = null;
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

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		initProjects();
		String tab = execution.getParameter(PARAM_SELECTED_TAB);

		if (tab != null) {
			if (tab.equals(projectsTab.getId())) {
				indexTbx.setSelectedTab(projectsTab);
			}
		} else {
			searchProjects(0);
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
		}

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

}
