/**
 * OKMFolder.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public interface OKMFolder extends java.rmi.Remote {
    public com.openkm.ws.endpoint.Folder create(java.lang.String token, com.openkm.ws.endpoint.Folder fld) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.Folder createSimple(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public void delete(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.LockException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.Folder[] getChilds(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException;
    public java.lang.String getPath(java.lang.String token, java.lang.String uuid) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.Folder getProperties(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public boolean isValid(java.lang.String token, java.lang.String fldPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public void move(java.lang.String token, java.lang.String fldPath, java.lang.String dstPath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.Folder rename(java.lang.String token, java.lang.String fldPath, java.lang.String newName) throws java.rmi.RemoteException, com.openkm.ws.endpoint.ItemExistsException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
}
