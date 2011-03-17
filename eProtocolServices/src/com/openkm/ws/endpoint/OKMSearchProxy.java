package com.openkm.ws.endpoint;

public class OKMSearchProxy implements com.openkm.ws.endpoint.OKMSearch {
  private String _endpoint = null;
  private com.openkm.ws.endpoint.OKMSearch oKMSearch = null;
  
  public OKMSearchProxy() {
    _initOKMSearchProxy();
  }
  
  public OKMSearchProxy(String endpoint) {
    _endpoint = endpoint;
    _initOKMSearchProxy();
  }
  
  private void _initOKMSearchProxy() {
    try {
      oKMSearch = (new com.openkm.ws.endpoint.OKMSearchServiceLocator()).getOKMSearchPort();
      if (oKMSearch != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)oKMSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)oKMSearch)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (oKMSearch != null)
      ((javax.xml.rpc.Stub)oKMSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.openkm.ws.endpoint.OKMSearch getOKMSearch() {
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch;
  }
  
  public com.openkm.ws.endpoint.QueryResult[] find(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.find(token, params);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByContent(java.lang.String token, java.lang.String content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.findByContent(token, content);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByKeywords(java.lang.String token, com.openkm.ws.endpoint.HashSet keywords) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.findByKeywords(token, keywords);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByName(java.lang.String token, java.lang.String name) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.findByName(token, name);
  }
  
  public com.openkm.ws.endpoint.QueryResult[] findByStatement(java.lang.String token, java.lang.String statement, java.lang.String type) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.findByStatement(token, statement, type);
  }
  
  public com.openkm.ws.endpoint.Document[] getCategorizedDocuments(java.lang.String token, java.lang.String categoryId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.getCategorizedDocuments(token, categoryId);
  }
  
  public com.openkm.ws.endpoint.IntegerPair[] getKeywordMap(java.lang.String token, java.lang.String[] filter) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException{
    if (oKMSearch == null)
      _initOKMSearchProxy();
    return oKMSearch.getKeywordMap(token, filter);
  }
  
  
}