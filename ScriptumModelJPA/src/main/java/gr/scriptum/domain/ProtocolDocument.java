package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import gr.scriptum.domain.ProtocolDependency;

import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the protocol_document database table.
 * 
 */
@Entity
@Table(name = "protocol_document")
@NamedQuery(name = "ProtocolDocument.findAll", query = "SELECT p FROM ProtocolDocument p")
public class ProtocolDocument extends ScriptumDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Integer createUserId;
	Integer updateUserId;
	@Temporal(TemporalType.TIMESTAMP)
	Date createDt;
	@Temporal(TemporalType.TIMESTAMP)
	Date updateTs;

	private Protocol protocol;
	private Set<ProtocolDependency> protocolDependencies;

	public ProtocolDocument() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getId() {
		return super.getId();
	}

	public void setId(Integer id) {
		super.setId(id);
	}

	@Column(name = "diavgeia_ada_code", length = 256)
	public String getDiavgeiaAdaCode() {
		return super.getDiavgeiaAdaCode();
	}

	public void setDiavgeiaAdaCode(String diavgeiaAdaCode) {
		super.setDiavgeiaAdaCode(diavgeiaAdaCode);
	}

	@Column(name = "document_keywords", length = 1024)
	public String getDocumentKeywords() {
		return super.getDocumentKeywords();
	}

	public void setDocumentKeywords(String documentKeywords) {
		super.setDocumentKeywords(documentKeywords);
	}

	@Column(name = "document_mime", length = 100)
	public String getDocumentMime() {
		return super.getDocumentMime();
	}

	public void setDocumentMime(String documentMime) {
		super.setDocumentMime(documentMime);
	}

	@Column(name = "document_name", nullable = false, length = 256)
	public String getDocumentName() {
		return super.getDocumentName();
	}

	public void setDocumentName(String documentName) {
		super.setDocumentName(documentName);
	}

	@Column(name = "document_number", nullable = false)
	public Integer getDocumentNumber() {
		return super.getDocumentNumber();
	}

	public void setDocumentNumber(Integer documentNumber) {
		super.setDocumentNumber(documentNumber);
	}

	@Column(name = "document_size", nullable = false)
	public Long getDocumentSize() {
		return super.getDocumentSize();
	}

	public void setDocumentSize(Long documentSize) {
		super.setDocumentSize(documentSize);
	}

	@Deprecated
	@Column(name = "okm_path", length = 1024)
	public String getOkmPath() {
		return super.getOkmPath();
	}

	@Deprecated
	public void setOkmPath(String okmPath) {
		super.setOkmPath(okmPath);
	}

	@Column(name = "okm_uuid", length = 256)
	public String getOkmUuid() {
		return super.getOkmUuid();
	}

	public void setOkmUuid(String okmUuid) {
		super.setOkmUuid(okmUuid);
	}
	
	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	// bi-directional many-to-one association to DocumentType
	@ManyToOne
	@JoinColumn(name = "document_types_id", nullable = false)
	public DocumentType getDocumentType() {
		return super.getDocumentType();
	}

	public void setDocumentType(DocumentType documentType) {
		super.setDocumentType(documentType);
	}

	// bi-directional many-to-one association to Protocol
	@ManyToOne
	@JoinColumn(name = "outgoing_protocol_id")
	public Protocol getProtocol() {
		return this.protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@OneToMany(mappedBy = "protocolDocument")
	public Set<ProtocolDependency> getProtocolDependencies() {
	    return protocolDependencies;
	}

	public void setProtocolDependencies(Set<ProtocolDependency> param) {
	    this.protocolDependencies = param;
	}

}