package gr.scriptum.eprotocol.wserver;

import gr.scriptum.dao.CompanyDAO;
import gr.scriptum.dao.ContactDAO;
import gr.scriptum.dao.DistributionMethodDAO;
import gr.scriptum.dao.IncomingProtocolDAO;
import gr.scriptum.dao.ParameterDAO;
import gr.scriptum.dao.ProtocolDocumentDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.eprotocol.ws.OkmDispatcherConfig;
import gr.scriptum.eprotocol.ws.OkmProtocolDispatcherImpl;
import gr.scriptum.eprotocol.ws.RequestLogin;
import gr.scriptum.eprotocol.ws.RequestLogout;
import gr.scriptum.eprotocol.ws.RequestNewNode;
import gr.scriptum.eprotocol.ws.Response;
import gr.scriptum.eprotocol.ws.ResponseLogin;
import gr.scriptum.eprotocol.ws.ResponseNewNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Web Service to implement the eProtocol subscription method.
 * 
 * @author Mike Mountrakis mountrakis@uit.gr
 * 
 */

@WebService(name = "ProtocolWebService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ProtocolWebService implements EProtocolWebService {

	@Resource
	WebServiceContext wsContext;

	public static final int DISTRIBUTION_METHOD_WS = 7;
	public static final int WS_UID = 777;
	public static final String SYSTEM = "WebService";
	public String openKmUser;
	public String openKmPassword;

	private static Log logger = LogFactory.getLog(ProtocolWebService.class
			.getSimpleName());

	private static void debug(String s) {
		logger.info(s);
	}

	@WebMethod
	public ProtocolReceipt receiveProtocol(
			@WebParam(name = "message") ProtocolMessage message) {

		final String ERROR = "Could not receive message. ";

		ProtocolReceipt protocolReceipt = new ProtocolReceipt();
		protocolReceipt.setClientId(message.getClientId());
		protocolReceipt.setSubject(message.getSubject());
		protocolReceipt.setYourMessageId(message.getYourMessageId());
		protocolReceipt.setReceivedDate(new Date());

		UserTransaction tx = null;
		try {
			tx = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			tx.begin();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle
			protocolReceipt.setError(ERROR);
			return protocolReceipt;
		}

		try {
			debug("receiveProtocol()" + Thread.currentThread().getId());

			// Company: Find if this client is authenticated
			Company company = authenticateClient(message.getClientId());

			// Get the OpenKm Web Service Dispatcher Client.
			ParameterDAO parameterDAO = new ParameterDAO();
			String okmAuthPortAddress = parameterDAO
					.getAsString("OKM_AUTH_PORT_ADDRESS");
			String okmDocumentPortAddress = parameterDAO
					.getAsString("OKM_DOCUMENT_PORT_ADDRESS");
			String okmFolderPortAddress = parameterDAO
					.getAsString("OKM_FOLDER_PORT_ADDRESS");
			String okmSearchPortAddress = parameterDAO
					.getAsString("OKM_SEARCH_PORT_ADDRESS");

			OkmDispatcherConfig config = new OkmDispatcherConfig(
					okmAuthPortAddress, okmDocumentPortAddress,
					okmFolderPortAddress, okmSearchPortAddress);

			OkmProtocolDispatcherImpl okmDispatcher = new OkmProtocolDispatcherImpl(
					config);

			// Create a RequestLogin in order to login into OpenKM and get
			// security token
			RequestLogin loginReq = new RequestLogin();
			loginReq.setUsername(openKmUser);
			loginReq.setPassword(openKmPassword);
			loginReq.setUserId(WS_UID);
			loginReq.setSystemName(SYSTEM);
			// loginReq.setUserIp( InetAddress.getLocalHost().getHostAddress()
			// );
			// Put the caller's IP inside the request instead of server's ip
			loginReq.setUserIp(getClientIp());

			// login to OpenKM and get token
			ResponseLogin respLogin = okmDispatcher.login(loginReq);
			if (respLogin.isError()) {
				logger.warn("Could not login to OpenKM " + respLogin.toString());
				throw new Exception(respLogin.toString());
			}
			debug("Logged into OpenKM");
			String okmToken = respLogin.getOkmToken();

			// Parse an IncomingProtocol from a WS message
			IncomingProtocol newIncoming = incomingProtocolFromProtocolMessage(message);
			newIncoming.setCreateUserId(WS_UID);

			// Distribution Method
			DistributionMethodDAO distributionMethodDAO = new DistributionMethodDAO();
			DistributionMethod distributionMethod = distributionMethodDAO
					.findById(DISTRIBUTION_METHOD_WS, false);

			// Contact from Company
			ContactDAO contactDAO = new ContactDAO();
			Contact contact = new Contact();
			contact.setCompany(company);
			contact.setName(company.getName());
			List<Contact> contacts = contactDAO.findByExample(contact);
			if (contacts.isEmpty()) {
				contact.setCompany(company);
				contactDAO.makePersistent(contact);
			} else {// use existing contact
				contact = contacts.get(0);
			}

			// deal with the Incoming Protocol, write it on the backend.
			Date now = new Date();
			IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
			ProtocolDocumentDAO protocolDocumentDAO = new ProtocolDocumentDAO();

			newIncoming.setContact(contact);
			newIncoming.setDistributionMethod(distributionMethod);
			newIncoming.setCreateDt(now);
			newIncoming.setUpdateTs(now);
			incomingProtocolDAO.makePersistent(newIncoming);

			for (ProtocolDocument protocolDocument : newIncoming
					.getProtocolDocuments()) {
				protocolDocument.setIncomingProtocol(newIncoming);
				protocolDocumentDAO.makePersistent(protocolDocument);
			}

			// create new OKM pending node, including documents
			RequestNewNode requestNewNode = new RequestNewNode(loginReq);
			requestNewNode.setOkmToken(okmToken);

			requestNewNode.setFolderPath("/okm:root/PendingIncoming/"
					+ newIncoming.getId());

			// Create the documents in this node
			for (ProtocolDocument document : newIncoming.getProtocolDocuments()) {
				requestNewNode.addDocument(document);
			}

			ResponseNewNode newNodeResponse = okmDispatcher
					.createNewNode(requestNewNode);
			if (newNodeResponse.isError()) {
				logger.warn("Could not create new OpenKM node "
						+ newNodeResponse.toString());
				throw new Exception(newNodeResponse.toString());
			} else
				debug("New pending protocol created in OpenKm "
						+ newIncoming.getId());

			// update documents, storing OKM Path, UUID and size
			for (ProtocolDocument document : newIncoming.getProtocolDocuments()) {
				protocolDocumentDAO.update(document);
			}

			RequestLogout requestLogout = new RequestLogout(respLogin);
			Response logoutResponse = okmDispatcher.logout(requestLogout);
			if (logoutResponse.isError()) {
				logger.warn("could not logout from OpenKM : "
						+ logoutResponse.toString());
				// no need to throw exception, let things slide...
			}

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace(); // nothing further to do
			}
			protocolReceipt.setError(ERROR + " Reason: " + e.getMessage());
		}

		return protocolReceipt;

	}

	@WebMethod
	public ProtocolInfo inquireProtocol(
			@WebParam(name = "query") ProtocolQuery query) {
		final String ERROR = "Cound not query protocol. ";

		ProtocolInfo protocolInfo = new ProtocolInfo();
		protocolInfo.setClientId(query.getClientId());
		protocolInfo.setProtocolId(query.getProtocolId());

		UserTransaction tx = null;
		try {
			tx = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			tx.begin();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle
			protocolInfo.setError(ERROR);
			return protocolInfo;
		}

		try {
			// Find the name of the sender.
			Company company = authenticateClient(query.getClientId());
			protocolInfo.setClientName(company.getName());

			// find the protocol
			IncomingProtocolDAO incomingProtocolDAO = new IncomingProtocolDAO();
			IncomingProtocol iprotocol = incomingProtocolDAO.findById(
					query.getProtocolId(), false);
			if (iprotocol != null) {
				protocolInfo.setComments(iprotocol.getComments());
				protocolInfo.setProtocolNumber(iprotocol.getProtocolNumber());
//				protocolInfo.setProtocolSeries(iprotocol.getProtocolSeries());
//				protocolInfo.setProtocolYear(iprotocol.getProtocolYear());
				protocolInfo.setProtocolDate(iprotocol.getProtocolDate());
				protocolInfo.setSubject(iprotocol.getSubject());
			} else
				protocolInfo.setComments("Not found.");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				tx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace(); // nothing further to do
			}
			protocolInfo.setError(ERROR + " Reason: " + e.getMessage());
		}

		return protocolInfo;
	}

	// --------------------------------------------------
	// Helper Methods
	// --------------------------------------------------

	private String getInitParam(String paramName) {
		String mBeanName = null;
		try {
			MessageContext mContext = wsContext.getMessageContext();
			ServletContext serv = (ServletContext) mContext
					.get(MessageContext.SERVLET_CONTEXT);

			String paramVal = serv.getInitParameter(paramName);
			System.out.println("Requested param value : " + paramVal);

			return paramVal;
		} catch (Exception e) {
			; // do nothing: simply parameter does not exists
		}

		return mBeanName;
	}

	/*
	 * look for this mbean <mbean
	 * name="wsconfigurator.uit.gr:service=ProtocolWebServiceConfiguration"
	 * code=
	 * "gr.scriptum.eprotocol.wsconfigurator.ProtocolWebServiceConfiguration"
	 * description=
	 * "Used in order to configure from jmx console an active eProtocol Web Service."
	 * > </mbean>
	 */
	private void getConfiguration() throws Exception {
		openKmUser = getInitParam("openKmUser");
		openKmPassword = getInitParam("openKmPassword");
	}

	private final String getClientIp() {

		MessageContext mc = wsContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest) mc
				.get(MessageContext.SERVLET_REQUEST);
		debug("Client IP = " + req.getRemoteAddr());
		return req.getRemoteAddr();
	}

	public final String getServerIp() {
		String serverIp = "localhost";
		try {
			serverIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			debug(e.getMessage());
			e.printStackTrace();
		}
		return serverIp;
	}

	private Company authenticateClient(Integer clientId) throws Exception {
		// Company: Find if this client is authenticated
		CompanyDAO companyDAO = new CompanyDAO();
		Company company = companyDAO.findById(clientId, false);
		if (company == null) {
			throw new Exception("No authority to use this WS. Leave at once!");
		} else {
			debug("Client authenticated : " + company.getName());
		}
		return company;
	}

	private IncomingProtocol incomingProtocolFromProtocolMessage(
			ProtocolMessage message) {
		IncomingProtocol newIncoming = new IncomingProtocol();

		newIncoming.setSubject(message.getSubject());
		newIncoming.setComments(message.getComments());
		newIncoming.setIncomingProtocolNumber(message.getYourMessageId());
		newIncoming.setIncomingPlace(message.getPlace());
		newIncoming.setIncomingDate(new Date());
		newIncoming.setCreateDt(new Date());

		for (WsDocument doc : message.getAttachements()) {
			ProtocolDocument protocolDoc = new ProtocolDocument();
			protocolDoc.setContent(doc.getContent());
			protocolDoc.setDocumentNumber(doc.getIndex());
			protocolDoc.setDocumentSize(new Long(doc.getByteFileSize()));
			protocolDoc.setDocumentName(doc.getFilename());
			protocolDoc.setDocumentKeywords(keywordsFromTitle(doc.getTitle()));
			newIncoming.getProtocolDocuments().add(protocolDoc);
		}

		return newIncoming;
	}

	private String keywordsFromTitle(String title) {
		String keywords = "";
		if (title != null) {
			if (!title.equals("")) {
				String[] kwords = title.split(" ");
				for (String word : kwords)
					keywords += word + ",";
			}
		}
		keywords += "FROM_WEB_SERVICE";
		return keywords;
	}

}
