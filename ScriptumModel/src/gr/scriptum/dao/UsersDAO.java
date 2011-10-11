/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

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

}
