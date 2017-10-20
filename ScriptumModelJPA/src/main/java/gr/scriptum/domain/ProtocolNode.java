package gr.scriptum.domain;

import gr.scriptum.domain.ProtocolBook;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Parent class for IncomingProtocol, Protocol so that the WS method
 * uses a single argument for both cases.
 * 
 * @author mike
 */

@SuppressWarnings("serial")
public abstract class ProtocolNode implements java.io.Serializable {

	public static final String NUMBER_DELIMITER = "/";

	public enum Direction {
		INCOMING, OUTGOING
	}

	Direction direction; // eiserxomeno-ejerxomeno
	protected Integer id; // kleidi

	Integer protocolNumber; // arithmos protokolloy
	Integer protocolSeries; // seira protokollou
	Date protocolDate; // hmeromhnia protokolisis
	Integer protocolYear; // etos protokollisis

	DistributionMethod distributionMethod;

	String subjectCode;
	String subject; // thema
	String comments; // parathrhseis - sxolia

	// bi-directional many-to-one association to ProtocolDocument
	@OneToMany(mappedBy = "protocol")
	Set<ProtocolDocument> protocolDocuments = new HashSet<>(0);

	Integer createUserId;
	Integer updateUserId;
	Date createDt;
	Date updateTs;

	Boolean isDeleted = null;
	String relativeProtocol;

	ProtocolBook protocolBook;
	Integer relativeTask;

	Dossier dossier;

	@Transient
	protected UUID uuid; 
	
	// -----------
	// Constructors
	// -----------
	public ProtocolNode() {
	}

	public ProtocolNode(Direction direction, Integer id,
			Integer protocolNumber, Integer protocolSeries, Date protocolDate,
			Integer protocolYear, DistributionMethod distributionMethod,
			String subject, String comments,
			Set<ProtocolDocument> protocolDocuments, Integer createUserId,
			Integer updateUserId, Date createDt, Date updateTs,
			ProtocolBook protocolBook,Integer relativeTask, Dossier dossier) {

		this.direction = direction;
		this.id = id;
		this.protocolNumber = protocolNumber;
		this.protocolSeries = protocolSeries;
		this.protocolDate = protocolDate;
		this.protocolYear = protocolYear;
		this.distributionMethod = distributionMethod;
		this.subject = subject;
		this.comments = comments;
		this.protocolDocuments = protocolDocuments;
		this.createUserId = createUserId;
		this.updateUserId = updateUserId;
		this.createDt = createDt;
		this.updateTs = updateTs;
		this.protocolBook = protocolBook;
		this.relativeTask = relativeTask;
		this.dossier = dossier;
	}

	public ProtocolNode(Direction direction) {
		this.direction = direction;
	}

	public String getFullNumber() {
		// return protocolNumber != null ? protocolNumber + NUMBER_DELIMITER
		// + protocolSeries + NUMBER_DELIMITER + protocolYear : null;
		return protocolNumber != null ? protocolNumber.toString() : null;
	}

	
	
	// -----------
	// getters & setters
	// -----------

	public void setIncoming() {
		this.direction = Direction.INCOMING;
	}

	public void setOutgoing() {
		this.direction = Direction.OUTGOING;
	}

	public Direction getDirection() {
		return this.direction;
	}

/*	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
*/
	public Integer getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Integer getProtocolSeries() {
		return protocolSeries;
	}

	public void setProtocolSeries(Integer protocolSeries) {
		this.protocolSeries = protocolSeries;
	}

	public Date getProtocolDate() {
		return protocolDate;
	}

	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}

	public Integer getProtocolYear() {
		return protocolYear;
	}

	public void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}

	public DistributionMethod getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(DistributionMethod distributionMethod) {
		this.distributionMethod = distributionMethod;
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

	public Set<ProtocolDocument> getProtocolDocuments() {
		return protocolDocuments;
	}

	public void setProtocolDocuments(Set<ProtocolDocument> protocolDocuments) {
		this.protocolDocuments = protocolDocuments;
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

	@Override
	public String toString() {
		return "ProtocolNode [direction=" + direction + ", id=" + id + ", protocolNumber=" + protocolNumber
				+ ", protocolSeries=" + protocolSeries + ", protocolDate=" + protocolDate + ", protocolYear="
				+ protocolYear + ", distributionMethod=" + distributionMethod + ", subjectCode=" + subjectCode
				+ ", subject=" + subject + ", comments=" + comments + ", createUserId=" + createUserId
				+ ", updateUserId=" + updateUserId + ", createDt=" + createDt + ", updateTs=" + updateTs
				+ ", isDeleted=" + isDeleted + ", relativeProtocol=" + relativeProtocol + ", protocolBook="
				+ protocolBook + ", relativeTask=" + relativeTask + ", dossier=" + dossier + "]";
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRelativeProtocol() {
		return relativeProtocol;
	}

	public void setRelativeProtocol(String relativeProtocol) {
		this.relativeProtocol = relativeProtocol;
	}

	public ProtocolBook getProtocolBook() {
		return protocolBook;
	}

	public void setProtocolBook(ProtocolBook protocolBook) {
		this.protocolBook = protocolBook;
	}

	public Integer getRelativeTask() {
		return relativeTask;
	}

	public void setRelativeTask(Integer relativeTask) {
		this.relativeTask = relativeTask;
	}

	public Dossier getDossier() {
		return dossier;
	}

	public void setDossier(Dossier dossier) {
		this.dossier = dossier;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
		ProtocolNode other = (ProtocolNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
