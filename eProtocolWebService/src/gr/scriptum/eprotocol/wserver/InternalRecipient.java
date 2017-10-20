/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class InternalRecipient {

	@XmlElement(required=true)
	@NotNull
	private String code;
	
	@XmlElement(required=true)
	@NotNull
	private CorrespondentAction action;
	
	@XmlElement(required=true)
	@NotNull
	private String distributionMethod;
	
	private Date routingDate;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CorrespondentAction getAction() {
		return action;
	}

	public void setAction(CorrespondentAction action) {
		this.action = action;
	}

	public String getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(String distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	public Date getRoutingDate() {
		return routingDate;
	}

	public void setRoutingDate(Date routingDate) {
		this.routingDate = routingDate;
	}

}
