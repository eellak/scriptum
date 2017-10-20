/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.eprotocol.csv.CompanyConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass=true)
@Init(superclass=true)
public class CompaniesVM extends GenericSearchVM<Company, CompanyDAO, CompanyConverter> {

	@Override
	public String getEntityPage() {
		return CompanyVM.PAGE;
	}

}
