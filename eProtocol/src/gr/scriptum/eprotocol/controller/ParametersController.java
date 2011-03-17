package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Parameter;
import gr.scriptum.eprotocol.csv.ParameterConverter;

public class ParametersController extends
		GenericSearchController<Parameter, ParameterDAO, ParameterConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8753114759635139834L;

	public String getEntityPage() {
		return ParameterController.PAGE;
	}
}
