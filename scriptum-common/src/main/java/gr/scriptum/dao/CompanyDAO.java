package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Company;

@Repository
public class CompanyDAO extends GenericDAO<Company, Integer> {

	@Transactional(readOnly=true)
	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Company c where c.name like :term or c.code like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Company> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Company c where c.name like :term or c.code like :term");
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
