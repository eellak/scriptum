package gr.scriptum.webserviceclient.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import gr.scriptum.webservice.client.Direction;
import gr.scriptum.webservice.client.EProtocolWebService;
import gr.scriptum.webservice.client.Exception_Exception;
import gr.scriptum.webservice.client.ProtocolInfo;
import gr.scriptum.webservice.client.ProtocolQuery;
import gr.scriptum.webservice.client.ProtocolWebServiceService;

/**
 * Test client
 *
 */
public class InquireProtocolsTest {

	public static void main(String[] args) {

		// get web service
		ProtocolWebServiceService service = new ProtocolWebServiceService();
		EProtocolWebService port = service.getProtocolWebServicePort();

		// set authentication
		Map<String, List<String>> requestHeaders = new HashMap<String, List<String>>();
		requestHeaders.put("username", Arrays.asList("admin"));
		requestHeaders.put("password", Arrays.asList("admin1!"));
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

		// prepare query
		ProtocolQuery query = new ProtocolQuery();
		query.setBook(14);
		query.setProtocolNumber(1180);
		query.setProtocolYear(2017);
		// query.setDirection(Direction.INCOMING); //optional
//		query.setDepartment("9998");

		// execute query
		List<ProtocolInfo> protocols = null;
		try {
			protocols = port.inquireProtocols(query);
		} catch (Exception_Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// print out results
		for (ProtocolInfo protocolInfo : protocols) {
			System.out.println(protocolInfo.getProtocolNumber() + "/" + protocolInfo.getDirection() + "/"
					+ protocolInfo.getProtocolDate());
		}
	}
}
