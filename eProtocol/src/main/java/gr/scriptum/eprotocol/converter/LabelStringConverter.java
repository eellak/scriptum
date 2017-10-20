/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class LabelStringConverter implements Converter<String, Object, Component> {

	@Override
	public String coerceToUi(Object beanProp, Component component, BindContext ctx) {
		if (beanProp == null) {
			return null;
		}
		String value = beanProp.toString();

		return Labels.getLabel(value);
	}

	@Override
	public Object coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
