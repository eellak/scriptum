package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.ProtocolBook;

@Repository
public class DocumentTypeDAO extends GenericDAO<DocumentType, Integer> {

	@Transactional(readOnly = true)
	public List<DocumentType> findByProtocolBook(ProtocolBook protocolBook) {
		Query query = getSession()
				.createQuery("select dt from DocumentType dt join dt.protocolBooks pb where pb = :protocolBook order by dt.name");
		query.setParameter("protocolBook", protocolBook);
		return query.list();
	}

	@Transactional(readOnly = true)
	public List<DocumentType> findByProtocolBookAndApplicableType(ProtocolBook protocolBook,
			ApplicableType applicableType,Integer firstResult, Integer maxResults) {
		Query query = getSession().createQuery(
				"select dt from DocumentType dt join dt.protocolBooks pb where pb =:protocolBook and dt.applicableTo=:applicableType order by dt.name");
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

	@Transactional(readOnly = true)
	public Integer countByTermAndProtocolBook(String term, ProtocolBook protocolBook) {
		Query query = getSession().createQuery("select count(*)from DocumentType dt join dt.protocolBooks pb"
				+ " where pb =:protocolBook and (dt.name like :term or dt.code like :term)");
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<DocumentType> findByTermAndProtocolBook(String term, ProtocolBook protocolBook, Integer firstResult,
			Integer maxResults) {

		Query query = getSession().createQuery("select(dt) from DocumentType dt join dt.protocolBooks pb"
				+ " where pb =:protocolBook and (dt.name like :term or dt.code like :term) order by dt.name");
		query.setParameter("protocolBook", protocolBook);
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
	public Integer countByTermAndProtocolBookAndApplicableType(String term, ProtocolBook protocolBook,
			ApplicableType applicableType) {
		Query query = getSession().createQuery("select count(*)from DocumentType dt join dt.protocolBooks pb"
				+ " where pb =:protocolBook and dt.applicableTo=:applicableType and (dt.name like :term or dt.code like :term)");
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("applicableType", applicableType);
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<DocumentType> findByTermAndProtocolBookAndApplicableType(String term, ProtocolBook protocolBook,
			ApplicableType applicableType, Integer firstResult, Integer maxResults) {

		Query query = getSession().createQuery("select(dt) from DocumentType dt join dt.protocolBooks pb"
				+ " where pb =:protocolBook and dt.applicableTo=:applicableType and (dt.name like :term or dt.code like :term) order by dt.name");
		query.setParameter("protocolBook", protocolBook);
		query.setParameter("applicableType", applicableType);
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
