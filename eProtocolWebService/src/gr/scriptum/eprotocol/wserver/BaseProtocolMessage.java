/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseProtocolMessage extends WsRequest {

	@XmlElement(required = true)
	@NotNull
	private String externalSystemId;
	@XmlElement(required = true)
	@NotNull
	private String externalUserId;
	@XmlElement(required = true)
	@NotNull
	private String department;
	@XmlElement(required = true)
	@NotNull
	private Integer book;
	@XmlElementWrapper(name = "transactors")
	@XmlElement(name = "transactor")
	@Valid
	private List<Transactor> transactors;
	private String subjectCode;
	@Size(min = 1, max = 512)
	private String subject;
	@Size(min = 1, max = 512)
	private String comments;
	@Min(1)
	private Integer attachedNumber;
	@Size(min = 1, max = 255)
	private String attachedDescription;
	@XmlElement(required = true)
	@NotNull
	private Integer documentType;
	private Date documentDate;
	@Size(min = 1, max = 32)
	private String externalProtocolNumber;
	@XmlElementWrapper(name = "relativeProtocols")
	@XmlElement(name = "relativeProtocol")
	@Valid
	private List<RelativeProtocol> relativeProtocols;

	public String getExternalId() {
		return getExternalSystemId();
	}

	public String getExternalSystemId() {
		return externalSystemId;
	}

	public void setExternalId(String externalSystemId) {
		setExternalSystemId(externalSystemId);
	}

	public void setExternalSystemId(String externalSystemId) {
		this.externalSystemId = externalSystemId;
	}

	public String getExternalUserId() {
		return externalUserId;
	}

	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getBook() {
		return book;
	}

	public void setBook(Integer book) {
		this.book = book;
	}

	public List<Transactor> getTransactors() {
		return transactors;
	}

	public void setTransactors(List<Transactor> transactors) {
		this.transactors = transactors;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getAttachedNumber() {
		return attachedNumber;
	}

	public void setAttachedNumber(Integer attachedNumber) {
		this.attachedNumber = attachedNumber;
	}

	public String getAttachedDescription() {
		return attachedDescription;
	}

	public void setAttachedDescription(String attachedDescription) {
		this.attachedDescription = attachedDescription;
	}

	public Integer getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getExternalProtocolNumber() {
		return externalProtocolNumber;
	}

	public void setExternalProtocolNumber(String externalProtocolNumber) {
		this.externalProtocolNumber = externalProtocolNumber;
	}

	public List<RelativeProtocol> getRelativeProtocols() {
		return relativeProtocols;
	}

	public void setRelativeProtocols(List<RelativeProtocol> relativeProtocols) {
		this.relativeProtocols = relativeProtocols;
	}
}
