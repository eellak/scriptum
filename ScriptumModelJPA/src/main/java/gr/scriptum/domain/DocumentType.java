package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import gr.scriptum.domain.ProtocolBook;
import javax.persistence.ManyToMany;


/**
 * The persistent class for the document_type database table.
 * 
 */
@Entity
@Table(name="document_type")
@NamedQuery(name="DocumentType.findAll", query="SELECT d FROM DocumentType d")
public class DocumentType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum ApplicableType{
		INCOMING, OUTGOING, DOCUMENT; 
	}
	
	private Integer id;
	private String code;
	private Date createDt;
	private String createUser;
	private Integer days;
	private String description;
	private Integer isPeriodic;
	private String name;
	private Date updateTs;
	private String updateUser;
	private ApplicableType applicableTo;
	private Boolean allowOutgoingFromIncoming;
	private Set<ProtocolDocument> protocolDocuments;
	private Set<TaskDocument> taskDocuments;
	private Set<Protocol> protocols;
	private Set<Subject> subjects;
	private Set<ProtocolBook> protocolBooks;

	public DocumentType() {
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentType other = (DocumentType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}


	@Column(name="create_user", length=16)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public Integer getDays() {
		return this.days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="is_periodic")
	public Integer getIsPeriodic() {
		return this.isPeriodic;
	}

	public void setIsPeriodic(Integer isPeriodic) {
		this.isPeriodic = isPeriodic;
	}


	@Column(length=256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	@Column(name="update_user", length=16)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	public ApplicableType getApplicableTo() {
		return applicableTo;
	}


	public void setApplicableTo(ApplicableType applicableTo) {
		this.applicableTo = applicableTo;
	}


	//bi-directional many-to-one association to ProtocolDocument
	@OneToMany(mappedBy="documentType")
	public Set<ProtocolDocument> getProtocolDocuments() {
		return this.protocolDocuments;
	}

	public void setProtocolDocuments(Set<ProtocolDocument> protocolDocuments) {
		this.protocolDocuments = protocolDocuments;
	}

	public ProtocolDocument addProtocolDocument(ProtocolDocument protocolDocument) {
		getProtocolDocuments().add(protocolDocument);
		protocolDocument.setDocumentType(this);

		return protocolDocument;
	}

	public ProtocolDocument removeProtocolDocument(ProtocolDocument protocolDocument) {
		getProtocolDocuments().remove(protocolDocument);
		protocolDocument.setDocumentType(null);

		return protocolDocument;
	}


	//bi-directional many-to-one association to TaskDocument
	@OneToMany(mappedBy="documentType")
	public Set<TaskDocument> getTaskDocuments() {
		return this.taskDocuments;
	}

	public void setTaskDocuments(Set<TaskDocument> taskDocuments) {
		this.taskDocuments = taskDocuments;
	}

	public TaskDocument addTaskDocument(TaskDocument taskDocument) {
		getTaskDocuments().add(taskDocument);
		taskDocument.setDocumentType(this);

		return taskDocument;
	}

	public TaskDocument removeTaskDocument(TaskDocument taskDocument) {
		getTaskDocuments().remove(taskDocument);
		taskDocument.setDocumentType(null);

		return taskDocument;
	}


	@OneToMany(mappedBy = "documentType")
	public Set<Protocol> getProtocols() {
	    return protocols;
	}


	public void setProtocols(Set<Protocol> param) {
	    this.protocols = param;
	}


	public Boolean getAllowOutgoingFromIncoming() {
		return allowOutgoingFromIncoming;
	}


	public void setAllowOutgoingFromIncoming(Boolean allowOutgoingFromIncoming) {
		this.allowOutgoingFromIncoming = allowOutgoingFromIncoming;
	}


	@OneToMany(mappedBy = "documentType")
	public Set<Subject> getSubjects() {
	    return subjects;
	}


	public void setSubjects(Set<Subject> param) {
	    this.subjects = param;
	}


	@ManyToMany
	public Set<ProtocolBook> getProtocolBooks() {
	    return protocolBooks;
	}


	public void setProtocolBooks(Set<ProtocolBook> param) {
	    this.protocolBooks = param;
	}

}