package gr.scriptum.eprotocol.ws;

import java.util.Date;
import java.util.UUID;

/**
 * Parent for all repository responses 
 * @author mike
 */

public class Response {
	private UUID   requestId;
	private Date   completed = new Date();
	private String eCode     = DISPACTER_ERROR.ECODE_NO_ERROR;
	private String eMessage  = DISPACTER_ERROR.EMESG_NO_ERROR;
	
	public Response(){
		
	}

	public Response( Request request ){
		this.requestId = request.getRequestId();
	}
	
	public Response( Request request, Exception e){
		this(request);
		this.eMessage = e.getMessage();
	}
	
	public Response( Request request, Exception e, String eCode){
		this(request,e);
		this.eCode = eCode;
	}

	public Response(UUID requestId, String okmToken, String eCode,
			String eMessage) {
		this.requestId = requestId;
		this.eCode = eCode;
		this.eMessage = eMessage;
	}
    //other methods
	
	public boolean isError(){
		if( !DISPACTER_ERROR.ECODE_NO_ERROR.equals(eCode) )
			return true;
		else
			return false;
	}
	
	//-------
	// getters setters
	//--------
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

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String geteMessage() {
		return eMessage;
	}

	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public void setCompletedNow() {
		this.completed = new Date();
	}	
	
	@Override
	public String toString() {
		return "Response [requestId=" + requestId + ", completed=" + completed.toString()
				+ ", eCode=" + eCode + ", eMessage=" + eMessage + "]";
	}

	
	

		
	
}