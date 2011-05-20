/**
 * 
 */
package gr.scriptum.eprotocol.controller;

import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.OutgoingProtocolDAO;
import gr.scriptum.dao.OutgoingRecipientDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.dao.ProtocolNumberDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.OutgoingRecipient;
import gr.scriptum.domain.OutgoingRecipient.RecipientType;
import gr.scriptum.domain.OutgoingRecipientId;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNumber;
import gr.scriptum.domain.ProtocolNumber.ProtocolNumberType;
import gr.scriptum.eprotocol.mailclients.ImapProtocolDispatcherImpl;
import gr.scriptum.eprotocol.mailclients.MailDispatcherConfig;
import gr.scriptum.eprotocol.mailclients.SendMailReceipt;
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
import gr.scriptum.eprotocol.ws.ResponseSendDocument;

import java.util.ArrayList;
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
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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

/**
 * @author aanagnostopoulos
 * 
 */
public class OutgoingController extends ProtocolController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733338289082257531L;

	private static Log log = LogFactory.getLog(OutgoingController.class);

	public static final String PAGE = "outgoing.zul";

	private static final String PAGE_PRINT = "outgoingPrint.zul";

	private int distributionMethodEmailId;

	private int distributionMethodWebServiceId;

	private String smtpHost = null;

	private String smtpUser = null;

	private String smtpPassword = null;

	private String emailFrom = null;

	/* data binding */
	private OutgoingProtocol protocol = null;

	private List<Contact> toContacts = null;

	private Contact toContact = null;

	private List<Contact> ccContacts = null;

	private Contact ccContact = null;

	private List<OutgoingRecipient> toList = null;

	private List<OutgoingRecipient> toListToBeDeleted = null;

	private List<OutgoingRecipient> ccList = null;

	private List<OutgoingRecipient> ccListToBeDeleted = null;

	private OutgoingRecipient toOutgoingRecipient = null;

	private OutgoingRecipient ccOutgoingRecipient = null;

	private List<DistributionMethod> distributionMethods = null;

	private List<ProtocolDocument> protocolDocuments = null;

	private List<ProtocolDocument> protocolDocumentsToBeDeleted = null;

	private String okmNodePendingOutgoing = null;

	private String okmNodeOutgoing = null;

	private Integer defaultDistributionMethodId = null;

	private String toTerm = null;

	private String ccTerm = null;

	/* components */
	Window outgoingWin;

	Window uploadWin;

	Window contactWin;

	Listbox documentsLstbx;

	Listbox toLstbx;

	Listbox ccLstbx;

	Button removeFileBtn;

	Button removeToBtn;

	Button removeCcBtn;

	Bandbox toBndbx;

	Bandbox ccBndbx;

	Paging toContactsPgng;

	Paging ccContactsPgng;

	Combobox distributionMethodCbx;

	private void initData() {
		protocol = new OutgoingProtocol();
		toContacts = new ArrayList<Contact>();
		toContact = null;
		ccContacts = new ArrayList<Contact>();
		ccContact = null;
		toList = new LinkedList<OutgoingRecipient>();
		toListToBeDeleted = new LinkedList<OutgoingRecipient>();
		ccList = new LinkedList<OutgoingRecipient>();
		ccListToBeDeleted = new LinkedList<OutgoingRecipient>();
		toOutgoingRecipient = null;
		ccOutgoingRecipient = null;
		protocolDocuments = new LinkedList<ProtocolDocument>();
		protocolDocumentsToBeDeleted = new ArrayList<ProtocolDocument>();
		protocolDocument = null;
		toTerm = "";
		ccTerm = "";
		for (DistributionMethod distributionMethod : distributionMethods) {
			if (distributionMethod.getId().equals(defaultDistributionMethodId)) {
				protocol.setDistributionMethod(distributionMethod);
				break;
			}
		}
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

				protocolNumber = protocolNumberDAO
						.getNextNumber(ProtocolNumberType.COMMON);

				tx.commit();
				log.info("Got Protocol Number (Commited transaction): " + tx);

				protocol.setProtocolNumber(protocolNumber.getNumber()
						.intValue());
				protocol.setProtocolSeries(protocolNumber.getSeries());
				protocol.setProtocolYear(protocolNumber.getYear());
				protocol.setProtocolDate(now);

			}

			tx.begin();
			log.info("Saving Protocol (Started transaction): " + tx);

			OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
			ProtocolDocumentDAO protocolDocumentDAO = new ProtocolDocumentDAO();
			OutgoingRecipientDAO outgoingRecipientDAO = new OutgoingRecipientDAO();
			OkmProtocolDispatcherImpl okmDispatcher = getOkmDispatcher();

			if (protocol.getId() == null) { // new protocol, create

				protocol.setCreateDt(now);
				protocol.setCreateUserId(getUserInSession().getId());

				/* local database actions */
				outgoingProtocolDAO.makePersistent(protocol);

				// Create documents
				for (ProtocolDocument document : protocolDocuments) {
					document.setOutgoingProtocol(protocol);
					protocolDocumentDAO.makePersistent(document);
					protocol.getProtocolDocuments().add(document);
				}

				// Create recipients
				for (OutgoingRecipient toRecipient : toList) {
					toRecipient.getId().setOutgoingProtocol(protocol);
					outgoingRecipientDAO.makePersistent(toRecipient);
					protocol.getOutgoingRecipients().add(toRecipient);
				}

				for (OutgoingRecipient ccRecipient : ccList) {
					ccRecipient.getId().setOutgoingProtocol(protocol);
					outgoingRecipientDAO.makePersistent(ccRecipient);
					protocol.getOutgoingRecipients().add(ccRecipient);
				}

				/* OpenKM actions */

				// create new OKM pending node, including documents
				RequestNewNode requestNewNode = new RequestNewNode(
						getUserInSession().getUsername(), getUserInSession()
								.getId(), getIp(), IConstants.SYSTEM_NAME,
						getOkmToken());

				if (isSubmission) { // final protocol submission
					requestNewNode.setFolderPath(okmNodeOutgoing
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getProtocolNumber()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolYear());

				} else {// pending protocol being stored
					requestNewNode.setFolderPath(okmNodePendingOutgoing
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
				outgoingProtocolDAO.update(protocol);

				// process newly added & existing documents
				List<ProtocolDocument> newlyAddedDocuments = new LinkedList<ProtocolDocument>();
				for (ProtocolDocument document : protocolDocuments) {
					if (document.getId() != null) { // existing document, update

						protocolDocumentDAO.update(document);

					} else {// new document, create
						document.setOutgoingProtocol(protocol);
						protocolDocumentDAO.makePersistent(document);
						protocol.getProtocolDocuments().add(document);
						newlyAddedDocuments.add(document);
					}
				}

				// Delete documents marked respectively
				for (ProtocolDocument document : protocolDocumentsToBeDeleted) {
					protocolDocumentDAO.delete(document);
					protocol.getProtocolDocuments().remove(document);
				}

				// process 'to' new & existing recipients
				for (OutgoingRecipient toRecipient : toList) {
					if (toRecipient.getId().getOutgoingProtocol() != null) {

						// existing recipient, nothing specific to do (for now,
						// at
						// least)

					} else { // new recipient, create
						toRecipient.getId().setOutgoingProtocol(protocol);
						outgoingRecipientDAO.makePersistent(toRecipient);
						protocol.getOutgoingRecipients().add(toRecipient);
					}
				}

				// process 'cc' new & existing recipients
				for (OutgoingRecipient ccRecipient : ccList) {
					if (ccRecipient.getId().getOutgoingProtocol() != null) {

						// existing recipient, nothing specific to do (for now,
						// at
						// least)

					} else { // new recipient, create
						ccRecipient.getId().setOutgoingProtocol(protocol);
						outgoingRecipientDAO.makePersistent(ccRecipient);
						protocol.getOutgoingRecipients().add(ccRecipient);
					}
				}

				// Delete 'to' recipients marked respectively
				for (OutgoingRecipient toRecipient : toListToBeDeleted) {
					outgoingRecipientDAO.delete(toRecipient);
					protocol.getOutgoingRecipients().remove(toRecipient);
				}

				// Delete 'cc' recipients marked respectively
				for (OutgoingRecipient ccRecipient : ccListToBeDeleted) {
					outgoingRecipientDAO.delete(ccRecipient);
					protocol.getOutgoingRecipients().remove(ccRecipient);
				}

				/* OpenKM actions */

				// add newly added documents to OpenKM
				for (ProtocolDocument document : newlyAddedDocuments) {

					RequestAddDocumentToNode request = new RequestAddDocumentToNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					request.setNodePath(okmNodePendingOutgoing
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
					// move 'pending' OpenKM node to 'outgoing'
					RequestMoveNode requestMoveNode = new RequestMoveNode(
							getUserInSession().getUsername(),
							getUserInSession().getId(), getIp(),
							IConstants.SYSTEM_NAME, getOkmToken());
					requestMoveNode.setOldPath(okmNodePendingOutgoing
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
					requestMoveNode.setNewPath(okmNodeOutgoing);

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
					requestRenameNode.setOldName(okmNodeOutgoing
							+ IConstants.OKM_FOLDER_DELIMITER
							+ protocol.getId());
					requestRenameNode.setNewName(protocol.getProtocolNumber()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolSeries()
							+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
							+ protocol.getProtocolYear());

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
										okmNodePendingOutgoing
												+ IConstants.OKM_FOLDER_DELIMITER
												+ protocol.getId(),
										okmNodeOutgoing
												+ IConstants.OKM_FOLDER_DELIMITER
												+ protocol.getProtocolNumber()
												+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
												+ protocol.getProtocolSeries()
												+ IConstants.OKM_PROTOCOL_NUMBER_DELIMITER
												+ protocol.getProtocolYear());
						document.setOkmPath(path);
						protocolDocumentDAO.update(document);
					}
				}
			}

			tx.commit();
			log.info("Saved Protocol (Committed transaction): " + tx);

			// clean up
			protocolDocumentsToBeDeleted.clear();
			toListToBeDeleted.clear();
			ccListToBeDeleted.clear();

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
		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		protocol.setIsDeleted(true);
		outgoingProtocolDAO.update(protocol);
	}

	private void emailProtocol() throws Exception {

		// fetch document content from OpenKM
		for (ProtocolDocument document : protocol.getProtocolDocuments()) {
			ResponseSendDocument responseSendDocument = fetchDocumentFromOpenKM(document);
			document.setContent(responseSendDocument.getContent());
		}

		MailDispatcherConfig config = new MailDispatcherConfig();
		config.setSmtpHost(smtpHost);
		config.setSmtpUser(smtpUser);
		config.setSmtpPassword(smtpPassword);
		config.setMessageFrom(emailFrom);
		config.setIMAP(); // works
		config.setDebug(true);

		ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(config);
		SendMailReceipt sendMailReceipt = disp.sendOutgoingProtocol(protocol);
		if (sendMailReceipt.isError()) {
			throw new Exception(sendMailReceipt.geteCode() + ":"
					+ sendMailReceipt.geteMessage());
		}

	}

	private void sendProtocolToWebService() {

		// TODO: implement
	}

	private void searchToContacts(Integer startIndex) {
		ContactDAO contactDAO = new ContactDAO();

		// set up paging by counting records first
		Integer totalSize = contactDAO.countByTerm(toTerm);
		toContactsPgng.setTotalSize(totalSize);
		int pageSize = toContactsPgng.getPageSize();

		toContacts = contactDAO.findByTerm(toTerm, startIndex, pageSize);
		for (Contact toContact : toContacts) {
			toContact.getCompany().getName().toString(); // force hibernate to
															// fetch company
		}

	}

	private void searchCcContacts(Integer startIndex) {
		ContactDAO contactDAO = new ContactDAO();

		// set up paging by counting records first
		Integer totalSize = contactDAO.countByTerm(ccTerm);
		ccContactsPgng.setTotalSize(totalSize);
		int pageSize = ccContactsPgng.getPageSize();

		ccContacts = contactDAO.findByTerm(ccTerm, startIndex, pageSize);
		for (Contact ccContact : ccContacts) {
			ccContact.getCompany().getName().toString(); // force hibernate to
															// fetch company
		}

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		page.setAttribute(this.getClass().getSimpleName(), this);

		ParameterDAO parameterDAO = new ParameterDAO();
		okmNodePendingOutgoing = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_PENDING_OUTGOING);
		okmNodeOutgoing = parameterDAO
				.getAsString(IConstants.PARAM_OKM_NODE_OUTGOING);

		distributionMethodEmailId = parameterDAO
				.getAsInteger(IConstants.PARAM_DISTRIBUTION_METHOD_EMAIL_ID);
		distributionMethodWebServiceId = parameterDAO
				.getAsInteger(IConstants.PARAM_DISTRIBUTION_METHOD_WEBSERVICE_ID);
		defaultDistributionMethodId = parameterDAO
				.getAsInteger(IConstants.PARAM_DISTRIBUTION_METHOD_NA_ID);

		smtpHost = parameterDAO.getAsString(IConstants.PARAM_SMTP_HOST);
		smtpHost = parameterDAO.getAsString(IConstants.PARAM_SMTP_USER);
		smtpPassword = parameterDAO.getAsString(IConstants.PARAM_SMTP_PASSWORD);
		emailFrom = parameterDAO.getAsString(IConstants.PARAM_EMAIL_FROM);

		DistributionMethodDAO distributionMethodDAO = new DistributionMethodDAO();
		distributionMethods = distributionMethodDAO.findAll();

		initData();

		// fetch existing protocol, if any
		String idString = execution.getParameter(IConstants.PARAM_KEY_ID);
		if (idString != null) {
			OutgoingProtocolDAO incomingProtocolDAO = new OutgoingProtocolDAO();
			protocol = incomingProtocolDAO.findById(Integer.valueOf(idString),
					false);

			if (protocol == null) { // not found
				Messagebox.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);
				protocol = new OutgoingProtocol();
				for (DistributionMethod distributionMethod : distributionMethods) {
					if (distributionMethod.getId().equals(
							defaultDistributionMethodId)) {
						protocol.setDistributionMethod(distributionMethod);
						break;
					}
				}
				return;
			}
			// populate recipients
			for (OutgoingRecipient recipient : protocol.getOutgoingRecipients()) {
				if (recipient.getRecipientType().equals(RecipientType.TO)) {
					toList.add(recipient);
				} else {
					ccList.add(recipient);
				}
			}
			// populate protocol documents listbox
			protocolDocuments = new LinkedList<ProtocolDocument>(
					protocol.getProtocolDocuments());
		}

		if (page.getRequestPath().endsWith(PAGE_PRINT)) {
			Clients.print();
		}

	}

	public void onChanging$toBndbx(InputEvent event) {
		toTerm = StringUtils.trimToNull(event.getValue());
		searchToContacts(0);
		getBinder(outgoingWin).loadAll();
	}

	public void onOpen$toBndbx(OpenEvent event) {
		if (!toContacts.isEmpty()) {
			return;
		}
		toTerm = "";
		searchToContacts(0);
		getBinder(outgoingWin).loadAll();
	}

	public void onPaging$toContactsPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * toContactsPgng.getPageSize();
		searchToContacts(startIndex);
		getBinder(outgoingWin).loadAll();
	}

	public void onSelect$toContactsLstbx(SelectEvent event) {
		toBndbx.setText("");
		toBndbx.close();
		// check if 'to' list already contains selected toContact
		for (OutgoingRecipient recipient : toList) {
			if (recipient.getId().getContact().getId()
					.equals(toContact.getId())) {
				// found, don't add
				toContact = null;
				getBinder(outgoingWin).loadAll();
				return;
			}
		}
		// check if 'cc' list already contains selected toContact
		for (OutgoingRecipient recipient : ccList) {
			if (recipient.getId().getContact().getId()
					.equals(toContact.getId())) {
				// found, don't add
				toContact = null;
				getBinder(outgoingWin).loadAll();
				return;
			}
		}

		OutgoingRecipient recipient = new OutgoingRecipient(
				new OutgoingRecipientId(toContact, null));
		recipient.setRecipientType(RecipientType.TO);
		toList.add(recipient);
		toContact = null;
		getBinder(outgoingWin).loadAll();
	}

	public void onClick$newToBtn() throws SuspendNotAllowedException,
			InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK,
				new Callback(toLstbx, "onToAdded"));

		contactWin = (Window) Executions.createComponents(
				ContactController.INCLUDE_CONTACT_WIN, outgoingWin, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	public void onToAdded$toLstbx(Event event) {
		Contact contact = (Contact) ((ForwardEvent) event).getOrigin()
				.getData();
		OutgoingRecipient recipient = new OutgoingRecipient(
				new OutgoingRecipientId(contact, null));
		recipient.setRecipientType(RecipientType.TO);
		toList.add(recipient);

		getBinder(outgoingWin).loadAll();
	}

	public void onSelect$toLstbx(SelectEvent event) {
		if (event.getSelectedItems().isEmpty()) {
			removeToBtn.setDisabled(true);
			return;
		}

		removeToBtn.setDisabled(false);
	}

	public void onClick$removeToBtn() {
		toList.remove(toOutgoingRecipient);
		// only mark for deletion recipients already created
		if (toOutgoingRecipient.getId().getOutgoingProtocol() != null) {
			toListToBeDeleted.add(toOutgoingRecipient);
		}
		removeToBtn.setDisabled(true);
		getBinder(outgoingWin).loadAll();
	}

	public void onChanging$ccBndbx(InputEvent event) {
		ccTerm = StringUtils.trimToNull(event.getValue());
		searchCcContacts(0);
		getBinder(outgoingWin).loadAll();
	}

	public void onOpen$ccBndbx(OpenEvent event) {
		if (!ccContacts.isEmpty()) {
			return;
		}
		ccTerm = "";
		searchCcContacts(0);
		getBinder(outgoingWin).loadAll();
	}

	public void onPaging$ccContactsPgng(PagingEvent event) {
		int activePage = event.getActivePage();
		int startIndex = activePage * ccContactsPgng.getPageSize();
		searchCcContacts(startIndex);
		getBinder(outgoingWin).loadAll();
	}

	public void onSelect$ccContactsLstbx(SelectEvent event) {
		ccBndbx.setText("");
		ccBndbx.close();
		// check if 'cc' list already contains selected toContact
		for (OutgoingRecipient recipient : ccList) {
			if (recipient.getId().getContact().getId()
					.equals(ccContact.getId())) {
				// found, don't add
				ccContact = null;
				getBinder(outgoingWin).loadAll();
				return;
			}
		}
		// check if 'to' list already contains selected toContact
		for (OutgoingRecipient recipient : toList) {
			if (recipient.getId().getContact().getId()
					.equals(ccContact.getId())) {
				// found, don't add
				ccContact = null;
				getBinder(outgoingWin).loadAll();
				return;
			}
		}
		OutgoingRecipient recipient = new OutgoingRecipient(
				new OutgoingRecipientId(ccContact, null));
		recipient.setRecipientType(RecipientType.CC);
		ccList.add(recipient);
		ccContact = null;
		getBinder(outgoingWin).loadAll();
	}

	public void onClick$newCcBtn() throws SuspendNotAllowedException,
			InterruptedException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK,
				new Callback(ccLstbx, "onCcAdded"));

		contactWin = (Window) Executions.createComponents(
				ContactController.INCLUDE_CONTACT_WIN, outgoingWin, params);
		contactWin.setClosable(true);
		contactWin.doModal();
	}

	public void onCcAdded$ccLstbx(Event event) {
		Contact contact = (Contact) ((ForwardEvent) event).getOrigin()
				.getData();
		OutgoingRecipient recipient = new OutgoingRecipient(
				new OutgoingRecipientId(contact, null));
		recipient.setRecipientType(RecipientType.TO);
		ccList.add(recipient);

		getBinder(outgoingWin).loadAll();
	}

	public void onSelect$ccLstbx(SelectEvent event) {
		if (event.getSelectedItems().isEmpty()) {
			removeCcBtn.setDisabled(true);
			return;
		}

		removeCcBtn.setDisabled(false);
	}

	public void onClick$removeCcBtn() {
		ccList.remove(ccOutgoingRecipient);
		// only mark for deletion recipients already created
		if (ccOutgoingRecipient.getId().getOutgoingProtocol() != null) {
			ccListToBeDeleted.add(ccOutgoingRecipient);
		}
		removeCcBtn.setDisabled(true);
		getBinder(outgoingWin).loadAll();
	}

	public void onClick$addFileBtn() throws SuspendNotAllowedException,
			InterruptedException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IConstants.PARAM_CALLBACK, new Callback(documentsLstbx,
				"onDocumentAdded"));
		uploadWin = (Window) Executions.createComponents(UploadController.PAGE,
				outgoingWin, params);
		uploadWin.setClosable(true);
		uploadWin.doModal();

	}

	public void onDocumentAdded$documentsLstbx(Event event) {
		ProtocolDocument document = (ProtocolDocument) ((ForwardEvent) event)
				.getOrigin().getData();
		document.setDocumentNumber(protocolDocuments.size() + 1);
		protocolDocuments.add(document);
		getBinder(outgoingWin).loadAll();
	}

	public void onSelect$documentsLstbx(SelectEvent event) {
		// if (event.getSelectedItems().isEmpty()) {
		// removeFileBtn.setDisabled(true);
		// return;
		// }
		//
		// removeFileBtn.setDisabled(false);
		getBinder(outgoingWin).loadAll();
	}

	public void onClick$removeFileBtn() {
		protocolDocuments.remove(protocolDocument);
		if (protocolDocument.getId() != null) { // only mark already created
												// documents for deletion
			protocolDocumentsToBeDeleted.add(protocolDocument);
		}
		renumberProtocolDocuments();
		removeFileBtn.setDisabled(true);
		getBinder(outgoingWin).loadAll();
	}

	public void onClick$newBtn() {
		initData();
		getBinder(outgoingWin).loadAll();
		clearValidationMessages(outgoingWin);
	}

	public void onClick$saveBtn() throws InterruptedException {

		// validate that protocol isn't already submitted
		if (protocol.getProtocolNumber() != null) {
			Messagebox.show(
					Labels.getLabel("incomingPage.protocolAlreadySubmitted"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		validateFields(outgoingWin, distributionMethodCbx);

		try {
			save(false);

		} catch (Exception e) {
			log.error(e);
			Messagebox.show(Labels.getLabel("incomingPage.errorSaving"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			getBinder(outgoingWin).loadAll();
			return;
		}

		// Messagebox.show(Labels.getLabel("save.OK"),
		// Labels.getLabel("save.title"), Messagebox.OK,
		// Messagebox.INFORMATION);
		// getBinder(outgoingWin).loadAll();

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
														+ "=outgoingTb");
								return;
							} else {
								getBinder(outgoingWin).loadAll();
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

		validateFields(outgoingWin);

		if (protocolDocuments.isEmpty()) {
			Messagebox.show(Labels.getLabel("incomingPage.noDocumentsAdded"),
					Labels.getLabel("error.title"), Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		if (toList.isEmpty()) {
			Messagebox.show(Labels.getLabel("outgoingPage.noToAdded"),
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
			getBinder(outgoingWin).loadAll();
			return;
		}

		// send any notifications to third party systems (e.g. email, web
		// service), based on selected distribution method
		if (protocol.getDistributionMethod().getId() == distributionMethodEmailId) {
			try {

				emailProtocol();

			} catch (Exception e) {
				log.error(e);
				Messagebox.show(Labels.getLabel(
						"outgoingPage.errorSendingEmail",
						new String[] { protocol.getFullNumber() }), Labels
						.getLabel("success.title"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				getBinder(outgoingWin).loadAll();
				return;
			}

		} else if (protocol.getDistributionMethod().getId() == distributionMethodWebServiceId) {
			try {
				sendProtocolToWebService();
			} catch (Exception e) {
				log.error(e);
				Messagebox.show(Labels.getLabel(
						"outgoingPage.errorSendingWebService",
						new String[] { protocol.getFullNumber() }), Labels
						.getLabel("success.title"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				getBinder(outgoingWin).loadAll();
				return;
			}
		}

		// Messagebox.show(Labels.getLabel("incomingPage.protocolSubmitted",
		// new String[] { protocol.getFullNumber() }), Labels
		// .getLabel("success.title"), Messagebox.OK,
		// Messagebox.INFORMATION);

		getBinder(outgoingWin).loadAll();

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
								getBinder(outgoingWin).loadAll();

							}
						}
					});
		} catch (InterruptedException e) {
			// swallow
		}

	}

	public void onClick$relativeBtn() throws InterruptedException {

		IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
		List<IncomingProtocol> incomingProtocols = incomingProtocolDAO.search(
				protocol.getRelativeProtocol(), null, null, null, null, null,
				null, false, null, null, new Order[0]);
		if (!incomingProtocols.isEmpty()) {
			IncomingProtocol relativeProtocol = incomingProtocols.get(0);
			Executions.getCurrent().sendRedirect(
					IncomingController.PAGE + "?" + IConstants.PARAM_KEY_ID
							+ "=" + relativeProtocol.getId());
			return;
		}

		OutgoingProtocolDAO outgoingProtocolDAO = new OutgoingProtocolDAO();
		List<OutgoingProtocol> outgoingProtocols = outgoingProtocolDAO.search(
				protocol.getRelativeProtocol(), null, null, null, null, null,
				null, false, null, null, new Order[0]);
		if (!outgoingProtocols.isEmpty()) {
			OutgoingProtocol relativeProtocol = outgoingProtocols.get(0);
			Executions.getCurrent().sendRedirect(
					OutgoingController.PAGE + "?" + IConstants.PARAM_KEY_ID
							+ "=" + relativeProtocol.getId());
			return;
		}

		Messagebox
				.show(Labels.getLabel("fetch.notFound"),
						Labels.getLabel("fetch.title"), Messagebox.OK,
						Messagebox.ERROR);

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

	public boolean isProtocolPending() {

		if (protocol.getProtocolNumber() == null) {
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

	public OutgoingProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(OutgoingProtocol protocol) {
		this.protocol = protocol;
	}

	public List<OutgoingRecipient> getToList() {
		return toList;
	}

	public void setToList(List<OutgoingRecipient> toList) {
		this.toList = toList;
	}

	public List<OutgoingRecipient> getCcList() {
		return ccList;
	}

	public void setCcList(List<OutgoingRecipient> ccList) {
		this.ccList = ccList;
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

	public OutgoingRecipient getToOutgoingRecipient() {
		return toOutgoingRecipient;
	}

	public void setToOutgoingRecipient(OutgoingRecipient toOutgoingRecipient) {
		this.toOutgoingRecipient = toOutgoingRecipient;
	}

	public OutgoingRecipient getCcOutgoingRecipient() {
		return ccOutgoingRecipient;
	}

	public void setCcOutgoingRecipient(OutgoingRecipient ccOutgoingRecipient) {
		this.ccOutgoingRecipient = ccOutgoingRecipient;
	}

	public List<Contact> getToContacts() {
		return toContacts;
	}

	public void setToContacts(List<Contact> contacts) {
		this.toContacts = contacts;
	}

	public Contact getToContact() {
		return toContact;
	}

	public void setToContact(Contact contact) {
		this.toContact = contact;
	}

	public List<Contact> getCcContacts() {
		return ccContacts;
	}

	public void setCcContacts(List<Contact> ccContacts) {
		this.ccContacts = ccContacts;
	}

	public Contact getCcContact() {
		return ccContact;
	}

	public void setCcContact(Contact ccContact) {
		this.ccContact = ccContact;
	}

}
