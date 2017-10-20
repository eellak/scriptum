/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.eprotocol.util.Permission.*;
import static gr.scriptum.eprotocol.vm.ReportsVM.Type.*;
import static java.lang.Boolean.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import eu.bitwalker.useragentutils.UserAgent;
import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.DepartmentTypeDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.ProtocolDAO.StatisticDirection;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.eprotocol.converter.DepartmentStringConverter;
import gr.scriptum.eprotocol.converter.DocumentTypeStringConverter2;
import gr.scriptum.eprotocol.converter.UserHierarchyStringConverter;
import gr.scriptum.eprotocol.csv.BaseCSVConverter;
import gr.scriptum.eprotocol.csv.StatisticsConverter;
import gr.scriptum.eprotocol.pdf.EProtocolPdfPrinter;
import gr.scriptum.eprotocol.pdf.ProtocolBookInfo;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.eprotocol.service.ReportsService;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.util.Permission;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReportsVM extends BaseVM {

	private static Log log = LogFactory.getLog(ReportsVM.class);

	public static final String PAGE = "reports.zul";

	public enum Type {
		CREATOR_REPORT, RECIPIENT_REPORT, ROUTING_REPORT, ASSIGNEES_REPORT;
	}

	/* reports related */
	private Type[] types;

	private Type type;

	private Date from;

	private Date to;

	private Date deliveryDateFrom;

	private Date deliveryDateTo;

	private Date routingDateFrom;

	private Date routingDateTo;

	private Integer numberFrom;

	private Integer numberTo;

	private Boolean incoming;

	private Boolean outgoing;

	private List<ProtocolBook> protocolBooks;

	private ProtocolBook protocolBook;

	private EntitySelection<Department> entitySelection;

	private EntitySelection<Department> unitDepartmentSelection;

	private EntitySelection<UserHierarchy> employeeSelection;

	private String sortBy = null;

	private String sortOrder = null;

	/* statistics related */
	private EntitySelection<StatisticDirection> statisticsDirectionSelection;

	private Date statisticsFrom;

	private Date statisticsTo;

	private Boolean statisticsFromToGrouping;

	private ProtocolBook statisticsProtocolBook;

	private EntitySelection<Department> statisticsDepartmentSelection;

	private EntitySelection<DocumentType> statisticDocumentTypeSelection;

	/* non accessible fields */
	private DepartmentType departmentType;

	@WireVariable
	private ParameterDAO parameterDAO;

	@WireVariable
	private ProtocolBookDAO protocolBookDAO;

	@WireVariable
	private ProtocolBookService protocolBookService;

	@WireVariable
	private ProtocolDAO protocolDAO;

	@WireVariable
	private DepartmentTypeDAO departmentTypeDAO;

	@WireVariable
	private ReportsService reportsService;

	@WireVariable
	private PermissionDAO permissionDAO;

	@WireVariable
	private DocumentTypeDAO documentTypeDAO;

	private void initReportCriteria() {
		for (ProtocolBook protocolBook : protocolBooks) {
			// pre-select first 'PUBLIC' book
			if (protocolBook.getType().equals(ProtocolBookType.PUBLIC)) {
				this.protocolBook = protocolBook;
				break;
			}
		}
		if (protocolBook == null && !protocolBooks.isEmpty()) {
			protocolBook = protocolBooks.get(0);
		}

		initEntitySelection();
		initUnitDepartmentSelection();
		initEmployeeSelection();

		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		from = type.equals(ROUTING_REPORT) || type.equals(RECIPIENT_REPORT) || type.equals(ASSIGNEES_REPORT) ? null
				: today;
		to = type.equals(ROUTING_REPORT) || type.equals(RECIPIENT_REPORT) || type.equals(ASSIGNEES_REPORT) ? null
				: today;
		deliveryDateFrom = today;
		deliveryDateTo = today;
		routingDateFrom = today;
		routingDateTo = today;
		numberFrom = null;
		numberTo = null;
		incoming = TRUE;
		outgoing = TRUE;
		sortBy = "protocolYear,protocolNumber";
		sortOrder = "ascending";
	}

	private void initStatisticsCriteria() {
		for (ProtocolBook protocolBook : protocolBooks) {
			// pre-select first 'PUBLIC' book
			if (protocolBook.getType().equals(ProtocolBookType.PUBLIC)) {
				this.statisticsProtocolBook = protocolBook;
				break;
			}
		}
		if (statisticsProtocolBook == null && !protocolBooks.isEmpty()) {
			statisticsProtocolBook = protocolBooks.get(0);
		}

		statisticDocumentTypeSelection = new EntitySelection<>();
		statisticDocumentTypeSelection.setActive(FALSE);

		if (statisticsProtocolBook != null) {
			selectStatisticsProtocolBook();
		}

		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		statisticsFrom = today;
		statisticsTo = today;
		statisticsFromToGrouping = FALSE;
		statisticsDepartmentSelection = new EntitySelection<>();
		statisticsDepartmentSelection.setPageSize(PAGE_SIZE_DEFAULT);
		statisticsDepartmentSelection.setEntites(new ArrayList<>());
		if (!Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType())) {
			statisticsDepartmentSelection.getEntites().add(getUserInSessionDepartment());
			statisticsDepartmentSelection.setSelectedEntity(getUserInSessionDepartment());
		}

		statisticsDirectionSelection = new EntitySelection<StatisticDirection>();
		statisticsDirectionSelection.setActive(FALSE);
		statisticsDirectionSelection.setEntites(Arrays.asList(StatisticDirection.values()));
		statisticsDirectionSelection.setSelectedEntity(StatisticDirection.ALL);
	}

	private void initEntitySelection() {
		entitySelection = new EntitySelection<Department>();
		entitySelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private void initUnitDepartmentSelection() {
		unitDepartmentSelection = new EntitySelection<Department>();
		unitDepartmentSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private void initEmployeeSelection() {
		employeeSelection = new EntitySelection<UserHierarchy>();
		employeeSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private void initstatisticsDepartmentSelection() {
		statisticsDepartmentSelection = new EntitySelection<Department>();
		statisticsDepartmentSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private Object[] searchEntities(String term, Integer startIndex, Integer pageSize) {
		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);

		// set up paging by counting records first
		Integer departmentsTotalSize = departmentDAO.countByTermAndType(term, departmentType).intValue();

		List<Department> departments = departmentDAO.findByTermAndType(term, departmentType, startIndex, pageSize);
		return new Object[] { departmentsTotalSize, departments };
	}

	@Init
	public void init() {

		departmentType = new DepartmentType();
		departmentType.setName(IConstants.DEPARTMENT_TYPE_CENTRAL_ADMINISTRATION_NAME);
		List<DepartmentType> departmentTypes = departmentTypeDAO.findByExample(departmentType);
		departmentType = departmentTypes.get(0);

		if (SecurityUtil.isAllGranted(BATCH_ROUTE.toString())) {
			types = Type.values();
		} else {
			types = new Type[] { CREATOR_REPORT, RECIPIENT_REPORT, ASSIGNEES_REPORT };
		}
		type = CREATOR_REPORT;

		gr.scriptum.domain.Permission searchPermission = new gr.scriptum.domain.Permission();
		searchPermission.setName(Permission.SEARCH_BOOK.toString());
		searchPermission = permissionDAO.findByExample(searchPermission).get(0);
		protocolBooks = protocolBookService.findByAssignedUser(getUserInSession(), searchPermission);

		initReportCriteria();
		initStatisticsCriteria();
	}

	@Command
	@NotifyChange("*")
	public void selectReportType() {
		initReportCriteria();
	}

	@Command
	@NotifyChange("*")
	public void selectProtocolBook() {
		// nothing to do
	}

	@Command
	@NotifyChange("entitySelection")
	public void searchEntitiesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		entitySelection.setTerm(StringUtils.trimToNull(event.getValue()));
		entitySelection.setActivePage(0);
		Object[] results = searchEntities(entitySelection.getTerm(), 0, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("entitySelection")
	public void listEntities() {
		if (entitySelection.getEntites() != null && !entitySelection.getEntites().isEmpty()) {
			return;
		}
		entitySelection.setTerm("");
		entitySelection.setActivePage(0);
		Object[] results = searchEntities(entitySelection.getTerm(), 0, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("entitySelection")
	public void clearEntities() {
		entitySelection.setEntites(null);
		entitySelection.setTerm(null);
		entitySelection.setTotalSize(0);
		entitySelection.setActivePage(0);
	}

	@Command
	@NotifyChange("entitySelection")
	public void pageEntities() {
		int startIndex = entitySelection.getActivePage() * entitySelection.getPageSize();
		Object[] results = searchEntities(entitySelection.getTerm(), startIndex, entitySelection.getPageSize());
		entitySelection.setTotalSize((Integer) results[0]);
		entitySelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange({ "entity", "entitySelection" })
	public void selectEntity() {
		entitySelection.setEntites(null);
		entitySelection.setTerm(null);
		entitySelection.setTotalSize(0);
		entitySelection.setActivePage(0);
	}

	@Command
	@NotifyChange("unitDepartmentSelection")
	public void searchUnitDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		unitDepartmentSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		unitDepartmentSelection.setActivePage(0);
		Object[] results = searchUnitDepartments(unitDepartmentSelection.getTerm(), getUserInSessionDepartment(), 0,
				unitDepartmentSelection.getPageSize());
		unitDepartmentSelection.setTotalSize((Integer) results[0]);
		unitDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("unitDepartmentSelection")
	public void listUnitDepartments() {
		if (unitDepartmentSelection.getEntites() != null && !unitDepartmentSelection.getEntites().isEmpty()) {
			return;
		}
		unitDepartmentSelection.setTerm("");
		unitDepartmentSelection.setActivePage(0);
		Object[] results = searchUnitDepartments(unitDepartmentSelection.getTerm(), getUserInSessionDepartment(), 0,
				unitDepartmentSelection.getPageSize());
		unitDepartmentSelection.setTotalSize((Integer) results[0]);
		unitDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("unitDepartmentSelection")
	public void clearUnitDepartments() {
		unitDepartmentSelection.setEntites(null);
		unitDepartmentSelection.setTerm(null);
		unitDepartmentSelection.setTotalSize(0);
		unitDepartmentSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("unitDepartmentSelection")
	public void pageUnitDepartments() {
		int startIndex = unitDepartmentSelection.getActivePage() * unitDepartmentSelection.getPageSize();
		Object[] results = searchUnitDepartments(unitDepartmentSelection.getTerm(), getUserInSessionDepartment(),
				startIndex, unitDepartmentSelection.getPageSize());
		unitDepartmentSelection.setTotalSize((Integer) results[0]);
		unitDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("unitDepartmentSelection")
	public void selectUnitDepartment() {
		unitDepartmentSelection.setEntites(null);
		unitDepartmentSelection.setTerm(null);
		unitDepartmentSelection.setTotalSize(0);
		unitDepartmentSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("employeeSelection")
	public void searchEmployeesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		employeeSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		employeeSelection.setActivePage(0);
		Object[] results = searchEmployees(employeeSelection.getTerm(), getUserInSessionDepartment(), 0,
				employeeSelection.getPageSize());
		employeeSelection.setTotalSize((Integer) results[0]);
		employeeSelection.setEntites((List<UserHierarchy>) results[1]);
	}

	@Command
	@NotifyChange("employeeSelection")
	public void listEmployees() {
		if (employeeSelection.getEntites() != null && !employeeSelection.getEntites().isEmpty()) {
			return;
		}
		employeeSelection.setTerm("");
		employeeSelection.setActivePage(0);
		Object[] results = searchEmployees(employeeSelection.getTerm(), getUserInSessionDepartment(), 0,
				employeeSelection.getPageSize());
		employeeSelection.setTotalSize((Integer) results[0]);
		employeeSelection.setEntites((List<UserHierarchy>) results[1]);
	}

	@Command
	@NotifyChange("employeeSelection")
	public void clearEmployees() {
		employeeSelection.setEntites(null);
		employeeSelection.setTerm(null);
		employeeSelection.setTotalSize(0);
		employeeSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("employeeSelection")
	public void pageEmployees() {
		int startIndex = employeeSelection.getActivePage() * employeeSelection.getPageSize();
		Object[] results = searchEmployees(employeeSelection.getTerm(), getUserInSessionDepartment(), startIndex,
				employeeSelection.getPageSize());
		employeeSelection.setTotalSize((Integer) results[0]);
		employeeSelection.setEntites((List<UserHierarchy>) results[1]);
	}

	@Command
	@NotifyChange("employeeSelection")
	public void selectEmployee() {
		employeeSelection.setEntites(null);
		employeeSelection.setTerm(null);
		employeeSelection.setTotalSize(0);
		employeeSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("*")
	public void exportProtocolBook(@ContextParam(ContextType.VIEW) Component win) {

		validateFields(win);

		if (type.equals(ASSIGNEES_REPORT) && !(unitDepartmentSelection.getSelectedEntity() != null
				^ employeeSelection.getSelectedEntity() != null)) {
			Messagebox.show(Labels.getLabel("reportsPage.invalidAssigneeSelection"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!incoming && !outgoing) {
			incoming = TRUE;
			outgoing = TRUE;
		}

		String[] tokens = sortBy.split(IConstants.SORTING_DELIMITER);
		List<Order> sortBy = new LinkedList<Order>();
		for (String token : tokens) {
			Order order = null;
			String sortOrder = this.sortOrder;
			if (sortOrder.equals("ascending")) {
				order = Order.asc(token);
			} else {
				order = Order.desc(token);
			}
			sortBy.add(order);
		}

		List<ProtocolBook> protocolBooksToSearchIn = new ArrayList<ProtocolBook>();
		protocolBooksToSearchIn.add(protocolBook);
		List<ProtocolNode> protocols = new ArrayList<ProtocolNode>();
		switch (type) {

		case CREATOR_REPORT:
			protocols.addAll(reportsService.getCreatorReport(getUserInSessionDepartment(), from, to, incoming, outgoing,
					numberFrom, numberTo, protocolBooksToSearchIn, sortBy));
			break;

		case RECIPIENT_REPORT:
			protocols.addAll(reportsService.getRecipientReport(getUserInSessionDepartment(), from, to, incoming,
					outgoing, deliveryDateFrom, deliveryDateTo, protocolBooksToSearchIn, sortBy));
			break;

		case ROUTING_REPORT:
			protocols.addAll(reportsService.getRoutingReport(entitySelection.getSelectedEntity(), from, to,
					routingDateFrom, routingDateTo, sortBy));
			break;

		case ASSIGNEES_REPORT:
			protocols.addAll(reportsService.getAssigneeReport(getUserInSessionDepartment(),
					unitDepartmentSelection.getSelectedEntity(), employeeSelection.getSelectedEntity(), from, to,
					incoming, outgoing, deliveryDateFrom, deliveryDateTo, protocolBooksToSearchIn, sortBy));
			break;

		default:
			protocols = new ArrayList<ProtocolNode>();
			break;
		}

		String bookInfoCompany = null;
		String title = null;
		switch (type) {
		case ROUTING_REPORT:
			DepartmentStringConverter converter = new DepartmentStringConverter();
			bookInfoCompany = converter.coerceToUi(entitySelection.getSelectedEntity(), null, null);
			title = Labels.getLabel(type.toString());
			break;
		case ASSIGNEES_REPORT:
			if (unitDepartmentSelection.getSelectedEntity() != null) {
				converter = new DepartmentStringConverter();
				bookInfoCompany = converter.coerceToUi(unitDepartmentSelection.getSelectedEntity(), null, null);
			} else if (employeeSelection.getSelectedEntity() != null) {
				UserHierarchyStringConverter userHierarchyStringConverter = new UserHierarchyStringConverter();
				bookInfoCompany = userHierarchyStringConverter.coerceToUi(employeeSelection.getSelectedEntity(), null,
						null);
			}
			title = Labels.getLabel(type.toString()) + " - " + Labels.getLabel("protocolBook") + ": "
					+ protocolBook.getProtocolSeries();
			break;
		default:
			bookInfoCompany = parameterDAO.getAsString(IConstants.PARAM_PROTOCOL_BOOK_COMPANY);
			title = Labels.getLabel(type.toString()) + " - " + Labels.getLabel("protocolBook") + ": "
					+ protocolBook.getProtocolSeries();
			break;
		}

		ProtocolBookInfo bookInfo = new ProtocolBookInfo();
		switch (type) {
		case ROUTING_REPORT:
			bookInfo.setStartDate(routingDateFrom);
			bookInfo.setStopDate(routingDateTo);
			break;
		case ASSIGNEES_REPORT:
		case RECIPIENT_REPORT:
			bookInfo.setStartDate(deliveryDateFrom);
			bookInfo.setStopDate(deliveryDateTo);
			break;
		default:
			bookInfo.setStartDate(from);
			bookInfo.setStopDate(to);
			break;
		}
		bookInfo.setCompany(bookInfoCompany);
		String name = StringUtils.trimToNull(StringUtils.trimToEmpty(getUserInSession().getName()) + " "
				+ StringUtils.trimToEmpty(getUserInSession().getSurname()));
		bookInfo.setAuthor(name != null ? name : getUserInSession().getUsername());
		bookInfo.setCreator(name != null ? name : getUserInSession().getUsername());
		// bookInfo.setCsvKeywords(bookInfoKeywords);
		// bookInfo.setSubject(bookInfoSubject);
		String reportFolder = parameterDAO.getAsString(IConstants.PARAM_REPORT_FOLDER);
		bookInfo.setTitle(title);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		File reportFile = new File(reportFolder,
				type + "_" + df.format(new Date()) + "_" + getUserInSession().getUsername() + ".pdf");
		bookInfo.setFile(reportFile.getAbsolutePath());

		EProtocolPdfPrinter printer = new EProtocolPdfPrinter(bookInfo, protocols);
		try {
			File file = printer.produceProtocolBook();
			Filedownload.save(file, "application/pdf");

			// Messagebox.show(Labels.getLabel("reportsPage.exportSuccess"),
			// Labels.getLabel("success.title"),
			// Messagebox.OK, Messagebox.INFORMATION);

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("reportsPage.exportError"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}

	@Command
	@NotifyChange("*")
	public void clear() {
		initReportCriteria();
	}

	/* statistics related */
	@Command
	@NotifyChange("statisticsDocumentTypeSelection")
	public void selectStatisticsProtocolBook() {
		statisticsProtocolBook = protocolBookDAO.merge(statisticsProtocolBook);

		List<DocumentType> statisticsDocumentTypes = new ArrayList<>();
		DocumentType documentType = new DocumentType();
		documentType.setName(Labels.getLabel("selectAll"));
		statisticsDocumentTypes.add(documentType);
		List<DocumentType> documentTypes = documentTypeDAO.findByProtocolBook(statisticsProtocolBook);
		statisticsDocumentTypes.addAll(documentTypes);
		statisticDocumentTypeSelection.setEntites(statisticsDocumentTypes);
		statisticDocumentTypeSelection.setSelectedEntity(statisticsDocumentTypes.get(0));
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void clearStatisticsDepartments() {
		statisticsDepartmentSelection.setActive(FALSE);
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void searchStatisticsDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		statisticsDepartmentSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		statisticsDepartmentSelection.setSelectedEntity(null);
		statisticsDepartmentSelection.setActivePage(0);
		Object[] results = searchProtocolDepartments(statisticsDepartmentSelection.getTerm(), 0,
				statisticsDepartmentSelection.getPageSize());
		statisticsDepartmentSelection.setTotalSize((Integer) results[0]);
		statisticsDepartmentSelection.setEntites((List<Department>) results[1]);
		statisticsDepartmentSelection.setActive(statisticsDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void listStatisticsDepartments() {
		// if (statisticsDepartmentSelection.getEntites() != null &&
		// !statisticsDepartmentSelection.getEntites().isEmpty()) {
		// return;
		// }
		statisticsDepartmentSelection.setTerm("");
		statisticsDepartmentSelection.setActivePage(0);
		Object[] results = searchProtocolDepartments(statisticsDepartmentSelection.getTerm(), 0,
				statisticsDepartmentSelection.getPageSize());
		statisticsDepartmentSelection.setTotalSize((Integer) results[0]);
		statisticsDepartmentSelection.setEntites((List<Department>) results[1]);
		statisticsDepartmentSelection.setActive(statisticsDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void pageStatisticsDepartments() {
		int startIndex = statisticsDepartmentSelection.getActivePage() * statisticsDepartmentSelection.getPageSize();
		Object[] results = searchProtocolDepartments(statisticsDepartmentSelection.getTerm(), startIndex,
				statisticsDepartmentSelection.getPageSize());
		statisticsDepartmentSelection.setTotalSize((Integer) results[0]);
		statisticsDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void selectStatisticsDepartment() {
		statisticsDepartmentSelection.setActive(FALSE);
	}

	@Command
	@NotifyChange("statisticsDepartmentSelection")
	public void clearStatisticsDepartment() {
		statisticsDepartmentSelection.setSelectedEntity(null);
		statisticsDepartmentSelection.setActive(FALSE);
	}

	@Command
	@NotifyChange("*")
	public void clearStatistics() {
		initStatisticsCriteria();
	}

	@Command
	@NotifyChange("*")
	public void exportStatistics(@ContextParam(ContextType.VIEW) Component win) {
		validateFields(win);

		List<Object[]> protocolCountByCriteria = reportsService.getProtocolCountByCriteria(statisticsProtocolBook,
				statisticsFrom, statisticsTo, statisticsFromToGrouping,
				statisticsDepartmentSelection.getSelectedEntity(),
				statisticDocumentTypeSelection.getActive() ? statisticDocumentTypeSelection.getSelectedEntity() : null,
				statisticsDirectionSelection.getActive() ? statisticsDirectionSelection.getSelectedEntity() : null);

		// post process labels
		if (statisticsDirectionSelection.getActive()) {
			for (Object[] results : protocolCountByCriteria) {
				results[results.length - 2] = "\""+Labels.getLabel(results[results.length - 2].toString())+"\"";
			}
		}
		// post process data
		DocumentTypeStringConverter2 converter2 = new DocumentTypeStringConverter2();
		for (Object[] row : protocolCountByCriteria) {
			//convert department code to string
			row[0] = "\""+row[0]+"\"";
//			row[1] = "\"=\"\""+row[1]+"\"\"\"";
			row[1] = "\"Κ"+row[1]+"\"";
			//replace document type id with actual label
			if (statisticDocumentTypeSelection.getActive()) {
				DocumentType documentType = documentTypeDAO.findById((Integer) row[2], false);
				row[2] = "\""+converter2.coerceToUi(documentType, null, null)+"\"";
			}
		}

		// add headers
		Object[] headers = { Labels.getLabel("department"), Labels.getLabel("department.code") };
		if (statisticsFromToGrouping) {
			headers = ArrayUtils.add(headers, Labels.getLabel("reportsPage.statistics.header.year"));
			headers = ArrayUtils.add(headers, Labels.getLabel("reportsPage.statistics.header.month"));
		}
		if (statisticDocumentTypeSelection.getActive()) {
			headers = ArrayUtils.add(headers, Labels.getLabel("documentType"));
		}
		if (statisticsDirectionSelection.getActive()) {
			headers = ArrayUtils.add(headers, Labels.getLabel("reportsPage.statistics.header.direction"));
		}
		headers = ArrayUtils.add(headers, Labels.getLabel("reportsPage.statistics.header.count"));

		protocolCountByCriteria.add(0, headers);

		String reportFolder = parameterDAO.getAsString(IConstants.PARAM_REPORT_FOLDER);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		File statisticsFile = new File(reportFolder,
				protocolBook.getProtocolSeries() + "_" + df2.format(statisticsFrom) + "_" + df2.format(statisticsTo)
						+ "_" + getUserInSession().getUsername() + "_" + df.format(new Date()) + ".csv");
		StatisticsConverter statisticsConverter = new StatisticsConverter();

		String clientOS = null;
		try {
			UserAgent userAgent = UserAgent.parseUserAgentString(Executions.getCurrent().getHeader("user-agent"));
			clientOS = userAgent.getOperatingSystem().getName();
			log.info(clientOS);
		} catch (Exception e) {
			log.warn(e);
		}

		String encoding = (clientOS == null || clientOS.startsWith("Windows")) ? "Cp1253" : BaseCSVConverter.ENCODING;
		try {
			statisticsFile = statisticsConverter.exportTo(protocolCountByCriteria, statisticsFile.getPath(), encoding);
			Filedownload.save(statisticsFile, "text/csv");
		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("reportsPage.exportError"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

	}

	public boolean isStatisticsDepartmentSelectionEnabled() {
		if (SecurityUtil.isNoneGranted(Permission.EXPORT_REPORTS_ALL_DEPARTMENTS.toString())) {
			return false;
		}
		return true;
	}

	public Type[] getTypes() {
		return types;
	}

	public void setTypes(Type[] types) {
		this.types = types;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Date getDeliveryDateFrom() {
		return deliveryDateFrom;
	}

	public void setDeliveryDateFrom(Date deliveryDateFrom) {
		this.deliveryDateFrom = deliveryDateFrom;
	}

	public Date getDeliveryDateTo() {
		return deliveryDateTo;
	}

	public void setDeliveryDateTo(Date deliveryDateTo) {
		this.deliveryDateTo = deliveryDateTo;
	}

	public Date getRoutingDateFrom() {
		return routingDateFrom;
	}

	public void setRoutingDateFrom(Date routingDateFrom) {
		this.routingDateFrom = routingDateFrom;
	}

	public Date getRoutingDateTo() {
		return routingDateTo;
	}

	public void setRoutingDateTo(Date routingDateTo) {
		this.routingDateTo = routingDateTo;
	}

	public Integer getNumberFrom() {
		return numberFrom;
	}

	public void setNumberFrom(Integer numberFrom) {
		this.numberFrom = numberFrom;
	}

	public Integer getNumberTo() {
		return numberTo;
	}

	public void setNumberTo(Integer numberTo) {
		this.numberTo = numberTo;
	}

	public Boolean getIncoming() {
		return incoming;
	}

	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	public Boolean getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(Boolean outgoing) {
		this.outgoing = outgoing;
	}

	public List<ProtocolBook> getProtocolBooks() {
		return protocolBooks;
	}

	public void setProtocolBooks(List<ProtocolBook> protocolBooks) {
		this.protocolBooks = protocolBooks;
	}

	public ProtocolBook getProtocolBook() {
		return protocolBook;
	}

	public void setProtocolBook(ProtocolBook protocolBook) {
		this.protocolBook = protocolBook;
	}

	public EntitySelection<Department> getEntitySelection() {
		return entitySelection;
	}

	public void setEntitySelection(EntitySelection<Department> entitySelection) {
		this.entitySelection = entitySelection;
	}

	public EntitySelection<Department> getUnitDepartmentSelection() {
		return unitDepartmentSelection;
	}

	public void setUnitDepartmentSelection(EntitySelection<Department> unitDepartmentSelection) {
		this.unitDepartmentSelection = unitDepartmentSelection;
	}

	public EntitySelection<UserHierarchy> getEmployeeSelection() {
		return employeeSelection;
	}

	public void setEmployeeSelection(EntitySelection<UserHierarchy> employeeSelection) {
		this.employeeSelection = employeeSelection;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public EntitySelection<StatisticDirection> getStatisticsDirectionSelection() {
		return statisticsDirectionSelection;
	}

	public void setStatisticsDirectionSelection(EntitySelection<StatisticDirection> statisticsDirectionSelection) {
		this.statisticsDirectionSelection = statisticsDirectionSelection;
	}

	public Date getStatisticsFrom() {
		return statisticsFrom;
	}

	public void setStatisticsFrom(Date reportFrom) {
		this.statisticsFrom = reportFrom;
	}

	public Date getStatisticsTo() {
		return statisticsTo;
	}

	public void setStatisticsTo(Date reportTo) {
		this.statisticsTo = reportTo;
	}

	public Boolean getStatisticsFromToGrouping() {
		return statisticsFromToGrouping;
	}

	public void setStatisticsFromToGrouping(Boolean statisticsFromToActive) {
		this.statisticsFromToGrouping = statisticsFromToActive;
	}

	public ProtocolBook getStatisticsProtocolBook() {
		return statisticsProtocolBook;
	}

	public void setStatisticsProtocolBook(ProtocolBook statisticsProtocolBook) {
		this.statisticsProtocolBook = statisticsProtocolBook;
	}

	public EntitySelection<Department> getStatisticsDepartmentSelection() {
		return statisticsDepartmentSelection;
	}

	public void setStatisticsDepartmentSelection(EntitySelection<Department> statisticsDepartmentSelection) {
		this.statisticsDepartmentSelection = statisticsDepartmentSelection;
	}

	public EntitySelection<DocumentType> getStatisticDocumentTypeSelection() {
		return statisticDocumentTypeSelection;
	}

	public void setStatisticDocumentTypeSelection(EntitySelection<DocumentType> statisticDocumentTypeSelection) {
		this.statisticDocumentTypeSelection = statisticDocumentTypeSelection;
	}

	public ReportsService getReportsService() {
		return reportsService;
	}

	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}
}
