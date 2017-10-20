/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.Subject;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class SubjectDAO extends GenericDAO<Subject, Integer> {

	@Transactional(readOnly=true)
	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Subject s where s.code like :term or s.description like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Subject> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Subject s where s.code like :term or s.description like :term");
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
	public Integer countByTermAndProtocolBookAndApplicableTo(String term, ProtocolBook protocolBook,
			ApplicableType applicableType) {
		Query query = getSession().createQuery(
				"select count(s) from Subject s inner join s.documentType dt join dt.protocolBooks pb"
				+ " where pb = :protocolBook"
				+ " and dt.applicableTo=:applicableType"
				+ " and (s.code like :term or s.description like :term)");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("applicableType", applicableType);

		return ((Long) query.uniqueResult()).intValue();
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Subject> findByTermAndProtocolBookAndApplicableTo(String term, ProtocolBook protocolBook,
			ApplicableType applicableType,Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"select s from Subject s inner join s.documentType dt join dt.protocolBooks pb"
								+ " where pb = :protocolBook"
								+ " and dt.applicableTo=:applicableType"
								+ " and (s.code like :term or s.description like :term)");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("applicableType", applicableType);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Subject> findByCodeAndProtocolBookAndApplicableTo(String code, ProtocolBook protocolBook,
			ApplicableType applicableType,Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"select s from Subject s inner join s.documentType dt join dt.protocolBooks pb"
								+ " where pb =:protocolBook"
								+ " and dt.applicableTo=:applicableType"
								+ " and s.code = :code");
		query.setParameter("code",code);
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("applicableType", applicableType);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

}
