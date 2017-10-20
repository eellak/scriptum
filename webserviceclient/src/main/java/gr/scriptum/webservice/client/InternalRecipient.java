
package gr.scriptum.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for internalRecipient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="internalRecipient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="action" type="{http://wserver.eprotocol.scriptum.gr/}correspondentAction"/>
 *         &lt;element name="distributionMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="routingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "internalRecipient", propOrder = {
    "code",
    "action",
    "distributionMethod",
    "routingDate"
})
public class InternalRecipient {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected CorrespondentAction action;
    @XmlElement(required = true)
    protected String distributionMethod;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar routingDate;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link CorrespondentAction }
     *     
     */
    public CorrespondentAction getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrespondentAction }
     *     
     */
    public void setAction(CorrespondentAction value) {
        this.action = value;
    }

    /**
     * Gets the value of the distributionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionMethod() {
        return distributionMethod;
    }

    /**
     * Sets the value of the distributionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionMethod(String value) {
        this.distributionMethod = value;
    }

    /**
     * Gets the value of the routingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRoutingDate() {
        return routingDate;
    }

    /**
     * Sets the value of the routingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRoutingDate(XMLGregorianCalendar value) {
        this.routingDate = value;
    }

}
