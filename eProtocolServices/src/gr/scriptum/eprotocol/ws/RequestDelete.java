
package gr.scriptum.eprotocol.ws;


public class RequestDelete extends Request{
	
	private boolean isDirectory = false;
	private String  path;
	
	public RequestDelete() {
		super();
		this.action = DISPATCHER_ACTION.DELETE;
	}
	public RequestDelete(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.DELETE;
		// TODO Auto-generated constructor stub
	}
	public RequestDelete(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.DELETE);
		// TODO Auto-generated constructor stub
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
    
}
