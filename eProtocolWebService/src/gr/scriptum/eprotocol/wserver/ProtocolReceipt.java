package gr.scriptum.eprotocol.wserver;

import java.util.Date;

import gr.scriptum.domain.ProtocolNode.Direction;

/**
 * The Receipt returned from receiveProtocol() method.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ProtocolReceipt extends WsResponse{
	
	/**
	 * Company.getId() the company id that allows the client to use the WS 
	 */
	private Integer  clientId;       
	/**
	 * Company.getName() 
	 */
	private String   clientName;     //Company.getName()
	/**
	 * The protocol Node subject as client has sent it.
	 */
	private String   subject;       
	/**
	 * Anything the client wishes to pass as referencial data. For example to sxetiko.
	 */
	private String   yourMessageId;  // caller specifies that 	
	/**
	 * The ID inside eProtocol database that is returned from web service method. This will be used 
	 * from clients as the basic argument in inquireProtocol() method. It is the IncomingProtocol.getId()
	 * as we set. 
	 */
	private Integer  protocolId;    
	
	private Integer protocolNumber;
	
	private Date protocolDate;
	
	private Direction direction;
	
	/**
	 * The system timestamp. When receiveProtocol() finishes processing the request.
	 */
	private Date     receivedDate;   
	
	
	public ProtocolReceipt() {
		super();
	}
	
	public ProtocolReceipt(String error){
		super(error);
	}
	
	//--------------------------------
	public final Integer getClientId() {
		return clientId;
	}
	public final void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public final String getClientName() {
		return clientName;
	}
	public final void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public final String getSubject() {
		return subject;
	}
	public final void setSubject(String subject) {
		this.subject = subject;
	}
	public final String getYourMessageId() {
		return yourMessageId;
	}
	public final void setYourMessageId(String yourMessageId) {
		this.yourMessageId = yourMessageId;
	}
	public final Integer getProtocolId() {
		return protocolId;
	}
	public final void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}
	public final Date getReceivedDate() {
		return receivedDate;
	}
	public final void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	public Integer getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Date getProtocolDate() {
		return protocolDate;
	}

	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "ProtocolReceipt [clientId=" + clientId + ", clientName="
				+ clientName + ", subject=" + subject + ", yourMessageId="
				+ yourMessageId + ", protocolId=" + protocolId
				+ ", receivedDate=" + receivedDate + "]";
	}
	
}