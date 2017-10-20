/**
 * 
 */
package gr.scriptum.eprotocol.util;

import org.apache.commons.lang.StringUtils;

import gr.scriptum.domain.ProtocolCorrespondent;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class TextUtils {

	public static String getSummary(String text, int numberOfWords) {
		String myString = text;
		String[] arr = myString.split("\\s+");
		// Splits words & assign to the arr[] ex : arr[0] -> Copying ,arr[1] ->
		// first

		int N = numberOfWords; // NUMBER OF WORDS THAT YOU NEED
		if (arr.length <= N) {
			return myString;
		}
		String nWords = "";
		// concatenating number of words that you required
		for (int i = 0; i < N; i++) {
			nWords = nWords + " " + arr[i];
		}
		return nWords + "...";
	}

	public static String getLabel(ProtocolCorrespondent correspondent) {
		String label = null;
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
			label = correspondent.getContact() != null
					? (correspondent.getContact().getFullName()
							+ (correspondent.getContact().getCode() != null
									? " (" + correspondent.getContact().getCode() + ")" : "")
							+ " - (" + correspondent.getDepartment().getName() + ")")
					: null;
			break;
		case DEPARTMENT:
			label = correspondent.getDepartment() != null
					? correspondent.getDepartment().getName() + " (" + correspondent.getDepartment().getCode() + ")"
					: null;
			break;
		case COMPANY:
			label = correspondent.getCompany() != null
					? (correspondent.getCompany().getName() + (correspondent.getCompany().getCode() == null ? ""
							: " (" + correspondent.getCompany().getCode() + ")"))
					: null;
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
		return label;
	}
}
