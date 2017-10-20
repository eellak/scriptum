package gr.scriptum.eprotocol.wserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ProtocolRelation.RelationType;

/**
 * The information requested from a ProtocolQuery message.
 * 
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtocolInfo extends WsResponse {

	private Direction direction;

	private Integer protocolNumber;

	private Date protocolDate;

	private Integer book;
	
	private String bookDescription;

	private String subjectCode;

	private String subject;

	private String comments;

	private Integer attachedNumber;

	private String attachedDescription;

	private Integer documentType;
	
	private String documentTypeDescription;

	private Date documentDate;

	private String externalProtocolNumber;

	private String distributionMethod;
	
	private String distributionMethodDescription;

	private String distributionMethodDetails;

	private Date createDt;

	private Date updateDt;

	private ProtocolCorrespondentInfo sender;

	private ProtocolCorrespondentInfo author;

	private ProtocolCorrespondentInfo creator;
	
	private ProtocolCorrespondentInfo creatorUser;
	
	private String externalSystemId;
	
	private String externalUserId;
	
	private Boolean identicalNumberExists = Boolean.FALSE;

	@XmlElementWrapper(name = "internalRecipients")
	@XmlElement(name = "internalRecipient")
	private List<ProtocolCorrespondentInfo> internalRecipients;

	@XmlElementWrapper(name = "recipients")
	@XmlElement(name = "recipient")
	private List<ProtocolCorrespondentInfo> recipients;

	@XmlElementWrapper(name = "transactors")
	@XmlElement(name = "transactor")
	private List<ProtocolCorrespondentInfo> transactors;

	@XmlElementWrapper(name = "relativeProtocols")
	@XmlElement(name = "relativeProtocol")
	private List<RelativeProtocol> relativeProtocols;

	public ProtocolInfo(Protocol protocol) {
		super();
		this.direction = protocol.getDirection();
		this.protocolNumber = protocol.getProtocolNumber();
		this.protocolDate = protocol.getProtocolDate();
		this.book = protocol.getProtocolBook().getId();
		this.bookDescription = protocol.getProtocolBook().getProtocolSeries();
		this.subjectCode = protocol.getSubjectCode();
		this.subject = protocol.getSubject();
		this.comments = protocol.getComments();
		this.attachedNumber = protocol.getAttachedNumber();
		this.attachedDescription = protocol.getAttachedDescription();
		this.documentType = protocol.getDocumentType().getId();
		this.documentTypeDescription = protocol.getDocumentType().getName();
		this.documentDate = protocol.getIncomingDate();
		this.externalProtocolNumber = protocol.getIncomingProtocolNumber();
		this.distributionMethod = protocol.getDistributionMethod() == null ? null
				: protocol.getDistributionMethod().getCode();
		this.distributionMethodDescription = protocol.getDistributionMethod() == null ? null
				: protocol.getDistributionMethod().getDescription();
		this.distributionMethodDetails = protocol.getDistributionMethodDetails();
		this.createDt = protocol.getCreateDt();
		this.updateDt = protocol.getUpdateTs();

		this.sender = protocol.getSender() == null ? null : new ProtocolCorrespondentInfo(protocol.getSender());
		this.author = protocol.getAuthor() == null ? null : new ProtocolCorrespondentInfo(protocol.getAuthor());

		if (protocol.getCreator() != null) {
			ProtocolCorrespondent creator2 = protocol.getCreator();
			this.creator = new ProtocolCorrespondentInfo(CorrespondentType.DEPARTMENT, creator2.getDepartment().getName(),
					creator2.getDepartment().getCode(), null, null, null, null, null, null, null, null,
					creator2.getCreateDt(), creator2.getUpdateTs());
			this.creatorUser = new ProtocolCorrespondentInfo(null, creator2.getUser().getSurname()+" "+creator2.getUser().getName(),
					creator2.getUser().getCode(), null, null, null, null, null, null, null, null,
					creator2.getCreateDt(), creator2.getUpdateTs());
		}
		this.internalRecipients = protocol.getInternalRecipients().stream().map(ProtocolCorrespondentInfo::new)
				.collect(Collectors.toCollection(ArrayList::new));
		this.recipients = protocol.getRecipients().stream().map(ProtocolCorrespondentInfo::new)
				.collect(Collectors.toCollection(ArrayList::new));
		this.transactors = protocol.getTransactors().stream().map(ProtocolCorrespondentInfo::new)
				.collect(Collectors.toCollection(ArrayList::new));
		this.relativeProtocols = protocol.getProtocolRelations().stream().map((pr) -> {
			if(pr.getRelationType().equals(RelationType.IDENTICAL_NUMBER)) {
				identicalNumberExists = Boolean.TRUE;
			}
			return new RelativeProtocol(pr.getSourceProtocol().getId().equals(protocol.getId()) ? pr.getTargetProtocol()
					: pr.getSourceProtocol());
		}).collect(Collectors.toCollection(ArrayList::new));
		this.externalSystemId = protocol.getExternalSystemId();
		this.externalUserId = protocol.getExternalUserId();
	}
}
