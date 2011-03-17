package gr.scriptum.eprotocol.ws;

public class RequestSendDocument extends Request {

	private String documentPath;

	public RequestSendDocument() {
		super();
		this.action = DISPATCHER_ACTION.SENDDOC;
	}
	
	


	public RequestSendDocument(Request req) {
		super(req);
		this.action = DISPATCHER_ACTION.SENDDOC;
	}

	public RequestSendDocument(String username, Integer id, String ip,
			String systemName, String okmToken) {
		super( username, id, ip, systemName, okmToken, DISPATCHER_ACTION.SENDDOC);
	}




	public final String getDocumentPath() {
		return documentPath;
	}

	public final void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	@Override
	public String toString() {
		return "RequestSendDocument [documentPath=" + documentPath
				+ ", action=" + action + "]";
	}

}
