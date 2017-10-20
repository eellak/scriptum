package gr.scriptum.eprotocol.wserver;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * The input message to Web Service method receiveProtocol().
 * 
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IncomingProtocolMessage extends BaseProtocolMessage{

	@XmlElement(required=true)
	@NotNull
	private String distributionMethod;
	
	@Size(min=1, max=255)
	private String distributionMethodDetails;
	
	@XmlElement(required = true)
	@NotNull
	@Valid
	private Sender sender;

	@XmlElementWrapper(name="internalRecipients") 
	@XmlElement(name="internalRecipient")
	@Valid
	private List<InternalRecipient> internalRecipients;

	// ArrayList<WsDocument> attachements;

	public IncomingProtocolMessage() {
		super();
	}

	public IncomingProtocolMessage(String externalId, String department) {
		super.setExternalSystemId(externalId);
		super.setDepartment(department);
	}

	// public final ArrayList<WsDocument> getAttachements() {
	// return attachements;
	// }
	//
	//
	// public final void setAttachements(ArrayList<WsDocument> attachements) {
	// this.attachements = attachements;
	// }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getDistributionMethod() {
		return distributionMethod;
	}

	public void setDistributionMethod(String distributionMethod) {
		this.distributionMethod = distributionMethod;
	}

	public String getDistributionMethodDetails() {
		return distributionMethodDetails;
	}

	public void setDistributionMethodDetails(String distributionMethodDetails) {
		this.distributionMethodDetails = distributionMethodDetails;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public List<InternalRecipient> getInternalRecipients() {
		return internalRecipients;
	}

	public void setInternalRecipients(List<InternalRecipient> internalRecipients) {
		this.internalRecipients = internalRecipients;
	}

}
