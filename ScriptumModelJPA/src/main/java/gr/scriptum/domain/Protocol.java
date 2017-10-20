package gr.scriptum.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentDirection;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolNode.Direction;

/**
 * The persistent class for the outgoing_protocol database table.
 * 
 */
@Entity
@Table(name = "protocol")
@NamedQuery(name = "Protocol.findAll", query = "SELECT o FROM Protocol o")
public class Protocol extends ProtocolNode implements Serializable {
	private static final long serialVersionUID = 1L;
	@Deprecated
	private Date outgoingDate;
	private String outgoingPlace;
	private Integer sentDiaygeia;
	private Date incomingDate;
	private String incomingPlace;
	private String incomingProtocolNumber;
	private Integer attachedNumber;
	private String attachedDescription;
	private String distributionMethodDetails;
	private DocumentType documentType;
	private String externalSystemId;
	private String externalUserId;

	private Set<ProtocolRelation> protocolRelationsAsSource = new HashSet<ProtocolRelation>();
	private Set<ProtocolRelation> protocolRelationsAsTarget = new HashSet<ProtocolRelation>();
	private Set<ApplicationLog> applicationLogs;
	private Set<ProtocolCorrespondent> protocolCorrespondents;
	private Set<ProtocolDependency> protocolDependencies;

	public enum DiavgeiaResult {
		FAILED, SUCCESS, SUCCESS_WITH_ERRORS, SUCCESS_NO_DOCUMENTS_SENT;
	}

	private Set<ProtocolCorrespondent> getProtocolCorrespondentsByDirection(CorrespondentDirection direction) {
		if (protocolCorrespondents == null) {
			return null;
		}
		Set<ProtocolCorrespondent> transactors = new HashSet<ProtocolCorrespondent>();
		for (ProtocolCorrespondent correspondent : protocolCorrespondents) {
			if (correspondent.getDirection().equals(direction)) {
				transactors.add(correspondent);
			}
		}
		return transactors;
	}

	public Protocol() {
	}

	public Protocol(Direction direction) {
		super(direction);
	}

	private void setProtocolCorrespondentByDirection(ProtocolCorrespondent correspondent,
			CorrespondentDirection direction) {
		if(correspondent==null) {
			return;
		}
		if (protocolCorrespondents == null) {
			protocolCorrespondents = new HashSet<ProtocolCorrespondent>();
		} else {
			// remove any existing correspondents of the same direction
			Iterator<ProtocolCorrespondent> iterator = protocolCorrespondents.iterator();
			while (iterator.hasNext()) {
				ProtocolCorrespondent protocolCorrespondent = iterator.next();
				if (protocolCorrespondent.getDirection().equals(direction)) {
					iterator.remove();
				}
			}
		}
		correspondent.setProtocol(this);
		correspondent.setDirection(direction);
		protocolCorrespondents.add(correspondent);
	}

	private void setProtocolCorrespondentsByDirection(Set<ProtocolCorrespondent> correspondents,
			CorrespondentDirection direction) {
		if (protocolCorrespondents == null) {
			protocolCorrespondents = new HashSet<ProtocolCorrespondent>();
		} else {
			// remove any existing correspondents of the same direction
			Iterator<ProtocolCorrespondent> iterator = protocolCorrespondents.iterator();
			while (iterator.hasNext()) {
				ProtocolCorrespondent protocolCorrespondent = iterator.next();
				if (protocolCorrespondent.getDirection().equals(direction)) {
					iterator.remove();
				}
			}
		}
		for (ProtocolCorrespondent transactor : correspondents) {
			transactor.setProtocol(this);
			transactor.setDirection(direction);
		}
		protocolCorrespondents.addAll(correspondents);

	}

	@Transient
	public ProtocolCorrespondent getSender() {
		ProtocolCorrespondent protocolCorrespondent = null;
		if (protocolCorrespondents != null) {
			for (ProtocolCorrespondent correspondent : protocolCorrespondents) {
				if (correspondent.getDirection().equals(CorrespondentDirection.SENDER)) {
					protocolCorrespondent = correspondent;
					break;
				}
			}
		}
		return protocolCorrespondent;
	}

	public void setSender(UserHierarchy userHierarchy) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.SENDER,
				CorrespondentType.EMPLOYEE);
		protocolCorrespondent.setContact(userHierarchy.getContact());
		protocolCorrespondent.setDepartment(userHierarchy.getDepartment());
		protocolCorrespondent.setDescription(userHierarchy.getContact().getFullName());
		protocolCorrespondent.setCode(userHierarchy.getContact().getCode());
		setSender(protocolCorrespondent);
	}

	public void setSender(Department department) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.SENDER,
				CorrespondentType.DEPARTMENT);
		protocolCorrespondent.setDepartment(department);
		protocolCorrespondent.setDescription(department.getName() + " (" + department.getCode() + ")");
		protocolCorrespondent.setCode(department.getCode());
		setSender(protocolCorrespondent);
	}

	public void setSender(Company company) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.SENDER,
				CorrespondentType.COMPANY);
		protocolCorrespondent.setCompany(company);
		protocolCorrespondent.setDescription(company.getName());
		protocolCorrespondent.setCode(company.getCode());
		setSender(protocolCorrespondent);
	}

	public void setSender(Contact contact) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.SENDER,
				CorrespondentType.CONTACT);
		protocolCorrespondent.setContact(contact);
		protocolCorrespondent.setDescription(contact.getFullName());
		protocolCorrespondent.setVatNumber(contact.getVatNumber());
		protocolCorrespondent.setSsn(contact.getSsn());
		setSender(protocolCorrespondent);
	}

	public void setSender(String description) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.SENDER,
				CorrespondentType.OTHER);
		protocolCorrespondent.setDescription(description);
		setSender(protocolCorrespondent);
	}

	public void setSender(ProtocolCorrespondent sender) {
		setProtocolCorrespondentByDirection(sender, CorrespondentDirection.SENDER);
	}

	@Transient
	public ProtocolCorrespondent getCreator() {
		ProtocolCorrespondent protocolCorrespondent = null;
		if (protocolCorrespondents != null) {
			for (ProtocolCorrespondent correspondent : protocolCorrespondents) {
				if (correspondent.getDirection().equals(CorrespondentDirection.CREATOR)) {
					protocolCorrespondent = correspondent;
					break;
				}
			}
		}
		return protocolCorrespondent;
	}

	public void setCreator(ProtocolCorrespondent sender) {
		setProtocolCorrespondentByDirection(sender, CorrespondentDirection.CREATOR);
	}

	@Transient
	public ProtocolCorrespondent getAuthor() {
		ProtocolCorrespondent protocolCorrespondent = null;
		if (protocolCorrespondents != null) {
			for (ProtocolCorrespondent correspondent : protocolCorrespondents) {
				if (correspondent.getDirection().equals(CorrespondentDirection.AUTHOR)) {
					protocolCorrespondent = correspondent;
					break;
				}
			}
		}
		return protocolCorrespondent;
	}

	public void setAuthor(UserHierarchy userHierarchy) {
		ProtocolCorrespondent protocolCorrespondent = new ProtocolCorrespondent(this, CorrespondentDirection.AUTHOR,
				CorrespondentType.EMPLOYEE);
		protocolCorrespondent.setContact(userHierarchy.getContact());
		protocolCorrespondent.setDepartment(userHierarchy.getDepartment());
		protocolCorrespondent.setDescription(userHierarchy.getContact().getFullName());
		protocolCorrespondent.setCode(userHierarchy.getContact().getCode());
		setProtocolCorrespondentByDirection(protocolCorrespondent, CorrespondentDirection.AUTHOR);
	}

	public void setAuthor(ProtocolCorrespondent author) {
		setProtocolCorrespondentByDirection(author, CorrespondentDirection.AUTHOR);
	}

	@Transient
	public Set<ProtocolCorrespondent> getTransactors() {
		return getProtocolCorrespondentsByDirection(CorrespondentDirection.TRANSACTOR);
	}

	public void setTransactors(Set<ProtocolCorrespondent> transactors) {
		setProtocolCorrespondentsByDirection(transactors, CorrespondentDirection.TRANSACTOR);
	}

	@Transient
	public Set<ProtocolCorrespondent> getInternalRecipients() {
		return getProtocolCorrespondentsByDirection(CorrespondentDirection.INTERNAL_RECIPIENT);
	}

	public void setInternalRecipients(Set<ProtocolCorrespondent> internalRecipients) {
		setProtocolCorrespondentsByDirection(internalRecipients, CorrespondentDirection.INTERNAL_RECIPIENT);
	}

	@Transient
	public Set<ProtocolCorrespondent> getRecipients() {
		return getProtocolCorrespondentsByDirection(CorrespondentDirection.RECIPIENT);
	}

	public void setRecipients(Set<ProtocolCorrespondent> recipients) {
		setProtocolCorrespondentsByDirection(recipients, CorrespondentDirection.RECIPIENT);
	}

	@Transient
	public Set<ProtocolCorrespondent> getAssignees() {
		return getProtocolCorrespondentsByDirection(CorrespondentDirection.ASSIGNEE);
	}

	public void setAssignees(Set<ProtocolCorrespondent> assignees) {
		setProtocolCorrespondentsByDirection(assignees, CorrespondentDirection.ASSIGNEE);
	}

	@Transient
	public Set<ProtocolRelation> getProtocolRelations() {
		Set<ProtocolRelation> protocolRelations = new HashSet<ProtocolRelation>();
		protocolRelations.addAll(protocolRelationsAsSource);
		protocolRelations.addAll(protocolRelationsAsTarget);
		return protocolRelations;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 512)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "create_user_id")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "outgoing_date")
	@Deprecated
	public Date getOutgoingDate() {
		return this.outgoingDate;
	}

	@Deprecated
	public void setOutgoingDate(Date outgoingDate) {
		this.outgoingDate = outgoingDate;
	}

	@Column(name = "outgoing_place", length = 256)
	public String getOutgoingPlace() {
		return this.outgoingPlace;
	}

	public void setOutgoingPlace(String outgoingPlace) {
		this.outgoingPlace = outgoingPlace;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "protocol_date")
	public Date getProtocolDate() {
		return this.protocolDate;
	}

	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}

	@Column(name = "protocol_number")
	public Integer getProtocolNumber() {
		return this.protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	@Column(name = "protocol_series")
	public Integer getProtocolSeries() {
		return this.protocolSeries;
	}

	public void setProtocolSeries(Integer protocolSeries) {
		this.protocolSeries = protocolSeries;
	}

	@Column(name = "protocol_year", columnDefinition = "YEAR(4)")
	public Integer getProtocolYear() {
		return this.protocolYear;
	}

	public void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}

	@Column(name = "relative_protocol", length = 64)
	public String getRelativeProtocol() {
		return this.relativeProtocol;
	}

	public void setRelativeProtocol(String relativeProtocol) {
		this.relativeProtocol = relativeProtocol;
	}

	@Column(name = "relative_task")
	public Integer getRelativeTask() {
		return this.relativeTask;
	}

	public void setRelativeTask(Integer relativeTask) {
		this.relativeTask = relativeTask;
	}

	@Column(name = "sent_diaygeia")
	public Integer getSentDiaygeia() {
		return this.sentDiaygeia;
	}

	public void setSentDiaygeia(Integer sentDiaygeia) {
		this.sentDiaygeia = sentDiaygeia;
	}

	@Override
	@Column(name = "subject_code")
	public String getSubjectCode() {
		// TODO Auto-generated method stub
		return super.getSubjectCode();
	}

	@Override
	public void setSubjectCode(String subjectCode) {
		// TODO Auto-generated method stub
		super.setSubjectCode(subjectCode);
	}

	@Column(length = 512)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	@Column(name = "update_user_id")
	public Integer getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	// bi-directional many-to-one association to DistributionMethod
	@ManyToOne
	@JoinColumn(name = "distribution_method_id")
	public DistributionMethod getDistributionMethod() {
		return this.distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	// bi-directional many-to-one association to Dossier
	@ManyToOne
	@JoinColumn(name = "dossier_id")
	public Dossier getDossier() {
		return this.dossier;
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}

	// bi-directional many-to-one association to ProtocolBook
	@ManyToOne
	@JoinColumn(name = "protocol_book_id")
	public ProtocolBook getProtocolBook() {
		return this.protocolBook;
	}

	public void setProtocolBook(ProtocolBook protocolBook) {
		this.protocolBook = protocolBook;
	}

	@OneToMany(mappedBy = "sourceProtocol", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ProtocolRelation> getProtocolRelationsAsSource() {
		return protocolRelationsAsSource;
	}

	public void setProtocolRelationsAsSource(Set<ProtocolRelation> protocolRelationsAsSource) {
		this.protocolRelationsAsSource = protocolRelationsAsSource;
	}

	@OneToMany(mappedBy = "targetProtocol", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ProtocolRelation> getProtocolRelationsAsTarget() {
		return protocolRelationsAsTarget;
	}

	public void setProtocolRelationsAsTarget(Set<ProtocolRelation> protocolRelationsAsTarget) {
		this.protocolRelationsAsTarget = protocolRelationsAsTarget;
	}

	// bi-directional many-to-one association to ProtocolDocument
	@OneToMany(mappedBy = "protocol")
	public Set<ProtocolDocument> getProtocolDocuments() {
		return this.protocolDocuments;
	}

	public void setProtocolDocuments(Set<ProtocolDocument> protocolDocuments) {
		this.protocolDocuments = protocolDocuments;
	}

	public ProtocolDocument addProtocolDocument(ProtocolDocument protocolDocument) {
		getProtocolDocuments().add(protocolDocument);
		protocolDocument.setProtocol(this);

		return protocolDocument;
	}

	public ProtocolDocument removeProtocolDocument(ProtocolDocument protocolDocument) {
		getProtocolDocuments().remove(protocolDocument);
		protocolDocument.setProtocol(null);

		return protocolDocument;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "incoming_date")
	public Date getIncomingDate() {
		return this.incomingDate;
	}

	public void setIncomingDate(Date incomingDate) {
		this.incomingDate = incomingDate;
	}

	@Column(name = "incoming_place", length = 256)
	public String getIncomingPlace() {
		return this.incomingPlace;
	}

	public void setIncomingPlace(String incomingPlace) {
		this.incomingPlace = incomingPlace;
	}

	@Column(name = "incoming_protocol_number", length = 32)
	public String getIncomingProtocolNumber() {
		return this.incomingProtocolNumber;
	}

	public void setIncomingProtocolNumber(String incomingProtocolNumber) {
		this.incomingProtocolNumber = incomingProtocolNumber;
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

	public String getDistributionMethodDetails() {
		return distributionMethodDetails;
	}

	public void setDistributionMethodDetails(String distributionMethodDetails) {
		this.distributionMethodDetails = distributionMethodDetails;
	}

	@Override
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public Direction getDirection() {
		return super.getDirection();
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@OneToMany(mappedBy = "protocol")
	public Set<ApplicationLog> getApplicationLogs() {
		return applicationLogs;
	}

	public void setApplicationLogs(Set<ApplicationLog> param) {
		this.applicationLogs = param;
	}

	@OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
		return protocolCorrespondents;
	}

	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
		this.protocolCorrespondents = param;
	}

	@OneToMany(mappedBy = "protocol")
	public Set<ProtocolDependency> getProtocolDependencies() {
		return protocolDependencies;
	}

	public void setProtocolDependencies(Set<ProtocolDependency> param) {
		this.protocolDependencies = param;
	}

	@ManyToOne(optional = false)
	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType param) {
		this.documentType = param;
	}

	public String getExternalSystemId() {
		return externalSystemId;
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

}