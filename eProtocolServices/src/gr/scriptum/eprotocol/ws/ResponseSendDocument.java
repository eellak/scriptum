package gr.scriptum.eprotocol.ws;

import java.util.Arrays;


import com.openkm.ws.endpoint.Document;

/**
 * Retrieves the document information after a  document has been registered with Document Management
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ResponseSendDocument extends Response {
	Document documentInfo = null;
	byte[] content = null;

	public ResponseSendDocument() {
		super();
	}

	public ResponseSendDocument(Request request, Exception e, String eCode) {
		super(request, e, eCode);
	}

	public ResponseSendDocument(Request request, Exception e) {
		super(request, e);
	}

	public ResponseSendDocument(Request request) {
		super(request);
	}



	public final Document getDocumentInfo() {
		return documentInfo;
	}

	public final void setDocumentInfo(Document documentInfo) {
		this.documentInfo = documentInfo;
	}

	public final byte[] getContent() {
		return content;
	}

	public final void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ResponseSendDocument [documentInfo=" + documentInfo
				+ ", content=" + Arrays.toString(content) + "]";
	}

	
	public String getMimeType(){
		if(documentInfo != null )
			return documentInfo.getMimeType();
		else
			return null;
	}
	
}
