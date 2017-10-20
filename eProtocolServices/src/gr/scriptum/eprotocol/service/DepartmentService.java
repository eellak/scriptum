/**
 * 
 */
package gr.scriptum.eprotocol.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.UserHierarchy;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Service
public class DepartmentService extends BaseService {

	private static Log log = LogFactory.getLog(DepartmentService.class);

	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private UserHierarchyDAO userHierarchyDAO;

	@Transactional
	public Department create(Department department) {

		department = departmentDAO.makePersistent(department);

		for (UserHierarchy userHierarchy : department.getUserHierarchies()) {
			userHierarchy.setDepartment(department);
			userHierarchyDAO.makePersistent(userHierarchy);
		}
		return department;
	}

	@Transactional
	public void update(Department department, Set<UserHierarchy> userHierarchiesToBeDeleted) {

		departmentDAO.update(department);

		// create new user hierarchies
		for (UserHierarchy userHierarchy : department.getUserHierarchies()) {
			if (userHierarchy.getId() == null) {
				userHierarchy = userHierarchyDAO.makePersistent(userHierarchy);
			}
		}

		// delete user hierarchies marked accordingly
		for (UserHierarchy userHierarchy : userHierarchiesToBeDeleted) {
			// TODO: check if 'delete by id' works the same as the
			// refersh/delete style
			userHierarchyDAO.deleteById(userHierarchy.getId());
		}
	}

	@Transactional
	public void delete(Department department) {

		departmentDAO.refresh(department);

		// delete associated user hierarchies
		for (UserHierarchy userHierarchy : department.getUserHierarchies()) {
			userHierarchyDAO.deleteById(userHierarchy.getId());
		}

		// delete department
		departmentDAO.delete(department);
	}
}
