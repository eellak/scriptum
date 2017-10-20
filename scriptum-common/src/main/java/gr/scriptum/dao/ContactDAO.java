package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Contact;

@Repository
public class ContactDAO extends GenericDAO<Contact, Integer> {

	
	@Transactional(readOnly=true)
	public List<Contact> findUnassignedContacts(){
		Query query = getSession().createQuery(
				"from Contact c where c not in (select distinct uh.contact from UserHierarchy uh) order by c.id");
		return query.list();

	}
	
	@Transactional(readOnly=true)
	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Contact c where c.name like :term or c.surname like :term or c.email like :term");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Contact> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Contact c where c.name like :term or c.surname like :term or c.email like :term");
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
