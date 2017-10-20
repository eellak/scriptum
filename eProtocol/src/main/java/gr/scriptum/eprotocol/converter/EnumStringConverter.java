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
public class EnumStringConverter implements Converter<String, Enum, Component> {

	@Override
	public String coerceToUi(Enum beanProp, Component component, BindContext ctx) {
		if (beanProp == null) {
			return null;
		}
		
		return Labels.getLabel(beanProp.getClass().getSimpleName()+"."+beanProp.toString());
	}

	@Override
	public Enum coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}
}
