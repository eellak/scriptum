/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OutgoingProtocolMessage extends BaseProtocolMessage {

	@XmlElementWrapper(name="recipients",required=true) 
	@XmlElement(name="recipient", required=true)
	@NotEmpty
	@Valid
	private List<Recipient> recipients;

	@Valid
	private Author author;

	public List<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
}
