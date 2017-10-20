/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Parameter;
import gr.scriptum.eprotocol.util.IConstants;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Init(superclass = true)
public class ParameterVM extends GenericEntityVM<Parameter, Integer, ParameterDAO> {

	public static final String PAGE = "parameter.zul";

	@Override
	protected void save() throws Exception {
		super.save();

		// update some application wide parameters with their new values
		//TODO: dynamically update global application parameters
		switch (entity.getName()) {
		case IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY:
			Executions.getCurrent().getDesktop().getWebApp().setAttribute(IConstants.PARAM_ENABLE_DOSSIER_FUNCTIONALITY,
					Boolean.valueOf(entity.getValue()));
			break;
		case IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY:
			Executions.getCurrent().getDesktop().getWebApp().setAttribute(IConstants.PARAM_ENABLE_PENDING_FUNCTIONALITY,
					Boolean.valueOf(entity.getValue()));
		case IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY:
			Executions.getCurrent().getDesktop().getWebApp().setAttribute(
					IConstants.PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY, Boolean.valueOf(entity.getValue()));
			break;
		}
	}

}
