/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolRelation.RelationType.*;
import static java.lang.Boolean.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import gr.scriptum.api.external.Entity;
import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.ProtocolCorrespondentDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.SubjectDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.Dossier;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ProtocolRelation;
import gr.scriptum.domain.ProtocolRelation.RelationType;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.service.LoggingService;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public class IncomingVM extends ProtocolVM {

	static Log log = LogFactory.getLog(IncomingVM.class);
	public static final String PAGE = "incoming.zul";

	public static final String PAGE_PRINT = "incomingPrint.zul";

	// TODO: parameterize this
	private CorrespondentType[] senderTypes = CorrespondentType.values();

	private CorrespondentAction[] correspondentActions = CorrespondentAction.values();

	private List<Contact> contacts = null;

	private Integer contactsTotalSize = null;

	private Integer contactsPageSize = PAGE_SIZE_DEFAULT;

	private Integer contactsActivePage = null;

	private List<DocumentType> documentTypes = null;

	private DocumentType documentType = null;

	private List<DistributionMethod> distributionMethods = null;

	private String defaultDistributionMethodId = null;

	private DistributionMethod defaultInternalRecipientDistributionMethod;

	private List<UserHierarchy> employees;

	private Integer employeesTotalSize;

	private Integer employeesPageSize = PAGE_SIZE_DEFAULT;

	private Integer employeesActivePage;

	private List<Company> companies;

	private Integer companiesTotalSize;

	private Integer companiesPageSize = PAGE_SIZE_DEFAULT;

	private Integer companiesActivePage;

	private List<Department> departments;

	private Integer departmentsTotalSize;

	private Integer departmentsPageSize = PAGE_SIZE_DEFAULT;

	private Integer departmentsActivePage;

	private List<Entity> activeMembers;

	private Integer activeMembersTotalSize;

	private Integer activeMembersPageSize = PAGE_SIZE_DEFAULT;

	private Integer activeMembersActivePage;

	private List<Department> internalRecipientDepartments;

	private Department internalRecipientDepartment;

	private Integer internalRecipientDepartmentsTotalSize;

	private Integer internalRecipientDepartmentsPageSize = PAGE_SIZE_DEFAULT;

	private Integer internalRecipientDepartmentsActivePage;

	private EntitySelection<CorrespondentGroup> internalRecipientGroupSelection;

	private List<ProtocolCorrespondent> internalRecipients;

	private ProtocolCorrespondent internalRecipient;

	private Boolean identicalNumberOutgoing = FALSE;

	private boolean protocolReceptionComplete = false;

	/* non-accessible fields */
	private List<ProtocolCorrespondent> internalRecipientsApplicableForReception;
	private List<ProtocolCorrespondent> internalRecipientsApplicableForRouting;
	private gr.scriptum.domain.Permission writeIncomingPermission;

	private void initNewProtocol() {
		// new protocol
		protocol = new Protocol(Direction.INCOMING);
		protocol.setUuid(UUID.randomUUID());
		protocol.setProtocolCorrespondents(new HashSet<ProtocolCorrespondent>());
		contacts = new ArrayList<Contact>();
		dossiers = new ArrayList<Dossier>();
		employees = new ArrayList<UserHierarchy>();
		companies = new ArrayList<Company>();
		departments = new ArrayList<Department>();
		activeMembers = new ArrayList<Entity>();
		transactors = new HashSet<ProtocolCorrespondent>();
		internalRecipientDepartments = new ArrayList<Department>();
		initInternalRecipientGroupSelection();
		internalRecipients = new ArrayList<ProtocolCorrespondent>();
		subjects = null;
		relativeProtocols = new ArrayList<Protocol>();
		subject = null;
		protocolDocuments = new LinkedList<ProtocolDocument>();
		protocolDocument = null;
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		term = null;
		dossierTerm = null;
		subjectTerm = null;
		relativeProtocolYear = Calendar.getInstance().get(Calendar.YEAR);
		for (DistributionMethod distributionMethod : distributionMethods) {
			if (distributionMethod.getCode() != null
					&& distributionMethod.getCode().equals(defaultDistributionMethodId)) {
				protocol.setDistributionMethod(distributionMethod);
				break;
			}
		}
		pertainsToSenderTypes = new CorrespondentType[] { CorrespondentType.ACTIVE_MEMBER,
				CorrespondentType.INACTIVE_MEMBER };

		// set protocol year to current
		Calendar calendar = Calendar.getInstance();
		protocol.setProtocolYear(calendar.get(Calendar.YEAR));
		// set protocol date to current
		protocol.setProtocolDate(calendar.getTime());
		// init protocol sender
		protocol.setSender(new ProtocolCorrespondent(protocol, CorrespondentDirection.SENDER, CorrespondentType.OTHER));

		// set department to current user's department
		department = getUserInSessionDepartment();

		initProtocolDocumentTypeSelection();

		populateApplicableProtocolBooks(writeIncomingPermission);
		if (protocolBooks.isEmpty()) {
			Messagebox.show(Labels.getLabel("protocolPage.noBooksAssignedToUser"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
		} else {
			// set first document type, applicable for this book
			DocumentTypeDAO documentTypeDAO = SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
			List<DocumentType> documentTypes = documentTypeDAO
					.findByProtocolBookAndApplicableType(protocol.getProtocolBook(), ApplicableType.INCOMING, 0, 1);
			protocol.setDocumentType(documentTypes.get(0));
		}

		pertainsToSenderType = CorrespondentType.ACTIVE_MEMBER;
		protocolRelation = null;
		dirty = false;
		log.info("Initialized new protocol, UUID/direction:" + protocol.getUuid() + "/" + protocol.getDirection());
	}

	private void initInternalRecipientGroupSelection() {
		internalRecipientGroupSelection = new EntitySelection<>();
		internalRecipientGroupSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private void initExistingProtocol() {
		protocol.setUuid(UUID.randomUUID());
		protocolDocuments = new LinkedList<ProtocolDocument>(protocol.getProtocolDocuments());
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		if (isProtocolPending()) {
			protocolBooks = new ArrayList<ProtocolBook>();
			protocolBooks.add(protocol.getProtocolBook());
		}
		initProtocolDocumentTypeSelection();
		transactors = protocol.getTransactors();
		internalRecipientDepartments = new ArrayList<>();
		initInternalRecipientGroupSelection();
		internalRecipients = new ArrayList<>(protocol.getInternalRecipients());
		internalRecipients.sort((r1, r2) -> {
			int codeCompare = StringUtils.trimToEmpty(r1.getCode()).compareTo(StringUtils.trimToEmpty(r2.getCode()));
			return codeCompare == 0 ? StringUtils.trimToEmpty(r1.getDescription())
					.compareTo(StringUtils.trimToEmpty(r2.getDescription())) : codeCompare;
		});
		department = protocol.getCreator() != null ? protocol.getCreator().getDepartment() : null;

		// filter internal recipients applicable for protocol routing
		if (SecurityUtil.isAllGranted(Permission.BATCH_ROUTE.toString()) && isProtocolSubmitted()) {
			ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
			internalRecipientsApplicableForRouting = protocolService
					.getInternalRecipientsApplicableForRouting(internalRecipients);
		}

		// filter internal recipients applicable for protocol reception
		Date today = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DATE);
		if (isProtocolSubmitted()) {
			internalRecipientsApplicableForReception = internalRecipients.stream()
					.filter(ir -> ir.getDepartment().equals(getUserInSessionDepartment())
							&& (ir.getDeliveryDate() == null
									|| (protocol.getAttachedNumber() != null && ir.getAttachedDeliveryDate() == null))
							&& ir.getRoutingDate() != null
							&& (today.equals(ir.getRoutingDate()) || today.after(ir.getRoutingDate())))
					.collect(Collectors.toCollection(ArrayList::new));

			// check if protocol reception is complete for current user's
			// department
			if (internalRecipientsApplicableForReception.isEmpty()) {
				protocolReceptionComplete = internalRecipients.stream().anyMatch(
						ir -> ir.getDepartment().equals(getUserInSessionDepartment()) && ir.getDeliveryDate() != null);
			}
		}
		relativeProtocols = new ArrayList<Protocol>();
		relativeProtocolYear = protocol.getProtocolYear();
		attachedNumberEditable = protocol.getAttachedNumber() != null ? true : false;
		dirty = false;
		log.info("Initialized existing protocol, UUID/number/id/direction:" + protocol.getUuid() + "/"
				+ protocol.getProtocolNumber() + "/" + protocol.getId() + "/" + protocol.getDirection());
	}

	private void editAssignees(Component view, boolean callback) {
		ProtocolCorrespondentDAO protocolCorrespondentDAO = SpringUtil.getApplicationContext()
				.getBean(ProtocolCorrespondentDAO.class);
		List<ProtocolCorrespondent> assigneesByDepartment = protocolCorrespondentDAO
				.findProtocolAssigneesByDepartment(protocol, getUserInSessionDepartment());

		// create modal window for assignees edit
		Map<String, Object> params = new HashMap<>();
		params.put(ReceptionVM.PARAM_PROTOCOL, protocol);
		params.put(ReceptionVM.PARAM_ASSIGNEES, assigneesByDepartment);
		if (callback) {
			params.put(IConstants.PARAM_CALLBACK, new Callback("refreshProtocol"));
		}

		Window assigneesWin = (Window) Executions.createComponents(ReceptionVM.PAGE, view, params);
		assigneesWin.setClosable(true);
		assigneesWin.doModal();
	}

	private void resetProtocolForResubmission() {
		protocol.setId(null);
		protocol.setProtocolNumber(null);
		protocol.setProtocolDocuments(new HashSet<>());
		Set<ProtocolCorrespondent> protocolCorrespondents = protocol.getProtocolCorrespondents();
		for (ProtocolCorrespondent protocolCorrespondent : protocolCorrespondents) {
			protocolCorrespondent.setId(null);
		}
		Set<ProtocolRelation> protocolRelationsAsSource = protocol.getProtocolRelationsAsSource();
		for (ProtocolRelation protocolRelation : protocolRelationsAsSource) {
			protocolRelation.setId(null);
		}
	}

	@Override
	protected void searchSubjects(Integer startIndex) {
		SubjectDAO subjectDAO = SpringUtil.getApplicationContext().getBean(SubjectDAO.class);
		// set up paging by counting records first
		subjectsTotalSize = subjectDAO.countByTermAndProtocolBookAndApplicableTo(subjectTerm,
				protocol.getProtocolBook(), ApplicableType.INCOMING);
		subjects = subjectDAO.findByTermAndProtocolBookAndApplicableTo(subjectTerm, protocol.getProtocolBook(),
				ApplicableType.INCOMING, startIndex, subjectsPageSize);
	}

	@Init
	public void init() throws Exception {

		// setup callback, if any
		Execution execution = Executions.getCurrent();
		Map<?, ?> arg = execution.getArg();
		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK);

		if (arg.containsKey(PARAM_DISPLAY_ONLY)) {
			displayOnly = (boolean) arg.get(PARAM_DISPLAY_ONLY);
		}

		ParameterDAO parameterDAO = SpringUtil.getApplicationContext().getBean(ParameterDAO.class);
		defaultDistributionMethodId = parameterDAO
				.getAsString(IConstants.PARAM_DEFAULT_INCOMING_DISTRIBUTION_METHOD_CODE);

		DistributionMethodDAO distributionMethodDAO = SpringUtil.getApplicationContext()
				.getBean(DistributionMethodDAO.class);
		distributionMethods = distributionMethodDAO.findAll();
		String defaultInternalRecipientDistributionMethodCode = parameterDAO
				.getAsString(IConstants.PARAM_DEFAULT_INTERNAL_RECIPIENT_DISTRIBUTION_METHOD_CODE);
		if (defaultInternalRecipientDistributionMethodCode == null) {
			throw new RuntimeException("No default distribution method set for internal recipients");
		}
		defaultInternalRecipientDistributionMethod = new DistributionMethod();
		defaultInternalRecipientDistributionMethod.setCode(defaultInternalRecipientDistributionMethodCode);
		List<DistributionMethod> results = distributionMethodDAO
				.findByExample(defaultInternalRecipientDistributionMethod);
		if (results.size() != 1) {
			throw new RuntimeException("No default distribution method set for internal recipients");
		}
		defaultInternalRecipientDistributionMethod = results.get(0);

		PermissionDAO permissionDAO = SpringUtil.getApplicationContext().getBean(PermissionDAO.class);
		writeIncomingPermission = new gr.scriptum.domain.Permission();
		writeIncomingPermission.setName(Permission.WRITE_INCOMING.toString());
		writeIncomingPermission = permissionDAO.findByExample(writeIncomingPermission).get(0);

		// fetch existing protocol, if any
		// check if param came straight from URL (i.e. by calling the incoming
		// protocol page directly)
		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString == null && arg.containsKey(IConstants.PARAM_KEY_ID)) {
			// check if param came from another component (i.e. by calling the
			// incoming protocol page as a modal window)
			idString = ((Integer) arg.get(IConstants.PARAM_KEY_ID)).toString();
		}
		if (idString != null) {
			ProtocolDAO incomingProtocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
			Integer protocolId = null;
			try {
				protocolId = Integer.valueOf(idString);
			} catch (Exception e) {
				log.error(e);
			}
			Protocol existingProtocol = null;
			if (protocolId != null) {
				log.info("Fetching protocol, id/direction:" + protocolId + "/INCOMING");
				try {
					existingProtocol = incomingProtocolDAO.findById(protocolId, false);
				} catch (Exception e) {
					log.error("Error fetching protocol, id/direction:" + protocolId + "/INCOMING", e);
					Messagebox.show(Labels.getLabel("error"), Labels.getLabel("error.title"), Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}

			if (existingProtocol == null) { // not found
				Messagebox.show(Labels.getLabel("fetch.notFound"), Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			protocol = existingProtocol;
		}

		// populate certain fields, both for new and existing protocols
		if (protocol == null) {
			initNewProtocol();
		} else {
			// existing protocol
			initExistingProtocol();
		}

		if (execution.getDesktop().getRequestPath().endsWith(PAGE_PRINT)) {
			log.info("Printing protocol, UUID/number/id/direction:" + protocol.getUuid() + "/"
					+ protocol.getProtocolNumber() + "/" + protocol.getId() + "/" + protocol.getDirection());
			organization = parameterDAO.getAsString(IConstants.PARAM_PROTOCOL_BOOK_COMPANY);
			Clients.print();
		}
	}

	@Command
	@NotifyChange("protocol")
	public void clearSender() {
		ProtocolCorrespondent sender = protocol.getSender();
		sender.setDescription(null);
		sender.setCode(null);
		sender.setVatNumber(null);
		sender.setSsn(null);
		sender.setDepartment(null);
		sender.setContact(null);
		sender.setCompany(null);
	}

	@Command
	@NotifyChange({ "contacts", "contactsTotalSize", "contactsActivePage" })
	public void searchContactsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());
		contactsActivePage = 0;
		Object[] results = searchContacts(term, 0, contactsPageSize);
		contactsTotalSize = (Integer) results[0];
		contacts = (List<Contact>) results[1];
	}

	@Command
	@NotifyChange({ "contacts", "contactsTotalSize", "contactsActivePage" })
	public void listContacts() {
		if (!contacts.isEmpty()) {
			return;
		}
		term = "";
		contactsActivePage = 0;
		Object[] results = searchContacts(term, 0, contactsPageSize);
		contactsTotalSize = (Integer) results[0];
		contacts = (List<Contact>) results[1];
	}

	@Command
	@NotifyChange({ "contacts", "contactsTotalSize", "contactsActivePage" })
	public void clearContacts() {
		contacts = new ArrayList<Contact>();
		contactsTotalSize = 0;
		contactsActivePage = 0;
	}

	@Command
	@NotifyChange({ "contacts", "contactsTotalSize", "contactsActivePage" })
	public void pageContacts() {
		int startIndex = contactsActivePage * contactsPageSize;
		Object[] results = searchContacts(term, startIndex, contactsPageSize);
		contactsTotalSize = (Integer) results[0];
		contacts = (List<Contact>) results[1];
	}

	@Command
	@NotifyChange({ "protocol", "contactFullName", "contacts", "contactsTotalSize", "contactsActivePage" })
	public void selectContact() {
		contacts = new ArrayList<Contact>();
		contactsTotalSize = 0;
		contactsActivePage = 0;
	}

	@Command
	public void addContact(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshContacts"));

		Window contactWin = (Window) Executions.createComponents(ContactVM.PAGE, view, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "protocol", "contacts", "contactFullName", "sender" })
	public void refreshContacts(@BindingParam("entity") Contact contact) {
		protocol.setSender(contact);
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void searchEmployeesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			employees = new ArrayList<UserHierarchy>();
			return;
		}
		employeesActivePage = 0;
		Object[] results = searchEmployees(term, 0, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void listEmployees() {
		if (!employees.isEmpty()) {
			return;
		}
		term = "";
		employeesActivePage = 0;
		Object[] results = searchEmployees(term, 0, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void clearEmployees() {
		employees = new ArrayList<UserHierarchy>();
		employeesTotalSize = 0;
		employeesActivePage = 0;
	}

	@Command
	@NotifyChange({ "employees", "employeesTotalSize", "employeesActivePage" })
	public void pageEmployees() {
		int startIndex = employeesActivePage * employeesPageSize;
		Object[] results = searchEmployees(term, startIndex, employeesPageSize);
		employeesTotalSize = (Integer) results[0];
		employees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "protocol", "employees", "employeesTotalSize", "employeesActivePage" })
	public void selectEmployee() {
		employees = new ArrayList<UserHierarchy>();
		employeesTotalSize = 0;
		employeesActivePage = 0;
	}

	@Command
	@NotifyChange({ "companies", "companiesTotalSize", "companiesActivePage" })
	public void searchCompaniesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			companies = new ArrayList<Company>();
			return;
		}
		companiesActivePage = 0;
		Object[] results = searchCompanies(term, 0, companiesPageSize);
		companiesTotalSize = (Integer) results[0];
		companies = (List<Company>) results[1];
	}

	@Command
	@NotifyChange({ "companies", "companiesTotalSize", "companiesActivePage" })
	public void listCompanies() {
		if (!companies.isEmpty()) {
			return;
		}
		term = "";
		companiesActivePage = 0;
		Object[] results = searchCompanies(term, 0, companiesPageSize);
		companiesTotalSize = (Integer) results[0];
		companies = (List<Company>) results[1];
	}

	@Command
	@NotifyChange({ "companies", "companiesTotalSize", "companiesActivePage" })
	public void clearCompanies() {
		companies = new ArrayList<Company>();
		companiesTotalSize = 0;
		companiesActivePage = 0;
	}

	@Command
	@NotifyChange({ "companies", "companiesTotalSize", "companiesActivePage" })
	public void pageCompanies() {
		int startIndex = companiesActivePage * companiesPageSize;
		Object[] results = searchCompanies(term, startIndex, companiesPageSize);
		companiesTotalSize = (Integer) results[0];
		companies = (List<Company>) results[1];
	}

	@Command
	@NotifyChange({ "protocol", "companies", "companiesTotalSize", "companiesActivePage" })
	public void selectCompany() {
		companies = new ArrayList<Company>();
		companiesTotalSize = 0;
		companiesActivePage = 0;
	}

	@Command
	public void addCompany(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshCompanies"));

		Window contactWin = (Window) Executions.createComponents(CompanyVM.PAGE, view, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "protocol", "contacts", "contactFullName", "sender" })
	public void refreshCompanies(@BindingParam("entity") Company company) {
		protocol.setSender(company);
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void searchDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			departments = new ArrayList<Department>();
			return;
		}
		departmentsActivePage = 0;
		Object[] results = searchProtocolDepartments(term, 0, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void listDepartments() {
		if (!departments.isEmpty()) {
			return;
		}
		term = "";
		departmentsActivePage = 0;
		Object[] results = searchProtocolDepartments(term, 0, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void clearDepartments() {
		departments = new ArrayList<Department>();
		departmentsTotalSize = 0;
		departmentsActivePage = 0;
	}

	@Command
	@NotifyChange({ "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void pageDepartments() {
		int startIndex = departmentsActivePage * departmentsPageSize;
		Object[] results = searchProtocolDepartments(term, startIndex, departmentsPageSize);
		departmentsTotalSize = (Integer) results[0];
		departments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "protocol", "departments", "departmentsTotalSize", "departmentsActivePage" })
	public void selectDepartment() {
		departments = new ArrayList<Department>();
		departmentsTotalSize = 0;
		departmentsActivePage = 0;
	}

	@Command
	@NotifyChange("*")
	public void selectDistributionMethod() {
		protocol.setDistributionMethodDetails(null);
		setProtocolChanged();
	}

	@Command
	@NotifyChange({ "activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void prepareSearchActiveMembersByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());
		if (term == null) {
			activeMembers = new ArrayList<Entity>();
			return;
		}
	}

	@Command
	@NotifyChange({ "protocol", "activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void searchActiveMembersByTerm() {
		protocol.getSender().setCode(null);
		protocol.getSender().setDescription(null);
		activeMembersActivePage = 0;
		Object[] results = searchActiveMembers(term, 0, activeMembersPageSize);
		activeMembersTotalSize = (Integer) results[0];
		activeMembers = (List<Entity>) results[1];
		if (activeMembers.size() == 1) {
			ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
					CorrespondentDirection.SENDER, protocol.getSender().getType()); // already
																					// set
																					// to
																					// one
																					// of
																					// ACTIVE_MEMBER/INACTIVE_MEMBER
			Entity entity = activeMembers.get(0);
			protocolCorrespondent.setCode(entity.getId().toString());
			protocolCorrespondent.setDescription(entity.getName());
			protocol.setSender(protocolCorrespondent);
			activeMembers = new ArrayList<Entity>();
			activeMembersTotalSize = 0;
			activeMembersActivePage = 0;
		}
	}

	@Command
	@NotifyChange({ "activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void clearActiveMembers() {
		activeMembers = new ArrayList<Entity>();
		activeMembersTotalSize = 0;
		activeMembersActivePage = 0;
	}

	@Command
	@NotifyChange({ "activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void pageActiveMembers() {
		int startIndex = activeMembersActivePage * activeMembersPageSize;
		Object[] results = searchActiveMembers(term, startIndex, activeMembersPageSize);
		activeMembersTotalSize = (Integer) results[0];
		activeMembers = (List<Entity>) results[1];
	}

	@Command
	@NotifyChange({ "protocol", "activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void selectActiveMember(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		activeMembers = new ArrayList<Entity>();
		activeMembersTotalSize = 0;
		activeMembersActivePage = 0;
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol, CorrespondentDirection.SENDER,
				protocol.getSender().getType()); // already set to one of
													// ACTIVE_MEMBER/INACTIVE_MEMBER
		Entity entity = (Entity) event.getSelectedObjects().iterator().next();
		protocolCorrespondent.setCode(entity.getId().toString());
		protocolCorrespondent.setDescription(entity.getName());
		protocol.setSender(protocolCorrespondent);
	}

	@Command
	@NotifyChange("protocol")
	public void setSenderOther(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol, CorrespondentDirection.SENDER,
				CorrespondentType.OTHER); // already set to one of
		// ACTIVE_MEMBER/INACTIVE_MEMBER
		protocolCorrespondent.setDescription(event.getValue());
		protocol.setSender(protocolCorrespondent);
	}

	@Command
	@NotifyChange({ "internalRecipientDepartments", "internalRecipientDepartment",
			"internalRecipientDepartmentsTotalSize", "internalRecipientDepartmentsActivePage" })
	public void searchInternalRecipientDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			internalRecipientDepartments = new ArrayList<Department>();
			return;
		}
		internalRecipientDepartmentsActivePage = 0;
		Object[] results = searchProtocolDepartments(term, 0, internalRecipientDepartmentsPageSize);
		internalRecipientDepartmentsTotalSize = (Integer) results[0];
		internalRecipientDepartments = (List<Department>) results[1];
		internalRecipientDepartment = null;
	}

	@Command
	@NotifyChange({ "internalRecipientDepartments", "internalRecipientDepartment",
			"internalRecipientDepartmentsTotalSize", "internalRecipientDepartmentsActivePage" })
	public void listInternalRecipientDepartments() {
		if (!internalRecipientDepartments.isEmpty()) {
			return;
		}
		term = "";
		internalRecipientDepartmentsActivePage = 0;
		Object[] results = searchProtocolDepartments(term, 0, internalRecipientDepartmentsPageSize);
		internalRecipientDepartmentsTotalSize = (Integer) results[0];
		internalRecipientDepartments = (List<Department>) results[1];
		internalRecipientDepartment = null;
	}

	@Command
	@NotifyChange({ "internalRecipientDepartments", "internalRecipientDepartment",
			"internalRecipientDepartmentsTotalSize", "internalRecipientDepartmentsActivePage" })
	public void clearInternalRecipientDepartments() {
		internalRecipientDepartments = new ArrayList<Department>();
		internalRecipientDepartmentsTotalSize = 0;
		internalRecipientDepartmentsActivePage = 0;
		internalRecipientDepartment = null;
	}

	@Command
	@NotifyChange({ "internalRecipientDepartments", "internalRecipientDepartmentsTotalSize",
			"internalRecipientDepartmentsActivePage" })
	public void pageInternalRecipientDepartments() {
		int startIndex = internalRecipientDepartmentsActivePage * internalRecipientDepartmentsPageSize;
		Object[] results = searchProtocolDepartments(term, startIndex, internalRecipientDepartmentsPageSize);
		internalRecipientDepartmentsTotalSize = (Integer) results[0];
		internalRecipientDepartments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange("*")
	public void selectInternalRecipientDepartment() {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent internalRecipient = protocolService.getInternalRecipient(internalRecipientDepartment,
				protocol, defaultInternalRecipientDistributionMethod);
		if (!protocolService.internalRecipientFoundInList(internalRecipient, internalRecipients)) {
			internalRecipients.add(internalRecipient);
		}

		internalRecipientDepartments = new ArrayList<Department>();
		internalRecipientDepartmentsTotalSize = 0;
		internalRecipientDepartmentsActivePage = 0;
		internalRecipientDepartment = null;
		setProtocolChanged();
	}

	@Command
	@NotifyChange("internalRecipientGroupSelection")
	public void searchInternalRecipientGroupsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		internalRecipientGroupSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		internalRecipientGroupSelection.setActivePage(0);
		Object[] results = searchCorrespondentGroups(internalRecipientGroupSelection.getTerm(), INTERNAL_RECIPIENT, 0,
				internalRecipientGroupSelection.getPageSize());
		internalRecipientGroupSelection.setTotalSize((Integer) results[0]);
		internalRecipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange("internalRecipientGroupSelection")
	public void listInternalRecipientGroups() {
		if (internalRecipientGroupSelection.getEntites() != null
				&& !internalRecipientGroupSelection.getEntites().isEmpty()) {
			return;
		}
		internalRecipientGroupSelection.setTerm("");
		internalRecipientGroupSelection.setActivePage(0);
		Object[] results = searchCorrespondentGroups(internalRecipientGroupSelection.getTerm(), INTERNAL_RECIPIENT, 0,
				internalRecipientGroupSelection.getPageSize());
		internalRecipientGroupSelection.setTotalSize((Integer) results[0]);
		internalRecipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange("internalRecipientGroupSelection")
	public void clearInternalRecipientGroups() {
		internalRecipientGroupSelection.setEntites(new ArrayList<>());
		internalRecipientGroupSelection.setTotalSize(0);
		internalRecipientGroupSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("internalRecipientGroupSelection")
	public void pageInternalRecipientGroups() {
		int startIndex = internalRecipientGroupSelection.getActivePage()
				* internalRecipientGroupSelection.getPageSize();
		Object[] results = searchCorrespondentGroups(internalRecipientGroupSelection.getTerm(), INTERNAL_RECIPIENT,
				startIndex, internalRecipientGroupSelection.getPageSize());
		internalRecipientGroupSelection.setTotalSize((Integer) results[0]);
		internalRecipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange("*")
	public void selectInternalRecipientGroup() {
		CorrespondentGroup correspondentGroup = internalRecipientGroupSelection.getSelectedEntity();
		// refresh from database
		CorrespondentGroupDAO correspondentGroupDAO = SpringUtil.getApplicationContext()
				.getBean(CorrespondentGroupDAO.class);
		correspondentGroup = correspondentGroupDAO.merge(correspondentGroup);
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		Set<ProtocolCorrespondent> groupMembers = correspondentGroup.getProtocolCorrespondents();
		for (ProtocolCorrespondent groupMember : groupMembers) {
			ProtocolCorrespondent internalRecipient = protocolService.getInternalRecipient(groupMember.getDepartment(),
					protocol, defaultInternalRecipientDistributionMethod);
			if (!protocolService.internalRecipientFoundInList(internalRecipient, internalRecipients)) {
				internalRecipients.add(internalRecipient);
			}
		}
		initInternalRecipientGroupSelection();
		setProtocolChanged();
	}

	@Command
	@NotifyChange({ "internalRecipient", "internalRecipients" })
	public void removeInternalRecipient() {
		internalRecipients.remove(internalRecipient);
		internalRecipient = null;
	}

	@Command
	@NotifyChange("*")
	public void newProtocol() {
		initNewProtocol();
	}

	@Command
	@NotifyChange("*")
	@Deprecated
	public void saveProtocol(@ContextParam(ContextType.VIEW) Component view) throws Exception {

		// validate that protocol isn't already submitted
		if (protocol.getProtocolNumber() != null) {
			Messagebox.show(Labels.getLabel("incomingPage.protocolAlreadySubmitted"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (department == null) {
			Messagebox.show(Labels.getLabel("protocolPage.invalidDepartment"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		validateFields(view);

		protocol.setTransactors(transactors);
		protocol.setInternalRecipients(new HashSet<>(internalRecipients));
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		try {

			protocolService.saveIncomingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
					protocolDocumentsToBeDeleted, false);

			// clean up
			protocolDocumentsToBeDeleted.clear();

		} catch (Exception e) {
			log.error(e);
			if (e instanceof OpenKMException) {
				// TODO: handle communication error with OpenKM (invalidate
				// protocol maybe??? )
				Messagebox.show(Labels.getLabel("protocolPage.errorOpenKM"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show(Labels.getLabel("incomingPage.errorSaving"), Labels.getLabel("error.title"),
						Messagebox.OK, Messagebox.ERROR);
			}
			return;
		} finally {

			if (callback != null) {
				// notify caller (if any)
				if (callback.getCaller() != null) {
					// called by MVC controller
					Events.postEvent(callback.getEvent(), callback.getCaller(), protocol);
				} else {
					// called by MVVM view model
					Map<String, Object> args = new HashMap<>();
					args.put("entity", protocol);
					BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
				}
			}
		}

		Messagebox.show(Labels.getLabel("save.OK"), Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);
		return;

	}

	@Command
	@NotifyChange("*")
	public void insertProtocol(@ContextParam(ContextType.VIEW) Component view) throws InterruptedException {

		// validate that protocol isn't already submitted
		if (protocol.getProtocolNumber() != null) {
			Messagebox.show(Labels.getLabel("incomingPage.protocolAlreadySubmitted"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		validateFields(view);

		// validate protocol book
		if (protocol.getProtocolBook() == null) {
			Messagebox.show(Labels.getLabel("protocolPage.noProtocolBookSelected"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// validate sender
		if (protocol.getSender() == null) {
			Messagebox.show(Labels.getLabel("incomingPage.noSenderSelected"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// validate distribution method
		if (protocol.getDistributionMethod() == null) {
			Messagebox.show(Labels.getLabel("incomingPage.noDistributionMethodSelected"),
					Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// validate document type
		if (protocol.getDocumentType() == null) {
			Messagebox.show(Labels.getLabel("protocolPage.invalidDocumentType"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// validate internal recipients
		if (SecurityUtil.isAnyGranted(Permission.ASSIGN_INTERNAL_RECIPIENTS.toString())) {
			// user must assign at least one internal recipient, since he/she
			// bears the corresponding permission
			if (internalRecipients.isEmpty()) {
				Messagebox.show(Labels.getLabel("incomingPage.noInternalRecipientsAssigned"),
						Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}

		protocol.setTransactors(transactors);
		protocol.setInternalRecipients(new HashSet<>(internalRecipients));

		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		List<Protocol> alreadySubmittedProtocols = protocolService.findAlreadySubmittedProtocols(protocol, department);
		log.debug("Already submitted protocols:" + alreadySubmittedProtocols.size());
		if (!alreadySubmittedProtocols.isEmpty()) {
			Protocol alreadySubmittedProtocol = alreadySubmittedProtocols.get(0);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Messagebox.show(
					Labels.getLabel("incomingPage.protocolAlreadyExists",
							new String[] { alreadySubmittedProtocol.getFullNumber(),
									df.format(alreadySubmittedProtocol.getProtocolDate()) }),
					Labels.getLabel("warning.title"), Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION,
					(Event event) -> {
						if (((Integer) event.getData()).intValue() == Messagebox.YES) {

							Map<String, Object> params = new HashMap<String, Object>();
							params.put(PARAM_DISPLAY_ONLY, true);
							params.put(IConstants.PARAM_KEY_ID, alreadySubmittedProtocol.getId());
							Window relativeProtocolWin = (Window) Executions.createComponents(IncomingVM.PAGE, view,
									params);
							relativeProtocolWin.setClosable(true);
							relativeProtocolWin.doModal();
						}
					});
			return;
		}

		try {
			protocolService.saveIncomingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
					null, true);
		} catch (ObjectOptimisticLockingFailureException e) {
			log.warn("Could not get number for protocol, UUID:" + protocol.getUuid());
			resetProtocolForResubmission();
			Messagebox.show(Labels.getLabel("protocolPage.errorGettingProtocolNumber"),
					Labels.getLabel("warning.title"), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} catch (Exception e) {
			log.error("Error saving protocol, UUID:" + protocol.getUuid(), e);
			resetProtocolForResubmission();
			Messagebox.show(Labels.getLabel("incomingPage.errorSubmitting"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		} finally {
			if (callback != null) {
				// notify caller (if any)
				Map<String, Object> args = new HashMap<>();
				args.put("entity", protocol);
				BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
			}
		}

		if (getIsDocumentUploadFunctionalityEnabled()) {

			try {
				protocolService.uploadProtocolDocuments(protocol, getUserInSession(), getIp(), protocolDocuments);
				BindUtils.postGlobalCommand(null, null, "finishInsertIncomingProtocol", null);
			} catch (OpenKMException e) {
				log.error(e);

				List<ProtocolDocument> failedProtocolDocuments = new ArrayList<ProtocolDocument>();
				for (ProtocolDocument protocolDocument : protocolDocuments) {
					if (protocolDocument.getOkmUuid() == null) {
						failedProtocolDocuments.add(protocolDocument);
					}
				}

				Messagebox.show(Labels.getLabel("protocolPage.errorOpenKM.retry"), Labels.getLabel("confirm.title"),
						Messagebox.YES | Messagebox.NO, Messagebox.ERROR, event -> {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								try {
									protocolService.uploadProtocolDocuments(protocol, getUserInSession(), getIp(),
											failedProtocolDocuments);
								} catch (Exception e1) {
									log.error(e1);
									Messagebox.show(Labels.getLabel("protocolPage.errorOpenKM"),
											Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);

								} finally {
									BindUtils.postGlobalCommand(null, null, "finishInsertIncomingProtocol", null);
								}

							} else {
								BindUtils.postGlobalCommand(null, null, "finishInsertIncomingProtocol", null);
							}
						});
			}

		} else {
			BindUtils.postGlobalCommand(null, null, "finishInsertIncomingProtocol", null);
		}

	}

	@GlobalCommand
	@NotifyChange("*")
	public void finishInsertIncomingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		log.info("finishing protocol insertion, UUID/id/number/direction:" + protocol.getUuid() + "/" + protocol.getId()
				+ "/" + protocol.getProtocolNumber() + "/" + protocol.getDirection());

		// store some protocol details locale, before clearing everything, to
		// allow for printing
		final String fullNumber = protocol.getFullNumber();
		final Integer protocolId = protocol.getId();

		try {
			if (identicalNumberOutgoing) {
				// prompt user for outgoing protocol creation
				Messagebox.show(Labels.getLabel("incomingPage.protocolSubmitted.outgoing", new String[] { fullNumber }),
						Labels.getLabel("success.title"), Messagebox.OK, Messagebox.INFORMATION, (Event event) -> {

							initNewProtocol();
							BindUtils.postNotifyChange(null, null, IncomingVM.this, "*");

							// create modal window for selected protocol
							Map<String, Object> params = new HashMap<String, Object>();
							params.put(IConstants.PARAM_KEY_INCOMING_ID, protocolId);
							params.put(IConstants.PARAM_KEY_RELATION_TYPE, RelationType.IDENTICAL_NUMBER);
							// automatically close current incoming protocol
							// window, and attach new outgoing window to parent
							// view
							Component parent = view.getParent();
							view.detach();
							Window outgoingWin = (Window) Executions.createComponents(OutgoingVM.PAGE, parent, params);
							outgoingWin.setClosable(true);
							outgoingWin.doModal();
							return;
						});
			} else {
				// prompt user for printout

				Messagebox.Button[] buttons;
				String[] labels;
				Map<String, String> params = new HashMap<>();
				if (SecurityUtil.isAllGranted(Permission.ASSIGN_INTERNAL_RECIPIENTS.toString())) {
					buttons = new Messagebox.Button[] { Messagebox.Button.OK, Messagebox.Button.YES };
					labels = new String[] { Labels.getLabel("ok"), Labels.getLabel("print") };
					params.put("width", "400");
				} else {
					buttons = new Messagebox.Button[] { Messagebox.Button.OK, Messagebox.Button.YES,
							Messagebox.Button.RETRY };
					labels = new String[] { Labels.getLabel("ok"), Labels.getLabel("print"),
							Labels.getLabel("protocolPage.assignees") };
					params.put("width", "500");
				}

				Messagebox.show(Labels.getLabel("protocolPage.protocolSubmitted", new String[] { fullNumber }),
						Labels.getLabel("success.title"), buttons, labels, Messagebox.INFORMATION, null,
						(ClickEvent event) -> {
							if (event.getButton() == null) {
								return;
							}
							switch (event.getButton()) {
							case YES:
								Executions.getCurrent().sendRedirect(
										PAGE_PRINT + "?" + IConstants.PARAM_KEY_ID + "=" + protocolId, "_blank");
								break;
							case RETRY:
								editAssignees(view, false);
								break;
							default:
								break;
							}
							initNewProtocol();
							BindUtils.postNotifyChange(null, null, IncomingVM.this, "*");
							return;
						}, params);

			}
		} catch (Exception e) {
			// swallow
			log.warn(e);
		}
	}

	@Command
	@NotifyChange("*")
	public void updateProtocol(@ContextParam(ContextType.VIEW) Component view) throws Exception {

		validateFields(view);

		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		List<Protocol> alreadySubmittedProtocols = protocolService.findAlreadySubmittedProtocols(protocol, department);
		log.debug("Already submitted protocols:" + alreadySubmittedProtocols.size());
		if (!alreadySubmittedProtocols.isEmpty()) {
			Protocol alreadySubmittedProtocol = alreadySubmittedProtocols.get(0);
			if (!alreadySubmittedProtocol.equals(protocol)) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Messagebox.show(
						Labels.getLabel("incomingPage.protocolAlreadyExists",
								new String[] { alreadySubmittedProtocol.getFullNumber(),
										df.format(alreadySubmittedProtocol.getProtocolDate()) }),
						Labels.getLabel("warning.title"), Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION,
						(Event event) -> {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {

								Map<String, Object> params = new HashMap<>();
								params.put(PARAM_DISPLAY_ONLY, true);
								params.put(IConstants.PARAM_KEY_ID, alreadySubmittedProtocol.getId());
								Window relativeProtocolWin = (Window) Executions.createComponents(IncomingVM.PAGE, view,
										params);
								relativeProtocolWin.setClosable(true);
								relativeProtocolWin.doModal();
							}
						});
				return;
			} else {
				// remove already submitted identical protocol from session
				ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
				protocolDAO.evict(alreadySubmittedProtocol);
			}
		}

		try {

			protocolService.saveIncomingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
					protocolDocumentsToBeDeleted, false);

			dirty = false;

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("incomingPage.errorSaving"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			// close current incoming window
			view.detach();
			return;
		}

		if (getIsDocumentUploadFunctionalityEnabled()) {

			// remove deleted documents from OpenKM
			try {
				protocolService.deleteUploadedDocuments(protocolDocumentsToBeDeleted, getUserInSession(), getIp());
			} catch (OpenKMException e) {
				log.error(e);
				// no special handling for failed deleted documents
			}

			// upload newly inserted documents
			List<ProtocolDocument> newDocuments = new ArrayList<ProtocolDocument>();
			for (ProtocolDocument protocolDocument : protocolDocuments) {
				if (protocolDocument.getOkmUuid() == null) {
					newDocuments.add(protocolDocument);
				}
			}

			try {
				protocolService.uploadProtocolDocuments(protocol, getUserInSession(), getIp(), newDocuments);
				BindUtils.postGlobalCommand(null, null, "finishUpdateIncomingProtocol", null);
			} catch (OpenKMException e) {
				log.error(e);

				List<ProtocolDocument> failedProtocolDocuments = new ArrayList<ProtocolDocument>();
				for (ProtocolDocument protocolDocument : newDocuments) {
					if (protocolDocument.getOkmUuid() == null) {
						failedProtocolDocuments.add(protocolDocument);
					}
				}

				Messagebox.show(Labels.getLabel("protocolPage.errorOpenKM.retry"), Labels.getLabel("confirm.title"),
						Messagebox.YES | Messagebox.NO, Messagebox.ERROR, event -> {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								try {
									protocolService.uploadProtocolDocuments(protocol, getUserInSession(), getIp(),
											failedProtocolDocuments);
								} catch (Exception e1) {
									log.error(e1);
									Messagebox.show(Labels.getLabel("protocolPage.errorOpenKM"),
											Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);

								} finally {
									BindUtils.postGlobalCommand(null, null, "finishUpdateIncomingProtocol", null);
								}

							} else {
								BindUtils.postGlobalCommand(null, null, "finishUpdateIncomingProtocol", null);
							}
						});
			}

		} else {
			BindUtils.postGlobalCommand(null, null, "finishUpdateIncomingProtocol", null);
		}

	}

	@GlobalCommand
	@NotifyChange("*")
	public void finishUpdateIncomingProtocol() {
		protocolDocumentsToBeDeleted.clear();
		
		try {
			LoggingService loggingService = SpringUtil.getApplicationContext().getBean(LoggingService.class);
			loggingService.success(Operation.EDIT_PROTOCOL, getUserInSession(), protocol);
		} catch (Exception e) {
			log.error(e);
			//swallow
		}

		Messagebox.show(Labels.getLabel("save.OK"), Labels.getLabel("save.title"), Messagebox.OK,
				Messagebox.INFORMATION);
		return;
	}

	@Command
	public void printProtocol() {
		Executions.getCurrent().sendRedirect(PAGE_PRINT + "?" + IConstants.PARAM_KEY_ID + "=" + protocol.getId(),
				"_blank");
	}

	@Command
	public void deleteProtocol() {

		try {
			Messagebox.show(Labels.getLabel("incomingPage.deleteProtocol"), Labels.getLabel("confirm.title"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {

								delete();

								BindUtils.postNotifyChange(null, null, IncomingVM.this, "*");
								if (callback != null) {
									// notify caller (if any)
									if (callback.getCaller() != null) {
										// called by MVC controller
										Events.postEvent(callback.getEvent(), callback.getCaller(), protocol);
									} else {
										// called by MVVM view model
										Map<String, Object> args = new HashMap<>();
										args.put("entity", protocol);
										BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
									}
								}
								Messagebox.show(Labels.getLabel("incomingPage.deleteProtocol.success"),
										Labels.getLabel("success.title"), Messagebox.OK, Messagebox.INFORMATION);
							}
						}
					});
		} catch (Exception e) {
			// swallow
		}
	}

	@Command
	@NotifyChange({ "updateProtocolEnabled", "deleteDisabled", "printDisabled", "outgoingProtocolCreationEnabled" })
	public void updateInternalRecipient(@BindingParam("internalRecipient") ProtocolCorrespondent internalRecipient) {
		if (!isProtocolCreated() || isProtocolPending()) {
			return;
		}
		internalRecipient.setDirty(TRUE);
		dirty = true;
		Messagebox.show(Labels.getLabel("protocolPage.protocolDirty"), Labels.getLabel("warning.title"), Messagebox.OK,
				Messagebox.EXCLAMATION);

	}

	@Command
	public void createOutgoingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		// get current incoming window parent view, for later use
		Component parent = view.getParent();
		// close current incoming window
		view.detach();
		// create modal window for selected protocol, attaching it to the
		// incoming window's parent view
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_INCOMING_ID, protocol.getId());
		params.put(IConstants.PARAM_KEY_RELATION_TYPE, RelationType.IDENTICAL_NUMBER);
		Window outgoingWin = (Window) Executions.createComponents(OutgoingVM.PAGE, parent, params);
		outgoingWin.setClosable(true);
		outgoingWin.doModal();
	}

	@Command
	public void routeProtocol(@ContextParam(ContextType.VIEW) Component view) {
		// create modal window for batch routing selected protocols
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BatchRouteVM.PARAM_PROTOCOL, protocol);
		params.put(BatchRouteVM.PARAM_BATCH_ROUTE_RECIPIENTS, internalRecipientsApplicableForRouting);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshProtocol"));

		Window batchRouteWin = (Window) Executions.createComponents(BatchRouteVM.PAGE, view, params);
		batchRouteWin.setClosable(true);
		batchRouteWin.doModal();
	}

	@Command
	public void receiveProtocol(@ContextParam(ContextType.VIEW) Component view) {
		ProtocolCorrespondentDAO protocolCorrespondentDAO = SpringUtil.getApplicationContext()
				.getBean(ProtocolCorrespondentDAO.class);
		List<ProtocolCorrespondent> assigneesByDepartment = protocolCorrespondentDAO
				.findProtocolAssigneesByDepartment(protocol, getUserInSessionDepartment());

		// create modal window for protocol reception
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ReceptionVM.PARAM_RECIPIENTS, internalRecipientsApplicableForReception);
		params.put(ReceptionVM.PARAM_ASSIGNEES, assigneesByDepartment);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshProtocol"));

		Window receptionWin = (Window) Executions.createComponents(ReceptionVM.PAGE, view, params);
		receptionWin.setClosable(true);
		receptionWin.doModal();
	}

	@Command
	public void editAssignees(@ContextParam(ContextType.VIEW) Component view) {
		editAssignees(view, true);
	}

	@GlobalCommand
	@NotifyChange("*")
	public void refreshProtocol(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Refreshing Protocol, id/number/direction:" + protocol.getId() + "/" + protocol.getProtocolNumber()
				+ "/" + protocol.getDirection());
		ProtocolDAO incomingProtocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		Protocol existingProtocol = incomingProtocolDAO.findById(protocol.getId(), false);
		if (existingProtocol == null) { // not found
			Messagebox.show(Labels.getLabel("incomingPage.errorRefreshing"), Labels.getLabel("fetch.title"),
					Messagebox.OK, Messagebox.ERROR);
			view.detach();
			return;
		}
		protocol = existingProtocol;
		initExistingProtocol();
		log.info("Refreshed Protocol, UUID/id/number/direction:" + protocol.getUuid() + "/" + protocol.getId() + "/"
				+ protocol.getProtocolNumber() + "/" + protocol.getDirection());
	}

	public boolean isInternalRecipientsVisible() {
		boolean visible;
		// for submitted protocols, show internal recipients only if current
		// user belongs to the same department as the one that created the
		// protocol
		if (isProtocolSubmitted()) {
			visible = true;
		} else {
			visible = SecurityUtil.isAllGranted(Permission.ASSIGN_INTERNAL_RECIPIENTS.toString());
		}
		return visible;
	}

	public boolean isOutgoingProtocolCreationEnabled() {

		// incoming protocol must already be submitted
		if (!isProtocolSubmitted()) {
			return FALSE;
		}

		if (isProtocolDeleted()) {
			return FALSE;
		}

		if (dirty) {
			return FALSE;
		}

		if (!protocol.getCreator().getDepartment().equals(getUserInSessionDepartment())) {
			return FALSE;
		}

		// incoming protocol year must be the same as the current year
		if (!protocol.getProtocolYear().equals(Calendar.getInstance().get(Calendar.YEAR))) {
			return FALSE;
		}
		// incoming protocol document type must allow for outgoing protocol
		// creation
		if (!protocol.getDocumentType().getAllowOutgoingFromIncoming()) {
			return FALSE;
		}

		// another explicit incoming-outgoing relation must not already exist
		Set<ProtocolRelation> protocolRelationsAsTarget = protocol.getProtocolRelationsAsTarget();
		boolean isOutgoingProtocolAlreadyCreated = protocolRelationsAsTarget.stream()
				.anyMatch(protocolRelation -> protocolRelation.getRelationType().equals(IDENTICAL_NUMBER)
						|| protocolRelation.getRelationType().equals(NEW_NUMBER));
		if (isOutgoingProtocolAlreadyCreated) {
			return FALSE;
		}

		return TRUE;
	}

	public boolean isAddProtocolEnabled() {
		return SecurityUtil.isAllGranted(Permission.WRITE_INCOMING.toString())
				&& !Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType());
	}

	public boolean isRouteProtocolEnabled() {
		return !isProtocolDeleted() && internalRecipientsApplicableForRouting != null
				&& !internalRecipientsApplicableForRouting.isEmpty();
	}

	public boolean isProtocolReceptionEnabled() {
		return !isProtocolDeleted() && !dirty && internalRecipientsApplicableForReception != null
				&& !internalRecipientsApplicableForReception.isEmpty();
	}

	public CorrespondentType[] getSenderTypes() {
		return senderTypes;
	}

	public CorrespondentAction[] getCorrespondentActions() {
		return correspondentActions;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Integer getContactsTotalSize() {
		return contactsTotalSize;
	}

	public void setContactsTotalSize(Integer contactsTotalSize) {
		this.contactsTotalSize = contactsTotalSize;
	}

	public Integer getContactsPageSize() {
		return contactsPageSize;
	}

	public void setContactsPageSize(Integer contactsPageSize) {
		this.contactsPageSize = contactsPageSize;
	}

	public Integer getContactsActivePage() {
		return contactsActivePage;
	}

	public void setContactsActivePage(Integer contactsActivePage) {
		this.contactsActivePage = contactsActivePage;
	}

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public List<DistributionMethod> getDistributionMethods() {
		return distributionMethods;
	}

	public void setDistributionMethods(List<DistributionMethod> distributionMethods) {
		this.distributionMethods = distributionMethods;
	}

	public List<UserHierarchy> getEmployees() {
		return employees;
	}

	public void setEmployees(List<UserHierarchy> employees) {
		this.employees = employees;
	}

	public Integer getEmployeesTotalSize() {
		return employeesTotalSize;
	}

	public void setEmployeesTotalSize(Integer employeesTotalSize) {
		this.employeesTotalSize = employeesTotalSize;
	}

	public Integer getEmployeesPageSize() {
		return employeesPageSize;
	}

	public void setEmployeesPageSize(Integer employeesPageSize) {
		this.employeesPageSize = employeesPageSize;
	}

	public Integer getEmployeesActivePage() {
		return employeesActivePage;
	}

	public void setEmployeesActivePage(Integer employeesActivePage) {
		this.employeesActivePage = employeesActivePage;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Integer getCompaniesTotalSize() {
		return companiesTotalSize;
	}

	public void setCompaniesTotalSize(Integer companiesTotalSize) {
		this.companiesTotalSize = companiesTotalSize;
	}

	public Integer getCompaniesPageSize() {
		return companiesPageSize;
	}

	public void setCompaniesPageSize(Integer companiesPageSize) {
		this.companiesPageSize = companiesPageSize;
	}

	public Integer getCompaniesActivePage() {
		return companiesActivePage;
	}

	public void setCompaniesActivePage(Integer companiesActivePage) {
		this.companiesActivePage = companiesActivePage;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Integer getDepartmentsTotalSize() {
		return departmentsTotalSize;
	}

	public void setDepartmentsTotalSize(Integer departmentsTotalSize) {
		this.departmentsTotalSize = departmentsTotalSize;
	}

	public Integer getDepartmentsPageSize() {
		return departmentsPageSize;
	}

	public void setDepartmentsPageSize(Integer departmentsPageSize) {
		this.departmentsPageSize = departmentsPageSize;
	}

	public Integer getDepartmentsActivePage() {
		return departmentsActivePage;
	}

	public void setDepartmentsActivePage(Integer departmentsActivePage) {
		this.departmentsActivePage = departmentsActivePage;
	}

	public List<Entity> getActiveMembers() {
		return activeMembers;
	}

	public void setActiveMembers(List<Entity> activeMembers) {
		this.activeMembers = activeMembers;
	}

	public Integer getActiveMembersTotalSize() {
		return activeMembersTotalSize;
	}

	public void setActiveMembersTotalSize(Integer activeMembersTotalSize) {
		this.activeMembersTotalSize = activeMembersTotalSize;
	}

	public Integer getActiveMembersPageSize() {
		return activeMembersPageSize;
	}

	public void setActiveMembersPageSize(Integer activeMembersPageSize) {
		this.activeMembersPageSize = activeMembersPageSize;
	}

	public Integer getActiveMembersActivePage() {
		return activeMembersActivePage;
	}

	public void setActiveMembersActivePage(Integer activeMembersActivePage) {
		this.activeMembersActivePage = activeMembersActivePage;
	}

	public List<Department> getInternalRecipientDepartments() {
		return internalRecipientDepartments;
	}

	public void setInternalRecipientDepartments(List<Department> internalRecipientDepartments) {
		this.internalRecipientDepartments = internalRecipientDepartments;
	}

	public Department getInternalRecipientDepartment() {
		return internalRecipientDepartment;
	}

	public void setInternalRecipientDepartment(Department internalRecipientDepartment) {
		this.internalRecipientDepartment = internalRecipientDepartment;
	}

	public Integer getInternalRecipientDepartmentsTotalSize() {
		return internalRecipientDepartmentsTotalSize;
	}

	public void setInternalRecipientDepartmentsTotalSize(Integer internalRecipientDepartmentsTotalSize) {
		this.internalRecipientDepartmentsTotalSize = internalRecipientDepartmentsTotalSize;
	}

	public Integer getInternalRecipientDepartmentsPageSize() {
		return internalRecipientDepartmentsPageSize;
	}

	public void setInternalRecipientDepartmentsPageSize(Integer internalRecipientDepartmentsPageSize) {
		this.internalRecipientDepartmentsPageSize = internalRecipientDepartmentsPageSize;
	}

	public Integer getInternalRecipientDepartmentsActivePage() {
		return internalRecipientDepartmentsActivePage;
	}

	public void setInternalRecipientDepartmentsActivePage(Integer internalRecipientDepartmentsActivePage) {
		this.internalRecipientDepartmentsActivePage = internalRecipientDepartmentsActivePage;
	}

	public EntitySelection<CorrespondentGroup> getInternalRecipientGroupSelection() {
		return internalRecipientGroupSelection;
	}

	public void setInternalRecipientGroupSelection(
			EntitySelection<CorrespondentGroup> internalRecipientGroupSelection) {
		this.internalRecipientGroupSelection = internalRecipientGroupSelection;
	}

	public List<ProtocolCorrespondent> getInternalRecipients() {
		return internalRecipients;
	}

	public void setInternalRecipients(List<ProtocolCorrespondent> internalRecipients) {
		this.internalRecipients = internalRecipients;
	}

	public ProtocolCorrespondent getInternalRecipient() {
		return internalRecipient;
	}

	public void setInternalRecipient(ProtocolCorrespondent internalRecipient) {
		this.internalRecipient = internalRecipient;
	}

	public Boolean getIdenticalNumberOutgoing() {
		return identicalNumberOutgoing;
	}

	public void setIdenticalNumberOutgoing(Boolean sameProtocolNumberOutgoing) {
		this.identicalNumberOutgoing = sameProtocolNumberOutgoing;
	}

	public boolean isProtocolReceptionComplete() {
		return protocolReceptionComplete;
	}

	public void setProtocolReceptionComplete(boolean protocolReceptionComplete) {
		this.protocolReceptionComplete = protocolReceptionComplete;
	}

}
