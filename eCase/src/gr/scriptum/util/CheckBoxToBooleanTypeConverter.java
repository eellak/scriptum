/**
 * 
 */
package gr.scriptum.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Checkbox;

/**
 * @author aanagnostopoulos
 * 
 */
public class CheckBoxToBooleanTypeConverter implements TypeConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zkoss.zkplus.databind.TypeConverter#coerceToBean(java.lang.Object,
	 * org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		Checkbox checkbox = (Checkbox) arg1;
		if (checkbox.isChecked()) {
			return new Boolean(true);
		}
		return new Boolean(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToUi(java.lang.Object,
	 * org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Checkbox checkbox = (Checkbox) arg1;
		Boolean value = (Boolean) arg0;
		if (value == null) {
//			 checkbox.setChecked(false);
			return false;
		}
		if (value == false) {
//			 checkbox.setChecked(false);
			return false;
		}
		if (value == true) {
//			 checkbox.setChecked(true);
			return true;
		}
		return null;
	}

}
