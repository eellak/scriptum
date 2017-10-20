/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zkplus.spring.SpringUtil;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.util.Permission;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class ContactVM extends GenericEntityVM<Contact, Integer, ContactDAO> {

	public static final String PAGE = "contact.zul";

	private List<Company> companies = null;

	@Init(superclass = true)
	public void initVM() {
		CompanyDAO companyDAO = SpringUtil.getApplicationContext().getBean(CompanyDAO.class);
		companies = companyDAO.findAll();
	}

	public boolean isSaveEnabled() throws Exception {
		if (isEntityNotCreated()) {
			return true;
		}
		return SecurityUtil.isAllGranted(Permission.EDIT_CONTACT.toString());
	}

	public boolean isDeleteEnabled() throws Exception {
		if (isEntityNotCreated()) {
			return false;
		}
		return SecurityUtil.isAllGranted(Permission.EDIT_CONTACT.toString());
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}
