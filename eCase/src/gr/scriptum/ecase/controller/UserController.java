package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericEntityController;
import gr.scriptum.dao.RoleDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.Users;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;

/**
 * @author aanagnostopoulos
 *
 */
public class UserController extends GenericEntityController<Users, UsersDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047093320331081017L;

	public static final String PAGE = "user.zul";
	
	private Checkbox isDisabledChkbx = null;
	
	private List<Role> roles = null;
	
	private Role role = null;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		RoleDAO roleDAO = new RoleDAO();
		roles = roleDAO.findCaseRoles();
		
		if(entity!=null && entity.getIsDisabled()!=null) {
			isDisabledChkbx.setChecked(entity.getIsDisabled());
			
			role = roleDAO.findCaseRole(entity);
			
		}
		
	}

	@Override
	public void onClick$newBtn() throws Exception {
		isDisabledChkbx.setChecked(false);
		role = null;
		super.onClick$newBtn();
	}
	
	@Override
	public void onClick$saveBtn() throws Exception {
		
		validateFields(win);
		
		entity.setIsDisabled(isDisabledChkbx.isChecked());
		List<Role> rolesToBeRemoved = new ArrayList<Role>();
		for(Role role: entity.getRoles()) {
			if(role.getIsProtocol().equals(RoleDAO.ROLE_IS_CASE)) {
				rolesToBeRemoved.add(role);
			}
		}
		entity.getRoles().removeAll(rolesToBeRemoved);
		RoleDAO roleDAO = new RoleDAO();
		entity.getRoles().add(roleDAO.findById(role.getId(), false));
		super.onClick$saveBtn();
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
	
}
