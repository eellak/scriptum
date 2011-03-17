package gr.scriptum.eprotocol.ws;

import java.util.UUID;

/**
 * A response to a RequestLogin request
 * @author mike
 *
 */

public class ResponseLogin extends Response{

	private String okmToken = null;
	
	public ResponseLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseLogin(Request request, Exception e, String eCode) {
		super(request, e, eCode);
		// TODO Auto-generated constructor stub
	}

	public ResponseLogin(Request request, Exception e) {
		super(request, e);
		// TODO Auto-generated constructor stub
	}

	public ResponseLogin(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public ResponseLogin(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		super(requestId, okmToken, eCode, eMessage);
		// TODO Auto-generated constructor stub
	}
	
	public String getOkmToken() {
		return okmToken;
	}

	public void setOkmToken(String okmToken) {
		this.okmToken = okmToken;
	}	
	
}
