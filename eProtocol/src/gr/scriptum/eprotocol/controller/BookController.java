/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.ProtocolBook;

/**
 * @author aanagnostopoulos
 * 
 */
public class BookController extends
		GenericEntityController<ProtocolBook, ProtocolBookDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 523942398656291980L;

	public static final String PAGE = "book.zul";
}
