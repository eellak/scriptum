/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.eprotocol.util.TextUtils;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class ProtocolCorrespondentStringConverter implements Converter<String, ProtocolCorrespondent, Component> {

	private static final int DEFAULT_SUMMARY_WORDS = 3;

	@Override
	public String coerceToUi(ProtocolCorrespondent correspondent, Component component, BindContext ctx) {

		final Boolean summary = ctx.getConverterArg("summary") == null ? false
				: (Boolean) ctx.getConverterArg("summary");
		final Long numberOfWords = ctx.getConverterArg("words") == null ? DEFAULT_SUMMARY_WORDS
				: (Long) ctx.getConverterArg("words");

		String label = null;

		if (correspondent == null || correspondent.getType()==null) {
			label = null;
		} else {
			switch (correspondent.getType()) {
			case INACTIVE_MEMBER:
			case ACTIVE_MEMBER:
				StringBuffer sb = new StringBuffer();
				if (correspondent.getDescription() != null) {
					sb.append(correspondent.getDescription());
				}
				if (correspondent.getCode() != null) {
					sb.append(" (" + correspondent.getCode() + ")");
				}
				label = StringUtils.trimToNull(sb.toString());
				break;
			case EMPLOYEE:
				label = correspondent.getContact() != null ? (correspondent.getContact().getFullName()
						+ (correspondent.getContact().getCode() != null
								? " (" + correspondent.getContact().getCode() + ")" : "")
						+ " - (" + correspondent.getDepartment().getName() + ")") : null;
				break;
			case DEPARTMENT:
				label = correspondent.getDepartment() != null
						? correspondent.getDepartment().getName() + " (" + correspondent.getDepartment().getCode() + ")"
						: null;
				break;
			case COMPANY:
				label = correspondent.getCompany() != null ? (correspondent.getCompany().getName() + (correspondent.getCompany().getCode() == null ? "" : " ("+correspondent.getCompany().getCode()+")") ) : null;
				break;
			case CONTACT:
				label = correspondent.getContact() != null ? correspondent.getContact().getFullName() : null;
				break;
			case OTHER:
				label = correspondent.getDescription();
				break;
			default:
				label = null;
			}
		}

		if (summary && label != null) {
			label = TextUtils.getSummary(label, numberOfWords.intValue());
		}

		return label;
	}

	@Override
	public ProtocolCorrespondent coerceToBean(String compAttr, Component component, BindContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
