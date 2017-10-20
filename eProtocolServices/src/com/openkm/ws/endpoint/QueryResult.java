/**
 * QueryResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class QueryResult  implements java.io.Serializable {
    private com.openkm.ws.endpoint.Document attachment;

    private com.openkm.ws.endpoint.Document document;

    private java.lang.String excerpt;

    private com.openkm.ws.endpoint.Folder folder;

    private com.openkm.ws.endpoint.Mail mail;

    private long score;

    public QueryResult() {
    }

    public QueryResult(
           com.openkm.ws.endpoint.Document attachment,
           com.openkm.ws.endpoint.Document document,
           java.lang.String excerpt,
           com.openkm.ws.endpoint.Folder folder,
           com.openkm.ws.endpoint.Mail mail,
           long score) {
           this.attachment = attachment;
           this.document = document;
           this.excerpt = excerpt;
           this.folder = folder;
           this.mail = mail;
           this.score = score;
    }


    /**
     * Gets the attachment value for this QueryResult.
     * 
     * @return attachment
     */
    public com.openkm.ws.endpoint.Document getAttachment() {
        return attachment;
    }


    /**
     * Sets the attachment value for this QueryResult.
     * 
     * @param attachment
     */
    public void setAttachment(com.openkm.ws.endpoint.Document attachment) {
        this.attachment = attachment;
    }


    /**
     * Gets the document value for this QueryResult.
     * 
     * @return document
     */
    public com.openkm.ws.endpoint.Document getDocument() {
        return document;
    }


    /**
     * Sets the document value for this QueryResult.
     * 
     * @param document
     */
    public void setDocument(com.openkm.ws.endpoint.Document document) {
        this.document = document;
    }


    /**
     * Gets the excerpt value for this QueryResult.
     * 
     * @return excerpt
     */
    public java.lang.String getExcerpt() {
        return excerpt;
    }


    /**
     * Sets the excerpt value for this QueryResult.
     * 
     * @param excerpt
     */
    public void setExcerpt(java.lang.String excerpt) {
        this.excerpt = excerpt;
    }


    /**
     * Gets the folder value for this QueryResult.
     * 
     * @return folder
     */
    public com.openkm.ws.endpoint.Folder getFolder() {
        return folder;
    }


    /**
     * Sets the folder value for this QueryResult.
     * 
     * @param folder
     */
    public void setFolder(com.openkm.ws.endpoint.Folder folder) {
        this.folder = folder;
    }


    /**
     * Gets the mail value for this QueryResult.
     * 
     * @return mail
     */
    public com.openkm.ws.endpoint.Mail getMail() {
        return mail;
    }


    /**
     * Sets the mail value for this QueryResult.
     * 
     * @param mail
     */
    public void setMail(com.openkm.ws.endpoint.Mail mail) {
        this.mail = mail;
    }


    /**
     * Gets the score value for this QueryResult.
     * 
     * @return score
     */
    public long getScore() {
        return score;
    }


    /**
     * Sets the score value for this QueryResult.
     * 
     * @param score
     */
    public void setScore(long score) {
        this.score = score;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryResult)) return false;
        QueryResult other = (QueryResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attachment==null && other.getAttachment()==null) || 
             (this.attachment!=null &&
              this.attachment.equals(other.getAttachment()))) &&
            ((this.document==null && other.getDocument()==null) || 
             (this.document!=null &&
              this.document.equals(other.getDocument()))) &&
            ((this.excerpt==null && other.getExcerpt()==null) || 
             (this.excerpt!=null &&
              this.excerpt.equals(other.getExcerpt()))) &&
            ((this.folder==null && other.getFolder()==null) || 
             (this.folder!=null &&
              this.folder.equals(other.getFolder()))) &&
            ((this.mail==null && other.getMail()==null) || 
             (this.mail!=null &&
              this.mail.equals(other.getMail()))) &&
            this.score == other.getScore();
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
        if (getAttachment() != null) {
            _hashCode += getAttachment().hashCode();
        }
        if (getDocument() != null) {
            _hashCode += getDocument().hashCode();
        }
        if (getExcerpt() != null) {
            _hashCode += getExcerpt().hashCode();
        }
        if (getFolder() != null) {
            _hashCode += getFolder().hashCode();
        }
        if (getMail() != null) {
            _hashCode += getMail().hashCode();
        }
        _hashCode += new Long(getScore()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attachment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("document");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excerpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "excerpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("folder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.openkm.com", "folder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "folder"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.openkm.com", "mail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "mail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("score");
        elemField.setXmlName(new javax.xml.namespace.QName("", "score"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
