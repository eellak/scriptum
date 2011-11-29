package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.OutgoingProtocolDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.dao.ProtocolNumberDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;
import gr.scriptum.eprotocol.util.Callback;
import gr.scriptum.eprotocol.util.IConstants;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestAddDocumentToNode;
import gr.scriptum.eprotocol.ws.RequestDelete;
import gr.scriptum.eprotocol.ws.RequestMoveNode;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.RequestRenameNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseAddDocumentToNode;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.scriptum.eprotocol.ws.ResponseRenameNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

public class IncomingController extends ProtocolController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6560601743973199676L;

	private static Log log = LogFactory.getLog(IncomingController.class);

	public static final String PAGE = "incoming.zul";

	public static final String PAGE_PRINT = "incomingPrint.zul";

	/* data binding */
	private IncomingProtocol protocol = null;

	private List<Contact> contacts = null;

	private List<DocumentType> documentTypes = null;

	private DocumentType documentType = null;

	private List<DistributionMethod> distributionMethods = null;

	private List<ProtocolDocument> protocolDocuments = null;

	private List<ProtocolDocument> protocolDocumentsToBeDeleted = null;

	private String okmNodePendingIncoming = null;

	private String okmNodeIncoming = null;

	private Integer defaultDistributionMethodId = null;

	private String term = null;

	/* components */
	Window incomingWin;

	Window uploadWin;

	Window contactWin;

	Bandbox contactBndbx;

	Bandbox protocolBookBndbx;

	Listbox documentsLstbx;

	Combobox distributionMethodCbx;

	Button removeFileBtn;

	Paging contactsPgng;

	private void initData() {
		protocol = new IncomingProtocol();
		contacts = new ArrayList<Contact>();
		protocolDocuments = new LinkedList<ProtocolDocument>();
		protocolDocument = null;
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		term = null;
		for (DistributionMethod distributionMethod : distributionMethods) {
			if (distributionMethod.getId().equals(defaultDistributionMethodId)) {
				protocol.setDistributionMethod(distributionMethod);
				break;
			}
		}
	}

	private void populateContactBndbx() {
		// contactBndbx.setValue(protocol.getContact().getName() + " "
		// + protocol.getContact().getSurname());
		contactBndbx.setValue(protocol.getContact().getFullName());
	}

	private void renumberProtocolDocuments() {
		for (int i = 0; i < protocolDocuments.size(); i++) {
			ProtocolDocument document = protocolDocuments.get(i);
			document.setDocumentNumber(i + 1);
		}
	}

	private void save(boolean isSubmission) throws Exception {

		// get the current transaction and commit it. We want to perform saving
		// in a new transaction, for better isolation.
		UserTransaction tx = null;

		try {
			tx = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			tx.commit();
			log.info("Committed transaction: " + tx);
		} catch (Exception e) {
			log.error(e);
			throw e;
		}

		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(getUserInSession().getId());

		try {

			if (isSubmission) {
				// get next availabe protocol number, using an isolated
				// transaction
				tx.begin();
				log.info("Getting Protocol Number (Started transaction): " + tx);

				// protocol.setProtocolNumber(Math.abs(UUID.randomUUID().hashCode()));
				ProtocolNumberDAO protocolNumberDAO = new ProtocolNumberDAO();
				ProtocolNumber protocolNumber = null;

				protocolNumber = protocolNumberDAO.getNextNumber(
						ProtocolNumberType.COMMON, protocol.getProtocolBook());

				tx.commit();
				log.info("Got Protocol Number (Commited transaction): " + tx);

				protocol.setProtocolNumber(protocolNumber.getNumber()
						.intValue());
				// protocol.setProtocolSeries(protocolNumber.getSeries());
				// protocol.setProtocolYear(protocolNumber.getYear());
				protocol.setProtocolDate(now);

			}

			tx.begin();
			log.info("Saving Protocol (Started transaction): " + tx);

			IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
			ProtocolDocumentDAO protocolDocumentDAO = new ProtocolDocumentDAO();
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			if (protocol.getId() == null) { // new protocol, create

				protocol.setCreateDt(now);
				protocol.setCreateUserId(getUserInSession().getId());

				/* local database actions */
				incomingProtocolDAO.makePersistent(protocol);

				// Create documents
				for (ProtocolDocument document : protocolDocuments) {
					document.setIncomingProtocol(protocol);
					protocolDocumentDAO.makePersistent(document);
					protocol.getProtocolDocuments().add(document);
				}

				/* OpenKM actions */

				// create new OKM pending node, including documents
				RequestNewNode requestNewNode = new RequestNewNode(
						getUserInSession().getUsername(), getUserInSession()
								.getId(), getIp(), IConstants.SYSTEM_NAME,
						getOkmToken());

				if (isSubmission) { // final protocol submission
					requestNewNode.setFolderPath(okmNodeIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getProtocolBook().getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolBook().getProtocolYear()
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getProtocolNumber());

				} else {// pending protocol being stored
					requestNewNode.setFolderPath(okmNodePendingIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
				}

				for (ProtocolDocument document : protocolDocuments) {
					requestNewNode.addDocument(document);
				}

				ResponseNewNode newNodeResponse = okmDispatcher
						.createNewNode(requestNewNode);
				if (newNodeResponse.isError()) {
					log.error(newNodeResponse.toString());
					throw new RuntimeException(newNodeResponse.toString());
				}

				// update documents, storing OKM Path, UUID and size
				for (ProtocolDocument document : protocolDocuments) {
					protocolDocumentDAO.update(document);
				}

			} else { // existing (ie. pending) protocol

				/* local database actions */
				incomingProtocolDAO.update(protocol);

				List<ProtocolDocument> newlyAdded = new LinkedList<ProtocolDocument>();
				for (ProtocolDocument document : protocolDocuments) {
					if (document.getId() != null) { // existing document, update

						protocolDocumentDAO.update(document);

					} else {// Create newly added documents
						document.setIncomingProtocol(protocol);
						protocolDocumentDAO.makePersistent(document);
						protocol.getProtocolDocuments().add(document);
						newlyAdded.add(document);
					}
				}

				// Delete documents marked respectively
				for (ProtocolDocument document : protocolDocumentsToBeDeleted) {
					protocolDocumentDAO.delete(document);
					protocol.getProtocolDocuments().remove(document);
				}

				/* OpenKM actions */

				// add newly added documents to OpenKM
				for (ProtocolDocument document : newlyAdded) {

					RequestAddDocumentToNode request = new RequestAddDocumentToNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					request.setNodePath(okmNodePendingIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
					request.setDocument(document);

					ResponseAddDocumentToNode response = okmDispatcher
							.addDocumentToNode(request);
					if (response.isError()) {
						log.error(response.toString());
						throw new RuntimeException(response.toString());
					}

					// update local document, storing OKM Path, UUID and size
					protocolDocumentDAO.update(document);

				}

				// delete documents marked respectively from OpenKM
				for (ProtocolDocument document : protocolDocumentsToBeDeleted) {

					RequestDelete requestDelete = new RequestDelete(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					requestDelete.setDirectory(false);
					requestDelete.setPath(document.getOkmPath());

					Response responseDelete = okmDispatcher
							.delete(requestDelete);
					if (responseDelete.isError()) {
						log.error(responseDelete.toString());
						throw new RuntimeException(responseDelete.toString());
					}
				}

				if (isSubmission) {
					// move 'pending' OpenKM node to 'incoming'
					RequestMoveNode requestMoveNode = new RequestMoveNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					requestMoveNode.setOldPath(okmNodePendingIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
					requestMoveNode.setNewPath(okmNodeIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getProtocolBook().getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolBook().getProtocolYear());

					Response responseMoveNode = okmDispatcher
							.moveNode(requestMoveNode);
					if (responseMoveNode.isError()) {
						log.error(responseMoveNode.toString());
						throw new RuntimeException(responseMoveNode.toString());
					}

					// rename node, based on assigned protocol number
					RequestRenameNode requestRenameNode = new RequestRenameNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					requestRenameNode.setOldName(okmNodeIncoming
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getProtocolBook().getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolBook().getProtocolYear()
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
					requestRenameNode.setNewName(protocol.getProtocolNumber()
							.toString());

					ResponseRenameNode responseRenameNode = okmDispatcher
							.renameNode(requestRenameNode);
					if (responseRenameNode.isError()) {
						log.error(responseRenameNode.toString());
						throw new RuntimeException(
								responseRenameNode.toString());
					}

					// update local protocol documents with new path
					for (ProtocolDocument document : protocolDocuments) {
						String path = document.getOkmPath();
						path = path
								.replaceFirst(
										okmNodePendingIncoming
												+ IConstants.OKM_FOLDER_DELIMITER
												+ protocol.getId(),
										okmNodeIncoming
												+ IConstants.OKM_FOLDER_DELIMITER
												+ protocol.getProtocolBook()
														.getProtocolSeries()
												+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
												+ protocol.getProtocolBook()
														.getProtocolYear()
												+ IConstants.OKM_FOLDER_DELIMITER
												+ protocol.getProtocolNumber());
						document.setOkmPath(path);
						protocolDocumentDAO.update(document);
					}
				}

			}

			tx.commit();
			log.info("Saved Protocol (Committed transaction): " + tx);

			// clean up
			protocolDocumentsToBeDeleted.clear();

		} catch (Exception e) {
			log.error(e);
			if (tx.getStatus() == Status.STATUS_ACTIVE) {
				tx.rollback();
				log.info("Rolled back transaction: " + tx);
			}
			throw e;
		}

	}

	private void delete() {
		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		Date now = new Date();
		protocol.setUpdateTs(now);
		protocol.setUpdateUserId(getUserInSession().getId());
		protocol.setIsDeleted(true);
		incomingProtocolDAO.update(protocol);
	}

	private void searchContacts(Integer startIndex) {
		ContactDAO contactDAO = new ContactDAO();

		// set up paging by counting records first
		Integer totalSize = contactDAO.countByTerm(term);
		contactsPgng.setTotalSize(totalSize);
		int pageSize = contactsPgng.getPageSize();

		contacts = contactDAO.findByTerm(term, startIndex, pageSize);
		for (Contact contact : contacts) {
			contact.getCompany().getName().toString(); // force hibernate to
														// fetch company
		}

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		ParameterDAO parameterDAO = new ParameterDAO();
		okmNodePendingIncoming = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_PENDING_INCOMING);
		okmNodeIncoming = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_INCOMING);
		defaultDistributionMethodId = parameterDAO
				.getAsInteger(IConstants.PARAM_DISTRIBUTION_METHOD_NA_ID);

		DistributionMethodDAO distributionMethodDAO = new DistributionMethodDAO();
		distributionMethods = distributionMethodDAO.findAll();

		ProtocolBookDAO protocolBookDAO = new ProtocolBookDAO();
		protocolBooks = protocolBookDAO.findActiveBooks();

		initData();

		// fetch existing protocol, if any
		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) {
			IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
			protocol = incomingProtocolDAO.findById(Integer.valueOf(idString),
					false);

			if (protocol == null) { // not found
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				protocol = new IncomingProtocol();
				for (DistributionMethod distributionMethod : distributionMethods) {
					if (distributionMethod.getId().equals(
							defaultDistributionMethodId)) {
						protocol.setDistributionMethod(distributionMethod);
						break;
					}
				}
				return;
			}
			// populate contacts combobox model (if necessary)
			if (protocol.getContact() != null) {
				contacts.add(protocol.getContact());
				// populateContactBndbx();
			}
			// populate protocol documents listbox
			protocolDocuments = new LinkedList<ProtocolDocument>(
					protocol.getProtocolDocuments());

		}

		if (page.getRequestPath().endsWith(PAGE_PRINT)) {
			Clients.print();
		}
	}

	public void onChanging$contactBndbx(InputEvent event) {
		term = StringUtils.trimToNull(event.getValue());

		searchContacts(0);
		getBinder(incomingWin).loadAll();
	}

	public void onOpen$contactBndbx(OpenEvent event) {
		if (!contacts.isEmpty()) {
			return;
		}
		term = "";
		searchContacts(0);

		getBinder(incomingWin).loadAll();
	}

	public void onPaging$contactsPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * contactsPgng.getPageSize();
		searchContacts(startIndex);
		getBinder(incomingWin).loadAll();
	}

	public void onSelect$contactsLstbx(SelectEvent event) {
		// populateContactBndbx();
		contactBndbx.close();
		getBinder(incomingWin).loadAll();
	}

	public void onSelect$protocolBookLstbx(SelectEvent event) {
		protocolBookBndbx.close();
		getBinder(incomingWin).loadAll();
	}

	public void onClick$addFileBtn() throws SuspendNotAllowedException,
			InterruptedException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback(documentsLstbx,
				"onDocumentAdded"));
		uploadWin = (Window) Executions.createComponents(UploadController.PAGE,
				incomingWin, params);
		uploadWin.setClosable(true);
		uploadWin.doModal();

	}

	public void onDocumentAdded$documentsLstbx(Event event) {
		ProtocolDocument document = (ProtocolDocument) ((ForwardEvent) event)
				.getOrigin().getData();
		document.setDocumentNumber(protocolDocuments.size() + 1);
		protocolDocuments.add(document);
		getBinder(incomingWin).loadAll();
	}

	public void onClick$newContactBtn() throws SuspendNotAllowedException,
			InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback(contactBndbx,
				"onContactAdded"));

		contactWin = (Window) Executions.createComponents(
				ContactController.INCLUDE_CONTACT_WIN, incomingWin, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	public void onContactAdded$contactBndbx(Event event) {
		Contact contact = (Contact) ((ForwardEvent) event).getOrigin()
				.getData();
		protocol.setContact(contact);
		contacts.add(contact);
		getBinder(incomingWin).loadAll();
	}

	public void onSelect$documentsLstbx(SelectEvent event) {
		// if (event.getSelectedItems().isEmpty()) {
		// removeFileBtn.setDisabled(true);
		// return;
		// }
		//
		// removeFileBtn.setDisabled(false);
		getBinder(incomingWin).loadAll();
	}

	public void onClick$removeFileBtn() {
		protocolDocuments.remove(protocolDocument);
		if (protocolDocument.getId() != null) { // only mark already created
												// documents for deletion
			protocolDocumentsToBeDeleted.add(protocolDocument);
		}
		renumberProtocolDocuments();
		removeFileBtn.setDisabled(true);
		getBinder(incomingWin).loadAll();
	}

	public void onClick$newBtn() {
		initData();
		getBinder(incomingWin).loadAll();
		clearValidationMessages(incomingWin);
	}

	public void onClick$saveBtn() throws Exception {

		// validate that protocol isn't already submitted
		if (protocol.getProtocolNumber() != null) {
			Messagebox.show(
					Labels.getLabel("incomingPage.protocolAlreadySubmitted"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;

		}

		validateFields(incomingWin, contactBndbx, distributionMethodCbx);

		try {
			save(false);

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("incomingPage.errorSaving"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			getBinder(incomingWin).loadAll();
			return;
		}

		try {
			Messagebox.show(Labels.getLabel("save.OK"),
					Labels.getLabel("save.title"), Messagebox.OK,
					Messagebox.INFORMATION, new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							log.info((Integer) event.getData());
							if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								Executions
										.getCurrent()
										.sendRedirect(
												IndexController.PAGE
														+ "?"
														+ IndexController.PARAM_SELECTED_TAB
														+ "=incomingTb");
								return;
							} else {
								getBinder(incomingWin).loadAll();
							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}

	}

	public void onClick$insertBtn() throws InterruptedException {

		// validate that protocol isn't already submitted
		if (protocol.getProtocolNumber() != null) {
			Messagebox.show(
					Labels.getLabel("incomingPage.protocolAlreadySubmitted"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		validateFields(incomingWin);

		if (protocolDocuments.isEmpty()) {
			Messagebox.show(Labels.getLabel("incomingPage.noDocumentsAdded"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		try {
			save(true);

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("incomingPage.errorSubmitting"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			getBinder(incomingWin).loadAll();
			return;
		}

		getBinder(incomingWin).loadAll();

		try {
			Messagebox.show(Labels.getLabel("incomingPage.protocolSubmitted",
					new String[] { protocol.getFullNumber() }), Labels
					.getLabel("success.title"), Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							log.info((Integer) event.getData());
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {
								Executions.getCurrent().sendRedirect(
										PAGE_PRINT + "?"
												+ IConstants.PARAM_KEY_ID + "="
												+ protocol.getId(), "_blank");
								return;
							} else {
								Executions.getCurrent().sendRedirect(
										IndexController.PAGE);
							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}
	}

	public void onClick$printBtn(Event event) {
		Executions.getCurrent().sendRedirect(
				PAGE_PRINT + "?" + IConstants.PARAM_KEY_ID + "="
						+ protocol.getId(), "_blank");
	}

	public void onClick$deleteBtn() {

		try {
			Messagebox.show(Labels.getLabel("incomingPage.deleteProtocol"),
					Labels.getLabel("confirm.title"), Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.YES) {

								delete();

								Messagebox.show(
										Labels.getLabel("incomingPage.deleteProtocol.success"),
										Labels.getLabel("success.title"),
										Messagebox.OK, Messagebox.INFORMATION);
								getBinder(incomingWin).loadAll();

							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}

	}

	public void onClick$relativeBtn() throws InterruptedException {

		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		List<OutgoingProtocol> outgoingProtocols = outgoingProtocolDAO.search(
				protocol.getRelativeProtocol(), null, null, null, null, null,
				null, false, false, null, null, null, new Order[0]);
		if (!outgoingProtocols.isEmpty()) {
			OutgoingProtocol relativeProtocol = outgoingProtocols.get(0);
			Executions.getCurrent().sendRedirect(
					OutgoingController.PAGE + "?" + IConstants.PARAM_KEY_ID
							+ "=" + relativeProtocol.getId());
			return;
		}

		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		List<IncomingProtocol> incomingProtocols = incomingProtocolDAO.search(
				protocol.getRelativeProtocol(), null, null, null, null, null,
				null, false, false, null, null, null, new Order[0]);
		if (!incomingProtocols.isEmpty()) {
			IncomingProtocol relativeProtocol = incomingProtocols.get(0);
			Executions.getCurrent().sendRedirect(
					IncomingController.PAGE + "?" + IConstants.PARAM_KEY_ID
							+ "=" + relativeProtocol.getId());
			return;
		}

		Messagebox
				.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);

	}

	public void onClick$taskBtn() {
		Executions.getCurrent().sendRedirect(
				"http://" + Executions.getCurrent().getServerName() + ":"
						+ Executions.getCurrent().getServerPort()
						+ "/eCase/task.zul?ip="+protocol.getId(), "_blank");
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

		if (protocol.getProtocolNumber() == null) {
			return true;
		}

		return false;
	}

	public boolean isProtocolSubmitted() {

		if (protocol.getProtocolNumber() != null) {
			return true;
		}

		return false;

	}

	public boolean isProtocolDeleted() {
		if (protocol.getIsDeleted() != null && protocol.getIsDeleted() == true) {
			return true;
		}
		return false;
	}

	public boolean isRemoveFileBtnDisabled() {
		if (protocol.getProtocolNumber() != null) {
			return true;
		}

		if (protocolDocument == null) {
			return true;
		}

		return false;

	}

	public boolean isPrintButtonDisabled() {
		return !isProtocolSubmitted();
	}

	public boolean isDeleteButtonDisabled() {

		if (getUserInSessionRole().equals(IConstants.ROLE_ADMIN)) {
			return isProtocolDeleted();
		} else

		if (getUserInSessionRole().equals(IConstants.ROLE_WRITER)) {
			return isProtocolSubmitted() | isProtocolDeleted();
		}

		return true;
	}

	public boolean isRelativeButtonDisabled() {

		if (StringUtils.trimToNull(protocol.getRelativeProtocol()) == null) {
			return true;
		}

		return false;
	}

	public boolean isTaskButtonDisabled() {
		return !isProtocolSubmitted();
	}

	public String getContactFullName() {
		if (protocol.getContact() == null) {
			return "";
		}
		return (protocol.getContact().getName() != null ? protocol.getContact()
				.getName() : "")
				+ " "
				+ (protocol.getContact().getSurname() != null ? protocol
						.getContact().getSurname() : "")
				+ " ("
				+ protocol.getContact().getCompany().getName() + ")";
	}

	public void setContactFullName(String contactFullName) {
		// do nothing
	}

	public String getProtocolBookDescription() {
		if (protocol.getProtocolBook() == null) {
			return "";
		}

		ProtocolBook protocolBook = protocol.getProtocolBook();
		return (protocolBook.getId() + "-" + protocolBook.getProtocolSeries()
				+ "-" + protocolBook.getProtocolYear());
	}

	public void setProtocolBookDescription(String protocolBookDescription) {
		// do nothing
	}

	public IncomingProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(IncomingProtocol protocol) {
		this.protocol = protocol;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
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

	public void setDistributionMethods(
			List<DistributionMethod> distributionMethods) {
		this.distributionMethods = distributionMethods;
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

	public void setProtocolDocumentsToBeDeleted(
			List<ProtocolDocument> protocolDocumentsToBeDeleted) {
		this.protocolDocumentsToBeDeleted = protocolDocumentsToBeDeleted;
	}

}
