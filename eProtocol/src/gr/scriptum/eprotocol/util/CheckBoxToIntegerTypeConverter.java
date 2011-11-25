/**
 * 
 */
package gr.scriptum.eprotocol.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Checkbox;

/**
 * @author aanagnostopoulos
 *
 */
public class CheckBoxToIntegerTypeConverter implements TypeConverter {

	/* (non-Javadoc)
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToBean(java.lang.Object, org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		Checkbox checkbox= (Checkbox) arg1;
		if(checkbox.isChecked()) {
			return new Integer(1);
		}
		return new Integer(0);
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zkplus.databind.TypeConverter#coerceToUi(java.lang.Object, org.zkoss.zk.ui.Component)
	 */
	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Checkbox checkbox= (Checkbox) arg1;
		Integer value = (Integer) arg0;
		if(value == null) {
//			checkbox.setChecked(false);
			return false;
		}
		if(value.intValue()==0) {
//			checkbox.setChecked(false);
			return false;
		}
		if(value.intValue()==1) {
//			checkbox.setChecked(true);
			return true;
		}
		return null;
	}

}
