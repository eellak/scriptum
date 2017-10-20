/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Dossier;

/**
 * @author aanagnostopoulos
 *
 */
@Repository
public class DossierDAO extends GenericDAO<Dossier, Integer> {

	@Transactional(readOnly=true)
	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Dossier c where c.name like :term and c.isActive = :isActive");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameter("isActive", Dossier.IS_ACTIVE);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Dossier> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Dossier c where c.name like :term and c.isActive = :isActive");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		query.setParameter("isActive", Dossier.IS_ACTIVE);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

}
