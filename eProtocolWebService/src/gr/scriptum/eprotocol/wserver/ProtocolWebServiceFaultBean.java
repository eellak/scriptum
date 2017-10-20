/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import gr.scriptum.eprotocol.wserver.ProtocolWebServiceFaultBean.ErrorCode;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtocolWebServiceFaultBean {
	
	public enum  ErrorCode{
		VALIDATION_ERROR, DUPLICATE_VALUE, AUTHENTICATION_ERROR, PERMISSIONS_ERROR, INSERTION_ERROR
	}
	
	private ErrorCode code;
	
	@XmlElementWrapper(name = "reasons")
	@XmlElement(name = "reason")
	private List<String> reasons;

	
	public ProtocolWebServiceFaultBean(ErrorCode code, List<String> reasons) {
		super();
		this.code = code;
		this.reasons = reasons;
	}

	public ProtocolWebServiceFaultBean(ErrorCode code, String reason) {
		super();
		this.code = code;
		this.reasons = new ArrayList<String>();
		this.reasons.add(reason);
	}

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}

	public List<String> getReasons() {
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}
	
}
