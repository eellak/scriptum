package gr.scriptum.eprotocol.wserver;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;

/**
 * Dispatcher Interface for eProtocol Web Service Client. Directs how clients to
 * eProtocol Web Service should operate
 * 
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface EProtocolWebService {

	@WebMethod
	public ProtocolReceipt receiveIncomingProtocol(
			@WebParam(name = "message") @XmlElement(required = true) IncomingProtocolMessage message)
			throws ProtocolWebServiceFault;

	@WebMethod
	public ProtocolReceipt receiveOutgoingProtocol(
			@WebParam(name = "message") @XmlElement(required = true) OutgoingProtocolMessage message)
			throws ProtocolWebServiceFault;

	@WebMethod
	public List<ProtocolInfo> inquireProtocols(
			@WebParam(name = "query") @XmlElement(required = true) ProtocolQuery query) throws Exception;
}
