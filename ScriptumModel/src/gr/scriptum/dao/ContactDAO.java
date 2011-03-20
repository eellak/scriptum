package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

import gr.scriptum.domain.Contact;

public class ContactDAO extends GenericDAO<Contact, Integer> {

	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Contact c where c.name like :term or c.surname like :term or c.email like :term or c.company.name like :term");
		query.setParameter("term", term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	public List<Contact> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Contact c where c.name like :term or c.surname like :term or c.email like :term or c.company.name like :term");
		query.setParameter("term", term + LIKE_OPERATOR);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}

		return query.list();
	}

}
