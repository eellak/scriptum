package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.eprotocol.csv.CompanyConverter;

public class CompaniesController extends GenericSearchController<Company, CompanyDAO, CompanyConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2311794761651744628L;
	
	public String getEntityPage() {
		return CompanyController.PAGE;
	}
}
