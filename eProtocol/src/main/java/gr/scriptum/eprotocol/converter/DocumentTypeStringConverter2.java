/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;

import gr.scriptum.domain.DocumentType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DocumentTypeStringConverter2 implements Converter<String, DocumentType, Component> {

	@Override
	public String coerceToUi(DocumentType documentType, Component component, BindContext ctx) {
		if (documentType == null) {
			return null;
		}
		return documentType.getName() + (documentType.getApplicableTo() == null ? "" :  " (" + Labels.getLabel(documentType.getApplicableTo().toString()) + ")");
	}

	@Override
	public DocumentType coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
