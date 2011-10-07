/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import gr.scriptum.domain.Department;

/**
 * @author aanagnostopoulos
 * 
 */
public class DepartmentDAO extends GenericDAO<Department, Integer> {

	public List<Department> findRootDepartments() {
		return getSession().createQuery(
				"from Department d where d.department is null order by d.id")
				.list();
	}

	public boolean isDeletable(Integer id) {
		Department department = findById(id, false);
		if (!department.getDepartments().isEmpty()) {
			return false;
		}
		if (!department.getUserHierarchies().isEmpty()) {
			return false;
		}
		return true;
	}

}
