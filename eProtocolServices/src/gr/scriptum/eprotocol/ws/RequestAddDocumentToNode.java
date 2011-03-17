package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ScriptumDocument;

public class RequestAddDocumentToNode extends Request {
	private String nodePath;
	private ScriptumDocument document;

	
	
	
	public RequestAddDocumentToNode() {
		super();
		this.action = DISPATCHER_ACTION.ADDDOC;
	}

	public RequestAddDocumentToNode(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.ADDDOC;
	}

	public RequestAddDocumentToNode(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.ADDDOC);
		// TODO Auto-generated constructor stub
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public ScriptumDocument getDocument() {
		return document;
	}

	public void setDocument(ScriptumDocument document) {
		this.document = document;
	}

}
