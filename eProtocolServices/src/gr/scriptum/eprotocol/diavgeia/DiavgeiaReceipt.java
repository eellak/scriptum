package gr.scriptum.eprotocol.diavgeia;

import java.util.Date;

public class DiavgeiaReceipt {
	Integer protocolId;
	Integer protocolNumber;
	String  confirmationId;
	String  sessionId;
	String  adaCode;
	Date    submitted;
	
	String errCode;
	String errDescr;
	
	public DiavgeiaReceipt(String sessionId, Integer protocolId, Integer protocolNumber ){
		submitted = new Date();
		this.sessionId = sessionId;
	}
	
	
	public long getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(Integer protocol_id) {
		this.protocolId = protocol_id;
	}
	public Integer getProtocolNumber() {
		return protocolNumber;
	}
	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	public String getConfirmationId() {
		return confirmationId;
	}
	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}
	public String getAdaCode() {
		return adaCode;
	}
	public void setAdaCode(String adaCode) {
		this.adaCode = adaCode;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrDescr() {
		return errDescr;
	}
	public void setErrDescr(String errDescr) {
		this.errDescr = errDescr;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "DiavgeiaReceipt [protocolId=" + protocolId
				+ ", protocolNumber=" + protocolNumber + ", confirmationId="
				+ confirmationId + ", sessionId=" + sessionId + ", adaCode="
				+ adaCode + ", submitted=" + submitted + ", errCode=" + errCode
				+ ", errDescr=" + errDescr + "]";
	}
	
	
	
}

