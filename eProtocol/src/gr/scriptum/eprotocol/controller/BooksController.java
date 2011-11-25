/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.eprotocol.csv.ProtocolBookConverter;

/**
 * @author aanagnostopoulos
 * 
 */
public class BooksController
		extends
		GenericSearchController<ProtocolBook, ProtocolBookDAO, ProtocolBookConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5623265686210379715L;

	public String getEntityPage() {
		return BookController.PAGE;
	}
}
