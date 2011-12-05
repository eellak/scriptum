package gr.scriptum.ecase.controller.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import gr.scriptum.controller.BaseController;
import gr.scriptum.dao.reports.ReportProducerDAO;
import gr.scriptum.domain.reports.Reporter;


/**
 * @author Michael Mountrakis mountrakis@uit.gr <br>
 *         Date: Dec 1, 2011 11:03:02 AM <br>
 *         Project: SCRIPTUM http://www.scriptum.gr <br>
 *         http://www.uit.gr <br>
 *         Template ZK Controller to be used for all reports. <br>
 * @param <T>
 *            The Hibernate domain - the Entity to be represented by each report
 *            line.
 * @param <DAO>
 *            The Hibernate Data Access Object (DAO) to be used for the report.
 */
public class GenericReportController<T extends Reporter, DAO extends ReportProducerDAO> extends
		BaseController {

	private static final long serialVersionUID = -4764822451990267965L;
	private static Log log = LogFactory.getLog(GenericReportController.class);

	/* components */
	Window win;
	Paging reportPgng;
	Bandbox reportBndbx;

	private Class<T> entityClass = null;

	private Class<DAO> daoClass = null;

	/* data binding */
	protected List<T> reportResults = null;

	protected T entity = null;


	@SuppressWarnings("unchecked")
	private T initEntity() throws Exception {
		return (T) Class.forName(entityClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	private DAO initDAO() throws Exception {
		return (DAO) Class.forName(daoClass.getName()).newInstance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// Beware!!!!!!!!!!!!!!!!!!!!!
		// Use the following to set AssignerReportController as the data binding
		// entity
		page.setAttribute(this.getClass().getSimpleName(), this);

		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		entity = initEntity();

		daoClass = (Class<DAO>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];

	}

	@SuppressWarnings("unchecked")
	protected void createReport(int startIndex) throws Exception {
		// Use the Domain to load the daos.
		DAO dao = initDAO();

		// set up paging by counting records first
		Integer totalSize = dao.countReportRows(getUserInSession());
		reportPgng.setTotalSize(totalSize);
		reportResults = (List<T>) dao.createReport(getUserInSession(),
				startIndex, reportPgng.getPageSize());
	}

	public void onClick$createBtn() throws Exception {
		createReport(0);
		log.info("Gathered Results Count:" + reportResults.size());
		getBinder(win).loadAll();
	}

	public void onPaging$reportPgng(PagingEvent event) throws Exception {

		int activePage = event.getActivePage();
		int startIndex = activePage * reportPgng.getPageSize();
		createReport(startIndex);
		getBinder(win).loadAll();

	}

	@SuppressWarnings("unchecked")
	public void onClick$exportBtn() throws Exception {

		DAO dao = initDAO();
		Integer totalSize = dao.countReportRows(getUserInSession());
		reportResults = (List<T>) dao.createReport(getUserInSession(), 0,
				totalSize);

		File file = exportTo(reportResults,
				"export_" + entityClass.getSimpleName() + ".csv");
		Filedownload.save(file, "text/csv");
	}

	public File exportTo(List<T> instances, String filename)
			throws IOException {
		final String LINE_DELIM = "\n";
		final String ENCODING = "UTF-8";

		File file = new File(filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, ENCODING);
	
		try {
			boolean writeHeader = true;
			for (T o : instances) {
				if(writeHeader){
					out.write( o.getReportHeader() + LINE_DELIM );
					writeHeader=false;
				}
                out.write( o.getReportLine()   + LINE_DELIM);
			}

		} finally {
			out.close();
		}
		return file;

	}

	// ---------------------------------

	public List<T> getReportResults() {
		return reportResults;
	}

	public void setReportResults(List<T> reportResults) {
		this.reportResults = reportResults;
	}

}
