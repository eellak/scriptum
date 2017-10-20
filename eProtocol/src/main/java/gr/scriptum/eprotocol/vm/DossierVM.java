/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.DossierDAO;
import gr.scriptum.domain.Dossier;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Init(superclass=true)
public class DossierVM extends GenericEntityVM<Dossier, Integer, DossierDAO> {

	public static final String PAGE = "dossier.zul";
}
