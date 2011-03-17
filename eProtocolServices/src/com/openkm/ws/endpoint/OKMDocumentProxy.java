package com.openkm.ws.endpoint;

public class OKMDocumentProxy implements com.openkm.ws.endpoint.OKMDocument {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMDocument oKMDocument = null;
  
  public OKMDocumentProxy() {
    _initOKMDocumentProxy();
  }
  
  public OKMDocumentProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMDocumentProxy();
  }
  
  private void _initOKMDocumentProxy() {
    try {
      oKMDocument = (new com.openkm.ws.endpoint.OKMDocumentServiceLocator()).getOKMDocumentPort();
      if (oKMDocument != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMDocument)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMDocument)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMDocument != null)
      ((javax.xml.rpc.Stub)oKMDocument)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMDocument getOKMDocument() {
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument;
  }
  
  public void cancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.cancelCheckout(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Version checkin(java.lang.String token, java.lang.String docPath, java.lang.String comment) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.checkin(token, docPath, comment);
  }
  
  public void checkout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.checkout(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document create(java.lang.String token, com.openkm.ws.endpoint.Document doc, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.UnsupportedMimeTypeException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.create(token, doc, content);
  }
  
  public com.openkm.ws.endpoint.Document createSimple(java.lang.String token, java.lang.String docPath, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException, com.openkm.ws.endpoint.UnsupportedMimeTypeException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.createSimple(token, docPath, content);
  }
  
  public void delete(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.delete(token, docPath);
  }
  
  public void forceCancelCheckout(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.forceCancelCheckout(token, docPath);
  }
  
  public void forceUnlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.forceUnlock(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getChilds(token, fldPath);
  }
  
  public byte[] getContent(java.lang.String token, java.lang.String docPath, boolean checkout) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getContent(token, docPath, checkout);
  }
  
  public byte[] getContentByVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getContentByVersion(token, docPath, versionId);
  }
  
  public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getPath(token, uuid);
  }
  
  public com.openkm.ws.endpoint.Document getProperties(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getProperties(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Version[] getVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getVersionHistory(token, docPath);
  }
  
  public long getVersionHistorySize(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.getVersionHistorySize(token, docPath);
  }
  
  public boolean isValid(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.isValid(token, docPath);
  }
  
  public void lock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.lock(token, docPath);
  }
  
  public void move(java.lang.String token, java.lang.String docPath, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.move(token, docPath, fldPath);
  }
  
  public void purge(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.purge(token, docPath);
  }
  
  public void purgeVersionHistory(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.purgeVersionHistory(token, docPath);
  }
  
  public com.openkm.ws.endpoint.Document rename(java.lang.String token, java.lang.String docPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    return oKMDocument.rename(token, docPath, newName);
  }
  
  public void restoreVersion(java.lang.String token, java.lang.String docPath, java.lang.String versionId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.restoreVersion(token, docPath, versionId);
  }
  
  public void setContent(java.lang.String token, java.lang.String docPath, byte[] content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.VirusDetectedException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.UserQuotaExceededException, com.openkm.ws.endpoint.FileSizeExceededException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.setContent(token, docPath, content);
  }
  
  public void setProperties(java.lang.String token, com.openkm.ws.endpoint.Document doc) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.VersionException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.setProperties(token, doc);
  }
  
  public void unlock(java.lang.String token, java.lang.String docPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMDocument == null)
      _initOKMDocumentProxy();
    oKMDocument.unlock(token, docPath);
  }
  
  
}