/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.CorrespondentGroup_;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class CorrespondentGroupDAO extends GenericDAO<CorrespondentGroup, Long> {

	@Transactional(readOnly = true)
	public boolean isDeletable(Long id) {
		return super.isDeletable(id, CorrespondentGroup_.protocolCorrespondents.getName());
	}

	@Transactional(readOnly = true)
	public Integer countByTermAndApplicableToAndActive(String term, CorrespondentDirection type,
			boolean active) {
		Query query = getSession().createQuery(
				"select count(*) from CorrespondentGroup d where d.applicableTo=:applicableTo and d.active=:active and  (d.name like :term or d.code like :term)");
		query.setParameter("applicableTo", type);
		query.setParameter("active", active);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<CorrespondentGroup> findByTermAndApplicableToAndActive(String term, CorrespondentDirection type,
			boolean active, Integer firstResult, Integer maxResults) {

		Query query = getSession().createQuery(
				"from CorrespondentGroup d where d.applicableTo=:applicableTo and d.active=:active and (d.name like :term or d.code like :term)");
		query.setParameter("applicableTo", type);
		query.setParameter("active", active);
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
