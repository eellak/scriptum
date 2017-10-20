package gr.scriptum.eprotocol.mailclients;


import javax.mail.MessagingException;


import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProjectTask;
/**
 * Interface that must be followed by all Implementation classes need to dispatch mail requests for SCRIPTUM API
 * @author mike
 *
 */
public interface MailProtocolDispatcher {
	
	/**
	 * To receive email protocols via IMAP / POP3
	 * @return an array of Incoming Protocol Nodes
	 * @throws MessagingException
	 */
    Protocol [] receiveProtocols() throws MessagingException;
	
    /**
     * To send via SMTP an Outgoing  Protocol
     * @param outProtocol The outgoing Protocol
     * @return
     */
    SendMailReceipt sendOutgoingProtocol( Protocol outProtocol);
    
    /**
     * To send an array of Outgoing Protocols
     * @param outProtocol The Outgoing Protocols
     * @return
     */
    SendMailReceipt [] sendAllOutgoingProtocols( Protocol [] outProtocol);
    
    /**
     * Send a task 
     * @param outTask the task
     * @return
     */
    SendMailReceipt sendOutgoingTask(ProjectTask outTask);

}
