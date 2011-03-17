package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.eprotocol.csv.DocumentTypeConverter;

public class DocumentTypesController extends
		GenericSearchController<DocumentType, DocumentTypeDAO, DocumentTypeConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955046061167139464L;

	public String getEntityPage() {
		return DocumentTypeController.PAGE;
	}

}
