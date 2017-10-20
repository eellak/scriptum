/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.RoleDAO;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Role;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RoleVM extends GenericEntityVM<Role, Integer, RoleDAO> {

	private static Log log = LogFactory.getLog(RoleVM.class);
	
	public static final String PAGE = "role.zul";

	@WireVariable
	private PermissionDAO permissionDAO;

	private Integer pageSize = PAGE_SIZE_LARGE;

	private List<Permission> permissions;

	private Set<Permission> selectedPermissions;

	private Set<Permission> userPermissions;
	
	@Init(superclass = true)
	public void initVM() {

		if (entity.getId() == null) {
			entity.setPermissions(new HashSet<Permission>());
		}
		permissions = permissionDAO.findAll();
		permissions.removeAll(entity.getPermissions());
	}

	@Override
	@Command
	@NotifyChange({ "entity", "selectedPermissions", "permissions" })
	public void addNewEntity() throws Exception {
		super.addNewEntity();
		selectedPermissions = null;
		permissions = permissionDAO.findAll();
		entity.setPermissions(new HashSet<Permission>());
	}

	@Command
	@NotifyChange({ "entity", "selectedPermissions", "userPermissions", "permissions" })
	public void addPermissions() {
		entity.getPermissions().addAll(selectedPermissions);
		permissions.removeAll(selectedPermissions);
		userPermissions = null;
		selectedPermissions= null;
	}

	@Command
	@NotifyChange({ "entity", "userPermissions", "permissions" })
	public void removePermissions() {
		entity.getPermissions().removeAll(userPermissions);
		permissions.addAll(userPermissions);
		userPermissions = null;
	}

	@Override
	@Command
	@NotifyChange("entity")
	public void saveEntity(@ContextParam(ContextType.VIEW) Window entityWin) throws Exception {
		validateFields(entityWin);

		// TODO: activate role-permission validation, once specs are defined
		// if (entity.getPermissions().isEmpty()) {
		// Messagebox.show(Labels.getLabel("rolePage.noPermissionsAdded"),
		// Labels.getLabel("error.title"), Messagebox.OK,
		// Messagebox.ERROR);
		// return;
		// }

		// refresh user roles from database
		for (Permission permission : entity.getPermissions()) {
			permissionDAO.refresh(permission);
		}
		super.saveEntity(entityWin);
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

	public Set<Permission> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(Set<Permission> userPermissions) {
		this.userPermissions = userPermissions;
	}

}
