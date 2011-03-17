package com.openkm.ws.endpoint;

public class OKMFolderProxy implements com.openkm.ws.endpoint.OKMFolder {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMFolder oKMFolder = null;
  
  public OKMFolderProxy() {
    _initOKMFolderProxy();
  }
  
  public OKMFolderProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMFolderProxy();
  }
  
  private void _initOKMFolderProxy() {
    try {
      oKMFolder = (new com.openkm.ws.endpoint.OKMFolderServiceLocator()).getOKMFolderPort();
      if (oKMFolder != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMFolder)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMFolder)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMFolder != null)
      ((javax.xml.rpc.Stub)oKMFolder)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMFolder getOKMFolder() {
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder;
  }
  
  public com.openkm.ws.endpoint.Folder create(java.lang.String token, com.openkm.ws.endpoint.Folder fld) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.create(token, fld);
  }
  
  public com.openkm.ws.endpoint.Folder createSimple(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.createSimple(token, fldPath);
  }
  
  public void delete(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    oKMFolder.delete(token, fldPath);
  }
  
  public com.openkm.ws.endpoint.Folder[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.getChilds(token, fldPath);
  }
  
  public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.getPath(token, uuid);
  }
  
  public com.openkm.ws.endpoint.Folder getProperties(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.getProperties(token, fldPath);
  }
  
  public boolean isValid(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.isValid(token, fldPath);
  }
  
  public void move(java.lang.String token, java.lang.String fldPath, java.lang.String dstPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    oKMFolder.move(token, fldPath, dstPath);
  }
  
  public com.openkm.ws.endpoint.Folder rename(java.lang.String token, java.lang.String fldPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMFolder == null)
      _initOKMFolderProxy();
    return oKMFolder.rename(token, fldPath, newName);
  }
  
  
}