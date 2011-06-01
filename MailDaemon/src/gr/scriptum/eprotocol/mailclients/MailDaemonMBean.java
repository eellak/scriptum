/**
 * 
 */
package gr.scriptum.eprotocol.mailclients;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;



/**
 * @author mike
 *
 */
public interface MailDaemonMBean{ 
	
    public void start() throws Exception;
    public void stop() throws Exception;	
    
    public String getName(); 
    public  ObjectName getObjectName() throws MalformedObjectNameException, NullPointerException;
    
	public  String getOpenKmUser();
	public  void setOpenKmUser(String openKmUser);
	public  String getOpenKmPassword();
	public  void setOpenKmPassword(String openKmPassword);   
    
	public String getSmtpHost();
	public void setSmtpHost(String smtpHost);
	public int getSmtpPort();
	public void setSmtpPort(int smtpPort);
	public String getSmtpUser();
	public void setSmtpUser(String smtpUser);
	public String getSmtpPassword();
	public void setSmtpPassword(String smtpPassword);
	public int getRcvPort();
	public void setRcvPort(int rcvPort);
	public String getServerType();
	public void setServerType(String serverType);
	public String getFolder();
	public void setFolder(String folder);
	public int getMaxEmails();
	public void setMaxEmails(int maxEmails);
	public boolean isDeleteOriginals();
	public void setDeleteOriginals(boolean deleteOriginals);
	public String isRunning();
	public  int getSleepPeriod() ;
	public void setSleepPeriod(int sleepPeriod);
	public void setEnableStarttls(boolean enableStarttls);
}
