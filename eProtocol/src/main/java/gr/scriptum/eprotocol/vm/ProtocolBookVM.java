/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.SortType;
import org.hibernate.criterion.Order;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.dao.ResourceDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.domain.Resource;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;
import gr.scriptum.domain.Users_;
import gr.scriptum.eprotocol.service.PermissionService;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProtocolBookVM extends GenericEntityVM<ProtocolBook, Integer, ProtocolBookDAO> {

	private static Log log = LogFactory.getLog(ProtocolBookVM.class);

	public static final String PAGE = "book.zul";

	@WireVariable
	private ProtocolBookService protocolBookService;

	@WireVariable
	private PermissionService permissionService;

	@WireVariable
	private UsersDAO usersDAO;

	@WireVariable
	private UserAssignmentDAO userAssignmentDAO;

	@WireVariable
	private ResourceDAO resourceDAO;

	@WireVariable
	private ProtocolBookDAO protocolBookDAO;
	
	private ProtocolBookType[] types = ProtocolBookType.values();

	private Resource resource;

	private Set<Permission> permissions;

	private Set<Permission> selectedPermissions;

	private List<Users> users;

	private Set<Users> selectedUsers;

	private List<UserAssignment> assignments;

	private Set<UserAssignment> assignmentsToBeDeleted;

	private Set<UserAssignment> selectedAssignments;

	private Integer pageSize = PAGE_SIZE_LARGE;

	@Override
	protected void save() throws Exception {

		Serializable id = getEntityId(entity);
		
		if (id == null) { // new entity, create
			entity = protocolBookService.create(entity, getUserInSession(), getIp(), assignments);
		} else {
			protocolBookService.update(entity, getUserInSession(), getIp(), assignments, assignmentsToBeDeleted);
		}

	}

	@Override
	protected void delete() throws Exception {

		protocolBookService.delete(entity, getUserInSession(), getIp());
	}

	@Init(superclass = true)
	public void initVM() {
		if (entity.getId() != null) {
			permissions = permissionService.getProtocolBookRelatedPermissions();
			// TODO: add database pagination
			resource = resourceDAO.findEntityResource(ProtocolBook.class, entity.getId());
			Users example = new Users();
			List<Order> sortBy = new ArrayList<Order>();
			sortBy.add(Order.asc(Users_.surname.getName()));
			sortBy.add(Order.asc(Users_.name.getName()));
			users = usersDAO.findByExample(example, null, sortBy, null, null);
			assignments = userAssignmentDAO.findByEntity(ProtocolBook.class, entity.getId());
		} else {
			// set newly created books as always active
			entity.setIsActive(ProtocolBook.IS_ACTIVE);
		}
		selectedPermissions = new HashSet<Permission>();
		selectedUsers = new HashSet<Users>();
		assignmentsToBeDeleted = new HashSet<UserAssignment>();
		selectedAssignments = new HashSet<UserAssignment>();
	}

	@Command
	public void selectAllUsers(@ContextParam(ContextType.TRIGGER_EVENT) CheckEvent e) {
		log.info(e);
		log.info(selectedUsers);
	}

	@Command
	@NotifyChange({ "selectedPermissions", "selectedUsers", "assignments" })
	public void addAssignments() {
		if (selectedUsers.isEmpty() || selectedPermissions.isEmpty()) {
			Messagebox.show(Labels.getLabel("bookPage.noAssignmentSelected"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		for (Permission selectedPermission : selectedPermissions) {
			for (Users user : selectedUsers) {
				UserAssignment userAssignment = new UserAssignment();
				userAssignment.setResource(resource);
				userAssignment.setPermission(selectedPermission);
				userAssignment.setUser(user);
				if (!assignments.contains(userAssignment)) {
					assignments.add(userAssignment);
				}
			}
		}

		selectedPermissions = new HashSet<Permission>();
		selectedUsers = new HashSet<Users>();
	}

	@Command
	@NotifyChange({ "assignments", "selectedAssignments" })
	public void removeAssignments() {
		for (UserAssignment userAssignment : selectedAssignments) {
			if (userAssignment.getId() != null) {
				assignmentsToBeDeleted.add(userAssignment);
			}
			assignments.remove(userAssignment);
		}
		selectedAssignments = new HashSet<UserAssignment>();
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		validateFields(entityWin);

		ProtocolBook example = new ProtocolBook();
		example.setProtocolSeries(entity.getProtocolSeries());

		if(entity.getId()==null) {
			// new entity, check if code exists
			Integer countByExample = protocolBookDAO.countByExample(example);
			if (countByExample > 0) {
				Messagebox.show(Labels.getLabel("bookPage.titleAlreadyExists"),
						Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}else {
			// existing entity, check if code exists and belongs to different
			// entity
			List<ProtocolBook> existingEntities = protocolBookDAO.findByExample(example, null, null);
			if (existingEntities.size() > 0) {
				for (ProtocolBook existingEntity : existingEntities) {
					protocolBookDAO.evict(existingEntity);
					if (!existingEntity.getId().equals(entity.getId())) {
						Messagebox.show(Labels.getLabel("bookPage.titleAlreadyExists"),
								Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
						return;
					}
				}
			}
		}

		try {
			save();

			Messagebox.show(Labels.getLabel("save.OK"), Labels.getLabel("save.title"), Messagebox.OK,
					Messagebox.INFORMATION);

		} catch (OpenKMException e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("bookPage.errorOpenKM"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
		}

		if (callback != null) {
			// notify caller (if any)
			// called by MVVM view model
			Map<String, Object> args = new HashMap<>();
			args.put("entity", entity);
			BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
			entityWin.detach();
			return;
		}
	}

	@Command
	@NotifyChange("entity")
	public void deleteEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {

		Integer id = entity.getId();

		ProtocolBookDAO dao = initDAO();
		if (!dao.isDeletable(id)) {

			Messagebox.show(Labels.getLabel("delete.notDeletable"), Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		log.info("Deleting");
		Messagebox.show(Labels.getLabel("delete.confirm"), Labels.getLabel("delete.title"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EntityDeleteListener(entityWin));

	}

	private class EntityDeleteListener implements EventListener<Event> {

		private Window entityWin = null;

		public EntityDeleteListener(Window entityWin) {
			this.entityWin = entityWin;
		}

		@Override
		public void onEvent(Event event) throws Exception {
			if (((Integer) event.getData()).intValue() == Messagebox.YES) {

				try {
					delete();

					Messagebox.show(Labels.getLabel("delete.success"), Labels.getLabel("success.title"), Messagebox.OK,
							Messagebox.INFORMATION);

				} catch (OpenKMException e) {
					log.error(e);
					Messagebox.show(Labels.getLabel("bookPage.errorOpenKM"), Labels.getLabel("error.title"),
							Messagebox.OK, Messagebox.ERROR);
				}

				if (callback != null) {
					// called by MVVM view model
					Map<String, Object> args = new HashMap<>();
					args.put("entity", entity);
					BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
					entityWin.detach();
					return;
				}
			}
		}
	}

	public ProtocolBookType[] getTypes() {
		return types;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> roles) {
		this.permissions = roles;
	}

	public Set<Permission> getSelectedPermissions() {
		return selectedPermissions;
	}

	public void setSelectedPermissions(Set<Permission> selectedRoles) {
		this.selectedPermissions = selectedRoles;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Set<Users> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(Set<Users> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<UserAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<UserAssignment> assignments) {
		this.assignments = assignments;
	}

	public Set<UserAssignment> getSelectedAssignments() {
		return selectedAssignments;
	}

	public void setSelectedAssignments(Set<UserAssignment> selectedAssignments) {
		this.selectedAssignments = selectedAssignments;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
