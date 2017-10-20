package gr.scriptum.eprotocol.ws;



import java.util.UUID;
import com.openkm.ws.endpoint.Folder;

/**
 * 
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ResponseRenameNode extends Response {
	private Folder   documentFolder = new Folder();

	
	public ResponseRenameNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseRenameNode(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseRenameNode(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseRenameNode(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseRenameNode(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}
	


	public Folder getDocumentFolder() {
		return documentFolder;
	}

	public void setDocumentFolder(Folder documentFolder) {
		this.documentFolder = documentFolder;
	}
	
}
