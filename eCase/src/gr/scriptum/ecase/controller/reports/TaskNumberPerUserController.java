package gr.scriptum.ecase.controller.reports;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.reports.ReportTaskNumberPerUserDAO;
import gr.scriptum.domain.reports.TaskPerUser;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;


/**
 * MySQL Report for the number of user tasks that have been assigned by this user for all all his projects
 * to all sub users regarding their state.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class TaskNumberPerUserController extends BaseController {

	private static final long serialVersionUID = -4764822453190267965L;
	private static Log log = LogFactory.getLog(TaskNumberPerUserController.class);

	/* components */
	Window win;
	Paging reportPgng;
	Bandbox reportBndbx;

	/* data binding */
	private List<TaskPerUser> reportResults = null;

	/**
	 * ola ta tasks poy exoyn anateuei se olous (kai ifistamenous) sta erga poy
	 * exw dimiourghsei, kathws kai ta eleuyera tasks pou dhmioyrghse o xristis
	 * pou tin kalei
	 */

	private void createReport(int startIndex) throws InterruptedException {
		// Use the Domain to load the daos.
		ReportTaskNumberPerUserDAO dao = new ReportTaskNumberPerUserDAO();

		// set up paging by counting records first
		Integer totalSize = dao.countReportRows(getUserInSession());;
		reportPgng.setTotalSize(totalSize);
		reportResults = (List<TaskPerUser>)dao.createReport(getUserInSession(),
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

	public List<TaskPerUser> getReportResults() {
		return reportResults;
	}

	public void setReportResults(List<TaskPerUser> reportResults) {
		this.reportResults = reportResults;
	}



}
