/**
 * OKMDocumentBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class OKMDocumentBindingStub extends org.apache.axis.client.Stub implements com.openkm.ws.endpoint.OKMDocument {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[25];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cancelCheckout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("checkin");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "comment"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "version"));
        oper.setReturnClass(com.openkm.ws.endpoint.Version.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"),
                      "com.openkm.ws.endpoint.VersionException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("checkout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"), com.openkm.ws.endpoint.Document.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "content"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"),
                      "com.openkm.ws.endpoint.VirusDetectedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"),
                      "com.openkm.ws.endpoint.ItemExistsException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"),
                      "com.openkm.ws.endpoint.UserQuotaExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"),
                      "com.openkm.ws.endpoint.FileSizeExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UnsupportedMimeTypeException"),
                      "com.openkm.ws.endpoint.UnsupportedMimeTypeException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UnsupportedMimeTypeException"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createSimple");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "content"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"),
                      "com.openkm.ws.endpoint.VirusDetectedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"),
                      "com.openkm.ws.endpoint.ItemExistsException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"),
                      "com.openkm.ws.endpoint.UserQuotaExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"),
                      "com.openkm.ws.endpoint.FileSizeExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UnsupportedMimeTypeException"),
                      "com.openkm.ws.endpoint.UnsupportedMimeTypeException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UnsupportedMimeTypeException"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("forceCancelCheckout");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("forceUnlock");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChilds");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "documentArray"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getContent");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "checkout"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        oper.setReturnClass(byte[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getContentByVersion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "versionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        oper.setReturnClass(byte[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPath");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getProperties");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVersionHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "versionArray"));
        oper.setReturnClass(com.openkm.ws.endpoint.Version[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "item"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVersionHistorySize");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("isValid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("lock");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("move");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"),
                      "com.openkm.ws.endpoint.ItemExistsException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("purge");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("purgeVersionHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("rename");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "newName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"));
        oper.setReturnClass(com.openkm.ws.endpoint.Document.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"),
                      "com.openkm.ws.endpoint.ItemExistsException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("restoreVersion");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "versionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setContent");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "content"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"),
                      "com.openkm.ws.endpoint.VirusDetectedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"),
                      "com.openkm.ws.endpoint.IOException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"),
                      "com.openkm.ws.endpoint.VersionException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"),
                      "com.openkm.ws.endpoint.UserQuotaExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"),
                      "com.openkm.ws.endpoint.FileSizeExceededException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setProperties");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document"), com.openkm.ws.endpoint.Document.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"),
                      "com.openkm.ws.endpoint.VersionException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unlock");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "docPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"),
                      "com.openkm.ws.endpoint.PathNotFoundException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"),
                      "com.openkm.ws.endpoint.RepositoryException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"),
                      "com.openkm.ws.endpoint.LockException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"),
                      "com.openkm.ws.endpoint.AccessDeniedException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"),
                      "com.openkm.ws.endpoint.DatabaseException",
                      new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException"), 
                      true
                     ));
        _operations[24] = oper;

    }

    public OKMDocumentBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public OKMDocumentBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public OKMDocumentBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "AccessDeniedException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.AccessDeniedException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "DatabaseException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.DatabaseException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "documentArray");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Document[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "document");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "FileSizeExceededException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.FileSizeExceededException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "IOException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.IOException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.ItemExistsException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "lock");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Lock.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.LockException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "note");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Note.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "PathNotFoundException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.PathNotFoundException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "RepositoryException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.RepositoryException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UnsupportedMimeTypeException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.UnsupportedMimeTypeException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "UserQuotaExceededException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.UserQuotaExceededException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "version");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Version.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "versionArray");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Version[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "version");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VersionException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.VersionException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "VirusDetectedException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.VirusDetectedException.class;
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

    public void cancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "cancelCheckout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public com.openkm.ws.endpoint.Version checkin(java.lang.String token, java.lang.String docPath, java.lang.String comment) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "checkin"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, comment});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Version) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Version) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Version.class);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VersionException) {
              throw (com.openkm.ws.endpoint.VersionException) axisFaultException.detail;
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

    public void checkout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "checkout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public com.openkm.ws.endpoint.Document create(java.lang.String token, com.openkm.ws.endpoint.Document doc, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.UnsupportedMimeTypeException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "create"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, doc, content});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Document) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Document.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VirusDetectedException) {
              throw (com.openkm.ws.endpoint.VirusDetectedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ItemExistsException) {
              throw (com.openkm.ws.endpoint.ItemExistsException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.UserQuotaExceededException) {
              throw (com.openkm.ws.endpoint.UserQuotaExceededException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.FileSizeExceededException) {
              throw (com.openkm.ws.endpoint.FileSizeExceededException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.UnsupportedMimeTypeException) {
              throw (com.openkm.ws.endpoint.UnsupportedMimeTypeException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.Document createSimple(java.lang.String token, java.lang.String docPath, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.UnsupportedMimeTypeException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "createSimple"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, content});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Document) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Document.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VirusDetectedException) {
              throw (com.openkm.ws.endpoint.VirusDetectedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ItemExistsException) {
              throw (com.openkm.ws.endpoint.ItemExistsException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.UserQuotaExceededException) {
              throw (com.openkm.ws.endpoint.UserQuotaExceededException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.FileSizeExceededException) {
              throw (com.openkm.ws.endpoint.FileSizeExceededException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.AccessDeniedException) {
              throw (com.openkm.ws.endpoint.AccessDeniedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.UnsupportedMimeTypeException) {
              throw (com.openkm.ws.endpoint.UnsupportedMimeTypeException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public void delete(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "delete"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public void forceCancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "forceCancelCheckout"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public void forceUnlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "forceUnlock"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public com.openkm.ws.endpoint.Document[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getChilds"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public byte[] getContent(java.lang.String token, java.lang.String docPath, boolean checkout) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getContent"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, new java.lang.Boolean(checkout)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (byte[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (byte[]) org.apache.axis.utils.JavaUtils.convert(_resp, byte[].class);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public byte[] getContentByVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getContentByVersion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, versionId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (byte[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (byte[]) org.apache.axis.utils.JavaUtils.convert(_resp, byte[].class);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getPath"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, uuid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
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

    public com.openkm.ws.endpoint.Document getProperties(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getProperties"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Document) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Document.class);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.openkm.ws.endpoint.Version[] getVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getVersionHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Version[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Version[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Version[].class);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public long getVersionHistorySize(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getVersionHistorySize"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.DatabaseException) {
              throw (com.openkm.ws.endpoint.DatabaseException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public boolean isValid(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "isValid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
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

    public void lock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "lock"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public void move(java.lang.String token, java.lang.String docPath, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "move"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, fldPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ItemExistsException) {
              throw (com.openkm.ws.endpoint.ItemExistsException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

    public void purge(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "purge"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
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

    public void purgeVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "purgeVersionHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
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

    public com.openkm.ws.endpoint.Document rename(java.lang.String token, java.lang.String docPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "rename"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, newName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Document) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Document) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Document.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.ItemExistsException) {
              throw (com.openkm.ws.endpoint.ItemExistsException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
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

    public void restoreVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "restoreVersion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, versionId});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
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

    public void setContent(java.lang.String token, java.lang.String docPath, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "setContent"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath, content});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VirusDetectedException) {
              throw (com.openkm.ws.endpoint.VirusDetectedException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.RepositoryException) {
              throw (com.openkm.ws.endpoint.RepositoryException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.IOException) {
              throw (com.openkm.ws.endpoint.IOException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VersionException) {
              throw (com.openkm.ws.endpoint.VersionException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.UserQuotaExceededException) {
              throw (com.openkm.ws.endpoint.UserQuotaExceededException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.FileSizeExceededException) {
              throw (com.openkm.ws.endpoint.FileSizeExceededException) axisFaultException.detail;
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

    public void setProperties(java.lang.String token, com.openkm.ws.endpoint.Document doc) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "setProperties"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, doc});

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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.PathNotFoundException) {
              throw (com.openkm.ws.endpoint.PathNotFoundException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.VersionException) {
              throw (com.openkm.ws.endpoint.VersionException) axisFaultException.detail;
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

    public void unlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "unlock"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, docPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
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
        if (axisFaultException.detail instanceof com.openkm.ws.endpoint.LockException) {
              throw (com.openkm.ws.endpoint.LockException) axisFaultException.detail;
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

}
