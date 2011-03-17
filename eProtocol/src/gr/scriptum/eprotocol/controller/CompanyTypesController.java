package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.CompanyType;
import gr.scriptum.eprotocol.csv.CompanyTypeConverter;

public class CompanyTypesController extends
		GenericSearchController<CompanyType, CompanyTypeDAO, CompanyTypeConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3541190192877787475L;


	public String getEntityPage() {
		return CompanyTypeController.PAGE;
	}

}
