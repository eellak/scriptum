/**
 * ProtocolInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolInfo  extends gr.scriptum.eprotocol.ws.endpoint.WsResponse  implements java.io.Serializable {
    private java.lang.Integer clientId;

    private java.lang.String clientName;

    private java.lang.String comments;

    private java.util.Calendar protocolDate;

    private java.lang.Integer protocolId;

    private java.lang.Integer protocolNumber;

    private java.lang.Integer protocolSeries;

    private java.lang.Integer protocolYear;

    private java.lang.String subject;

    private java.lang.String yourMessageId;

    public ProtocolInfo() {
    }

    public ProtocolInfo(
           java.lang.String error,
           java.lang.Integer clientId,
           java.lang.String clientName,
           java.lang.String comments,
           java.util.Calendar protocolDate,
           java.lang.Integer protocolId,
           java.lang.Integer protocolNumber,
           java.lang.Integer protocolSeries,
           java.lang.Integer protocolYear,
           java.lang.String subject,
           java.lang.String yourMessageId) {
        super(
            error);
        this.clientId = clientId;
        this.clientName = clientName;
        this.comments = comments;
        this.protocolDate = protocolDate;
        this.protocolId = protocolId;
        this.protocolNumber = protocolNumber;
        this.protocolSeries = protocolSeries;
        this.protocolYear = protocolYear;
        this.subject = subject;
        this.yourMessageId = yourMessageId;
    }


    /**
     * Gets the clientId value for this ProtocolInfo.
     * 
     * @return clientId
     */
    public java.lang.Integer getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this ProtocolInfo.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.Integer clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the clientName value for this ProtocolInfo.
     * 
     * @return clientName
     */
    public java.lang.String getClientName() {
        return clientName;
    }


    /**
     * Sets the clientName value for this ProtocolInfo.
     * 
     * @param clientName
     */
    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }


    /**
     * Gets the comments value for this ProtocolInfo.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this ProtocolInfo.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the protocolDate value for this ProtocolInfo.
     * 
     * @return protocolDate
     */
    public java.util.Calendar getProtocolDate() {
        return protocolDate;
    }


    /**
     * Sets the protocolDate value for this ProtocolInfo.
     * 
     * @param protocolDate
     */
    public void setProtocolDate(java.util.Calendar protocolDate) {
        this.protocolDate = protocolDate;
    }


    /**
     * Gets the protocolId value for this ProtocolInfo.
     * 
     * @return protocolId
     */
    public java.lang.Integer getProtocolId() {
        return protocolId;
    }


    /**
     * Sets the protocolId value for this ProtocolInfo.
     * 
     * @param protocolId
     */
    public void setProtocolId(java.lang.Integer protocolId) {
        this.protocolId = protocolId;
    }


    /**
     * Gets the protocolNumber value for this ProtocolInfo.
     * 
     * @return protocolNumber
     */
    public java.lang.Integer getProtocolNumber() {
        return protocolNumber;
    }


    /**
     * Sets the protocolNumber value for this ProtocolInfo.
     * 
     * @param protocolNumber
     */
    public void setProtocolNumber(java.lang.Integer protocolNumber) {
        this.protocolNumber = protocolNumber;
    }


    /**
     * Gets the protocolSeries value for this ProtocolInfo.
     * 
     * @return protocolSeries
     */
    public java.lang.Integer getProtocolSeries() {
        return protocolSeries;
    }


    /**
     * Sets the protocolSeries value for this ProtocolInfo.
     * 
     * @param protocolSeries
     */
    public void setProtocolSeries(java.lang.Integer protocolSeries) {
        this.protocolSeries = protocolSeries;
    }


    /**
     * Gets the protocolYear value for this ProtocolInfo.
     * 
     * @return protocolYear
     */
    public java.lang.Integer getProtocolYear() {
        return protocolYear;
    }


    /**
     * Sets the protocolYear value for this ProtocolInfo.
     * 
     * @param protocolYear
     */
    public void setProtocolYear(java.lang.Integer protocolYear) {
        this.protocolYear = protocolYear;
    }


    /**
     * Gets the subject value for this ProtocolInfo.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this ProtocolInfo.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the yourMessageId value for this ProtocolInfo.
     * 
     * @return yourMessageId
     */
    public java.lang.String getYourMessageId() {
        return yourMessageId;
    }


    /**
     * Sets the yourMessageId value for this ProtocolInfo.
     * 
     * @param yourMessageId
     */
    public void setYourMessageId(java.lang.String yourMessageId) {
        this.yourMessageId = yourMessageId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProtocolInfo)) return false;
        ProtocolInfo other = (ProtocolInfo) obj;
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
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.protocolDate==null && other.getProtocolDate()==null) || 
             (this.protocolDate!=null &&
              this.protocolDate.equals(other.getProtocolDate()))) &&
            ((this.protocolId==null && other.getProtocolId()==null) || 
             (this.protocolId!=null &&
              this.protocolId.equals(other.getProtocolId()))) &&
            ((this.protocolNumber==null && other.getProtocolNumber()==null) || 
             (this.protocolNumber!=null &&
              this.protocolNumber.equals(other.getProtocolNumber()))) &&
            ((this.protocolSeries==null && other.getProtocolSeries()==null) || 
             (this.protocolSeries!=null &&
              this.protocolSeries.equals(other.getProtocolSeries()))) &&
            ((this.protocolYear==null && other.getProtocolYear()==null) || 
             (this.protocolYear!=null &&
              this.protocolYear.equals(other.getProtocolYear()))) &&
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
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getProtocolDate() != null) {
            _hashCode += getProtocolDate().hashCode();
        }
        if (getProtocolId() != null) {
            _hashCode += getProtocolId().hashCode();
        }
        if (getProtocolNumber() != null) {
            _hashCode += getProtocolNumber().hashCode();
        }
        if (getProtocolSeries() != null) {
            _hashCode += getProtocolSeries().hashCode();
        }
        if (getProtocolYear() != null) {
            _hashCode += getProtocolYear().hashCode();
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
        new org.apache.axis.description.TypeDesc(ProtocolInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "protocolInfo"));
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
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocolDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("protocolNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocolNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolSeries");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocolSeries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocolYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
