package gr.scriptum.eprotocol.mailclients;


import javax.mail.MessagingException;


import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;

public interface MailProtocolDispatcher {
	
	/**
	 * To receive email protocols via IMAP / POP3
	 * @return an array of Incoming Protocol Nodes
	 * @throws MessagingException
	 */
    IncomingProtocol [] receiveIncomingProtocols() throws MessagingException;
	
    /**
     * To send via SMTP an Outgoing  Protocol
     * @param outProtocol The outgoing Protocol
     * @return
     */
    SendMailReceipt sendOutgoingProtocol( OutgoingProtocol outProtocol);
    
    /**
     * To send an array of Outgoing Protocols
     * @param outProtocol The Outgoing Protocols
     * @return
     */
    SendMailReceipt [] sendAllOutgoingProtocols( OutgoingProtocol [] outProtocol);

}
