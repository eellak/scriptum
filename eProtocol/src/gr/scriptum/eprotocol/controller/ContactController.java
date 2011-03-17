package gr.scriptum.eprotocol.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;

public class ContactController extends
		GenericEntityController<Contact, ContactDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3814570875167621069L;

	public static final String PAGE = "contact.zul";
	
	public static final String INCLUDE_CONTACT_WIN = "contact_win.zul";

	private List<Company> companies = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		CompanyDAO companyDAO = new CompanyDAO();
		companies = companyDAO.findAll();
	}

//	@Override
//	public void onClick$saveBtn() throws Exception {
//		super.onClick$saveBtn();
//		
//		Component fellow = win.getParent().getFellowIfAny("contactCbx", true);
//		if(fellow!=null) {
//			Events.postEvent("onContactAdded", fellow, entity);
//		}
//		
//	}
	
	/**
	 * @return the companies
	 */
	public List<Company> getCompanies() {
		return companies;
	}

	/**
	 * @param companies
	 *            the companies to set
	 */
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
}
