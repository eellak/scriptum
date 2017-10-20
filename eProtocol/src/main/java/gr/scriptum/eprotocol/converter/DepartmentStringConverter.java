/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import gr.scriptum.domain.Department;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DepartmentStringConverter implements Converter<String, Department, Component> {

	@Override
	public String coerceToUi(Department department, Component component, BindContext ctx) {
		if (department == null) {
			return null;
		}
		return department.getName() + (department.getCode() != null ? " (" + department.getCode() + ")" : "");
	}

	@Override
	public Department coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
