/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;

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

	public List<UserHierarchy> findByUser(Users manager) {
		Query query = getSession().createQuery(
				"from UserHierarchy uh where uh.users = :user order by uh.id");
		query.setParameter("user", manager);
		return query.list();
	}

	public List<UserHierarchy> findByUser(Users manager, Project project) {
		Query query = getSession()
				.createQuery(
						"select uh from UserHierarchy uh, ProjectUser pj where uh.users = pj.id.users and uh.users = :user and pj.id.project = :project order by uh.id");
		query.setParameter("user", manager);
		query.setParameter("project", project);
		return query.list();
	}
}
