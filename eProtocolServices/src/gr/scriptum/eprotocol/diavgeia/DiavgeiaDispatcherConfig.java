package gr.scriptum.eprotocol.diavgeia;

import gr.scriptum.dao.ParameterDAO;


/**
 * Diavgeia Dispatcher Configuration Object. 
 * Makes PDF document posts to diavgeia.gov.gr
 * The default baseUrl points to diavgeia.gov.gr test server 
 * @author Mike Mountrakis mountrakis@uit.gr
 */
 

public class DiavgeiaDispatcherConfig {

	/**
	 * The default baseUrl points to diavgeia.gov.gr test server
	 */
	static String baseUrl = "https://193.105.109.110/apofaseis-dokimes";
	static int    timeout = 60 * 1000;
	
	//init parameters
	static String trustStoreFile = "C:\\Users\\.keystore";
	static String trustStorePass =  "changeit";	
	
	//login
	static String user;           // edUser
	static String password;       // edPassword

	//postPdf
	static String email;          //field_syntaktis_email
	static String organizationId; //
	static String unitId;         //field_monades_text
	static String signs;          //field_telikos_ypografwn
    static String tempFilePath;   //directory where temporary files are placed


	private DiavgeiaDispatcherConfig(){
		init();
	}
	
	private static synchronized void  init(){
		ParameterDAO parameterDAO = new ParameterDAO();
		
		baseUrl = parameterDAO.getAsString( "DIAVGEIA_BASE_URL");
		timeout = parameterDAO.getAsInteger("DIAVGEIA_TIMEOUT");
		trustStoreFile = parameterDAO.getAsString("DIAVGEIA_TRUSTSTORE_FILE");
		trustStorePass = parameterDAO.getAsString("DIAVGEIA_TRUSTSTORE_PASS");
		user = parameterDAO.getAsString("DIAVGEIA_USER");
		password = parameterDAO.getAsString("DIAVGEIA_PASSWORD");
		email = parameterDAO.getAsString("DIAVGEIA_EMAIL");
		organizationId = parameterDAO.getAsString("DIAVGEIA_ORGANIZATION_ID");
		unitId  = parameterDAO.getAsString("DIAVGEIA_UNIT_ID");
		signs = parameterDAO.getAsString("DIAVGEIA_SIGNS");
		tempFilePath = parameterDAO.getAsString("DIAVGEIA_TMP_FILES");
				
	}	
	
	
	public static DiavgeiaDispatcherConfig getInstance(){
		return new DiavgeiaDispatcherConfig();
	}
	
   	
	
	public String getTempFilePath() {
		return tempFilePath;
	}


	public  String getBaseUrl() {
		return baseUrl;
	}

	public int getTimeout() {
		return timeout;
	}

	public  String getTrustStoreFile() {
		return trustStoreFile;
	}

	public  String getTrustStorePass() {
		return trustStorePass;
	}

	public  String getUser() {
		return user;
	}

	public  String getPassword() {
		return password;
	}

	public  String getEmail() {
		return email;
	}

	public  String getUnitId() {
		return unitId;
	}

	public String getOrganizationId(){
		return organizationId;
	}
	
	
	public String getSigns() {
		return signs;
	}
	
}
