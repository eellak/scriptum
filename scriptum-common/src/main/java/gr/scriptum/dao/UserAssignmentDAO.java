/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import gr.scriptum.domain.Permission;
import gr.scriptum.domain.Resource.ResourceType;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.Users;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Repository
public class UserAssignmentDAO extends GenericDAO<UserAssignment, Integer> {

	@Transactional(readOnly = true)
	public List<UserAssignment> findByEntity(Class entityType, Object entityId) {

		Query query = getSession().createQuery("from UserAssignment ua where ua.resource.type=:type"
				+ " and ua.resource.keyCode=:keyCode" + " and ua.resource.value=:value"
						+ " order by ua.user.surname, ua.user.name");
		query.setParameter("type", ResourceType.ENTITY);
		query.setParameter("keyCode", entityType.getSimpleName());
		query.setParameter("value", entityId.toString());
		return query.list();
	}

	@Transactional(readOnly=true)
	public List<UserAssignment> findByUserPermissions(Users user){
		Query query = getSession().createQuery("from UserAssignment ua where ua.user=:user"
				+ " and ua.resource is null");
		query.setParameter("user", user);
		return query.list();
	}
	
	public List<UserAssignment> findByUserAndEntityType(Users user, Class<?> entityType, Permission... permissions){
		Query query = getSession().createQuery("from UserAssignment ua where ua.user=:user and ua.resource.type=:type"
				+ " and ua.resource.keyCode=:keyCode" + (permissions.length==0 ? "" : " and ua.permission in :permissions"));
		//alternative query
/*		Query query = getSession().createQuery(
				"select ua from UserAssignment as ua inner join ua.resource as r where ua.user=:user and r.type=:type"
						+ " and r.keyCode=:keyCode");
*/		query.setParameter("user", user);
		query.setParameter("type", ResourceType.ENTITY);
		query.setParameter("keyCode", entityType.getSimpleName());
		if(permissions.length>0) {
			query.setParameterList("permissions", permissions);
		}
		return query.list();
	}
	
	@Transactional
	public List<UserAssignment> findByUserAndEntityTypeAndEntityId(Users user, Class<?> entityType, Object entityId, Permission...permissions){
		Query query = getSession().createQuery("from UserAssignment ua where ua.user=:user and ua.resource.type=:type"
				+ " and ua.resource.keyCode=:keyCode and ua.resource.value=:value" + (permissions.length==0 ? "" : " and ua.permission in :permissions"));
		//alternative query
/*		Query query = getSession().createQuery(
				"select ua from UserAssignment as ua inner join ua.resource as r where ua.user=:user and r.type=:type"
						+ " and r.keyCode=:keyCode");
*/		query.setParameter("user", user);
		query.setParameter("type", ResourceType.ENTITY);
		query.setParameter("keyCode", entityType.getSimpleName());
		query.setParameter("value", entityId.toString());
		if(permissions.length>0) {
			query.setParameterList("permissions", permissions);
		}
		return query.list();
	}

}
