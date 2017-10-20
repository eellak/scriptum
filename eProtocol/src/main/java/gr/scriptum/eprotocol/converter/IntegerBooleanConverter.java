/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class IntegerBooleanConverter implements Converter<Boolean, Integer, Component> {

	@Override
	public Boolean coerceToUi(Integer beanProp, Component component, BindContext ctx) {
		if (beanProp == null) {
			return false;
		}
		switch (beanProp) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			return null;
		}
	}

	@Override
	public Integer coerceToBean(Boolean compAttr, Component component, BindContext ctx) {
		if (compAttr == null) {
			return 0;
		}
		return compAttr ? 1 : 0;
	}

}
