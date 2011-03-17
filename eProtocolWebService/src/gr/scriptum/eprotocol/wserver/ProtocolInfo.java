package gr.scriptum.eprotocol.wserver;

import java.util.Date;

/**
 * The information requested from a ProtocolQuery message.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ProtocolInfo extends WsResponse{
	/**
	 * Company.getId() the company id we have sent you in order to use the protocol
	 */
	Integer  clientId;   
	/**
	 * the  incomingProtocol.getId(). The protocol the client requests information about. 
	 * This was returned to the client as a field of ProtocolReceipt by a 
	 * prior call to  receiveProtocol().  
	 */
	Integer  protocolId;     // the  incomingProtocol.getId() we set 
	                          
	                         //Service must supply the following: 
	/**
	 * The following are the returned data members:
	 * The client name that corresponds to clientId
	 */
	String   clientName;     
	/**
	 * The protocol subject if the protocolId was found.
	 */
	String   subject;        
	/**
	 * Anything the user specified upon protocol creation.
	 */
	String   yourMessageId; 

	                     
	/**
	 * Α�?ιθμός Π�?ωτοκόλλου if the protocol was subscribed.
	 */
	Integer  protocolNumber; 
	/**
	 * Seira Protokolou
	 */
	Integer  protocolSeries;
	/**
	 * Hmerominia Protokolisis
	 */
	Date     protocolDate;  
	/**
	 * Etos protokolisis
	 */
	Integer  protocolYear;   
	/**
	 * se periptwsi poy DEN prwtokollithike, ayto einai to sxolio pou apostelletai  (ProtocolNode)
	 */
	String   comments;        


	public ProtocolInfo(){
		super();
	}


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


	public final Integer getProtocolNumber() {
		return protocolNumber;
	}


	public final void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}


	public final Integer getProtocolSeries() {
		return protocolSeries;
	}


	public final void setProtocolSeries(Integer protocolSeries) {
		this.protocolSeries = protocolSeries;
	}


	public final Date getProtocolDate() {
		return protocolDate;
	}


	public final void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}


	public final Integer getProtocolYear() {
		return protocolYear;
	}


	public final void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}


	public final String getComments() {
		return comments;
	}


	public final void setComments(String comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return "ProtocolInfo [clientId=" + clientId + ", protocolId="
				+ protocolId + ", clientName=" + clientName + ", subject="
				+ subject + ", yourMessageId=" + yourMessageId
				+ ", protocolNumber=" + protocolNumber + ", protocolSeries="
				+ protocolSeries + ", protocolDate=" + protocolDate
				+ ", protocolYear=" + protocolYear + ", comments=" + comments
				+ "]";
	}

	
	
}
