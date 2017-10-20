package gr.scriptum.webserviceclient.test;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import gr.scriptum.webservice.client.BaseProtocolMessage.Transactors;
import gr.scriptum.webservice.client.Author;
import gr.scriptum.webservice.client.CorrespondentAction;
import gr.scriptum.webservice.client.CorrespondentType;
import gr.scriptum.webservice.client.EProtocolWebService;
import gr.scriptum.webservice.client.Exception_Exception;
import gr.scriptum.webservice.client.IncomingProtocolMessage;
import gr.scriptum.webservice.client.IncomingProtocolMessage.InternalRecipients;
import gr.scriptum.webservice.client.InternalRecipient;
import gr.scriptum.webservice.client.OutgoingProtocolMessage;
import gr.scriptum.webservice.client.OutgoingProtocolMessage.Recipients;
import gr.scriptum.webservice.client.ProtocolReceipt;
import gr.scriptum.webservice.client.ProtocolWebServiceFault;
import gr.scriptum.webservice.client.ProtocolWebServiceFaultBean.Reasons;
import gr.scriptum.webservice.client.ProtocolWebServiceService;
import gr.scriptum.webservice.client.Recipient;
import gr.scriptum.webservice.client.Sender;
import gr.scriptum.webservice.client.Transactor;
import gr.scriptum.webservice.client.TransactorType;

/**
 * Test client
 *
 */
public class ReceiveOutgoingProtocolTest {

	public static void main(String[] args) throws DatatypeConfigurationException  {

		// get web service
		ProtocolWebServiceService service = new ProtocolWebServiceService();
		EProtocolWebService port = service.getProtocolWebServicePort();

		// set authentication
		Map<String, List<String>> requestHeaders = new HashMap<String, List<String>>();
		requestHeaders.put("username", Arrays.asList("admin"));
		requestHeaders.put("password", Arrays.asList("admin1!"));
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

		// prepare protocol
		OutgoingProtocolMessage message = new OutgoingProtocolMessage();

		message.setExternalSystemId("externalApp01"); //external application user (stored as text)
		message.setExternalUserId("externalUser01"); //external application user (stored as text)
		message.setDepartment("9998"); //code
		message.setBook(14);

		Transactor transactor = new Transactor();
		transactor.setType(TransactorType.ACTIVE_MEMBER);
		transactor.setCode("001");
		transactor.setDescription("test transactor");
		transactor.setSsn("123123");
		transactor.setVatNumber("1010100101");
		
		Transactors transactors = new Transactors();
		transactors.getTransactor().add(transactor);
		message.setTransactors(transactors);

		// message.setSubjectCode("0100");
		message.setSubject("test subject, only one of subject code or this can be present");
		message.setComments("web service outgoing test");
		message.setAttachedNumber(1);
		message.setAttachedDescription("envelope");
		message.setDocumentType(25); //id, must be related to protocol book and applicable to outgoing protocols

		GregorianCalendar c = new GregorianCalendar(2016, 11, 01); //December 1st, 2016
		XMLGregorianCalendar documentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		message.setDocumentDate(documentDate);
		
		message.setExternalProtocolNumber("123");

		Recipient recipient =new Recipient();
		recipient.setType(CorrespondentType.DEPARTMENT);
		recipient.setCode("0750");
		recipient.setAction(CorrespondentAction.TO);
		recipient.setDistributionMethod("3"); //code
		recipient.setDispatchDate(documentDate); // same as doc date, only for testing purposes
		
		Recipients recipients = new Recipients();
		recipients.getRecipient().add(recipient);
		message.setRecipients(recipients );
		
//		Author author = new Author();
//		author.setCode("100"); // contact code (must be an employee, i.e. belonging to a department)
//		message.setAuthor(author);
		
		// execute query
		ProtocolReceipt protocolReceipt = null;
		try {
			protocolReceipt = port.receiveOutgoingProtocol(message);
		} catch (ProtocolWebServiceFault e) {
			System.out.println(e.getFaultInfo().getCode());
			Reasons reasonsBean = e.getFaultInfo().getReasons();
			List<String> reasons = reasonsBean.getReason();
			for (String reason : reasons) {
				System.out.println(reason);
			}
			if(e.getCause()!=null) {
				System.out.println(e.getCause());
			}
			System.exit(0);
		}

		// print out results
		System.out.println(protocolReceipt.getProtocolNumber() + "/" + protocolReceipt.getDirection() + "/"
				+ protocolReceipt.getProtocolDate());
	}
}
