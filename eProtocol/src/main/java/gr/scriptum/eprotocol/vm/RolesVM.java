/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.RoleDAO;
import gr.scriptum.domain.Role;
import gr.scriptum.eprotocol.csv.DummyCSVConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass=true)
@Init(superclass = true)
public class RolesVM extends GenericSearchVM<Role, RoleDAO, DummyCSVConverter> {

	@Override
	public String getEntityPage() {
		return RoleVM.PAGE;
	}

}
