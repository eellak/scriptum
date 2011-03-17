/**
 * OKMSearch.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public interface OKMSearch extends java.rmi.Remote {
    public com.openkm.ws.endpoint.QueryResult[] find(java.lang.String token, com.openkm.ws.endpoint.QueryParams params) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.QueryResult[] findByContent(java.lang.String token, java.lang.String content) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.QueryResult[] findByKeywords(java.lang.String token, com.openkm.ws.endpoint.HashSet keywords) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.QueryResult[] findByName(java.lang.String token, java.lang.String name) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.IOException, com.openkm.ws.endpoint.ParseException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.QueryResult[] findByStatement(java.lang.String token, java.lang.String statement, java.lang.String type) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.Document[] getCategorizedDocuments(java.lang.String token, java.lang.String categoryId) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.IntegerPair[] getKeywordMap(java.lang.String token, java.lang.String[] filter) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException;
}
