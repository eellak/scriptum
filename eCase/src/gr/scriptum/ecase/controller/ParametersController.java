package gr.scriptum.ecase.controller;

import gr.scriptum.controller.GenericSearchController;
import gr.scriptum.csv.ParameterConverter;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.Parameter;

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
