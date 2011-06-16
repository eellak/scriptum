package gr.scriptum.eprotocol.wsclient;


import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceProxy;
import gr.scriptum.eprotocol.wserver.*;

/**
 * eProtocol Web Service Client Implementation class
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ProtocolWebServiceClient implements EProtocolWebServiceClient{
	private static Log logger = LogFactory.getLog(ProtocolWebServiceClient.class.getSimpleName());
	ProtocolWebServiceClientConfig 	configuration;
	  
	
	public ProtocolWebServiceClient(String wsendpoint){
		configuration = new ProtocolWebServiceClientConfig();
		configuration.setPortAddress(wsendpoint);
	}
	  
	
	/**
	 * Proxy Singletton factory to create WEB Service clients 
	 * @return
	 * @throws Exception
	 */
	  private ProtocolWebServiceProxy getInstance() throws Exception {
		  ProtocolWebServiceProxy eProtocolProxy = new ProtocolWebServiceProxy();
			if (configuration != null) {
				eProtocolProxy.setEndpoint(configuration.getPortAddress());
				logger.warn("Binding with "
						+ configuration.getPortAddress());
			}
			return eProtocolProxy;	  
	  }
	  
	  /**
	   * WSDL Client method implementation 
	   */
	  public ProtocolReceipt sendProtocol( ProtocolMessage message ) throws Exception{
		  ProtocolWebServiceProxy  proxy = getInstance();
		  
		  gr.scriptum.eprotocol.ws.endpoint.ProtocolMessage epMessage = 
			        new gr.scriptum.eprotocol.ws.endpoint.ProtocolMessage();
		  epMessage.setClientId(message.getClientId());
		  epMessage.setComments(message.getComments());
		  epMessage.setPlace(message.getPlace());
		  epMessage.setSubject(message.getSubject());
		  epMessage.setYourMessageId(message.getYourMessageId());
		  
		  //get the message 's attachments.
		  ArrayList<WsDocument>  mesgDocuments = message.getAttachements();	
		  
		  gr.scriptum.eprotocol.ws.endpoint.WsDocument epDocuments [] = 
			  new gr.scriptum.eprotocol.ws.endpoint.WsDocument[mesgDocuments.size()];
		  int i=0;
		  for( WsDocument doc : message.getAttachements() ){
			  gr.scriptum.eprotocol.ws.endpoint.WsDocument epDoc = 
				  new gr.scriptum.eprotocol.ws.endpoint.WsDocument();
			      epDoc.setContent(doc.getContent());
			      epDoc.setByteFileSize(doc.getByteFileSize());
			      epDoc.setFilename(doc.getFilename());
			      epDoc.setIndex(doc.getIndex());
			      epDoc.setTitle(doc.getTitle());
			      epDocuments[i++] = epDoc;
		  }
		  epMessage.setAttachements(epDocuments);
		  
		  //-->
		  gr.scriptum.eprotocol.ws.endpoint.ProtocolReceipt epReceipt = proxy.receiveProtocol(epMessage);
		  
		  //<--
		  ProtocolReceipt receipt = new ProtocolReceipt();
		  receipt.setClientId(epReceipt.getClientId());
		  receipt.setClientName(epReceipt.getClientName());
		  receipt.setError(epReceipt.getError());
		  receipt.setProtocolId(epReceipt.getProtocolId());
		  Calendar c = epReceipt.getReceivedDate();
		  c.getTime();
		  receipt.setReceivedDate(c.getTime());
		  receipt.setSubject(epReceipt.getSubject());
		  receipt.setYourMessageId(epReceipt.getYourMessageId());
		  
		  return receipt;
		  
	  }
	  
		  /**
		   * Inquires about a protocol. WSDL method implementation
		   */
	  public ProtocolInfo inquireProtocol(ProtocolQuery query) throws Exception{
		  ProtocolWebServiceProxy  proxy = getInstance();
		  
		  gr.scriptum.eprotocol.ws.endpoint.ProtocolQuery epQuery = 
			  new gr.scriptum.eprotocol.ws.endpoint.ProtocolQuery();
		  
		  
		  gr.scriptum.eprotocol.ws.endpoint.ProtocolInfo epInfo = proxy.inquireProtocol(epQuery);
		  
		  
		  ProtocolInfo info = new ProtocolInfo();
		  info.setClientId(epInfo.getClientId());
		  info.setClientName(epInfo.getClientName());
		  info.setComments(epInfo.getComments());
		  info.setError(epInfo.getError());
		  info.setProtocolDate(epInfo.getProtocolDate().getTime());
		  info.setProtocolId(epInfo.getProtocolId());
		  info.setProtocolNumber(epInfo.getProtocolNumber());
		  info.setProtocolSeries(epInfo.getProtocolSeries());
		  info.setProtocolYear(epInfo.getProtocolYear());
		  info.setSubject(epInfo.getSubject());
		  info.setYourMessageId(epInfo.getYourMessageId());
		  return info;  
	  }
	  
}
