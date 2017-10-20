package com.openkm.ws.endpoint;

public class OKMFolderProxy implements com.openkm.ws.endpoint.OKMFolder_PortType {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMFolder_PortType oKMFolder_PortType = null;
  
  public OKMFolderProxy() {
    _initOKMFolderProxy();
  }
  
  public OKMFolderProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMFolderProxy();
  }
  
  private void _initOKMFolderProxy() {
    try {
      oKMFolder_PortType = (new com.openkm.ws.endpoint.OKMFolder_ServiceLocator()).getOKMFolderPort();
      if (oKMFolder_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMFolder_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMFolder_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMFolder_PortType != null)
      ((javax.xml.rpc.Stub)oKMFolder_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMFolder_PortType getOKMFolder_PortType() {
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType;
  }
  
  public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.getPath(token, uuid);
  }
  
  public void move(java.lang.String token, java.lang.String fldPath, java.lang.String dstPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    oKMFolder_PortType.move(token, fldPath, dstPath);
  }
  
  public boolean isValid(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.isValid(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder create(java.lang.String token, com.openkm.ws.endpoint.Folder fld) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.create(token, fld);
  }
  
  public void delete(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    oKMFolder_PortType.delete(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder[] getChildren(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.getChildren(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.getChilds(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder createSimple(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.createSimple(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder rename(java.lang.String token, java.lang.String fldPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.rename(token, fldPath, newName);
  }
  
  public com.openkm.ws.endpoint.Folder getProperties(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder_PortType == null)
      _initOKMFolderProxy();
    return oKMFolder_PortType.getProperties(token, fldPath);
  }
  
  
}