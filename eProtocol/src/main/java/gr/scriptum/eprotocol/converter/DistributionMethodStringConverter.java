/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import gr.scriptum.domain.DistributionMethod;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DistributionMethodStringConverter implements Converter<String, DistributionMethod, Component> {

	@Override
	public String coerceToUi(DistributionMethod distributionMethod, Component component, BindContext ctx) {
		if(distributionMethod==null) {
			return null;
		}
		return distributionMethod.getDescription() + (distributionMethod.getCode() != null ? " (" + distributionMethod.getCode() + ")" : "");
	}

	@Override
	public DistributionMethod coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
