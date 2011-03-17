package gr.scriptum.eprotocol.ws.endpoint;

public class ProtocolWebServiceProxy implements gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService {
  private String _endpoint = null;
  private gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService protocolWebService = null;
  
  public ProtocolWebServiceProxy() {
    _initProtocolWebServiceProxy();
  }
  
  public ProtocolWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initProtocolWebServiceProxy();
  }
  
  private void _initProtocolWebServiceProxy() {
    try {
      protocolWebService = (new gr.scriptum.eprotocol.ws.endpoint.ProtocolWebServiceServiceLocator()).getProtocolWebServicePort();
      if (protocolWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)protocolWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)protocolWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (protocolWebService != null)
      ((javax.xml.rpc.Stub)protocolWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gr.scriptum.eprotocol.ws.endpoint.ProtocolWebService getProtocolWebService() {
    if (protocolWebService == null)
      _initProtocolWebServiceProxy();
    return protocolWebService;
  }
  
  public gr.scriptum.eprotocol.ws.endpoint.ProtocolInfo inquireProtocol(gr.scriptum.eprotocol.ws.endpoint.ProtocolQuery query) throws java.rmi.RemoteException{
    if (protocolWebService == null)
      _initProtocolWebServiceProxy();
    return protocolWebService.inquireProtocol(query);
  }
  
  public gr.scriptum.eprotocol.ws.endpoint.ProtocolReceipt receiveProtocol(gr.scriptum.eprotocol.ws.endpoint.ProtocolMessage message) throws java.rmi.RemoteException{
    if (protocolWebService == null)
      _initProtocolWebServiceProxy();
    return protocolWebService.receiveProtocol(message);
  }
  
  
}