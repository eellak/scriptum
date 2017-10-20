/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.RoleDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.service.UserService;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserVM extends GenericEntityVM<Users, Integer, UsersDAO> {

	public static final String PAGE = "user.zul";

	private static Log log = LogFactory.getLog(UserVM.class);

	@WireVariable
	private UsersDAO usersDAO;
	
	@WireVariable
	private RoleDAO roleDAO;

	@WireVariable
	private PermissionDAO permissionDAO;

	@WireVariable
	private UserAssignmentDAO userAssignmentDAO;
	
	@WireVariable
	private UserService userService;

	private List<Role> roles = null;

	private Role role = null;

	private Role userRole = null;

	private Integer pageSize = PAGE_SIZE_LARGE;

	private List<Permission> permissions;

	private Set<Permission> selectedPermissions;

	private List<UserAssignment> userAssignments;

	private Set<UserAssignment> selectedUserAssignments;

	private Set<UserAssignment> assignmentsToBeDeleted;
	
	private List<UserAssignment> protocolBookAssignments;

	@Override
	protected void save() throws Exception {
		
		if(entity.getId()==null) { //new user, create
			userService.create(entity,getUserInSession().getId());
		}else {
			userService.update(entity, assignmentsToBeDeleted,getUserInSession().getId());
		}
	}
	
	@Override
	protected void delete() throws Exception {
		userService.delete(entity);
	}
	
	@Init(superclass = true)
	public void initVM() {

		if (entity.getId() == null) {
			entity.setRoles(new HashSet<Role>());
		}
		roles = roleDAO.findAll();

		permissions = permissionDAO.findAll();
		selectedPermissions = new HashSet<Permission>();
		if (entity.getId() == null) {
			userAssignments = new ArrayList<UserAssignment>();
		} else {
			userAssignments = userAssignmentDAO.findByUserPermissions(entity);
			for (UserAssignment userAssignment : userAssignments) {
				permissions.remove(userAssignment.getPermission());
			}
			protocolBookAssignments = userAssignmentDAO.findByUserAndEntityType(entity, ProtocolBook.class);
		}
		selectedUserAssignments = new HashSet<UserAssignment>();
		assignmentsToBeDeleted = new HashSet<UserAssignment>();
	}

	@Override
	@Command
	@NotifyChange("*")
	public void addNewEntity() throws Exception {
		super.addNewEntity();
		role = null;
		entity.setRoles(new HashSet<Role>());
		permissions = permissionDAO.findAll();
		selectedPermissions = new HashSet<Permission>();
		userAssignments = new ArrayList<UserAssignment>();
		selectedUserAssignments = new HashSet<UserAssignment>();
		assignmentsToBeDeleted = new HashSet<UserAssignment>();
	}

	@Command
	@NotifyChange({ "entity", "userRole", "role" })
	public void addRole() {
		entity.getRoles().add(role);
		userRole = role;
		role = null;
	}

	@Command
	@NotifyChange({ "entity", "userRole" })
	public void removeRole() {
		entity.getRoles().remove(userRole);
		userRole = null;
	}

	@Command
	@NotifyChange({ "selectedPermissions", "permissions", "userAssignments" })
	public void addPermissions() {
		if (selectedPermissions.isEmpty()) {
			Messagebox.show(Labels.getLabel("userPage.noAssignmentsSelected"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		for (Permission permission : selectedPermissions) {
			UserAssignment userAssignment = new UserAssignment();
			userAssignment.setUser(entity);
			userAssignment.setPermission(permission);
			if (!userAssignments.contains(userAssignment)) {
				userAssignments.add(userAssignment);
			}
		}
		permissions.removeAll(selectedPermissions);
		selectedPermissions = null;
	}

	@Command
	@NotifyChange({ "selectedPermissions", "permissions", "userAssignments", "selectedUserAssignments" })
	public void removePermissions() {
		for (UserAssignment userAssignment : selectedUserAssignments) {
			if (userAssignment.getId() != null) {
				assignmentsToBeDeleted.add(userAssignment);
			}
			userAssignments.remove(userAssignment);
			if (!permissions.contains(userAssignment.getPermission())) {
				permissions.add(userAssignment.getPermission());
			}
		}
		selectedUserAssignments = null;
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {
		validateFields(entityWin);

		if(entity.getId()==null) {
			//new user, check if username exists
			Long countByUsername = usersDAO.countByUsername(entity.getUsername());
			if(countByUsername>0) {
				Messagebox.show(Labels.getLabel("userPage.usernameAlreadyExists"), Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}else {
			//existing user, check if username exists and belongs to different user 
			List<Users> existingUsers = usersDAO.findByUsername(entity.getUsername(), null, null);
			if(existingUsers.size()>0) {
				for (Users existingUser : existingUsers) {
					usersDAO.evict(existingUser);
					if(!existingUser.getId().equals(entity.getId())) {
						Messagebox.show(Labels.getLabel("userPage.usernameAlreadyExists"), Labels.getLabel("error.title"), Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
			}
		}
		
		if (entity.getRoles().isEmpty()) {
			Messagebox.show(Labels.getLabel("userPage.noRolesAdded"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		entity.setUserAssignments(new LinkedHashSet<UserAssignment>(userAssignments));
		
		super.saveEntity(entityWin);
	}

	public boolean isRoleEmpty() {
		return this.role == null;
	}

	public boolean isUserRoleEmpty() {
		return userRole == null;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Set<Permission> getSelectedPermissions() {
		return selectedPermissions;
	}

	public void setSelectedPermissions(Set<Permission> selectedPermissions) {
		this.selectedPermissions = selectedPermissions;
	}

	public List<UserAssignment> getUserAssignments() {
		return userAssignments;
	}

	public void setUserAssignments(List<UserAssignment> userAssignments) {
		this.userAssignments = userAssignments;
	}

	public Set<UserAssignment> getSelectedUserAssignments() {
		return selectedUserAssignments;
	}

	public void setSelectedUserAssignments(Set<UserAssignment> selectedUserAssignments) {
		this.selectedUserAssignments = selectedUserAssignments;
	}

	public List<UserAssignment> getProtocolBookAssignments() {
		return protocolBookAssignments;
	}

	public void setProtocolBookAssignments(List<UserAssignment> protocolBookAssignments) {
		this.protocolBookAssignments = protocolBookAssignments;
	}

}
