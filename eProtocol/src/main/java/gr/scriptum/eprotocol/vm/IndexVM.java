/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;
import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType.*;
import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SortEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.dao.DepartmentDAO;
import gr.scriptum.dao.DepartmentTypeDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.PermissionDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.dao.UserHierarchyDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DepartmentType;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolCorrespondent_;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ScriptumDocument;
import gr.scriptum.domain.UserHierarchy;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.eprotocol.vm.IndexVM.SearchSenderType;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestSearch;
import gr.scriptum.eprotocol.ws.ResponseSearch;
import gr.scriptum.exception.OpenKMException;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;
import gr.scriptum.predicate.CorrespondentPredicateGroup.JunctionType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class IndexVM extends BaseVM {

	public enum SearchSenderType {
		FREE, ACTIVE_MEMBER, INACTIVE_MEMBER, EMPLOYEE, DEPARTMENT, COMPANY, CONTACT, OTHER
	}

	public enum SearchTransactorType {
		FREE, ACTIVE_MEMBER, INACTIVE_MEMBER
	}

	private static Log log = LogFactory.getLog(IndexVM.class);

	public static final String PAGE = "index_vm.zul";

	public static final String PARAM_SELECTED_TAB = "tab";

	@Wire("#incomingLstbx")
	Listbox incomingLstbx;

	@Wire("#outgoingLstbx")
	Listbox outgoingLstbx;

	@Wire("#searchProtocolsLstbx")
	Listbox searchProtocolsLstbx;

	@Wire("#batchRouteProtocolsLstbx")
	Listbox batchRouteProtocolsLstbx;

	/* data binding */

	/* common */
	private String selectedTab = null;

	/* incoming */
	private List<ProtocolBook> incomingProtocolBooks;
	private Protocol incomingProtocol = null;
	private List<Protocol> incomingProtocols = null;
	private Integer incomingProtocolsTotalSize;
	private Integer incomingProtocolsPageSize = PAGE_SIZE_LARGE;
	private Integer incomingProtocolsActivePage = 0;
	private List<Protocol> selectedIncomingProtocols;
	private Protocol selectedIncomingProtocol = null;
	private Date incomingDateFrom = null;
	private Date incomingDateTo = null;
	private Boolean incomingCreator;
	private Boolean incomingRoutingDate;

	/* outgoing */
	private List<ProtocolBook> outgoingProtocolBooks;
	private Protocol outgoingProtocol = null;
	private List<Protocol> outgoingProtocols = null;
	private Integer outgoingProtocolsTotalSize;
	private Integer outgoingProtocolsPageSize = PAGE_SIZE_LARGE;
	private Integer outgoingProtocolsActivePage = 0;
	private Protocol selectedOutgoingProtocol = null;
	private Date outgoingDateFrom = null;
	private Date outgoingDateTo = null;

	/* search criteria */
	private Boolean searchIncoming = null;
	private Boolean searchOutgoing = null;
	private Integer protocolNumber = null;
	private Date searchDateFrom = null;
	private Date searchDateTo = null;
	private String incomingProtocolNumber;
	private Date searchIncomingDateFrom;
	private Date searchIncomingDateTo;
	private String subject = null;
	private String comments = null;
	private Integer attachedNumber;
	private String attachedDescription;
	private String keywords = null;
	private String content = null;
	private List<DistributionMethod> distributionMethods = null;
	private DistributionMethod distributionMethod = null;
	private String distributionMethodDetails;
	private List<ProtocolBook> searchProtocolBooks = null;
	private ProtocolBook searchProtocolBook;
	private boolean searchProtocolBookSelectionActive = false;
	private EntitySelection<DocumentType> searchProtocolDocumentTypeSelection;
	private EntitySelection<DepartmentType> searchProtocolDepartmentTypeSelection;
	private EntitySelection<Department> searchDepartmentSelection;
	private Date searchRoutingDate;
	private Date searchDeliveryDate;
	private SearchSenderType[] searchSenderRecipientTypes;

	private SearchSenderType searchSenderType;
	private ProtocolCorrespondent searchSender;
	private String searchSenderText;
	private EntitySelection<Object> searchSenderSelection;

	private SearchSenderType searchRecipientType;
	private ProtocolCorrespondent searchRecipient;
	private String searchRecipientText;
	private EntitySelection<Object> searchRecipientSelection;

	private SearchTransactorType[] searchTransactorTypes;
	private SearchTransactorType searchTransactorType;
	private ProtocolCorrespondent searchTransactor;
	private String searchTransactorText;
	private EntitySelection<Object> searchTransactorSelection;

	private Boolean searchCriteriaEnabled = TRUE;

	/* search results */
	private List<ProtocolDocument> searchProtocolDocuments;
	private ProtocolDocument selectedSearchProtocolDocument;
	private List<Protocol> searchProtocols;
	private Integer searchProtocolsTotalSize;
	private Integer searchProtocolsPageSize = PAGE_SIZE_LARGE;
	private Integer searchProtocolsActivePage = 0;
	private Protocol selectedSearchProtocol;

	/* batch route */
	private Boolean batchRouteIncoming;
	private Boolean batchRouteOutgoing;
	private List<Department> batchRouteDepartments;
	private Department batchRouteDepartment;
	private Integer batchRouteDepartmentsTotalSize;
	private Integer batchRouteDepartmentsPageSize = PAGE_SIZE_DEFAULT;
	private Integer batchRouteDepartmentsActivePage;
	private List<Protocol> batchRouteProtocols;
	private List<Protocol> selectedBatchRouteProtocols;
	private Integer batchRouteProtocolsTotalSize;
	private Integer batchRouteProtocolsPageSize = PAGE_SIZE_LARGE;
	private Integer batchRouteProtocolsActivePage = 0;

	/* non-accessible fields (i.e no getters/setters) */
	private String batchRouterDepartmentsTerm;

	private DepartmentType departmentType;

	private Date today;

	private void initIncoming() {
		incomingProtocol = new Protocol(Direction.INCOMING);
		incomingDateFrom = null;
		incomingDateTo = null;
		incomingCreator = TRUE;
		incomingRoutingDate = TRUE;
	}

	private void initOutgoing() {
		outgoingProtocol = new Protocol(Direction.OUTGOING);
		outgoingDateFrom = null;
		outgoingDateTo = null;
	}

	private void initSearch() {
		searchIncoming = true;
		searchOutgoing = true;
		protocolNumber = null;
		searchDateFrom = null;
		searchDateTo = null;
		incomingProtocolNumber = null;
		searchIncomingDateFrom = null;
		searchIncomingDateTo = null;
		subject = null;
		comments = null;
		distributionMethod = null;
		distributionMethodDetails = null;
		keywords = null;
		attachedNumber = null;
		attachedDescription = null;
		content = null;

		searchDepartmentSelection = new EntitySelection<>();
		searchDepartmentSelection.setPageSize(PAGE_SIZE_DEFAULT);
		searchDepartmentSelection.setEntites(new ArrayList<>());
		if (!Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType())) {
			searchDepartmentSelection.getEntites().add(getUserInSessionDepartment());
			searchDepartmentSelection.setSelectedEntity(getUserInSessionDepartment());
		}

		searchProtocolBook = null;
		searchProtocolBookSelectionActive = false;
		for (ProtocolBook protocolBook : searchProtocolBooks) {
			// pre-select first 'PUBLIC' book
			if (protocolBook.getType().equals(ProtocolBookType.PUBLIC)) {
				this.searchProtocolBook = protocolBook;
				break;
			}
		}
		if (searchProtocolBook == null && !searchProtocolBooks.isEmpty()) {
			searchProtocolBook = searchProtocolBooks.get(0);
		}

		initSearchProtocolDocumentTypeSelection();

		searchRoutingDate = null;
		searchDeliveryDate = null;

		searchSenderRecipientTypes = SearchSenderType.values();
		searchSenderType = SearchSenderType.FREE;
		searchSender = null;
		searchSenderText = null;
		searchSenderSelection = new EntitySelection<>();
		searchSenderSelection.setPageSize(PAGE_SIZE_DEFAULT);

		searchRecipientType = SearchSenderType.FREE;
		searchRecipientText = null;
		searchRecipient = null;
		searchRecipientSelection = new EntitySelection<>();
		searchRecipientSelection.setPageSize(PAGE_SIZE_DEFAULT);

		searchTransactorTypes = SearchTransactorType.values();
		searchTransactorType = SearchTransactorType.FREE;
		searchTransactor = null;
		searchTransactorText = null;
		searchTransactorSelection = new EntitySelection<>();
		searchTransactorSelection.setPageSize(PAGE_SIZE_DEFAULT);

		searchProtocolsTotalSize = null;
		searchProtocols = null;
		searchProtocolsActivePage = 0;

	}

	private void initBatchRoute() {
		batchRouteIncoming = TRUE;
		batchRouteOutgoing = TRUE;
		batchRouteDepartments = null;
		batchRouteDepartment = null;
		batchRouteDepartmentsTotalSize = 0;
		batchRouteDepartmentsActivePage = 0;
	}

	private void fetchIncoming(Integer startIndex) {
		boolean includeDeletedProtocols = false;

		if (incomingProtocolBooks == null || incomingProtocolBooks.isEmpty()) {
			incomingProtocolsTotalSize = 0;
			incomingProtocols = null;
			return;
		}

		incomingProtocol = (Protocol) trimStringProperties(incomingProtocol);
		ProtocolDAO incomingProtocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		//TODO: remove redundant correspondent predicates
		List<CorrespondentPredicate> correspondentPredicatesList = new ArrayList<CorrespondentPredicate>();
		if (getUserInSessionDepartment() != null) {
			if (incomingCreator) {
				ProtocolCorrespondent creatorCorrespondent = new ProtocolCorrespondent(null, null,
						CorrespondentType.DEPARTMENT);
				creatorCorrespondent.setDepartment(getUserInSessionDepartment());
				CorrespondentPredicate creatorCorrespondentPredicate = new CorrespondentPredicate(creatorCorrespondent);
				creatorCorrespondentPredicate.setProtocolDirection(Direction.INCOMING);
				creatorCorrespondentPredicate.setDirection(CorrespondentDirection.CREATOR);
				correspondentPredicatesList.add(creatorCorrespondentPredicate);
			} else {
				ProtocolCorrespondent recipientCorrespondent = new ProtocolCorrespondent(null, null,
						CorrespondentType.DEPARTMENT);
				recipientCorrespondent.setDepartment(getUserInSessionDepartment());
				CorrespondentPredicate recipientCorrespondentPredicate = new CorrespondentPredicate(
						recipientCorrespondent);
				recipientCorrespondentPredicate.setProtocolDirection(Direction.OUTGOING);
				recipientCorrespondentPredicate.setDirection(RECIPIENT);
				today = DateUtils.truncate(new Date(), Calendar.DATE);

				recipientCorrespondentPredicate.setAdditionalCriteria(
						new Criterion[] { Restrictions.isNull(ProtocolCorrespondent_.deliveryDate.getName()),
								incomingRoutingDate
										? Restrictions.isNotNull(ProtocolCorrespondent_.routingDate.getName())
										: Restrictions.isNull(ProtocolCorrespondent_.routingDate.getName()) });
				correspondentPredicatesList.add(recipientCorrespondentPredicate);

				ProtocolCorrespondent internalRecipientCorrespondent = new ProtocolCorrespondent(null, null,
						CorrespondentType.DEPARTMENT);
				internalRecipientCorrespondent.setDepartment(getUserInSessionDepartment());
				CorrespondentPredicate internalRecipientCorrespondentPredicate = new CorrespondentPredicate(
						internalRecipientCorrespondent);
				internalRecipientCorrespondentPredicate.setProtocolDirection(Direction.INCOMING);
				internalRecipientCorrespondentPredicate.setDirection(INTERNAL_RECIPIENT);
				internalRecipientCorrespondentPredicate.setAdditionalCriteria(
						new Criterion[] { Restrictions.isNull(ProtocolCorrespondent_.deliveryDate.getName()),
								incomingRoutingDate
										? Restrictions.isNotNull(ProtocolCorrespondent_.routingDate.getName())
										: Restrictions.isNull(ProtocolCorrespondent_.routingDate.getName()) });
				correspondentPredicatesList.add(internalRecipientCorrespondentPredicate);
			}
		}
		if (correspondentPredicatesList.isEmpty()) {
			incomingProtocolsTotalSize = 0;
			incomingProtocols = new ArrayList<Protocol>();
			return;
		}

		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicatesList,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		// set up paging by counting records first
		incomingProtocolsTotalSize = incomingCreator
				? incomingProtocolDAO.countIncomingAsCreator(getUserInSessionDepartment(), incomingProtocolBooks,
						incomingDateFrom, incomingDateTo)
				: incomingProtocolDAO.countIncomingAsRecipient(getUserInSessionDepartment(), incomingProtocolBooks,
						incomingDateFrom, incomingDateTo, incomingRoutingDate);
		// figure out which header to sort by
		Listheader header = getSortingListheader(incomingLstbx);
		List<Order> sortBy = getSortBy(header);

		incomingProtocols = incomingCreator ?  incomingProtocolDAO.findIncomingAsCreator(getUserInSessionDepartment(), incomingProtocolBooks,
				incomingDateFrom, incomingDateTo, startIndex, incomingProtocolsPageSize, sortBy.toArray(new Order[0])) : incomingProtocolDAO.findIncomingAsRecipient(getUserInSessionDepartment(), incomingProtocolBooks,
						incomingDateFrom, incomingDateTo, incomingRoutingDate, startIndex, incomingProtocolsPageSize, sortBy.toArray(new Order[0]));

	}

	private void fetchOutgoing(Integer startIndex) {
		boolean includeDeletedProtocols = false;

		if (outgoingProtocolBooks == null || outgoingProtocolBooks.isEmpty()) {
			outgoingProtocolsTotalSize = 0;
			outgoingProtocols = null;
			return;
		}

		// filter by creator (i.e. current user department)
		ProtocolCorrespondent creatorCorrespondent = new ProtocolCorrespondent(null, null,
				CorrespondentType.DEPARTMENT);
		creatorCorrespondent.setDepartment(getUserInSessionDepartment());
		CorrespondentPredicate creatorCorrespondentPredicate = new CorrespondentPredicate(creatorCorrespondent);
		// creatorCorrespondentPredicate.setProtocolDirection(Direction.CREATOR_OUTGOING);
		creatorCorrespondentPredicate.setDirection(CorrespondentDirection.CREATOR);

		CorrespondentPredicateGroup creatorGroup = new CorrespondentPredicateGroup(creatorCorrespondentPredicate);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(creatorGroup);

		outgoingProtocol = (Protocol) trimStringProperties(outgoingProtocol);
		ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		// set up paging by counting records first
		outgoingProtocolsTotalSize = protocolDAO.countOutgoing(getUserInSessionDepartment(), outgoingProtocolBooks, outgoingDateFrom, outgoingDateTo);

		// figure out which header to sort by
		Listheader header = getSortingListheader(outgoingLstbx);
		List<Order> sortBy = getSortBy(header);

		outgoingProtocols = protocolDAO.findOutgoing(getUserInSessionDepartment(), outgoingProtocolBooks,
				outgoingDateFrom, outgoingDateTo, startIndex, outgoingProtocolsPageSize, sortBy.toArray(new Order[0]));
		//		outgoingProtocols = protocolDAO.search(Direction.OUTGOING, null, null, null, null, null, outgoingDateFrom,
//				outgoingDateTo, outgoingProtocol.getSubject(), null, null, null, null, null, null, null,
//				getIsPendingFunctionalityEnabled(), includeDeletedProtocols, outgoingProtocolBooks, startIndex,
//				outgoingProtocolsPageSize, predicateGroups, null, sortBy.toArray(new Order[0]));
	}

	private void initSearchProtocolDocumentTypeSelection() {
		searchProtocolDocumentTypeSelection = new EntitySelection<DocumentType>();
		searchProtocolDocumentTypeSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	private Object[] searchDepartmentsByType(String term, Integer startIndex, Integer pageSize) {
		DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);

		// set up paging by counting records first
		Integer departmentsTotalSize = departmentDAO
				.countDepartmentsByTerm(term, DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES).intValue();

		List<Department> departments = departmentDAO.findDepartmentsByTerm(term,
				DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES, startIndex, pageSize);
		return new Object[] { departmentsTotalSize, departments };
	}

	private Object[] searchCorrespondentDependencies(String term, CorrespondentDirection[] directions,
			CorrespondentType type, int startIndex, int pageSize) {
		Object[] results = null;
		switch (type) {
		case ACTIVE_MEMBER:
			results = searchProtocolCorrespondents(term, directions, ACTIVE_MEMBER, startIndex, pageSize);
			break;
		case INACTIVE_MEMBER:
			results = searchProtocolCorrespondents(term, directions, INACTIVE_MEMBER, startIndex, pageSize);
			break;
		case CONTACT:
			results = searchContacts(term, startIndex, pageSize);
			break;
		case DEPARTMENT:
			results = searchProtocolDepartments(term, startIndex, pageSize);
			break;
		case EMPLOYEE:
			results = searchEmployees(term, startIndex, pageSize);
			break;
		case COMPANY:
			results = searchCompanies(term, startIndex, pageSize);
			break;
		case OTHER:
			results = searchProtocolCorrespondents(term, directions, OTHER, startIndex, pageSize);
			break;
		}
		return results;
	}

	private ProtocolCorrespondent getProtocolCorrespondentFromSelection(CorrespondentType type,
			EntitySelection<Object> selection) {
		ProtocolCorrespondent searchSender = new ProtocolCorrespondent();
		searchSender.setType(type);

		switch (type) {
		case ACTIVE_MEMBER:
		case INACTIVE_MEMBER:
		case OTHER:
			searchSender = (ProtocolCorrespondent) selection.getSelectedEntity();
			break;
		case CONTACT:
			searchSender.setContact((Contact) selection.getSelectedEntity());
			break;
		case DEPARTMENT:
			searchSender.setDepartment((Department) selection.getSelectedEntity());
			break;
		case EMPLOYEE:
			UserHierarchy selectedEntity = (UserHierarchy) selection.getSelectedEntity();
			searchSender.setContact(selectedEntity.getContact());
			// TODO: also filter by department, or not???
			searchSender.setDepartment(selectedEntity.getDepartment());
			break;
		case COMPANY:
			searchSender.setCompany((Company) selection.getSelectedEntity());
			break;
		default:
			break;
		}
		return searchSender;
	}

	private Object[] searchDocumentTypes(String term, ProtocolBook protocolBook, int startIndex, Integer pageSize) {
		DocumentTypeDAO documentTypeDAO = SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
		// set up paging by counting records first
		Integer totalSize = documentTypeDAO.countByTermAndProtocolBook(term, protocolBook);

		List<DocumentType> results = documentTypeDAO.findByTermAndProtocolBook(term, protocolBook, startIndex,
				pageSize);

		return new Object[] { totalSize, results };
	}

	private void search(Integer startIndex) {
		boolean includeDeletedProtocols = true;

		ProtocolDAO incomingProtocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		keywords = StringUtils.trimToNull(keywords);
		subject = StringUtils.trimToNull(subject);
		comments = StringUtils.trimToNull(comments);
		distributionMethodDetails = StringUtils.trimToNull(distributionMethodDetails);
		attachedDescription = StringUtils.trimToNull(attachedDescription);

		List<ProtocolBook> searchProtocolBooks = new ArrayList<>();
		searchProtocolBooks.add(searchProtocolBook);

		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		// List<CorrespondentPredicate> correspondentPredicatesList = new
		// ArrayList<CorrespondentPredicate>();

		// creator/recipient/internal recipient criteria
		if (searchDepartmentSelection.getSelectedEntity() != null) {

			// general direction is not applicable
			// outgoing & department -> creator with outgoing
			// incoming & department -> (creator with incoming) or (internal
			// recipient with incoming) or (recipient with outgoing)
			// incoming & outgoing & department-> (creator with outgoing) or
			// (creator with incoming) or (internal recipient with incoming) or
			// (recipient with outgoing)

			// general search direction doesn't apply, each correspondent must
			// apply it's own direction criteria
			List<CorrespondentPredicate> departmentCorrespondentPredicates = new ArrayList<CorrespondentPredicate>();

			ProtocolCorrespondent departmentCorrespondent = new ProtocolCorrespondent(null, null,
					CorrespondentType.DEPARTMENT);
			departmentCorrespondent.setDepartment(searchDepartmentSelection.getSelectedEntity());

			if (searchOutgoing) {
				CorrespondentPredicate creatorPredicate = new CorrespondentPredicate(departmentCorrespondent);
				creatorPredicate.setProtocolDirection(Direction.OUTGOING);
				creatorPredicate.setDirection(CREATOR);
				departmentCorrespondentPredicates.add(creatorPredicate);
			}
			if (searchIncoming) {
				CorrespondentPredicate creatorPredicate = new CorrespondentPredicate(departmentCorrespondent);
				creatorPredicate.setProtocolDirection(Direction.INCOMING);
				creatorPredicate.setDirection(CREATOR);
				departmentCorrespondentPredicates.add(creatorPredicate);

				CorrespondentPredicate recipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
				recipientPredicate.setProtocolDirection(Direction.OUTGOING);
				recipientPredicate.setDirection(RECIPIENT);
				departmentCorrespondentPredicates.add(recipientPredicate);

				CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
				internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);
				internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
				departmentCorrespondentPredicates.add(internalRecipientPredicate);
			}

			CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(
					departmentCorrespondentPredicates, JunctionType.OR);
			predicateGroups.add(predicateGroup);
		}

		// routing date criteria
		if (searchRoutingDate != null) {
			CorrespondentPredicate routingDatePredicate = new CorrespondentPredicate();
			routingDatePredicate.setAdditionalCriteria(new Criterion[] {
					Restrictions.eq(ProtocolCorrespondent_.routingDate.getName(), searchRoutingDate) });
			if (searchIncoming && !searchOutgoing) {
				routingDatePredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				routingDatePredicate.setProtocolDirection(Direction.OUTGOING);
			}
			CorrespondentPredicateGroup routingDatePredicateGroup = new CorrespondentPredicateGroup(
					routingDatePredicate);
			predicateGroups.add(routingDatePredicateGroup);
		}

		// delivery date criteria
		if (searchDeliveryDate != null) {
			CorrespondentPredicate deliveryDatePredicate = new CorrespondentPredicate();
			deliveryDatePredicate.setAdditionalCriteria(new Criterion[] {
					Restrictions.eq(ProtocolCorrespondent_.deliveryDate.getName(), searchDeliveryDate) });
			if (searchIncoming && !searchOutgoing) {
				deliveryDatePredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				deliveryDatePredicate.setProtocolDirection(Direction.OUTGOING);
			}
			CorrespondentPredicateGroup routingDatePredicateGroup = new CorrespondentPredicateGroup(
					deliveryDatePredicate);
			predicateGroups.add(routingDatePredicateGroup);
		}

		// sender criteria
		String senderText = StringUtils.trimToNull(searchSenderText);
		if (searchSender != null || senderText != null) {
			CorrespondentPredicate senderPredicate = new CorrespondentPredicate();
			senderPredicate.setDirection(SENDER);
			if (searchSender != null) {
				// specific sender search
				senderPredicate.setCorrespondent(searchSender);
			}
			if (searchIncoming && !searchOutgoing) {
				senderPredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				senderPredicate.setProtocolDirection(Direction.OUTGOING);
			}
			if (senderText != null) {
				// free text sender search
				Criterion[] senderTextCriteria = new Criterion[] { Restrictions
						.like(ProtocolCorrespondent_.description.getName(), senderText, MatchMode.ANYWHERE) };
				senderPredicate.setAdditionalCriteria(senderTextCriteria);
			}
			CorrespondentPredicateGroup senderPredicateGroup = new CorrespondentPredicateGroup(senderPredicate);
			predicateGroups.add(senderPredicateGroup);
		}

		// recipient/internal recipient criteria
		String recipientText = StringUtils.trimToNull(searchRecipientText);
		if (searchRecipient != null || recipientText != null) {

			Criterion[] recipientTextCriteria = null;
			if (recipientText != null) {
				// free text recipient search
				recipientTextCriteria = new Criterion[] { Restrictions
						.like(ProtocolCorrespondent_.description.getName(), recipientText, MatchMode.ANYWHERE) };
			}

			List<CorrespondentPredicate> recipientPredicates = new ArrayList<CorrespondentPredicate>();
			CorrespondentPredicate recipientPredicate = new CorrespondentPredicate();
			recipientPredicate.setDirection(RECIPIENT);
			// specific recipient search (if applicable)
			recipientPredicate.setCorrespondent(searchRecipient == null ? null : searchRecipient);
			if (searchIncoming && !searchOutgoing) {
				recipientPredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				recipientPredicate.setProtocolDirection(Direction.OUTGOING);
			}
			recipientPredicate.setAdditionalCriteria(recipientTextCriteria);
			recipientPredicates.add(recipientPredicate);

			CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate();
			internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
			internalRecipientPredicate.setCorrespondent(searchRecipient == null ? null : searchRecipient);
			if (searchIncoming && !searchOutgoing) {
				internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				internalRecipientPredicate.setProtocolDirection(Direction.OUTGOING);
			}
			internalRecipientPredicate.setAdditionalCriteria(recipientTextCriteria);
			recipientPredicates.add(internalRecipientPredicate);

			CorrespondentPredicateGroup recipientsPredicateGroup = new CorrespondentPredicateGroup(recipientPredicates,
					JunctionType.OR);
			predicateGroups.add(recipientsPredicateGroup);
		}

		// transactor criteria
		String transactorText = StringUtils.trimToNull(searchTransactorText);
		if (searchTransactor != null || transactorText != null) {
			CorrespondentPredicate transactorPredicate = new CorrespondentPredicate();
			transactorPredicate.setDirection(TRANSACTOR);
			transactorPredicate.setCorrespondent(searchTransactor == null ? null : searchTransactor); // specific
																										// transactor
																										// search
			if (searchIncoming && !searchOutgoing) {
				transactorPredicate.setProtocolDirection(Direction.INCOMING);
			} else if (!searchIncoming && searchOutgoing) {
				transactorPredicate.setProtocolDirection(Direction.OUTGOING);
			}
			if (transactorText != null) {
				// free text transactor search
				Criterion[] transactorTextCriteria = new Criterion[] { Restrictions
						.like(ProtocolCorrespondent_.description.getName(), transactorText, MatchMode.ANYWHERE) };
				transactorPredicate.setAdditionalCriteria(transactorTextCriteria);
			}

			CorrespondentPredicateGroup transactorPredicateGroup = new CorrespondentPredicateGroup(transactorPredicate);
			predicateGroups.add(transactorPredicateGroup);
		}

		Direction searchDirection = null;
		// only apply general search direction criteria, if no correspondent
		// predicates are set
		if (predicateGroups.isEmpty()) {
			if (searchIncoming == true && searchOutgoing == false) {
				searchDirection = Direction.INCOMING;
			} else if (searchIncoming == false && searchOutgoing == true) {
				searchDirection = Direction.OUTGOING;
			}
		}
		if (getContent() == null) {
			// local database search

			// set up paging by counting records first
			// only count during first time search (i.e. not on paging events) 
			if (searchProtocolsTotalSize == null) {
				searchProtocolsTotalSize = incomingProtocolDAO.countSearch(searchDirection, null, protocolNumber,
						incomingProtocolNumber, searchIncomingDateFrom, searchIncomingDateTo, searchDateFrom,
						searchDateTo, subject, comments, keywords, attachedNumber, attachedDescription,
						distributionMethod, distributionMethodDetails,
						searchProtocolDocumentTypeSelection.getSelectedEntity(), true, includeDeletedProtocols,
						searchProtocolBooks, predicateGroups, null);
			}
			
			// figure out which header to sort by
			Listheader header = getSortingListheader(searchProtocolsLstbx);
			List<Order> sortBy = getSortBy(header);

			// fetch actual records
			searchProtocols = incomingProtocolDAO.search(searchDirection, null, protocolNumber, incomingProtocolNumber,
					searchIncomingDateFrom, searchIncomingDateTo, searchDateFrom, searchDateTo, subject, comments,
					keywords, attachedNumber, attachedDescription, distributionMethod, distributionMethodDetails,
					searchProtocolDocumentTypeSelection.getSelectedEntity(), true, includeDeletedProtocols,
					searchProtocolBooks, startIndex == null ? 0 : startIndex, searchProtocolsPageSize, predicateGroups,
					null, sortBy.toArray(new Order[0]));

		} else {
			// combined database-OpenKM search
			// if (startIndex == null) {
			// first time search (i.e. no pagination/sorting event etc)

			// count local records first
			Integer countSearch = incomingProtocolDAO.countSearch(searchDirection, null, protocolNumber,
					incomingProtocolNumber, searchIncomingDateFrom, searchIncomingDateTo, searchDateFrom, searchDateTo,
					subject, comments, keywords, attachedNumber, attachedDescription, distributionMethod,
					distributionMethodDetails, searchProtocolDocumentTypeSelection.getSelectedEntity(), false,
					includeDeletedProtocols, searchProtocolBooks, predicateGroups, null);

			if (countSearch > getContentSearchResultsLimit()) {
				searchProtocols = null;
				searchProtocolsTotalSize = null;
				return;
			}
			// }
			// figure out which header to sort by
			Listheader header = getSortingListheader(searchProtocolsLstbx);
			List<Order> sortBy = getSortBy(header);

			// fetch local records
			List<Protocol> localProtocolResults = incomingProtocolDAO.search(searchDirection, null, protocolNumber,
					incomingProtocolNumber, searchIncomingDateFrom, searchIncomingDateTo, searchDateFrom, searchDateTo,
					subject, comments, keywords, attachedNumber, attachedDescription, distributionMethod,
					distributionMethodDetails, searchProtocolDocumentTypeSelection.getSelectedEntity(), false,
					includeDeletedProtocols, searchProtocolBooks, 0, countSearch, predicateGroups, null,
					sortBy.toArray(new Order[0]));

			// fetch OpenKM records (limit also applied here)
			ResponseSearch responseSearch = searchContent();
			if (responseSearch == null) {
				searchProtocols = null;
				searchProtocolsTotalSize = -2;
				return;
			}

			if (responseSearch.getTotal() > getContentSearchResultsLimit()) {
				searchProtocols = null;
				searchProtocolsTotalSize = -1;
				return;
			}

			ArrayList<ScriptumDocument> searchResults = responseSearch.getSearchResults();
			if (searchResults.isEmpty()) {
				searchProtocols = null;
				searchProtocolsTotalSize = 0;
				return;
			}

			List<String> okmUuids = new ArrayList<String>();
			for (ScriptumDocument scriptumDocument : searchResults) {
				okmUuids.add(scriptumDocument.getOkmUuid());
			}
			ProtocolDocumentDAO protocolDocumentDAO = SpringUtil.getApplicationContext()
					.getBean(ProtocolDocumentDAO.class);
			List<ProtocolDocument> protocolDocuments = protocolDocumentDAO.findByOkmUuids(okmUuids);

			// if no okm results are found, then discard all local results
			// as well
			if (protocolDocuments == null) {
				searchProtocols = null;
				searchProtocolsTotalSize = 0;
				return;
			}

			// filter local results by the okm ones
			HashSet<Protocol> okmProtocols = protocolDocuments.stream().map(ProtocolDocument::getProtocol)
					.collect(Collectors.toCollection(HashSet::new));

			for (Iterator<Protocol> iterator = localProtocolResults.iterator(); iterator.hasNext();) {
				Protocol protocol = (Protocol) iterator.next();
				if (!okmProtocols.contains(protocol)) {
					iterator.remove();
				}
			}
			if (localProtocolResults.isEmpty()) {
				searchProtocols = localProtocolResults;
			} else if (startIndex == null) {
				// first time search
				searchProtocols = localProtocolResults.subList(0, localProtocolResults.size() <= searchProtocolsPageSize
						? localProtocolResults.size() : searchProtocolsPageSize);
			} else {
				// paging/sorting event
				searchProtocols = localProtocolResults.subList(startIndex,
						startIndex + searchProtocolsPageSize <= localProtocolResults.size()
								? startIndex + searchProtocolsPageSize : localProtocolResults.size());
			}
			searchProtocolsTotalSize = localProtocolResults.size();
		}
	}

	private void searchBatchRouteProtocols(Integer startIndex) {
		boolean includeDeletedProtocols = false;

		if (searchProtocolBooks == null || searchProtocolBooks.isEmpty()) {
			batchRouteProtocolsTotalSize = 0;
			batchRouteProtocols = null;
			return;
		}

		// general direction is not applicable
		// no department ->
		// outgoing -> creator and outgoing and department type = CENTRAL and
		// routing date null
		// incoming -> (creator and incoming and department type = CENTRAL and
		// routing date null) or
		// (internal recipient and incoming and department type = CENTRAL and
		// routing date null) or
		// (recipient and outgoing and department type = CENTRAL and routing
		// date null)
		// no direction -> or between the above

		// department ->
		// outgoing -> creator and outgoing and department and routing date null
		// incoming -> (creator and incoming and department and routing date
		// null) or
		// (internal recipient and incoming and department and routing date
		// null) or
		// (recipient and outgoing and department and routing date null)
		// no direction -> or between the above

		List<CorrespondentPredicate> correspondentPredicates = new ArrayList<CorrespondentPredicate>();
		ProtocolCorrespondent correspondent = null;
		Criterion[] additionalCriteria = null;

		if (batchRouteDepartment == null) {
			additionalCriteria = new Criterion[] {
					Restrictions.eq(ProtocolCorrespondent_.type.getName(), CorrespondentType.DEPARTMENT),
					Restrictions.isNull(ProtocolCorrespondent_.routingDate.getName()) };

		} else {
			// explicit department selected, use as main criteria
			correspondent = new ProtocolCorrespondent(null, null, CorrespondentType.DEPARTMENT);
			correspondent.setDepartment(batchRouteDepartment);
			additionalCriteria = new Criterion[] { Restrictions.isNull(ProtocolCorrespondent_.routingDate.getName()) };
		}

		if (batchRouteOutgoing) {
			// CorrespondentPredicate creatorPredicate = new
			// CorrespondentPredicate(correspondent);
			// creatorPredicate.setProtocolDirection(Direction.CREATOR_OUTGOING);
			// creatorPredicate.setDirection(CREATOR);
			// creatorPredicate.setAdditionalCriteria(additionalCriteria);
			// correspondentPredicates.add(creatorPredicate);

			CorrespondentPredicate recipientPredicate = new CorrespondentPredicate(correspondent);
			recipientPredicate.setProtocolDirection(Direction.OUTGOING);
			recipientPredicate.setDirection(RECIPIENT);
			recipientPredicate.setAdditionalCriteria(additionalCriteria);
			correspondentPredicates.add(recipientPredicate);

		}

		if (batchRouteIncoming) {
			// CorrespondentPredicate creatorPredicate = new
			// CorrespondentPredicate(correspondent);
			// creatorPredicate.setProtocolDirection(Direction.CREATOR_INCOMING);
			// creatorPredicate.setDirection(CREATOR);
			// creatorPredicate.setAdditionalCriteria(additionalCriteria);
			// correspondentPredicates.add(creatorPredicate);

			CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate(correspondent);
			internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);
			internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
			internalRecipientPredicate.setAdditionalCriteria(additionalCriteria);
			correspondentPredicates.add(internalRecipientPredicate);
		}

		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicates,
				JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		// set up paging by counting records first
		batchRouteProtocolsTotalSize = protocolDAO.countSearch(null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, true, includeDeletedProtocols, searchProtocolBooks,
				predicateGroups, null);

		// figure out which header to sort by
		Listheader header = getSortingListheader(batchRouteProtocolsLstbx);
		List<Order> sortBy = getSortBy(header);

		// fetch actual records
		batchRouteProtocols = protocolDAO.search(null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, true, includeDeletedProtocols, searchProtocolBooks, startIndex,
				batchRouteProtocolsPageSize, predicateGroups, null, sortBy.toArray(new Order[0]));
	}

	private ResponseSearch searchContent() {

		/* OpenKM actions */
		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

		// log into OpenKM
		String okmToken = null;
		try {
			okmToken = getOkmToken();
		} catch (Exception e) {
			log.error(e);
			return null;
		}

		ResponseSearch responseSearch = null;
		try {
			RequestSearch searchRequest = new RequestSearch(getUserInSession().getUsername(),
					getUserInSession().getId(), getIp(), IConstants.SYSTEM_NAME, okmToken);
			searchRequest.setContent(content);
			searchRequest.setOffset(0);
			searchRequest.setLimit(getContentSearchResultsLimit());
			responseSearch = okmDispatcher.searchByContent(searchRequest);
			if (responseSearch.isError()) {
				log.error(responseSearch.toString());
				throw new RuntimeException(responseSearch.toString());
			}
		} catch (Exception e) {
			log.error(e);
			return null;
		} finally {
			// log out of OpenKM
			try {
				clearOkmToken(okmToken);
			} catch (OpenKMException e) {
				log.error(e);
				// swallow
			}
		}
		return responseSearch;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);

		String tab = Executions.getCurrent().getParameter(PARAM_SELECTED_TAB);
		if (tab == null) {
			tab = "incomingTb";
		}
		switch (tab) {
		case "incomingTb":
			Listheader header = getSortingListheader(incomingLstbx);
			fetchIncoming(0);
			selectedTab = tab;
			break;
		case "outgoingTb":
			header = getSortingListheader(outgoingLstbx);
			fetchOutgoing(0);
			selectedTab = tab;
			break;
		case "searchTb":
			initSearch();
			selectedTab = tab;
			break;
		}
	}

	@Init
	public void init() {

		DistributionMethodDAO distributionMethodDAO = SpringUtil.getApplicationContext()
				.getBean(DistributionMethodDAO.class);
		distributionMethods = distributionMethodDAO.findAll();

		ProtocolBookService protocolBookService = SpringUtil.getApplicationContext().getBean(ProtocolBookService.class);

		PermissionDAO permissionDAO = SpringUtil.getApplicationContext().getBean(PermissionDAO.class);
		gr.scriptum.domain.Permission searchPermission = new gr.scriptum.domain.Permission();
		searchPermission.setName(Permission.SEARCH_BOOK.toString());
		searchPermission = permissionDAO.findByExample(searchPermission).get(0);
		searchProtocolBooks = protocolBookService.findByAssignedUser(getUserInSession(), searchPermission);
		incomingProtocolBooks = searchProtocolBooks;
		outgoingProtocolBooks = searchProtocolBooks;

		if (SecurityUtil.isAllGranted(Permission.BATCH_ROUTE.toString())) {
			departmentType = new DepartmentType();
			departmentType.setName(IConstants.DEPARTMENT_TYPE_CENTRAL_ADMINISTRATION_NAME);
			DepartmentTypeDAO departmentTypeDAO = SpringUtil.getApplicationContext().getBean(DepartmentTypeDAO.class);
			List<DepartmentType> departmentTypes = departmentTypeDAO.findByExample(departmentType);
			departmentType = departmentTypes.get(0);
		}

		initIncoming();
		initOutgoing();
		initSearch();
		initBatchRoute();
	}

	/* incoming protocols related */
	@GlobalCommand
	@NotifyChange({ "incomingProtocolsTotalSize", "incomingProtocols", "selectedIncomingProtocols",
			"protocolReceptionEnabled" })
	public void refreshIncomingProtocols() {
		selectedIncomingProtocols = null;
		int startIndex = incomingProtocolsActivePage * incomingProtocolsPageSize;
		fetchIncoming(startIndex);
	}

	@NotifyChange({ "incomingProtocolsActivePage", "incomingProtocolsTotalSize", "incomingProtocols" })
	@Command
	public void sortIncomingProtocols(@ContextParam(ContextType.TRIGGER_EVENT) SortEvent sortEvent) throws Exception {
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		setSortingListheader(header);
		incomingProtocolsActivePage = 0;
		fetchIncoming(0);
	}

	@Command
	@NotifyChange({ "incomingProtocolsActivePage", "incomingProtocolsTotalSize", "incomingProtocols" })
	public void pageIncomingProtocols() throws Exception {
		int startIndex = incomingProtocolsActivePage * incomingProtocolsPageSize;
		fetchIncoming(startIndex);
	}

	@Command
	@NotifyChange("protocolReceptionEnabled")
	public void updateIncomingProtocols() {

	}

	@Command
	@NotifyChange("selectedIncomingProtocols")
	public void selectIncomingProtocol(@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("protocol") Protocol selectedIncomingProtocol) {
		Integer id = selectedIncomingProtocol.getId();
		// create modal window for selected protocol
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_ID, id);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshIncomingProtocols"));
		Window incomingWin = (Window) Executions.createComponents(
				selectedIncomingProtocol.getDirection().equals(Direction.INCOMING) ? IncomingVM.PAGE : OutgoingVM.PAGE,
				view, params);
		incomingWin.setClosable(true);
		incomingWin.doModal();
		selectedIncomingProtocol = null;
	}

	@Command
	@NotifyChange({ "incomingProtocolsActivePage", "incomingProtocolsTotalSize", "incomingProtocols",
			"selectedIncomingProtocols" })
	public void searchIncomingProtocols() {
		selectedIncomingProtocols = null;
		incomingProtocolsActivePage = 0;
		fetchIncoming(0);

		/*
		 * if (incomingProtocols.isEmpty()) {
		 * Messagebox.show(Labels.getLabel("search.notFound"),
		 * Labels.getLabel("search.title"), Messagebox.OK,
		 * Messagebox.EXCLAMATION); }
		 */ }

	@Command
	@NotifyChange({ "incomingProtocol", "incomingCreator", "incomingRoutingDate", "incomingRecipient",
			"incomingInternalRecipient", "incomingDateFrom", "incomingDateTo", "selectedIncomingProtocols" })
	public void clearIncomingProtocolsSearch() {
		selectedIncomingProtocols = null;
		initIncoming();
	}

	@Command
	public void addNewIncomingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshIncomingProtocols"));
		Window incomingWin = (Window) Executions.createComponents(IncomingVM.PAGE, view, params);
		incomingWin.setClosable(true);
		incomingWin.doModal();
	}

	/* outgoing protocols related */
	@GlobalCommand
	@NotifyChange({ "outgoingProtocolsTotalSize", "outgoingProtocols" })
	public void refreshOutgoingProtocols() {
		// initOutgoing();
		// outgoingProtocolsActivePage = 0;
		int startIndex = outgoingProtocolsActivePage * outgoingProtocolsPageSize;
		fetchOutgoing(startIndex);
	}

	@NotifyChange({ "outgoingProtocolsActivePage", "outgoingProtocolsTotalSize", "outgoingProtocols" })
	@Command
	public void sortOutgoingProtocols(@ContextParam(ContextType.TRIGGER_EVENT) SortEvent sortEvent) throws Exception {
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		setSortingListheader(header);
		outgoingProtocolsActivePage = 0;
		fetchOutgoing(0);
	}

	@Command
	@NotifyChange({ "outgoingProtocolsActivePage", "outgoingProtocolsTotalSize", "outgoingProtocols" })
	public void pageOutgoingProtocols() throws Exception {
		int startIndex = outgoingProtocolsActivePage * outgoingProtocolsPageSize;
		fetchOutgoing(startIndex);
	}

	@Command
	@NotifyChange("selectedOutgoingProtocol")
	public void selectOutgoingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		Integer id = selectedOutgoingProtocol.getId();
		// create modal window for selected protocol
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_ID, id);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshOutgoingProtocols"));
		Window incomingWin = (Window) Executions.createComponents(OutgoingVM.PAGE, view, params);
		incomingWin.setClosable(true);
		incomingWin.doModal();
		// clear seleted protocol
		selectedOutgoingProtocol = null;
	}

	@Command
	@NotifyChange({ "outgoingProtocolsActivePage", "outgoingProtocolsTotalSize", "outgoingProtocols" })
	public void searchOutgoingProtocols() {
		outgoingProtocolsActivePage = 0;
		fetchOutgoing(0);

		if (outgoingProtocols.isEmpty()) {
			Messagebox.show(Labels.getLabel("search.notFound"), Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Command
	@NotifyChange({ "outgoingProtocol", "outgoingDateFrom", "outgoingDateTo" })
	public void clearOutgoingProtocolsSearch() {
		initOutgoing();
	}

	@Command
	public void addNewOutgoingProtocol(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshOutgoingProtocols"));
		Window outgoingWin = (Window) Executions.createComponents(OutgoingVM.PAGE, view, params);
		outgoingWin.setClosable(true);
		outgoingWin.doModal();
	}

	/* search protocols related */
	@Command
	@NotifyChange({ "searchProtocolsActivePage", "searchProtocolsTotalSize", "searchProtocols" })
	public void refreshSearchProtocols() {
		if (searchProtocolBooks.isEmpty()) {
			searchProtocolsTotalSize = 0;
			searchProtocols = null;
			return;
		}
		int startIndex = searchProtocolsActivePage * searchProtocolsPageSize;
		search(startIndex);
	}

	@NotifyChange({ "searchProtocolsActivePage", "searchProtocolsTotalSize", "searchProtocols" })
	@Command
	public void sortSearchProtocols(@ContextParam(ContextType.TRIGGER_EVENT) SortEvent sortEvent) throws Exception {
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		setSortingListheader(header);
		searchProtocolsActivePage = 0;
		search(0);
	}

	@Command
	@NotifyChange({ "searchProtocolsActivePage", "searchProtocolsTotalSize", "searchProtocols" })
	public void pageSearchProtocols() {
		int startIndex = searchProtocolsActivePage * searchProtocolsPageSize;
		search(startIndex);
	}

	@Command
	@NotifyChange("selectedSearchProtocol")
	public void selectSearchProtocol(@ContextParam(ContextType.VIEW) Component view) {
		Integer id = selectedSearchProtocol.getId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_ID, id);
		Department creatorDepartment = selectedSearchProtocol.getCreator() != null
				? selectedSearchProtocol.getCreator().getDepartment() : null;
		boolean displayOnly = true;
		if (creatorDepartment != null && creatorDepartment.equals(getUserInSessionDepartment())) {
			displayOnly = false;
		}
		params.put(ProtocolVM.PARAM_DISPLAY_ONLY, displayOnly);
		switch (selectedSearchProtocol.getDirection()) {
		case INCOMING:
			Window incomingWin = (Window) Executions.createComponents(IncomingVM.PAGE, view, params);
			incomingWin.setClosable(true);
			incomingWin.doModal();
			break;
		case OUTGOING:
			Window outgoingWin = (Window) Executions.createComponents(OutgoingVM.PAGE, view, params);
			outgoingWin.setClosable(true);
			outgoingWin.doModal();
			break;
		default:
			break;
		}
		selectedSearchProtocol = null;
	}

	@Command
	@NotifyChange("searchDepartmentSelection")
	public void clearSearchDepartments() {
		searchDepartmentSelection.setActive(FALSE);
		// searchDepartmentSelection = new EntitySelection<>();
		// searchDepartmentSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	@Command
	@NotifyChange("searchDepartmentSelection")
	public void searchSearchDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		searchDepartmentSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		searchDepartmentSelection.setSelectedEntity(null);
		searchDepartmentSelection.setActivePage(0);
		Object[] results = searchProtocolDepartments(searchDepartmentSelection.getTerm(), 0,
				searchDepartmentSelection.getPageSize());
		searchDepartmentSelection.setTotalSize((Integer) results[0]);
		searchDepartmentSelection.setEntites((List<Department>) results[1]);
		searchDepartmentSelection.setActive(searchDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("searchDepartmentSelection")
	public void listSearchDepartments() {
		// if (searchDepartmentSelection.getEntites() != null &&
		// !searchDepartmentSelection.getEntites().isEmpty()) {
		// return;
		// }
		searchDepartmentSelection.setTerm("");
		searchDepartmentSelection.setActivePage(0);
		Object[] results = searchProtocolDepartments(searchDepartmentSelection.getTerm(), 0,
				searchDepartmentSelection.getPageSize());
		searchDepartmentSelection.setTotalSize((Integer) results[0]);
		searchDepartmentSelection.setEntites((List<Department>) results[1]);
		searchDepartmentSelection.setActive(searchDepartmentSelection.getTotalSize() > 0);
	}

	@Command
	@NotifyChange("searchDepartmentSelection")
	public void pageSearchDepartments() {
		int startIndex = searchDepartmentSelection.getActivePage() * searchDepartmentSelection.getPageSize();
		Object[] results = searchProtocolDepartments(searchDepartmentSelection.getTerm(), startIndex,
				searchDepartmentSelection.getPageSize());
		searchDepartmentSelection.setTotalSize((Integer) results[0]);
		searchDepartmentSelection.setEntites((List<Department>) results[1]);
	}

	@Command
	@NotifyChange("searchDepartmentSelection")
	public void selectSearchDepartment() {
		searchDepartmentSelection.setActive(FALSE);
		// searchDepartmentSelection.setEntites(null/);
	}

	@Command
	@NotifyChange({ "searchProtocolDocumentTypeSelection", "searchDepartmentSelectionEnabled", "searchDepartment",
			"searchProtocolBookSelectionActive" })
	public void selectSearchProtocolBook() {
		searchProtocolBookSelectionActive = false;
		initSearchProtocolDocumentTypeSelection();
		if (searchProtocolBook.getType().equals(ProtocolBookType.SECRET)) {
			clearSearchDepartments();
			searchDepartmentSelection.setEntites(new ArrayList<>());
			searchDepartmentSelection.getEntites().add(getUserInSessionDepartment());
			searchDepartmentSelection.setSelectedEntity(getUserInSessionDepartment());
		}
	}

	@Command
	@NotifyChange("searchSender")
	public void clearSearchSender() {
		searchSender = null;
	}

	@Command
	@NotifyChange("searchSenderSelection")
	public void clearSearchSenders() {
		searchSenderSelection = new EntitySelection<>();
		searchSenderSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	@Command
	@NotifyChange("searchSenderSelection")
	public void searchSearchSendersByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		searchSenderSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		searchSenderSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchSenderSelection.getTerm(),
				new CorrespondentDirection[] { SENDER }, CorrespondentType.valueOf(searchSenderType.toString()), 0,
				searchSenderSelection.getPageSize());
		searchSenderSelection.setTotalSize((Integer) results[0]);
		searchSenderSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchSenderSelection")
	public void listSearchSenders() {
		if (searchSenderSelection.getEntites() != null && !searchSenderSelection.getEntites().isEmpty()) {
			return;
		}
		searchSenderSelection.setTerm("");
		searchSenderSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchSenderSelection.getTerm(),
				new CorrespondentDirection[] { SENDER }, CorrespondentType.valueOf(searchSenderType.toString()), 0,
				searchSenderSelection.getPageSize());
		searchSenderSelection.setTotalSize((Integer) results[0]);
		searchSenderSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchSenderSelection")
	public void pageSearchSenders() {
		int startIndex = searchSenderSelection.getActivePage() * searchSenderSelection.getPageSize();
		Object[] results = searchCorrespondentDependencies(searchSenderSelection.getTerm(),
				new CorrespondentDirection[] { SENDER }, CorrespondentType.valueOf(searchSenderType.toString()),
				startIndex, searchSenderSelection.getPageSize());
		searchSenderSelection.setTotalSize((Integer) results[0]);
		searchSenderSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange({ "searchSender", "searchSenderSelection" })
	public void selectSearchSender() {
		searchSender = getProtocolCorrespondentFromSelection(CorrespondentType.valueOf(searchSenderType.toString()),
				searchSenderSelection);
		searchSenderSelection.setEntites(null);
		searchSenderSelection.setSelectedEntity(null);
		clearSearchSenders();
	}

	@Command
	@NotifyChange("searchRecipient")
	public void clearSearchRecipient() {
		searchRecipient = null;
	}

	@Command
	@NotifyChange("searchRecipientSelection")
	public void clearSearchRecipients() {
		searchRecipientSelection = new EntitySelection<>();
		searchRecipientSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	@Command
	@NotifyChange("searchRecipientSelection")
	public void searchSearchRecipientsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		searchRecipientSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		searchRecipientSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchRecipientSelection.getTerm(),
				new CorrespondentDirection[] { RECIPIENT, INTERNAL_RECIPIENT },
				CorrespondentType.valueOf(searchRecipientType.toString()), 0, searchRecipientSelection.getPageSize());
		searchRecipientSelection.setTotalSize((Integer) results[0]);
		searchRecipientSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchRecipientSelection")
	public void listSearchRecipients() {
		if (searchRecipientSelection.getEntites() != null && !searchRecipientSelection.getEntites().isEmpty()) {
			return;
		}
		searchRecipientSelection.setTerm("");
		searchRecipientSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchRecipientSelection.getTerm(),
				new CorrespondentDirection[] { RECIPIENT, INTERNAL_RECIPIENT },
				CorrespondentType.valueOf(searchRecipientType.toString()), 0, searchRecipientSelection.getPageSize());
		searchRecipientSelection.setTotalSize((Integer) results[0]);
		searchRecipientSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchRecipientSelection")
	public void pageSearchRecipients() {
		int startIndex = searchRecipientSelection.getActivePage() * searchRecipientSelection.getPageSize();
		Object[] results = searchCorrespondentDependencies(searchRecipientSelection.getTerm(),
				new CorrespondentDirection[] { RECIPIENT, INTERNAL_RECIPIENT },
				CorrespondentType.valueOf(searchRecipientType.toString()), startIndex,
				searchRecipientSelection.getPageSize());
		searchRecipientSelection.setTotalSize((Integer) results[0]);
		searchRecipientSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange({ "searchRecipient", "searchRecipientSelection" })
	public void selectSearchRecipient() {
		searchRecipient = getProtocolCorrespondentFromSelection(
				CorrespondentType.valueOf(searchRecipientType.toString()), searchRecipientSelection);
		searchRecipientSelection.setEntites(null);
		searchRecipientSelection.setSelectedEntity(null);
		clearSearchRecipients();
	}

	@Command
	@NotifyChange("searchTransactor")
	public void clearSearchTransactor() {
		searchTransactor = null;
	}

	@Command
	@NotifyChange("searchTransactorSelection")
	public void clearSearchTransactors() {
		searchTransactorSelection = new EntitySelection<>();
		searchTransactorSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	@Command
	@NotifyChange("searchTransactorSelection")
	public void searchSearchTransactorsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		searchTransactorSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		searchTransactorSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchTransactorSelection.getTerm(),
				new CorrespondentDirection[] { TRANSACTOR }, CorrespondentType.valueOf(searchTransactorType.toString()),
				0, searchTransactorSelection.getPageSize());
		searchTransactorSelection.setTotalSize((Integer) results[0]);
		searchTransactorSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchTransactorSelection")
	public void listSearchTransactors() {
		if (searchTransactorSelection.getEntites() != null && !searchTransactorSelection.getEntites().isEmpty()) {
			return;
		}
		searchTransactorSelection.setTerm("");
		searchTransactorSelection.setActivePage(0);
		Object[] results = searchCorrespondentDependencies(searchTransactorSelection.getTerm(),
				new CorrespondentDirection[] { TRANSACTOR }, CorrespondentType.valueOf(searchTransactorType.toString()),
				0, searchTransactorSelection.getPageSize());
		searchTransactorSelection.setTotalSize((Integer) results[0]);
		searchTransactorSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange("searchTransactorSelection")
	public void pageSearchTransactors() {
		int startIndex = searchTransactorSelection.getActivePage() * searchTransactorSelection.getPageSize();
		Object[] results = searchCorrespondentDependencies(searchTransactorSelection.getTerm(),
				new CorrespondentDirection[] { TRANSACTOR }, CorrespondentType.valueOf(searchTransactorType.toString()),
				startIndex, searchTransactorSelection.getPageSize());
		searchTransactorSelection.setTotalSize((Integer) results[0]);
		searchTransactorSelection.setEntites((List<Object>) results[1]);
	}

	@Command
	@NotifyChange({ "searchTransactor", "searchTransactorSelection" })
	public void selectSearchTransactor() {
		searchTransactor = getProtocolCorrespondentFromSelection(
				CorrespondentType.valueOf(searchTransactorType.toString()), searchTransactorSelection);
		searchTransactorSelection.setEntites(null);
		searchTransactorSelection.setSelectedEntity(null);
		clearSearchTransactors();
	}

	@Command
	@NotifyChange("searchProtocolDocumentTypeSelection")
	public void searchProtocolDocumentTypes(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		searchProtocolDocumentTypeSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		searchProtocolDocumentTypeSelection.setActivePage(0);
		Object[] results = searchDocumentTypes(searchProtocolDocumentTypeSelection.getTerm(), searchProtocolBook, 0,
				searchProtocolDocumentTypeSelection.getPageSize());
		searchProtocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		searchProtocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("searchProtocolDocumentTypeSelection")
	public void listProtocolDocumentTypes() {
		if (searchProtocolDocumentTypeSelection.getEntites() != null
				&& !searchProtocolDocumentTypeSelection.getEntites().isEmpty()) {
			return;
		}
		searchProtocolDocumentTypeSelection.setTerm("");
		searchProtocolDocumentTypeSelection.setActivePage(0);
		Object[] results = searchDocumentTypes(searchProtocolDocumentTypeSelection.getTerm(), searchProtocolBook, 0,
				searchProtocolDocumentTypeSelection.getPageSize());
		searchProtocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		searchProtocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("searchProtocolDocumentTypeSelection")
	public void clearProtocolDocumentTypes() {
		searchProtocolDocumentTypeSelection.setEntites(new ArrayList<>());
		searchProtocolDocumentTypeSelection.setTotalSize(0);
		searchProtocolDocumentTypeSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("searchProtocolDocumentTypeSelection")
	public void pageProtocolDocumentTypes() {
		int startIndex = searchProtocolDocumentTypeSelection.getActivePage()
				* searchProtocolDocumentTypeSelection.getPageSize();
		Object[] results = searchDocumentTypes(searchProtocolDocumentTypeSelection.getTerm(), searchProtocolBook,
				startIndex, searchProtocolDocumentTypeSelection.getPageSize());
		searchProtocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		searchProtocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("searchProtocolDocumentTypeSelection")
	public void selectProtocolDocumentType() {
		searchProtocolDocumentTypeSelection.setEntites(null);
		searchProtocolDocumentTypeSelection.setActivePage(0);
	}

	@Command
	@NotifyChange({ "searchCriteriaEnabled", "searchIncoming", "searchOutgoing", "searchProtocolsActivePage",
			"searchProtocolsTotalSize", "searchProtocols" })
	public void searchProtocols() {

		if (searchIncoming == false && searchOutgoing == false) {
			searchIncoming = true;
			searchOutgoing = true;
		}

		searchProtocolsActivePage = 0;
		search(null);
		if (searchProtocolsTotalSize == null) {
			Messagebox.show(
					Labels.getLabel("search.content.tooManyLocalResults",
							new Object[] { getContentSearchResultsLimit() }),
					Labels.getLabel("warning.title"), Messagebox.OK, Messagebox.EXCLAMATION);
		} else if (searchProtocolsTotalSize == -1) {
			searchProtocolsTotalSize = null;
			Messagebox.show(
					Labels.getLabel("search.content.tooManyResults", new Object[] { getContentSearchResultsLimit() }),
					Labels.getLabel("warning.title"), Messagebox.OK, Messagebox.EXCLAMATION);
		} else if (searchProtocolsTotalSize == -2) {
			searchProtocolsTotalSize = null;
			Messagebox.show(Labels.getLabel("error.openKM"), Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
		} else if (searchProtocolsTotalSize == 0) {
			searchProtocolsTotalSize = null;
			Messagebox.show(Labels.getLabel("search.notFound"), Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		} else {
			searchCriteriaEnabled = FALSE;
		}
	}

	@Command
	@NotifyChange({ "searchIncoming", "searchOutgoing", "protocolNumber", "searchDateFrom", "searchDateTo",
			"incomingProtocolNumber", "searchIncomingDateFrom", "searchIncomingDateFrom", "subject", "comments",
			"distributionMethod", "distributionMethodDetails", "searchProtocolDocumentTypeSelection",
			"searchDepartment", "keywords", "attachedNumber", "attachedDescription", "content", "contacts", "contact",
			"toContacts", "toContact", "searchDepartmentSelection", "searchDepartmentSelectionEnabled",
			"searchProtocolBook", "searchProtocolBookSelectionActive", "searchRoutingDate", "searchDeliveryDate",
			"searchSenderRecipientTypes", "searchSenderType", "searchSender", "searchSenderText",
			"searchSenderSelection", "searchRecipientType", "searchRecipient", "searchRecipientText",
			"searchRecipientSelection", "searchTransactorTypes", "searchTransactorType", "searchTransactor",
			"searchTransactorText", "searchTransactorSelection", "searchProtocols", "searchProtocolsTotalSize",
			"searchProtocolsActivePage" })
	public void clearSearch() {
		initSearch();
	}

	/* Batch route related */
	@GlobalCommand
	@Command
	@NotifyChange({ "selectedBatchRouteProtocols", "batchRouteProtocolsActivePage", "batchRouteProtocolsTotalSize",
			"batchRouteProtocols" })
	public void refreshBatchRouteProtocols() {
		selectedBatchRouteProtocols = null;
		int startIndex = batchRouteProtocolsActivePage * batchRouteProtocolsPageSize;
		searchBatchRouteProtocols(startIndex);
	}

	@NotifyChange({ "batchRouteProtocolsActivePage", "batchRouteTotalSize", "batchRouteProtocols" })
	@Command
	public void sortBatchRouteProtocols(@ContextParam(ContextType.TRIGGER_EVENT) SortEvent sortEvent) throws Exception {
		// prevent default sorting from getting called
		sortEvent.stopPropagation();
		// setup sorting
		Listheader header = (Listheader) sortEvent.getTarget();
		setSortingListheader(header);
		batchRouteProtocolsActivePage = 0;
		searchBatchRouteProtocols(0);
	}

	@Command
	@NotifyChange({ "batchRouteProtocolsActivePage", "batchRouteProtocolsTotalSize", "batchRouteProtocols" })
	public void pageBatchRouteProtocols() {
		int startIndex = batchRouteProtocolsActivePage * batchRouteProtocolsPageSize;
		searchBatchRouteProtocols(startIndex);
	}

	@Command
	@NotifyChange({ "batchRouteDepartments", "batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage" })
	public void searchBatchRouteDepartmentsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		batchRouterDepartmentsTerm = StringUtils.trimToNull(event.getValue());

		if (batchRouterDepartmentsTerm == null) {
			batchRouteDepartments = null;
			return;
		}
		batchRouteDepartmentsActivePage = 0;
		Object[] results = searchDepartmentsByType(batchRouterDepartmentsTerm, 0, batchRouteDepartmentsPageSize);
		batchRouteDepartmentsTotalSize = (Integer) results[0];
		batchRouteDepartments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "batchRouteDepartments", "batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage" })
	public void listBatchRouteDepartments() {
		if (batchRouteDepartments != null && !batchRouteDepartments.isEmpty()) {
			return;
		}
		batchRouterDepartmentsTerm = "";
		batchRouteDepartmentsActivePage = 0;
		Object[] results = searchDepartmentsByType(batchRouterDepartmentsTerm, 0, batchRouteDepartmentsPageSize);
		batchRouteDepartmentsTotalSize = (Integer) results[0];
		batchRouteDepartments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "batchRouteDepartments", "batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage" })
	public void clearBatchRouteDepartments() {
		batchRouteDepartments = null;
		batchRouteDepartmentsTotalSize = 0;
		batchRouteDepartmentsActivePage = 0;
	}

	@Command
	@NotifyChange({ "batchRouteDepartments", "batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage" })
	public void pageBatchRouteDepartments() {
		int startIndex = batchRouteDepartmentsActivePage * batchRouteDepartmentsPageSize;
		Object[] results = searchDepartmentsByType(batchRouterDepartmentsTerm, startIndex,
				batchRouteDepartmentsPageSize);
		batchRouteDepartmentsTotalSize = (Integer) results[0];
		batchRouteDepartments = (List<Department>) results[1];
	}

	@Command
	@NotifyChange({ "batchRouteDepartments", "batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage" })
	public void selectBatchRouteDepartment() {
		batchRouteDepartments = null;
		batchRouteDepartmentsTotalSize = 0;
		batchRouteDepartmentsActivePage = 0;
	}

	@Command
	@NotifyChange({ "batchRouteIncoming", "batchRouteOutgoing", "batchRouteProtocolsActivePage",
			"batchRouteProtocolsTotalSize", "batchRouteProtocols", "selectedBatchRouteProtocols" })
	public void searchBatchRouteProtocols() {
		selectedBatchRouteProtocols = null;
		batchRouteProtocolsActivePage = 0;
		if (batchRouteIncoming == false && batchRouteOutgoing == false) {
			batchRouteIncoming = true;
			batchRouteOutgoing = true;
		}

		searchBatchRouteProtocols(0);
		if (batchRouteProtocolsTotalSize == 0) {
			Messagebox.show(Labels.getLabel("search.notFound"), Labels.getLabel("search.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	@Command
	@NotifyChange({ "batchRouteIncoming", "batchRouteOutgoing", "batchRouteDepartments", "batchRouteDepartment",
			"batchRouteDepartmentsTotalSize", "batchRouteDepartmentsActivePage", "selectedBatchRouteProtocols" })
	public void clearBatchRouteProtocolsSearch() {
		initBatchRoute();
		selectedBatchRouteProtocols = null;
	}

	@Command
	@NotifyChange("selectedIncomingProtocols")
	public void selectBatchRouteProtocol(@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("protocol") Protocol selectedBatchRouteProtocol) {
		Integer id = selectedBatchRouteProtocol.getId();
		// create modal window for selected protocol
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_KEY_ID, id);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshBatchRouteProtocols"));
		Window incomingWin = (Window) Executions
				.createComponents(selectedBatchRouteProtocol.getDirection().equals(Direction.INCOMING) ? IncomingVM.PAGE
						: OutgoingVM.PAGE, view, params);
		incomingWin.setClosable(true);
		incomingWin.doModal();
		selectedBatchRouteProtocol = null;
	}

	@Command
	public void batchRouteProtocols(@ContextParam(ContextType.VIEW) Component view) {

		// create modal window for batch routing selected protocols
		Map<String, Object> params = new HashMap<String, Object>();
		if (selectedBatchRouteProtocols.size() == 1) {
			Protocol protocol = selectedBatchRouteProtocols.get(0);
			params.put(BatchRouteVM.PARAM_PROTOCOL, protocol);

			ProtocolService protocolService = SpringUtil.getApplicationContext().getBean(ProtocolService.class);
			switch (protocol.getDirection()) {
			case INCOMING:
				List<ProtocolCorrespondent> internalRecipientsApplicableForRouting = protocolService
						.getInternalRecipientsApplicableForRouting(new ArrayList<>(protocol.getInternalRecipients()));
				params.put(BatchRouteVM.PARAM_BATCH_ROUTE_RECIPIENTS, internalRecipientsApplicableForRouting);
				break;
			case OUTGOING:
				List<ProtocolCorrespondent> recipientsApplicableForRouting = protocolService
						.getRecipientsApplicableForRouting(new ArrayList<>(protocol.getRecipients()));
				params.put(BatchRouteVM.PARAM_BATCH_ROUTE_RECIPIENTS, recipientsApplicableForRouting);
				break;
			default:
				break;
			}

		} else {
			params.put(BatchRouteVM.PARAM_BATCH_ROUTE_PROTOCOLS, selectedBatchRouteProtocols);
			List<Department> batchRouteRecipients;
			if (batchRouteDepartment == null) {
				DepartmentDAO departmentDAO = SpringUtil.getApplicationContext().getBean(DepartmentDAO.class);
				batchRouteRecipients = departmentDAO.findDepartmentsByTerm("", DepartmentDAO.PROTOCOL_DEPARTMENT_TYPES,
						null, null);
			} else {
				batchRouteRecipients = new ArrayList<Department>();
				batchRouteRecipients.add(batchRouteDepartment);
			}
			params.put(BatchRouteVM.PARAM_BATCH_ROUTE_DEPARTMENTS, batchRouteRecipients);
		}
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshBatchRouteProtocols"));

		Window batchRouteWin = (Window) Executions.createComponents(BatchRouteVM.PAGE, view, params);
		batchRouteWin.setClosable(true);
		batchRouteWin.doModal();
	}

	@Command
	public void receiveProtocols(@ContextParam(ContextType.VIEW) Component view) {
		today = DateUtils.truncate(new Date(), Calendar.DATE);
		// create modal window for batch protocol reception
		Map<String, Object> params = new HashMap<String, Object>();
		List<ProtocolCorrespondent> recipients = new ArrayList<ProtocolCorrespondent>();
		for (Protocol selectedIncomingProtocol : selectedIncomingProtocols) {
			switch (selectedIncomingProtocol.getDirection()) {
			case INCOMING:
				selectedIncomingProtocol.getInternalRecipients().stream()
						.filter(ir -> ir.getDepartment().equals(getUserInSessionDepartment())
								&& ir.getDeliveryDate() == null && ir.getRoutingDate() != null
								&& (today.equals(ir.getRoutingDate()) || today.after(ir.getRoutingDate())))
						.forEach(recipients::add);
				break;
			case OUTGOING:
				selectedIncomingProtocol.getRecipients().stream()
						.filter(r -> r.getType().equals(CorrespondentType.DEPARTMENT)
								&& r.getDepartment().equals(getUserInSessionDepartment()) && r.getDeliveryDate() == null
								&& r.getRoutingDate() != null
								&& (today.equals(r.getRoutingDate()) || today.after(r.getRoutingDate())))
						.forEach(recipients::add);
				break;
			default:
				break;
			}
		}
		params.put(ReceptionVM.PARAM_RECIPIENTS, recipients);
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshIncomingProtocols"));

		Window batchRouteWin = (Window) Executions.createComponents(ReceptionVM.PAGE, view, params);
		batchRouteWin.setClosable(true);
		batchRouteWin.doModal();
	}

	public boolean isProtocolReceptionEnabled() {
		today = DateUtils.truncate(new Date(), Calendar.DATE);

		if (!incomingRoutingDate) {
			return false;
		}
		if (selectedIncomingProtocols == null || selectedIncomingProtocols.isEmpty()) {
			return false;
		}
		boolean anyRecipientsApplicantForReception = false;
		for (Protocol selectedProtocol : selectedIncomingProtocols) {
			switch (selectedProtocol.getDirection()) {
			case INCOMING:
				anyRecipientsApplicantForReception = anyRecipientsApplicantForReception
						|| selectedProtocol.getInternalRecipients().stream()
								.anyMatch(ir -> ir.getDepartment().equals(getUserInSessionDepartment())
										&& ir.getRoutingDate() != null && (today.equals(ir.getRoutingDate())
												|| today.after(ir.getRoutingDate())));
				break;
			case OUTGOING:
				anyRecipientsApplicantForReception = anyRecipientsApplicantForReception || selectedProtocol
						.getRecipients().stream()
						.anyMatch(r -> r.getType().equals(CorrespondentType.DEPARTMENT)
								&& r.getDepartment().equals(getUserInSessionDepartment()) && r.getRoutingDate() != null
								&& (today.equals(r.getRoutingDate()) || today.after(r.getRoutingDate())));
				break;
			default:
				break;
			}
		}
		return anyRecipientsApplicantForReception;
	}

	public boolean isSearchDepartmentSelectionEnabled() {
		if (SecurityUtil.isNoneGranted(Permission.SEARCH_ALL_DEPARTMENTS.toString())) {
			return false;
		}
		if (searchProtocolBook != null && searchProtocolBook.getType().equals(ProtocolBookType.SECRET)) {
			return false;
		}
		return true;
	}

	public boolean isSearchEnabled() {
		if (SecurityUtil.isNoneGranted(Permission.SEARCH_BOOK.toString())) {
			return false;
		}

		if (searchProtocolBooks.isEmpty()) {
			return false;
		}

		if (getUserInSessionDepartment() == null) {
			return false;
		}

		return true;
	}

	public boolean isAddIncomingProtocolEnabled() {
		return SecurityUtil.isAllGranted(Permission.WRITE_INCOMING.toString())
				&& !Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType());
	}

	public boolean isAddOutgoingProtocolEnabled() {
		return SecurityUtil.isAllGranted(Permission.WRITE_OUTGOING.toString())
				&& !Name.UNIT_DEPARTMENT.equals(getUserInSessionDepartmentType());
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public List<ProtocolBook> getIncomingProtocolBooks() {
		return incomingProtocolBooks;
	}

	public void setIncomingProtocolBooks(List<ProtocolBook> incomingProtocolBooks) {
		this.incomingProtocolBooks = incomingProtocolBooks;
	}

	public Protocol getIncomingProtocol() {
		return incomingProtocol;
	}

	public void setIncomingProtocol(Protocol incomingProtocol) {
		this.incomingProtocol = incomingProtocol;
	}

	public List<Protocol> getIncomingProtocols() {
		return incomingProtocols;
	}

	public void setIncomingProtocols(List<Protocol> incomingProtocols) {
		this.incomingProtocols = incomingProtocols;
	}

	public Integer getIncomingProtocolsTotalSize() {
		return incomingProtocolsTotalSize;
	}

	public void setIncomingProtocolsTotalSize(Integer incomingProtocolsTotalSize) {
		this.incomingProtocolsTotalSize = incomingProtocolsTotalSize;
	}

	public Integer getIncomingProtocolsPageSize() {
		return incomingProtocolsPageSize;
	}

	public void setIncomingProtocolsPageSize(Integer incomingProtocolsPageSize) {
		this.incomingProtocolsPageSize = incomingProtocolsPageSize;
	}

	public Integer getIncomingProtocolsActivePage() {
		return incomingProtocolsActivePage;
	}

	public void setIncomingProtocolsActivePage(Integer incomingProtocolsActivePage) {
		this.incomingProtocolsActivePage = incomingProtocolsActivePage;
	}

	public List<Protocol> getSelectedIncomingProtocols() {
		return selectedIncomingProtocols;
	}

	public void setSelectedIncomingProtocols(List<Protocol> selectedIncomingProtocols) {
		this.selectedIncomingProtocols = selectedIncomingProtocols;
	}

	public Protocol getSelectedIncomingProtocol() {
		return selectedIncomingProtocol;
	}

	public void setSelectedIncomingProtocol(Protocol selectedIncomingProtocol) {
		this.selectedIncomingProtocol = selectedIncomingProtocol;
	}

	public Date getIncomingDateFrom() {
		return incomingDateFrom;
	}

	public void setIncomingDateFrom(Date incomingDateFrom) {
		this.incomingDateFrom = incomingDateFrom;
	}

	public Date getIncomingDateTo() {
		return incomingDateTo;
	}

	public void setIncomingDateTo(Date incomingDateTo) {
		this.incomingDateTo = incomingDateTo;
	}

	public Boolean getIncomingCreator() {
		return incomingCreator;
	}

	public void setIncomingCreator(Boolean incomingCreator) {
		this.incomingCreator = incomingCreator;
	}

	public Boolean getIncomingRoutingDate() {
		return incomingRoutingDate;
	}

	public void setIncomingRoutingDate(Boolean incomingRoutingDate) {
		this.incomingRoutingDate = incomingRoutingDate;
	}

	public List<ProtocolBook> getOutgoingProtocolBooks() {
		return outgoingProtocolBooks;
	}

	public void setOutgoingProtocolBooks(List<ProtocolBook> outgoingProtocolBooks) {
		this.outgoingProtocolBooks = outgoingProtocolBooks;
	}

	public Protocol getOutgoingProtocol() {
		return outgoingProtocol;
	}

	public void setOutgoingProtocol(Protocol outgoingProtocol) {
		this.outgoingProtocol = outgoingProtocol;
	}

	public List<Protocol> getOutgoingProtocols() {
		return outgoingProtocols;
	}

	public void setOutgoingProtocols(List<Protocol> outgoingProtocols) {
		this.outgoingProtocols = outgoingProtocols;
	}

	public Integer getOutgoingProtocolsTotalSize() {
		return outgoingProtocolsTotalSize;
	}

	public void setOutgoingProtocolsTotalSize(Integer outgoingProtocolsTotalSize) {
		this.outgoingProtocolsTotalSize = outgoingProtocolsTotalSize;
	}

	public Integer getOutgoingProtocolsPageSize() {
		return outgoingProtocolsPageSize;
	}

	public void setOutgoingProtocolsPageSize(Integer outgoingProtocolsPageSize) {
		this.outgoingProtocolsPageSize = outgoingProtocolsPageSize;
	}

	public Integer getOutgoingProtocolsActivePage() {
		return outgoingProtocolsActivePage;
	}

	public void setOutgoingProtocolsActivePage(Integer outgoingProtocolsActivePage) {
		this.outgoingProtocolsActivePage = outgoingProtocolsActivePage;
	}

	public Protocol getSelectedOutgoingProtocol() {
		return selectedOutgoingProtocol;
	}

	public void setSelectedOutgoingProtocol(Protocol selectedOutgoingProtocol) {
		this.selectedOutgoingProtocol = selectedOutgoingProtocol;
	}

	public Date getOutgoingDateFrom() {
		return outgoingDateFrom;
	}

	public void setOutgoingDateFrom(Date outgoingDateFrom) {
		this.outgoingDateFrom = outgoingDateFrom;
	}

	public Date getOutgoingDateTo() {
		return outgoingDateTo;
	}

	public void setOutgoingDateTo(Date outgoingDateTo) {
		this.outgoingDateTo = outgoingDateTo;
	}

	public Boolean getSearchIncoming() {
		return searchIncoming;
	}

	public void setSearchIncoming(Boolean searchIncoming) {
		this.searchIncoming = searchIncoming;
	}

	public Boolean getSearchOutgoing() {
		return searchOutgoing;
	}

	public void setSearchOutgoing(Boolean searchOutgoing) {
		this.searchOutgoing = searchOutgoing;
	}

	public Integer getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Date getSearchDateFrom() {
		return searchDateFrom;
	}

	public void setSearchDateFrom(Date searchDateFrom) {
		this.searchDateFrom = searchDateFrom;
	}

	public Date getSearchDateTo() {
		return searchDateTo;
	}

	public void setSearchDateTo(Date searchDateTo) {
		this.searchDateTo = searchDateTo;
	}

	public String getIncomingProtocolNumber() {
		return incomingProtocolNumber;
	}

	public void setIncomingProtocolNumber(String incomingProtocolNumber) {
		this.incomingProtocolNumber = incomingProtocolNumber;
	}

	public Date getSearchIncomingDateFrom() {
		return searchIncomingDateFrom;
	}

	public void setSearchIncomingDateFrom(Date incomingDate) {
		this.searchIncomingDateFrom = incomingDate;
	}

	public Date getSearchIncomingDateTo() {
		return searchIncomingDateTo;
	}

	public void setSearchIncomingDateTo(Date searchIncomingDateTo) {
		this.searchIncomingDateTo = searchIncomingDateTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getAttachedNumber() {
		return attachedNumber;
	}

	public void setAttachedNumber(Integer attachedNumber) {
		this.attachedNumber = attachedNumber;
	}

	public String getAttachedDescription() {
		return attachedDescription;
	}

	public void setAttachedDescription(String attachedDescription) {
		this.attachedDescription = attachedDescription;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getDistributionMethodDetails() {
		return distributionMethodDetails;
	}

	public void setDistributionMethodDetails(String distributionMethodDetails) {
		this.distributionMethodDetails = distributionMethodDetails;
	}

	public List<ProtocolBook> getSearchProtocolBooks() {
		return searchProtocolBooks;
	}

	public void setSearchProtocolBooks(List<ProtocolBook> searchProtocolBooks) {
		this.searchProtocolBooks = searchProtocolBooks;
	}

	public ProtocolBook getSearchProtocolBook() {
		return searchProtocolBook;
	}

	public void setSearchProtocolBook(ProtocolBook protocolBook) {
		this.searchProtocolBook = protocolBook;
	}

	public boolean isSearchProtocolBookSelectionActive() {
		return searchProtocolBookSelectionActive;
	}

	public void setSearchProtocolBookSelectionActive(boolean searchProtocolBookSelectionActive) {
		this.searchProtocolBookSelectionActive = searchProtocolBookSelectionActive;
	}

	public EntitySelection<DocumentType> getSearchProtocolDocumentTypeSelection() {
		return searchProtocolDocumentTypeSelection;
	}

	public void setSearchProtocolDocumentTypeSelection(
			EntitySelection<DocumentType> searchProtocolDocumentTypeSelection) {
		this.searchProtocolDocumentTypeSelection = searchProtocolDocumentTypeSelection;
	}

	public EntitySelection<DepartmentType> getSearchProtocolDepartmentTypeSelection() {
		return searchProtocolDepartmentTypeSelection;
	}

	public void setSearchProtocolDepartmentTypeSelection(
			EntitySelection<DepartmentType> searchProtocolDepartmentTypeSelection) {
		this.searchProtocolDepartmentTypeSelection = searchProtocolDepartmentTypeSelection;
	}

	public EntitySelection<Department> getSearchDepartmentSelection() {
		return searchDepartmentSelection;
	}

	public void setSearchDepartmentSelection(EntitySelection<Department> searchDepartmentSelection) {
		this.searchDepartmentSelection = searchDepartmentSelection;
	}

	public Date getSearchRoutingDate() {
		return searchRoutingDate;
	}

	public void setSearchRoutingDate(Date searchRoutingDate) {
		this.searchRoutingDate = searchRoutingDate;
	}

	public Date getSearchDeliveryDate() {
		return searchDeliveryDate;
	}

	public void setSearchDeliveryDate(Date searchDeliveryDate) {
		this.searchDeliveryDate = searchDeliveryDate;
	}

	public SearchSenderType[] getSearchSenderRecipientTypes() {
		return searchSenderRecipientTypes;
	}

	public void setSearchSenderRecipientTypes(SearchSenderType[] searchSenderRecipientTypes) {
		this.searchSenderRecipientTypes = searchSenderRecipientTypes;
	}

	public SearchSenderType getSearchSenderType() {
		return searchSenderType;
	}

	public void setSearchSenderType(SearchSenderType searchSenderType) {
		this.searchSenderType = searchSenderType;
	}

	public ProtocolCorrespondent getSearchSender() {
		return searchSender;
	}

	public void setSearchSender(ProtocolCorrespondent searchSender) {
		this.searchSender = searchSender;
	}

	public String getSearchSenderText() {
		return searchSenderText;
	}

	public void setSearchSenderText(String searchSenderText) {
		this.searchSenderText = searchSenderText;
	}

	public EntitySelection<Object> getSearchSenderSelection() {
		return searchSenderSelection;
	}

	public void setSearchSenderSelection(EntitySelection<Object> searchSenderSelection) {
		this.searchSenderSelection = searchSenderSelection;
	}

	public SearchSenderType getSearchRecipientType() {
		return searchRecipientType;
	}

	public void setSearchRecipientType(SearchSenderType searchRecipientType) {
		this.searchRecipientType = searchRecipientType;
	}

	public ProtocolCorrespondent getSearchRecipient() {
		return searchRecipient;
	}

	public void setSearchRecipient(ProtocolCorrespondent searchRecipient) {
		this.searchRecipient = searchRecipient;
	}

	public String getSearchRecipientText() {
		return searchRecipientText;
	}

	public void setSearchRecipientText(String searchRecipientText) {
		this.searchRecipientText = searchRecipientText;
	}

	public EntitySelection<Object> getSearchRecipientSelection() {
		return searchRecipientSelection;
	}

	public void setSearchRecipientSelection(EntitySelection<Object> searchRecipientSelection) {
		this.searchRecipientSelection = searchRecipientSelection;
	}

	public SearchTransactorType[] getSearchTransactorTypes() {
		return searchTransactorTypes;
	}

	public void setSearchTransactorTypes(SearchTransactorType[] searchTransactorTypes) {
		this.searchTransactorTypes = searchTransactorTypes;
	}

	public SearchTransactorType getSearchTransactorType() {
		return searchTransactorType;
	}

	public void setSearchTransactorType(SearchTransactorType searchTransactorType) {
		this.searchTransactorType = searchTransactorType;
	}

	public ProtocolCorrespondent getSearchTransactor() {
		return searchTransactor;
	}

	public void setSearchTransactor(ProtocolCorrespondent searchTransactor) {
		this.searchTransactor = searchTransactor;
	}

	public String getSearchTransactorText() {
		return searchTransactorText;
	}

	public void setSearchTransactorText(String searchTransactorText) {
		this.searchTransactorText = searchTransactorText;
	}

	public EntitySelection<Object> getSearchTransactorSelection() {
		return searchTransactorSelection;
	}

	public void setSearchTransactorSelection(EntitySelection<Object> searchTransactorSelection) {
		this.searchTransactorSelection = searchTransactorSelection;
	}

	public Boolean getSearchCriteriaEnabled() {
		return searchCriteriaEnabled;
	}

	public void setSearchCriteriaEnabled(Boolean searchCriteriaEnabled) {
		this.searchCriteriaEnabled = searchCriteriaEnabled;
	}

	public List<Protocol> getSearchProtocols() {
		return searchProtocols;
	}

	public void setSearchProtocols(List<Protocol> searchProtocols) {
		this.searchProtocols = searchProtocols;
	}

	public Integer getSearchProtocolsTotalSize() {
		return searchProtocolsTotalSize;
	}

	public void setSearchProtocolsTotalSize(Integer searchProtocolsTotalSize) {
		this.searchProtocolsTotalSize = searchProtocolsTotalSize;
	}

	public Integer getSearchProtocolsPageSize() {
		return searchProtocolsPageSize;
	}

	public void setSearchProtocolsPageSize(Integer searchProtocolsPageSize) {
		this.searchProtocolsPageSize = searchProtocolsPageSize;
	}

	public Integer getSearchProtocolsActivePage() {
		return searchProtocolsActivePage;
	}

	public void setSearchProtocolsActivePage(Integer searchProtocolsActivePage) {
		this.searchProtocolsActivePage = searchProtocolsActivePage;
	}

	public Protocol getSelectedSearchProtocol() {
		return selectedSearchProtocol;
	}

	public void setSelectedSearchProtocol(Protocol selectedSearchProtocol) {
		this.selectedSearchProtocol = selectedSearchProtocol;
	}

	public List<ProtocolDocument> getSearchProtocolDocuments() {
		return searchProtocolDocuments;
	}

	public void setSearchProtocolDocuments(List<ProtocolDocument> searchProtocolDocuments) {
		this.searchProtocolDocuments = searchProtocolDocuments;
	}

	public ProtocolDocument getSelectedSearchProtocolDocument() {
		return selectedSearchProtocolDocument;
	}

	public void setSelectedSearchProtocolDocument(ProtocolDocument selectedSearchProtocolDocument) {
		this.selectedSearchProtocolDocument = selectedSearchProtocolDocument;
	}

	public Boolean getBatchRouteIncoming() {
		return batchRouteIncoming;
	}

	public void setBatchRouteIncoming(Boolean batchRouteIncoming) {
		this.batchRouteIncoming = batchRouteIncoming;
	}

	public Boolean getBatchRouteOutgoing() {
		return batchRouteOutgoing;
	}

	public void setBatchRouteOutgoing(Boolean batchRouteOutgoing) {
		this.batchRouteOutgoing = batchRouteOutgoing;
	}

	public List<Department> getBatchRouteDepartments() {
		return batchRouteDepartments;
	}

	public void setBatchRouteDepartments(List<Department> batchRouteDepartments) {
		this.batchRouteDepartments = batchRouteDepartments;
	}

	public Department getBatchRouteDepartment() {
		return batchRouteDepartment;
	}

	public void setBatchRouteDepartment(Department batchRouteDepartment) {
		this.batchRouteDepartment = batchRouteDepartment;
	}

	public Integer getBatchRouteDepartmentsTotalSize() {
		return batchRouteDepartmentsTotalSize;
	}

	public void setBatchRouteDepartmentsTotalSize(Integer batchRouteDepartmentsTotalSize) {
		this.batchRouteDepartmentsTotalSize = batchRouteDepartmentsTotalSize;
	}

	public Integer getBatchRouteDepartmentsPageSize() {
		return batchRouteDepartmentsPageSize;
	}

	public void setBatchRouteDepartmentsPageSize(Integer batchRouteDepartmentsPageSize) {
		this.batchRouteDepartmentsPageSize = batchRouteDepartmentsPageSize;
	}

	public Integer getBatchRouteDepartmentsActivePage() {
		return batchRouteDepartmentsActivePage;
	}

	public void setBatchRouteDepartmentsActivePage(Integer batchRouteDepartmentsActivePage) {
		this.batchRouteDepartmentsActivePage = batchRouteDepartmentsActivePage;
	}

	public List<Protocol> getBatchRouteProtocols() {
		return batchRouteProtocols;
	}

	public void setBatchRouteProtocols(List<Protocol> batchRouteProtocols) {
		this.batchRouteProtocols = batchRouteProtocols;
	}

	public List<Protocol> getSelectedBatchRouteProtocols() {
		return selectedBatchRouteProtocols;
	}

	public void setSelectedBatchRouteProtocols(List<Protocol> selectedBatchRouteProtocols) {
		this.selectedBatchRouteProtocols = selectedBatchRouteProtocols;
	}

	public Integer getBatchRouteProtocolsTotalSize() {
		return batchRouteProtocolsTotalSize;
	}

	public void setBatchRouteProtocolsTotalSize(Integer batchRouteProtocolsTotalSize) {
		this.batchRouteProtocolsTotalSize = batchRouteProtocolsTotalSize;
	}

	public Integer getBatchRouteProtocolsPageSize() {
		return batchRouteProtocolsPageSize;
	}

	public void setBatchRouteProtocolsPageSize(Integer batchRouteProtocolsPageSize) {
		this.batchRouteProtocolsPageSize = batchRouteProtocolsPageSize;
	}

	public Integer getBatchRouteProtocolsActivePage() {
		return batchRouteProtocolsActivePage;
	}

	public void setBatchRouteProtocolsActivePage(Integer batchRouteProtocolsActivePage) {
		this.batchRouteProtocolsActivePage = batchRouteProtocolsActivePage;
	}

}
