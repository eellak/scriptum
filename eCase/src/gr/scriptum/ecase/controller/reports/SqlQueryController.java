package gr.scriptum.ecase.controller.reports;


import java.io.File;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import gr.scriptum.dao.reports.ReportSqlDAO;
import gr.scriptum.domain.reports.ReportSqlDomain;




/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 * Date: Dec 1, 2011 11:09:00 AM <br>
 * Project: SCRIPTUM http://www.scriptum.gr <br>
 * http://www.uit.gr <br>
   MySQL Report for all tasks (and free tasks) that have been assigned by this user from all his projects
   to all sub users.
 */
public class SqlQueryController extends GenericReportController<ReportSqlDomain, ReportSqlDAO>{

	private static final long serialVersionUID = -569094817118003679L;
	Window win;
	private String sqlQuery;
	private String reportName;	
	Listbox listBox = new Listbox();
	

	
	
	public void onClick$exportBtn() throws Exception {

		ReportSqlDAO dao = new ReportSqlDAO();
		dao.setSqlQuery(sqlQuery);
		
		Integer rowCount = dao.countReportRows(getUserInSession());
		reportResults = dao.createReport(getUserInSession(), 0, rowCount);

		File file = exportTo(reportResults,
				"export_" + reportName + ".csv");
		Filedownload.save(file, "text/csv");
		
		createListBox();
	}

	
	
	
	private void createListBox(){
	
		Listbox listBox = new Listbox();
		listBox.renderAll();
		
		boolean populateHeaders = false;
		for( ReportSqlDomain result : reportResults){
			
			if(!populateHeaders){	
				Listhead listHead = new Listhead();
				String headers [] = result.getSelectedFields();
				for(int i=0; i<headers.length; i++ ){
					Listheader listHeader = new Listheader();
					listHeader.setLabel(headers[i]);
					listHead.appendChild(listHeader);
				}
				listBox.appendChild(listHead);
				populateHeaders = true;
			}
			
			Listitem listItem = new Listitem();
			String [] values = result.getFieldsValues();
			for(int i=0; i<values.length; i++){
				Listcell listCell = new Listcell();
				Label label = new Label();
				label.setValue(values[i]);
				listCell.appendChild(label);
				listItem.appendChild(listCell);
			}
			listBox.appendChild(listItem);
		}	
		win.appendChild(listBox);
	}
	
	
	
	
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
}
