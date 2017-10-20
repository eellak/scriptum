/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.ExcludeDefaultListeners;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.TreeSelectableModel;

import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.DepartmentTypeDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.service.DepartmentService;
import gr.scriptum.eprotocol.util.EntitySelection;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StructureVM extends BaseVM {

	private static Log log = LogFactory.getLog(StructureVM.class);

	public static final String PAGE = "structure.zul";

	@WireVariable
	private DepartmentDAO departmentDAO;

	@WireVariable
	private DepartmentTypeDAO departmentTypeDAO;

	@WireVariable
	private UsersDAO usersDAO;

	@WireVariable
	private ContactDAO contactDAO;

	@WireVariable
	private UserHierarchyDAO userHierarchyDAO;

	@WireVariable
	private DepartmentService departmentService;

	/* data binding */
	private TreeModel<?> treeModel;

	private TreeNode selectedNode;

	private Department department;

	private EntitySelection<Department> parentDepartmentSelection;

	private List<DepartmentType> departmentTypes;

	private List<Users> users;
	private Integer usersTotalSize;
	private Integer usersPageSize = PAGE_SIZE_LARGE;
	private Integer usersActivePage = 0;
	private String usersTerm;

	private Set<Users> selectedUsers;

	private List<Contact> contacts;

	private Set<Contact> selectedContacts;

	private Set<UserHierarchy> selectedUserHierarchies;

	private Set<UserHierarchy> userHierarchiesToBeDeleted;

	private Set<UserHierarchy> selectedContactHierarchies;

	private Integer pageSize = PAGE_SIZE_LARGE;

	private Boolean departmentSelectionEnabled;

	/* non accessible fields */
	private List<Department> nonApplicableDepartments;

	private void initNewDepartment(Department parentDepartment) {
		department = new Department();
		department.setCanAssignAnywhere(Department.CAN_ASSIGN_ANYWHERE_FALSE);
		department.setUserHierarchies(new HashSet<UserHierarchy>());
		department.setDepartmentType(departmentTypes.get(0));
		department.setDepartment(parentDepartment);
		parentDepartmentSelection = new EntitySelection<Department>(PAGE_SIZE_DEFAULT);
		parentDepartmentSelection.setSelectedEntity(parentDepartment);
		nonApplicableDepartments = null;

		selectedUsers = new HashSet<Users>();
		selectedUserHierarchies = new HashSet<UserHierarchy>();
		selectedContacts = new HashSet<Contact>();
		userHierarchiesToBeDeleted = new HashSet<UserHierarchy>();
		selectedContactHierarchies = new HashSet<UserHierarchy>();
		selectedContactHierarchies = new HashSet<UserHierarchy>();
	}

	private void refreshUnassignedUsers() {
		usersTerm = "";
		usersActivePage = 0;
		Object[] results = searchUsers(usersTerm, 0, usersPageSize);
		usersTotalSize = (Integer) results[0];
		users = (List<Users>) results[1];
	}

	private List<Department> getNonApplicableParentDepartments(Department selectedDepartment) {
		List<Department> nonApplicableDepartments = new ArrayList<Department>();

		Department department = selectedDepartment;
		while (department != null) {
			nonApplicableDepartments.add(department);
			Set<Department> children = department.getDepartments();
			if (children.isEmpty()) {
				department = null;
				continue;
			}
			for (Department child : children) {
				department = child;
				nonApplicableDepartments.addAll(getNonApplicableParentDepartments(department));
			}
		}
		return nonApplicableDepartments;
	}

	private DefaultTreeNode<Department> getTreeBranch(Department root) {

		DefaultTreeNode<Department> rootNode = null;
		if (root.getDepartments().isEmpty()) {
			rootNode = new DefaultTreeNode<Department>(root); // no children
																// allowed
		} else {
			rootNode = new DefaultTreeNode<Department>(root,
					new ArrayList<DefaultTreeNode<Department>>(root.getDepartments().size()));
		}
		List<Department> children = new ArrayList<>(root.getDepartments());
		children.sort((d1, d2) -> d1.getCode().compareTo(d2.getCode()));
		for (Department child : children) {
			rootNode.add(getTreeBranch(child));
		}
		return rootNode;
	}

	private void doCollapseExpandAll(DefaultTreeNode<Department> node, boolean open) {
		((DefaultTreeModel<Department>) treeModel).addOpenObject(node);

		List<org.zkoss.zul.TreeNode<Department>> children = node.getChildren();
		if (children != null) {
			for (org.zkoss.zul.TreeNode<Department> child : children) {
				doCollapseExpandAll((DefaultTreeNode<Department>) child, open);
			}
		}
	}

	private void refreshTree() {

		DefaultTreeNode<Department> root = null;

		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);
		List<Department> rootDepartments = departmentDAO.findRootDepartments();
		if (rootDepartments.isEmpty()) {
			root = new DefaultTreeNode<Department>(null);
		} else {
			root = new DefaultTreeNode<Department>(null,
					new ArrayList<DefaultTreeNode<Department>>(rootDepartments.size()));
		}
		for (Department rootDepartment : rootDepartments) {

			DefaultTreeNode<Department> branchNode = getTreeBranch(rootDepartment);

			root.add(branchNode);
		}

		DefaultTreeModel<DefaultTreeNode> defaultTreeModel = new DefaultTreeModel(root);
		defaultTreeModel.setMultiple(false);
		treeModel = defaultTreeModel;

		// open all nodes
		doCollapseExpandAll(root, true);
	}

	private Object[] searchUsers(String term, Integer startIndex, Integer pageSize) {
		Integer usersTotalSize = null;
		List<Users> users = null;
		HashSet<Users> excludedUsers = department.getUserHierarchies().stream()
				.filter(uh -> uh.getId() == null && uh.getUser() != null).map(UserHierarchy::getUser)
				.collect(Collectors.toCollection(HashSet::new));
		HashSet<Users> includedUsers = userHierarchiesToBeDeleted.stream().map(UserHierarchy::getUser)
				.collect(Collectors.toCollection(HashSet::new));

		if (excludedUsers.isEmpty() && includedUsers.isEmpty()) {
			usersTotalSize = usersDAO.countUnassignedUsersByTerm(term);
			users = usersDAO.findUnassignedUsersByTerm(term, startIndex, pageSize);
		} else {
			usersTotalSize = usersDAO.countUnassignedUsersByTerm(term, excludedUsers, includedUsers);
			users = usersDAO.findUnassignedUsersByTerm(term, excludedUsers, includedUsers, startIndex, pageSize);
		}
		return new Object[] { usersTotalSize, users };
	}

	@Init
	public void init() {
		departmentTypes = departmentTypeDAO.findAll();
		departmentSelectionEnabled = TRUE;

		initNewDepartment(null);
		refreshUnassignedUsers();
		refreshTree();
	}

	@Command
	@NotifyChange("*")
	public void selectDepartment(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {

		departmentSelectionEnabled = FALSE;

		TreeSelectableModel selModel = (TreeSelectableModel) treeModel;
		AbstractTreeModel model = (AbstractTreeModel) selModel;

		int[] selectionPath = selModel.getSelectionPath();
		department = (Department) ((DefaultTreeNode) model.getChild(selectionPath)).getData();
		// refresh from database
		departmentDAO.refresh(department);

		if (department.getUserHierarchies() == null) {
			department.setUserHierarchies(new HashSet<UserHierarchy>());
		}

		parentDepartmentSelection.setSelectedEntity(department.getDepartment());
		nonApplicableDepartments = getNonApplicableParentDepartments(department);

		selectedUsers = new HashSet<Users>();
		selectedUserHierarchies = new HashSet<UserHierarchy>();
		contacts = contactDAO.findUnassignedContacts();
		selectedContacts = new HashSet<Contact>();
		selectedContactHierarchies = new HashSet<UserHierarchy>();
		userHierarchiesToBeDeleted = new HashSet<UserHierarchy>();

		refreshUnassignedUsers();
	}

	@Command
	@NotifyChange("*")
	public void addNewDepartment() {
		initNewDepartment(department);
		refreshUnassignedUsers();
	}

	@Command
	@NotifyChange("*")
	public void saveDepartment(@ContextParam(ContextType.VIEW) Component view) {

		validateFields(view);

		Department example = new Department();
		example.setCode(department.getCode());

		if (department.getId() == null) {

			// new entity, check if code exists
			Integer countByExample = departmentDAO.countByExample(example);
			if (countByExample > 0) {
				Messagebox.show(Labels.getLabel("structurePage.codeAlreadyExists"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
			department = departmentService.create(department);
		} else {

			// existing entity, check if code exists and belongs to different
			// entity
			boolean sameCodeExists = departmentDAO.findByExample(example, null, null).stream().anyMatch(d -> {
				departmentDAO.evict(d);
				return !d.getId().equals(department.getId());
			});
			if (sameCodeExists) {
				Messagebox.show(Labels.getLabel("structurePage.codeAlreadyExists"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
				return;

			}
			departmentService.update(department, userHierarchiesToBeDeleted);
		}
		userHierarchiesToBeDeleted = new HashSet<UserHierarchy>();
		refreshTree();
		refreshUnassignedUsers();

		Messagebox.show(Labels.getLabel("save.OK"), Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	@Command
	public void deleteDepartment() {

		if (!departmentDAO.isDeletable(department.getId())) {
			Messagebox.show(Labels.getLabel("delete.notDeletable"), Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		log.info("Deleting Department");
		Messagebox.show(Labels.getLabel("delete.confirm"), Labels.getLabel("delete.title"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (event) -> {
					if (((Integer) event.getData()).intValue() == Messagebox.YES) {
						departmentService.delete(department);

						Messagebox.show(Labels.getLabel("delete.success"), Labels.getLabel("success.title"),
								Messagebox.OK, Messagebox.INFORMATION);

						initNewDepartment(null);
						refreshTree();
						refreshUnassignedUsers();
						BindUtils.postNotifyChange(null, null, StructureVM.this, "*");
					}
				});
	}

	@Command
	@NotifyChange("parentDepartmentSelection")
	public void clearParentDepartments() {
		parentDepartmentSelection.setActive(FALSE);
	}

	@Command
	@NotifyChange({ "parentDepartmentSelection", "department" })
	public void searchParentDepartments(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		parentDepartmentSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		department.setDepartment(null);
		parentDepartmentSelection.setActivePage(0);
		Object[] results = searchAllDepartments(parentDepartmentSelection.getTerm(), 0,
				parentDepartmentSelection.getPageSize(),
				nonApplicableDepartments == null ? null : nonApplicableDepartments.toArray(new Department[0]));
		parentDepartmentSelection.setTotalSize((Integer) results[0]);
		parentDepartmentSelection.setEntites((List<Department>) results[1]);
		parentDepartmentSelection.setActive(parentDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("parentDepartmentSelection")
	public void listParentDepartments() {
		parentDepartmentSelection.setTerm("");
		parentDepartmentSelection.setActivePage(0);
		Object[] results = searchAllDepartments(parentDepartmentSelection.getTerm(), 0,
				parentDepartmentSelection.getPageSize(),
				nonApplicableDepartments == null ? null : nonApplicableDepartments.toArray(new Department[0]));
		parentDepartmentSelection.setTotalSize((Integer) results[0]);
		parentDepartmentSelection.setEntites((List<Department>) results[1]);
		parentDepartmentSelection.setActive(parentDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("parentDepartmentSelection")
	public void pageParentDepartments() {
		int startIndex = parentDepartmentSelection.getActivePage() * parentDepartmentSelection.getPageSize();
		Object[] results = searchAllDepartments(parentDepartmentSelection.getTerm(), startIndex,
				parentDepartmentSelection.getPageSize(),
				nonApplicableDepartments == null ? null : nonApplicableDepartments.toArray(new Department[0]));
		parentDepartmentSelection.setTotalSize((Integer) results[0]);
		parentDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange({ "parentDepartmentSelection", "department" })
	public void selectParentDepartment() {
		parentDepartmentSelection.setActive(FALSE);
		department.setDepartment(parentDepartmentSelection.getSelectedEntity());
	}

	@Command
	@NotifyChange({ "usersTerm", "usersActivePage", "usersTotalSize", "users", "selectedUsers" })
	public void searchUsers(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		usersTerm = StringUtils.trimToNull(event.getValue());
		usersActivePage = 0;
		selectedUsers = new HashSet<>();
		Object[] results = searchUsers(usersTerm, 0, usersPageSize);
		usersTotalSize = (Integer) results[0];
		users = (List<Users>) results[1];
	}

	@Command
	@NotifyChange({ "usersActivePage", "usersTotalSize", "users", "selectedUsers" })
	public void pageUsers() {
		int startIndex = usersActivePage * usersPageSize;
		Object[] results = searchUsers(usersTerm, startIndex, usersPageSize);
		usersTotalSize = (Integer) results[0];
		users = (List<Users>) results[1];
	}

	@Command
	@NotifyChange({ "selectedUsers", "department", "users", "userHierarchies", "usersActivePage", "usersTotalSize",
			"usersTerm" })
	public void addUsersToDepartment() {

		for (Users selectedUser : selectedUsers) {
			UserHierarchy userHierarchy = new UserHierarchy(department, selectedUser);
			department.getUserHierarchies().add(userHierarchy);
		}
		// users.removeAll(selectedUsers);
		selectedUsers = new HashSet<Users>();
		refreshUnassignedUsers();
	}

	@Command
	@NotifyChange({ "selectedUsers", "department", "users", "selectedUserHierarchies", "userHierarchies",
			"usersActivePage", "usersTotalSize", "usersTerm" })
	public void removeUsersFromDepartment() {
		for (UserHierarchy selectedUserHierarchy : selectedUserHierarchies) {
			department.getUserHierarchies().remove(selectedUserHierarchy);
			if (selectedUserHierarchy.getId() != null) {
				userHierarchiesToBeDeleted.add(selectedUserHierarchy);
			}
			// users.add(selectedUserHierarchy.getUser());
		}
		selectedUserHierarchies = null;
		refreshUnassignedUsers();
	}

	@Command
	@NotifyChange({ "selectedContacts", "department", "contacts", "contactHierarchies" })
	public void addContactsToDepartment() {

		for (Contact selectedContact : selectedContacts) {
			UserHierarchy userHierarchy = new UserHierarchy(department, selectedContact);
			department.getUserHierarchies().add(userHierarchy);
		}
		contacts.removeAll(selectedContacts);
		selectedContacts = null;
	}

	@Command
	@NotifyChange({ "selectedContacts", "department", "contacts", "selectedContactHierarchies", "contactHierarchies" })
	public void removeContactsFromDepartment() {
		for (UserHierarchy selectedContactHierarchy : selectedContactHierarchies) {
			department.getUserHierarchies().remove(selectedContactHierarchy);
			if (selectedContactHierarchy.getId() != null) {
				userHierarchiesToBeDeleted.add(selectedContactHierarchy);
			}
			contacts.add(selectedContactHierarchy.getContact());
		}
		selectedUserHierarchies = null;
	}

	public List<UserHierarchy> getUserHierarchies() {
		if (department == null) {
			return null;
		}
		List<UserHierarchy> userHierarchies = new ArrayList<UserHierarchy>();
		for (UserHierarchy userHierarchy : department.getUserHierarchies()) {
			if (userHierarchy.getUser() != null) {
				userHierarchies.add(userHierarchy);
			}
		}
		return userHierarchies;
	}

	public List<UserHierarchy> getContactHierarchies() {
		if (department == null) {
			return null;
		}
		List<UserHierarchy> contactHierarchies = new ArrayList<UserHierarchy>();
		for (UserHierarchy userHierarchy : department.getUserHierarchies()) {
			if (userHierarchy.getContact() != null) {
				contactHierarchies.add(userHierarchy);
			}
		}
		return contactHierarchies;
	}

	public TreeModel<?> getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(TreeModel<?> treeModel) {
		this.treeModel = treeModel;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EntitySelection<Department> getParentDepartmentSelection() {
		return parentDepartmentSelection;
	}

	public void setParentDepartmentSelection(EntitySelection<Department> departmentSelection) {
		this.parentDepartmentSelection = departmentSelection;
	}

	public List<DepartmentType> getDepartmentTypes() {
		return departmentTypes;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	// public List<UserHierarchy> getUserHierarchies() {
	// return userHierarchies;
	// }
	//
	// public void setUserHierarchies(List<UserHierarchy> userHierarchies) {
	// this.userHierarchies = userHierarchies;
	// }

	public Integer getUsersTotalSize() {
		return usersTotalSize;
	}

	public void setUsersTotalSize(Integer usersTotalSize) {
		this.usersTotalSize = usersTotalSize;
	}

	public Integer getUsersPageSize() {
		return usersPageSize;
	}

	public void setUsersPageSize(Integer usersPageSize) {
		this.usersPageSize = usersPageSize;
	}

	public Integer getUsersActivePage() {
		return usersActivePage;
	}

	public void setUsersActivePage(Integer usersActivePage) {
		this.usersActivePage = usersActivePage;
	}

	public String getUsersTerm() {
		return usersTerm;
	}

	public void setUsersTerm(String usersTerm) {
		this.usersTerm = usersTerm;
	}

	public Set<Users> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(Set<Users> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public Set<UserHierarchy> getSelectedUserHierarchies() {
		return selectedUserHierarchies;
	}

	public void setSelectedUserHierarchies(Set<UserHierarchy> selectecUserHierarchies) {
		this.selectedUserHierarchies = selectecUserHierarchies;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<Contact> getSelectedContacts() {
		return selectedContacts;
	}

	public void setSelectedContacts(Set<Contact> selectedContacts) {
		this.selectedContacts = selectedContacts;
	}

	public Set<UserHierarchy> getSelectedContactHierarchies() {
		return selectedContactHierarchies;
	}

	public void setSelectedContactHierarchies(Set<UserHierarchy> selectedContactHierarchies) {
		this.selectedContactHierarchies = selectedContactHierarchies;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getDepartmentSelectionEnabled() {
		return departmentSelectionEnabled;
	}

	public void setDepartmentSelectionEnabled(Boolean departmentSelectionEnabled) {
		this.departmentSelectionEnabled = departmentSelectionEnabled;
	}

}
