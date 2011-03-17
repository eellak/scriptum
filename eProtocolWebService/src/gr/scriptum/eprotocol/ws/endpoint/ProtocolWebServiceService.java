/**
 * ProtocolWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public interface ProtocolWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getProtocolWebServicePortAddress();

    public gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService getProtocolWebServicePort() throws javax.xml.rpc.ServiceException;

    public gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService getProtocolWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
