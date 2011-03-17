/**
 * OKMSearchServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public class OKMSearchServiceLocator extends org.apache.axis.client.Service implements com.openkm.ws.endpoint.OKMSearchService {

    public OKMSearchServiceLocator() {
    }


    public OKMSearchServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OKMSearchServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OKMSearchPort
    private java.lang.String OKMSearchPort_address = "http://10.0.0.85:8080/OpenKM/OKMSearch";

    public java.lang.String getOKMSearchPortAddress() {
        return OKMSearchPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OKMSearchPortWSDDServiceName = "OKMSearchPort";

    public java.lang.String getOKMSearchPortWSDDServiceName() {
        return OKMSearchPortWSDDServiceName;
    }

    public void setOKMSearchPortWSDDServiceName(java.lang.String name) {
        OKMSearchPortWSDDServiceName = name;
    }

    public com.openkm.ws.endpoint.OKMSearch getOKMSearchPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OKMSearchPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOKMSearchPort(endpoint);
    }

    public com.openkm.ws.endpoint.OKMSearch getOKMSearchPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.openkm.ws.endpoint.OKMSearchBindingStub _stub = new com.openkm.ws.endpoint.OKMSearchBindingStub(portAddress, this);
            _stub.setPortName(getOKMSearchPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOKMSearchPortEndpointAddress(java.lang.String address) {
        OKMSearchPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.openkm.ws.endpoint.OKMSearch.class.isAssignableFrom(serviceEndpointInterface)) {
                com.openkm.ws.endpoint.OKMSearchBindingStub _stub = new com.openkm.ws.endpoint.OKMSearchBindingStub(new java.net.URL(OKMSearchPort_address), this);
                _stub.setPortName(getOKMSearchPortWSDDServiceName());
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
        if ("OKMSearchPort".equals(inputPortName)) {
            return getOKMSearchPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "OKMSearchService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://endpoint.ws.openkm.com/", "OKMSearchPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OKMSearchPort".equals(portName)) {
            setOKMSearchPortEndpointAddress(address);
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
