/**
 * Mail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class Mail  extends com.openkm.ws.endpoint.Node  implements java.io.Serializable {
    private com.openkm.ws.endpoint.Document[] attachments;

    private java.lang.String[] bcc;

    private java.lang.String[] cc;

    private java.lang.String content;

    private java.lang.String from;

    private java.lang.String mimeType;

    private java.util.Calendar receivedDate;

    private java.lang.String[] reply;

    private java.util.Calendar sentDate;

    private long size;

    private java.lang.String subject;

    private java.lang.String[] to;

    public Mail() {
    }

    public Mail(
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
           com.openkm.ws.endpoint.Document[] attachments,
           java.lang.String[] bcc,
           java.lang.String[] cc,
           java.lang.String content,
           java.lang.String from,
           java.lang.String mimeType,
           java.util.Calendar receivedDate,
           java.lang.String[] reply,
           java.util.Calendar sentDate,
           long size,
           java.lang.String subject,
           java.lang.String[] to) {
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
        this.attachments = attachments;
        this.bcc = bcc;
        this.cc = cc;
        this.content = content;
        this.from = from;
        this.mimeType = mimeType;
        this.receivedDate = receivedDate;
        this.reply = reply;
        this.sentDate = sentDate;
        this.size = size;
        this.subject = subject;
        this.to = to;
    }


    /**
     * Gets the attachments value for this Mail.
     * 
     * @return attachments
     */
    public com.openkm.ws.endpoint.Document[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this Mail.
     * 
     * @param attachments
     */
    public void setAttachments(com.openkm.ws.endpoint.Document[] attachments) {
        this.attachments = attachments;
    }

    public com.openkm.ws.endpoint.Document getAttachments(int i) {
        return this.attachments[i];
    }

    public void setAttachments(int i, com.openkm.ws.endpoint.Document _value) {
        this.attachments[i] = _value;
    }


    /**
     * Gets the bcc value for this Mail.
     * 
     * @return bcc
     */
    public java.lang.String[] getBcc() {
        return bcc;
    }


    /**
     * Sets the bcc value for this Mail.
     * 
     * @param bcc
     */
    public void setBcc(java.lang.String[] bcc) {
        this.bcc = bcc;
    }

    public java.lang.String getBcc(int i) {
        return this.bcc[i];
    }

    public void setBcc(int i, java.lang.String _value) {
        this.bcc[i] = _value;
    }


    /**
     * Gets the cc value for this Mail.
     * 
     * @return cc
     */
    public java.lang.String[] getCc() {
        return cc;
    }


    /**
     * Sets the cc value for this Mail.
     * 
     * @param cc
     */
    public void setCc(java.lang.String[] cc) {
        this.cc = cc;
    }

    public java.lang.String getCc(int i) {
        return this.cc[i];
    }

    public void setCc(int i, java.lang.String _value) {
        this.cc[i] = _value;
    }


    /**
     * Gets the content value for this Mail.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this Mail.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the from value for this Mail.
     * 
     * @return from
     */
    public java.lang.String getFrom() {
        return from;
    }


    /**
     * Sets the from value for this Mail.
     * 
     * @param from
     */
    public void setFrom(java.lang.String from) {
        this.from = from;
    }


    /**
     * Gets the mimeType value for this Mail.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this Mail.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * Gets the receivedDate value for this Mail.
     * 
     * @return receivedDate
     */
    public java.util.Calendar getReceivedDate() {
        return receivedDate;
    }


    /**
     * Sets the receivedDate value for this Mail.
     * 
     * @param receivedDate
     */
    public void setReceivedDate(java.util.Calendar receivedDate) {
        this.receivedDate = receivedDate;
    }


    /**
     * Gets the reply value for this Mail.
     * 
     * @return reply
     */
    public java.lang.String[] getReply() {
        return reply;
    }


    /**
     * Sets the reply value for this Mail.
     * 
     * @param reply
     */
    public void setReply(java.lang.String[] reply) {
        this.reply = reply;
    }

    public java.lang.String getReply(int i) {
        return this.reply[i];
    }

    public void setReply(int i, java.lang.String _value) {
        this.reply[i] = _value;
    }


    /**
     * Gets the sentDate value for this Mail.
     * 
     * @return sentDate
     */
    public java.util.Calendar getSentDate() {
        return sentDate;
    }


    /**
     * Sets the sentDate value for this Mail.
     * 
     * @param sentDate
     */
    public void setSentDate(java.util.Calendar sentDate) {
        this.sentDate = sentDate;
    }


    /**
     * Gets the size value for this Mail.
     * 
     * @return size
     */
    public long getSize() {
        return size;
    }


    /**
     * Sets the size value for this Mail.
     * 
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }


    /**
     * Gets the subject value for this Mail.
     * 
     * @return subject
     */
    public java.lang.String getSubject() {
        return subject;
    }


    /**
     * Sets the subject value for this Mail.
     * 
     * @param subject
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }


    /**
     * Gets the to value for this Mail.
     * 
     * @return to
     */
    public java.lang.String[] getTo() {
        return to;
    }


    /**
     * Sets the to value for this Mail.
     * 
     * @param to
     */
    public void setTo(java.lang.String[] to) {
        this.to = to;
    }

    public java.lang.String getTo(int i) {
        return this.to[i];
    }

    public void setTo(int i, java.lang.String _value) {
        this.to[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Mail)) return false;
        Mail other = (Mail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attachments==null && other.getAttachments()==null) || 
             (this.attachments!=null &&
              java.util.Arrays.equals(this.attachments, other.getAttachments()))) &&
            ((this.bcc==null && other.getBcc()==null) || 
             (this.bcc!=null &&
              java.util.Arrays.equals(this.bcc, other.getBcc()))) &&
            ((this.cc==null && other.getCc()==null) || 
             (this.cc!=null &&
              java.util.Arrays.equals(this.cc, other.getCc()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.from==null && other.getFrom()==null) || 
             (this.from!=null &&
              this.from.equals(other.getFrom()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
            ((this.receivedDate==null && other.getReceivedDate()==null) || 
             (this.receivedDate!=null &&
              this.receivedDate.equals(other.getReceivedDate()))) &&
            ((this.reply==null && other.getReply()==null) || 
             (this.reply!=null &&
              java.util.Arrays.equals(this.reply, other.getReply()))) &&
            ((this.sentDate==null && other.getSentDate()==null) || 
             (this.sentDate!=null &&
              this.sentDate.equals(other.getSentDate()))) &&
            this.size == other.getSize() &&
            ((this.subject==null && other.getSubject()==null) || 
             (this.subject!=null &&
              this.subject.equals(other.getSubject()))) &&
            ((this.to==null && other.getTo()==null) || 
             (this.to!=null &&
              java.util.Arrays.equals(this.to, other.getTo())));
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
        if (getAttachments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBcc() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBcc());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBcc(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCc() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCc());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCc(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getFrom() != null) {
            _hashCode += getFrom().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        if (getReceivedDate() != null) {
            _hashCode += getReceivedDate().hashCode();
        }
        if (getReply() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReply());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReply(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSentDate() != null) {
            _hashCode += getSentDate().hashCode();
        }
        _hashCode += new Long(getSize()).hashCode();
        if (getSubject() != null) {
            _hashCode += getSubject().hashCode();
        }
        if (getTo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Mail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "mail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachments");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attachments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bcc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("", "content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("from");
        elemField.setXmlName(new javax.xml.namespace.QName("", "from"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("receivedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "receivedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reply");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reply"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("size");
        elemField.setXmlName(new javax.xml.namespace.QName("", "size"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("to");
        elemField.setXmlName(new javax.xml.namespace.QName("", "to"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
