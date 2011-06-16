package gr.scriptum.eprotocol.ws;

/**
 * Interface for Dispatching requests to the Document Management System.
 * If the name tricks you, it is written regardless of OpenKM. You can 
 *  @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public interface OkmProtocolDispatcher {
	ResponseLogin login(RequestLogin loginRequest);

	Response logout(RequestLogout logoutRequest);

	Response delete(RequestDelete deleteRequest);

	ResponseAddDocumentToNode addDocumentToNode(RequestAddDocumentToNode newDocRequest);

	ResponseNewNode createNewNode(RequestNewNode newNodeRequest);

	ResponseNewNode createProtocolNode(RequestProtocolNode protocolRequest);

	ResponseRenameNode renameNode(RequestRenameNode renameRequest);
	
	Response moveNode( RequestMoveNode moveRequest );

	ResponseSearch searchByContent(RequestSearch searchRequest);
	
	ResponseSendDocument sendDocument(RequestSendDocument req);
}
