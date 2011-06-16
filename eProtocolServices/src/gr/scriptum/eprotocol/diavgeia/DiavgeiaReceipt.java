package gr.scriptum.eprotocol.diavgeia;

import java.util.Date;
/**
 * What are the operation results for posting to diavgeia.gov.gr
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class DiavgeiaReceipt {
	Integer documentId;
	String  sessionId;
	Date    submitted;
	String  confirmationId = null;
	String  adaCode = null;

	
	String errCode;
	String errDescr;
	
	public DiavgeiaReceipt(String sessionId, Integer documentId){
		submitted = new Date();
		this.sessionId = sessionId;
		this.documentId = documentId;
	}
	
	

	public Integer getDocumentId() {
		return documentId;
	}



	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
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
		return "DiavgeiaReceipt [documentId=" + documentId
				+ ", confirmationId=" + confirmationId + ", sessionId="
				+ sessionId + ", adaCode=" + adaCode + ", submitted="
				+ submitted + ", errCode=" + errCode + ", errDescr=" + errDescr
				+ "]";
	}
	
	
	
}

