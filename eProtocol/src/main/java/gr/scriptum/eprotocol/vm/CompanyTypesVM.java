/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.CompanyType;
import gr.scriptum.eprotocol.csv.CompanyTypeConverter;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
@AfterCompose(superclass=true)
@Init(superclass=true)
public class CompanyTypesVM extends GenericSearchVM<CompanyType, CompanyTypeDAO, CompanyTypeConverter> {

	@Override
	public String getEntityPage() {
		return CompanyTypeVM.PAGE;
	}

}
