/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Role;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class PermissionService extends BaseService {

	@Autowired
	private PermissionDAO permissionDAO;

	@Autowired
	private UsersDAO usersDAO;

	@Transactional(readOnly = true)
	public Set<Permission> getProtocolBookRelatedPermissions() {
		// TODO: combine in single query
		Set<Permission> permissions = new HashSet<Permission>();
		// TODO: parameterize this
		permissions.add(permissionDAO.findByName("WRITE_INCOMING"));
		permissions.add(permissionDAO.findByName("WRITE_OUTGOING"));
		permissions.add(permissionDAO.findByName("SEARCH_BOOK"));
		return permissions;
	}

	@Transactional(readOnly = true)
	public Set<Permission> getUserPermissions(Users user) {
		Set<Permission> permissions = new HashSet<Permission>();
		user = usersDAO.merge(user);
		for (Role role : user.getRoles()) {
			// add all permissions belonging to each role
			permissions.addAll(role.getPermissions());
		}
		// add permissions explicitly assigned to user
		Set<UserAssignment> userAssignments = user.getUserAssignments();
		for (UserAssignment userAssignment : userAssignments) {
			if (userAssignment.getResource() == null) {
				permissions.add(userAssignment.getPermission());
			}
		}
		return permissions;
	}
}
