/**
 * QueryParams.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class QueryParams  implements java.io.Serializable {
    private java.lang.String author;

    private java.lang.String[] categories;

    private java.lang.String content;

    private boolean dashboard;

    private long domain;

    private int id;

    private java.lang.String[] keywords;

    private java.util.Calendar lastModifiedFrom;

    private java.util.Calendar lastModifiedTo;

    private java.lang.String mailFrom;

    private java.lang.String mailSubject;

    private java.lang.String mailTo;

    private java.lang.String mimeType;

    private java.lang.String name;

    private java.lang.String operator;

    private java.lang.String path;

    private com.openkm.ws.endpoint.QueryParamsPropertiesEntry[] properties;

    private java.lang.String queryName;

    private java.lang.String user;

    public QueryParams() {
    }

    public QueryParams(
           java.lang.String author,
           java.lang.String[] categories,
           java.lang.String content,
           boolean dashboard,
           long domain,
           int id,
           java.lang.String[] keywords,
           java.util.Calendar lastModifiedFrom,
           java.util.Calendar lastModifiedTo,
           java.lang.String mailFrom,
           java.lang.String mailSubject,
           java.lang.String mailTo,
           java.lang.String mimeType,
           java.lang.String name,
           java.lang.String operator,
           java.lang.String path,
           com.openkm.ws.endpoint.QueryParamsPropertiesEntry[] properties,
           java.lang.String queryName,
           java.lang.String user) {
           this.author = author;
           this.categories = categories;
           this.content = content;
           this.dashboard = dashboard;
           this.domain = domain;
           this.id = id;
           this.keywords = keywords;
           this.lastModifiedFrom = lastModifiedFrom;
           this.lastModifiedTo = lastModifiedTo;
           this.mailFrom = mailFrom;
           this.mailSubject = mailSubject;
           this.mailTo = mailTo;
           this.mimeType = mimeType;
           this.name = name;
           this.operator = operator;
           this.path = path;
           this.properties = properties;
           this.queryName = queryName;
           this.user = user;
    }


    /**
     * Gets the author value for this QueryParams.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this QueryParams.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the categories value for this QueryParams.
     * 
     * @return categories
     */
    public java.lang.String[] getCategories() {
        return categories;
    }


    /**
     * Sets the categories value for this QueryParams.
     * 
     * @param categories
     */
    public void setCategories(java.lang.String[] categories) {
        this.categories = categories;
    }

    public java.lang.String getCategories(int i) {
        return this.categories[i];
    }

    public void setCategories(int i, java.lang.String _value) {
        this.categories[i] = _value;
    }


    /**
     * Gets the content value for this QueryParams.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this QueryParams.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the dashboard value for this QueryParams.
     * 
     * @return dashboard
     */
    public boolean isDashboard() {
        return dashboard;
    }


    /**
     * Sets the dashboard value for this QueryParams.
     * 
     * @param dashboard
     */
    public void setDashboard(boolean dashboard) {
        this.dashboard = dashboard;
    }


    /**
     * Gets the domain value for this QueryParams.
     * 
     * @return domain
     */
    public long getDomain() {
        return domain;
    }


    /**
     * Sets the domain value for this QueryParams.
     * 
     * @param domain
     */
    public void setDomain(long domain) {
        this.domain = domain;
    }


    /**
     * Gets the id value for this QueryParams.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this QueryParams.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the keywords value for this QueryParams.
     * 
     * @return keywords
     */
    public java.lang.String[] getKeywords() {
        return keywords;
    }


    /**
     * Sets the keywords value for this QueryParams.
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
     * Gets the lastModifiedFrom value for this QueryParams.
     * 
     * @return lastModifiedFrom
     */
    public java.util.Calendar getLastModifiedFrom() {
        return lastModifiedFrom;
    }


    /**
     * Sets the lastModifiedFrom value for this QueryParams.
     * 
     * @param lastModifiedFrom
     */
    public void setLastModifiedFrom(java.util.Calendar lastModifiedFrom) {
        this.lastModifiedFrom = lastModifiedFrom;
    }


    /**
     * Gets the lastModifiedTo value for this QueryParams.
     * 
     * @return lastModifiedTo
     */
    public java.util.Calendar getLastModifiedTo() {
        return lastModifiedTo;
    }


    /**
     * Sets the lastModifiedTo value for this QueryParams.
     * 
     * @param lastModifiedTo
     */
    public void setLastModifiedTo(java.util.Calendar lastModifiedTo) {
        this.lastModifiedTo = lastModifiedTo;
    }


    /**
     * Gets the mailFrom value for this QueryParams.
     * 
     * @return mailFrom
     */
    public java.lang.String getMailFrom() {
        return mailFrom;
    }


    /**
     * Sets the mailFrom value for this QueryParams.
     * 
     * @param mailFrom
     */
    public void setMailFrom(java.lang.String mailFrom) {
        this.mailFrom = mailFrom;
    }


    /**
     * Gets the mailSubject value for this QueryParams.
     * 
     * @return mailSubject
     */
    public java.lang.String getMailSubject() {
        return mailSubject;
    }


    /**
     * Sets the mailSubject value for this QueryParams.
     * 
     * @param mailSubject
     */
    public void setMailSubject(java.lang.String mailSubject) {
        this.mailSubject = mailSubject;
    }


    /**
     * Gets the mailTo value for this QueryParams.
     * 
     * @return mailTo
     */
    public java.lang.String getMailTo() {
        return mailTo;
    }


    /**
     * Sets the mailTo value for this QueryParams.
     * 
     * @param mailTo
     */
    public void setMailTo(java.lang.String mailTo) {
        this.mailTo = mailTo;
    }


    /**
     * Gets the mimeType value for this QueryParams.
     * 
     * @return mimeType
     */
    public java.lang.String getMimeType() {
        return mimeType;
    }


    /**
     * Sets the mimeType value for this QueryParams.
     * 
     * @param mimeType
     */
    public void setMimeType(java.lang.String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * Gets the name value for this QueryParams.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this QueryParams.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the operator value for this QueryParams.
     * 
     * @return operator
     */
    public java.lang.String getOperator() {
        return operator;
    }


    /**
     * Sets the operator value for this QueryParams.
     * 
     * @param operator
     */
    public void setOperator(java.lang.String operator) {
        this.operator = operator;
    }


    /**
     * Gets the path value for this QueryParams.
     * 
     * @return path
     */
    public java.lang.String getPath() {
        return path;
    }


    /**
     * Sets the path value for this QueryParams.
     * 
     * @param path
     */
    public void setPath(java.lang.String path) {
        this.path = path;
    }


    /**
     * Gets the properties value for this QueryParams.
     * 
     * @return properties
     */
    public com.openkm.ws.endpoint.QueryParamsPropertiesEntry[] getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this QueryParams.
     * 
     * @param properties
     */
    public void setProperties(com.openkm.ws.endpoint.QueryParamsPropertiesEntry[] properties) {
        this.properties = properties;
    }


    /**
     * Gets the queryName value for this QueryParams.
     * 
     * @return queryName
     */
    public java.lang.String getQueryName() {
        return queryName;
    }


    /**
     * Sets the queryName value for this QueryParams.
     * 
     * @param queryName
     */
    public void setQueryName(java.lang.String queryName) {
        this.queryName = queryName;
    }


    /**
     * Gets the user value for this QueryParams.
     * 
     * @return user
     */
    public java.lang.String getUser() {
        return user;
    }


    /**
     * Sets the user value for this QueryParams.
     * 
     * @param user
     */
    public void setUser(java.lang.String user) {
        this.user = user;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryParams)) return false;
        QueryParams other = (QueryParams) obj;
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
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            this.dashboard == other.isDashboard() &&
            this.domain == other.getDomain() &&
            this.id == other.getId() &&
            ((this.keywords==null && other.getKeywords()==null) || 
             (this.keywords!=null &&
              java.util.Arrays.equals(this.keywords, other.getKeywords()))) &&
            ((this.lastModifiedFrom==null && other.getLastModifiedFrom()==null) || 
             (this.lastModifiedFrom!=null &&
              this.lastModifiedFrom.equals(other.getLastModifiedFrom()))) &&
            ((this.lastModifiedTo==null && other.getLastModifiedTo()==null) || 
             (this.lastModifiedTo!=null &&
              this.lastModifiedTo.equals(other.getLastModifiedTo()))) &&
            ((this.mailFrom==null && other.getMailFrom()==null) || 
             (this.mailFrom!=null &&
              this.mailFrom.equals(other.getMailFrom()))) &&
            ((this.mailSubject==null && other.getMailSubject()==null) || 
             (this.mailSubject!=null &&
              this.mailSubject.equals(other.getMailSubject()))) &&
            ((this.mailTo==null && other.getMailTo()==null) || 
             (this.mailTo!=null &&
              this.mailTo.equals(other.getMailTo()))) &&
            ((this.mimeType==null && other.getMimeType()==null) || 
             (this.mimeType!=null &&
              this.mimeType.equals(other.getMimeType()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.operator==null && other.getOperator()==null) || 
             (this.operator!=null &&
              this.operator.equals(other.getOperator()))) &&
            ((this.path==null && other.getPath()==null) || 
             (this.path!=null &&
              this.path.equals(other.getPath()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              java.util.Arrays.equals(this.properties, other.getProperties()))) &&
            ((this.queryName==null && other.getQueryName()==null) || 
             (this.queryName!=null &&
              this.queryName.equals(other.getQueryName()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser())));
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
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        _hashCode += (isDashboard() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += new Long(getDomain()).hashCode();
        _hashCode += getId();
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
        if (getLastModifiedFrom() != null) {
            _hashCode += getLastModifiedFrom().hashCode();
        }
        if (getLastModifiedTo() != null) {
            _hashCode += getLastModifiedTo().hashCode();
        }
        if (getMailFrom() != null) {
            _hashCode += getMailFrom().hashCode();
        }
        if (getMailSubject() != null) {
            _hashCode += getMailSubject().hashCode();
        }
        if (getMailTo() != null) {
            _hashCode += getMailTo().hashCode();
        }
        if (getMimeType() != null) {
            _hashCode += getMimeType().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getOperator() != null) {
            _hashCode += getOperator().hashCode();
        }
        if (getPath() != null) {
            _hashCode += getPath().hashCode();
        }
        if (getProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProperties(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getQueryName() != null) {
            _hashCode += getQueryName().hashCode();
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryParams.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "queryParams"));
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
        elemField.setFieldName("dashboard");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dashboard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domain");
        elemField.setXmlName(new javax.xml.namespace.QName("", "domain"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("lastModifiedFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModifiedFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedTo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModifiedTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mailFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailSubject");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mailSubject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailTo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mailTo"));
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
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", ">>queryParams>properties>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user"));
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
