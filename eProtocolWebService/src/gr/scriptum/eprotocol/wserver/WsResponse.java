package gr.scriptum.eprotocol.wserver;
/**
 * The head of all eProtocol Web Service Responses
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public abstract class WsResponse{
	String error;

	public WsResponse(){}
	
	
	
	public WsResponse(String error) {
		this.error = error;
	}



	public final String getError() {
		return error;
	}

	public final void setError(String error) {
		this.error = error;
	}

}