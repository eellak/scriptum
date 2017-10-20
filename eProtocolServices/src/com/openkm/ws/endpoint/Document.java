/**
 * Document.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class Document  extends com.openkm.ws.endpoint.Node  implements java.io.Serializable {
    private com.openkm.ws.endpoint.Version actualVersion;

    private boolean checkedOut;

    private boolean convertibleToPdf;

    private boolean convertibleToSwf;

    private java.lang.String description;

    private java.lang.String language;

    private java.util.Calendar lastModified;

    private com.openkm.ws.endpoint.LockInfo lockInfo;

    private boolean locked;

    private java.lang.String mimeType;

    private java.lang.String title;

    public Document() {
    }

    public Document(
           java.lang.String author,
           com.openkm.ws.endpoint.Folder[] categories,
           java.util.Calendar created,
           java.lang.String[] keywords,
           com.openkm.ws.endpoint.Note[] notes,
           java.lang.String path,
           int permissions,
           boolean subscribed,
           java.lang.String[] subscriptors,
           java.lang.String uuid,
           com.openkm.ws.endpoint.Version actualVersion,
           boolean checkedOut,
           boolean convertibleToPdf,
           boolean convertibleToSwf,
           java.lang.String description,
           java.lang.String language,
           java.util.Calendar lastModified,
           com.openkm.ws.endpoint.LockInfo lockInfo,
           boolean locked,
           java.lang.String mimeType,
           java.lang.String title) {
        super(
            author,
            categories,
            created,
            keywords,
            notes,
            path,
            permissions,
            subscribed,
            subscriptors,
            uuid);
        this.actualVersion = actualVersion;
        this.checkedOut = checkedOut;
        this.convertibleToPdf = convertibleToPdf;
        this.convertibleToSwf = convertibleToSwf;
        this.description = description;
        this.language = language;
        this.lastModified = lastModified;
        this.lockInfo = lockInfo;
        this.locked = locked;
        this.mimeType = mimeType;
        this.title = title;
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
     * Gets the description value for this Document.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Document.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
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
    public com.openkm.ws.endpoint.LockInfo getLockInfo() {
        return lockInfo;
    }


    /**
     * Sets the lockInfo value for this Document.
     * 
     * @param lockInfo
     */
    public void setLockInfo(com.openkm.ws.endpoint.LockInfo lockInfo) {
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
     * Gets the title value for this Document.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Document.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
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
        _equals = super.equals(obj) && 
            ((this.actualVersion==null && other.getActualVersion()==null) || 
             (this.actualVersion!=null &&
              this.actualVersion.equals(other.getActualVersion()))) &&
            this.checkedOut == other.isCheckedOut() &&
            this.convertibleToPdf == other.isConvertibleToPdf() &&
            this.convertibleToSwf == other.isConvertibleToSwf() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
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
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        if (getActualVersion() != null) {
            _hashCode += getActualVersion().hashCode();
        }
        _hashCode += (isCheckedOut() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isConvertibleToPdf() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isConvertibleToSwf() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
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
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Document.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actualVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "version"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.openkm.com", "lockInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "lockInfo"));
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
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
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
