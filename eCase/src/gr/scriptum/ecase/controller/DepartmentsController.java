/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.domain.Department;

/**
 * @author aanagnostopoulos
 * 
 */
public class DepartmentsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7691729026696290141L;

	private static Log log = LogFactory.getLog(DepartmentsController.class);

	Window departmentsWin;

	Tree depatmentsTree;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		DepartmentDAO departmentDAO = new DepartmentDAO();
		List<Department> rootDepartments = departmentDAO.findRootDepartments();
		if(!rootDepartments.isEmpty()) {
			
		}
		page.setAttribute(this.getClass().getSimpleName(), this);
	}
}
