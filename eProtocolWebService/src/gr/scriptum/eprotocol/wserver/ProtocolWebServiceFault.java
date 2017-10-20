/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.util.List;

import javax.xml.ws.WebFault;

import gr.scriptum.eprotocol.wserver.ProtocolWebServiceFaultBean.ErrorCode;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@WebFault(faultBean = "ProtocolWebServiceFaultBean", name="faultInfo")
public class ProtocolWebServiceFault extends Exception {

	private static final String ERROR = "ERROR";

	private ProtocolWebServiceFaultBean faultInfo;

	public ProtocolWebServiceFault(ProtocolWebServiceFaultBean faultInfo) {
		super(ERROR);
		this.faultInfo = faultInfo;
	}
	
	public ProtocolWebServiceFault(ErrorCode code, List<String> reasons) {
		super(ERROR);
		ProtocolWebServiceFaultBean faultInfo = new ProtocolWebServiceFaultBean(code, reasons);
		this.faultInfo = faultInfo;
	}

	public ProtocolWebServiceFault(ErrorCode code, String reason) {
		super(ERROR);
		ProtocolWebServiceFaultBean faultInfo = new ProtocolWebServiceFaultBean(code, reason);
		this.faultInfo = faultInfo;
	}

	public ProtocolWebServiceFault(String message, ProtocolWebServiceFaultBean faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public ProtocolWebServiceFault(ProtocolWebServiceFaultBean faultInfo, Throwable cause) {
		super(ERROR, cause);
		this.faultInfo = faultInfo;
	}

	public ProtocolWebServiceFaultBean getFaultInfo() {
		return faultInfo;
	}

	public void setFaultInfo(ProtocolWebServiceFaultBean faultInfo) {
		this.faultInfo = faultInfo;
	}

}
