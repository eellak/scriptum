/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Department;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class ProtocolCorrespondentDAO extends GenericDAO<ProtocolCorrespondent, Long> {

	@Transactional(readOnly = true)
	public Integer countByTermAndDirectionsAndType(String term, CorrespondentDirection[] directions,
			CorrespondentType type) {
		Query query = getSession().createQuery(
				"select count(*) from ProtocolCorrespondent c where c.direction in :directions and c.type=:type and (c.description like :term or c.code like :term or c.vatNumber like :term or c.ssn like :term)");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameterList("directions", directions);
		query.setParameter("type", type);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<ProtocolCorrespondent> findByTermAndDirectionsAndType(String term, CorrespondentDirection[] directions,
			CorrespondentType type, Integer firstResult, Integer maxResults) {

		Query query = getSession().createQuery(
				"from ProtocolCorrespondent c where c.direction in :directions and c.type=:type and (c.description like :term or c.code like :term or c.vatNumber like :term or c.ssn like :term)");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameterList("directions", directions);
		query.setParameter("type", type);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

	@Transactional(readOnly = true)
	public List<ProtocolCorrespondent> findProtocolAssigneesByDepartment(Protocol protocol, Department department) {
		Query query = getSession().createQuery(
				"from ProtocolCorrespondent c where c.protocol = :protocol and c.direction =:direction"
				+ " and ((c.type='EMPLOYEE' and c.department = :department) or (c.type='DEPARTMENT' and c.department.department=:department))");
		query.setParameter("protocol", protocol);
		query.setParameter("direction", CorrespondentDirection.ASSIGNEE);
		query.setParameter("department", department);
		return query.list();
	}
}
