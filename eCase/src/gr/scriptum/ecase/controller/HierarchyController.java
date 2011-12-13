/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.ecase.util.UserHierarchyTreeRenderer;

/**
 * @author aanagnostopoulos
 * 
 */
public class HierarchyController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1207017426214746275L;

	private static Log log = LogFactory.getLog(HierarchyController.class);

	/* components */
	Window hierarchyWin;
	Tree hierarchyTree;

	/* data binding */
	private List<Department> departments = null;
	private Department department = null;
	private UserHierarchy userHierarchy = null;
	private List<Users> users = null;
	private Users user = null;

	private DefaultTreeNode getTreeBranch(UserHierarchy root) {
		DefaultTreeNode rootNode = null;

		if (root.getUserHierarchies().isEmpty()) {
			rootNode = new DefaultTreeNode(root);
		} else {
			rootNode = new DefaultTreeNode(root,
					new ArrayList<DefaultTreeNode>(root.getUserHierarchies()
							.size()));
		}

		for (UserHierarchy child : root.getUserHierarchies()) {
			rootNode.add(getTreeBranch(child));
		}

		return rootNode;
	}

	private void refreshTree() {

		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();
		List<UserHierarchy> rootUserHierarchies = userHierarchyDAO
				.findRootUserHierarchies(department);

		DefaultTreeNode root = null;
		if (rootUserHierarchies.isEmpty()) {
			root = new DefaultTreeNode(null);
		} else {
			root = new DefaultTreeNode(null, new ArrayList<DefaultTreeNode>(
					rootUserHierarchies.size()));
		}

		for (UserHierarchy rootUserHierarchy : rootUserHierarchies) {

			DefaultTreeNode branchNode = getTreeBranch(rootUserHierarchy);
			root.add(branchNode);
		}

		hierarchyTree.setItemRenderer(new UserHierarchyTreeRenderer());
		hierarchyTree.setModel(new DefaultTreeModel(root));

	}

	private void refreshDepartments() {
		DepartmentDAO departmentDAO = new DepartmentDAO();
		departments = departmentDAO.findAll();
	}

	private void refreshUsers() {
		UsersDAO usersDAO = new UsersDAO();
		users = usersDAO.findUnassignedUsers();
	}

	private void delete() throws InterruptedException {

		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();

		userHierarchyDAO.deleteById(userHierarchy.getId());

		Messagebox.show(Labels.getLabel("delete.success"),
				Labels.getLabel("success.title"), Messagebox.OK,
				Messagebox.INFORMATION);

		userHierarchy = null;

		refreshTree();
		refreshUsers();
		getBinder(hierarchyWin).loadAll();

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		refreshDepartments();

		DepartmentDAO departmentDAO = new DepartmentDAO();
		List<Department> rootDepartments = departmentDAO.findRootDepartments();
		if (!rootDepartments.isEmpty()) {
			department = rootDepartments.get(0);
		}

		refreshTree();
		refreshUsers();

		page.setAttribute(this.getClass().getSimpleName(), this);
	}

	public void onSelect$departmentCbx(SelectEvent event) {
		refreshTree();
		getBinder(hierarchyWin).loadAll();
	}

	public void onSelect$usersLstbx(SelectEvent event) {
		getBinder(hierarchyWin).loadAll();
	}

	public void onSelect$hierarchyTree(SelectEvent event) {
		if (event.getSelectedItems().isEmpty()) {
			userHierarchy = null;
		} else {
			Treeitem treeItem = (Treeitem) event.getReference();
			userHierarchy = (UserHierarchy) treeItem.getValue();
		}
		getBinder(hierarchyWin).loadAll();
	}

	public void onClick$addBtn() throws InterruptedException {

		UserHierarchy userHierarchy = new UserHierarchy();
		userHierarchy.setUsers(user);
		userHierarchy.setDepartment(department);
		userHierarchy.setUserHierarchy(this.userHierarchy);
		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();
		userHierarchyDAO.makePersistent(userHierarchy);

		refreshTree();
		refreshUsers();

		getBinder(hierarchyWin).loadAll();

		Messagebox.show(Labels.getLabel("save.OK"),
				Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);

	}

	public void onClick$removeBtn() throws InterruptedException {

		UserHierarchyDAO userHierarchyDAO = new UserHierarchyDAO();
		if (!userHierarchyDAO.isDeletable(userHierarchy.getId())) {
			Messagebox.show(Labels.getLabel("delete.notDeletable"),
					Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		try {
			boolean delete = false;
			Messagebox.show(Labels.getLabel("delete.confirm"),
					Labels.getLabel("delete.title"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								delete();
							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}

	}

	public boolean isAddDisabled() {
		if (user != null && department !=null) {
			return false;
		}
		return true;
	}

	public boolean isRemoveDisabled() {
		if (userHierarchy != null) {
			return false;
		}
		return true;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UserHierarchy getUserHierarchy() {
		return userHierarchy;
	}

	public void setUserHierarchy(UserHierarchy userHierarchy) {
		this.userHierarchy = userHierarchy;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
