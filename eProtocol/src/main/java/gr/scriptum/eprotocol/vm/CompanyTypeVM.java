/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.CompanyTypeDAO;
import gr.scriptum.domain.CompanyType;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Init(superclass=true)
public class CompanyTypeVM extends GenericEntityVM<CompanyType, Integer, CompanyTypeDAO> {

	public static final String PAGE = "companyType.zul";
	
}
