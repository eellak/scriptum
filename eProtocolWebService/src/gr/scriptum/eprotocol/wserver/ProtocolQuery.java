package gr.scriptum.eprotocol.wserver;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.eprotocol.wserver.WsRequest;

/**
 * The request message to inquire about a previously submitted protocol message.
 * 
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtocolQuery extends WsRequest {

	@XmlElement(required = false)
	private String department;
	@XmlElement(required = true)
	@NotNull
	private Integer book;
	@XmlElement(required = true)
	@NotNull
	private Integer protocolNumber;
	@XmlElement(required = true)
	@NotNull
	private Integer protocolYear;
	private Direction direction;
	

	public ProtocolQuery() {
		super();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getBook() {
		return book;
	}

	public void setBook(Integer book) {
		this.book = book;
	}

	public Integer getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Integer getProtocolYear() {
		return protocolYear;
	}

	public void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
