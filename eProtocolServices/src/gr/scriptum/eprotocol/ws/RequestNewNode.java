package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ScriptumDocument;

import java.util.ArrayList;

public class RequestNewNode extends Request{

	
	String folderPath;
	ArrayList<ScriptumDocument> documentList = new ArrayList<ScriptumDocument> ();
	
	
	public RequestNewNode() {
		super();
		this.action = DISPATCHER_ACTION.CREATENODE;
	}

	public RequestNewNode(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.CREATENODE;
	}

	public RequestNewNode(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken,  DISPATCHER_ACTION.CREATENODE);
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public ArrayList<ScriptumDocument> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(ArrayList<ScriptumDocument> documentList) {
		this.documentList = documentList;
	}
	
	public void addDocument(ScriptumDocument scriptumDoc ){
		documentList.add(scriptumDoc);
	}
	
	
}
