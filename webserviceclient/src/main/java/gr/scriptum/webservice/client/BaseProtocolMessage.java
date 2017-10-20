
package gr.scriptum.webservice.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for baseProtocolMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseProtocolMessage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://wserver.eprotocol.scriptum.gr/}wsRequest">
 *       &lt;sequence>
 *         &lt;element name="externalSystemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="externalUserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="book" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transactors" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="transactor" type="{http://wserver.eprotocol.scriptum.gr/}transactor" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="subjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attachedNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="attachedDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="documentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="externalProtocolNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relativeProtocols" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="relativeProtocol" type="{http://wserver.eprotocol.scriptum.gr/}relativeProtocol" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "baseProtocolMessage", propOrder = {
    "externalSystemId",
    "externalUserId",
    "department",
    "book",
    "transactors",
    "subjectCode",
    "subject",
    "comments",
    "attachedNumber",
    "attachedDescription",
    "documentType",
    "documentDate",
    "externalProtocolNumber",
    "relativeProtocols"
})
@XmlSeeAlso({
    OutgoingProtocolMessage.class,
    IncomingProtocolMessage.class
})
public class BaseProtocolMessage
    extends WsRequest
{

    @XmlElement(required = true)
    protected String externalSystemId;
    @XmlElement(required = true)
    protected String externalUserId;
    @XmlElement(required = true)
    protected String department;
    protected int book;
    protected BaseProtocolMessage.Transactors transactors;
    protected String subjectCode;
    protected String subject;
    protected String comments;
    protected Integer attachedNumber;
    protected String attachedDescription;
    protected int documentType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentDate;
    protected String externalProtocolNumber;
    protected BaseProtocolMessage.RelativeProtocols relativeProtocols;

    /**
     * Gets the value of the externalSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSystemId() {
        return externalSystemId;
    }

    /**
     * Sets the value of the externalSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSystemId(String value) {
        this.externalSystemId = value;
    }

    /**
     * Gets the value of the externalUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalUserId() {
        return externalUserId;
    }

    /**
     * Sets the value of the externalUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalUserId(String value) {
        this.externalUserId = value;
    }

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
     * Gets the value of the transactors property.
     * 
     * @return
     *     possible object is
     *     {@link BaseProtocolMessage.Transactors }
     *     
     */
    public BaseProtocolMessage.Transactors getTransactors() {
        return transactors;
    }

    /**
     * Sets the value of the transactors property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseProtocolMessage.Transactors }
     *     
     */
    public void setTransactors(BaseProtocolMessage.Transactors value) {
        this.transactors = value;
    }

    /**
     * Gets the value of the subjectCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * Sets the value of the subjectCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectCode(String value) {
        this.subjectCode = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the attachedNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAttachedNumber() {
        return attachedNumber;
    }

    /**
     * Sets the value of the attachedNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAttachedNumber(Integer value) {
        this.attachedNumber = value;
    }

    /**
     * Gets the value of the attachedDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachedDescription() {
        return attachedDescription;
    }

    /**
     * Sets the value of the attachedDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachedDescription(String value) {
        this.attachedDescription = value;
    }

    /**
     * Gets the value of the documentType property.
     * 
     */
    public int getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     */
    public void setDocumentType(int value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocumentDate(XMLGregorianCalendar value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the externalProtocolNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalProtocolNumber() {
        return externalProtocolNumber;
    }

    /**
     * Sets the value of the externalProtocolNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalProtocolNumber(String value) {
        this.externalProtocolNumber = value;
    }

    /**
     * Gets the value of the relativeProtocols property.
     * 
     * @return
     *     possible object is
     *     {@link BaseProtocolMessage.RelativeProtocols }
     *     
     */
    public BaseProtocolMessage.RelativeProtocols getRelativeProtocols() {
        return relativeProtocols;
    }

    /**
     * Sets the value of the relativeProtocols property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseProtocolMessage.RelativeProtocols }
     *     
     */
    public void setRelativeProtocols(BaseProtocolMessage.RelativeProtocols value) {
        this.relativeProtocols = value;
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
     *         &lt;element name="relativeProtocol" type="{http://wserver.eprotocol.scriptum.gr/}relativeProtocol" maxOccurs="unbounded" minOccurs="0"/>
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
        "relativeProtocol"
    })
    public static class RelativeProtocols {

        protected List<RelativeProtocol> relativeProtocol;

        /**
         * Gets the value of the relativeProtocol property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the relativeProtocol property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRelativeProtocol().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RelativeProtocol }
         * 
         * 
         */
        public List<RelativeProtocol> getRelativeProtocol() {
            if (relativeProtocol == null) {
                relativeProtocol = new ArrayList<RelativeProtocol>();
            }
            return this.relativeProtocol;
        }

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
     *         &lt;element name="transactor" type="{http://wserver.eprotocol.scriptum.gr/}transactor" maxOccurs="unbounded" minOccurs="0"/>
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
        "transactor"
    })
    public static class Transactors {

        protected List<Transactor> transactor;

        /**
         * Gets the value of the transactor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the transactor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTransactor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Transactor }
         * 
         * 
         */
        public List<Transactor> getTransactor() {
            if (transactor == null) {
                transactor = new ArrayList<Transactor>();
            }
            return this.transactor;
        }

    }

}
