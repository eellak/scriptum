/**
 * Folder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class Folder  implements java.io.Serializable {
    private java.lang.String author;

    private java.util.Calendar created;

    private boolean hasChilds;

    private java.lang.String path;

    private byte permissions;

    private boolean subscribed;

    private java.lang.String[] subscriptors;

    private java.lang.String uuid;

    public Folder() {
    }

    public Folder(
           java.lang.String author,
           java.util.Calendar created,
           boolean hasChilds,
           java.lang.String path,
           byte permissions,
           boolean subscribed,
           java.lang.String[] subscriptors,
           java.lang.String uuid) {
           this.author = author;
           this.created = created;
           this.hasChilds = hasChilds;
           this.path = path;
           this.permissions = permissions;
           this.subscribed = subscribed;
           this.subscriptors = subscriptors;
           this.uuid = uuid;
    }


    /**
     * Gets the author value for this Folder.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this Folder.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the created value for this Folder.
     * 
     * @return created
     */
    public java.util.Calendar getCreated() {
        return created;
    }


    /**
     * Sets the created value for this Folder.
     * 
     * @param created
     */
    public void setCreated(java.util.Calendar created) {
        this.created = created;
    }


    /**
     * Gets the hasChilds value for this Folder.
     * 
     * @return hasChilds
     */
    public boolean isHasChilds() {
        return hasChilds;
    }


    /**
     * Sets the hasChilds value for this Folder.
     * 
     * @param hasChilds
     */
    public void setHasChilds(boolean hasChilds) {
        this.hasChilds = hasChilds;
    }


    /**
     * Gets the path value for this Folder.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this Folder.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the permissions value for this Folder.
     * 
     * @return permissions
     */
    public byte getPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this Folder.
     * 
     * @param permissions
     */
    public void setPermissions(byte permissions) {
        this.permissions = permissions;
    }


    /**
     * Gets the subscribed value for this Folder.
     * 
     * @return subscribed
     */
    public boolean isSubscribed() {
        return subscribed;
    }


    /**
     * Sets the subscribed value for this Folder.
     * 
     * @param subscribed
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }


    /**
     * Gets the subscriptors value for this Folder.
     * 
     * @return subscriptors
     */
    public java.lang.String[] getSubscriptors() {
        return subscriptors;
    }


    /**
     * Sets the subscriptors value for this Folder.
     * 
     * @param subscriptors
     */
    public void setSubscriptors(java.lang.String[] subscriptors) {
        this.subscriptors = subscriptors;
    }

    public java.lang.String getSubscriptors(int i) {
        return this.subscriptors[i];
    }

    public void setSubscriptors(int i, java.lang.String _value) {
        this.subscriptors[i] = _value;
    }


    /**
     * Gets the uuid value for this Folder.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this Folder.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Folder)) return false;
        Folder other = (Folder) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            this.hasChilds == other.isHasChilds() &&
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            this.permissions == other.getPermissions() &&
            this.subscribed == other.isSubscribed() &&
            ((this.subscriptors==null && other.getSubscriptors()==null) || 
             (this.subscriptors!=null &&
              java.util.Arrays.equals(this.subscriptors, other.getSubscriptors()))) &&
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAuthor() != null) {
            _hashCode += getAuthor().hashCode();
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        _hashCode += (isHasChilds() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        _hashCode += getPermissions();
        _hashCode += (isSubscribed() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSubscriptors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubscriptors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubscriptors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Folder.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hasChilds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hasChilds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("path");
        elemField.setXmlName(new javax.xml.namespace.QName("", "path"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissions");
        elemField.setXmlName(new javax.xml.namespace.QName("", "permissions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "byte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscribed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subscribed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subscriptors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
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
