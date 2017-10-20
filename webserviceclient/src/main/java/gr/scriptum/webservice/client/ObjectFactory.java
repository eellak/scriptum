
package gr.scriptum.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gr.scriptum.webservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReceiveOutgoingProtocolResponse_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "receiveOutgoingProtocolResponse");
    private final static QName _InquireProtocolsResponse_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "inquireProtocolsResponse");
    private final static QName _Exception_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "Exception");
    private final static QName _ReceiveIncomingProtocol_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "receiveIncomingProtocol");
    private final static QName _FaultInfo_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "faultInfo");
    private final static QName _InquireProtocols_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "inquireProtocols");
    private final static QName _ReceiveIncomingProtocolResponse_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "receiveIncomingProtocolResponse");
    private final static QName _ReceiveOutgoingProtocol_QNAME = new QName("http://wserver.eprotocol.scriptum.gr/", "receiveOutgoingProtocol");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gr.scriptum.webservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProtocolInfo.Transactors }
     * 
     */
    public ProtocolInfo.Transactors createProtocolInfoTransactors() {
        return new ProtocolInfo.Transactors();
    }

    /**
     * Create an instance of {@link ReceiveIncomingProtocolResponse }
     * 
     */
    public ReceiveIncomingProtocolResponse createReceiveIncomingProtocolResponse() {
        return new ReceiveIncomingProtocolResponse();
    }

    /**
     * Create an instance of {@link ProtocolReceipt }
     * 
     */
    public ProtocolReceipt createProtocolReceipt() {
        return new ProtocolReceipt();
    }

    /**
     * Create an instance of {@link ProtocolInfo }
     * 
     */
    public ProtocolInfo createProtocolInfo() {
        return new ProtocolInfo();
    }

    /**
     * Create an instance of {@link Recipient }
     * 
     */
    public Recipient createRecipient() {
        return new Recipient();
    }

    /**
     * Create an instance of {@link ProtocolInfo.Recipients }
     * 
     */
    public ProtocolInfo.Recipients createProtocolInfoRecipients() {
        return new ProtocolInfo.Recipients();
    }

    /**
     * Create an instance of {@link InquireProtocols }
     * 
     */
    public InquireProtocols createInquireProtocols() {
        return new InquireProtocols();
    }

    /**
     * Create an instance of {@link InquireProtocolsResponse }
     * 
     */
    public InquireProtocolsResponse createInquireProtocolsResponse() {
        return new InquireProtocolsResponse();
    }

    /**
     * Create an instance of {@link ProtocolInfo.InternalRecipients }
     * 
     */
    public ProtocolInfo.InternalRecipients createProtocolInfoInternalRecipients() {
        return new ProtocolInfo.InternalRecipients();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Transactor }
     * 
     */
    public Transactor createTransactor() {
        return new Transactor();
    }

    /**
     * Create an instance of {@link ProtocolInfo.RelativeProtocols }
     * 
     */
    public ProtocolInfo.RelativeProtocols createProtocolInfoRelativeProtocols() {
        return new ProtocolInfo.RelativeProtocols();
    }

    /**
     * Create an instance of {@link BaseProtocolMessage.Transactors }
     * 
     */
    public BaseProtocolMessage.Transactors createBaseProtocolMessageTransactors() {
        return new BaseProtocolMessage.Transactors();
    }

    /**
     * Create an instance of {@link IncomingProtocolMessage.InternalRecipients }
     * 
     */
    public IncomingProtocolMessage.InternalRecipients createIncomingProtocolMessageInternalRecipients() {
        return new IncomingProtocolMessage.InternalRecipients();
    }

    /**
     * Create an instance of {@link OutgoingProtocolMessage.Recipients }
     * 
     */
    public OutgoingProtocolMessage.Recipients createOutgoingProtocolMessageRecipients() {
        return new OutgoingProtocolMessage.Recipients();
    }

    /**
     * Create an instance of {@link ReceiveOutgoingProtocolResponse }
     * 
     */
    public ReceiveOutgoingProtocolResponse createReceiveOutgoingProtocolResponse() {
        return new ReceiveOutgoingProtocolResponse();
    }

    /**
     * Create an instance of {@link RelativeProtocol }
     * 
     */
    public RelativeProtocol createRelativeProtocol() {
        return new RelativeProtocol();
    }

    /**
     * Create an instance of {@link ProtocolCorrespondentInfo }
     * 
     */
    public ProtocolCorrespondentInfo createProtocolCorrespondentInfo() {
        return new ProtocolCorrespondentInfo();
    }

    /**
     * Create an instance of {@link ProtocolQuery }
     * 
     */
    public ProtocolQuery createProtocolQuery() {
        return new ProtocolQuery();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link Sender }
     * 
     */
    public Sender createSender() {
        return new Sender();
    }

    /**
     * Create an instance of {@link OutgoingProtocolMessage }
     * 
     */
    public OutgoingProtocolMessage createOutgoingProtocolMessage() {
        return new OutgoingProtocolMessage();
    }

    /**
     * Create an instance of {@link BaseProtocolMessage.RelativeProtocols }
     * 
     */
    public BaseProtocolMessage.RelativeProtocols createBaseProtocolMessageRelativeProtocols() {
        return new BaseProtocolMessage.RelativeProtocols();
    }

    /**
     * Create an instance of {@link ProtocolWebServiceFaultBean.Reasons }
     * 
     */
    public ProtocolWebServiceFaultBean.Reasons createProtocolWebServiceFaultBeanReasons() {
        return new ProtocolWebServiceFaultBean.Reasons();
    }

    /**
     * Create an instance of {@link ProtocolWebServiceFaultBean }
     * 
     */
    public ProtocolWebServiceFaultBean createProtocolWebServiceFaultBean() {
        return new ProtocolWebServiceFaultBean();
    }

    /**
     * Create an instance of {@link IncomingProtocolMessage }
     * 
     */
    public IncomingProtocolMessage createIncomingProtocolMessage() {
        return new IncomingProtocolMessage();
    }

    /**
     * Create an instance of {@link ReceiveOutgoingProtocol }
     * 
     */
    public ReceiveOutgoingProtocol createReceiveOutgoingProtocol() {
        return new ReceiveOutgoingProtocol();
    }

    /**
     * Create an instance of {@link InternalRecipient }
     * 
     */
    public InternalRecipient createInternalRecipient() {
        return new InternalRecipient();
    }

    /**
     * Create an instance of {@link ReceiveIncomingProtocol }
     * 
     */
    public ReceiveIncomingProtocol createReceiveIncomingProtocol() {
        return new ReceiveIncomingProtocol();
    }

    /**
     * Create an instance of {@link BaseProtocolMessage }
     * 
     */
    public BaseProtocolMessage createBaseProtocolMessage() {
        return new BaseProtocolMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveOutgoingProtocolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "receiveOutgoingProtocolResponse")
    public JAXBElement<ReceiveOutgoingProtocolResponse> createReceiveOutgoingProtocolResponse(ReceiveOutgoingProtocolResponse value) {
        return new JAXBElement<ReceiveOutgoingProtocolResponse>(_ReceiveOutgoingProtocolResponse_QNAME, ReceiveOutgoingProtocolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquireProtocolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "inquireProtocolsResponse")
    public JAXBElement<InquireProtocolsResponse> createInquireProtocolsResponse(InquireProtocolsResponse value) {
        return new JAXBElement<InquireProtocolsResponse>(_InquireProtocolsResponse_QNAME, InquireProtocolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveIncomingProtocol }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "receiveIncomingProtocol")
    public JAXBElement<ReceiveIncomingProtocol> createReceiveIncomingProtocol(ReceiveIncomingProtocol value) {
        return new JAXBElement<ReceiveIncomingProtocol>(_ReceiveIncomingProtocol_QNAME, ReceiveIncomingProtocol.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolWebServiceFaultBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "faultInfo")
    public JAXBElement<ProtocolWebServiceFaultBean> createFaultInfo(ProtocolWebServiceFaultBean value) {
        return new JAXBElement<ProtocolWebServiceFaultBean>(_FaultInfo_QNAME, ProtocolWebServiceFaultBean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InquireProtocols }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "inquireProtocols")
    public JAXBElement<InquireProtocols> createInquireProtocols(InquireProtocols value) {
        return new JAXBElement<InquireProtocols>(_InquireProtocols_QNAME, InquireProtocols.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveIncomingProtocolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "receiveIncomingProtocolResponse")
    public JAXBElement<ReceiveIncomingProtocolResponse> createReceiveIncomingProtocolResponse(ReceiveIncomingProtocolResponse value) {
        return new JAXBElement<ReceiveIncomingProtocolResponse>(_ReceiveIncomingProtocolResponse_QNAME, ReceiveIncomingProtocolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveOutgoingProtocol }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wserver.eprotocol.scriptum.gr/", name = "receiveOutgoingProtocol")
    public JAXBElement<ReceiveOutgoingProtocol> createReceiveOutgoingProtocol(ReceiveOutgoingProtocol value) {
        return new JAXBElement<ReceiveOutgoingProtocol>(_ReceiveOutgoingProtocol_QNAME, ReceiveOutgoingProtocol.class, null, value);
    }

}
