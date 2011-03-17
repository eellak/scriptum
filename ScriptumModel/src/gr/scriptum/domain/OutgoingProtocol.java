package gr.scriptum.domain;

import gr.scriptum.domain.ProtocolNode.Direction;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Re-written manually from Mike
 */
@SuppressWarnings("serial")
public class OutgoingProtocol extends ProtocolNode implements
		java.io.Serializable {

	private Date outgoingDate;
	private String author;
	private Integer sentDiaygeia;
	private String relativeProtocol;
	private String outgoingPlace;
	private Set<OutgoingRecipient> outgoingRecipients = new HashSet<OutgoingRecipient>(
			0);

	public OutgoingProtocol() {
		super(Direction.OUTGOING);
	}

	public OutgoingProtocol(DistributionMethod distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	public OutgoingProtocol(Direction direction, Integer id,
			Integer protocolNumber, Integer protocolSeries, Date protocolDate,
			Integer protocolYear, DistributionMethod distributionMethod,
			String subject, String comments,
			Set<ProtocolDocument> protocolDocuments, Integer createUserId,
			Integer updateUserId, Date createDt, Date updateTs,

			Date outgoingDate, String author, Integer sentDiaygeia,
			String relativeProtocol, String outgoingPlace,
			Set<OutgoingRecipient> outgoingRecipients) {

		super(direction, id, protocolNumber, protocolSeries, protocolDate,
				protocolYear, distributionMethod, subject, comments,
				protocolDocuments, createUserId, updateUserId, createDt,
				updateTs);

		this.outgoingDate = outgoingDate;
		this.author = author;
		this.sentDiaygeia = sentDiaygeia;
		this.relativeProtocol = relativeProtocol;
		this.outgoingRecipients = outgoingRecipients;
		this.outgoingPlace = outgoingPlace;
	}

	public Date getOutgoingDate() {
		return this.outgoingDate;
	}

	public void setOutgoingDate(Date outgoingDate) {
		this.outgoingDate = outgoingDate;
	}

	public Date getProtocolDate() {
		return this.protocolDate;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getSentDiaygeia() {
		return this.sentDiaygeia;
	}

	public void setSentDiaygeia(Integer sentDiaygeia) {
		this.sentDiaygeia = sentDiaygeia;
	}

	public Set<OutgoingRecipient> getOutgoingRecipients() {
		return this.outgoingRecipients;
	}

	public void setOutgoingRecipients(Set<OutgoingRecipient> outgoingRecipients) {
		this.outgoingRecipients = outgoingRecipients;
	}

	public String getRelativeProtocol() {
		return relativeProtocol;
	}

	public void setRelativeProtocol(String relativeProtocol) {
		this.relativeProtocol = relativeProtocol;
	}

	public String getOutgoingPlace() {
		return outgoingPlace;
	}

	public void setOutgoingPlace(String outgoingPlace) {
		this.outgoingPlace = outgoingPlace;
	}

}
