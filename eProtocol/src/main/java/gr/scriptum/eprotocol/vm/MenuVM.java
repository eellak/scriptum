/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class MenuVM extends BaseVM {

	private static Log log = LogFactory.getLog(MenuVM.class);
	
	public String getLogoutLabel() {
		String name = StringUtils.trimToNull(StringUtils.trimToEmpty(getUserInSession().getName()) + " " + StringUtils.trimToEmpty(getUserInSession().getSurname()));
		return Labels.getLabel("menu.exit") +" (" + (name == null ? super.getUserInSession().getUsername() : name)  +")";
	}
	
	@Command
	@NotifyChange("*")
	public void reloadLabels() {
		Labels.reset();
		Executions.getCurrent().sendRedirect(null);
	}
}
