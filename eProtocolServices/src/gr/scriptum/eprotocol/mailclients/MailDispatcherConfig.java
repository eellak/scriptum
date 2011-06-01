package gr.scriptum.eprotocol.mailclients;


import java.util.Properties;

/**
 * Class that server Mail configuration, like mailer type, authentication
 * It extends  Authenticator so that it can be used in case the mail account is 
 * configured inside JBoss. For example using a jndi lookup session for  
 * eprotocol/mailer configuration
 * @author mike
 *
 */

public class MailDispatcherConfig{
	
	private static boolean debug = true;
	
	public static final int DEFAULT_SMTP_PORT = 25;
	public static final int DEFAULT_IMAP_PORT = 143;
	public static final int DEFAULT_POP3_PORT = 110 ;
	public static final int DEFAULT_IMAPS_PORT = 993;
	// To send mails we need the SMTP details
	private String  smtpHost;
	private int     smtpPort     = DEFAULT_SMTP_PORT;
	private String  smtpUser     = "";
	private String  smtpPassword = "";
    
	//To receive mails
	public int rcvPort = DEFAULT_POP3_PORT;
	public static final String POP3 = "pop3";
	public static final String IMAP = "imap";
	public static final String IMAPS = "imaps";
	
	private String serverType = POP3;	
	
	
	private String   folder    = "INBOX";
	private int      maxEmails = 20;
	private boolean  deleteOriginals = false;
	private boolean  enableStarttls = false;
	
	
	public MailDispatcherConfig(){	
	}
	
	public void setIMAP(){
		serverType = IMAP;
		rcvPort = DEFAULT_IMAP_PORT;
	}
	public void setPOP3(){
		serverType = POP3;
		rcvPort = DEFAULT_POP3_PORT;
	}
	
	public void setIMAPS(){
		serverType = IMAPS;
		rcvPort = DEFAULT_IMAPS_PORT;		
	}
	
	public String getServerType(){
		return serverType;
	}
	public boolean isImap(){
		return serverType.equals(IMAP);
	}
	public boolean isPop3(){
		return serverType.equals(POP3);
	}
	public boolean isImaps(){
		return serverType.equals(IMAPS);
	}	
	
	public boolean getEnableStarttls(){
		return enableStarttls;
	}
	public void setEnableStarttls(boolean enableStarttls){
		this.enableStarttls = enableStarttls;
	}
		
	//For the outgoing Protocol we also need some constants that will be used
	//in order to have a standardized message exchange
	private String messageFrom    = "protocol@uit.gr";
	private String messageBodyTxt = "A new Protocol";
	private String messageSubject = "Hi, this is a text from UIT's protocol system.";
	
	private Properties properties;	
	
	/**
	 * Explicitly call this AFTER you set all parameters...
	 */
	public void init(){
	    // Create properties, get Session
		properties = new Properties();

		if( isPop3() ){
			properties.setProperty("mail.pop3.host", smtpHost);
			properties.setProperty("mail.pop3.port", "" + rcvPort);
			properties.setProperty("mail.pop3.connectiontimeout", "5000");
			properties.setProperty("mail.pop3.timeout", "5000");
		}else if(isImap()){
			properties.setProperty("mail.imap.host", smtpHost);
			properties.setProperty("mail.imap.port", "" + rcvPort);
			properties.setProperty("mail.imap.connectiontimeout", "5000");
			properties.setProperty("mail.imap.timeout", "5000");
		}else{ //imaps
			properties.setProperty("mail.store.protocol", "imaps");
		}
		
	    // If using static Transport.send(),
	    // need to specify which host to send it to
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.host", smtpHost);
		properties.setProperty("mail.smtp.port", "" + smtpPort);
		
		//properties.put("mail.smtp.auth", "true");
		//properties.setProperty("mail.user", smtpUser);
		//properties.setProperty("mail.password", smtpPassword);	
				
	    // To see what is going on behind the scene
		if(debug)
			properties.put("mail.debug", "true");	
	}	
	
	
	
	public final String getFolder() {
		return folder;
	}

	public final void setFolder(String folder) {
		this.folder = folder;
	}

	public final int getMaxEmails() {
		return maxEmails;
	}

	public final void setMaxEmails(int maxEmails) {
		this.maxEmails = maxEmails;
	}

	public final boolean isDeleteOriginals() {
		return deleteOriginals;
	}

	public final void setDeleteOriginals(boolean deleteOriginals) {
		this.deleteOriginals = deleteOriginals;
	}

	public final int getSmtpPort() {
		return smtpPort;
	}

	public final void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public final int getRcvPort() {
		return rcvPort;
	}

	public final void setRcvPort(int rcvPort) {
		this.rcvPort = rcvPort;
	}

	public final String getMessageBodyTxt() {
		return messageBodyTxt;
	}

	public final void setDebug(boolean debug ){
		debug = true;
	}
	public final boolean getDebug(){
		return debug;
	}	
	public  final String getSmtpHost() {
		return smtpHost;
	}
	public final void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public final String getSmtpUser() {
		return smtpUser;
	}
	public final void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}
	public final String getSmtpPassword() {
		return smtpPassword;
	}
	public final void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public final String getMessageFrom() {
		return messageFrom;
	}
	public final void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	public final String getMessageBodyTxt(Integer protocolNumber) {
		return messageBodyTxt + " # " +  protocolNumber.intValue();
	}
	public final void setMessageBodyTxt(String messageBodyTxt) {
		this.messageBodyTxt = messageBodyTxt;
	}
	public final String getMessageSubject() {
		return messageSubject;
	}	
	public final void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	public final Properties getProperties() {
		return properties;
	}
	public final void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "MailDispatcherConfig [smtpHost=" + smtpHost + ", smtpPort="
				+ smtpPort + ", smtpUser=" + smtpUser + ", smtpPassword="
				+ smtpPassword + ", rcvPort=" + rcvPort + ", serverType="
				+ serverType + ", folder=" + folder + ", maxEmails="
				+ maxEmails + ", deleteOriginals=" + deleteOriginals
				+ ", messageFrom=" + messageFrom + ", messageBodyTxt="
				+ messageBodyTxt + ", messageSubject=" + messageSubject
				+ ", properties=" + properties + "]";
	}
	
	
	
}
