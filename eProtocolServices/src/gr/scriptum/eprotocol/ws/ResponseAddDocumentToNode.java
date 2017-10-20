package gr.scriptum.eprotocol.ws;

import gr.scriptum.domain.ScriptumDocument;

import java.util.UUID;

/**
 * 
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ResponseAddDocumentToNode extends Response{
	private ScriptumDocument document;

	public ResponseAddDocumentToNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseAddDocumentToNode(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseAddDocumentToNode(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseAddDocumentToNode(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseAddDocumentToNode(UUID requestId, String okmToken,
			String eCode, String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}

	public ScriptumDocument getDocument() {
		return document;
	}

	public void setDocument(ScriptumDocument document) {
		this.document = document;
	}
 
	
}
