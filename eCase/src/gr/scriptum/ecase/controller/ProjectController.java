/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericEntityController;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectUser;
import gr.scriptum.domain.ProjectUserId;
import gr.scriptum.domain.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.SelectEvent;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProjectController extends
		GenericEntityController<Project, ProjectDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5346867503629173059L;

	private static Log log = LogFactory.getLog(ProjectController.class);

	public static final String PAGE = "project.zul";

	/* data binding */
	private List<Users> users = null;
	private Users selectedUser = null;
	private ProjectUser projectUser = null;

	private void initProject() {
		((Project) entity).setUsers(getUserInSession());
		((Project) entity).setProjectUsers(new HashSet<ProjectUser>());
	}

	/**
	 * Helper method. Due to Hibernate proxy problems, we're not able to use
	 * list.remove() method.
	 * 
	 * @param userToBeRemoved
	 */
	private void removeFromUsers(Users userToBeRemoved) {
		int indexToBeRemoved = -1;
		for (int i = 0; i < users.size(); i++) {
			Users user = users.get(i);
			if (user.getId().intValue() == userToBeRemoved.getId().intValue()) {
				indexToBeRemoved = i;
				break;
			}
		}
		if (indexToBeRemoved > -1) {
			users.remove(indexToBeRemoved);
		}
	}

	private void refreshUsers() {
		UsersDAO usersDAO = new UsersDAO();
		users = usersDAO.findAll();
		for (ProjectUser projectUser : ((Project) entity).getProjectUsers()) {
			log.info(projectUser.getId().getUsers().getId());
			removeFromUsers(projectUser.getId().getUsers());
		}
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (isEntityNotCreated()) {
			initProject();
		}
		refreshUsers();
	}

	@Override
	public void onClick$newBtn() throws Exception {
		super.onClick$newBtn();
		initProject();
		refreshUsers();
		getBinder(win).loadAll();
	}

	public void onSelect$usersLstbx(SelectEvent event) {
		projectUser = null;
		getBinder(win).loadAll();
	}

	public void onSelect$projectUsersLstbx(SelectEvent event) {
		selectedUser = null;
		getBinder(win).loadAll();
	}

	public void onClick$addProjectUserBtn() {
		ProjectUser projectUser = new ProjectUser();
		projectUser.setId(new ProjectUserId((Project) entity, selectedUser));
		((Project) entity).getProjectUsers().add(projectUser);
		removeFromUsers(selectedUser);
		getBinder(win).loadAll();
	}

	public void onClick$removeProjectUserBtn() {
		((Project) entity).getProjectUsers().remove(projectUser);
		users.add(projectUser.getId().getUsers());
		getBinder(win).loadAll();
	}

	public boolean isAddProjectUserBtnDisabled() throws Exception {
		return !(entity != null && selectedUser != null);
	}

	public boolean isRemoveProjectUserBtnDisabled() throws Exception {
		return !(entity != null && projectUser != null);
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Users user) {
		this.selectedUser = user;
	}

	public ProjectUser getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(ProjectUser projectUser) {
		this.projectUser = projectUser;
	}
}
