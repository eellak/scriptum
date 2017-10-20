/**
 * Node.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class Node  implements java.io.Serializable {
    private java.lang.String author;

    private com.openkm.ws.endpoint.Folder[] categories;

    private java.util.Calendar created;

    private java.lang.String[] keywords;

    private com.openkm.ws.endpoint.Note[] notes;

    private java.lang.String path;

    private int permissions;

    private boolean subscribed;

    private java.lang.String[] subscriptors;

    private java.lang.String uuid;

    public Node() {
    }

    public Node(
           java.lang.String author,
           com.openkm.ws.endpoint.Folder[] categories,
           java.util.Calendar created,
           java.lang.String[] keywords,
           com.openkm.ws.endpoint.Note[] notes,
           java.lang.String path,
           int permissions,
           boolean subscribed,
           java.lang.String[] subscriptors,
           java.lang.String uuid) {
           this.author = author;
           this.categories = categories;
           this.created = created;
           this.keywords = keywords;
           this.notes = notes;
           this.path = path;
           this.permissions = permissions;
           this.subscribed = subscribed;
           this.subscriptors = subscriptors;
           this.uuid = uuid;
    }


    /**
     * Gets the author value for this Node.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this Node.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the categories value for this Node.
     * 
     * @return categories
     */
    public com.openkm.ws.endpoint.Folder[] getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this Node.
     * 
     * @param categories
     */
    public void setCategories(com.openkm.ws.endpoint.Folder[] categories) {
        this.categories = categories;
    }

    public com.openkm.ws.endpoint.Folder getCategories(int i) {
        return this.categories[i];
    }

    public void setCategories(int i, com.openkm.ws.endpoint.Folder _value) {
        this.categories[i] = _value;
    }


    /**
     * Gets the created value for this Node.
     * 
     * @return created
     */
    public java.util.Calendar getCreated() {
        return created;
    }


    /**
     * Sets the created value for this Node.
     * 
     * @param created
     */
    public void setCreated(java.util.Calendar created) {
        this.created = created;
    }


    /**
     * Gets the keywords value for this Node.
     * 
     * @return keywords
     */
    public java.lang.String[] getKeywords() {
        return keywords;
    }


    /**
     * Sets the keywords value for this Node.
     * 
     * @param keywords
     */
    public void setKeywords(java.lang.String[] keywords) {
        this.keywords = keywords;
    }

    public java.lang.String getKeywords(int i) {
        return this.keywords[i];
    }

    public void setKeywords(int i, java.lang.String _value) {
        this.keywords[i] = _value;
    }


    /**
     * Gets the notes value for this Node.
     * 
     * @return notes
     */
    public com.openkm.ws.endpoint.Note[] getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Node.
     * 
     * @param notes
     */
    public void setNotes(com.openkm.ws.endpoint.Note[] notes) {
        this.notes = notes;
    }

    public com.openkm.ws.endpoint.Note getNotes(int i) {
        return this.notes[i];
    }

    public void setNotes(int i, com.openkm.ws.endpoint.Note _value) {
        this.notes[i] = _value;
    }


    /**
     * Gets the path value for this Node.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this Node.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the permissions value for this Node.
     * 
     * @return permissions
     */
    public int getPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this Node.
     * 
     * @param permissions
     */
    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }


    /**
     * Gets the subscribed value for this Node.
     * 
     * @return subscribed
     */
    public boolean isSubscribed() {
        return subscribed;
    }


    /**
     * Sets the subscribed value for this Node.
     * 
     * @param subscribed
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }


    /**
     * Gets the subscriptors value for this Node.
     * 
     * @return subscriptors
     */
    public java.lang.String[] getSubscriptors() {
        return subscriptors;
    }


    /**
     * Sets the subscriptors value for this Node.
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
     * Gets the uuid value for this Node.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this Node.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
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
            ((this.categories==null && other.getCategories()==null) || 
             (this.categories!=null &&
              java.util.Arrays.equals(this.categories, other.getCategories()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.keywords==null && other.getKeywords()==null) || 
             (this.keywords!=null &&
              java.util.Arrays.equals(this.keywords, other.getKeywords()))) &&
            ((this.notes==null && other.getNotes()==null) || 
             (this.notes!=null &&
              java.util.Arrays.equals(this.notes, other.getNotes()))) &&
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
        if (getCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCategories(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getKeywords() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getKeywords());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getKeywords(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNotes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNotes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNotes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
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
        new org.apache.axis.description.TypeDesc(Node.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "node"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categories");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "folder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keywords");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "note"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
