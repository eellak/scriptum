package gr.scriptum.eprotocol.mailclients;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.DocumentTypeDAO;
import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.DocumentType;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.eprotocol.mailclients.ImapProtocolDispatcherImpl;
import gr.scriptum.eprotocol.mailclients.MailDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.RequestLogout;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseLogin;
import gr.scriptum.eprotocol.ws.ResponseNewNode;
import gr.uit.mthreads.MWork;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FetchIncomingMailsWork implements MWork {

	private static Log log = LogFactory.getLog(FetchIncomingMailsWork.class);

	public static final int DEFAULT_COMPANY_ID = 1;

	public static final int DISTRIBUTION_METHOD_EMAIL = 3;

	private static final int DOCUMENT_TYPE = 1;

	private String openKmUser, openKmPass;
	private MailDispatcherConfig mailDispatcherConfig;

	private FetchIncomingMailsWork() {
	}

	public FetchIncomingMailsWork(MailDispatcherConfig mailDispatcherConfig,
			String openKmUser, String openKmPass) {
		this();
		this.mailDispatcherConfig = mailDispatcherConfig;
		this.openKmUser = openKmUser;
		this.openKmPass = openKmPass;
	}

	private static void debug(String s) {
		log.info(s);
	}

	public void initializeWork() {
		debug("initializeWork()" + Thread.currentThread().getId());
	}

	public void performWork() {
		debug("performWork()" + Thread.currentThread().getId());

		UserTransaction tx = null;
		try {
			// tx = (UserTransaction) new InitialContext()
			// .lookup("java:comp/UserTransaction");
			tx = (UserTransaction) new InitialContext()
					.lookup("UserTransaction"); // Since the thread is created
												// outside of WorkManager, the
												// java:comp/ prefix doesn't
												// always work, hence the use of 'UserTransaction'

			if (tx == null) {
				return;
			}
			tx.begin();
		} catch (Exception e) {
			log.error(e);
			return;
		}

		try {

			// login to OpenKM and get token
			OkmProtocolDispatcherImpl okmDispatcher = new OkmProtocolDispatcherImpl(
					new OkmDispatcherConfig());

			RequestLogin loginReq = new RequestLogin();
			loginReq.setUsername(openKmUser);
			loginReq.setPassword(openKmPass);
			loginReq.setUserId(MailDaemon.MAIL_UID);
			loginReq.setSystemName(MailDaemon.SYSTEM);
			loginReq.setUserIp(InetAddress.getLocalHost().getHostAddress());

			ResponseLogin respLogin = okmDispatcher.login(loginReq);
			if (respLogin.isError()) {
				log.warn("Could not login to OpenKM " + respLogin.toString());
				throw new Exception(respLogin.toString());
			}
			debug("Logged into OpenKM");
			String okmToken = respLogin.getOkmToken();

			// Get the mail Dispatcher object to grab the mails
			ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(
					mailDispatcherConfig);
			IncomingProtocol[] incomingProtocols = disp
					.receiveIncomingProtocols();
			debug("Retrieved " + incomingProtocols.length + " mails.");

			CompanyDAO companyDAO = new CompanyDAO();
			Company company = companyDAO.findById(DEFAULT_COMPANY_ID, false);

			DistributionMethodDAO distributionMethodDAO = new DistributionMethodDAO();
			DistributionMethod distributionMethod = distributionMethodDAO
					.findById(DISTRIBUTION_METHOD_EMAIL, false);

			DocumentTypeDAO documentTypeDAO = new DocumentTypeDAO();
			DocumentType documentType = documentTypeDAO.findById(DOCUMENT_TYPE,
					false);

			IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
			ProtocolDocumentDAO protocolDocumentDAO = new ProtocolDocumentDAO();

			Date now = new Date();

			for (IncomingProtocol incomingProtocol : incomingProtocols) {
				String email = incomingProtocol.getContact().getEmail();

				// check if contact already exists in database
				ContactDAO contactDAO = new ContactDAO();
				Contact contact = new Contact();
				contact.setEmail(email);
				List<Contact> contacts = contactDAO.findByExample(contact);
				if (contacts.isEmpty()) {
					// create new contact
					contact.setCompany(company);

					contactDAO.makePersistent(contact);

				} else {// use existing contact
					contact = contacts.get(0);
				}
				incomingProtocol.setContact(contact);
				incomingProtocol.setDistributionMethod(distributionMethod);
				incomingProtocol.setCreateDt(now);
				incomingProtocol.setUpdateTs(now);
				incomingProtocolDAO.makePersistent(incomingProtocol);

				for (ProtocolDocument protocolDocument : incomingProtocol
						.getProtocolDocuments()) {
					protocolDocument.setIncomingProtocol(incomingProtocol);
					protocolDocument.setDocumentType(documentType);
					protocolDocumentDAO.makePersistent(protocolDocument);
				}

				// create new OKM pending node, including documents
				RequestNewNode requestNewNode = new RequestNewNode(loginReq);
				requestNewNode.setOkmToken(okmToken);

				requestNewNode.setFolderPath("/okm:root/PendingIncoming/"
						+ incomingProtocol.getId());

				for (ProtocolDocument document : incomingProtocol
						.getProtocolDocuments()) {
					requestNewNode.addDocument(document);
				}

				ResponseNewNode newNodeResponse = okmDispatcher
						.createNewNode(requestNewNode);
				if (newNodeResponse.isError()) {
					log.warn("Could not create new OpenKM node "
							+ newNodeResponse.toString());
					throw new Exception(newNodeResponse.toString());
				} else
					debug("New pending protocol created in OpenKm "
							+ incomingProtocol.getId());

				// update documents, storing OKM Path, UUID and size
				for (ProtocolDocument document : incomingProtocol
						.getProtocolDocuments()) {
					protocolDocumentDAO.update(document);
				}

			}

			RequestLogout requestLogout = new RequestLogout(respLogin);
			Response logoutResponse = okmDispatcher.logout(requestLogout);
			if (logoutResponse.isError()) {
				log.warn("could not logout from OpenKM : "
						+ logoutResponse.toString());
				// no need to throw exception, let things slide...
			}

			tx.commit();

		} catch (Exception e) {
			log.error(e);
			try {
				tx.rollback();
			} catch (Exception e1) {
				log.error(e1);
			}
		}

	}

	public void finilizeWork() {
		debug("finilizeWork()" + Thread.currentThread().getId());
	}
}