package gr.scriptum.eprotocol.ws;

public class RequestLogin extends Request {
	
	String password;

	public RequestLogin() {
		super();
		this.action = DISPATCHER_ACTION.LOGIN;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
