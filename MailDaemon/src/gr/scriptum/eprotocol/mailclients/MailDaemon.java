package gr.scriptum.eprotocol.mailclients;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import gr.scriptum.eprotocol.mailclients.MailDaemonMBean;
import gr.scriptum.eprotocol.mailclients.MailDispatcherConfig;
import gr.uit.mthreads.MTransientThread;


public class MailDaemon  implements MailDaemonMBean{
	
	public static final int DEFAULT_SLEEP_PERIOD = 60000;
	private static MTransientThread daemonThread = null;
	private static MailDispatcherConfig configuration;
	private static String openKmUser, openKmPassword;
	
	
	public static final String SYSTEM = "MailDaemon";
	public static final int    MAIL_UID = 666; //the Daemon of course....
	private static int sleepPeriod = DEFAULT_SLEEP_PERIOD;	
	/**
	 * Default constructor.
	 */
	public MailDaemon() {
		super();
		configuration = new MailDispatcherConfig();
	}

//------
// Getters and Setters
//------
	
    public  final String getSmtpHost() {
		return configuration.getSmtpHost();
	}
                 
	public final String getOpenKmUser() {
		return openKmUser;
	}

	public final void setOpenKmUser(String openKmUser) {
		MailDaemon.openKmUser = openKmUser;
	}

	public final String getOpenKmPassword() {
		return openKmPassword;
	}

	public final void setOpenKmPassword(String openKmPassword) {
		MailDaemon.openKmPassword = openKmPassword;
	}

	public final void setSmtpHost(String smtpHost) {
		configuration.setSmtpHost( smtpHost );
	}

	public final int getSmtpPort() {
		return configuration.getSmtpPort();
	}

	public final void setSmtpPort(int smtpPort) {
		configuration.setSmtpPort(  smtpPort );
	}

	public final String getSmtpUser() {
		return configuration.getSmtpUser();
	}

	public final void setSmtpUser(String smtpUser) {
		configuration.setSmtpUser(smtpUser);
	}

	public final String getSmtpPassword() {
		return configuration.getSmtpPassword();
	}

	public final void setSmtpPassword(String smtpPassword) {
		configuration.setSmtpPassword( smtpPassword );
	}

	public final int getRcvPort() {
		return configuration.getRcvPort();
	}

	public final void setRcvPort(int rcvPort) {
		configuration.setRcvPort( rcvPort );
	}

	public final String getServerType() {
		return configuration.getServerType();
	}

	public final void setServerType(String serverType) {
		if( serverType.equalsIgnoreCase("pop3") )
			configuration.setPOP3();
		else if ( serverType.equalsIgnoreCase("imap") )
			configuration.setIMAP();
		else
			;
	}

	public final String getFolder() {
		return configuration.getFolder();
	}

	public final void setFolder(String folder) {
		configuration.setFolder(folder);
	}

	public final int getMaxEmails() {
		return configuration.getMaxEmails();
	}

	public final void setMaxEmails(int maxEmails) {
		configuration.setMaxEmails(maxEmails);
	}

	public final boolean isDeleteOriginals() {
		return configuration.isDeleteOriginals();
	}

	public final void setDeleteOriginals(boolean deleteOriginals) {
		configuration.setDeleteOriginals(deleteOriginals);
	}

    public final MailDispatcherConfig getConfiguration(){
    	return configuration;
    }
	
    public final void setConfiguration( MailDispatcherConfig config ){
    	configuration = config;
    }
    
	public int getSleepPeriod() {
		return sleepPeriod;
	}

	public void setSleepPeriod(int sleepPeriod) {
		MailDaemon.sleepPeriod = sleepPeriod;
	}

//------------
// MBean Methods that are implemented
//------------    
	
	/**
	 * If it is not already running, it starts a new thread of this class and 
	 * returns. Fire and forgets...
	 */
	public void start() throws Exception {
		if( daemonThread == null ){
			daemonThread = new MTransientThread( 
					           new FetchIncomingMailsWork( configuration, 
					        		                       openKmUser, 
					        		                       openKmPassword ) );
			daemonThread.setSleepPeriod(sleepPeriod);
			daemonThread.Start();
		}
	}

	public void stop() {
		if(daemonThread != null){
			daemonThread.Stop();
			daemonThread = null;
		}
	}

	/**
	 * Returns the class status
	 * @return a message with the class status
	 */
	public String isRunning() {	
		if( daemonThread == null)
			return "Thread is null.";
		
		if( daemonThread.IsRunning() )
			return "Runs since " + daemonThread.getDateStarted();
		else
			return "Stoped on " + daemonThread.getDateStoped();
	}

	
	public String getName() {
		return  MailDaemon.class.getName() + " Active Thread " + ( daemonThread!=null ?daemonThread.getId(): "0");
	}

	public  ObjectName getObjectName() throws MalformedObjectNameException, NullPointerException{
		String absoluteName = MailDaemon.class.getName();
		int lastDotIdx = absoluteName.lastIndexOf('.');
		String packageName   = absoluteName.substring(0, lastDotIdx-1);
		String className     = absoluteName.substring(lastDotIdx+1);
		//"com.example:type=Hello"
	    return new ObjectName( packageName + ":type=" +className);
	}	
	
	
	
}	