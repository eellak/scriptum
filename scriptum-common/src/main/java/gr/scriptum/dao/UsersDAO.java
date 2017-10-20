/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Users;

/**
 * @author aanagnostopoulos
 * 
 */
@Repository
public class UsersDAO extends GenericDAO<Users, Integer> {

	@Transactional(readOnly = true)
	public List<Users> findUnassignedUsers() {

		Query query = getSession().createQuery(
				"from Users u where u not in (select distinct uh.user from UserHierarchy uh) order by u.id");
		return query.list();

	}

	@Transactional(readOnly = true)
	public Integer countUnassignedUsersByTerm(String term) {
		Query query = getSession().createQuery(
				"select count(*) from Users u where u not in (select distinct uh.user from UserHierarchy uh)"
				+ " and (u.username like :term or u.name like :term or u.surname like :term or u.code like :term)");
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		return ((Long) query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<Users> findUnassignedUsersByTerm(String term, Integer firstResult, Integer maxResults) {
		Query query = getSession().createQuery(
				"from Users u where u not in (select distinct uh.user from UserHierarchy uh)"
				+ " and (u.username like :term or u.name like :term or u.surname like :term or u.code like :term) order by u.id");
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
	public Integer countUnassignedUsersByTerm(String term, Set<Users> excludedUsers, Set<Users> includedUsers) {
		Query query = getSession().createQuery(
				"select count(*) from Users u where u not in (select distinct uh.user from UserHierarchy uh)"
						+ (excludedUsers.isEmpty() ? "" : " and u not in (:excluded)")
						+ (includedUsers.isEmpty() ? "" : " or u in (:included)")
				+ " and (u.username like :term or u.name like :term or u.surname like :term or u.code like :term)");
		if(excludedUsers.size()>0) {
			query.setParameterList("excluded", excludedUsers);
		}
		if(includedUsers.size()>0) {
			query.setParameterList("included",includedUsers);
		}
		query.setParameter("term", LIKE_OPERATOR + term + LIKE_OPERATOR);
		return ((Long) query.uniqueResult()).intValue();
	}

	@Transactional(readOnly = true)
	public List<Users> findUnassignedUsersByTerm(String term, Set<Users> excludedUsers, Set<Users> includedUsers, Integer firstResult, Integer maxResults) {
		Query query = getSession().createQuery(
				"from Users u where u not in (select distinct uh.user from UserHierarchy uh)"
				+ (excludedUsers.isEmpty() ? "" : " and u not in (:excluded)")
				+ (includedUsers.isEmpty() ? "" : " or u in (:included)")
				+ " and (u.username like :term or u.name like :term or u.surname like :term or u.code like :term) order by u.id");
		if(excludedUsers.size()>0) {
			query.setParameterList("excluded", excludedUsers);
		}
		if(includedUsers.size()>0) {
			query.setParameterList("included",includedUsers);
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

	
	@Transactional(readOnly = true)
	public Integer countByTerm(String term) {
		Query query = getSession().createQuery(
				"select count(*) from Users u where u.name like :term or u.surname like :term or u.email like :term or u.username like :term");
		query.setParameter("term", term + LIKE_OPERATOR);

		return ((Long) query.uniqueResult()).intValue();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Users> findByTerm(String term, Integer firstResult, Integer maxResults) {

		Query query = getSession().createQuery(
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

	@Override
	public boolean isDeletable(Integer id) {
		return super.isDeletable(id, "roles", "userAssignments");
	}

	@Transactional(readOnly = true)
	public Long countByUsername(String username) {
		Query query = getSession().createQuery(
				"select count(*) from Users u where u.username = :username");
		query.setParameter("username", username);

		return ((Long) query.uniqueResult());
	}
	
	@Transactional(readOnly = true)
	public List<Users> findByUsername(String username, Integer firstResult, Integer maxResults) {
		Query query = getSession().createQuery("from Users u where u.username = :username");
		query.setParameter("username", username);

		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
}
