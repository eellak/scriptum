package com.openkm.ws.endpoint;

public class OKMAuthProxy implements com.openkm.ws.endpoint.OKMAuth_PortType {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMAuth_PortType oKMAuth_PortType = null;
  
  public OKMAuthProxy() {
    _initOKMAuthProxy();
  }
  
  public OKMAuthProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMAuthProxy();
  }
  
  private void _initOKMAuthProxy() {
    try {
      oKMAuth_PortType = (new com.openkm.ws.endpoint.OKMAuth_ServiceLocator()).getOKMAuthPort();
      if (oKMAuth_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMAuth_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMAuth_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMAuth_PortType != null)
      ((javax.xml.rpc.Stub)oKMAuth_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMAuth_PortType getOKMAuth_PortType() {
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType;
  }
  
  public void grantRole(java.lang.String token, java.lang.String nodePath, java.lang.String role, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    oKMAuth_PortType.grantRole(token, nodePath, role, permissions, recursive);
  }
  
  public void revokeUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    oKMAuth_PortType.revokeUser(token, nodePath, user, permissions, recursive);
  }
  
  public void logout(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    oKMAuth_PortType.logout(token);
  }
  
  public java.lang.String[] getUsersByRole(java.lang.String token, java.lang.String role) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getUsersByRole(token, role);
  }
  
  public void grantUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    oKMAuth_PortType.grantUser(token, nodePath, user, permissions, recursive);
  }
  
  public java.lang.String getName(java.lang.String token, java.lang.String user) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getName(token, user);
  }
  
  public java.lang.String[] getRolesByUser(java.lang.String token, java.lang.String user) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getRolesByUser(token, user);
  }
  
  public java.lang.String[] getUsers(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getUsers(token);
  }
  
  public java.lang.String getMail(java.lang.String token, java.lang.String user) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getMail(token, user);
  }
  
  public com.openkm.ws.endpoint.IntegerPair[] getGrantedRoles(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getGrantedRoles(token, nodePath);
  }
  
  public void revokeRole(java.lang.String token, java.lang.String nodePath, java.lang.String role, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    oKMAuth_PortType.revokeRole(token, nodePath, role, permissions, recursive);
  }
  
  public java.lang.String[] getRoles(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getRoles(token);
  }
  
  public java.lang.String login(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.login(user, password);
  }
  
  public com.openkm.ws.endpoint.IntegerPair[] getGrantedUsers(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth_PortType == null)
      _initOKMAuthProxy();
    return oKMAuth_PortType.getGrantedUsers(token, nodePath);
  }
  
  
}