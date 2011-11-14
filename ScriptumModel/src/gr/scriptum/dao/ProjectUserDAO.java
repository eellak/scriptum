/**
 * 
 */
package gr.scriptum.dao;

import java.util.List;

import org.hibernate.Query;

import gr.scriptum.domain.Project;
import gr.scriptum.domain.ProjectUser;
import gr.scriptum.domain.ProjectUserId;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProjectUserDAO extends GenericDAO<ProjectUser, ProjectUserId> {

	public List<ProjectUser> findByProject(Project project) {
		Query query = getSession()
				.createQuery(
						"from ProjectUser pu where pu.id.project = :project order by pu.id.users.id");
		query.setParameter("project", project);
		return query.list();

	}

}
