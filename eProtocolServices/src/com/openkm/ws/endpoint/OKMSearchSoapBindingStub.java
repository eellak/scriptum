/**
 * OKMSearchSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class OKMSearchSoapBindingStub extends org.apache.axis.client.Stub implements com.openkm.ws.endpoint.OKMSearch_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[14];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "params"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"), com.openkm.ws.endpoint.QueryParams.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findByName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "name"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "qpId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryParams.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "qpId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateSearch");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "params"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"), com.openkm.ws.endpoint.QueryParams.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findMoreLikeThis");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "max"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "resultSet"));
        oper.setReturnClass(com.openkm.ws.endpoint.ResultSet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("find");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "params"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"), com.openkm.ws.endpoint.QueryParams.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findByContent");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "content"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCategorizedDocuments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "document"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllSearchs");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryParams[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findByKeywords");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "keywords"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult"));
        oper.setReturnClass(com.openkm.ws.endpoint.QueryResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findSimpleQueryPaginated");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "statement"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "offset"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "limit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "resultSet"));
        oper.setReturnClass(com.openkm.ws.endpoint.ResultSet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getKeywordMap");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "filter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "integerPair"));
        oper.setReturnClass(com.openkm.ws.endpoint.IntegerPair[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findPaginated");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "params"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams"), com.openkm.ws.endpoint.QueryParams.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "offset"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "limit"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.openkm.com", "resultSet"));
        oper.setReturnClass(com.openkm.ws.endpoint.ResultSet.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"),
                      "com.openkm.ws.endpoint.ParseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException"), 
                      true
                     ));
        _operations[13] = oper;

    }

    public OKMSearchSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public OKMSearchSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public OKMSearchSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.openkm.com", ">>queryParams>properties>entry");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.QueryParamsPropertiesEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", ">queryParams>properties");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.QueryParamsPropertiesEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.openkm.com", ">>queryParams>properties>entry");
            qName2 = new javax.xml.namespace.QName("", "entry");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "AccessDeniedException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.AccessDeniedException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "DatabaseException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.DatabaseException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "document");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "folder");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "integerPair");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.IntegerPair.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "IOException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.IOException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "lockInfo");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.LockInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "mail");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Mail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "node");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Node.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "note");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Note.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "ParseException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.ParseException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "PathNotFoundException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.PathNotFoundException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "queryParams");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.QueryParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "queryResult");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.QueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "RepositoryException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.RepositoryException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "resultSet");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.ResultSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.openkm.com", "version");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Version.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public long saveSearch(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "saveSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, params});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryResult[] findByName(java.lang.String token, java.lang.String name) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findByName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, name});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryParams getSearch(java.lang.String token, int qpId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "getSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, new java.lang.Integer(qpId)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryParams) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryParams) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryParams.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public void deleteSearch(java.lang.String token, int qpId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "deleteSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, new java.lang.Integer(qpId)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public void updateSearch(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "updateSearch"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, params});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.ResultSet findMoreLikeThis(java.lang.String token, java.lang.String uuid, int max) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findMoreLikeThis"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, uuid, new java.lang.Integer(max)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.ResultSet) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.ResultSet) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.ResultSet.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryResult[] find(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "find"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, params});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryResult[] findByContent(java.lang.String token, java.lang.String content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findByContent"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, content});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.Document[] getCategorizedDocuments(java.lang.String token, java.lang.String categoryId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "getCategorizedDocuments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, categoryId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Document[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Document[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Document[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryParams[] getAllSearchs(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "getAllSearchs"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryParams[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryParams[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryParams[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.QueryResult[] findByKeywords(java.lang.String token, java.lang.String[] keywords) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findByKeywords"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, keywords});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.QueryResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.QueryResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.QueryResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.ResultSet findSimpleQueryPaginated(java.lang.String token, java.lang.String statement, int offset, int limit) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findSimpleQueryPaginated"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, statement, new java.lang.Integer(offset), new java.lang.Integer(limit)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.ResultSet) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.ResultSet) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.ResultSet.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.IntegerPair[] getKeywordMap(java.lang.String token, java.lang.String[] filter) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "getKeywordMap"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, filter});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.IntegerPair[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.IntegerPair[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.IntegerPair[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.ResultSet findPaginated(java.lang.String token, com.openkm.ws.endpoint.QueryParams params, int offset, int limit) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.openkm.com", "findPaginated"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, params, new java.lang.Integer(offset), new java.lang.Integer(limit)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.ResultSet) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.ResultSet) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.ResultSet.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ParseException) {
              throw (com.openkm.ws.endpoint.ParseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
