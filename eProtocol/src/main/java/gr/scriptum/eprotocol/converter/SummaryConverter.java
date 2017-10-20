/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import gr.scriptum.eprotocol.util.TextUtils;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class SummaryConverter implements Converter<String, String, Component> {

	private static final int DEFAULT_SUMMARY_WORDS = 3;

	@Override
	public String coerceToBean(String arg0, Component arg1, BindContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String coerceToUi(String val, Component arg1, BindContext ctx) {
		
		final Long numberOfWords = ctx.getConverterArg("words") == null ? DEFAULT_SUMMARY_WORDS
				: (Long) ctx.getConverterArg("words");

		if (val == null) {
			return null;
		}else {
			return TextUtils.getSummary(val, numberOfWords.intValue());
		}
	}
}
