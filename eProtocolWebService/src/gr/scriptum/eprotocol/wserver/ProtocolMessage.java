package gr.scriptum.eprotocol.wserver;

import java.util.ArrayList;

/**
 * The input message to Web Service method receiveProtocol().
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */

public class ProtocolMessage extends WsRequest{

	/**
	 * Where this protocol was composed
	 */
	String   place;
	/**
	 * The protocol subject
	 */
	String   subject;
	/**
	 * Comments regarding this node 
	 */
	String   comments;
	/**
	 * The file attachments
	 */	
	ArrayList<WsDocument>  attachements;
	
	String   yourMessageId;   //your outgoing protocol number or anything you want in order to identify your message

	public ProtocolMessage() {
		super();
	}
	
	
	public ProtocolMessage(Integer clientId) {
		super(clientId);
	}
	

	public final String getPlace() {
		return place;
	}

	public final void setPlace(String place) {
		this.place = place;
	}

	public final String getSubject() {
		return subject;
	}

	public final void setSubject(String subject) {
		this.subject = subject;
	}

	public final String getComments() {
		return comments;
	}

	public final void setComments(String comments) {
		this.comments = comments;
	}




	public final ArrayList<WsDocument> getAttachements() {
		return attachements;
	}


	public final void setAttachements(ArrayList<WsDocument> attachements) {
		this.attachements = attachements;
	}


	public final String getYourMessageId() {
		return yourMessageId;
	}

	public final void setYourMessageId(String yourMessageId) {
		this.yourMessageId = yourMessageId;
	}


	@Override
	public String toString() {
		return "ProtocolMessage [place=" + place + ", subject=" + subject
				+ ", comments=" + comments + ", attachements=" + attachements
				+ ", yourMessageId=" + yourMessageId + "]";
	}

	
}

