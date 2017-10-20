/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Permission;
import gr.scriptum.exception.DataCorruptionException;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class PermissionDAO extends GenericDAO<Permission, Long> {

	@Transactional(readOnly=true)
	public Permission findByName(String name) {
		Permission permission = new Permission();
		permission.setName(name);
		List<Permission> permissions = findByExample(permission);
		if (permissions.isEmpty()) {
			return null;
		}
		if (permissions.size() != 1) {
			throw new DataCorruptionException("Invalid number of permissions found:" + permissions.size() + " for name:" + name);
		}
		return permissions.get(0);
	}

}
