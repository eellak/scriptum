/**
 * OKMFolderBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class OKMFolderBindingStub extends org.apache.axis.client.Stub implements com.openkm.ws.endpoint.OKMFolder {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fld"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"), com.openkm.ws.endpoint.Folder.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        oper.setReturnClass(com.openkm.ws.endpoint.Folder.class);
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
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createSimple");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        oper.setReturnClass(com.openkm.ws.endpoint.Folder.class);
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
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
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
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getChilds");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folderArray"));
        oper.setReturnClass(com.openkm.ws.endpoint.Folder[].class);
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
        _operations[3] = oper;

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
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getProperties");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        oper.setReturnClass(com.openkm.ws.endpoint.Folder.class);
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
        oper.setName("isValid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
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
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("move");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dstPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
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
        oper.setName("rename");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "fldPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "newName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder"));
        oper.setReturnClass(com.openkm.ws.endpoint.Folder.class);
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
        _operations[8] = oper;

    }

    public OKMFolderBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public OKMFolderBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public OKMFolderBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folderArray");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.Folder[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "folder");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "ItemExistsException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.ItemExistsException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "LockException");
            cachedSerQNames.add(qName);
            cls = com.openkm.ws.endpoint.LockException.class;
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

    public com.openkm.ws.endpoint.Folder create(java.lang.String token, com.openkm.ws.endpoint.Folder fld) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "create"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fld});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Folder.class);
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

    public com.openkm.ws.endpoint.Folder createSimple(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "createSimple"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Folder.class);
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

    public void delete(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "delete"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath});

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

    public com.openkm.ws.endpoint.Folder[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException {
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
                return (com.openkm.ws.endpoint.Folder[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Folder[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Folder[].class);
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

    public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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

    public com.openkm.ws.endpoint.Folder getProperties(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "getProperties"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Folder.class);
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

    public boolean isValid(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "isValid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath});

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

    public void move(java.lang.String token, java.lang.String fldPath, java.lang.String dstPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "move"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath, dstPath});

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

    public com.openkm.ws.endpoint.Folder rename(java.lang.String token, java.lang.String fldPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "rename"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token, fldPath, newName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.openkm.ws.endpoint.Folder) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.openkm.ws.endpoint.Folder) org.apache.axis.utils.JavaUtils.convert(_resp, com.openkm.ws.endpoint.Folder.class);
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

}
