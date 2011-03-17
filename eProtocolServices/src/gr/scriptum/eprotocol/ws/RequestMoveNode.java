package gr.scriptum.eprotocol.ws;

public class RequestMoveNode extends Request{
	private String oldPath;
	private String newPath;
	
	
	public RequestMoveNode() {
		super();
		this.action = DISPATCHER_ACTION.MOVENODE;
	}
	public RequestMoveNode(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.MOVENODE;
	}
	
	
	public RequestMoveNode(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.MOVENODE);
		// TODO Auto-generated constructor stub
	}
	public String getOldPath() {
		return oldPath;
	}
	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}
	public String getNewPath() {
		return newPath;
	}
	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}
	
	
	@Override
	public String toString() {
		return "RequestMoveNode [oldPath=" + oldPath + ", newPath=" + newPath
				+ "]";
	}

}
