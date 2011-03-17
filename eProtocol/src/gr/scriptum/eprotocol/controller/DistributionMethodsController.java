package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.Parameter;
import gr.scriptum.eprotocol.csv.DistributionMethodConverter;

public class DistributionMethodsController extends
		GenericSearchController<DistributionMethod, DistributionMethodDAO, DistributionMethodConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2971601867534626279L;

	public String getEntityPage() {
		return DistributionMethodController.PAGE;
	}
}
