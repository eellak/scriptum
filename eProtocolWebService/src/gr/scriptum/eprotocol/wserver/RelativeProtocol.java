/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolNode.Direction;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RelativeProtocol {

	@XmlElement(required = true)
	@NotNull
	private Integer number;
	@XmlElement(required = true)
	@NotNull
	private Integer year;
	@XmlElement(required = true)
	@NotNull
	private Direction direction;

	public RelativeProtocol() {
		// TODO Auto-generated constructor stub
	}

	public RelativeProtocol(Protocol protocol) {
		this.number = protocol.getProtocolNumber();
		this.year = protocol.getProtocolYear();
		this.direction = protocol.getDirection();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
