package gr.scriptum.domain.reports;

import java.util.List;

/**
 * @author Michael Mountrakis mountrakis@uit.gr
 * Date: Dec 5, 2011
 * Project: SCRIPTUM http://www.scriptum.gr
 * http://www.uit.gr
 */
public class ReportSqlDomain extends ReportDomain implements Reporter {
	String selectedFields[];
	String fieldsValues[];
	
	public ReportSqlDomain(){
	
	}
	
	
	public String getReportHeader() {
		StringBuffer buffy = new StringBuffer();
		for(int i=0; i<selectedFields.length; i++){
			buffy.append(selectedFields[i]);
			buffy.append(COL_DILIMITER);
		}
		return buffy.toString();
	}

	@Override
	public String getReportLine() {
		StringBuffer buffy = new StringBuffer();
		for(int i=0; i<fieldsValues.length; i++){
			if( fieldsValues[i] != null )
				buffy.append(fieldsValues[i].toString());
			buffy.append(COL_DILIMITER);
		}
		return buffy.toString();
	}

	public String[] getSelectedFields() {
		return selectedFields;
	}

	public void setSelectedFields(List<String> fieldsList) {
		selectedFields = new String[ fieldsList.size()];
		int i=0;
		for( String s : fieldsList){
			selectedFields[i++] = s;
		}
	}
	
	public void setSelectedFields(String [] fieldsArray) {
		selectedFields = new String[ fieldsArray.length];
		for( int i=0 ; i< fieldsArray.length; i++){
			selectedFields[i] = fieldsArray[i];
		}
	}
	

	public String[] getFieldsValues() {
		return fieldsValues;
	}

	public void setFieldsValues(Object [] oFieldsValues) {
		fieldsValues = new String[ oFieldsValues.length ];
		for(int i=0; i<oFieldsValues.length; i++ ){
			if( oFieldsValues[i] != null  )
				fieldsValues[i] = oFieldsValues[i].toString();
			else
				fieldsValues[i] = "";
		}
	}

}
