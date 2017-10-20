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
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Recipient {

	@XmlElement(required=true)
	@NotNull
	private CorrespondentType type;
	private String code;
	private String description;
	private String vatNumber;
	private String ssn;

	@XmlElement(required=true)
	@NotNull
	private CorrespondentAction action;
	
	@XmlElement(required=true)
	@NotNull
	private String distributionMethod;

	private Date dispatchDate;
	
	public CorrespondentType getType() {
		return type;
	}

	public void setType(CorrespondentType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

}
