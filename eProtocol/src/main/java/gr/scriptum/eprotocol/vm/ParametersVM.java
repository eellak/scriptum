/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Parameter;
import gr.scriptum.eprotocol.csv.ParameterConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)
@Init(superclass = true)
public class ParametersVM extends GenericSearchVM<Parameter, ParameterDAO, ParameterConverter> {

	@Override
	public String getEntityPage() {
		return ParameterVM.PAGE;
	}

}
