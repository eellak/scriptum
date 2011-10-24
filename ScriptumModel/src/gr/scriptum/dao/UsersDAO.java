/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
public class UsersDAO extends GenericDAO<Users, Integer> {

	public List<Users> findUnassignedUsers() {

		Query query = getSession()
				.createQuery(
						"from Users u where u not in (select distinct uh.users from UserHierarchy uh) order by u.id");
		return query.list();

	}

	public Integer countByTerm(String term) {
		Query query = getSession()
				.createQuery(
						"select count(*) from Users u where u.name like :term or u.surname like :term or u.email like :term or u.username like :term");
		query.setParameter("term", term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@SuppressWarnings("unchecked")
	public List<Users> findByTerm(String term, Integer firstResult,
			Integer maxResults) {

		Query query = getSession()
				.createQuery(
						"from Users u where u.name like :term or u.surname like :term or u.email like :term or u.username like :term");
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
