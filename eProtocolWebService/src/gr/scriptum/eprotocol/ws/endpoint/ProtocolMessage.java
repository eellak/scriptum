/**
 * ProtocolMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolMessage  extends gr.scriptum.eprotocol.ws.endpoint.WsRequest  implements java.io.Serializable {
    private gr.scriptum.eprotocol.ws.endpoint.WsDocument[] attachements;

    private java.lang.String comments;

    private java.lang.String place;

    private java.lang.String subject;

    private java.lang.String yourMessageId;

    public ProtocolMessage() {
    }

    public ProtocolMessage(
           java.lang.Integer clientId,
           gr.scriptum.eprotocol.ws.endpoint.WsDocument[] attachements,
           java.lang.String comments,
           java.lang.String place,
           java.lang.String subject,
           java.lang.String yourMessageId) {
        super(
            clientId);
        this.attachements = attachements;
        this.comments = comments;
        this.place = place;
        this.subject = subject;
        this.yourMessageId = yourMessageId;
    }


    /**
     * Gets the attachements value for this ProtocolMessage.
     * 
     * @return attachements
     */
    public gr.scriptum.eprotocol.ws.endpoint.WsDocument[] getAttachements() {
        return attachements;
    }


    /**
     * Sets the attachements value for this ProtocolMessage.
     * 
     * @param attachements
     */
    public void setAttachements(gr.scriptum.eprotocol.ws.endpoint.WsDocument[] attachements) {
        this.attachements = attachements;
    }

    public gr.scriptum.eprotocol.ws.endpoint.WsDocument getAttachements(int i) {
        return this.attachements[i];
    }

    public void setAttachements(int i, gr.scriptum.eprotocol.ws.endpoint.WsDocument _value) {
        this.attachements[i] = _value;
    }


    /**
     * Gets the comments value for this ProtocolMessage.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this ProtocolMessage.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the place value for this ProtocolMessage.
     * 
     * @return place
     */
    public java.lang.String getPlace() {
        return place;
    }


    /**
     * Sets the place value for this ProtocolMessage.
     * 
     * @param place
     */
    public void setPlace(java.lang.String place) {
        this.place = place;
    }


    /**
     * Gets the subject value for this ProtocolMessage.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this ProtocolMessage.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the yourMessageId value for this ProtocolMessage.
     * 
     * @return yourMessageId
     */
    public java.lang.String getYourMessageId() {
        return yourMessageId;
    }


    /**
     * Sets the yourMessageId value for this ProtocolMessage.
     * 
     * @param yourMessageId
     */
    public void setYourMessageId(java.lang.String yourMessageId) {
        this.yourMessageId = yourMessageId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProtocolMessage)) return false;
        ProtocolMessage other = (ProtocolMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attachements==null && other.getAttachements()==null) || 
             (this.attachements!=null &&
              java.util.Arrays.equals(this.attachements, other.getAttachements()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.place==null && other.getPlace()==null) || 
             (this.place!=null &&
              this.place.equals(other.getPlace()))) &&
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
        if (getAttachements() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachements());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachements(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getPlace() != null) {
            _hashCode += getPlace().hashCode();
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
        new org.apache.axis.description.TypeDesc(ProtocolMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "protocolMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachements");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attachements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "wsDocument"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("place");
        elemField.setXmlName(new javax.xml.namespace.QName("", "place"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
