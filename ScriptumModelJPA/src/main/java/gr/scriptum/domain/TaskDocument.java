package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the task_document database table.
 * 
 */
@Entity
@Table(name="task_document")
@NamedQuery(name="TaskDocument.findAll", query="SELECT t FROM TaskDocument t")
public class TaskDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String documentKeywords;
	private String documentName;
	private Integer documentNumber;
	private Long documentSize;
	private String okmPath;
	private String okmUuid;
	private DocumentType documentType;
	private ProjectTask projectTask;

	public TaskDocument() {
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


	@Column(name="document_keywords", length=1024)
	public String getDocumentKeywords() {
		return this.documentKeywords;
	}

	public void setDocumentKeywords(String documentKeywords) {
		this.documentKeywords = documentKeywords;
	}


	@Column(name="document_name", nullable=false, length=256)
	public String getDocumentName() {
		return this.documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	@Column(name="document_number", nullable=false)
	public Integer getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}


	@Column(name="document_size", nullable=false)
	public Long getDocumentSize() {
		return this.documentSize;
	}

	public void setDocumentSize(Long documentSize) {
		this.documentSize = documentSize;
	}


	@Column(name="okm_path", length=1024)
	public String getOkmPath() {
		return this.okmPath;
	}

	public void setOkmPath(String okmPath) {
		this.okmPath = okmPath;
	}


	@Column(name="okm_uuid", length=256)
	public String getOkmUuid() {
		return this.okmUuid;
	}

	public void setOkmUuid(String okmUuid) {
		this.okmUuid = okmUuid;
	}


	//bi-directional many-to-one association to DocumentType
	@ManyToOne
	@JoinColumn(name="document_type_id", nullable=false)
	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}


	//bi-directional many-to-one association to ProjectTask
	@ManyToOne
	@JoinColumn(name="project_task_id", nullable=false)
	public ProjectTask getProjectTask() {
		return this.projectTask;
	}

	public void setProjectTask(ProjectTask projectTask) {
		this.projectTask = projectTask;
	}

}