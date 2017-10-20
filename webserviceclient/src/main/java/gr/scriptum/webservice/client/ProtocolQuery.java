
package gr.scriptum.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocolQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolQuery">
 *   &lt;complexContent>
 *     &lt;extension base="{http://wserver.eprotocol.scriptum.gr/}wsRequest">
 *       &lt;sequence>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="book" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="protocolNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="protocolYear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="direction" type="{http://wserver.eprotocol.scriptum.gr/}direction" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "protocolQuery", propOrder = {
    "department",
    "book",
    "protocolNumber",
    "protocolYear",
    "direction"
})
public class ProtocolQuery
    extends WsRequest
{

    protected String department;
    protected int book;
    protected int protocolNumber;
    protected int protocolYear;
    protected Direction direction;

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the book property.
     * 
     */
    public int getBook() {
        return book;
    }

    /**
     * Sets the value of the book property.
     * 
     */
    public void setBook(int value) {
        this.book = value;
    }

    /**
     * Gets the value of the protocolNumber property.
     * 
     */
    public int getProtocolNumber() {
        return protocolNumber;
    }

    /**
     * Sets the value of the protocolNumber property.
     * 
     */
    public void setProtocolNumber(int value) {
        this.protocolNumber = value;
    }

    /**
     * Gets the value of the protocolYear property.
     * 
     */
    public int getProtocolYear() {
        return protocolYear;
    }

    /**
     * Sets the value of the protocolYear property.
     * 
     */
    public void setProtocolYear(int value) {
        this.protocolYear = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link Direction }
     *     
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Direction }
     *     
     */
    public void setDirection(Direction value) {
        this.direction = value;
    }

}
