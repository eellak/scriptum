package com.openkm.ws.endpoint;

public class OKMDocumentProxy implements com.openkm.ws.endpoint.OKMDocument_PortType {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMDocument_PortType oKMDocument_PortType = null;
  
  public OKMDocumentProxy() {
    _initOKMDocumentProxy();
  }
  
  public OKMDocumentProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMDocumentProxy();
  }
  
  private void _initOKMDocumentProxy() {
    try {
      oKMDocument_PortType = (new com.openkm.ws.endpoint.OKMDocument_ServiceLocator()).getOKMDocumentPort();
      if (oKMDocument_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMDocument_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMDocument_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMDocument_PortType != null)
      ((javax.xml.rpc.Stub)oKMDocument_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMDocument_PortType getOKMDocument_PortType() {
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType;
  }
  
  public void setProperties(java.lang.String token, com.openkm.ws.endpoint.Document doc) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.setProperties(token, doc);
  }
  
  public void move(java.lang.String token, java.lang.String docPath, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.move(token, docPath, fldPath);
  }
  
  public boolean isValid(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.isValid(token, docPath);
  }
  
  public byte[] getContentByVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getContentByVersion(token, docPath, versionId);
  }
  
  public com.openkm.ws.endpoint.LockInfo lock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.lock(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Version checkin(java.lang.String token, java.lang.String docPath, byte[] content, java.lang.String comment) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.checkin(token, docPath, content, comment);
  }
  
  public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getPath(token, uuid);
  }
  
  public long getVersionHistorySize(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getVersionHistorySize(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document create(java.lang.String token, com.openkm.ws.endpoint.Document doc, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException, com.openkm.ws.endpoint.UnsupportedMimeTypeException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.create(token, doc, content);
  }
  
  public com.openkm.ws.endpoint.Version[] getVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getVersionHistory(token, docPath);
  }
  
  public void purge(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.purge(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document[] getChildren(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getChildren(token, fldPath);
  }
  
  public void unlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.unlock(token, docPath);
  }
  
  public void forceUnlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.forceUnlock(token, docPath);
  }
  
  public void delete(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.delete(token, docPath);
  }
  
  public void checkout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.checkout(token, docPath);
  }
  
  public byte[] getContent(java.lang.String token, java.lang.String docPath, boolean checkout) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getContent(token, docPath, checkout);
  }
  
  public com.openkm.ws.endpoint.Document[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getChilds(token, fldPath);
  }
  
  public void cancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.cancelCheckout(token, docPath);
  }
  
  public void restoreVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.restoreVersion(token, docPath, versionId);
  }
  
  public void forceCancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.PrincipalAdapterException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.forceCancelCheckout(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document createSimple(java.lang.String token, java.lang.String docPath, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.AutomationException, com.openkm.ws.endpoint.UnsupportedMimeTypeException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.createSimple(token, docPath, content);
  }
  
  public com.openkm.ws.endpoint.Document rename(java.lang.String token, java.lang.String docPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.ExtensionException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.rename(token, docPath, newName);
  }
  
  public void purgeVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    oKMDocument_PortType.purgeVersionHistory(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document getProperties(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument_PortType == null)
      _initOKMDocumentProxy();
    return oKMDocument_PortType.getProperties(token, docPath);
  }
  
  
}