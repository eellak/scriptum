/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;
import static gr.scriptum.domain.ProtocolRelation.RelationType.*;
import static gr.scriptum.eprotocol.util.IConstants.*;
import static java.lang.Boolean.*;

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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;

import gr.scriptum.api.external.Entity;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.ProtocolCorrespondentDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.SubjectDAO;
import gr.scriptum.dao.UserAssignmentDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.Department;
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
import gr.scriptum.domain.Subject;
import gr.scriptum.domain.UserAssignment;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.service.LoggingService;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.exception.OpenKMException;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class OutgoingVM extends ProtocolVM {

	private static Log log = LogFactory.getLog(OutgoingVM.class);

	public static final String PAGE = "outgoing.zul";

	private static final String PAGE_PRINT = "outgoingPrint.zul";

	private List<Contact> toContacts = null;

	private Integer toContactsTotalSize = null;

	private Integer toContactsPageSize = PAGE_SIZE_DEFAULT;

	private Integer toContactsActivePage = null;

	private Contact toContact = null;

	private List<Contact> ccContacts = null;

	private Integer ccContactsTotalSize = null;

	private Integer ccContactsPageSize = PAGE_SIZE_DEFAULT;

	private Integer ccContactsActivePage = null;

	private Contact ccContact = null;

	private List<DistributionMethod> distributionMethods = null;

	private DistributionMethod distributionMethod;

	private String toTerm = null;

	private String ccTerm = null;

	private List<UserHierarchy> authorEmployees;

	private Integer authorEmployeesTotalSize;

	private Integer authorEmployeesPageSize = PAGE_SIZE_DEFAULT;

	private Integer authorEmployeesActivePage;

	private CorrespondentType[] recipientTypes = CorrespondentType.values();

	private CorrespondentType recipientType;

	private CorrespondentAction[] correspondentActions = CorrespondentAction.values();

	private List<ProtocolCorrespondent> recipients;

	private ProtocolCorrespondent recipient;

	private List<Contact> contacts = null;

	private Integer contactsTotalSize = null;

	private Integer contactsPageSize = PAGE_SIZE_DEFAULT;

	private Integer contactsActivePage = null;

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

	private String recipientTerm;

	private EntitySelection<CorrespondentGroup> recipientGroupSelection;

	private Protocol incomingProtocol;

	private ProtocolRelation outgoingIncomingRelation;

	private RelationType[] outgoingIncomingRelationTypes = { IDENTICAL_NUMBER, NEW_NUMBER };

	private boolean protocolReceptionComplete = false;

	private boolean numberAllocationEnabled = false;

	/* non-accessible fields */
	private DistributionMethod defaultRecipientDistributionMethod;
	private List<ProtocolCorrespondent> recipientsApplicableForReception;
	private List<ProtocolCorrespondent> recipientsApplicableForRouting;
	private gr.scriptum.domain.Permission writeOutgoingPermission;

	private void initNewProtocol() {
		protocol = new Protocol(Direction.OUTGOING);
		protocol.setUuid(UUID.randomUUID());
		protocol.setProtocolCorrespondents(new HashSet<ProtocolCorrespondent>());
		recipientType = CorrespondentType.OTHER;
		toContacts = new ArrayList<Contact>();
		toContact = null;
		ccContacts = new ArrayList<Contact>();
		ccContact = null;
		dossiers = new ArrayList<Dossier>();
		authorEmployees = new ArrayList<UserHierarchy>();
		contacts = new ArrayList<Contact>();
		employees = new ArrayList<UserHierarchy>();
		companies = new ArrayList<Company>();
		departments = new ArrayList<Department>();
		activeMembers = new ArrayList<Entity>();
		transactors = new HashSet<ProtocolCorrespondent>();
		recipients = new ArrayList<ProtocolCorrespondent>();
		initRecipientGroupSelection();
		subjects = new ArrayList<Subject>();
		relativeProtocols = new ArrayList<Protocol>();
		protocolDocuments = new LinkedList<ProtocolDocument>();
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		protocolDocument = null;
		term = null;
		toTerm = null;
		ccTerm = null;
		dossierTerm = "";
		subjectTerm = null;
		relativeProtocolYear = Calendar.getInstance().get(Calendar.YEAR);

		pertainsToSenderTypes = new CorrespondentType[] { CorrespondentType.ACTIVE_MEMBER,
				CorrespondentType.INACTIVE_MEMBER };

		// set protocol year to current
		Calendar calendar = Calendar.getInstance();
		protocol.setProtocolYear(calendar.get(Calendar.YEAR));
		// set protocol date to current
		protocol.setProtocolDate(calendar.getTime());

		// set department to current user's department
		department = getUserInSessionDepartment();

		initProtocolDocumentTypeSelection();

		populateApplicableProtocolBooks(writeOutgoingPermission);
		if (protocolBooks.isEmpty()) {
			Messagebox.show(Labels.getLabel("protocolPage.noBooksAssignedToUser"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
		} else {
			// set first document type, applicable for this book
			DocumentTypeDAO documentTypeDAO = SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
			List<DocumentType> documentTypes = documentTypeDAO
					.findByProtocolBookAndApplicableType(protocol.getProtocolBook(), ApplicableType.OUTGOING, 0, 1);
			protocol.setDocumentType(documentTypes.get(0));
		}

		pertainsToSenderType = CorrespondentType.ACTIVE_MEMBER;
		protocolRelation = null;
		dirty = false;
		log.info("Initialized new protocol, UUID/direction:" + protocol.getUuid() + "/" + protocol.getDirection());
	}

	private void initNewProtocolFromIncoming() {
		protocol = new Protocol(Direction.OUTGOING);
		protocol.setUuid(UUID.randomUUID());
		protocol.setProtocolCorrespondents(new HashSet<ProtocolCorrespondent>());
		recipientType = CorrespondentType.OTHER;
		toContacts = new ArrayList<Contact>();
		toContact = null;
		ccContacts = new ArrayList<Contact>();
		ccContact = null;
		dossiers = new ArrayList<Dossier>();
		authorEmployees = new ArrayList<UserHierarchy>();
		contacts = new ArrayList<Contact>();
		employees = new ArrayList<UserHierarchy>();
		companies = new ArrayList<Company>();
		departments = new ArrayList<Department>();
		activeMembers = new ArrayList<Entity>();
		transactors = new HashSet<ProtocolCorrespondent>();
		recipients = new ArrayList<ProtocolCorrespondent>();
		subjects = new ArrayList<Subject>();
		relativeProtocols = new ArrayList<Protocol>();
		protocolDocuments = new LinkedList<ProtocolDocument>();
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		protocolDocument = null;
		term = null;
		toTerm = null;
		ccTerm = null;
		dossierTerm = "";
		subjectTerm = null;
		relativeProtocolYear = Calendar.getInstance().get(Calendar.YEAR);

		pertainsToSenderTypes = new CorrespondentType[] { CorrespondentType.ACTIVE_MEMBER,
				CorrespondentType.INACTIVE_MEMBER };

		// set protocol year to incoming's one
		Calendar calendar = Calendar.getInstance();
		protocol.setProtocolYear(incomingProtocol.getProtocolYear());

		// set protocol date to current
		protocol.setProtocolDate(calendar.getTime());

		// set department to current user's department
		department = getUserInSessionDepartment();

		// check if user has write permission on incoming protocol's book
		UserAssignmentDAO userAssignmentDAO = SpringUtil.getApplicationContext().getBean(UserAssignmentDAO.class);
		List<UserAssignment> userBookAssignments = userAssignmentDAO.findByUserAndEntityTypeAndEntityId(
				getUserInSession(), ProtocolBook.class, incomingProtocol.getProtocolBook().getId(),
				writeOutgoingPermission);
		protocolBooks = new ArrayList<ProtocolBook>();
		if (userBookAssignments.isEmpty()) {
			Messagebox.show(Labels.getLabel("outgoingPage.bookNotAssignedToUser"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
		} else {
			protocolBooks.add(incomingProtocol.getProtocolBook());
			protocol.setProtocolBook(incomingProtocol.getProtocolBook());
		}

		initProtocolDocumentTypeSelection();

		pertainsToSenderType = CorrespondentType.ACTIVE_MEMBER;

		// set subject to incoming's one
		protocol.setSubject(incomingProtocol.getSubject());

		// set outgoing date to current date
		protocol.setIncomingDate(calendar.getTime());

		// set outgoing comments to incoming's ones
		protocol.setComments(incomingProtocol.getComments());
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent incomingSender = incomingProtocol.getSender();
		ProtocolCorrespondent recipient = null;
		switch (incomingSender.getType()) {
		case ACTIVE_MEMBER:
			Entity entity = new Entity();
			entity.setId(incomingSender.getCode());
			entity.setName(incomingSender.getDescription());
			recipient = protocolService.getRecipientActiveMember(entity.getId().toString(), entity.getName(), protocol,
					department, defaultRecipientDistributionMethod, TO);
			break;
		case INACTIVE_MEMBER:
			entity = new Entity();
			entity.setId(incomingSender.getCode());
			entity.setName(incomingSender.getDescription());
			recipient = protocolService.getRecipientInactiveMember(entity.getId().toString(), entity.getName(),
					protocol, department, defaultRecipientDistributionMethod, TO);
			break;
		case EMPLOYEE:
			UserHierarchy userHierarchy = new UserHierarchy(incomingSender.getDepartment(),
					incomingSender.getContact());
			recipient = protocolService.getRecipient(userHierarchy, protocol, department,
					defaultRecipientDistributionMethod, TO);
			break;
		case DEPARTMENT:
			recipient = protocolService.getRecipient(incomingSender.getDepartment(), protocol, department,
					defaultRecipientDistributionMethod, TO);
			break;
		case COMPANY:
			recipient = protocolService.getRecipient(incomingSender.getCompany(), protocol, department,
					defaultRecipientDistributionMethod, TO);
			break;
		case CONTACT:
			recipient = protocolService.getRecipient(incomingSender.getContact(), protocol, department,
					defaultRecipientDistributionMethod, TO);
			break;
		case OTHER:
			recipient = protocolService.getRecipient(incomingSender.getDescription(), protocol, department,
					defaultRecipientDistributionMethod, TO);
		default:
			break;
		}
		if (recipient != null) {
			recipients.add(recipient);
		}

		outgoingIncomingRelation = new ProtocolRelation();
		outgoingIncomingRelation.setSourceProtocol(protocol);
		outgoingIncomingRelation.setTargetProtocol(incomingProtocol);

		RelationType relationType = (RelationType) Executions.getCurrent().getArg().get(PARAM_KEY_RELATION_TYPE);
		outgoingIncomingRelation.setRelationType(relationType);
		protocol.getProtocolRelationsAsSource().add(outgoingIncomingRelation);

		initRecipientGroupSelection();

		Boolean numberAllocationEnabledParameter = SpringUtil.getApplicationContext().getBean(ParameterDAO.class)
				.getAsBoolean(IConstants.PARAM_ENABLE_IDENTICAL_PROTOCOL_NUMBER_ASSIGNMENT);
		if (numberAllocationEnabledParameter != null) {
			numberAllocationEnabled = numberAllocationEnabledParameter;
		}
		log.info("Initialized new protocol from incoming, UUID/direction:" + protocol.getUuid() + "/"
				+ protocol.getDirection());
	}

	private void initExistingProtocol() {
		protocol.setUuid(UUID.randomUUID());
		// existing protocol
		if (isProtocolPending()) {
			recipientType = CorrespondentType.OTHER;
			protocolBooks = new ArrayList<ProtocolBook>();
			protocolBooks.add(protocol.getProtocolBook());
		}

		// populate recipients
		recipients = new ArrayList<>(protocol.getRecipients());
		recipients.sort((r1, r2) -> {
			int codeCompare = StringUtils.trimToEmpty(r1.getCode()).compareTo(StringUtils.trimToEmpty(r2.getCode()));
			return codeCompare == 0 ? StringUtils.trimToEmpty(r1.getDescription())
					.compareTo(StringUtils.trimToEmpty(r2.getDescription())) : codeCompare;
		});
		initRecipientGroupSelection();
		transactors = protocol.getTransactors();
		// populate protocol documents listbox
		protocolDocuments = new LinkedList<ProtocolDocument>(protocol.getProtocolDocuments());
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		initProtocolDocumentTypeSelection();
		department = protocol.getCreator() != null ? protocol.getCreator().getDepartment() : null; // TODO:
		// remove
		// check
		// once
		// database
		// is
		// pristine

		// filter recipients applicable for protocol routing
		if (SecurityUtil.isAllGranted(Permission.BATCH_ROUTE.toString()) && isProtocolSubmitted()) {
			ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
			recipientsApplicableForRouting = protocolService.getRecipientsApplicableForRouting(recipients);
		}

		// filter recipients applicable for protocol reception
		Date today = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DATE);
		if (isProtocolSubmitted()) {
			recipientsApplicableForReception = recipients.stream()
					.filter(r -> r.getType().equals(CorrespondentType.DEPARTMENT)
							&& r.getDepartment().equals(getUserInSessionDepartment())
							&& (r.getDeliveryDate() == null
									|| (protocol.getAttachedNumber() != null && r.getAttachedDeliveryDate() == null))
							&& r.getRoutingDate() != null
							&& (today.equals(r.getRoutingDate()) || today.after(r.getRoutingDate())))
					.collect(Collectors.toCollection(ArrayList::new));

			// check if protocol reception is complete for current user's
			// department
			if (recipientsApplicableForReception.isEmpty()) {
				protocolReceptionComplete = recipients.stream()
						.anyMatch(r -> r.getType().equals(CorrespondentType.DEPARTMENT)
								&& r.getDepartment().equals(getUserInSessionDepartment())
								&& r.getDeliveryDate() != null);
			}
		}
		relativeProtocols = new ArrayList<Protocol>();
		relativeProtocolYear = protocol.getProtocolYear();
		attachedNumberEditable = protocol.getAttachedNumber() != null ? true : false;
		authorEmployees = new ArrayList<UserHierarchy>();
		dirty = false;
		log.info("Initialized existing protocol, UUID/number/id/direction:" + protocol.getUuid() + "/"
				+ protocol.getProtocolNumber() + "/" + protocol.getId() + "/" + protocol.getDirection());
	}

	private List<ProtocolCorrespondent> getTransactorsForRecipient(Set<ProtocolCorrespondent> transactors,
			ProtocolCorrespondent recipient) {
		return transactors.stream().filter(
				r -> r.getType().equals(recipient.getType()) && r.getDescription().equals(recipient.getDescription()))
				.collect(Collectors.toList());
	}

	private void initRecipientGroupSelection() {
		recipientGroupSelection = new EntitySelection<>();
		recipientGroupSelection.setPageSize(PAGE_SIZE_DEFAULT);
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
				protocol.getProtocolBook(), ApplicableType.OUTGOING);
		subjects = subjectDAO.findByTermAndProtocolBookAndApplicableTo(subjectTerm, protocol.getProtocolBook(),
				ApplicableType.OUTGOING, startIndex, subjectsPageSize);
	}

	@Init
	public void init() throws Exception {

		// setup callback, if any
		Execution execution = Executions.getCurrent();
		Map<?, ?> arg = execution.getArg();
		callback = (Callback) arg.get(IConstants.PARAM_CALLBACK);

		if (arg.containsKey(PARAM_DISPLAY_ONLY)) {
			displayOnly = (Boolean) arg.get(PARAM_DISPLAY_ONLY);
		}

		DistributionMethodDAO distributionMethodDAO = SpringUtil.getApplicationContext()
				.getBean(DistributionMethodDAO.class);
		distributionMethods = distributionMethodDAO.findAll();
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		defaultRecipientDistributionMethod = protocolService.getProtocolRecipientDefaultDistributionMethod();
		distributionMethod = defaultRecipientDistributionMethod;

		PermissionDAO permissionDAO = SpringUtil.getApplicationContext().getBean(PermissionDAO.class);
		writeOutgoingPermission = new gr.scriptum.domain.Permission();
		writeOutgoingPermission.setName(Permission.WRITE_OUTGOING.toString());
		writeOutgoingPermission = permissionDAO.findByExample(writeOutgoingPermission).get(0);

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
			ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
			Integer protocolId = null;
			try {
				protocolId = Integer.valueOf(idString);
			} catch (Exception e) {
				log.error(e);
			}
			Protocol existingProtocol = null;
			if (protocolId != null) {
				log.info("Fetching protocol, id/direction:" + protocolId + "/OUTGOING");
				try {
					existingProtocol = protocolDAO.findById(protocolId, false);
				} catch (Exception e) {
					log.info("Error fetching protocol, id/direction:" + protocolId + "/OUTGOING", e);
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
			if (arg.containsKey(IConstants.PARAM_KEY_INCOMING_ID)) {
				Integer incomingId = (Integer) arg.get(IConstants.PARAM_KEY_INCOMING_ID);
				ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
				incomingProtocol = protocolDAO.findById(incomingId, false);
				// init outgoing protocol from incoming
				initNewProtocolFromIncoming();

			} else {
				initNewProtocol();
			}

		} else {
			initExistingProtocol();
		}

		if (execution.getDesktop().getRequestPath().endsWith(PAGE_PRINT)) {
			log.info("Printing protocol, UUID/number/id/direction:" + protocol.getUuid() + "/"
					+ protocol.getProtocolNumber() + "/" + protocol.getId() + "/" + protocol.getDirection());
			ParameterDAO parameterDAO = SpringUtil.getApplicationContext().getBean(ParameterDAO.class);
			organization = parameterDAO.getAsString(IConstants.PARAM_PROTOCOL_BOOK_COMPANY);
			Clients.print();
		}
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
	@NotifyChange({ "recipients", "contacts", "contactsTotalSize", "contactsActivePage", "transactorsEnabled" })
	public void selectContact(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent recipient = protocolService.getRecipient(
				(Contact) event.getSelectedObjects().iterator().next(), protocol, department,
				defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, recipient)) {
			recipients.add(recipient);
		}
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
	@NotifyChange({ "recipients", "contacts", "contactsTotalSize", "contactsActivePage" })
	public void refreshContacts(@BindingParam("entity") Contact contact) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent recipient2 = protocolService.getRecipient(contact, protocol, department,
				defaultRecipientDistributionMethod, TO);
		recipients.add(recipient2);
		contacts = new ArrayList<Contact>();
		contactsTotalSize = 0;
		contactsActivePage = 0;
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
	@NotifyChange({ "recipients", "employees", "employeesTotalSize", "employeesActivePage", "transactorsEnabled" })
	public void selectEmployee(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent recipient = protocolService.getRecipient(
				(UserHierarchy) event.getSelectedObjects().iterator().next(), protocol, department,
				defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, recipient)) {
			recipients.add(recipient);
		}
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
	@NotifyChange({ "recipients", "companies", "companiesTotalSize", "companiesActivePage", "transactorsEnabled" })
	public void selectCompany(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent recipient = protocolService.getRecipient(
				(Company) event.getSelectedObjects().iterator().next(), protocol, department,
				defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, recipient)) {
			recipients.add(recipient);
		}
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
	@NotifyChange({ "recipients", "companies", "companiesTotalSize", "companiesActivePage" })
	public void refreshCompanies(@BindingParam("entity") Company company) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent protocolCorrespondent = protocolService.getRecipient(company, protocol, department,
				defaultRecipientDistributionMethod, TO);
		recipients.add(protocolCorrespondent);
		companies = new ArrayList<Company>();
		companiesTotalSize = 0;
		companiesActivePage = 0;
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
	@NotifyChange({ "recipients", "departments", "departmentsTotalSize", "departmentsActivePage",
			"transactorsEnabled" })
	public void selectDepartment(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent recipient = protocolService.getRecipient(
				(Department) event.getSelectedObjects().iterator().next(), protocol, department,
				defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, recipient)) {
			recipients.add(recipient);
		}
		departments = new ArrayList<Department>();
		departmentsTotalSize = 0;
		departmentsActivePage = 0;
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
	@NotifyChange({ "recipients", "transactors", "transactor", "transactorRemovable", "transactorsEnabled",
			"activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void searchActiveMembersByTerm() {
		activeMembersActivePage = 0;
		Object[] results = searchActiveMembers(term, 0, activeMembersPageSize);
		activeMembersTotalSize = (Integer) results[0];
		activeMembers = (List<Entity>) results[1];
		if (activeMembers.size() == 1) {
			Entity entity = activeMembers.get(0);
			ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
			ProtocolCorrespondent protocolCorrespondent = recipientType.equals(ACTIVE_MEMBER)
					? protocolService.getRecipientActiveMember(entity.getId().toString(), entity.getName(), protocol,
							department, defaultRecipientDistributionMethod, TO)
					: protocolService.getRecipientInactiveMember(entity.getId().toString(), entity.getName(), protocol,
							department, defaultRecipientDistributionMethod, TO);
			if (!protocolService.recipientFoundInList(recipients, protocolCorrespondent)) {
				recipients.add(protocolCorrespondent);
			}
			activeMembers = new ArrayList<Entity>();
			activeMembersTotalSize = 0;
			activeMembersActivePage = 0;

			// also add a transactor (if not already in list)
			if (getTransactorsForRecipient(transactors, protocolCorrespondent).size() > 0) {
				return;
			}
			ProtocolCorrespondent transactor = new ProtocolCorrespondent(protocol, CorrespondentDirection.TRANSACTOR,
					recipientType);
			transactor.setCode(entity.getId().toString());
			transactor.setDescription(entity.getName());
			transactors.add(transactor);
			transactor = null;
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
	@NotifyChange("recipientTerm")
	public void prepareAddOther(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		recipientTerm = event.getValue();
	}

	@Command
	@NotifyChange({ "recipients", "recipientTerm", "transactorsEnabled" })
	public void addOther() {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		recipientTerm = StringUtils.trimToNull(recipientTerm);
		if (recipientTerm == null) {
			return;
		}
		ProtocolCorrespondent recipient = protocolService.getRecipient(recipientTerm, protocol, department,
				defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, recipient)) {
			recipients.add(recipient);
		}
		recipientTerm = null;
	}

	@Command
	@NotifyChange({ "recipients", "transactors", "transactor", "transactorRemovable", "transactorsEnabled",
			"activeMembers", "activeMembersTotalSize", "activeMembersActivePage" })
	public void selectActiveMember(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent event) {
		activeMembers = new ArrayList<Entity>();
		activeMembersTotalSize = 0;
		activeMembersActivePage = 0;
		Entity entity = (Entity) event.getSelectedObjects().iterator().next();
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		ProtocolCorrespondent protocolCorrespondent = recipientType.equals(ACTIVE_MEMBER)
				? protocolService.getRecipientActiveMember(entity.getId().toString(), entity.getName(), protocol,
						department, defaultRecipientDistributionMethod, TO)
				: protocolService.getRecipientInactiveMember(entity.getId().toString(), entity.getName(), protocol,
						department, defaultRecipientDistributionMethod, TO);
		if (!protocolService.recipientFoundInList(recipients, protocolCorrespondent)) {
			recipients.add(protocolCorrespondent);
		}

		// also add a transactor (if not already in list)
		if (getTransactorsForRecipient(transactors, protocolCorrespondent).size() > 0) {
			return;
		}
		ProtocolCorrespondent transactor = new ProtocolCorrespondent(protocol, CorrespondentDirection.TRANSACTOR,
				recipientType);
		transactor.setCode(entity.getId().toString());
		transactor.setDescription(entity.getName());
		transactors.add(transactor);
		transactor = null;
	}

	@Command
	@NotifyChange("recipientGroupSelection")
	public void searchRecipientGroupsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		recipientGroupSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		recipientGroupSelection.setActivePage(0);
		Object[] results = searchCorrespondentGroups(recipientGroupSelection.getTerm(), RECIPIENT, 0,
				recipientGroupSelection.getPageSize());
		recipientGroupSelection.setTotalSize((Integer) results[0]);
		recipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange("recipientGroupSelection")
	public void listRecipientGroups() {
		if (recipientGroupSelection.getEntites() != null && !recipientGroupSelection.getEntites().isEmpty()) {
			return;
		}
		recipientGroupSelection.setTerm("");
		recipientGroupSelection.setActivePage(0);
		Object[] results = searchCorrespondentGroups(recipientGroupSelection.getTerm(), RECIPIENT, 0,
				recipientGroupSelection.getPageSize());
		recipientGroupSelection.setTotalSize((Integer) results[0]);
		recipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange("recipientGroupSelection")
	public void clearRecipientGroups() {
		recipientGroupSelection.setEntites(new ArrayList<>());
		recipientGroupSelection.setTotalSize(0);
		recipientGroupSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("recipientGroupSelection")
	public void pageRecipientGroups() {
		int startIndex = recipientGroupSelection.getActivePage() * recipientGroupSelection.getPageSize();
		Object[] results = searchCorrespondentGroups(recipientGroupSelection.getTerm(), RECIPIENT, startIndex,
				recipientGroupSelection.getPageSize());
		recipientGroupSelection.setTotalSize((Integer) results[0]);
		recipientGroupSelection.setEntites((List<CorrespondentGroup>) results[1]);
	}

	@Command
	@NotifyChange({ "recipients", "recipientGroupSelection" })
	public void selectRecipientGroup() {
		CorrespondentGroup correspondentGroup = recipientGroupSelection.getSelectedEntity();
		// refresh from database
		CorrespondentGroupDAO correspondentGroupDAO = SpringUtil.getApplicationContext()
				.getBean(CorrespondentGroupDAO.class);
		correspondentGroup = correspondentGroupDAO.merge(correspondentGroup);
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		Set<ProtocolCorrespondent> groupMembers = correspondentGroup.getProtocolCorrespondents();
		for (ProtocolCorrespondent groupMember : groupMembers) {
			ProtocolCorrespondent recipient = null;
			switch (groupMember.getType()) {
			case EMPLOYEE:
				UserHierarchy userHierarchy = new UserHierarchy();
				userHierarchy.setContact(groupMember.getContact());
				userHierarchy.setDepartment(groupMember.getDepartment());
				recipient = protocolService.getRecipient(userHierarchy, protocol, department,
						defaultRecipientDistributionMethod, TO);
				break;
			case DEPARTMENT:
				recipient = protocolService.getRecipient(groupMember.getDepartment(), protocol, department,
						defaultRecipientDistributionMethod, TO);
				break;
			case COMPANY:
				recipient = protocolService.getRecipient(groupMember.getCompany(), protocol, department,
						defaultRecipientDistributionMethod, TO);
				break;
			case CONTACT:
				recipient = protocolService.getRecipient(groupMember.getContact(), protocol, department,
						defaultRecipientDistributionMethod, TO);
				break;
			default:
				continue;
			}
			if (!protocolService.recipientFoundInList(recipients, recipient)) {
				recipients.add(recipient);
			}
		}
		initRecipientGroupSelection();
	}

	@Command
	@NotifyChange("recipients")
	public void selectRecipientDistributionMethod(@BindingParam("recipient") ProtocolCorrespondent recipient) {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		recipient.setRoutingDate(protocolService.getRoutingDate(recipient, department));
	}

	@Command
	@NotifyChange("recipients")
	public void selectRecipientsDistributionMethod() {
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		for (ProtocolCorrespondent recipient : recipients) {
			recipient.setDistributionMethod(distributionMethod);
			recipient.setRoutingDate(protocolService.getRoutingDate(recipient, department));
		}
	}

	@Command
	@NotifyChange({ "recipients", "recipient", "transactor", "transactors", "transactorsEnabled",
			"transactorRemovable" })
	public void removeRecipient() {
		recipients.remove(recipient);
		// clear all transactors, if recipients is empty
		if (recipients.isEmpty()) {
			transactors = new HashSet<ProtocolCorrespondent>();
		}
		// also remove corresponding transactor (if found in list)
		List<ProtocolCorrespondent> transactorsForRecipient = getTransactorsForRecipient(transactors, recipient);
		transactors.removeAll(transactorsForRecipient);
		recipient = null;
		setTransactor(null);
	}

	@Command
	@NotifyChange("protocol")
	public void clearRecipient() {
		// TODO
	}

	@Command
	@NotifyChange({ "recipients", "recipient", "transactor", "transactors", "transactorsEnabled",
			"transactorRemovable" })
	public void removeAllRecipient() {
		recipients = new ArrayList<ProtocolCorrespondent>();
		// clear all transactors, as well
		transactors = new HashSet<ProtocolCorrespondent>();
		recipient = null;
		setTransactor(null);
	}

	@Command
	@NotifyChange({ "authorEmployees", "authorEmployeesTotalSize", "authorEmployeesActivePage" })
	public void searchAuthorEmployeesByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			authorEmployees = new ArrayList<UserHierarchy>();
			return;
		}
		authorEmployeesActivePage = 0;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), 0, authorEmployeesPageSize);
		authorEmployeesTotalSize = (Integer) results[0];
		authorEmployees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "authorEmployees", "authorEmployeesTotalSize", "authorEmployeesActivePage" })
	public void listAuthorEmployees() {
		if (!authorEmployees.isEmpty()) {
			return;
		}
		term = "";
		authorEmployeesActivePage = 0;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), 0, authorEmployeesPageSize);
		authorEmployeesTotalSize = (Integer) results[0];
		authorEmployees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange({ "authorEmployees", "authorEmployeesTotalSize", "authorEmployeesActivePage" })
	public void clearAuthorEmployees() {
		authorEmployees = new ArrayList<UserHierarchy>();
		authorEmployeesTotalSize = 0;
		authorEmployeesActivePage = 0;
	}

	@Command
	@NotifyChange({ "authorEmployees", "authorEmployeesTotalSize", "authorEmployeesActivePage" })
	public void pageAuthorEmployees() {
		int startIndex = authorEmployeesActivePage * authorEmployeesPageSize;
		Object[] results = searchEmployees(term, getUserInSessionDepartment(), startIndex, authorEmployeesPageSize);
		authorEmployeesTotalSize = (Integer) results[0];
		authorEmployees = (List<UserHierarchy>) results[1];
	}

	@Command
	@NotifyChange("*")
	public void selectAuthorEmployee() {
		authorEmployees = new ArrayList<UserHierarchy>();
		authorEmployeesTotalSize = 0;
		authorEmployeesActivePage = 0;
		setProtocolChanged();
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

		// validateFields(outgoingWin, distributionMethodCbx);
		validateFields(view);

		protocol.setTransactors(transactors);
		protocol.setRecipients(new HashSet<>(recipients));
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		try {

			protocolService.saveOutgoingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
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

		// validate document type
		if (protocol.getDocumentType() == null) {
			Messagebox.show(Labels.getLabel("protocolPage.invalidDocumentType"), Labels.getLabel("error.title"),
					Messagebox.OK, Messagebox.ERROR);
			return;
		}

		// validate recipients
		if (recipients.isEmpty()) {
			Messagebox.show(Labels.getLabel("outgoingPage.noToAdded"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		protocol.setRecipients(new HashSet<>(recipients));
		protocol.setTransactors(transactors);

		if (outgoingIncomingRelation != null) {
			if (outgoingIncomingRelation.getRelationType().equals(IDENTICAL_NUMBER)) {
				protocol.setProtocolNumber(incomingProtocol.getProtocolNumber());
			} else {
				protocol.setProtocolNumber(null);
			}
		}

		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		try {

			protocolService.saveOutgoingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
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
				BindUtils.postGlobalCommand(null, null, "finishInsertOutgoingProtocol", null);
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
									BindUtils.postGlobalCommand(null, null, "finishInsertOutgoingProtocol", null);
								}

							} else {
								BindUtils.postGlobalCommand(null, null, "finishInsertOutgoingProtocol", null);
							}
						});
			}

		} else {
			BindUtils.postGlobalCommand(null, null, "finishInsertOutgoingProtocol", null);
		}
	}

	@GlobalCommand
	@NotifyChange("*")
	public void finishInsertOutgoingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		// store some protocol details locale, before clearing everything, to
		// allow for printing
		final String fullNumber = protocol.getFullNumber();
		final Integer protocolId = protocol.getId();

		// clear everything
		if (incomingProtocol != null) {
			// automatically close window, when creating an incoming-based
			// outgoing protocol
			view.detach();
		} else {
			initNewProtocol();
		}

		try {
			Messagebox.show(Labels.getLabel("protocolPage.protocolSubmitted", new String[] { fullNumber }),
					Labels.getLabel("success.title"),
					new Messagebox.Button[] { Messagebox.Button.OK, Messagebox.Button.YES },
					new String[] { Labels.getLabel("ok"), Labels.getLabel("print") }, Messagebox.INFORMATION, null,
					(ClickEvent event) -> {
						if (event.getButton() == null) {
							return;
						}
						if (event.getButton().equals(Button.YES)) {
							Executions.getCurrent().sendRedirect(
									PAGE_PRINT + "?" + IConstants.PARAM_KEY_ID + "=" + protocolId, "_blank");
							return;
						}
					});
		} catch (Exception e) {
			// swallow
		}
	}

	@Command
	@NotifyChange("*")
	public void updateProtocol(@ContextParam(ContextType.VIEW) Component view) throws Exception {

		validateFields(view);

		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		try {

			protocolService.saveOutgoingProtocol(protocol, getUserInSession(), department, getIp(), protocolDocuments,
					protocolDocumentsToBeDeleted, false);

			dirty = false;

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("incomingPage.errorSaving"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			// close current outgoing window
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
				BindUtils.postGlobalCommand(null, null, "finishUpdateOutgoingProtocol", null);
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
									BindUtils.postGlobalCommand(null, null, "finishUpdateOutgoingProtocol", null);
								}

							} else {
								BindUtils.postGlobalCommand(null, null, "finishUpdateOutgoingProtocol", null);
							}
						});
			}

		} else {
			BindUtils.postGlobalCommand(null, null, "finishUpdateOutgoingProtocol", null);
		}
	}

	@GlobalCommand
	@NotifyChange("*")
	public void finishUpdateOutgoingProtocol() {
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

								BindUtils.postNotifyChange(null, null, OutgoingVM.this, "*");
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
	public void routeProtocol(@ContextParam(ContextType.VIEW) Component view) {
		// create modal window for batch routing selected protocols
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(BatchRouteVM.PARAM_PROTOCOL, protocol);
		params.put(BatchRouteVM.PARAM_BATCH_ROUTE_RECIPIENTS, recipientsApplicableForRouting);
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
		params.put(ReceptionVM.PARAM_RECIPIENTS, recipientsApplicableForReception);
		params.put(ReceptionVM.PARAM_ASSIGNEES, assigneesByDepartment);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshProtocol"));

		Window batchRouteWin = (Window) Executions.createComponents(ReceptionVM.PAGE, view, params);
		batchRouteWin.setClosable(true);
		batchRouteWin.doModal();
	}

	@Command
	public void editAssignees(@ContextParam(ContextType.VIEW) Component view) {
		ProtocolCorrespondentDAO protocolCorrespondentDAO = SpringUtil.getApplicationContext()
				.getBean(ProtocolCorrespondentDAO.class);
		List<ProtocolCorrespondent> assigneesByDepartment = protocolCorrespondentDAO
				.findProtocolAssigneesByDepartment(protocol, getUserInSessionDepartment());

		// create modal window for assignees edit
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ReceptionVM.PARAM_PROTOCOL, protocol);
		params.put(ReceptionVM.PARAM_ASSIGNEES, assigneesByDepartment);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshProtocol"));

		Window assigneesWin = (Window) Executions.createComponents(ReceptionVM.PAGE, view, params);
		assigneesWin.setClosable(true);
		assigneesWin.doModal();
	}

	@GlobalCommand
	@NotifyChange("*")
	public void refreshProtocol(@ContextParam(ContextType.VIEW) Component view) {
		log.info("Refreshing Protocol, id/number/direction:" + protocol.getId() + "/" + protocol.getProtocolNumber()
				+ "/" + protocol.getDirection());
		ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		Protocol existingProtocol = protocolDAO.findById(protocol.getId(), false);
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

	@Command
	@NotifyChange("transactorRemovable")
	public void selectTransactor() {
		// nothing to do
	}

	@Override
	@Command
	@NotifyChange({ "transactor", "transactors", "transactorRemovable" })
	public void removeTransactor() {
		super.removeTransactor();
	}

	public boolean isTransactorRemovable() {
		if (getTransactor() == null) {
			return false;
		}
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		return !protocolService.recipientFoundInList(recipients, getTransactor());
	}

	public boolean isTransactorsEnabled() {
		if (transactors == null) {
			return false;
		}
		ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
		return protocolService.recipientsContainAnyInternalTypes(recipients);
	}

	public boolean isNumberAllocationEnabled() {
		return numberAllocationEnabled;
	}

	public boolean isAddProtocolEnabled() {
		return SecurityUtil.isAllGranted(Permission.WRITE_OUTGOING.toString())
				&& !Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType());
	}

	public boolean isRouteProtocolEnabled() {

		if (isProtocolDeleted()) {
			return FALSE;
		}

		if (SecurityUtil.isNoneGranted(Permission.BATCH_ROUTE.toString())) {
			return FALSE;
		}

		if (isProtocolPending()) {
			return FALSE;
		}

		if (dirty) {
			return FALSE;
		}

		return recipients.stream()
				.anyMatch(recipient -> recipient.getType().equals(CorrespondentType.DEPARTMENT)
						&& recipient.getDepartment().getDepartmentType().getName()
								.equals(IConstants.DEPARTMENT_TYPE_CENTRAL_ADMINISTRATION_NAME)
						&& recipient.getRoutingDate() == null);
	}

	public boolean isProtocolReceptionEnabled() {
		return !isProtocolDeleted() && !dirty && recipientsApplicableForReception != null
				&& !recipientsApplicableForReception.isEmpty();
	}

	public boolean isEditAssigneesEnabled() {
		return dirty;
	}

	public String getToTerm() {
		return toTerm;
	}

	public void setToTerm(String toTerm) {
		this.toTerm = toTerm;
	}

	public String getCcTerm() {
		return ccTerm;
	}

	public void setCcTerm(String ccTerm) {
		this.ccTerm = ccTerm;
	}

	public List<Contact> getToContacts() {
		return toContacts;
	}

	public void setToContacts(List<Contact> toContacts) {
		this.toContacts = toContacts;
	}

	public Integer getToContactsTotalSize() {
		return toContactsTotalSize;
	}

	public void setToContactsTotalSize(Integer toContactsTotalSize) {
		this.toContactsTotalSize = toContactsTotalSize;
	}

	public Integer getToContactsPageSize() {
		return toContactsPageSize;
	}

	public void setToContactsPageSize(Integer toContactsPageSize) {
		this.toContactsPageSize = toContactsPageSize;
	}

	public Integer getToContactsActivePage() {
		return toContactsActivePage;
	}

	public void setToContactsActivePage(Integer toContactsActivePage) {
		this.toContactsActivePage = toContactsActivePage;
	}

	public Contact getToContact() {
		return toContact;
	}

	public void setToContact(Contact toContact) {
		this.toContact = toContact;
	}

	public List<Contact> getCcContacts() {
		return ccContacts;
	}

	public void setCcContacts(List<Contact> ccContacts) {
		this.ccContacts = ccContacts;
	}

	public Integer getCcContactsTotalSize() {
		return ccContactsTotalSize;
	}

	public void setCcContactsTotalSize(Integer ccContactsTotalSize) {
		this.ccContactsTotalSize = ccContactsTotalSize;
	}

	public Integer getCcContactsPageSize() {
		return ccContactsPageSize;
	}

	public void setCcContactsPageSize(Integer ccContactsPageSize) {
		this.ccContactsPageSize = ccContactsPageSize;
	}

	public Integer getCcContactsActivePage() {
		return ccContactsActivePage;
	}

	public void setCcContactsActivePage(Integer ccContactsActivePage) {
		this.ccContactsActivePage = ccContactsActivePage;
	}

	public Contact getCcContact() {
		return ccContact;
	}

	public void setCcContact(Contact ccContact) {
		this.ccContact = ccContact;
	}

	public List<DistributionMethod> getDistributionMethods() {
		return distributionMethods;
	}

	public void setDistributionMethods(List<DistributionMethod> distributionMethods) {
		this.distributionMethods = distributionMethods;
	}

	public DistributionMethod getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	public List<UserHierarchy> getAuthorEmployees() {
		return authorEmployees;
	}

	public void setAuthorEmployees(List<UserHierarchy> employees) {
		this.authorEmployees = employees;
	}

	public Integer getAuthorEmployeesTotalSize() {
		return authorEmployeesTotalSize;
	}

	public void setAuthorEmployeesTotalSize(Integer employeesTotalSize) {
		this.authorEmployeesTotalSize = employeesTotalSize;
	}

	public Integer getAuthorEmployeesPageSize() {
		return authorEmployeesPageSize;
	}

	public void setAuthorEmployeesPageSize(Integer employeesPageSize) {
		this.authorEmployeesPageSize = employeesPageSize;
	}

	public Integer getAuthorEmployeesActivePage() {
		return authorEmployeesActivePage;
	}

	public void setAuthorEmployeesActivePage(Integer employeesActivePage) {
		this.authorEmployeesActivePage = employeesActivePage;
	}

	public CorrespondentType getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(CorrespondentType recipientType) {
		this.recipientType = recipientType;
	}

	public CorrespondentAction[] getCorrespondentActions() {
		return correspondentActions;
	}

	public CorrespondentType[] getRecipientTypes() {
		return recipientTypes;
	}

	public List<ProtocolCorrespondent> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<ProtocolCorrespondent> recipients) {
		this.recipients = recipients;
	}

	public ProtocolCorrespondent getRecipient() {
		return recipient;
	}

	public void setRecipient(ProtocolCorrespondent recipient) {
		this.recipient = recipient;
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

	public String getRecipientTerm() {
		return recipientTerm;
	}

	public void setRecipientTerm(String recipientTerm) {
		this.recipientTerm = recipientTerm;
	}

	public EntitySelection<CorrespondentGroup> getRecipientGroupSelection() {
		return recipientGroupSelection;
	}

	public void setRecipientGroupSelection(EntitySelection<CorrespondentGroup> recipientGroupSelection) {
		this.recipientGroupSelection = recipientGroupSelection;
	}

	public Protocol getIncomingProtocol() {
		return incomingProtocol;
	}

	public void setIncomingProtocol(Protocol incomingProtocol) {
		this.incomingProtocol = incomingProtocol;
	}

	public ProtocolRelation getOutgoingIncomingRelation() {
		return outgoingIncomingRelation;
	}

	public void setOutgoingIncomingRelation(ProtocolRelation outgoingIncomingRelation) {
		this.outgoingIncomingRelation = outgoingIncomingRelation;
	}

	public RelationType[] getOutgoingIncomingRelationTypes() {
		return outgoingIncomingRelationTypes;
	}

	public void setOutgoingIncomingRelationTypes(RelationType[] outgoingIncomingRelationTypes) {
		this.outgoingIncomingRelationTypes = outgoingIncomingRelationTypes;
	}

	public boolean isProtocolReceptionComplete() {
		return protocolReceptionComplete;
	}

	public void setProtocolReceptionComplete(boolean protocolReceptionComplete) {
		this.protocolReceptionComplete = protocolReceptionComplete;
	}

}
