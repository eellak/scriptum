/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import gr.scriptum.domain.Role;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
public class RoleDAO extends GenericDAO<Role, Integer> {

	public static final int ROLE_IS_PROTOCOL = 1;

	public static final int ROLE_IS_CASE = 2;

	public static final int ROLE_IS_COMMON = 3;

	public List<Role> findProtocolRoles() {
		// Role example = new Role();
		// example.setIsProtocol(ROLE_IS_PROTOCOL);
		// return findByExample(example, new String[] {});
		Query query = getSession().createQuery(
				"from Role r where r.isProtocol in (:roles) order by r.id");
		query.setParameterList("roles", new Integer[] { ROLE_IS_PROTOCOL,
				ROLE_IS_COMMON });
		return query.list();

	}

	public Role findProtocolRole(Users user) {
		Set<Role> userRoles = user.getRoles();
		for (Role userRole : userRoles) {
			if (userRole.getIsProtocol().intValue() == ROLE_IS_PROTOCOL
					|| userRole.getIsProtocol().intValue() == ROLE_IS_COMMON) {
				// only one role per system should be assigned to a user, always
				// return the first occurrence found
				return userRole;
			}
		}
		return null;
	}
	
	public List<Role> findCaseRoles() {
		// Role example = new Role();
		// example.setIsProtocol(ROLE_IS_PROTOCOL);
		// return findByExample(example, new String[] {});
		Query query = getSession().createQuery(
				"from Role r where r.isProtocol in :roles order by r.id");
		query.setParameterList("roles", new Integer[] { ROLE_IS_CASE,
				ROLE_IS_COMMON });
		return query.list();

	}

	public Role findCaseRole(Users user) {
		Set<Role> userRoles = user.getRoles();
		for (Role userRole : userRoles) {
			if (userRole.getIsProtocol().intValue() == ROLE_IS_CASE
					|| userRole.getIsProtocol().intValue() == ROLE_IS_COMMON) {
				// only one role per system should be assigned to a user, always
				// return the first occurrence found
				return userRole;
			}
		}
		return null;
	}

}
