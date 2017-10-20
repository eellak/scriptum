/**
 * OKMAuth_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public interface OKMAuth_Service extends javax.xml.rpc.Service {
    public java.lang.String getOKMAuthPortAddress();

    public com.openkm.ws.endpoint.OKMAuth_PortType getOKMAuthPort() throws javax.xml.rpc.ServiceException;

    public com.openkm.ws.endpoint.OKMAuth_PortType getOKMAuthPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
