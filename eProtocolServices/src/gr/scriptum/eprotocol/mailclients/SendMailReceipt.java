package gr.scriptum.eprotocol.mailclients;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.OutgoingRecipient;
import gr.scriptum.domain.OutgoingRecipientId;
import gr.scriptum.domain.OutgoingRecipient.RecipientType;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * The receipt of sending a message.
 * @author mike
 *
 */

public class SendMailReceipt {
	
	private Integer outProtocolId = -1;
	private String  subject = "";
	private Integer outProtocolNumber = -1;
	
	private String [] sentTo;
	private String [] sentCc;
	
	
	private Date   sentTimestamp;
	private String eCode;
	private String eMessage;
	
	
	//--------------------
	// Constructors
	//--------------------	
	
	public SendMailReceipt(){
		sentTimestamp = new Date();
	}
	

	public SendMailReceipt( OutgoingProtocol outProtocol){
		super();
		outProtocolId = outProtocol.getId();
		subject = outProtocol.getSubject();
		outProtocolNumber = outProtocol.getProtocolNumber();
		
		
		Set<OutgoingRecipient> recipients = outProtocol.getOutgoingRecipients();
        ArrayList<String> addressesTo = new ArrayList<String>();
        ArrayList<String> addressesCc = new ArrayList<String>();
        for( OutgoingRecipient recipient : recipients){
      	  OutgoingRecipientId  rcpId = recipient.getId();
      	  Contact  contact           =  rcpId.getContact(); 
      	  if(  recipient.getRecipientType() == RecipientType.CC )
      		  addressesCc.add(contact.getEmail() ); 
      	  else
      		  addressesTo.add( contact.getEmail()); 
        }
        sentTo = new String[addressesTo.size()];
        sentTo = addressesTo.toArray(sentTo);
        sentCc = new String[addressesCc.size()];
        sentCc = addressesTo.toArray(sentCc);
	}
	
	public void appendError( Throwable t ){
		eMessage = t.getMessage();
		eCode    = t.getClass().getName();
		sentTimestamp = new Date();
	}
	
	public boolean isError(){
		return ( eCode == null ?   false : true);
	}
	
	//--------------------
	// Getters & setters
	//--------------------

	public Integer getOutProtocolId() {
		return outProtocolId;
	}

	public void setOutProtocolId(Integer outProtocolId) {
		this.outProtocolId = outProtocolId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getOutProtocolNumber() {
		return outProtocolNumber;
	}

	public void setOutProtocolNumber(Integer outProtocolNumber) {
		this.outProtocolNumber = outProtocolNumber;
	}

	public String[] getSentTo() {
		return sentTo;
	}

	public void setSentTo(String[] sentTo) {
		this.sentTo = sentTo;
	}

	public String[] getSentCc() {
		return sentCc;
	}

	public void setSentCc(String[] sentCc) {
		this.sentCc = sentCc;
	}

	public Date getSentTimestamp() {
		return sentTimestamp;
	}

	public void setSentTimestamp(Date sentTimestamp) {
		this.sentTimestamp = sentTimestamp;
	}

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String geteMessage() {
		return eMessage;
	}

	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}
	
	
	
	
}
