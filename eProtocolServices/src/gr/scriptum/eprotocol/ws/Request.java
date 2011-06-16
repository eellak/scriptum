package gr.scriptum.eprotocol.ws;

import java.util.Date;
import java.util.UUID;

/**
 * Parent for all Document Repository (OpenKM) requests
 *  @author Mike Mountrakis mountrakis@uit.gr
 */


class Request {
	private String   username;
	private Integer  userId;
	private String   userIp;
	private String   systemName;
	private UUID     requestId;
	private String   okmToken;
	protected String action;
	private Date     created = new Date();
	
	public Request(){
		setRequestId();
	}

	
	public Request(String token){
		this();
		this.okmToken = token;
	}
	
	public Request(String username, Integer userId, String userIp,
			String systemName,  String okmToken, String action) {
		this();
		this.username = username;
		this.userId = userId;
		this.userIp = userIp;
		this.systemName = systemName;
		this.okmToken = okmToken;
		this.action = action;
	}

   public Request( Request req ){
    	this(req.username, req.userId, req.userIp,
			req.systemName,  req.okmToken, req.action);
    	setRequestId(req.getRequestId());  	
    }

   
   
   
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserIp() {
		return this.userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getRequestIdString() {
		return this.requestId.toString();
	}

	public UUID getRequestId() {
		return this.requestId;
	}
	
	
	public void setRequestId() {
		this.requestId = UUID.randomUUID();
	}
	
	public void setRequestId(UUID reqId) {
		this.requestId = reqId;
	}
	

	public String getOkmToken() {
		return okmToken;
	}

	public void setOkmToken(String okmToken) {
		this.okmToken = okmToken;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "Request [Username=" + username + ", UserId=" + userId
				+ ", UserIp=" + userIp + ", SystemName=" + systemName
				+ ", RequestId=" + requestId.toString() + ", okmToken=" + okmToken
				+ ", Action=" + action + ", Created=" + created.toString() + "]";
	}
	
	
	
	
}