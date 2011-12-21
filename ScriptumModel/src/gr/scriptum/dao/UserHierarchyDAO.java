/**
 * 
 */
package gr.scriptum.dao;

import java.util.ArrayList;
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

	public List<UserHierarchy> findByUser(Users user) {
		Query query = getSession().createQuery(
				"from UserHierarchy uh where uh.users = :user order by uh.id");
		query.setParameter("user", user);
		return query.list();
	}

	public List<UserHierarchy> findByDepartment(Department department) {
		Query query = getSession()
				.createQuery(
						"from UserHierarchy uh where uh.department = :department order by uh.id");
		query.setParameter("department", department);
		return query.list();
	}

	public List<UserHierarchy> findSubordinates(Users user) {
		List<UserHierarchy> subordinates = new ArrayList<UserHierarchy>();
		// add the user him/herself
		List<UserHierarchy> userHierarchies = findByUser(user);
		subordinates.addAll(userHierarchies);

		// add subordinates within the same department, for each department that
		// the user belongs to
		for (UserHierarchy userHierarchy : userHierarchies) {
			UserHierarchy root = userHierarchy;
			while (!root.getUserHierarchies().isEmpty()) {
				for (UserHierarchy child : root.getUserHierarchies()) {
					root = child;
					if (!subordinates.contains(child)) {
						subordinates.add(child);
					}
				}
			}
		}

		// add users within 'subordinate' departments
		for (UserHierarchy userHierarchy : userHierarchies) {
//			Department department = userHierarchy.getDepartment();
//			while (department!=null) {
//				for (Department childDepartment : department.getDepartments()) {
//					subordinates.addAll(findByDepartment(childDepartment));
//					department = childDepartment;
//				}
//				department = null;
//			}
			getUserHierarchies(userHierarchy.getDepartment(), subordinates);
		}

		return subordinates;
	}

	private List<UserHierarchy> getUserHierarchies(Department department, List<UserHierarchy> subordinates){
		Department dept = department;
		for(Department child: dept.getDepartments()) {
			List<UserHierarchy> findByDepartment = findByDepartment(child);
			for(UserHierarchy userHierarchy: findByDepartment) {
				Integer id = userHierarchy.getId();
				boolean found = false;
				for(UserHierarchy subordinate: subordinates) {
					if(subordinate.getId().intValue()==id.intValue()) {
						found = true;
						break;
					}
				}
				if(!found) {
					subordinates.add(userHierarchy);
				}
//				if(!subordinates.contains(userHierarchy)) {
//					subordinates.add(userHierarchy);
//				}
			}
			if(!child.getDepartments().isEmpty()) {
				getUserHierarchies(child, subordinates);
			}
		}
		return subordinates;
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
