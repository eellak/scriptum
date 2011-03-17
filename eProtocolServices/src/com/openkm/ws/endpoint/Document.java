/**
 * Document.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class Document  implements java.io.Serializable {
    private com.openkm.ws.endpoint.Version actualVersion;

    private java.lang.String author;

    private com.openkm.ws.endpoint.Folder[] categories;

    private boolean checkedOut;

    private boolean convertibleToPdf;

    private boolean convertibleToSwf;

    private java.util.Calendar created;

    private java.lang.String[] keywords;

    private java.lang.String language;

    private java.util.Calendar lastModified;

    private com.openkm.ws.endpoint.Lock lockInfo;

    private boolean locked;

    private java.lang.String mimeType;

    private com.openkm.ws.endpoint.Note[] notes;

    private java.lang.String path;

    private byte permissions;

    private boolean subscribed;

    private java.lang.String[] subscriptors;

    private java.lang.String uuid;

    public Document() {
    }

    public Document(
           com.openkm.ws.endpoint.Version actualVersion,
           java.lang.String author,
           com.openkm.ws.endpoint.Folder[] categories,
           boolean checkedOut,
           boolean convertibleToPdf,
           boolean convertibleToSwf,
           java.util.Calendar created,
           java.lang.String[] keywords,
           java.lang.String language,
           java.util.Calendar lastModified,
           com.openkm.ws.endpoint.Lock lockInfo,
           boolean locked,
           java.lang.String mimeType,
           com.openkm.ws.endpoint.Note[] notes,
           java.lang.String path,
           byte permissions,
           boolean subscribed,
           java.lang.String[] subscriptors,
           java.lang.String uuid) {
           this.actualVersion = actualVersion;
           this.author = author;
           this.categories = categories;
           this.checkedOut = checkedOut;
           this.convertibleToPdf = convertibleToPdf;
           this.convertibleToSwf = convertibleToSwf;
           this.created = created;
           this.keywords = keywords;
           this.language = language;
           this.lastModified = lastModified;
           this.lockInfo = lockInfo;
           this.locked = locked;
           this.mimeType = mimeType;
           this.notes = notes;
           this.path = path;
           this.permissions = permissions;
           this.subscribed = subscribed;
           this.subscriptors = subscriptors;
           this.uuid = uuid;
    }


    /**
     * Gets the actualVersion value for this Document.
     * 
     * @return actualVersion
     */
    public com.openkm.ws.endpoint.Version getActualVersion() {
        return actualVersion;
    }


    /**
     * Sets the actualVersion value for this Document.
     * 
     * @param actualVersion
     */
    public void setActualVersion(com.openkm.ws.endpoint.Version actualVersion) {
        this.actualVersion = actualVersion;
    }


    /**
     * Gets the author value for this Document.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this Document.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the categories value for this Document.
     * 
     * @return categories
     */
    public com.openkm.ws.endpoint.Folder[] getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this Document.
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
     * Gets the checkedOut value for this Document.
     * 
     * @return checkedOut
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }


    /**
     * Sets the checkedOut value for this Document.
     * 
     * @param checkedOut
     */
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }


    /**
     * Gets the convertibleToPdf value for this Document.
     * 
     * @return convertibleToPdf
     */
    public boolean isConvertibleToPdf() {
        return convertibleToPdf;
    }


    /**
     * Sets the convertibleToPdf value for this Document.
     * 
     * @param convertibleToPdf
     */
    public void setConvertibleToPdf(boolean convertibleToPdf) {
        this.convertibleToPdf = convertibleToPdf;
    }


    /**
     * Gets the convertibleToSwf value for this Document.
     * 
     * @return convertibleToSwf
     */
    public boolean isConvertibleToSwf() {
        return convertibleToSwf;
    }


    /**
     * Sets the convertibleToSwf value for this Document.
     * 
     * @param convertibleToSwf
     */
    public void setConvertibleToSwf(boolean convertibleToSwf) {
        this.convertibleToSwf = convertibleToSwf;
    }


    /**
     * Gets the created value for this Document.
     * 
     * @return created
     */
    public java.util.Calendar getCreated() {
        return created;
    }


    /**
     * Sets the created value for this Document.
     * 
     * @param created
     */
    public void setCreated(java.util.Calendar created) {
        this.created = created;
    }


    /**
     * Gets the keywords value for this Document.
     * 
     * @return keywords
     */
    public java.lang.String[] getKeywords() {
        return keywords;
    }


    /**
     * Sets the keywords value for this Document.
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
     * Gets the language value for this Document.
     * 
     * @return language
     */
    public java.lang.String getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this Document.
     * 
     * @param language
     */
    public void setLanguage(java.lang.String language) {
        this.language = language;
    }


    /**
     * Gets the lastModified value for this Document.
     * 
     * @return lastModified
     */
    public java.util.Calendar getLastModified() {
        return lastModified;
    }


    /**
     * Sets the lastModified value for this Document.
     * 
     * @param lastModified
     */
    public void setLastModified(java.util.Calendar lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * Gets the lockInfo value for this Document.
     * 
     * @return lockInfo
     */
    public com.openkm.ws.endpoint.Lock getLockInfo() {
        return lockInfo;
    }


    /**
     * Sets the lockInfo value for this Document.
     * 
     * @param lockInfo
     */
    public void setLockInfo(com.openkm.ws.endpoint.Lock lockInfo) {
        this.lockInfo = lockInfo;
    }


    /**
     * Gets the locked value for this Document.
     * 
     * @return locked
     */
    public boolean isLocked() {
        return locked;
    }


    /**
     * Sets the locked value for this Document.
     * 
     * @param locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }


    /**
     * Gets the mimeType value for this Document.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this Document.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * Gets the notes value for this Document.
     * 
     * @return notes
     */
    public com.openkm.ws.endpoint.Note[] getNotes() {
        return notes;
    }


    /**
     * Sets the notes value for this Document.
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
     * Gets the path value for this Document.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this Document.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the permissions value for this Document.
     * 
     * @return permissions
     */
    public byte getPermissions() {
        return permissions;
    }


    /**
     * Sets the permissions value for this Document.
     * 
     * @param permissions
     */
    public void setPermissions(byte permissions) {
        this.permissions = permissions;
    }


    /**
     * Gets the subscribed value for this Document.
     * 
     * @return subscribed
     */
    public boolean isSubscribed() {
        return subscribed;
    }


    /**
     * Sets the subscribed value for this Document.
     * 
     * @param subscribed
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }


    /**
     * Gets the subscriptors value for this Document.
     * 
     * @return subscriptors
     */
    public java.lang.String[] getSubscriptors() {
        return subscriptors;
    }


    /**
     * Sets the subscriptors value for this Document.
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
     * Gets the uuid value for this Document.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this Document.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Document)) return false;
        Document other = (Document) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.actualVersion==null && other.getActualVersion()==null) || 
             (this.actualVersion!=null &&
              this.actualVersion.equals(other.getActualVersion()))) &&
            ((this.author==null && other.getAuthor()==null) || 
             (this.author!=null &&
              this.author.equals(other.getAuthor()))) &&
            ((this.categories==null && other.getCategories()==null) || 
             (this.categories!=null &&
              java.util.Arrays.equals(this.categories, other.getCategories()))) &&
            this.checkedOut == other.isCheckedOut() &&
            this.convertibleToPdf == other.isConvertibleToPdf() &&
            this.convertibleToSwf == other.isConvertibleToSwf() &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.keywords==null && other.getKeywords()==null) || 
             (this.keywords!=null &&
              java.util.Arrays.equals(this.keywords, other.getKeywords()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.lastModified==null && other.getLastModified()==null) || 
             (this.lastModified!=null &&
              this.lastModified.equals(other.getLastModified()))) &&
            ((this.lockInfo==null && other.getLockInfo()==null) || 
             (this.lockInfo!=null &&
              this.lockInfo.equals(other.getLockInfo()))) &&
            this.locked == other.isLocked() &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
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
        if (getActualVersion() != null) {
            _hashCode += getActualVersion().hashCode();
        }
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
        _hashCode += (isCheckedOut() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isConvertibleToPdf() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isConvertibleToSwf() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getLastModified() != null) {
            _hashCode += getLastModified().hashCode();
        }
        if (getLockInfo() != null) {
            _hashCode += getLockInfo().hashCode();
        }
        _hashCode += (isLocked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
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
        new org.apache.axis.description.TypeDesc(Document.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actualVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "version"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categories");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkedOut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checkedOut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("convertibleToPdf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "convertibleToPdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("convertibleToSwf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "convertibleToSwf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
        elemField.setFieldName("keywords");
        elemField.setXmlName(new javax.xml.namespace.QName("", "keywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("", "language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModified");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModified"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lockInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "lock"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locked");
        elemField.setXmlName(new javax.xml.namespace.QName("", "locked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mimeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mimeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "note"));
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
