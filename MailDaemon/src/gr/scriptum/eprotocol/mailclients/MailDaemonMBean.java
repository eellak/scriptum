/**
 * 
 */
package gr.scriptum.eprotocol.mailclients;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;



/**
 * The MBEan Interface.
 * Always stop and restart the service when you change parameters 
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public interface MailDaemonMBean{ 
	/**
	 * Starts the service by creating a new thread
	 * @throws Exception
	 */
    public void start() throws Exception;
    /**
     * Stops the service interrupting the running thread
     * @throws Exception
     */
    public void stop() throws Exception;	
    /**
     * Returns the JMX - friendly object Name
     * @return
     */
    public String getName(); 
    public  ObjectName getObjectName() throws MalformedObjectNameException, NullPointerException;
    /**
     * Gets the current OpenKM user
     * @return
     */
	public  String getOpenKmUser();
	/**
	 * Set the current OpenKM User
	 * @param openKmUser
	 */
	public  void setOpenKmUser(String openKmUser);
	/**
	 * Returns the currently used OpenKM Password
	 * @return
	 */
	public  String getOpenKmPassword();
	/**
	 * Sets the OpenKM Password
	 * @param openKmPassword
	 */
	public  void setOpenKmPassword(String openKmPassword);   
    /**
     * gets the Used SMTP host
     * @return
     */
	public String getSmtpHost();
	/**
	 * Sets the SMTP host
	 * @param smtpHost
	 */
	public void setSmtpHost(String smtpHost);
	/**
	 * Gets the SMTP port
	 * @return
	 */
	public int getSmtpPort();
	/**
	 * Sets the SMTP port
	 * @param smtpPort
	 */
	public void setSmtpPort(int smtpPort);
	/**
	 * Gets the SMTP user 
	 * @return
	 */
	public String getSmtpUser();
	/**
	 * Sets the SMTP user
	 * @param smtpUser
	 */
	public void setSmtpUser(String smtpUser);
	/**
	 * gets the SMTP user 's password
	 * @return
	 */
	public String getSmtpPassword();
	/**
	 * Sets the SMTP user's password
	 * @param smtpPassword
	 */
	public void setSmtpPassword(String smtpPassword);
	/**
	 * Gets the Receive Mail Port
	 * @return
	 */
	public int getRcvPort();
	/**
	 * Set the Receive Mail Port
	 * @param rcvPort
	 */
	public void setRcvPort(int rcvPort);
	/**
	 * Gets the Mail Server type
	 * @return
	 */
	public String getServerType();
	/**
	 * Sets the server type. Valid values pop3, imap, imps
	 * @param serverType
	 */
	public void setServerType(String serverType);
	/**
	 * Sets the incoming mail folder.Default is INBOX
	 * @return
	 */
	public String getFolder();
	/**
	 * Sets the incoming mail folder
	 * @param folder
	 */
	public void setFolder(String folder);
	/**
	 * Gets the Maximum number of incoming mails to retrieve every time it runs. 
	 * Default value is 20
	 * @return
	 */
	public int getMaxEmails();
	/**
	 * Gets the Maximum number of incoming mails to retrieve every time it runs. 
	 * Default value is 20
	 * @param maxEmails
	 */
	public void setMaxEmails(int maxEmails);
	/**
	 * Do i delete the original mails when fetch them from mail server?
	 * @return
	 */
	public boolean isDeleteOriginals();
	/**
	 * Delete original incoming mails from Incoming Folder when fetched them. Does not supported for POP3
	 * @param deleteOriginals
	 */
	public void setDeleteOriginals(boolean deleteOriginals);
	/**
	 * Checks if the service thread is running or not
	 * @return
	 */
	public String isRunning();
	/**
	 * Daemon thread sleep period. Defines how much time in msec debore daemon check for new incomings again.
	 * @return
	 */
	public  int getSleepPeriod() ;
	/**
	 * Sets the sleep period time in msces
	 * @param sleepPeriod
	 */
	public void setSleepPeriod(int sleepPeriod);
	/**
	 * This is used for GMAIL / IMAPS
	 * @param enableStarttls
	 */
	public void setEnableStarttls(boolean enableStarttls);
	public boolean getEnableStarttls();
}
