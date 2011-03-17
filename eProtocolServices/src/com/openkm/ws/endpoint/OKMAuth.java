/**
 * OKMAuth.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openkm.ws.endpoint;

public interface OKMAuth extends java.rmi.Remote {
    public com.openkm.ws.endpoint.BytePair[] getGrantedRoles(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public com.openkm.ws.endpoint.BytePair[] getGrantedUsers(java.lang.String token, java.lang.String nodePath) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public java.lang.String[] getMails(java.lang.String token, java.lang.String[] users) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException;
    public java.lang.String[] getRoles(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException;
    public java.lang.String[] getRolesByUser(java.lang.String token, java.lang.String user) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException;
    public java.lang.String[] getUsers(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException;
    public java.lang.String[] getUsersByRole(java.lang.String token, java.lang.String role) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PrincipalAdapterException;
    public void grantRole(java.lang.String token, java.lang.String nodePath, java.lang.String role, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public void grantUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public java.lang.String login(java.lang.String user, java.lang.String password) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public void logout(java.lang.String token) throws java.rmi.RemoteException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.DatabaseException;
    public void revokeRole(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
    public void revokeUser(java.lang.String token, java.lang.String nodePath, java.lang.String user, int permissions, boolean recursive) throws java.rmi.RemoteException, com.openkm.ws.endpoint.PathNotFoundException, com.openkm.ws.endpoint.RepositoryException, com.openkm.ws.endpoint.AccessDeniedException, com.openkm.ws.endpoint.DatabaseException;
}
