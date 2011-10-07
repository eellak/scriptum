/**
 * 
 */
package gr.scriptum.ecase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
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

	/* components */
	Window departmentsWin;
	Tree departmentsTree;

	/* data binding */
	private Department department = null;
	private List<Department> departments = null;

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

	private void refreshTree() {

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

		departmentsTree.setItemRenderer(new DepartmentTreeRenderer());
		departmentsTree.setModel(new DefaultTreeModel(root));

	}

	private void refreshDepartments() {
		DepartmentDAO departmentDAO = new DepartmentDAO();
		departments = departmentDAO.findAll();
	}

	private void delete() throws Exception {

		DepartmentDAO departmentDAO = new DepartmentDAO();

		departmentDAO.deleteById(department.getId());
		Messagebox.show(Labels.getLabel("delete.success"),
				Labels.getLabel("success.title"), Messagebox.OK,
				Messagebox.INFORMATION);

		department = null;

		refreshDepartments();
		refreshTree();
		getBinder(departmentsWin).loadAll();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		refreshDepartments();
		refreshTree();

		page.setAttribute(this.getClass().getSimpleName(), this);
	}

	public void onSelect$departmentsTree(SelectEvent event) {
		log.info(event);
		if (event.getSelectedItems().isEmpty()) {
			department = null;
		} else {
			Treeitem treeItem = (Treeitem) event.getReference();
			department = (Department) treeItem.getValue();
		}
		getBinder(departmentsWin).loadAll();
	}

	public void onClick$newBtn() {
		department = new Department();
		Treeitem selectedItem = departmentsTree.getSelectedItem();
		if (selectedItem != null) {
			department.setDepartment((Department) selectedItem.getValue());
		}
		getBinder(departmentsWin).loadAll();

	}

	public void onClick$saveBtn() throws InterruptedException {

		validateFields(departmentsWin);

		DepartmentDAO departmentDAO = new DepartmentDAO();
		if (department.getId() == null) {
			departmentDAO.makePersistent(department);
		} else {
			departmentDAO.update(department);
		}

		refreshDepartments();
		refreshTree();

		getBinder(departmentsWin).loadAll();

		Messagebox.show(Labels.getLabel("save.OK"),
				Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);
	}

	public void onClick$deleteBtn() throws InterruptedException {
		DepartmentDAO departmentDAO = new DepartmentDAO();
		if (!departmentDAO.isDeletable(department.getId())) {

			Messagebox.show(Labels.getLabel("delete.notDeletable"),
					Labels.getLabel("delete.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		log.info("Deleting");
		try {
			boolean delete = false;
			Messagebox.show(Labels.getLabel("delete.confirm"),
					Labels.getLabel("delete.title"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								delete();
							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}
	}

	public boolean isSaveDisabled() {
		if (department == null) {
			return true;
		}
		return false;
	}

	public boolean isDepartmentCreated() {
		if (department != null && department.getId() != null) {
			return true;
		}
		return false;
	}

	public boolean isDepartmentNotCreated() {
		return !isDepartmentCreated();
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}
