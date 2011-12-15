package gr.scriptum.eprotocol.mailclients;

import gr.uit.mthreads.MTransientThread;


public class MailNotificationDaemon implements MailNotificationDaemonMBean{
	
	public static final int DEFAULT_SLEEP_PERIOD = 6 * 60 * 60000; //six hours
	private static MTransientThread daemonThread = null;
	private static MailDispatcherConfig configuration;
	
	private static int sleepPeriod = DEFAULT_SLEEP_PERIOD;	
	/**
	 * Default constructor.
	 */
	public MailNotificationDaemon() {
		super();
		configuration = new MailDispatcherConfig();
	}

//------
// Getters and Setters
//------
	
    public  final String getSmtpHost() {
		return configuration.getSmtpHost();
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
		else if ( serverType.equalsIgnoreCase("imaps") )
			configuration.setIMAPS();
		else
			;
	}
	
	public void setEnableStarttls(boolean enableStarttls){
		configuration.setEnableStarttls(enableStarttls);
	}
	
	public boolean getEnableStarttls(){
		return configuration.getEnableStarttls();
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
		MailNotificationDaemon.sleepPeriod = sleepPeriod;
	}

	
	public void setDebug(boolean d) {
		configuration.setDebug(d);
		
	}


	public boolean getDebug() {
		return configuration.getDebug();
	}


	public int getTimeout() {
		return configuration.getTimeout();
	}


	public void setTimeout(int timeout) {
		configuration.setTimeout(timeout);
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
					           new SendTaskExpireNotifications( configuration ) );
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


	
	
	public static void main(String argv[] ){
		MailDaemon daemon = new MailDaemon();
		System.out.println( MailNotificationDaemon.configuration.toString() );
		try {
			daemon.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Worked");
	}

}
