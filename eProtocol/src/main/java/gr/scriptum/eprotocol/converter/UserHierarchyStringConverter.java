/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import gr.scriptum.domain.UserHierarchy;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class UserHierarchyStringConverter implements Converter<String, UserHierarchy, Component> {

	@Override
	public String coerceToUi(UserHierarchy userHierarchy, Component component, BindContext ctx) {
		return (userHierarchy!=null && userHierarchy.getContact() != null)
				? (userHierarchy.getContact().getFullName()
						+ (userHierarchy.getContact().getCode() != null
								? " (" + userHierarchy.getContact().getCode() + ")" : "")
						+ " - (" + userHierarchy.getDepartment().getName() + ")")
				: null;
	}

	@Override
	public UserHierarchy coerceToBean(String compAttr, Component component, BindContext ctx) {
		return null;
	}

}
