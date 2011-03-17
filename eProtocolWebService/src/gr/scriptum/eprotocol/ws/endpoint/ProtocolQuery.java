/**
 * ProtocolQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolQuery  extends gr.scriptum.eprotocol.ws.endpoint.WsRequest  implements java.io.Serializable {
    private java.lang.String clientPass;

    private java.lang.Integer protocolId;

    public ProtocolQuery() {
    }

    public ProtocolQuery(
           java.lang.Integer clientId,
           java.lang.String clientPass,
           java.lang.Integer protocolId) {
        super(
            clientId);
        this.clientPass = clientPass;
        this.protocolId = protocolId;
    }


    /**
     * Gets the clientPass value for this ProtocolQuery.
     * 
     * @return clientPass
     */
    public java.lang.String getClientPass() {
        return clientPass;
    }


    /**
     * Sets the clientPass value for this ProtocolQuery.
     * 
     * @param clientPass
     */
    public void setClientPass(java.lang.String clientPass) {
        this.clientPass = clientPass;
    }


    /**
     * Gets the protocolId value for this ProtocolQuery.
     * 
     * @return protocolId
     */
    public java.lang.Integer getProtocolId() {
        return protocolId;
    }


    /**
     * Sets the protocolId value for this ProtocolQuery.
     * 
     * @param protocolId
     */
    public void setProtocolId(java.lang.Integer protocolId) {
        this.protocolId = protocolId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProtocolQuery)) return false;
        ProtocolQuery other = (ProtocolQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.clientPass==null && other.getClientPass()==null) || 
             (this.clientPass!=null &&
              this.clientPass.equals(other.getClientPass()))) &&
            ((this.protocolId==null && other.getProtocolId()==null) || 
             (this.protocolId!=null &&
              this.protocolId.equals(other.getProtocolId())));
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
        if (getClientPass() != null) {
            _hashCode += getClientPass().hashCode();
        }
        if (getProtocolId() != null) {
            _hashCode += getProtocolId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProtocolQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "protocolQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientPass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientPass"));
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
