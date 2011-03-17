package com.openkm.ws.endpoint;

public class OKMAuthProxy implements com.openkm.ws.endpoint.OKMAuth {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMAuth oKMAuth = null;
  
  public OKMAuthProxy() {
    _initOKMAuthProxy();
  }
  
  public OKMAuthProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMAuthProxy();
  }
  
  private void _initOKMAuthProxy() {
    try {
      oKMAuth = (new com.openkm.ws.endpoint.OKMAuthServiceLocator()).getOKMAuthPort();
      if (oKMAuth != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMAuth)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMAuth)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMAuth != null)
      ((javax.xml.rpc.Stub)oKMAuth)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMAuth getOKMAuth() {
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth;
  }
  
  public com.openkm.ws.endpoint.BytePair[] getGrantedRoles(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getGrantedRoles(token, nodePath);
  }
  
  public com.openkm.ws.endpoint.BytePair[] getGrantedUsers(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getGrantedUsers(token, nodePath);
  }
  
  public java.lang.String[] getMails(java.lang.String token, java.lang.String[] users) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getMails(token, users);
  }
  
  public java.lang.String[] getRoles(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getRoles(token);
  }
  
  public java.lang.String[] getRolesByUser(java.lang.String token, java.lang.String user) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getRolesByUser(token, user);
  }
  
  public java.lang.String[] getUsers(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getUsers(token);
  }
  
  public java.lang.String[] getUsersByRole(java.lang.String token, java.lang.String role) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.getUsersByRole(token, role);
  }
  
  public void grantRole(java.lang.String token, java.lang.String nodePath, java.lang.String role, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    oKMAuth.grantRole(token, nodePath, role, permissions, recursive);
  }
  
  public void grantUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    oKMAuth.grantUser(token, nodePath, user, permissions, recursive);
  }
  
  public java.lang.String login(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    return oKMAuth.login(user, password);
  }
  
  public void logout(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    oKMAuth.logout(token);
  }
  
  public void revokeRole(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    oKMAuth.revokeRole(token, nodePath, user, permissions, recursive);
  }
  
  public void revokeUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMAuth == null)
      _initOKMAuthProxy();
    oKMAuth.revokeUser(token, nodePath, user, permissions, recursive);
  }
  
  
}