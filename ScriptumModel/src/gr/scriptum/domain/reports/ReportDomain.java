package gr.scriptum.domain.reports;

import java.lang.reflect.Field;


/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 2, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportDomain implements Reporter{

	public static final String COL_DILIMITER = ",";
	
	
	@Override 
	public String  getReportLine() {

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
				
				if(i < fieldlist.length - 1 )
					buffy.append(COL_DILIMITER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buffy.toString();
	}

	@Override
	public String getReportHeader() {
		StringBuffer buffy = new StringBuffer();

		Class<?> cls = this.getClass();
		Field fieldlist[] = cls.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			Field fld = fieldlist[i];
			buffy.append(fld.getName());
			if(i < fieldlist.length - 1 )
				buffy.append(COL_DILIMITER);		
		}
		
		return buffy.toString();
		
	}

	
}
