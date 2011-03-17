/**
 * OKMDocumentServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class OKMDocumentServiceLocator extends org.apache.axis.client.Service implements com.openkm.ws.endpoint.OKMDocumentService {

    public OKMDocumentServiceLocator() {
    }


    public OKMDocumentServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OKMDocumentServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OKMDocumentPort
    private java.lang.String OKMDocumentPort_address = "http://10.0.0.85:8080/OpenKM/OKMDocument";

    public java.lang.String getOKMDocumentPortAddress() {
        return OKMDocumentPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OKMDocumentPortWSDDServiceName = "OKMDocumentPort";

    public java.lang.String getOKMDocumentPortWSDDServiceName() {
        return OKMDocumentPortWSDDServiceName;
    }

    public void setOKMDocumentPortWSDDServiceName(java.lang.String name) {
        OKMDocumentPortWSDDServiceName = name;
    }

    public com.openkm.ws.endpoint.OKMDocument getOKMDocumentPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OKMDocumentPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOKMDocumentPort(endpoint);
    }

    public com.openkm.ws.endpoint.OKMDocument getOKMDocumentPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.openkm.ws.endpoint.OKMDocumentBindingStub _stub = new com.openkm.ws.endpoint.OKMDocumentBindingStub(portAddress, this);
            _stub.setPortName(getOKMDocumentPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOKMDocumentPortEndpointAddress(java.lang.String address) {
        OKMDocumentPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.openkm.ws.endpoint.OKMDocument.class.isAssignableFrom(serviceEndpointInterface)) {
                com.openkm.ws.endpoint.OKMDocumentBindingStub _stub = new com.openkm.ws.endpoint.OKMDocumentBindingStub(new java.net.URL(OKMDocumentPort_address), this);
                _stub.setPortName(getOKMDocumentPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("OKMDocumentPort".equals(inputPortName)) {
            return getOKMDocumentPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "OKMDocumentService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "OKMDocumentPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OKMDocumentPort".equals(portName)) {
            setOKMDocumentPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
