package gr.scriptum.eprotocol.wserver;
import gr.scriptum.eprotocol.wserver.WsRequest;

/**
 * The request message to inquire about a previously submitted protocol message.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ProtocolQuery  extends WsRequest{
	
	/**
	 * Not currently used.
	 */
	String   clientPass; 
	
	/**
	 * this was sent to you as an answer from previous call to receiveProtocol()
	 */
	Integer  protocolId; 	
	
	
	public ProtocolQuery(){
		super();
	}
	
	public ProtocolQuery(Integer clientId) {
		super(clientId);
	}

	public final String getClientPass() {
		return clientPass;
	}

	public final void setClientPass(String clientPass) {
		this.clientPass = clientPass;
	}

	public final Integer getProtocolId() {
		return protocolId;
	}

	public final void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}

	@Override
	public String toString() {
		return "ProtocolQuery [clientPass=" + clientPass + ", protocolId="
				+ protocolId + "]";
	}	
	
	

}
