package gr.scriptum.eprotocol.ws;

import java.util.UUID;
/**
 * 
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ResponseIsNodeValid extends Response {
	
	private Boolean valid = null;
	
	public ResponseIsNodeValid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseIsNodeValid(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseIsNodeValid(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseIsNodeValid(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseIsNodeValid(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
}
