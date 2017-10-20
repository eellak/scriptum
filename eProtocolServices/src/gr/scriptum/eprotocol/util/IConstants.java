package gr.scriptum.eprotocol.util;

public interface IConstants {

	public static final String PARAM_KEY_ID = "id";

	public static final int DEFAULT_COMPANY_ID = 1;

	public static final String KEY_USER = "user";

	public static final String KEY_OKM_TOKEN = "token";

	public static final String SYSTEM_NAME = "eProtocol";

	public static final String OKM_FOLDER_DELIMITER = "/";

	public static final String OKM_PROTOCOL_NUMBER_DELIMITER = "-";

	public static final String PARAM_CALLBACK = "callback";

	public static final String SORTING_DELIMITER = ",";

	public static final String PARAM_DEFAULT_INTERNAL_RECIPIENT_DISTRIBUTION_METHOD_CODE = "DEFAULT_INTERNAL_RECIPIENT_DISTRIBUTION_METHOD_CODE";
	
	public static final String PARAM_DEFAULT_RECIPIENT_DISTRIBUTION_METHOD_CODE = "DEFAULT_RECIPIENT_DISTRIBUTION_METHOD_CODE";
	
	public static final String PARAM_DEFAULT_INCOMING_DISTRIBUTION_METHOD_CODE = "DEFAULT_INCOMING_DISTRIBUTION_METHOD_CODE";

	public static final String PARAM_MAIL_SERVER_TYPE = "MAIL_SERVER_TYPE";
	
	public static final String PARAM_SMTP_HOST = "SMTP_HOST";

	public static final String PARAM_SMTP_PORT = "SMTP_PORT";

	public static final String PARAM_SMTP_USER = "SMTP_USER";

	public static final String PARAM_SMTP_PASSWORD = "SMTP_PASSWORD";

	public static final String PARAM_EMAIL_FROM = "EMAIL_FROM";

	public static final String OKM_ROOT_FOLDER = "/okm:root";
	
	@Deprecated
	public static final String PARAM_OKM_NODE_PENDING_INCOMING = "OKM_NODE_PENDING_INCOMING";
	
	@Deprecated
	public static final String PARAM_OKM_NODE_INCOMING = "OKM_NODE_INCOMING";

	@Deprecated
	public static final String PARAM_OKM_NODE_PENDING_OUTGOING = "OKM_NODE_PENDING_OUTGOING";
	
	@Deprecated
	public static final String PARAM_OKM_NODE_OUTGOING = "OKM_NODE_OUTGOING";
	
	public static final String PARAM_OKM_AUTH_PORT_ADDRESS="OKM_AUTH_PORT_ADDRESS";
	
	public static final String PARAM_OKM_DOCUMENT_PORT_ADDRESS="OKM_DOCUMENT_PORT_ADDRESS";
	
	public static final String PARAM_OKM_FOLDER_PORT_ADDRESS="OKM_FOLDER_PORT_ADDRESS";
	
	public static final String PARAM_OKM_SEARCH_PORT_ADDRESS="OKM_SEARCH_PORT_ADDRESS";

	public static final String PARAM_PROTOCOL_BOOK= "PROTOCOL_BOOK";

	public static final String PARAM_PROTOCOL_BOOK_COMPANY = "PROTOCOL_BOOK_COMPANY";
	
	public static final String PARAM_PROTOCOL_BOOK_AUTHOR = "PROTOCOL_BOOK_AUTHOR";
	
	public static final String PARAM_PROTOCOL_BOOK_CREATOR = "PROTOCOL_BOOK_CREATOR";
	
	public static final String PARAM_PROTOCOL_BOOK_KEYWORDS = "PROTOCOL_BOOK_KEYWORDS";
	
	public static final String PARAM_PROTOCOL_BOOK_SUBJECT = "PROTOCOL_BOOK_SUBJECT";
	
	public static final String PARAM_PROTOCOL_BOOK_TITLE = "PROTOCOL_BOOK_TITLE";
	
	public static final String PARAM_PROTOCOL_BOOK_FILE= "PROTOCOL_BOOK_FILE";

	public static final String PARAM_REPORT_FOLDER = "REPORT_FOLDER";
	
	public static final String PARAM_ENABLE_PENDING_FUNCTIONALITY= "ENABLE_PENDING_FUNCTIONALITY";
	
	public static final String PARAM_ENABLE_DOSSIER_FUNCTIONALITY= "ENABLE_DOSSIER_FUNCTIONALITY";

	public static final String PARAM_ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY = "ENABLE_DOCUMENT_UPLOAD_FUNCTIONALITY";

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final String ROLE_WRITER = "ROLE_WRITER";
	
	public static final String ROLE_READER = "ROLE_READER";
	
	public static final String PARAM_KEY_INCOMING_ID = "incomingId";

	public static final String PARAM_KEY_RELATION_TYPE = "relationType";

	public static final String PARAM_OKM_USERNAME = "OKM_USERNAME";

	public static final String PARAM_OKM_PASSWORD = "OKM_PASSWORD";

	public String DEPARTMENT_TYPE_CENTRAL_ADMINISTRATION_NAME = "CENTRAL";
	
	public String DEPARTMENT_TYPE_UNIT_DEPARTMENT_NAME = "UNIT_DEPARTMENT";
	
	public String PARAM_CONTENT_SEARCH_RESULTS_LIMIT ="CONTENT_SEARCH_RESULTS_LIMIT";
	
	public String PARAM_ENABLE_IDENTICAL_PROTOCOL_NUMBER_ASSIGNMENT = "ENABLE_IDENTICAL_PROTOCOL_NUMBER_ASSIGNMENT";
	
	public String PARAM_USER_PASSWORD_EXPIRE_INTERVAL = "USER_PASSWORD_EXPIRE_INTERVAL";
	
}
