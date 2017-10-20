/**
 * 
 */
package gr.scriptum.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class UserHierarchyDAO extends GenericDAO<UserHierarchy, Integer> {

	@Transactional(readOnly=true)
	public List<UserHierarchy> findRootUserHierarchies(Department department) {

		Query query = getSession()
				.createQuery(
						"from UserHierarchy uh where uh.userHierarchy is null and uh.department = :department");
		query.setParameter("department", department);
		return query.list();
	}

	@Transactional(readOnly=true)
	public boolean isDeletable(Integer id) {
		UserHierarchy userHierarchy = findById(id, false);
		if (!userHierarchy.getUserHierarchies().isEmpty()) {
			return false;
		}

		// TODO: check for pending tasks

		return true;
	}

	@Transactional(readOnly=true)
	public List<UserHierarchy> findByUser(Users user) {
		Query query = getSession().createQuery(
				"from UserHierarchy uh where uh.user = :user order by uh.id");
		query.setParameter("user", user);
		return query.list();
	}

	@Transactional(readOnly=true)
	public List<UserHierarchy> findByDepartment(Department department) {
		Query query = getSession()
				.createQuery(
						"from UserHierarchy uh where uh.department = :department order by uh.id");
		query.setParameter("department", department);
		return query.list();
	}

	@Transactional(readOnly=true)
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
	
	@Transactional(readOnly=true)
	public List<UserHierarchy> findByUser(Users manager, Project project) {
		Query query = getSession()
				.createQuery(
						"select uh from UserHierarchy uh, ProjectUser pj where uh.users = pj.id.users and uh.users = :user and pj.id.project = :project order by uh.id");
		query.setParameter("user", manager);
		query.setParameter("project", project);
		return query.list();
	}
	
	@Transactional(readOnly=true)
	public Long countEmployeesByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(uh) from UserHierarchy uh inner join uh.contact c"
						+ " where c.name like :term or c.surname like :term or c.email like :term or c.code like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return (Long) query.uniqueResult();
	}

	@Transactional(readOnly=true)
	public List<UserHierarchy> findEmployeesByTerm(String term,Integer firstResult,
			Integer maxResults) {
		Query query = getSession()
				.createQuery(
						"select uh from UserHierarchy uh inner join uh.contact c"
						+ " where c.name like :term or c.surname like :term or c.email like :term or c.code like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}
	
	@Transactional(readOnly = true)
	public Long countEmployeesByTermAndDepartment(String term, Department department) {
		Query query = getSession().createQuery("select count(uh) from UserHierarchy uh inner join uh.contact c"
				+ " where uh.department=:department and (c.name like :term or c.surname like :term or c.email like :term or c.code like :term)");
		query.setParameter("department", department);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return (Long) query.uniqueResult();
	}

	@Transactional(readOnly = true)
	public List<UserHierarchy> findEmployeesByTermAndDepartment(String term, Department department, Integer firstResult,
			Integer maxResults) {
		Query query = getSession().createQuery("select uh from UserHierarchy uh inner join uh.contact c"
				+ " where uh.department=:department and (c.name like :term or c.surname like :term or c.email like :term or c.code like :term)");
		query.setParameter("department", department);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

	@Transactional(readOnly=true)
	public List<UserHierarchy> findEmployeesByCode(String code,Integer firstResult,
			Integer maxResults) {
		Query query = getSession()
				.createQuery(
						"select uh from UserHierarchy uh inner join uh.contact c"
						+ " where c.code = :code");
		query.setParameter("code", code);
		
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

	@Transactional(readOnly=true)
	public List<UserHierarchy> findEmployeesByCodeAndDepartment(String code, Department department, Integer firstResult,
			Integer maxResults) {
		Query query = getSession()
				.createQuery(
						"select uh from UserHierarchy uh inner join uh.contact c"
						+" where uh.department=:department"
						+ " and c.code = :code");
		query.setParameter("department", department);
		query.setParameter("code", code);
		
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

}
