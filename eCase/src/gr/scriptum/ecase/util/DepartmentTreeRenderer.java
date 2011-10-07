/**
 * 
 */
package gr.scriptum.ecase.util;

import gr.scriptum.domain.Department;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 * @author aanagnostopoulos
 * 
 */
public class DepartmentTreeRenderer implements TreeitemRenderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.TreeitemRenderer#render(org.zkoss.zul.Treeitem,
	 * java.lang.Object)
	 */
	@Override
	public void render(Treeitem treeItem, Object node) throws Exception {
		final DefaultTreeNode treeNode = (DefaultTreeNode) node;
		final Department data = (Department) treeNode.getData();

		treeItem.setOpen(true);

		Treerow treerow = new Treerow();
		treeItem.appendChild(treerow);
		treerow.appendChild(new Treecell(data.getId().toString()));
		treerow.appendChild(new Treecell(data.getName()));
		treerow.appendChild(new Treecell(data.getDescription()));
	}

}
