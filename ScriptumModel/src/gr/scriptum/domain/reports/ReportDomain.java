package gr.scriptum.domain.reports;

import java.lang.reflect.Field;


/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 2, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportDomain {

	@Override 
	public String toString() {
		final String COL_DILIMITER = ",";
		StringBuffer buffy = new StringBuffer();

		Class<?> cls = this.getClass();
		Field fieldlist[] = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			try {
				String val = fld.get(this).toString()
						.replace(COL_DILIMITER, "");

				System.out.println( "Value : " + val );
				buffy.append(val);
				buffy.append(COL_DILIMITER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buffy.toString();
	}

}
