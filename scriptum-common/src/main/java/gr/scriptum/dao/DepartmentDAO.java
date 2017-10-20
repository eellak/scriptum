/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class DepartmentDAO extends GenericDAO<Department, Integer> {

	public static final Integer[] PROTOCOL_DEPARTMENT_TYPES = new Integer[]{1,2,3,4};
	
	public static final Integer[] UNIT_DEPARTMENT_TYPES = new Integer[]{5};
	
	public static final Integer[] ALL_DEPARTMENT_TYPES = new Integer[]{1,2,3,4,5};

	@Transactional(readOnly=true)
	public List<Department> findRootDepartments() {
		return getSession().createQuery(
				"from Department d where d.department is null order by d.id")
				.list();
	}

	@Transactional(readOnly=true)
	public boolean isDeletable(Integer id) {
		return super.isDeletable(id, "userHierarchies");
//		Department department = findById(id, false);
//		if (!department.getDepartments().isEmpty()) {
//			return false;
//		}
//		if (!department.getUserHierarchies().isEmpty()) {
//			return false;
//		}
//		return true;
	}

	@Transactional(readOnly=true)
	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Department d where d.name like :term or d.code like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Department> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Department d where d.name like :term or d.code like :term order by d.code asc");
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
	public Integer countByTermAndType(String term, DepartmentType type) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Department d where d.departmentType=:type and  (d.name like :term or d.code like :term)");
		query.setParameter("type", type);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Department> findByTermAndType(String term, DepartmentType type, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Department d where d.departmentType=:type and (d.name like :term or d.code like :term) order by d.code asc");
		query.setParameter("type", type);
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
	public Integer countByTermAndParentDepartment(String term, Department parentDepartment) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Department d where d.department=:parent and  (d.name like :term or d.code like :term)");
		query.setParameter("parent", parentDepartment);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Department> findByTermAndParentDepartment(String term, Department parentDepartment, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Department d where d.department=:parent and (d.name like :term or d.code like :term) order by d.code asc");
		query.setParameter("parent", parentDepartment);
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
	public Integer countUnitDepartmentsByTermAndParentDepartment(String term, Department parentDepartment) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Department d where d.department=:parent"
						+ " and d.departmentType.id in :types and  (d.name like :term or d.code like :term)");
		query.setParameter("parent", parentDepartment);
		query.setParameterList("types", UNIT_DEPARTMENT_TYPES);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Department> findUnitDepartmentsByTermAndParentDepartment(String term, Department parentDepartment, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Department d where d.department=:parent"
						+ " and d.departmentType.id in :types and (d.name like :term or d.code like :term) order by d.code asc");
		query.setParameter("parent", parentDepartment);
		query.setParameterList("types", UNIT_DEPARTMENT_TYPES);
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
	public Integer countDepartmentsByTerm(String term, Integer[] departmentTypes, Department... excludedDepartments) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Department d where d.departmentType.id in (:types)"
						+ (excludedDepartments!=null && excludedDepartments.length>0 ? " and d not in (:departments)" : "")
						+ " and (d.name like :term or d.code like :term)");
		query.setParameterList("types", departmentTypes);
		if(excludedDepartments!=null && excludedDepartments.length>0) {
			query.setParameterList("departments", excludedDepartments);
		}
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentsByTerm(String term, Integer[] departmentTypes, Integer firstResult,
			Integer maxResults,Department... excludedDepartments) {

		Query query = getSession()
				.createQuery(
						"from Department d where d.departmentType.id in (:types)"
						+ (excludedDepartments!=null && excludedDepartments.length>0 ? " and d not in :departments" : "")
						+ " and (d.name like :term or d.code like :term) order by d.code asc");
		query.setParameterList("types", departmentTypes);
		if(excludedDepartments!=null && excludedDepartments.length>0) {
			query.setParameterList("departments", excludedDepartments);
		}
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

	
}
