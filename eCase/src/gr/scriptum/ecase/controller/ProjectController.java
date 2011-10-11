/**
 * 
 */
package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericEntityController;
import gr.scriptum.dao.ProjectDAO;
import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Project;
import gr.scriptum.domain.Users;

import java.util.List;

import org.zkoss.zk.ui.Component;

/**
 * @author aanagnostopoulos
 * 
 */
public class ProjectController extends
		GenericEntityController<Project, ProjectDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5346867503629173059L;

	public static final String PAGE = "project.zul";

	/* data binding */
	private List<Users> users = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		UsersDAO usersDAO = new UsersDAO();
		users = usersDAO.findAll();
		
	}
}
