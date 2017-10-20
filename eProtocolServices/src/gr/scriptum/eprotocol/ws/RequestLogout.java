package gr.scriptum.eprotocol.ws;

/**
 * The logout request.
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class RequestLogout extends Request{
	
	public RequestLogout() {
		super();
		this.action = DISPATCHER_ACTION.LOGOUT;
	}

	public  RequestLogout( String okmToken){
		this();
		this.setOkmToken(okmToken);
	}
	
	public RequestLogout( ResponseLogin loginResp){
		this();
		this.setOkmToken(loginResp.getOkmToken());
	}
	
	public RequestLogout(String username, Integer userId, String userIp,
			String systemName, String okmToken) {
		super(username, userId, userIp, systemName, okmToken, DISPATCHER_ACTION.LOGOUT);
		// TODO Auto-generated constructor stub
	}	

}
