/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Transactor {

	public enum TransactorType{
		ACTIVE_MEMBER, INACTIVE_MEMBER
	}
	
	@XmlElement(required=true)
	@NotNull
	private TransactorType type;
	@XmlElement(required=true)
	@NotNull
	private String code;
	@XmlElement(required=true)
	@NotNull
	private String description;
	private String vatNumber;
	private String ssn;

	public TransactorType getType() {
		return type;
	}

	public void setType(TransactorType type) {
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

}
