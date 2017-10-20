package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ScriptumDocument;

import java.util.ArrayList;
import java.util.UUID;
import com.openkm.ws.endpoint.Folder;
/**
 * 
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ResponseNewNode extends Response {
	private Folder              documentFolder = new Folder();
	private ArrayList<ScriptumDocument> documentList = new ArrayList<ScriptumDocument>();
	
	
	
	public ResponseNewNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseNewNode(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseNewNode(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseNewNode(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseNewNode(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}
	
	public void addDocument( ScriptumDocument doc ){
		documentList.add(doc);
	}


	public Folder getDocumentFolder() {
		return documentFolder;
	}

	public void setDocumentFolder(Folder documentFolder) {
		this.documentFolder = documentFolder;
	}

	public ArrayList<ScriptumDocument> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(ArrayList<ScriptumDocument> documentList) {
		this.documentList = documentList;
	}

	
	
}
