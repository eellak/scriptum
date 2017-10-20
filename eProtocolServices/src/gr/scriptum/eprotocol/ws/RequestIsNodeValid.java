package gr.scriptum.eprotocol.ws;

public class RequestIsNodeValid extends Request{

	
	String folderPath;
	
	public RequestIsNodeValid() {
		super();
		this.action = DISPATCHER_ACTION.ISNODEVALID;
	}

	public RequestIsNodeValid(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.ISNODEVALID;
	}

	public RequestIsNodeValid(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken,  DISPATCHER_ACTION.ISNODEVALID);
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	
}
