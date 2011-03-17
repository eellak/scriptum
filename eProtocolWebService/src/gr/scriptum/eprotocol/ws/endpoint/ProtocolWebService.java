/**
 * ProtocolWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gr.scriptum.eprotocol.ws.endpoint;

public interface ProtocolWebService extends java.rmi.Remote {
    public gr.scriptum.eprotocol.ws.endpoint.ProtocolInfo inquireProtocol(gr.scriptum.eprotocol.ws.endpoint.ProtocolQuery query) throws java.rmi.RemoteException;
    public gr.scriptum.eprotocol.ws.endpoint.ProtocolReceipt receiveProtocol(gr.scriptum.eprotocol.ws.endpoint.ProtocolMessage message) throws java.rmi.RemoteException;
}
