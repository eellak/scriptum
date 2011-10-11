/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.UserHierarchy;

/**
 * @author aanagnostopoulos
 * 
 */
public class UserHierarchyDAO extends GenericDAO<UserHierarchy, Integer> {

	public List<UserHierarchy> findRootUserHierarchies(Department department) {

		Query query = getSession()
				.createQuery(
						"from UserHierarchy uh where uh.userHierarchy is null and uh.department = :department");
		query.setParameter("department", department);
		return query.list();
	}

	public boolean isDeletable(Integer id) {
		UserHierarchy userHierarchy = findById(id, false);
		if (!userHierarchy.getUserHierarchies().isEmpty()) {
			return false;
		}

		// TODO: check for pending tasks

		return true;
	}

}
