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

}
