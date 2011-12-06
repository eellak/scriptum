package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolNode;

/**
 * Creates a protocol node in Document Management System
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class RequestProtocolNode extends Request {

	/**
	 * the new protocol node to be created.
	 */
	private ProtocolNode protocolNode;  
	/**
	 * The protocolBook under the node will be registered.
	 */
	private ProtocolBook protocolBook;

	
	
	public RequestProtocolNode(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.CREATENODE;
	}

	public RequestProtocolNode(){
		super();
		this.action = DISPATCHER_ACTION.CREATENODE;
	}

	public RequestProtocolNode(ProtocolNode newProtocolNode) {
		this();
		this.protocolNode = newProtocolNode;
	}
	
	public RequestProtocolNode(ProtocolNode newProtocolNode, 
			                   ProtocolBook protocolBook) {
		this();
		this.protocolNode = newProtocolNode;
		this.protocolBook = protocolBook;
	}
	
	public RequestProtocolNode(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.CREATENODE);
		// TODO Auto-generated constructor stub
	}


	//-----------
	//  getters & setters
	//-----------
	
	public ProtocolNode getProtocolNode() {
		return protocolNode;
	}

	public void setProtocolNode(ProtocolNode newProtocolNode) {
		this.protocolNode = newProtocolNode;
	}

	public ProtocolBook getProtocolBook() {
		return protocolBook;
	}

	public void setProtocolBook(ProtocolBook protocolBook) {
		this.protocolBook = protocolBook;
	}
	
	

}
