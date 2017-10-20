/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.DossierDAO;
import gr.scriptum.domain.Dossier;
import gr.scriptum.eprotocol.csv.DossierConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)
@Init(superclass = true)
public class DossiersVM extends GenericSearchVM<Dossier, DossierDAO, DossierConverter> {

	@Override
	public String getEntityPage() {
		return DossierVM.PAGE;
	}

}
