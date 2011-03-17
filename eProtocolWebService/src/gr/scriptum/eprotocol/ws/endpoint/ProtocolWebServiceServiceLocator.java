/**
 * ProtocolWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolWebServiceServiceLocator extends org.apache.axis.client.Service implements gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceService {

    public ProtocolWebServiceServiceLocator() {
    }


    public ProtocolWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ProtocolWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ProtocolWebServicePort
    private java.lang.String ProtocolWebServicePort_address = "http://127.0.0.1:8080/eProtocolTestWebService/ProtocolWebService";

    public java.lang.String getProtocolWebServicePortAddress() {
        return ProtocolWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ProtocolWebServicePortWSDDServiceName = "ProtocolWebServicePort";

    public java.lang.String getProtocolWebServicePortWSDDServiceName() {
        return ProtocolWebServicePortWSDDServiceName;
    }

    public void setProtocolWebServicePortWSDDServiceName(java.lang.String name) {
        ProtocolWebServicePortWSDDServiceName = name;
    }

    public gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService getProtocolWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ProtocolWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getProtocolWebServicePort(endpoint);
    }

    public gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService getProtocolWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceBindingStub _stub = new gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceBindingStub(portAddress, this);
            _stub.setPortName(getProtocolWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setProtocolWebServicePortEndpointAddress(java.lang.String address) {
        ProtocolWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceBindingStub _stub = new gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceBindingStub(new java.net.URL(ProtocolWebServicePort_address), this);
                _stub.setPortName(getProtocolWebServicePortWSDDServiceName());
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
        if ("ProtocolWebServicePort".equals(inputPortName)) {
            return getProtocolWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "ProtocolWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.eprotocol.uit.gr/", "ProtocolWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ProtocolWebServicePort".equals(portName)) {
            setProtocolWebServicePortEndpointAddress(address);
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
