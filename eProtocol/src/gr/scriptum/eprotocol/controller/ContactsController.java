package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ContactDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.eprotocol.csv.ContactConverter;

public class ContactsController extends GenericSearchController<Contact, ContactDAO, ContactConverter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3976185376125242826L;

	
	public String getEntityPage() {
		return ContactController.PAGE;
	}
}
