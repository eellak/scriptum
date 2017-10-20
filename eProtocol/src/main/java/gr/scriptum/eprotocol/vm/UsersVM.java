/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static java.lang.Boolean.*;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Users;
import gr.scriptum.eprotocol.csv.UserConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)

public class UsersVM extends GenericSearchVM<Users, UsersDAO, UserConverter> {

	private Boolean enabled;

	private Boolean disabled;

	private void initStatusFilter() {
		enabled = TRUE;
		disabled = FALSE;
		super.getEntity().setIsDisabled(FALSE);
	}

	@Override
	public String getEntityPage() {
		return UserVM.PAGE;
	}

	@Init(superclass = true)
	public void initVM() {
		initStatusFilter();
	}

	@Override
	@Command
	@NotifyChange({ "entity", "entities", "totalSize", "activePage", "enabled", "disabled" })
	public void searchEntities() throws Exception {
		if (!enabled && !disabled) {
			initStatusFilter();
		} else if (enabled ^ disabled) {
			getEntity().setIsDisabled(disabled ? TRUE : FALSE);
		} else {
			getEntity().setIsDisabled(null);
		}
		super.searchEntities();
	}

	@Override
	@Command
	@NotifyChange({ "entity", "enabled", "disabled" })
	public void clearSearch() throws Exception {
		super.clearSearch();
		initStatusFilter();
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

}
