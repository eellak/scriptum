/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import org.apache.commons.lang3.ArrayUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.eprotocol.csv.DocumentTypeConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)
public class DocumentTypesVM extends GenericSearchVM<DocumentType, DocumentTypeDAO, DocumentTypeConverter> {

	private ApplicableType[] applicableTypes;

	@Override
	public String getEntityPage() {
		return DocumentTypeVM.PAGE;
	}

	@Init(superclass = true)
	public void initVM() {
		applicableTypes = ApplicableType.values();
		applicableTypes = ArrayUtils.add(applicableTypes, 0, null);
	}

	public ApplicableType[] getApplicableTypes() {
		return applicableTypes;
	}

	public void setApplicableTypes(ApplicableType[] applicableTypes) {
		this.applicableTypes = applicableTypes;
	}

}
