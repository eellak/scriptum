
package gr.scriptum.webservice.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for protocolInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="protocolInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://wserver.eprotocol.scriptum.gr/}wsResponse">
 *       &lt;sequence>
 *         &lt;element name="direction" type="{http://wserver.eprotocol.scriptum.gr/}direction" minOccurs="0"/>
 *         &lt;element name="protocolNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="protocolDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="book" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="bookDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subjectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attachedNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="attachedDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="documentTypeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="externalProtocolNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distributionMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distributionMethodDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distributionMethodDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="updateDt" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" minOccurs="0"/>
 *         &lt;element name="author" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" minOccurs="0"/>
 *         &lt;element name="creator" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" minOccurs="0"/>
 *         &lt;element name="creatorUser" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" minOccurs="0"/>
 *         &lt;element name="externalSystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalUserId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identicalNumberExists" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="internalRecipients" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="internalRecipient" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="recipients" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="recipient" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="transactors" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="transactor" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "protocolInfo", propOrder = {
    "direction",
    "protocolNumber",
    "protocolDate",
    "book",
    "bookDescription",
    "subjectCode",
    "subject",
    "comments",
    "attachedNumber",
    "attachedDescription",
    "documentType",
    "documentTypeDescription",
    "documentDate",
    "externalProtocolNumber",
    "distributionMethod",
    "distributionMethodDescription",
    "distributionMethodDetails",
    "createDt",
    "updateDt",
    "sender",
    "author",
    "creator",
    "creatorUser",
    "externalSystemId",
    "externalUserId",
    "identicalNumberExists",
    "internalRecipients",
    "recipients",
    "transactors",
    "relativeProtocols"
})
public class ProtocolInfo
    extends WsResponse
{

    protected Direction direction;
    protected Integer protocolNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar protocolDate;
    protected Integer book;
    protected String bookDescription;
    protected String subjectCode;
    protected String subject;
    protected String comments;
    protected Integer attachedNumber;
    protected String attachedDescription;
    protected Integer documentType;
    protected String documentTypeDescription;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar documentDate;
    protected String externalProtocolNumber;
    protected String distributionMethod;
    protected String distributionMethodDescription;
    protected String distributionMethodDetails;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDt;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDt;
    protected ProtocolCorrespondentInfo sender;
    protected ProtocolCorrespondentInfo author;
    protected ProtocolCorrespondentInfo creator;
    protected ProtocolCorrespondentInfo creatorUser;
    protected String externalSystemId;
    protected String externalUserId;
    protected Boolean identicalNumberExists;
    protected ProtocolInfo.InternalRecipients internalRecipients;
    protected ProtocolInfo.Recipients recipients;
    protected ProtocolInfo.Transactors transactors;
    protected ProtocolInfo.RelativeProtocols relativeProtocols;

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

    /**
     * Gets the value of the protocolNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProtocolNumber() {
        return protocolNumber;
    }

    /**
     * Sets the value of the protocolNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProtocolNumber(Integer value) {
        this.protocolNumber = value;
    }

    /**
     * Gets the value of the protocolDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProtocolDate() {
        return protocolDate;
    }

    /**
     * Sets the value of the protocolDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProtocolDate(XMLGregorianCalendar value) {
        this.protocolDate = value;
    }

    /**
     * Gets the value of the book property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBook() {
        return book;
    }

    /**
     * Sets the value of the book property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBook(Integer value) {
        this.book = value;
    }

    /**
     * Gets the value of the bookDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookDescription() {
        return bookDescription;
    }

    /**
     * Sets the value of the bookDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookDescription(String value) {
        this.bookDescription = value;
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
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDocumentType(Integer value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the documentTypeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    /**
     * Sets the value of the documentTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentTypeDescription(String value) {
        this.documentTypeDescription = value;
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
     * Gets the value of the distributionMethodDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributionMethodDescription() {
        return distributionMethodDescription;
    }

    /**
     * Sets the value of the distributionMethodDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributionMethodDescription(String value) {
        this.distributionMethodDescription = value;
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
     * Gets the value of the createDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDt() {
        return createDt;
    }

    /**
     * Sets the value of the createDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDt(XMLGregorianCalendar value) {
        this.createDt = value;
    }

    /**
     * Gets the value of the updateDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDt() {
        return updateDt;
    }

    /**
     * Sets the value of the updateDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDt(XMLGregorianCalendar value) {
        this.updateDt = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public ProtocolCorrespondentInfo getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public void setSender(ProtocolCorrespondentInfo value) {
        this.sender = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public ProtocolCorrespondentInfo getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public void setAuthor(ProtocolCorrespondentInfo value) {
        this.author = value;
    }

    /**
     * Gets the value of the creator property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public ProtocolCorrespondentInfo getCreator() {
        return creator;
    }

    /**
     * Sets the value of the creator property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public void setCreator(ProtocolCorrespondentInfo value) {
        this.creator = value;
    }

    /**
     * Gets the value of the creatorUser property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public ProtocolCorrespondentInfo getCreatorUser() {
        return creatorUser;
    }

    /**
     * Sets the value of the creatorUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolCorrespondentInfo }
     *     
     */
    public void setCreatorUser(ProtocolCorrespondentInfo value) {
        this.creatorUser = value;
    }

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
     * Gets the value of the identicalNumberExists property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIdenticalNumberExists() {
        return identicalNumberExists;
    }

    /**
     * Sets the value of the identicalNumberExists property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIdenticalNumberExists(Boolean value) {
        this.identicalNumberExists = value;
    }

    /**
     * Gets the value of the internalRecipients property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolInfo.InternalRecipients }
     *     
     */
    public ProtocolInfo.InternalRecipients getInternalRecipients() {
        return internalRecipients;
    }

    /**
     * Sets the value of the internalRecipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolInfo.InternalRecipients }
     *     
     */
    public void setInternalRecipients(ProtocolInfo.InternalRecipients value) {
        this.internalRecipients = value;
    }

    /**
     * Gets the value of the recipients property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolInfo.Recipients }
     *     
     */
    public ProtocolInfo.Recipients getRecipients() {
        return recipients;
    }

    /**
     * Sets the value of the recipients property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolInfo.Recipients }
     *     
     */
    public void setRecipients(ProtocolInfo.Recipients value) {
        this.recipients = value;
    }

    /**
     * Gets the value of the transactors property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolInfo.Transactors }
     *     
     */
    public ProtocolInfo.Transactors getTransactors() {
        return transactors;
    }

    /**
     * Sets the value of the transactors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolInfo.Transactors }
     *     
     */
    public void setTransactors(ProtocolInfo.Transactors value) {
        this.transactors = value;
    }

    /**
     * Gets the value of the relativeProtocols property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolInfo.RelativeProtocols }
     *     
     */
    public ProtocolInfo.RelativeProtocols getRelativeProtocols() {
        return relativeProtocols;
    }

    /**
     * Sets the value of the relativeProtocols property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolInfo.RelativeProtocols }
     *     
     */
    public void setRelativeProtocols(ProtocolInfo.RelativeProtocols value) {
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
     *         &lt;element name="internalRecipient" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<ProtocolCorrespondentInfo> internalRecipient;

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
         * {@link ProtocolCorrespondentInfo }
         * 
         * 
         */
        public List<ProtocolCorrespondentInfo> getInternalRecipient() {
            if (internalRecipient == null) {
                internalRecipient = new ArrayList<ProtocolCorrespondentInfo>();
            }
            return this.internalRecipient;
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
     *         &lt;element name="recipient" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
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
        "recipient"
    })
    public static class Recipients {

        protected List<ProtocolCorrespondentInfo> recipient;

        /**
         * Gets the value of the recipient property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recipient property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecipient().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProtocolCorrespondentInfo }
         * 
         * 
         */
        public List<ProtocolCorrespondentInfo> getRecipient() {
            if (recipient == null) {
                recipient = new ArrayList<ProtocolCorrespondentInfo>();
            }
            return this.recipient;
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
     *         &lt;element name="transactor" type="{http://wserver.eprotocol.scriptum.gr/}protocolCorrespondentInfo" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<ProtocolCorrespondentInfo> transactor;

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
         * {@link ProtocolCorrespondentInfo }
         * 
         * 
         */
        public List<ProtocolCorrespondentInfo> getTransactor() {
            if (transactor == null) {
                transactor = new ArrayList<ProtocolCorrespondentInfo>();
            }
            return this.transactor;
        }

    }

}
