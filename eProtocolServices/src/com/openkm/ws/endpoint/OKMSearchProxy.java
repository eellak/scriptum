package com.openkm.ws.endpoint;

public class OKMSearchProxy implements com.openkm.ws.endpoint.OKMSearch_PortType {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMSearch_PortType oKMSearch_PortType = null;
  
  public OKMSearchProxy() {
    _initOKMSearchProxy();
  }
  
  public OKMSearchProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMSearchProxy();
  }
  
  private void _initOKMSearchProxy() {
    try {
      oKMSearch_PortType = (new com.openkm.ws.endpoint.OKMSearch_ServiceLocator()).getOKMSearchPort();
      if (oKMSearch_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMSearch_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMSearch_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMSearch_PortType != null)
      ((javax.xml.rpc.Stub)oKMSearch_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMSearch_PortType getOKMSearch_PortType() {
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType;
  }
  
  public long saveSearch(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.saveSearch(token, params);
  }
  
  public com.openkm.ws.endpoint.QueryParams getSearch(java.lang.String token, int qpId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.getSearch(token, qpId);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByName(java.lang.String token, java.lang.String name) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findByName(token, name);
  }
  
  public void deleteSearch(java.lang.String token, int qpId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    oKMSearch_PortType.deleteSearch(token, qpId);
  }
  
  public void updateSearch(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    oKMSearch_PortType.updateSearch(token, params);
  }
  
  public com.openkm.ws.endpoint.ResultSet findMoreLikeThis(java.lang.String token, java.lang.String uuid, int max) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findMoreLikeThis(token, uuid, max);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] find(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.find(token, params);
  }
  
  public com.openkm.ws.endpoint.Document[] getCategorizedDocuments(java.lang.String token, java.lang.String categoryId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.getCategorizedDocuments(token, categoryId);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByContent(java.lang.String token, java.lang.String content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findByContent(token, content);
  }
  
  public com.openkm.ws.endpoint.QueryParams[] getAllSearchs(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.getAllSearchs(token);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByKeywords(java.lang.String token, java.lang.String[] keywords) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findByKeywords(token, keywords);
  }
  
  public com.openkm.ws.endpoint.ResultSet findSimpleQueryPaginated(java.lang.String token, java.lang.String statement, int offset, int limit) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findSimpleQueryPaginated(token, statement, offset, limit);
  }
  
  public com.openkm.ws.endpoint.IntegerPair[] getKeywordMap(java.lang.String token, java.lang.String[] filter) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.getKeywordMap(token, filter);
  }
  
  public com.openkm.ws.endpoint.ResultSet findPaginated(java.lang.String token, com.openkm.ws.endpoint.QueryParams params, int offset, int limit) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch_PortType == null)
      _initOKMSearchProxy();
    return oKMSearch_PortType.findPaginated(token, params, offset, limit);
  }
  
  
}