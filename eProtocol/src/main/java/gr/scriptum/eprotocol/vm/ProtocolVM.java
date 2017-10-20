/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import static gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import gr.scriptum.api.external.Entity;
import gr.scriptum.api.external.ExternalSearcher;
import gr.scriptum.dao.CorrespondentGroupDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.DossierDAO;
import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.domain.ApplicationLog.Operation;
import gr.scriptum.domain.DepartmentType.Name;
import gr.scriptum.domain.CorrespondentGroup;
import gr.scriptum.domain.Department;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.DocumentType.ApplicableType;
import gr.scriptum.domain.Dossier;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ProtocolRelation;
import gr.scriptum.domain.ProtocolRelation.RelationType;
import gr.scriptum.domain.Subject;
import gr.scriptum.eprotocol.security.SecurityUtil;
import gr.scriptum.eprotocol.service.LoggingService;
import gr.scriptum.eprotocol.service.ProtocolBookService;
import gr.scriptum.eprotocol.service.ProtocolService;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.EntitySelection;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.util.Permission;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestSendDocument;
import gr.scriptum.eprotocol.ws.ResponseSendDocument;
import gr.scriptum.exception.OpenKMException;
import gr.scriptum.predicate.CorrespondentPredicate;
import gr.scriptum.predicate.CorrespondentPredicateGroup;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public abstract class ProtocolVM extends BaseVM {

	public static final String PARAM_DISPLAY_ONLY = "DISPLAY_ONLY";

	private static Log log = LogFactory.getLog(ProtocolVM.class);

	private List<Entity> pertainsToMembers;

	private Entity pertainsToMember;

	private Integer pertainsToMembersTotalSize;

	private Integer pertainsToMembersPageSize = PAGE_SIZE_DEFAULT;

	private Integer pertainsToMembersActivePage;

	private ProtocolCorrespondent transactor;

	protected ProtocolDocument protocolDocument = null;

	protected Boolean isProtocolBookSelectionActive = Boolean.FALSE;

	protected List<ProtocolBook> protocolBooks = null;

	protected String dossierTerm = null;

	protected List<Dossier> dossiers = null;

	protected Integer dossiersTotalSize = null;

	protected Integer dossiersPageSize = PAGE_SIZE_DEFAULT;

	protected Integer dossiersActivePage = null;

	protected String subjectTerm = null;

	protected Boolean isSubjectSelectionActive = Boolean.FALSE;

	protected List<Subject> subjects = null;

	protected Integer subjectsTotalSize = null;

	protected Integer subjectsPageSize = PAGE_SIZE_DEFAULT;

	protected Integer subjectsActivePage = null;

	protected Subject subject = null;

	protected Integer relativeProtocolYear = null;

	protected String relativeProtocolNumber = null;

	protected List<Protocol> relativeProtocols = null;

	protected Integer relativeProtocolsTotalSize = null;

	protected Integer relativeProtocolsPageSize = PAGE_SIZE_DEFAULT;

	protected Integer relativeProtocolsActivePage = null;

	// protected Paging relativeProtocolsPgng;

	protected Protocol relativeProtocol = null;

	protected ProtocolRelation protocolRelation = null;

	protected ProtocolRelation protocolRelationAsTarget = null;

	// protected Bandbox relativeProtocolBndbx;

	protected Callback callback = null;

	protected Protocol protocol = null;

	protected Department department;

	protected List<ProtocolDocument> protocolDocuments = null;

	protected List<ProtocolDocument> protocolDocumentsToBeDeleted = null;

	protected EntitySelection<DocumentType> protocolDocumentTypeSelection;

	protected String term = null;

	protected CorrespondentType[] pertainsToSenderTypes;

	protected CorrespondentType pertainsToSenderType;

	protected Set<ProtocolCorrespondent> transactors;

	protected boolean displayOnly = false;
	
	protected String organization;
	
	protected boolean attachedNumberEditable = false;

	/* non-accessible fields */
	protected boolean dirty = false;

	protected abstract void searchSubjects(Integer startIndex);

	protected void populateApplicableProtocolBooks(gr.scriptum.domain.Permission permission) {
		ProtocolBookService protocolBookService = SpringUtil.getApplicationContext().getBean(ProtocolBookService.class);
		protocolBooks = protocolBookService.findByAssignedUser(getUserInSession(), permission);
		if (protocolBooks.size() == 1) {
			protocol.setProtocolBook(protocolBooks.get(0));
		} else {
			for (ProtocolBook protocolBook : protocolBooks) {
				// pre-select first 'PUBLIC' book
				if (protocolBook.getType().equals(ProtocolBookType.PUBLIC)) {
					protocol.setProtocolBook(protocolBook);
					break;
				}
			}
		}
	}

	protected ResponseSendDocument fetchDocumentFromOpenKM(ProtocolDocument protocolDocument) throws OpenKMException {

		OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();
		String okmToken = getOkmToken();
		RequestSendDocument requestSendDocument = new RequestSendDocument(getUserInSession().getUsername(),
				getUserInSession().getId(), getIp(), IConstants.SYSTEM_NAME, okmToken);
		requestSendDocument.setDocumentPath(protocolDocument.getOkmUuid());
		ResponseSendDocument responseSendDocument = okmDispatcher.sendDocument(requestSendDocument);
		clearOkmToken(okmToken);

		return responseSendDocument;
	}

	protected void searchRelativeProtocols(Integer startIndex) {
		boolean includeDeletedProtocols = false;
		ProtocolDAO incomingProtocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		// set up paging by counting records first
		Integer totalSize = null;
		// TODO: include current protobol book
		List<ProtocolBook> relativeProtocolBooks = new ArrayList<ProtocolBook>();
		relativeProtocolBooks.add(protocol.getProtocolBook());
		Integer relativeProtocolNumberAsNumber = relativeProtocolNumber == null ? null
				: Integer.valueOf(relativeProtocolNumber);

		ProtocolCorrespondent departmentCorrespondent = new ProtocolCorrespondent();
		departmentCorrespondent.setDepartment(department);
		departmentCorrespondent.setType(CorrespondentType.DEPARTMENT);
		CorrespondentPredicate creatorPredicate = new CorrespondentPredicate(departmentCorrespondent);
		creatorPredicate.setDirection(CREATOR);

		CorrespondentPredicate recipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
		recipientPredicate.setDirection(RECIPIENT);
		recipientPredicate.setProtocolDirection(Direction.OUTGOING);

		CorrespondentPredicate internalRecipientPredicate = new CorrespondentPredicate(departmentCorrespondent);
		internalRecipientPredicate.setDirection(INTERNAL_RECIPIENT);
		internalRecipientPredicate.setProtocolDirection(Direction.INCOMING);

		List<CorrespondentPredicate> correspondentPredicates = new ArrayList<>();
		correspondentPredicates.add(creatorPredicate);
		correspondentPredicates.add(recipientPredicate);
		correspondentPredicates.add(internalRecipientPredicate);

		CorrespondentPredicateGroup predicateGroup = new CorrespondentPredicateGroup(correspondentPredicates,
				CorrespondentPredicateGroup.JunctionType.OR);
		List<CorrespondentPredicateGroup> predicateGroups = new ArrayList<CorrespondentPredicateGroup>();
		predicateGroups.add(predicateGroup);

		relativeProtocolsTotalSize = incomingProtocolDAO.countSearch(null, relativeProtocolYear,
				relativeProtocolNumberAsNumber, null, null, null, null, null, null, null, null, null, null, null, null,
				null, true, includeDeletedProtocols, relativeProtocolBooks, predicateGroups, null);

		// figure out which header to sort by
		List<Order> sortBy = new ArrayList<Order>();
		sortBy.add(Order.desc("protocolYear"));
		sortBy.add(Order.desc("protocolNumber"));
		relativeProtocols = incomingProtocolDAO.search(null, relativeProtocolYear, relativeProtocolNumberAsNumber, null,
				null, null, null, null, null, null, null, null, null, null, null, null, true, includeDeletedProtocols,
				relativeProtocolBooks, startIndex, relativeProtocolsPageSize, predicateGroups, null,
				sortBy.toArray(new Order[0]));
	}

	protected void searchDossiers(Integer startIndex) {
		DossierDAO dossierDAO = SpringUtil.getApplicationContext().getBean(DossierDAO.class);

		// set up paging by counting records first
		dossiersTotalSize = dossierDAO.countByTerm(dossierTerm);

		dossiers = dossierDAO.findByTerm(dossierTerm, startIndex, dossiersPageSize);

	}

	protected Object[] searchActiveMembers(String term, Integer startIndex, Integer pageSize) {
		ExternalSearcher searcher = SpringUtil.getApplicationContext().getBean(ExternalSearcher.class);

		// set up paging by counting records first
		Integer membersTotalSize = searcher.countByTerm(term).intValue();

		List<Entity> members = searcher.findByTerm(term, startIndex, pageSize);
		return new Object[] { membersTotalSize, members };
	}

	protected void renumberProtocolDocuments() {
		for (int i = 0; i < protocolDocuments.size(); i++) {
			ProtocolDocument document = protocolDocuments.get(i);
			document.setDocumentNumber(i + 1);
		}
	}

	protected void delete() {
		ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(getUserInSession().getId());
		protocol.setIsDeleted(true);
		protocolDAO.update(protocol);

		LoggingService loggingService = SpringUtil.getApplicationContext().getBean(LoggingService.class);
		loggingService.success(Operation.DELETE_PROTOCOL, getUserInSession(), protocol);

	}

	protected boolean transactorFoundInList(ProtocolCorrespondent transactor, Set<ProtocolCorrespondent> transactors) {
		boolean found = false;
		for (ProtocolCorrespondent existingTransactor : transactors) {
			found = found | (existingTransactor.getType().equals(transactor.getType())
					&& existingTransactor.getCode().equals(transactor.getCode())
					&& existingTransactor.getDescription().equals(transactor.getDescription()));
		}
		return found;
	}

	protected Object[] searchCorrespondentGroups(String term, CorrespondentDirection applicableTo, Integer startIndex,
			Integer pageSize) {
		CorrespondentGroupDAO correspondentGroupDAO = SpringUtil.getApplicationContext()
				.getBean(CorrespondentGroupDAO.class);

		// set up paging by counting records first
		Integer correspondentGroupsTotalSize = correspondentGroupDAO.countByTermAndApplicableToAndActive(term,
				applicableTo, true);

		List<CorrespondentGroup> correspondentGroups = correspondentGroupDAO.findByTermAndApplicableToAndActive(term,
				applicableTo, true, startIndex, pageSize);
		return new Object[] { correspondentGroupsTotalSize, correspondentGroups };
	}

	protected void initProtocolDocumentTypeSelection() {
		protocolDocumentTypeSelection = new EntitySelection<DocumentType>();
		protocolDocumentTypeSelection.setPageSize(PAGE_SIZE_DEFAULT);
	}

	protected Object[] searchDocumentTypes(String term, Protocol protocol, int startIndex, Integer pageSize) {
		DocumentTypeDAO documentTypeDAO = SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
		// set up paging by counting records first
		Integer totalSize = documentTypeDAO.countByTermAndProtocolBookAndApplicableType(term,
				protocol.getProtocolBook(),
				protocol.getDirection().equals(Direction.INCOMING) ? ApplicableType.INCOMING : ApplicableType.OUTGOING);

		List<DocumentType> results = documentTypeDAO.findByTermAndProtocolBookAndApplicableType(term,
				protocol.getProtocolBook(),
				protocol.getDirection().equals(Direction.INCOMING) ? ApplicableType.INCOMING : ApplicableType.OUTGOING,
				startIndex, pageSize);

		return new Object[] { totalSize, results };
	}

	protected boolean isUserDepartmentCreator() {
		return protocol.getCreator().getDepartment().equals(getUserInSessionDepartment());
	}

	@Command
	@NotifyChange({"isProtocolBookSelectionActive","protocol"})
	public void initProtocolBookSelection() {
		if(protocolBooks.size()==1) {
			protocol.setProtocolBook(null);
		}
		isProtocolBookSelectionActive = true;
	}
	
	@Command
	@NotifyChange({"isProtocolBookSelectionActive","protocol"})
	public void toggleProtocolBookSelection(@BindingParam("status") Boolean status) {
		isProtocolBookSelectionActive = status;
	}

	@Command
	@NotifyChange({ "protocol", "protocolBookDescription", "isProtocolBookSelectionActive", "protocolDocumentTypes",
			"protocolRelation", "showProtocolRelationEnabled", "removeProtocolRelationEnabled", "addFileEnabled" })
	public void selectProtocolBook() {
		isProtocolBookSelectionActive = false;
		// DocumentTypeDAO documentTypeDAO =
		// SpringUtil.getApplicationContext().getBean(DocumentTypeDAO.class);
		// protocolDocumentTypes =
		// documentTypeDAO.findByProtocolBookAndApplicableType(protocol.getProtocolBook(),
		// protocol.getDirection().equals(Direction.INCOMING) ?
		// ApplicableType.INCOMING : ApplicableType.OUTGOING);
		protocol.setDocumentType(null);

		// clear any relative protocols already added
		protocol.getProtocolRelationsAsSource().clear();
		protocolRelation = null;
	}

	@Command
	@NotifyChange({ "dossiers", "dossiersTotalSize", "dossiersActivePage" })
	public void searchDossiersByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		dossierTerm = StringUtils.trimToNull(event.getValue());

		if (dossierTerm == null) {
			dossiers = new ArrayList<Dossier>();
			return;
		}
		dossiersActivePage = 0;
		searchDossiers(0);
	}

	@Command
	@NotifyChange({ "dossiers", "dossiersTotalSize", "dossiersActivePage" })
	public void listDossiers() {
		if (!dossiers.isEmpty()) {
			return;
		}
		dossierTerm = "";
		dossiersActivePage = 0;
		searchDossiers(0);
	}

	@Command
	@NotifyChange({ "dossiers", "dossiersTotalSize", "dossiersActivePage" })
	public void clearDossiers() {
		dossiers = new ArrayList<Dossier>();
		dossiersTotalSize = 0;
		dossiersActivePage = 0;
	}

	@Command
	@NotifyChange({ "dossiers", "dossiersTotalSize", "dossiersActivePage" })
	public void pageDossiers() {
		int startIndex = dossiersActivePage * dossiersPageSize;
		searchDossiers(startIndex);
	}

	@Command
	@NotifyChange({ "protocol", "dossierName", "dossiers", "dossiersTotalSize", "dossiersActivePage" })
	public void selectDossier() {
		dossiers = new ArrayList<Dossier>();
		dossiersTotalSize = 0;
		dossiersActivePage = 0;
	}

	@Command
	public void addDossier(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshDossiers"));

		Window dossierWin = (Window) Executions.createComponents(DossierVM.PAGE, view, params);
		dossierWin.setClosable(true);
		dossierWin.doModal();
	}

	@GlobalCommand
	@NotifyChange({ "protocol", "dossiers", "dossierName" })
	public void refreshDossiers(@BindingParam("entity") Dossier dossier) {
		protocol.setDossier(dossier);
	}

	@Command
	@NotifyChange("isSubjectSelectionActive")
	public void toggleSubjectSelection(@BindingParam("status") Boolean status) {
		isSubjectSelectionActive = status;
	}

	@Command
	@NotifyChange({ "subject", "subjects", "subjectsTotalSize", "subjectsActivePage" })
	public void searchSubjectsByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		subjectTerm = StringUtils.trimToNull(event.getValue());

		if (subjectTerm == null) {
			subjects = null;
			return;
		}
		subject = null;
		subjectsActivePage = 0;
		searchSubjects(0);
		// subjectBndbx.open();
	}

	@Command
	@NotifyChange({ "subject", "subjects", "subjectsTotalSize", "subjectsActivePage" })
	public void listSubjects() {
		if (subjects != null && !subjects.isEmpty()) {
			return;
		}
		subjectTerm = "";
		subject = null;
		searchSubjects(0);
	}

	@Command
	@NotifyChange({ "subject", "subjects", "subjectsTotalSize", "subjectsActivePage" })
	public void clearSubjects() {
		subject = null;
		subjects = null;
		subjectsTotalSize = 0;
		subjectsActivePage = 0;
	}

	@Command
	@NotifyChange({ "subjects", "subjectsTotalSize", "subjectsActivePage" })
	public void pageSubjects() {
		int startIndex = subjectsActivePage * subjectsPageSize;
		searchSubjects(startIndex);
	}

	@Command
	@NotifyChange("*")
	public void selectSubject() {
		protocol.setSubjectCode(subject.getCode());
		protocol.setSubject(subject.getDescription());
		protocol.setDocumentType(subject.getDocumentType());
		subject = null;
		subjects = null;
		dirty = true;
	}

	@Command
	public void downloadFile() throws InterruptedException {

		try {

			if (protocolDocument.getId() == null) { // not stored in OpenKM,
													// available locally
				Filedownload.save(protocolDocument.getContent(), protocolDocument.getDocumentMime(),
						protocolDocument.getDocumentName());
			} else { // document stored in OpenKM, retrieve content

				ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(protocolDocument);
				if (responseSendDocument.getContent() == null) {
					Messagebox.show(Labels.getLabel("error.openKM.documentNotFound"), Labels.getLabel("error.title"),
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
				Filedownload.save(responseSendDocument.getContent(), responseSendDocument.getMimeType(),
						protocolDocument.getDocumentName());
			}

		} catch (Exception e) {
			log.error(e);
			if (e instanceof OpenKMException) {
				Messagebox.show(Labels.getLabel("error.openKM"), Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
			} else {
				Messagebox.show(Labels.getLabel("error"), Labels.getLabel("error.title"), Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	@NotifyChange({ "removeFileEnabled", "downloadFileDisabled" })
	public void selectDocument() {
		// if (event.getSelectedItems().isEmpty()) {
		// removeFileBtn.setDisabled(true);
		// return;
		// }
		//
		// removeFileBtn.setDisabled(false);

	}

	@Command
	public void addFile(@ContextParam(ContextType.VIEW) Component view)
			throws SuspendNotAllowedException, InterruptedException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback("refreshFiles"));
		params.put(IConstants.PARAM_PROTOCOL_BOOK, protocol.getProtocolBook());
		Window uploadWin = (Window) Executions.createComponents(UploadVM.PAGE, view, params);
		uploadWin.setClosable(true);
		uploadWin.doModal();
	}

	@Command
	@NotifyChange({ "protocolDocuments", "protocolDocument", "removeFileEnabled", "downloadFileDisabled",
			"updateProtocolEnabled", "outgoingProtocolCreationEnabled", "deleteDisabled", "printDisabled",
			"routeProtocolEnabled", "protocolReceptionEnabled", "editAssigneesEnabled" })
	public void removeFile() {
		protocolDocuments.remove(protocolDocument);
		if (protocolDocument.getId() != null) { // only mark already created
												// documents for deletion
			protocolDocumentsToBeDeleted.add(protocolDocument);
		}
		protocolDocument = null;
		renumberProtocolDocuments();
		dirty = true;
	}

	@GlobalCommand
	@NotifyChange({ "protocol", "protocolDocuments", "updateProtocolEnabled", "outgoingProtocolCreationEnabled",
			"deleteDisabled", "printDisabled", "routeProtocolEnabled", "protocolReceptionEnabled",
			"editAssigneesEnabled" })
	public void refreshFiles(@BindingParam("entity") ProtocolDocument document) {
		document.setDocumentNumber(protocolDocuments.size() + 1);
		protocolDocuments.add(document);
		dirty = true;
	}

	public boolean isAddFileEnabled() {

		if (protocol.getProtocolBook() == null) {
			return false;
		}

		if (!isProtocolCreated()) {
			return true;
		}

		if (isProtocolPending()) {
			return true;
		}

		if(!getIsDocumentUploadFunctionalityEnabled()) {
			return false;
		}

		if (SecurityUtil.isNoneGranted(Permission.EDIT_PROTOCOL_DOCUMENTS.toString())) {
			return false;
		}
		
		return isProtocolRelatedToUserDepartment();
	}

	public boolean isRemoveFileEnabled() {

		if (protocolDocument == null) {
			return false;
		}

		if (!isProtocolCreated()) {
			return true;
		}

		if (isProtocolPending()) {
			return true;
		}

		if(!getIsDocumentUploadFunctionalityEnabled()) {
			return false;
		}

		if (SecurityUtil.isNoneGranted(Permission.EDIT_PROTOCOL_DOCUMENTS.toString())) {
			return false;
		}

		return isProtocolRelatedToUserDepartment();

	}

	@Command
	@NotifyChange({ "relativeProtocols", "relativeProtocolsTotalSize", "relativeProtocolsActivePage" })
	public void searchRelativeProtocolsByNumber(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		relativeProtocolNumber = StringUtils.trimToNull(event.getValue());

		if (relativeProtocolNumber == null) {
			relativeProtocol = null;
			relativeProtocols = new ArrayList<Protocol>();
			return;
		}

		try {
			Integer.valueOf(event.getValue());
		} catch (NumberFormatException e) {
			return;
		}

		relativeProtocolsActivePage = 0;
		searchRelativeProtocols(0);
	}

	@Command
	@NotifyChange({ "relativeProtocols", "relativeProtocolsTotalSize", "relativeProtocolsActivePage" })
	public void listRelativeProtocols() {
		if (!relativeProtocols.isEmpty()) {
			return;
		}
		relativeProtocolNumber = null;
		relativeProtocol = null;
		relativeProtocolsActivePage = 0;
		searchRelativeProtocols(0);
	}

	@Command
	@NotifyChange({ "relativeProtocols", "relativeProtocolsTotalSize", "relativeProtocolsActivePage" })
	public void clearRelativeProtocols() {
		relativeProtocols = new ArrayList<Protocol>();
		relativeProtocolsTotalSize = 0;
		relativeProtocolsActivePage = 0;
	}

	@Command
	@NotifyChange({ "relativeProtocols", "relativeProtocolsTotalSize", "relativeProtocolsActivePage" })
	public void pageRelativeProtocols() {
		int startIndex = relativeProtocolsActivePage * relativeProtocolsPageSize;
		searchRelativeProtocols(startIndex);
	}

	@Command
	@NotifyChange({ "protocol", "relativeProtocol", "relativeProtocols", "relativeProtocolNumber",
			"relativeProtocolsTotalSize", "relativeProtocolsActivePage", "updateProtocolEnabled",
			"protocolReceptionEnabled" })
	public void selectRelativeProtocol() {
		boolean alreadyRelated = relativeProtocol.equals(protocol) || protocol.getProtocolRelations().stream()
				.anyMatch(pr -> relativeProtocol.equals(pr.getSourceProtocol())
						|| relativeProtocol.equals(pr.getTargetProtocol()));
		if (alreadyRelated) {
			Messagebox.show(
					relativeProtocol.equals(protocol) ? Labels.getLabel("protocolPage.protocolCannotBeRelatedToSelf")
							: Labels.getLabel("incomingPage.protocolAlreadyRelated"),
					Labels.getLabel("error.title"), Messagebox.OK, Messagebox.ERROR);
			relativeProtocol = null;
			relativeProtocols = new ArrayList<Protocol>();
			return;
		}
		ProtocolRelation newProtocolRelation = new ProtocolRelation();
		newProtocolRelation.setSourceProtocol(protocol);
		newProtocolRelation.setTargetProtocol(relativeProtocol);
		newProtocolRelation.setRelationType(RelationType.RELATIVE);
		// set date later, when saving
		protocol.getProtocolRelationsAsSource().add(newProtocolRelation);
		dirty = true;

		// reset GUI
		relativeProtocol = null;
		relativeProtocols = new ArrayList<Protocol>();
		relativeProtocolsTotalSize = 0;
		relativeProtocolsActivePage = 0;
		relativeProtocolNumber = null;
	}

	@Command
	@NotifyChange({ "showProtocolRelationEnabled", "removeProtocolRelationEnabled" })
	public void selectProtocolRelation() {
		// nothing to do
	}

	@Command
	public void showProtocolRelation(@ContextParam(ContextType.VIEW) Component view) {
		Protocol protocolToDisplay = null;

		if (protocolRelation.getSourceProtocol().equals(protocol)) {
			protocolToDisplay = protocolRelation.getTargetProtocol();
		} else {
			protocolToDisplay = protocolRelation.getSourceProtocol();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PARAM_DISPLAY_ONLY, true);
		params.put(IConstants.PARAM_KEY_ID, protocolToDisplay.getId());
		Window relativeProtocolWin = (Window) Executions.createComponents(
				protocolToDisplay.getDirection().equals(Direction.INCOMING) ? IncomingVM.PAGE : OutgoingVM.PAGE, view,
				params);
		relativeProtocolWin.setClosable(true);
		relativeProtocolWin.doModal();

	}

	@Command
	@NotifyChange({ "protocol", "protocolRelation", "showProtocolRelationEnabled", "removeProtocolRelationEnabled" })
	public void removeProtocolRelation() {

		if (protocolRelation.getId() == null) {
			if (protocolRelation.getSourceProtocol().equals(protocol)) {
				protocol.getProtocolRelationsAsSource().remove(protocolRelation);
			} else {
				protocol.getProtocolRelationsAsTarget().remove(protocolRelation);
			}
			protocolRelation = null;
			return;
		}

		Messagebox.show(Labels.getLabel("protocolPage.removeProtocolRelation.prompt"), Labels.getLabel("confirm.title"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, (Event event) -> {
					if (((Integer) event.getData()).intValue() == Messagebox.YES) {
						if (protocolRelation.getSourceProtocol().equals(protocol)) {
							protocol.getProtocolRelationsAsSource().remove(protocolRelation);
						} else {
							protocol.getProtocolRelationsAsTarget().remove(protocolRelation);
						}
						String comments = protocolRelation.getSourceProtocol().equals(protocol)
								? protocolRelation.getTargetProtocol().getId().toString()
								: protocolRelation.getSourceProtocol().getId().toString();
						protocolRelation = null;
						ProtocolService protocolService = SpringUtil.getApplicationContext()
								.getBean(ProtocolService.class);
						protocol = protocolService.updateProtocol(protocol, getUserInSession());
						LoggingService loggingService = SpringUtil.getApplicationContext()
								.getBean(LoggingService.class);
						loggingService.success(Operation.DISSASOCIATE_PROTOCOL, getUserInSession(), protocol, comments);
						// work-around for @NotifyChange not working from inside
						// anonymous class
						BindUtils.postGlobalCommand(null, null, "refreshProtocol", null);
						Messagebox.show(Labels.getLabel("protocolPage.removeProtocolRelation.success"),
								Labels.getLabel("success.title"), Messagebox.OK, Messagebox.INFORMATION);
					}
				});
	}

	@Command
	@NotifyChange("protocolDocumentTypeSelection")
	public void searchProtocolDocumentTypes(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		protocolDocumentTypeSelection.setTerm(StringUtils.trimToNull(event.getValue()));
		protocolDocumentTypeSelection.setActivePage(0);
		Object[] results = searchDocumentTypes(protocolDocumentTypeSelection.getTerm(), protocol, 0,
				protocolDocumentTypeSelection.getPageSize());
		protocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		protocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("protocolDocumentTypeSelection")
	public void listProtocolDocumentTypes() {
		if (protocolDocumentTypeSelection.getEntites() != null
				&& !protocolDocumentTypeSelection.getEntites().isEmpty()) {
			return;
		}
		protocolDocumentTypeSelection.setTerm("");
		protocolDocumentTypeSelection.setActivePage(0);
		Object[] results = searchDocumentTypes(protocolDocumentTypeSelection.getTerm(), protocol, 0,
				protocolDocumentTypeSelection.getPageSize());
		protocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		protocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("protocolDocumentTypeSelection")
	public void clearProtocolDocumentTypes() {
		protocolDocumentTypeSelection.setEntites(new ArrayList<>());
		protocolDocumentTypeSelection.setTotalSize(0);
		protocolDocumentTypeSelection.setActivePage(0);
	}

	@Command
	@NotifyChange("protocolDocumentTypeSelection")
	public void pageProtocolDocumentTypes() {
		int startIndex = protocolDocumentTypeSelection.getActivePage() * protocolDocumentTypeSelection.getPageSize();
		Object[] results = searchDocumentTypes(protocolDocumentTypeSelection.getTerm(), protocol, startIndex,
				protocolDocumentTypeSelection.getPageSize());
		protocolDocumentTypeSelection.setTotalSize((Integer) results[0]);
		protocolDocumentTypeSelection.setEntites((List<DocumentType>) results[1]);
	}

	@Command
	@NotifyChange("*")
	public void selectProtocolDocumentType() {
		protocolDocumentTypeSelection = new EntitySelection<DocumentType>();
		protocolDocumentTypeSelection.setPageSize(PAGE_SIZE_DEFAULT);
		setProtocolChanged();
	}

	@Command
	@NotifyChange("*")
	public void setProtocolChanged() {
		dirty = true;
	}
	
	public boolean isLocked() {
		if (protocol == null) {
			return true;
		}
		return false;
	}

	public boolean isProtocolCreated() {
		if (protocol.getId() != null) {
			return true;
		}
		return false;
	}

	public boolean isProtocolPending() {

		return protocol.getProtocolNumber() == null;

	}

	public boolean isProtocolSubmitted() {

		return protocol.getProtocolNumber() != null;

	}

	public boolean isProtocolDeleted() {
		if (protocol.getIsDeleted() != null && protocol.getIsDeleted() == true) {
			return true;
		}
		return false;
	}

	public boolean isRemoveProtocolRelationEnabled() {

		if (protocolRelation == null) {
			return false;
		}

		if(protocolRelation.getRelationType().equals(RelationType.IDENTICAL_NUMBER)) {
			return false;
		}
		
		if (protocol.getProtocolNumber() == null) {
			return true;
		}

		if (SecurityUtil.isNoneGranted(Permission.DISASSOCIATE_PROTOCOL.toString())) {
			return false;
		}

		Protocol relatedProtocol = protocolRelation.getSourceProtocol().equals(protocol)
				? protocolRelation.getTargetProtocol() : protocolRelation.getSourceProtocol();
		if (protocol.getProtocolNumber().equals(relatedProtocol.getProtocolNumber())) {
			return false;
		}

		// refresh from databse
		// TODO check whether transaction is required
		ProtocolDAO protocolDAO = SpringUtil.getApplicationContext().getBean(ProtocolDAO.class);
		relatedProtocol = protocolDAO.merge(relatedProtocol);

		boolean isUserDepartmentCreator = relatedProtocol.getCreator().getDepartment().equals(department);
		if (isUserDepartmentCreator) {
			return true;
		}

		boolean isUserDepartmentRecipient = false;
		switch (relatedProtocol.getDirection()) {
		case INCOMING:
			isUserDepartmentRecipient = relatedProtocol.getInternalRecipients().stream()
					.anyMatch(ir -> ir.getDepartment().equals(department));
			break;
		case OUTGOING:
			isUserDepartmentRecipient = relatedProtocol.getRecipients().stream().anyMatch(
					r -> r.getType().equals(CorrespondentType.DEPARTMENT) && r.getDepartment().equals(department));
			break;
		}
		if (isUserDepartmentRecipient) {

			return true;
		}

		return false;
	}

	public boolean isShowProtocolRelationEnabled() {
		return protocolRelation != null;
	}

	public boolean isShowProtocolRelationAsTargetDisabled() {
		return protocolRelationAsTarget == null;
	}

	public boolean isPrintDisabled() {
		return !isProtocolSubmitted() | isProtocolDeleted() | dirty;
	}

	public boolean isDeleteDisabled() {

		if (!isProtocolCreated()) {
			return true;
		}

		if (isProtocolDeleted()) {
			return true;
		}

		if (dirty) {
			return true;
		}

		if (department == null) {
			return true;
		}

		if (!department.equals(getUserInSessionDepartment())) {
			return true;
		}

		if (SecurityUtil.isAnyGranted(Permission.DELETE_PROTOCOL.toString())) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean getIsPendingFunctionalityEnabled() {

		return super.getIsPendingFunctionalityEnabled();
	}

	public Boolean getIsDossierInputEnabled() {
		return super.getIsDossierFunctionalityEnabled();
	}

	public Boolean getIsDossierDisplayEnabled() {
		return super.getIsDossierFunctionalityEnabled() & isProtocolSubmitted();
	}

	public boolean isDownloadFileDisabled() {

		if (protocolDocument == null) {
			return true;
		}

		return false;

	}

	public boolean isProtocolRelatedToUserDepartment() {

		boolean isUserDepartmentCreator = protocol.getCreator().getDepartment().equals(getUserInSessionDepartment());
		if (isUserDepartmentCreator) {
			return true;
		}
		boolean isUserDepartmentRecipient = false;
		switch (protocol.getDirection()) {
		case INCOMING:
			isUserDepartmentRecipient = protocol.getInternalRecipients().stream()
					.anyMatch(ir -> ir.getDepartment().equals(getUserInSessionDepartment()));
			break;
		case OUTGOING:
			isUserDepartmentRecipient = protocol.getRecipients().stream()
					.anyMatch(r -> r.getType().equals(CorrespondentType.DEPARTMENT)
							&& r.getDepartment().equals(getUserInSessionDepartment()));
			break;
		}

		if (isUserDepartmentRecipient) {
			return true;
		}

		return false;
	}

	public boolean isInsertProtocolEnabled() {

		if (isProtocolSubmitted()) {
			return false;
		}

		if(getUserInSessionDepartmentType() ==  null || getUserInSessionDepartmentType().equals(Name.UNIT_DEPARTMENT)) {
			return false;
		}
		
		switch (protocol.getDirection()) {
		case INCOMING:
			return SecurityUtil.isAllGranted(Permission.WRITE_INCOMING.toString());
		case OUTGOING:
			return SecurityUtil.isAllGranted(Permission.WRITE_OUTGOING.toString());
		default:
			return false;
		}
	}

	public boolean isProtocolFieldEditable() {
		if (isProtocolPending()) {
			return true;
		}
		
		// submitted protocols
		if(isDisplayOnly()) {
			return false;
		}
		
		if (SecurityUtil.isNoneGranted(Permission.EDIT_PROTOCOL.toString())) {
			return false;
		}

		if(!isUserDepartmentCreator()) {
			return false;
		}
		
		return true;
	}

	public boolean isUpdateProtocolEnabled() {

		if (isProtocolPending()) {
			return false;
		}

		// submitted protocols
		if(isDisplayOnly()) {
			return false; 
		}

		//TODO: separate cases based on permissions
		if (SecurityUtil.isNoneGranted(Permission.EDIT_PROTOCOL_DOCUMENTS.toString() + ","
				+ Permission.EDIT_PROTOCOL.toString() + "," + Permission.ASSIGN_INTERNAL_RECIPIENTS.toString())) {
			return false;
		}
		
		if (!isProtocolRelatedToUserDepartment()) {
			return false;
		}
		
		return dirty;
	}

	public boolean isAttachedNumberEditable() {

		if (isProtocolPending()) {
			return true;
		}
		// submitted protocols
		if (SecurityUtil.isNoneGranted(Permission.EDIT_PROTOCOL.toString())) {
			return false;
		}

		if(isDisplayOnly()) {
			return false;
		}

		return attachedNumberEditable;
	}
	
	public ProtocolDocument getProtocolDocument() {
		return protocolDocument;
	}

	public void setProtocolDocument(ProtocolDocument protocolDocument) {
		this.protocolDocument = protocolDocument;
	}

	public List<ProtocolBook> getProtocolBooks() {
		return protocolBooks;
	}

	public void setProtocolBooks(List<ProtocolBook> protocolBooks) {
		this.protocolBooks = protocolBooks;
	}

	public List<Dossier> getDossiers() {
		return dossiers;
	}

	public void setDossiers(List<Dossier> dossiers) {
		this.dossiers = dossiers;
	}

	public Integer getDossiersTotalSize() {
		return dossiersTotalSize;
	}

	public void setDossiersTotalSize(Integer dossiersTotalSize) {
		this.dossiersTotalSize = dossiersTotalSize;
	}

	public Integer getDossiersPageSize() {
		return dossiersPageSize;
	}

	public void setDossiersPageSize(Integer dossiersPageSize) {
		this.dossiersPageSize = dossiersPageSize;
	}

	public Integer getDossiersActivePage() {
		return dossiersActivePage;
	}

	public void setDossiersActivePage(Integer dossiersActivePage) {
		this.dossiersActivePage = dossiersActivePage;
	}

	public Boolean getIsSubjectSelectionActive() {
		return isSubjectSelectionActive;
	}

	public void setIsSubjectSelectionActive(Boolean isSubjectSelectionActive) {
		this.isSubjectSelectionActive = isSubjectSelectionActive;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public Integer getSubjectsTotalSize() {
		return subjectsTotalSize;
	}

	public void setSubjectsTotalSize(Integer subjectsTotalSize) {
		this.subjectsTotalSize = subjectsTotalSize;
	}

	public Integer getSubjectsPageSize() {
		return subjectsPageSize;
	}

	public void setSubjectsPageSize(Integer subjectsPageSize) {
		this.subjectsPageSize = subjectsPageSize;
	}

	public Integer getSubjectsActivePage() {
		return subjectsActivePage;
	}

	public void setSubjectsActivePage(Integer subjectsActivePage) {
		this.subjectsActivePage = subjectsActivePage;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Integer getRelativeProtocolYear() {
		return relativeProtocolYear;
	}

	public void setRelativeProtocolYear(Integer relativeProtocolYear) {
		this.relativeProtocolYear = relativeProtocolYear;
	}

	public String getRelativeProtocolNumber() {
		return relativeProtocolNumber;
	}

	public void setRelativeProtocolNumber(String relativeProtocolNumber) {
		this.relativeProtocolNumber = relativeProtocolNumber;
	}

	public List<Protocol> getRelativeProtocols() {
		return relativeProtocols;
	}

	public void setRelativeProtocols(List<Protocol> relativeProtocols) {
		this.relativeProtocols = relativeProtocols;
	}

	public Integer getRelativeProtocolsTotalSize() {
		return relativeProtocolsTotalSize;
	}

	public void setRelativeProtocolsTotalSize(Integer relativeProtocolsTotalSize) {
		this.relativeProtocolsTotalSize = relativeProtocolsTotalSize;
	}

	public Integer getRelativeProtocolsPageSize() {
		return relativeProtocolsPageSize;
	}

	public void setRelativeProtocolsPageSize(Integer relativeProtocolsPageSize) {
		this.relativeProtocolsPageSize = relativeProtocolsPageSize;
	}

	public Integer getRelativeProtocolsActivePage() {
		return relativeProtocolsActivePage;
	}

	public void setRelativeProtocolsActivePage(Integer relativeProtocolsActivePage) {
		this.relativeProtocolsActivePage = relativeProtocolsActivePage;
	}

	public Protocol getRelativeProtocol() {
		return relativeProtocol;
	}

	public void setRelativeProtocol(Protocol relativeProtocol) {
		this.relativeProtocol = relativeProtocol;
	}

	public ProtocolRelation getProtocolRelation() {
		return protocolRelation;
	}

	public void setProtocolRelation(ProtocolRelation protocolRelation) {
		this.protocolRelation = protocolRelation;
	}

	public ProtocolRelation getProtocolRelationAsTarget() {
		return protocolRelationAsTarget;
	}

	public void setProtocolRelationAsTarget(ProtocolRelation protocolRelationAsTarget) {
		this.protocolRelationAsTarget = protocolRelationAsTarget;
	}

	public Boolean getIsProtocolBookSelectionActive() {
		return isProtocolBookSelectionActive;
	}

	public void setIsProtocolBookSelectionActive(Boolean isProtocolBookSelectionActive) {
		this.isProtocolBookSelectionActive = isProtocolBookSelectionActive;
	}

	public String getProtocolBookDescription() {
		if (protocol.getProtocolBook() == null) {
			return "";
		}

		ProtocolBook protocolBook = protocol.getProtocolBook();
		return (protocolBook.getId() + "-" + protocolBook.getProtocolSeries() + "-" + protocolBook.getProtocolYear());
	}

	public void setProtocolBookDescription(String protocolBookDescription) {
		// do nothing
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDossierName() {
		if (protocol.getDossier() == null) {
			return "";
		}

		Dossier dossier = protocol.getDossier();
		return (dossier.getId() + "-" + dossier.getName() + "-" + dossier.getProtocolYear());
	}

	public void setDossierName(String dossierName) {
		// do nothing
	}

	public List<ProtocolDocument> getProtocolDocuments() {
		return protocolDocuments;
	}

	public void setProtocolDocuments(List<ProtocolDocument> protocolDocuments) {
		this.protocolDocuments = protocolDocuments;
	}

	public List<ProtocolDocument> getProtocolDocumentsToBeDeleted() {
		return protocolDocumentsToBeDeleted;
	}

	public void setProtocolDocumentsToBeDeleted(List<ProtocolDocument> protocolDocumentsToBeDeleted) {
		this.protocolDocumentsToBeDeleted = protocolDocumentsToBeDeleted;
	}

	public EntitySelection<DocumentType> getProtocolDocumentTypeSelection() {
		return protocolDocumentTypeSelection;
	}

	public void setProtocolDocumentTypeSelection(EntitySelection<DocumentType> protocolDocumentTypeSelection) {
		this.protocolDocumentTypeSelection = protocolDocumentTypeSelection;
	}

	@Command
	@NotifyChange({ "pertainsToMembers", "pertainsToMember" })
	public void prepareSearchPertainsToMembersByTerm(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		if (term == null) {
			pertainsToMembers = new ArrayList<Entity>();
			pertainsToMember = null;
			return;
		}
	}

	@Command
	@NotifyChange({ "transactors", "pertainsToMembers", "pertainsToMember", "pertainsToMembersTotalSize",
			"pertainsToMembersActivePage" })
	public void searchPertainsToMembersByTerm() {
		pertainsToMembersActivePage = 0;
		Object[] results = searchActiveMembers(term, 0, pertainsToMembersPageSize);
		pertainsToMembersTotalSize = (Integer) results[0];
		pertainsToMembers = (List<Entity>) results[1];

		if (pertainsToMembers.size() == 1) {
			ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
					CorrespondentDirection.TRANSACTOR, pertainsToSenderType);
			Entity entity = pertainsToMembers.get(0);
			protocolCorrespondent.setCode(entity.getId().toString());
			protocolCorrespondent.setDescription(entity.getName());
			if (!transactorFoundInList(protocolCorrespondent, transactors)) {
				transactors.add(protocolCorrespondent);
			}
			pertainsToMembers = new ArrayList<Entity>();
			pertainsToMembersTotalSize = 0;
			pertainsToMembersActivePage = 0;
			pertainsToMember = null;
		}
	}

	@Command
	@NotifyChange({ "pertainsToMembers", "pertainsToMember", "pertainsToMembersTotalSize",
			"pertainsToMembersActivePage" })
	public void clearPertainsToMembers() {
		pertainsToMembers = new ArrayList<Entity>();
		pertainsToMembersTotalSize = 0;
		pertainsToMembersActivePage = 0;
		pertainsToMember = null;
	}

	@Command
	@NotifyChange({ "pertainsToMembers", "pertainsToMembersTotalSize", "pertainsToMembersActivePage" })
	public void pagePertainsToMembers() {
		int startIndex = pertainsToMembersActivePage * pertainsToMembersPageSize;
		Object[] results = searchActiveMembers(term, startIndex, pertainsToMembersPageSize);
		pertainsToMembersTotalSize = (Integer) results[0];
		pertainsToMembers = (List<Entity>) results[1];
	}

	@Command
	@NotifyChange({ "transactors", "pertainsToMembers", "pertainsToMember", "pertainsToMembersTotalSize",
			"pertainsToMembersActivePage" })
	public void selectPertainsToMember() {

		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(protocol,
				CorrespondentDirection.TRANSACTOR, pertainsToSenderType);
		protocolCorrespondent.setCode(pertainsToMember.getId().toString());
		protocolCorrespondent.setDescription(pertainsToMember.getName());
		if (!transactorFoundInList(protocolCorrespondent, transactors)) {
			transactors.add(protocolCorrespondent);
		}

		pertainsToMembers = new ArrayList<Entity>();
		pertainsToMembersTotalSize = 0;
		pertainsToMembersActivePage = 0;
		pertainsToMember = null;
	}

	@Command
	@NotifyChange({ "transactor", "transactors" })
	public void removeTransactor() {
		transactors.remove(transactor);
		transactor = null;
	}

	@Command
	public void finishProtocolEdit() {
		// notify caller (if any)
		if (callback != null) {
			// called by MVVM view model
			Map<String, Object> args = new HashMap<>();
			args.put("entity", protocol);
			BindUtils.postGlobalCommand(null, null, callback.getEvent(), args);
		}

	}

	public CorrespondentType[] getPertainsToSenderTypes() {
		return pertainsToSenderTypes;
	}

	public CorrespondentType getPertainsToSenderType() {
		return pertainsToSenderType;
	}

	public void setPertainsToSenderType(CorrespondentType pertainsToSenderType) {
		this.pertainsToSenderType = pertainsToSenderType;
	}

	public List<Entity> getPertainsToMembers() {
		return pertainsToMembers;
	}

	public void setPertainsToMembers(List<Entity> pertainsToMembers) {
		this.pertainsToMembers = pertainsToMembers;
	}

	public Entity getPertainsToMember() {
		return pertainsToMember;
	}

	public void setPertainsToMember(Entity pertainsToMember) {
		this.pertainsToMember = pertainsToMember;
	}

	public Integer getPertainsToMembersTotalSize() {
		return pertainsToMembersTotalSize;
	}

	public void setPertainsToMembersTotalSize(Integer pertainsToMembersTotalSize) {
		this.pertainsToMembersTotalSize = pertainsToMembersTotalSize;
	}

	public Integer getPertainsToMembersPageSize() {
		return pertainsToMembersPageSize;
	}

	public void setPertainsToMembersPageSize(Integer pertainsToMembersPageSize) {
		this.pertainsToMembersPageSize = pertainsToMembersPageSize;
	}

	public Integer getPertainsToMembersActivePage() {
		return pertainsToMembersActivePage;
	}

	public void setPertainsToMembersActivePage(Integer pertainsToMembersActivePage) {
		this.pertainsToMembersActivePage = pertainsToMembersActivePage;
	}

	public ProtocolCorrespondent getTransactor() {
		return transactor;
	}

	public void setTransactor(ProtocolCorrespondent transactor) {
		this.transactor = transactor;
	}

	public Set<ProtocolCorrespondent> getTransactors() {
		return transactors;
	}

	public void setTransactors(Set<ProtocolCorrespondent> transactors) {
		this.transactors = transactors;
	}

	public boolean isDisplayOnly() {
		return displayOnly;
	}

	public void setDisplayOnly(boolean displayOnly) {
		this.displayOnly = displayOnly;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
}
