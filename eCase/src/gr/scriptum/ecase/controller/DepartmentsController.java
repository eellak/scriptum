/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.domain.Department;
import gr.scriptum.ecase.util.DepartmentTreeRenderer;

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

	Tree departmentsTree;

	private DefaultTreeNode getTreeBranch(Department root) {

		DefaultTreeNode rootNode = null;
		if (root.getDepartments().isEmpty()) {
			rootNode = new DefaultTreeNode(root); // no children allowed
		} else {
			rootNode = new DefaultTreeNode(
					root,
					new ArrayList<DefaultTreeNode>(root.getDepartments().size()));
		}
		for (Department child : root.getDepartments()) {
			rootNode.add(getTreeBranch(child));
		}
		return rootNode;
	}

	private DefaultTreeNode refreshTree() {

		DefaultTreeNode root = null;

		DepartmentDAO departmentDAO = new DepartmentDAO();
		List<Department> rootDepartments = departmentDAO.findRootDepartments();
		if (rootDepartments.isEmpty()) {
			root = new DefaultTreeNode(null);
		} else {
			root = new DefaultTreeNode(null, new ArrayList<DefaultTreeNode>(
					rootDepartments.size()));
		}
		for (Department rootDepartment : rootDepartments) {

			DefaultTreeNode branchNode = getTreeBranch(rootDepartment);

			root.add(branchNode);
		}

		return root;

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		departmentsTree.setItemRenderer(new DepartmentTreeRenderer());
		departmentsTree.setModel(new DefaultTreeModel(refreshTree()));

		page.setAttribute(this.getClass().getSimpleName(), this);
	}
}
