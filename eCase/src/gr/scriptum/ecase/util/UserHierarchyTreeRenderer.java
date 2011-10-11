/**
 * 
 */
package gr.scriptum.ecase.util;

import gr.scriptum.domain.UserHierarchy;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 * @author aanagnostopoulos
 * 
 */
public class UserHierarchyTreeRenderer implements TreeitemRenderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zul.TreeitemRenderer#render(org.zkoss.zul.Treeitem,
	 * java.lang.Object)
	 */
	@Override
	public void render(Treeitem treeItem, Object node) throws Exception {
		final DefaultTreeNode treeNode = (DefaultTreeNode) node;
		final UserHierarchy data = (UserHierarchy) treeNode.getData();

		treeItem.setOpen(true);
		treeItem.setValue(data);

		Treerow treerow = new Treerow();
		treeItem.appendChild(treerow);
		treerow.appendChild(new Treecell(data.getUsers().getName()));
		treerow.appendChild(new Treecell(data.getUsers().getSurname()));
		treerow.appendChild(new Treecell(data.getUsers().getUsername()));
	}

}
