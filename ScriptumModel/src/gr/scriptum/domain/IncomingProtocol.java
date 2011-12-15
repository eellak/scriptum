package gr.scriptum.domain;

// Generated Jan 19, 2011 4:36:37 PM by Hibernate Tools 3.3.0.GA

import gr.scriptum.domain.ProtocolNode.Direction;

import java.util.Date;
import java.util.Set;

/**
 * Re-written manually from Mike
 */
@SuppressWarnings("serial")
public class IncomingProtocol extends ProtocolNode implements
		java.io.Serializable {

	/**
	 * apostoleas
	 */
	private Contact contact;
	/**
	 * Arithmos eggrafou
	 */
	private String incomingProtocolNumber;
	/**
	 * Hmeromhnia eggrafou
	 */
	private Date incomingDate;
	/**
	 * Topos
	 */
	private String incomingPlace;

	// -----------
	// Constructors
	// -----------
	public IncomingProtocol() {
		super(Direction.INCOMING);
	}

	public IncomingProtocol(DistributionMethod distributionMethod,
			Contact contact) {
		this();
		this.distributionMethod = distributionMethod;
		this.contact = contact;
	}

	public IncomingProtocol(Direction direction, Integer id,
			Integer protocolNumber, Integer protocolSeries, Date protocolDate,
			Integer protocolYear, DistributionMethod distributionMethod,
			String subject, String comments,
			Set<ProtocolDocument> protocolDocuments, Integer createUserId,
			Integer updateUserId, Date createDt, Date updateTs,

			Contact contact, String incomingProtocolNumber, Date incomingDate,
			String incomingPlace, ProtocolBook protocolBook,
			Integer relativeTask) {

		super(direction, id, protocolNumber, protocolSeries, protocolDate,
				protocolYear, distributionMethod, subject, comments,
				protocolDocuments, createUserId, updateUserId, createDt,
				updateTs, protocolBook, relativeTask);

		this.contact = contact;
		this.incomingProtocolNumber = incomingProtocolNumber;
		this.incomingDate = incomingDate;
		this.incomingPlace = incomingPlace;
	}

	// -----------
	// getters & setters
	// -----------
	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getIncomingProtocolNumber() {
		return this.incomingProtocolNumber;
	}

	public void setIncomingProtocolNumber(String incomingProtocolNumber) {
		this.incomingProtocolNumber = incomingProtocolNumber;
	}

	public Date getIncomingDate() {
		return this.incomingDate;
	}

	public void setIncomingDate(Date incomingDate) {
		this.incomingDate = incomingDate;
	}

	public String getIncomingPlace() {
		return this.incomingPlace;
	}

	public void setIncomingPlace(String incomingPlace) {
		this.incomingPlace = incomingPlace;
	}
}
