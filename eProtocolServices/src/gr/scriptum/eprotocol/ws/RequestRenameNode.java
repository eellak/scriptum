package gr.scriptum.eprotocol.ws;
/**
 * Rename a protocol node
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class RequestRenameNode extends Request{
	private String oldName;
	private String newName;
	
	
	public RequestRenameNode() {
		super();
		this.action = DISPATCHER_ACTION.RENAMENODE;
	}
	public RequestRenameNode(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.RENAMENODE;
	}
	
	
	public RequestRenameNode(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.RENAMENODE);
		// TODO Auto-generated constructor stub
	}

	
	
	public final String getOldName() {
		return oldName;
	}
	public final void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public final String getNewName() {
		return newName;
	}
	public final void setNewName(String newName) {
		this.newName = newName;
	}
	
	@Override
	public String toString() {
		return "RequestRenameNode [oldName=" + oldName + ", newName=" + newName
				+ "]";
	}


}
