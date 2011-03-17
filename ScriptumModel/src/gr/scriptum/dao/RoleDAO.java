/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;
import java.util.Set;

import gr.scriptum.domain.Role;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
public class RoleDAO extends GenericDAO<Role, Integer> {

	private static final int ROLE_IS_PROTOCOL = 1;

	public List<Role> findProtocolRoles() {
		Role example = new Role();
		example.setIsProtocol(ROLE_IS_PROTOCOL);
		return findByExample(example, new String[] {});
	}

	public Role findProtocolRole(Users user) {
		Set<Role> userRoles = user.getRoles();
		for (Role userRole : userRoles) {
			if (userRole.getIsProtocol() == ROLE_IS_PROTOCOL) {
				// only one role per system should be assigned to a user, always
				// return the first occurrence found
				return userRole;
			}
		}
		return null;
	}
}
