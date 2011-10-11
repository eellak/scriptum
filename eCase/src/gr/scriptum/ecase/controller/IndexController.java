/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import gr.scriptum.controller.BaseController;
import gr.scriptum.domain.Project;

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
		project = null;
		projects = null;
		selectedProject = null;
		projectStartDateFrom = null;
		projectStartDateTo = null;
		projectEndDateFrom = null;
		projectEndDateTo = null;
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
		}

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
