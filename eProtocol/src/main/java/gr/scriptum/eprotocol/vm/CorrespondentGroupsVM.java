/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.eprotocol.csv.CorrespondentGroupConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)
public class CorrespondentGroupsVM
		extends GenericSearchVM<CorrespondentGroup, CorrespondentGroupDAO, CorrespondentGroupConverter> {

	@Override
	public String getEntityPage() {
		return CorrespondentGroupVM.PAGE;
	}
	
	@Init(superclass = true)
	public void initVM() {
		getEntity().setActive(null);
	}
}
