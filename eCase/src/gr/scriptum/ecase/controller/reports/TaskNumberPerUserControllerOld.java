package gr.scriptum.ecase.controller.reports;


import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.reports.ReportTaskPerProjectDAO;
import gr.scriptum.domain.reports.TaskPerProject;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class TaskNumberPerUserControllerOld extends BaseController {

	private static final long serialVersionUID = -4764822453190267965L;
	private static Log log = LogFactory.getLog(TasksPerProjectController.class);

	/* components */
	Window win;
	Paging reportPgng;
	Bandbox reportBndbx;

	/* data binding */
	private List<TaskPerProject> reportResults = null;

	/**
	 * ola ta tasks poy exoyn anateuei se olous (kai ifistamenous) sta erga poy
	 * exw dimiourghsei, kathws kai ta eleuyera tasks pou dhmioyrghse o xristis
	 * pou tin kalei
	 */

	private void createReport(int startIndex) throws InterruptedException {
		// Use the Domain to load the daos.
		ReportTaskPerProjectDAO dao = new ReportTaskPerProjectDAO();

		// set up paging by counting records first
		Integer totalSize = dao.countReportRows(getUserInSession());;
		reportPgng.setTotalSize(totalSize);
		reportResults = (List<TaskPerProject>)dao.createReport(getUserInSession(),
				startIndex, reportPgng.getPageSize());
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		//Beware!!!!!!!!!!!!!!!!!!!!!
		//Use the following to set AssignerReportController as the data binding entity
		page.setAttribute(this.getClass().getSimpleName(), this);
	}

	public void onClick$createBtn() throws Exception {
		createReport(0);
		log.info("Gathered Results Count:" + reportResults.size());
		getBinder(win).loadAll();
	}

	public void onPaging$reportPgng(PagingEvent event)
			throws InterruptedException {

		int activePage = event.getActivePage();
		int startIndex = activePage * reportPgng.getPageSize();
		createReport(startIndex);
		getBinder(win).loadAll();

	}

	public List<TaskPerProject> getReportResults() {
		return reportResults;
	}

	public void setReportResults(List<TaskPerProject> reportResults) {
		this.reportResults = reportResults;
	}


}
