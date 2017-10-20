
package gr.scriptum.webservice.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incomingProtocolMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incomingProtocolMessage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://wserver.eprotocol.scriptum.gr/}baseProtocolMessage">
 *       &lt;sequence>
 *         &lt;element name="distributionMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="distributionMethodDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://wserver.eprotocol.scriptum.gr/}sender"/>
 *         &lt;element name="internalRecipients" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="internalRecipient" type="{http://wserver.eprotocol.scriptum.gr/}internalRecipient" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incomingProtocolMessage", propOrder = {
    "distributionMethod",
    "distributionMethodDetails",
    "sender",
    "internalRecipients"
})
public class IncomingProtocolMessage
    extends BaseProtocolMessage
{

    @XmlElement(required = true)
    protected String distributionMethod;
    protected String distributionMethodDetails;
    @XmlElement(required = true)
    protected Sender sender;
    protected IncomingProtocolMessage.InternalRecipients internalRecipients;

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
     * Gets the value of the distributionMethodDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionMethodDetails() {
        return distributionMethodDetails;
    }

    /**
     * Sets the value of the distributionMethodDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionMethodDetails(String value) {
        this.distributionMethodDetails = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link Sender }
     *     
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sender }
     *     
     */
    public void setSender(Sender value) {
        this.sender = value;
    }

    /**
     * Gets the value of the internalRecipients property.
     * 
     * @return
     *     possible object is
     *     {@link IncomingProtocolMessage.InternalRecipients }
     *     
     */
    public IncomingProtocolMessage.InternalRecipients getInternalRecipients() {
        return internalRecipients;
    }

    /**
     * Sets the value of the internalRecipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link IncomingProtocolMessage.InternalRecipients }
     *     
     */
    public void setInternalRecipients(IncomingProtocolMessage.InternalRecipients value) {
        this.internalRecipients = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="internalRecipient" type="{http://wserver.eprotocol.scriptum.gr/}internalRecipient" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "internalRecipient"
    })
    public static class InternalRecipients {

        protected List<InternalRecipient> internalRecipient;

        /**
         * Gets the value of the internalRecipient property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the internalRecipient property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInternalRecipient().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InternalRecipient }
         * 
         * 
         */
        public List<InternalRecipient> getInternalRecipient() {
            if (internalRecipient == null) {
                internalRecipient = new ArrayList<InternalRecipient>();
            }
            return this.internalRecipient;
        }

    }

}
