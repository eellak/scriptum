/**
 * ProtocolReceipt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolReceipt  extends gr.scriptum.eprotocol.ws.endpoint.WsResponse  implements java.io.Serializable {
    private java.lang.Integer clientId;

    private java.lang.String clientName;

    private java.lang.Integer protocolId;

    private java.util.Calendar receivedDate;

    private java.lang.String subject;

    private java.lang.String yourMessageId;

    public ProtocolReceipt() {
    }

    public ProtocolReceipt(
           java.lang.String error,
           java.lang.Integer clientId,
           java.lang.String clientName,
           java.lang.Integer protocolId,
           java.util.Calendar receivedDate,
           java.lang.String subject,
           java.lang.String yourMessageId) {
        super(
            error);
        this.clientId = clientId;
        this.clientName = clientName;
        this.protocolId = protocolId;
        this.receivedDate = receivedDate;
        this.subject = subject;
        this.yourMessageId = yourMessageId;
    }


    /**
     * Gets the clientId value for this ProtocolReceipt.
     * 
     * @return clientId
     */
    public java.lang.Integer getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this ProtocolReceipt.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.Integer clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the clientName value for this ProtocolReceipt.
     * 
     * @return clientName
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this ProtocolReceipt.
     * 
     * @param clientName
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the protocolId value for this ProtocolReceipt.
     * 
     * @return protocolId
     */
    public java.lang.Integer getProtocolId() {
        return protocolId;
    }


    /**
     * Sets the protocolId value for this ProtocolReceipt.
     * 
     * @param protocolId
     */
    public void setProtocolId(java.lang.Integer protocolId) {
        this.protocolId = protocolId;
    }


    /**
     * Gets the receivedDate value for this ProtocolReceipt.
     * 
     * @return receivedDate
     */
    public java.util.Calendar getReceivedDate() {
        return receivedDate;
    }


    /**
     * Sets the receivedDate value for this ProtocolReceipt.
     * 
     * @param receivedDate
     */
    public void setReceivedDate(java.util.Calendar receivedDate) {
        this.receivedDate = receivedDate;
    }


    /**
     * Gets the subject value for this ProtocolReceipt.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this ProtocolReceipt.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the yourMessageId value for this ProtocolReceipt.
     * 
     * @return yourMessageId
     */
    public java.lang.String getYourMessageId() {
        return yourMessageId;
    }


    /**
     * Sets the yourMessageId value for this ProtocolReceipt.
     * 
     * @param yourMessageId
     */
    public void setYourMessageId(java.lang.String yourMessageId) {
        this.yourMessageId = yourMessageId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProtocolReceipt)) return false;
        ProtocolReceipt other = (ProtocolReceipt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.clientName==null && other.getClientName()==null) || 
             (this.clientName!=null &&
              this.clientName.equals(other.getClientName()))) &&
            ((this.protocolId==null && other.getProtocolId()==null) || 
             (this.protocolId!=null &&
              this.protocolId.equals(other.getProtocolId()))) &&
            ((this.receivedDate==null && other.getReceivedDate()==null) || 
             (this.receivedDate!=null &&
              this.receivedDate.equals(other.getReceivedDate()))) &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.yourMessageId==null && other.getYourMessageId()==null) || 
             (this.yourMessageId!=null &&
              this.yourMessageId.equals(other.getYourMessageId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getClientName() != null) {
            _hashCode += getClientName().hashCode();
        }
        if (getProtocolId() != null) {
            _hashCode += getProtocolId().hashCode();
        }
        if (getReceivedDate() != null) {
            _hashCode += getReceivedDate().hashCode();
        }
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getYourMessageId() != null) {
            _hashCode += getYourMessageId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProtocolReceipt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "protocolReceipt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocolId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receivedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "receivedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yourMessageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yourMessageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
